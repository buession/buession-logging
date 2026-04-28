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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.springboot.autoconfigure.rocketmq;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rocketmq.spring.RocketMQLogHandlerFactoryBean;
import com.buession.logging.rocketmq.spring.config.AbstractRocketMQConfiguration;
import com.buession.logging.rocketmq.spring.config.RocketMQConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * RocketMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({RocketMQLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "rocketmq.enabled", havingValue = "true")
public class RocketMQConfiguration extends AbstractRocketMQConfiguration {

	private final RocketMQProperties rocketMQProperties;

	public RocketMQConfiguration(LogProperties logProperties) {
		this.rocketMQProperties = logProperties.getRocketmq();
	}

	@Bean(name = "loggingRocketMQConfigurer")
	@ConditionalOnMissingBean(name = "loggingRocketMQConfigurer")
	public RocketMQConfigurer rocketMQConfigurer() {
		final RocketMQConfigurer configurer = new RocketMQConfigurer();

		configurer.setNameServer(rocketMQProperties.getNameServer());
		configurer.setGroup(rocketMQProperties.getGroupName());
		configurer.setNamespace(rocketMQProperties.getNamespace());
		configurer.setNamespaceV2(rocketMQProperties.getNamespaceV2());
		configurer.setInstanceName(rocketMQProperties.getInstanceName());
		configurer.setAccessKey(rocketMQProperties.getAccessKey());
		configurer.setSecretKey(rocketMQProperties.getSecretKey());
		configurer.setAccessChannel(rocketMQProperties.getAccessChannel());
		configurer.setCharset(rocketMQProperties.getCharset());
		configurer.setSendMessageTimeout((int) rocketMQProperties.getSendMessageTimeout().toMillis());
		configurer.setMaxMessageSize((int) rocketMQProperties.getMaxMessageSize().toBytes());
		configurer.setCompressMessageBodyThreshold(
				(int) rocketMQProperties.getCompressMessageBodyThreshold().toBytes());
		configurer.setRetryNextServer(rocketMQProperties.isRetryNextServer());
		configurer.setRetryTimesWhenSendFailed(rocketMQProperties.getRetryTimesWhenSendFailed());
		configurer.setRetryTimesWhenSendAsyncFailed(rocketMQConfigurer().getRetryTimesWhenSendAsyncFailed());
		configurer.setEnableMsgTrace(rocketMQProperties.isEnableMsgTrace());
		configurer.setCustomizedTraceTopic(rocketMQProperties.getCustomizedTraceTopic());
		configurer.setTlsEnable(rocketMQProperties.isTlsEnable());

		return configurer;
	}

	@Bean(name = "loggingRocketMQProducer")
	@ConditionalOnMissingBean(name = "loggingRocketMQProducer")
	@Override
	public DefaultMQProducer defaultMQProducer(@Qualifier("loggingRocketMQConfigurer") RocketMQConfigurer configurer) {
		return super.defaultMQProducer(configurer);
	}

	@Bean(name = "loggingRocketTemplate")
	public RocketTemplate rocketTemplate(@Qualifier("loggingRocketMQConfigurer") RocketMQConfigurer configurer,
	                                     @Qualifier("loggingRocketMQProducer") DefaultMQProducer producer) {
		RocketTemplate rocketTemplate = super.rocketTemplate(configurer);

		rocketTemplate.setProducer(producer);

		return rocketTemplate;
	}

}
