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
package com.buession.logging.rabbitmq.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

/**
 * RabbitMQ 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RabbitLogHandler extends AbstractLogHandler {

	/**
	 * {@link RabbitTemplate}
	 */
	private final RabbitTemplate rabbitTemplate;

	/**
	 * 消息转换器 {@link MessageConverter}
	 */
	private final MessageConverter messageConverter;

	/**
	 * Exchange 名称
	 */
	private final String exchange;

	/**
	 * Routing key 名称
	 */
	private final String routingKey;

	/**
	 * 构造函数
	 *
	 * @param rabbitTemplate
	 *        {@link RabbitTemplate}
	 * @param exchange
	 * 		Exchange 名称
	 * @param routingKey
	 * 		Routing key 名称
	 */
	public RabbitLogHandler(final RabbitTemplate rabbitTemplate, final String exchange, final String routingKey) {
		this(rabbitTemplate, exchange, routingKey, new SimpleMessageConverter());
	}

	/**
	 * 构造函数
	 *
	 * @param rabbitTemplate
	 *        {@link RabbitTemplate}
	 * @param exchange
	 * 		Exchange 名称
	 * @param routingKey
	 * 		Routing key 名称
	 * @param messageConverter
	 *        {@link MessageConverter}
	 */
	public RabbitLogHandler(final RabbitTemplate rabbitTemplate, final String exchange, final String routingKey,
	                        final MessageConverter messageConverter) {
		Assert.isNull(rabbitTemplate, "RabbitTemplate is null.");
		Assert.isNull(exchange, "exchange is null.");
		Assert.isNull(routingKey, "routingKey is null.");
		Assert.isNull(messageConverter, "MessageConverter is null.");
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.messageConverter = messageConverter;
	}

	@Override
	protected Status doHandle(final LogData logData) throws Exception {
		final Message message = createMessage(logData);
		rabbitTemplate.convertAndSend(exchange, routingKey, message);
		return Status.SUCCESS;
	}

	private Message createMessage(final LogData logData) {
		try{
			return messageConverter.toMessage(logData, new MessageProperties());
		}catch(MessageConversionException e){
			throw new MessageConversionException("Could not convert '" + logData + "'", e);
		}
	}

}
