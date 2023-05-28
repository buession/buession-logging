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
import com.buession.core.utils.StringUtils;
import com.buession.logging.core.SslConfiguration;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.rabbitmq.core.Constants;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;

import java.time.Duration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class ConnectionFactory {

	/**
	 * RabbitMQ 地址
	 */
	private final String host;

	/**
	 * RabbitMQ 端口
	 */
	private final int port;

	/**
	 * 用户名
	 */
	private final String username;

	/**
	 * 密码
	 */
	private final String password;

	/**
	 * 虚拟机
	 */
	private final String virtualHost;

	/**
	 * 连接超时
	 */
	private final Duration connectionTimeout;

	/**
	 * SSL 配置
	 */
	private final SslConfiguration sslConfiguration;

	/**
	 * Requested heartbeat timeout; zero for none. If a duration suffix is not specified,
	 * seconds will be used.
	 */
	private final Duration requestedHeartbeat;

	/**
	 * Number of channels per connection requested by the client. Use 0 for unlimited.
	 */
	private final int requestedChannelMax;

	/**
	 * Whether to enable publisher returns.
	 */
	private final boolean publisherReturns;

	/**
	 * Type of publisher confirms to use.
	 */
	private final CachingConnectionFactory.ConfirmType publisherConfirmType;

	/**
	 * 缓存配置
	 */
	private final Cache cache;

	ConnectionFactory(final String host, final int port, final String username, final String password,
					  final String virtualHost, final Duration connectionTimeout,
					  final SslConfiguration sslConfiguration, final Duration requestedHeartbeat,
					  final int requestedChannelMax, final boolean publisherReturns,
					  final CachingConnectionFactory.ConfirmType publisherConfirmType, final Cache cache){
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.virtualHost = virtualHost;
		this.connectionTimeout = connectionTimeout;
		this.sslConfiguration = sslConfiguration;
		this.requestedHeartbeat = requestedHeartbeat;
		this.requestedChannelMax = requestedChannelMax;
		this.publisherReturns = publisherReturns;
		this.publisherConfirmType = publisherConfirmType;
		this.cache = cache;
	}

	public org.springframework.amqp.rabbit.connection.ConnectionFactory createConnectionFactory() throws Exception{
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = createRabbitConnectionFactory();
		final CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);

		propertyMapper.from(host).to(connectionFactory::setAddresses);
		propertyMapper.from(publisherReturns).to(connectionFactory::setPublisherReturns);
		propertyMapper.from(publisherConfirmType).to(connectionFactory::setPublisherConfirmType);

		Cache.Channel channel = cache.getChannel();
		propertyMapper.from(channel::getSize).to(connectionFactory::setChannelCacheSize);
		propertyMapper.from(channel::getCheckoutTimeout).as(Duration::toMillis)
				.to(connectionFactory::setChannelCheckoutTimeout);

		Cache.Connection connection = cache.getConnection();
		propertyMapper.from(connection::getMode).to(connectionFactory::setCacheMode);
		propertyMapper.from(connection::getSize).to(connectionFactory::setConnectionCacheSize);

		return connectionFactory;
	}

	private com.rabbitmq.client.ConnectionFactory createRabbitConnectionFactory() throws Exception{
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();

		propertyMapper.from(host).to(factory::setHost);
		propertyMapper.from(this::determinePort).to(factory::setPort);
		propertyMapper.from(username).to(factory::setUsername);
		propertyMapper.from(password).to(factory::setPassword);
		propertyMapper.from(virtualHost).to(factory::setVirtualHost);
		propertyMapper.from(requestedHeartbeat).asInt(Duration::getSeconds).to(factory::setRequestedHeartbeat);
		propertyMapper.from(requestedChannelMax).to(factory::setRequestedChannelMax);
		propertyMapper.from(connectionTimeout).asInt(Duration::toMillis).to(factory::setConnectionTimeout);

		if(sslConfiguration != null && sslConfiguration.isEnabled()){
			factory.setUseSSL(true);
			propertyMapper.from(sslConfiguration::getAlgorithms).as((algorithms)->StringUtils.join(algorithms, ','))
					.to(factory::setSslAlgorithm);
			propertyMapper.from(sslConfiguration::getKeyStoreType).to(factory::setKeyStoreType);
			propertyMapper.from(sslConfiguration::getKeyStorePath).to(factory::setKeyStore);
			propertyMapper.from(sslConfiguration::getKeyStorePassword).to(factory::setKeyStorePassphrase);
			propertyMapper.from(sslConfiguration::getTrustStoreType).to(factory::setTrustStoreType);
			propertyMapper.from(sslConfiguration::getTrustStorePath).to(factory::setTrustStore);
			propertyMapper.from(sslConfiguration::getTrustStorePassword).to(factory::setTrustStorePassphrase);
			propertyMapper.from(sslConfiguration::isValidateServerCertificate)
					.to((validate)->factory.setSkipServerCertificateValidation(validate == false));
			propertyMapper.from(sslConfiguration::isVerifyHostname).to(factory::setEnableHostnameVerification);
		}

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	protected int determinePort(){
		if(port > 0){
			return port;
		}

		return sslConfiguration != null && sslConfiguration.isEnabled() ? Constants.DEFAULT_SECURE_PORT :
				Constants.DEFAULT_PORT;
	}

}
