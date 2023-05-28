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
import com.buession.logging.rabbitmq.core.Retry;
import com.buession.logging.rabbitmq.support.RabbitRetryTemplateCustomizer;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class RetryTemplateFactory {

	private final List<RabbitRetryTemplateCustomizer> customizers;

	RetryTemplateFactory(final List<RabbitRetryTemplateCustomizer> customizers){
		this.customizers = customizers;
	}

	RetryTemplate createRetryTemplate(Retry properties, RabbitRetryTemplateCustomizer.Target target){
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
