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
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;

/**
 * RocketMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class AbstractRocketMQConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	public DefaultMQProducer defaultMQProducer(RocketMQConfigurer configurer) {
		DefaultMQProducer producer = RocketMQUtil.createDefaultMQProducer(configurer.getGroup(),
				configurer.getAccessKey(), configurer.getSecretKey(), configurer.isEnableMsgTrace(),
				configurer.getCustomizedTraceTopic());

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

}
