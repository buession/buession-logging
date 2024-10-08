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

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.logging.rabbitmq.handler.RabbitLogHandler;
import com.buession.logging.rabbitmq.spring.config.RabbitLogHandlerFactoryBeanConfigurer;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * RabbitMQ 日志处理器 {@link RabbitLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RabbitLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<RabbitLogHandler> {

	private RabbitTemplate rabbitTemplate;

	/**
	 * Exchange 名称
	 */
	private String exchange;

	/**
	 * Routing key 名称
	 */
	private String routingKey;

	/**
	 * 构造函数
	 */
	public RabbitLogHandlerFactoryBean() {

	}

	/**
	 * 构造函数
	 *
	 * @param configurer
	 *        {@link RabbitLogHandlerFactoryBeanConfigurer}
	 */
	public RabbitLogHandlerFactoryBean(final RabbitLogHandlerFactoryBeanConfigurer configurer) {
		setExchange(configurer.getExchange());
		setRoutingKey(configurer.getRoutingKey());
	}

	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 返回 Exchange 名称
	 *
	 * @return Exchange 名称
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * 设置 Exchange 名称
	 *
	 * @param exchange
	 * 		Exchange 名称
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * 返回 Routing key 名称
	 *
	 * @return Routing key 名称
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * 设置 Routing key 名称
	 *
	 * @param routingKey
	 * 		Routing key 名称
	 */
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getRabbitTemplate(), "Property 'rabbitTemplate' is required");
		Assert.isBlank(getRoutingKey(), "Property 'topic' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = new RabbitLogHandler(getRabbitTemplate(), new Jackson2JsonMessageConverter());

					propertyMapper.alwaysApplyingWhenHasText().from(getExchange()).to(logHandler::setExchange);
					propertyMapper.alwaysApplyingWhenHasText().from(getRoutingKey()).to(logHandler::setRoutingKey);
				}
			}
		}
	}

}
