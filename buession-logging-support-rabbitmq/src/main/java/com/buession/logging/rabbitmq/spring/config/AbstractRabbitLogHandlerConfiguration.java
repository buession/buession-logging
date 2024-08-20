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

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.utils.StringUtils;
import com.buession.logging.core.SslConfiguration;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.rabbitmq.core.Constants;
import com.buession.logging.rabbitmq.core.Retry;
import com.buession.logging.rabbitmq.core.Template;
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.rabbitmq.support.RabbitRetryTemplateCustomizer;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.RabbitProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({RabbitLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "rabbit.enabled", havingValue = "true")
public class RabbitLogHandlerConfiguration extends AbstractLogHandlerConfiguration<RabbitProperties> {

	public RabbitLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getRabbit());
	}

	@Bean(name = "loggingRabbitConnectionFactory")
	@ConditionalOnMissingBean(name = "loggingRabbitConnectionFactory")
	public ConnectionFactory rabbitConnectionFactory() throws Exception {
		Assert.isBlank(properties.getHost(), "Property 'host' is required");

		final RabbitConnectionFactoryBean rabbitConnectionFactoryBean = new RabbitConnectionFactoryBean();

		propertyMapper.from(properties::getHost).to(rabbitConnectionFactoryBean::setHost);
		propertyMapper.from(this::determinePort).to(rabbitConnectionFactoryBean::setPort);
		propertyMapper.from(properties::getUsername).to(rabbitConnectionFactoryBean::setUsername);
		propertyMapper.from(properties::getPassword).to(rabbitConnectionFactoryBean::setPassword);
		propertyMapper.from(properties.getVirtualHost()).to(rabbitConnectionFactoryBean::setVirtualHost);
		propertyMapper.from(properties::getRequestedHeartbeat).asInt(Duration::getSeconds)
				.to(rabbitConnectionFactoryBean::setRequestedHeartbeat);
		propertyMapper.from(properties.getRequestedChannelMax())
				.to(rabbitConnectionFactoryBean::setRequestedChannelMax);
		propertyMapper.from(properties::getConnectionTimeout).asInt(Duration::toMillis)
				.to(rabbitConnectionFactoryBean::setConnectionTimeout);

		SslConfiguration sslConfiguration = properties.getSslConfiguration();
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

		rabbitConnectionFactoryBean.afterPropertiesSet();

		final CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				rabbitConnectionFactoryBean.getObject());

		propertyMapper.from(properties.getHost() + ":" + determinePort()).to(connectionFactory::setAddresses);
		propertyMapper.from(properties.isPublisherReturns()).to(connectionFactory::setPublisherReturns);
		propertyMapper.from(properties.getPublisherConfirmType()).to(connectionFactory::setPublisherConfirmType);

		if(properties.getCache() != null){
			Cache.Channel channel = properties.getCache().getChannel();
			if(channel != null){
				propertyMapper.from(channel::getSize).to(connectionFactory::setChannelCacheSize);
				propertyMapper.from(channel::getCheckoutTimeout).as(Duration::toMillis)
						.to(connectionFactory::setChannelCheckoutTimeout);
			}

			Cache.Connection connection = properties.getCache().getConnection();
			if(connection != null){
				propertyMapper.from(connection::getMode).to(connectionFactory::setCacheMode);
				propertyMapper.from(connection::getSize).to(connectionFactory::setConnectionCacheSize);
			}
		}

		return connectionFactory;
	}

	@Bean(name = "loggingRabbitRabbitTemplate")
	public RabbitTemplate rabbitTemplate(
			@Qualifier("loggingRabbitConnectionFactory") ObjectProvider<ConnectionFactory> connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory.getIfAvailable());

		rabbitTemplate.setMandatory(determineMandatoryFlag());

		Template template = properties.getTemplate();
		if(template != null){
			propertyMapper.from(template::getReceiveTimeout).as(Duration::toMillis)
					.to(rabbitTemplate::setReceiveTimeout);
			propertyMapper.from(template::getReplyTimeout).as(Duration::toMillis).to(rabbitTemplate::setReplyTimeout);
			propertyMapper.from(template::getDefaultReceiveQueue).to(rabbitTemplate::setDefaultReceiveQueue);

			if(template.getRetry() != null && template.getRetry().isEnabled()){
				rabbitTemplate.setRetryTemplate(createRetryTemplate(template));
			}
		}

		return rabbitTemplate;
	}

	@Bean
	public RabbitLogHandlerFactoryBean logHandlerFactoryBean(@Qualifier("loggingRabbitRabbitTemplate")
															 ObjectProvider<RabbitTemplate> rabbitTemplate) {
		final RabbitLogHandlerFactoryBean logHandlerFactoryBean = new RabbitLogHandlerFactoryBean();

		rabbitTemplate.ifAvailable(logHandlerFactoryBean::setRabbitTemplate);
		logHandlerFactoryBean.setExchange(properties.getExchange());
		logHandlerFactoryBean.setRoutingKey(properties.getRoutingKey());

		return logHandlerFactoryBean;
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
		if(properties.getTemplate() != null){
			Boolean mandatory = properties.getTemplate().getMandatory();
			if(mandatory != null){
				return mandatory;
			}
		}

		return properties.isPublisherReturns();
	}

	protected RetryTemplate createRetryTemplate(final Template template) {
		final RetryTemplateFactory retryTemplateFactory = new RetryTemplateFactory(template.getRetryCustomizers());
		return retryTemplateFactory.createRetryTemplate(template.getRetry(),
				RabbitRetryTemplateCustomizer.Target.SENDER);
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
