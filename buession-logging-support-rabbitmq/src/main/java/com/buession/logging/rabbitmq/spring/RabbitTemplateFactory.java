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
import com.buession.logging.rabbitmq.core.Retry;
import com.buession.logging.rabbitmq.core.Template;
import com.buession.logging.rabbitmq.support.RabbitRetryTemplateCustomizer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RabbitTemplateFactory {

	private org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory;

	/**
	 * Template 配置
	 */
	private Template template;

	/**
	 * Whether to enable publisher returns.
	 */
	private boolean publisherReturns;

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public boolean isPublisherReturns() {
		return publisherReturns;
	}

	public void setPublisherReturns(boolean publisherReturns) {
		this.publisherReturns = publisherReturns;
	}

	protected RabbitTemplate createRabbitTemplate() {
		Assert.isNull(getConnectionFactory(), "Property 'connectionFactory' is required");

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		rabbitTemplate.setMandatory(determineMandatoryFlag());

		propertyMapper.from(template::getReceiveTimeout).as(Duration::toMillis).to(rabbitTemplate::setReceiveTimeout);
		propertyMapper.from(template::getReplyTimeout).as(Duration::toMillis).to(rabbitTemplate::setReplyTimeout);
		propertyMapper.from(template::getDefaultReceiveQueue).to(rabbitTemplate::setDefaultReceiveQueue);

		if(template.getRetry() != null && template.getRetry().isEnabled()){
			rabbitTemplate.setRetryTemplate(createRetryTemplate());
		}

		return rabbitTemplate;
	}

	protected RetryTemplate createRetryTemplate() {
		final RetryTemplateFactory retryTemplateFactory = new RetryTemplateFactory(template.getRetryCustomizers());
		return retryTemplateFactory.createRetryTemplate(template.getRetry(),
				RabbitRetryTemplateCustomizer.Target.SENDER);
	}

	protected boolean determineMandatoryFlag() {
		Boolean mandatory = template.getMandatory();
		return mandatory != null ? mandatory : publisherReturns;
	}

	static class RetryTemplateFactory {

		private final List<RabbitRetryTemplateCustomizer> customizers;

		RetryTemplateFactory(final List<RabbitRetryTemplateCustomizer> customizers) {
			this.customizers = customizers;
		}

		RetryTemplate createRetryTemplate(Retry properties, RabbitRetryTemplateCustomizer.Target target) {
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
			final RetryTemplate retryTemplate = new RetryTemplate();

			final SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
			propertyMapper.from(properties::getMaxAttempts).to(retryPolicy::setMaxAttempts);
			retryTemplate.setRetryPolicy(retryPolicy);

			final ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
			propertyMapper.from(properties::getInitialInterval).as(Duration::toMillis)
					.to(backOffPolicy::setInitialInterval);
			propertyMapper.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
			propertyMapper.from(properties::getMaxInterval).as(Duration::toMillis)
					.to(backOffPolicy::setMaxInterval);
			retryTemplate.setBackOffPolicy(backOffPolicy);

			if(customizers != null){
				for(RabbitRetryTemplateCustomizer customizer : customizers){
					customizer.customize(target, retryTemplate);
				}
			}

			return retryTemplate;
		}

	}

}
