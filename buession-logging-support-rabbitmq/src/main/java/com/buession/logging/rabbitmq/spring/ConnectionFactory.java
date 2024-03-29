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
public class ConnectionFactory {

	public final static String DEFAULT_HOST = "localhost";

	public final static String DEFAULT_USERNAME = "guest";

	public final static String DEFAULT_PASSWORD = "guest";

	public final static String DEFAULT_VIRTUAL_HOST = "/";

	public final static Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(1);

	public final static int DEFAULT_REQUESTED_CHANNEL_MAX = 2047;

	/**
	 * RabbitMQ 地址
	 */
	private String host = DEFAULT_HOST;

	/**
	 * RabbitMQ 端口
	 */
	private int port;

	/**
	 * 用户名
	 */
	private String username = DEFAULT_USERNAME;

	/**
	 * 密码
	 */
	private String password = DEFAULT_PASSWORD;

	/**
	 * 虚拟机
	 */
	private String virtualHost = DEFAULT_VIRTUAL_HOST;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

	/**
	 * SSL 配置
	 */
	private SslConfiguration sslConfiguration;

	/**
	 * Requested heartbeat timeout; zero for none. If a duration suffix is not specified,
	 * seconds will be used.
	 */
	private Duration requestedHeartbeat;

	/**
	 * Number of channels per connection requested by the client. Use 0 for unlimited.
	 */
	private int requestedChannelMax = DEFAULT_REQUESTED_CHANNEL_MAX;

	/**
	 * Whether to enable publisher returns.
	 */
	private boolean publisherReturns;

	/**
	 * Type of publisher confirms to use.
	 */
	private CachingConnectionFactory.ConfirmType publisherConfirmType;

	/**
	 * 缓存配置
	 */
	private Cache cache;

	/**
	 * 返回 RabbitMQ 地址
	 *
	 * @return RabbitMQ 地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 RabbitMQ 地址
	 *
	 * @param host
	 * 		RabbitMQ 地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 RabbitMQ 端口
	 *
	 * @return RabbitMQ 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 RabbitMQ 端口
	 *
	 * @param port
	 * 		RabbitMQ 端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回虚拟机
	 *
	 * @return 虚拟机
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * 设置虚拟机
	 *
	 * @param virtualHost
	 * 		虚拟机
	 */
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectionTimeout
	 * 		连接超时
	 */
	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 返回 SSL 配置
	 *
	 * @return SSL 配置
	 */
	public SslConfiguration getSslConfiguration() {
		return sslConfiguration;
	}

	/**
	 * 设置 SSL 配置
	 *
	 * @param sslConfiguration
	 * 		SSL 配置
	 */
	public void setSslConfiguration(SslConfiguration sslConfiguration) {
		this.sslConfiguration = sslConfiguration;
	}

	/**
	 * Return requested heartbeat timeout.
	 *
	 * @return Requested heartbeat timeout.
	 */
	public Duration getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	/**
	 * Sets requested heartbeat timeout.
	 *
	 * @param requestedHeartbeat
	 * 		Requested heartbeat timeout
	 */
	public void setRequestedHeartbeat(Duration requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}

	/**
	 * Return number of channels per connection requested by the client. Use 0 for unlimited.
	 *
	 * @return Number of channels per connection requested by the client.
	 */
	public int getRequestedChannelMax() {
		return requestedChannelMax;
	}

	/**
	 * Sets number of channels per connection requested by the client.
	 *
	 * @param requestedChannelMax
	 * 		Number of channels per connection requested by the client.
	 */
	public void setRequestedChannelMax(int requestedChannelMax) {
		this.requestedChannelMax = requestedChannelMax;
	}

	/**
	 * Return whether to enable publisher returns.
	 *
	 * @return Whether to enable publisher returns.
	 */
	public boolean isPublisherReturns() {
		return publisherReturns;
	}

	/**
	 * Sets enable publisher returns.
	 *
	 * @param publisherReturns
	 * 		Whether to enable publisher returns.
	 */
	public void setPublisherReturns(boolean publisherReturns) {
		this.publisherReturns = publisherReturns;
	}

	/**
	 * Return type of publisher confirms to use.
	 *
	 * @return Type of publisher confirms to use.
	 */
	public CachingConnectionFactory.ConfirmType getPublisherConfirmType() {
		return publisherConfirmType;
	}

	/**
	 * Sets type of publisher confirms to use.
	 *
	 * @param publisherConfirmType
	 * 		Type of publisher confirms to use.
	 */
	public void setPublisherConfirmType(
			CachingConnectionFactory.ConfirmType publisherConfirmType) {
		this.publisherConfirmType = publisherConfirmType;
	}

	/**
	 * 返回缓存配置
	 *
	 * @return 缓存配置
	 */
	public Cache getCache() {
		return cache;
	}

	/**
	 * 设置缓存配置
	 *
	 * @param cache
	 * 		缓存配置
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public org.springframework.amqp.rabbit.connection.ConnectionFactory createConnectionFactory() throws Exception {
		Assert.isBlank(getHost(), "Property 'host' is required");

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = createRabbitConnectionFactory();
		final CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);

		propertyMapper.from(host + ":" + this.determinePort()).to(connectionFactory::setAddresses);
		propertyMapper.from(publisherReturns).to(connectionFactory::setPublisherReturns);
		propertyMapper.from(publisherConfirmType).to(connectionFactory::setPublisherConfirmType);

		if(cache != null){
			Cache.Channel channel = cache.getChannel();
			if(channel != null){
				propertyMapper.from(channel::getSize).to(connectionFactory::setChannelCacheSize);
				propertyMapper.from(channel::getCheckoutTimeout).as(Duration::toMillis)
						.to(connectionFactory::setChannelCheckoutTimeout);
			}

			Cache.Connection connection = cache.getConnection();
			if(connection != null){
				propertyMapper.from(connection::getMode).to(connectionFactory::setCacheMode);
				propertyMapper.from(connection::getSize).to(connectionFactory::setConnectionCacheSize);
			}
		}

		return connectionFactory;
	}

	private com.rabbitmq.client.ConnectionFactory createRabbitConnectionFactory() throws Exception {
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
			propertyMapper.from(sslConfiguration::getAlgorithms)
					.as((algorithms)->StringUtils.join(algorithms, ','))
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

	protected int determinePort() {
		if(port > 0){
			return port;
		}

		return sslConfiguration != null && sslConfiguration.isEnabled() ? Constants.DEFAULT_SECURE_PORT :
				Constants.DEFAULT_PORT;
	}

}
