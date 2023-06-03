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
package com.buession.logging.rabbitmq.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.core.SslConfiguration;
import com.buession.logging.rabbitmq.core.Template;
import com.buession.logging.rabbitmq.handler.RabbitLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.time.Duration;

/**
 * RabbitMQ 日志处理器 {@link RabbitLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RabbitLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<RabbitLogHandler> {

	private RabbitTemplate rabbitTemplate;

	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(rabbitTemplate, "RabbitTemplate is null.");
		logHandler = new RabbitLogHandler(rabbitTemplate, new Jackson2JsonMessageConverter());
	}

}
