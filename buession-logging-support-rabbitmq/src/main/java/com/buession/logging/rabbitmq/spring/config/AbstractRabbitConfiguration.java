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
package com.buession.logging.rabbitmq.spring.config;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.logging.core.SslConfiguration;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.rabbitmq.core.Retry;
import com.buession.logging.rabbitmq.support.RabbitRetryTemplateConfigurer;
import com.rabbitmq.client.impl.DefaultCredentialsProvider;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.List;

/**
 * RabbitMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class AbstractRabbitConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	public ConnectionFactory rabbitConnectionFactory(RabbitConfigurer configurer) throws Exception {
		final RabbitConnectionFactoryBean rabbitConnectionFactoryBean = createRabbitConnectionFactoryBean(configurer);

		rabbitConnectionFactoryBean.afterPropertiesSet();

		final CachingConnectionFactory connectionFactory = createCachingConnectionFactory(configurer,
				rabbitConnectionFactoryBean.getObject());

		if(configurer.getCache() != null){
			Cache.Channel channel = configurer.getCache().getChannel();
			if(channel != null){
				propertyMapper.from(channel::getSize).to(connectionFactory::setChannelCacheSize);
				propertyMapper.from(channel::getCheckoutTimeout).as(Duration::toMillis)
						.to(connectionFactory::setChannelCheckoutTimeout);
			}

			Cache.Connection connection = configurer.getCache().getConnection();
			if(connection != null){
				propertyMapper.from(connection::getMode).to(connectionFactory::setCacheMode);
				propertyMapper.from(connection::getSize).to(connectionFactory::setConnectionCacheSize);
			}
		}

		return connectionFactory;
	}

	public RabbitTemplate rabbitTemplate(RabbitConfigurer configurer, ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		propertyMapper.from(configurer::getMessageConverter).to(rabbitTemplate::setMessageConverter);
		propertyMapper.from(configurer::getReceiveTimeout).as(Duration::toMillis).to(rabbitTemplate::setReceiveTimeout);
		propertyMapper.from(configurer::getReplyTimeout).as(Duration::toMillis).to(rabbitTemplate::setReplyTimeout);
		propertyMapper.from(configurer::getDefaultReceiveQueue).to(rabbitTemplate::setDefaultReceiveQueue);
		rabbitTemplate.setMandatory(determineMandatoryFlag());

		if(configurer.getRetry() != null && configurer.getRetry().isEnabled()){
			rabbitTemplate.setRetryTemplate(createRetryTemplate(configurer.getRetry()));
		}

		return rabbitTemplate;
	}

	protected RabbitConnectionFactoryBean createRabbitConnectionFactoryBean(final RabbitConfigurer configurer) {
		final RabbitConnectionFactoryBean rabbitConnectionFactoryBean = new RabbitConnectionFactoryBean();

		rabbitConnectionFactoryBean.setHost(configurer.getHost());
		rabbitConnectionFactoryBean.setPort(configurer.getPort());
		if(Validate.hasText(configurer.getUsername()) && Validate.hasText(configurer.getPassword())){
			rabbitConnectionFactoryBean.setCredentialsProvider(
					new DefaultCredentialsProvider(configurer.getUsername(), configurer.getPassword()));
		}
		propertyMapper.from(configurer::getVirtualHost).to(rabbitConnectionFactoryBean::setVirtualHost);
		propertyMapper.from(configurer::getConnectionTimeout).asInt(Duration::toMillis)
				.to(rabbitConnectionFactoryBean::setConnectionTimeout);
		propertyMapper.from(configurer::getChannelRpcTimeout).asInt(Duration::toMillis)
				.to(rabbitConnectionFactoryBean::setChannelRpcTimeout);
		propertyMapper.from(configurer::getRequestedHeartbeat).asInt(Duration::getSeconds)
				.to(rabbitConnectionFactoryBean::setRequestedHeartbeat);
		propertyMapper.from(configurer.getRequestedChannelMax())
				.to(rabbitConnectionFactoryBean::setRequestedChannelMax);

		SslConfiguration sslConfiguration = configurer.getSslConfiguration();
		if(sslConfiguration != null && sslConfiguration.isEnabled()){
			rabbitConnectionFactoryBean.setUseSSL(true);
			propertyMapper.from(sslConfiguration::getAlgorithms)
					.as((algorithms)->StringUtils.join(algorithms, ','))
					.to(rabbitConnectionFactoryBean::setSslAlgorithm);
			propertyMapper.from(sslConfiguration::getKeyStoreType).to(rabbitConnectionFactoryBean::setKeyStoreType);
			propertyMapper.from(sslConfiguration::getKeyStorePath).to(rabbitConnectionFactoryBean::setKeyStore);
			propertyMapper.from(sslConfiguration::getKeyStorePassword)
					.to(rabbitConnectionFactoryBean::setKeyStorePassphrase);
			propertyMapper.from(sslConfiguration::getTrustStoreType).to(rabbitConnectionFactoryBean::setTrustStoreType);
			propertyMapper.from(sslConfiguration::getTrustStorePath).to(rabbitConnectionFactoryBean::setTrustStore);
			propertyMapper.from(sslConfiguration::getTrustStorePassword)
					.to(rabbitConnectionFactoryBean::setTrustStorePassphrase);
			propertyMapper.from(sslConfiguration::isValidateServerCertificate)
					.to((validate)->rabbitConnectionFactoryBean.setSkipServerCertificateValidation(validate == false));
			propertyMapper.from(sslConfiguration::isVerifyHostname)
					.to(rabbitConnectionFactoryBean::setEnableHostnameVerification);
		}

		return rabbitConnectionFactoryBean;
	}

	protected CachingConnectionFactory createCachingConnectionFactory(final RabbitConfigurer configurer,
																	  final com.rabbitmq.client.ConnectionFactory connectionFactory) {
		final CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);

		propertyMapper.from(configurer::getPublisherReturns).to(cachingConnectionFactory::setPublisherReturns);
		propertyMapper.from(configurer::getPublisherConfirmType).to(cachingConnectionFactory::setPublisherConfirmType);

		return cachingConnectionFactory;
	}

	protected abstract boolean determineMandatoryFlag();

	protected RetryTemplate createRetryTemplate(final Retry retry) {
		final RetryTemplateFactory retryTemplateFactory = new RetryTemplateFactory(retry.getRetryCustomizers());
		return retryTemplateFactory.createRetryTemplate(retry, RabbitRetryTemplateConfigurer.Target.SENDER);
	}

	static class RetryTemplateFactory {

		private final List<RabbitRetryTemplateConfigurer> configurers;

		RetryTemplateFactory(final List<RabbitRetryTemplateConfigurer> configurers) {
			this.configurers = configurers;
		}

		RetryTemplate createRetryTemplate(final Retry properties, final RabbitRetryTemplateConfigurer.Target target) {
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

			if(configurers != null){
				for(RabbitRetryTemplateConfigurer configurer : configurers){
					configurer.configure(target, retryTemplate);
				}
			}

			return retryTemplate;
		}

	}

}
