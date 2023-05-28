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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.springboot.autoconfigure.rabbit;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rabbitmq.handler.RabbitLogHandler;
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({RabbitLogHandler.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "rabbit.enabled", havingValue = "true")
public class RabbitLogHandlerConfiguration extends AbstractLogHandlerConfiguration<RabbitProperties,
		RabbitLogHandlerFactoryBean> {

	public RabbitLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getRabbit());
	}

	@Bean
	@Override
	public RabbitLogHandlerFactoryBean logHandlerFactoryBean() {
		final RabbitLogHandlerFactoryBean logHandlerFactoryBean = new RabbitLogHandlerFactoryBean();

		propertyMapper.from(handlerProperties::getHost).to(logHandlerFactoryBean::setHost);
		propertyMapper.from(handlerProperties::getPort).to(logHandlerFactoryBean::setPort);
		propertyMapper.from(handlerProperties::getUsername).to(logHandlerFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(logHandlerFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getVirtualHost).to(logHandlerFactoryBean::setVirtualHost);
		propertyMapper.from(handlerProperties::getRequestedHeartbeat).to(logHandlerFactoryBean::setRequestedHeartbeat);
		propertyMapper.from(handlerProperties::getRequestedChannelMax)
				.to(logHandlerFactoryBean::setRequestedChannelMax);
		propertyMapper.from(handlerProperties::isPublisherReturns).to(logHandlerFactoryBean::setPublisherReturns);
		propertyMapper.from(handlerProperties::getPublisherConfirmType)
				.to(logHandlerFactoryBean::setPublisherConfirmType);
		propertyMapper.from(handlerProperties::getConnectionTimeout).to(logHandlerFactoryBean::setConnectionTimeout);
		propertyMapper.from(handlerProperties::getSslConfiguration).to(logHandlerFactoryBean::setSslConfiguration);
		propertyMapper.from(handlerProperties::getCache).to(logHandlerFactoryBean::setCache);
		propertyMapper.from(handlerProperties::getTemplate).to(logHandlerFactoryBean::setTemplate);
		propertyMapper.from(handlerProperties::getCache).to(logHandlerFactoryBean::setCache);

		return logHandlerFactoryBean;
	}

}
