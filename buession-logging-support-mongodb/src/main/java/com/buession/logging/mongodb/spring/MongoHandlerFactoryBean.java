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
package com.buession.logging.mongodb.spring;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.dao.mongodb.core.ReadConcern;
import com.buession.dao.mongodb.core.ReadPreference;
import com.buession.dao.mongodb.core.WriteConcern;
import com.buession.logging.mongodb.core.PoolConfiguration;
import com.buession.logging.mongodb.handler.MongoLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import org.bson.UuidRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * MongoDB 日志处理器 {@link MongoLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class MongoHandlerFactoryBean extends BaseLogHandlerFactoryBean<MongoLogHandler> {

	public final static int DEFAULT_PORT = MongoClientFactory.DEFAULT_PORT;

	public final static String DEFAULT_URL = "mongodb://localhost/test";

	public final static Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(1);

	public final static Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(3);

	public final static UuidRepresentation DEFAULT_UUID_REPRESENTATION = UuidRepresentation.JAVA_LEGACY;

	public final static Class<? extends FieldNamingStrategy> DEFAULT_FIELD_NAMING_STRATEGY =
			SnakeCaseFieldNamingStrategy.class;

	/**
	 * MongoDB 主机地址
	 */
	private String host;

	/**
	 * MongoDB 端口
	 */
	private int port = DEFAULT_PORT;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * Mongo database URI.
	 */
	private String url = DEFAULT_URL;

	/**
	 * 副本集名称
	 */
	private String replicaSetName;

	/**
	 * 数据库名称
	 */
	private String databaseName;

	/**
	 * 认证数据库名称
	 */
	private String authenticationDatabase;

	/**
	 * Collection 名称
	 */
	private String collectionName;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

	/**
	 * 读取超时
	 */
	private Duration readTimeout = DEFAULT_READ_TIMEOUT;

	/**
	 * Representation to use when converting a UUID to a BSON binary value.
	 */
	private UuidRepresentation uuidRepresentation = DEFAULT_UUID_REPRESENTATION;

	/**
	 * Whether to enable auto-index creation.
	 */
	private Boolean autoIndexCreation;

	/**
	 * Fully qualified name of the FieldNamingStrategy to use.
	 */
	private Class<? extends FieldNamingStrategy> fieldNamingStrategy = DEFAULT_FIELD_NAMING_STRATEGY;

	/**
	 * {@link ReadPreference}
	 */
	private ReadPreference readPreference;

	/**
	 * {@link ReadConcern}
	 */
	private ReadConcern readConcern = ReadConcern.AVAILABLE;

	/**
	 * {@link WriteConcern}
	 */
	private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;

	/**
	 * 连接池配置
	 */
	private PoolConfiguration poolConfiguration = new PoolConfiguration();

	private final static Logger logger = LoggerFactory.getLogger(MongoHandlerFactoryBean.class);

	/**
	 * 返回 MongoDB 主机地址
	 *
	 * @return MongoDB 主机地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 MongoDB 主机地址
	 *
	 * @param host
	 * 		MongoDB 主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 MongoDB 端口
	 *
	 * @return MongoDB 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 MongoDB 端口
	 *
	 * @param port
	 * 		MongoDB 端口
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
	 * Return Mongo database URI.
	 *
	 * @return Mongo database URI.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets Mongo database URI.
	 *
	 * @param url
	 * 		Mongo database URI.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回副本集名称
	 *
	 * @return 副本集名称
	 */
	public String getReplicaSetName() {
		return replicaSetName;
	}

	/**
	 * 设置副本集名称
	 *
	 * @param replicaSetName
	 * 		副本集名称
	 */
	public void setReplicaSetName(String replicaSetName) {
		this.replicaSetName = replicaSetName;
	}

	/**
	 * 返回数据库名称
	 *
	 * @return 数据库名称
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置数据库名称
	 *
	 * @param databaseName
	 * 		数据库名称
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 返回认证数据库名称
	 *
	 * @return 认证数据库名称
	 */
	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}

	/**
	 * 设置认证数据库名称
	 *
	 * @param authenticationDatabase
	 * 		认证数据库名称
	 */
	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	/**
	 * 返回 Collection 名称
	 *
	 * @return Collection 名称
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 设置 Collection 名称
	 *
	 * @param collectionName
	 * 		Collection 名称
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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
	 * 返回读取超时
	 *
	 * @return 读取超时
	 */
	public Duration getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 设置读取超时
	 *
	 * @param readTimeout
	 * 		读取超时
	 */
	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * Return representation to use when converting a UUID to a BSON binary value.
	 *
	 * @return Representation to use when converting a UUID to a BSON binary value.
	 */
	public UuidRepresentation getUuidRepresentation() {
		return uuidRepresentation;
	}

	/**
	 * Sets representation to use when converting a UUID to a BSON binary value.
	 *
	 * @param uuidRepresentation
	 * 		Representation to use when converting a UUID to a BSON binary value.
	 */
	public void setUuidRepresentation(UuidRepresentation uuidRepresentation) {
		this.uuidRepresentation = uuidRepresentation;
	}

	/**
	 * Return enable auto-index creation.
	 *
	 * @return true / false
	 */
	public Boolean getAutoIndexCreation() {
		return autoIndexCreation;
	}

	/**
	 * Sets enable auto-index creation.
	 *
	 * @param autoIndexCreation
	 * 		enable auto-index creation.
	 */
	public void setAutoIndexCreation(Boolean autoIndexCreation) {
		this.autoIndexCreation = autoIndexCreation;
	}

	/**
	 * Return fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @return Fully qualified name of the FieldNamingStrategy to use.
	 */
	public Class<? extends FieldNamingStrategy> getFieldNamingStrategy() {
		return fieldNamingStrategy;
	}

	/**
	 * Sets fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @param fieldNamingStrategy
	 * 		Fully qualified name of the FieldNamingStrategy to use.
	 */
	public void setFieldNamingStrategy(
			Class<? extends FieldNamingStrategy> fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	/**
	 * 返回 {@link ReadPreference}
	 *
	 * @return {@link ReadPreference}
	 */
	public ReadPreference getReadPreference() {
		return readPreference;
	}

	/**
	 * 设置 {@link ReadPreference}
	 *
	 * @param readPreference
	 *        {@link ReadPreference}
	 */
	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	/**
	 * 返回 {@link ReadConcern}
	 *
	 * @return {@link ReadConcern}
	 */
	public ReadConcern getReadConcern() {
		return readConcern;
	}

	/**
	 * 设置 {@link ReadConcern}
	 *
	 * @param readConcern
	 *        {@link ReadConcern}
	 */
	public void setReadConcern(ReadConcern readConcern) {
		this.readConcern = readConcern;
	}

	/**
	 * 返回 {@link WriteConcern}
	 *
	 * @return {@link WriteConcern}
	 */
	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	/**
	 * 设置 {@link WriteConcern}
	 *
	 * @param writeConcern
	 *        {@link WriteConcern}
	 */
	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}

	/**
	 * 返回连接池配置
	 *
	 * @return 连接池配置
	 */
	public PoolConfiguration getPoolConfiguration() {
		return poolConfiguration;
	}

	/**
	 * 设置连接池配置
	 *
	 * @param poolConfiguration
	 * 		连接池配置
	 */
	public void setPoolConfiguration(PoolConfiguration poolConfiguration) {
		this.poolConfiguration = poolConfiguration;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(Validate.isBlank(username) && Validate.isBlank(url),
				"Username or url cloud not be empty, blank or null.");

		if(Validate.isBlank(databaseName) && Validate.hasText(url)){
			databaseName = new ConnectionString(url).getDatabase();
		}

		final MongoClientSettings.Builder mongoClientSettingsBuilder = mongoClientSettingsBuilder();
		final MongoClientFactory mongoClientFactory = new MongoClientFactory(host, port, username, password == null ?
				null : password.toCharArray(), url, replicaSetName, databaseName, authenticationDatabase,
				uuidRepresentation, mongoClientSettingsBuilder.build(), MongoClients::create);
		final MongoDatabaseFactory mongoDatabaseFactory = new MongoDatabaseFactory(mongoClientFactory, databaseName);
		final MongoTemplateFactory mongoTemplateFactory = new MongoTemplateFactory(mongoDatabaseFactory,
				autoIndexCreation, fieldNamingStrategy);
		final MongoTemplate mongoTemplate = mongoTemplateFactory.createMongoTemplate();

		createCollection(mongoTemplate);

		logHandler = new MongoLogHandler(mongoTemplate, collectionName);
	}

	private MongoClientSettings.Builder mongoClientSettingsBuilder() {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final MongoClientSettings.Builder builder = MongoClientSettings.builder();

		propertyMapper.from(readPreference).as(ReadPreference::getValue).to(builder::readPreference);
		propertyMapper.from(readConcern).as(ReadConcern::getValue).to(builder::readConcern);
		propertyMapper.from(writeConcern).as(WriteConcern::getValue).to(builder::writeConcern);

		builder.applyToSocketSettings(($builder)->{
			final SocketSettings.Builder socketBuilder = SocketSettings.builder();

			if(connectionTimeout != null){
				socketBuilder.connectTimeout((int) connectionTimeout.toMillis(), TimeUnit.MILLISECONDS);
			}
			if(readTimeout != null){
				socketBuilder.readTimeout((int) readTimeout.toMillis(), TimeUnit.MILLISECONDS);
			}

			$builder.applySettings(socketBuilder.build());
		}).applyToConnectionPoolSettings(($builder)->{
			if(poolConfiguration != null){
				final ConnectionPoolSettings.Builder poolBuilder = ConnectionPoolSettings.builder();

				if(poolConfiguration.getMinSize() > 0){
					poolBuilder.minSize(poolConfiguration.getMinSize());
				}
				if(poolConfiguration.getMaxSize() > 0){
					poolBuilder.minSize(poolConfiguration.getMaxSize());
				}
				if(poolConfiguration.getMaxWaitTime() != null){
					poolBuilder.maxWaitTime(poolConfiguration.getMaxWaitTime().toMillis(), TimeUnit.MILLISECONDS);
				}
				if(poolConfiguration.getMaxConnectionLifeTime() != null){
					poolBuilder.maxConnectionLifeTime(poolConfiguration.getMaxConnectionLifeTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(poolConfiguration.getMaxConnectionIdleTime() != null){
					poolBuilder.maxConnectionIdleTime(poolConfiguration.getMaxConnectionIdleTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(poolConfiguration.getMaintenanceInitialDelay() != null){
					poolBuilder.maintenanceInitialDelay(poolConfiguration.getMaintenanceInitialDelay().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(poolConfiguration.getMaintenanceFrequency() != null){
					poolBuilder.maintenanceFrequency(poolConfiguration.getMaintenanceFrequency().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(poolConfiguration.getMaxConnecting() > 0){
					poolBuilder.maxConnecting(poolConfiguration.getMaxConnecting());
				}

				$builder.applySettings(poolBuilder.build());
			}
		});

		return builder;
	}

	private void createCollection(final MongoTemplate mongoTemplate) {
		if(mongoTemplate.collectionExists(collectionName) == false){
			logger.trace("Creating database collection: [{}]", collectionName);
			mongoTemplate.createCollection(collectionName);
		}
	}

}
