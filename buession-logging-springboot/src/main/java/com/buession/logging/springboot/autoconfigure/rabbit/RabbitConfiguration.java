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
package com.buession.logging.springboot.autoconfigure.rabbit;

import com.buession.logging.rabbitmq.core.Constants;
import com.buession.logging.rabbitmq.spring.config.AbstractRabbitConfiguration;
import com.buession.logging.rabbitmq.spring.config.RabbitConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.RabbitProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * RabbitMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "rabbit.enabled", havingValue = "true")
public class RabbitConfiguration extends AbstractRabbitConfiguration {

	private final RabbitProperties properties;

	public RabbitConfiguration(LogProperties logProperties) {
		this.properties = logProperties.getRabbit();
	}

	@Bean(name = "loggingRabbitConfigurer")
	@ConditionalOnMissingBean(name = "loggingRabbitConfigurer")
	public RabbitConfigurer rabbitConfigurer() {
		final RabbitConfigurer configurer = new RabbitConfigurer();

		configurer.setHost(properties.getHost());
		configurer.setPort(determinePort());
		configurer.setUsername(properties.getUsername());
		configurer.setPassword(properties.getPassword());
		configurer.setVirtualHost(properties.getVirtualHost());
		configurer.setConnectionTimeout(properties.getConnectionTimeout());
		configurer.setChannelRpcTimeout(properties.getChannelRpcTimeout());
		configurer.setRequestedHeartbeat(properties.getRequestedHeartbeat());
		configurer.setRequestedChannelMax(properties.getRequestedChannelMax());
		configurer.setReceiveTimeout(properties.getReceiveTimeout());
		configurer.setReplyTimeout(properties.getReplyTimeout());
		configurer.setDefaultReceiveQueue(properties.getDefaultReceiveQueue());
		configurer.setSslConfiguration(properties.getSslConfiguration());
		configurer.setPublisherReturns(properties.isPublisherReturns());
		configurer.setPublisherConfirmType(properties.getPublisherConfirmType());
		propertyMapper.from(properties::getMessageConverter).as(BeanUtils::instantiateClass)
				.to(configurer::setMessageConverter);
		configurer.setCache(properties.getCache());
		configurer.setRetry(properties.getRetry());

		return configurer;
	}

	@Bean(name = "loggingRabbitConnectionFactory")
	@ConditionalOnMissingBean(name = "loggingRabbitConnectionFactory")
	public ConnectionFactory rabbitConnectionFactory(@Qualifier("loggingRabbitConfigurer") RabbitConfigurer configurer)
			throws Exception {
		return super.rabbitConnectionFactory(configurer);
	}

	@Bean(name = "loggingRabbitRabbitTemplate")
	public RabbitTemplate rabbitTemplate(
			@Qualifier("loggingRabbitConfigurer") RabbitConfigurer configurer,
			@Qualifier("loggingRabbitConnectionFactory") ConnectionFactory connectionFactory) {
		return super.rabbitTemplate(configurer, connectionFactory);
	}

	protected int determinePort() {
		if(properties.getPort() > 0){
			return properties.getPort();
		}

		return properties.getSslConfiguration() != null &&
				properties.getSslConfiguration().isEnabled() ? Constants.DEFAULT_SECURE_PORT :
				Constants.DEFAULT_PORT;
	}

	protected boolean determineMandatoryFlag() {
		Boolean mandatory = properties.getMandatory();
		if(mandatory != null){
			return mandatory;
		}

		return properties.isPublisherReturns();
	}

}
