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
import com.buession.logging.rabbitmq.core.Template;
import com.buession.logging.rabbitmq.support.RabbitRetryTemplateCustomizer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class RabbitTemplateFactory {

	private final ConnectionFactory connectionFactory;

	/**
	 * Template 配置
	 */
	private final Template template;

	/**
	 * Whether to enable publisher returns.
	 */
	private final boolean publisherReturns;

	public RabbitTemplateFactory(final ConnectionFactory connectionFactory,
								 final Template template, final boolean publisherReturns){
		this.connectionFactory = connectionFactory;
		this.template = template;
		this.publisherReturns = publisherReturns;
	}

	public RabbitTemplate createRabbitTemplate() throws Exception{
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory.createConnectionFactory());

		rabbitTemplate.setMandatory(determineMandatoryFlag());

		propertyMapper.from(template::getReceiveTimeout).as(Duration::toMillis).to(rabbitTemplate::setReceiveTimeout);
		propertyMapper.from(template::getReplyTimeout).as(Duration::toMillis).to(rabbitTemplate::setReplyTimeout);
		propertyMapper.from(template::getDefaultReceiveQueue).to(rabbitTemplate::setDefaultReceiveQueue);

		if(template.getRetry() != null && template.getRetry().isEnabled()){
			rabbitTemplate.setRetryTemplate(createRetryTemplate());
		}

		return rabbitTemplate;
	}

	private RetryTemplate createRetryTemplate(){
		final RetryTemplateFactory retryTemplateFactory = new RetryTemplateFactory(template.getRetryCustomizers());
		return retryTemplateFactory.createRetryTemplate(template.getRetry(),
				RabbitRetryTemplateCustomizer.Target.SENDER);
	}

	private boolean determineMandatoryFlag(){
		Boolean mandatory = template.getMandatory();
		return mandatory != null ? mandatory : publisherReturns;
	}

}
