/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.rocketmq.spring.config;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.trace.AsyncTraceDispatcher;
import org.apache.rocketmq.client.trace.TraceDispatcher;
import org.apache.rocketmq.client.trace.hook.SendMessageTraceHookImpl;
import org.apache.rocketmq.spring.core.RocketTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * RocketMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class AbstractRocketMQConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	private final static Logger logger = LoggerFactory.getLogger(AbstractRocketMQConfiguration.class);

	public DefaultMQProducer defaultMQProducer(RocketMQConfigurer configurer) {
		DefaultMQProducer producer = createDefaultMQProducer(configurer.getGroup(), configurer.getAccessKey(),
				configurer.getSecretKey(), configurer.isEnableMsgTrace(), configurer.getCustomizedTraceTopic());

		producer.setNamesrvAddr(configurer.getNameServer());
		if(Validate.hasText(configurer.getNamespace())){
			producer.setNamespace(configurer.getNamespace());
		}
		if(Validate.hasText(configurer.getNamespaceV2())){
			producer.setNamespaceV2(configurer.getNamespaceV2());
		}
		producer.setInstanceName(configurer.getInstanceName());

		if(configurer.getAccessChannel() != null){
			producer.setAccessChannel(configurer.getAccessChannel());
		}
		producer.setSendMsgTimeout(configurer.getSendMessageTimeout());
		producer.setMaxMessageSize(configurer.getMaxMessageSize());
		producer.setCompressMsgBodyOverHowmuch(configurer.getCompressMessageBodyThreshold());
		producer.setRetryAnotherBrokerWhenNotStoreOK(configurer.isRetryNextServer());
		producer.setRetryTimesWhenSendFailed(configurer.getRetryTimesWhenSendFailed());
		producer.setRetryTimesWhenSendAsyncFailed(configurer.getRetryTimesWhenSendAsyncFailed());
		producer.setUseTLS(configurer.isTlsEnable());

		return producer;
	}

	public RocketTemplate rocketTemplate(RocketMQConfigurer configurer) {
		final RocketTemplate rocketTemplate = new RocketTemplate();

		rocketTemplate.setCharset(configurer.getCharset());
		rocketTemplate.setMessageConverter(new RocketMQMessageConverter().getMessageConverter());

		return rocketTemplate;
	}

	public static DefaultMQProducer createDefaultMQProducer(String groupName, String ak, String sk,
	                                                        boolean isEnableMsgTrace, String customizedTraceTopic) {

		boolean isEnableAcl = StringUtils.hasLength(ak) && StringUtils.hasLength(sk);
		DefaultMQProducer producer;

		if(isEnableAcl){
			producer = new TransactionMQProducer(groupName, new AclClientRPCHook(new SessionCredentials(ak, sk)));
			producer.setVipChannelEnabled(false);
		}else{
			producer = new TransactionMQProducer(groupName);
		}

		if(isEnableMsgTrace){
			try{
				AsyncTraceDispatcher dispatcher = new AsyncTraceDispatcher(groupName, TraceDispatcher.Type.PRODUCE, 10,
						customizedTraceTopic,
						isEnableAcl ? new AclClientRPCHook(new SessionCredentials(ak, sk)) : null);
				dispatcher.setHostProducer(producer.getDefaultMQProducerImpl());
				Field field = DefaultMQProducer.class.getDeclaredField("traceDispatcher");
				field.setAccessible(true);
				field.set(producer, dispatcher);
				producer.getDefaultMQProducerImpl().registerSendMessageHook(
						new SendMessageTraceHookImpl(dispatcher));
			}catch(Throwable e){
				logger.error("system trace hook init failed ,maybe can't send msg trace data");
			}
		}

		return producer;
	}

}
