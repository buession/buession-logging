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
package com.buession.logging.springboot.autoconfigure.mongo;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.mongodb.spring.config.AbstractMongoConfiguration;
import com.buession.logging.mongodb.spring.config.MongoConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.MongoProperties;
import com.mongodb.client.MongoClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "mongo.enabled", havingValue = "true")
public class MongoConfiguration extends AbstractMongoConfiguration {

	private final MongoProperties mongoProperties;

	public MongoConfiguration(LogProperties logProperties, MongoConfigurer mongoConfigurer) {
		super(mongoConfigurer);
		this.mongoProperties = logProperties.getMongo();
	}

	@Bean(name = "loggingMongoConfigurer")
	public MongoConfigurer mongoConfigurer() {
		final MongoConfigurer configurer = new MongoConfigurer();

		configurer.setHost(mongoProperties.getHost());
		configurer.setPort(mongoProperties.getPort());
		configurer.setUsername(mongoProperties.getUsername());
		configurer.setPassword(mongoProperties.getPassword());
		configurer.setAuthenticationDatabase(mongoProperties.getAuthenticationDatabase());
		configurer.setUrl(mongoProperties.getUrl());

		configurer.setReplicaSetName(mongoProperties.getReplicaSetName());

		configurer.setDatabaseName(mongoProperties.getDatabaseName());

		configurer.setConnectionTimeout(mongoProperties.getConnectionTimeout());
		configurer.setReadTimeout(mongoProperties.getReadTimeout());

		configurer.setUuidRepresentation(mongoProperties.getUuidRepresentation());

		configurer.setReadPreference(mongoProperties.getReadPreference());

		configurer.setReadConcern(mongoProperties.getReadConcern());
		configurer.setWriteConcern(mongoProperties.getWriteConcern());

		configurer.setAutoIndexCreation(mongoProperties.getAutoIndexCreation());

		propertyMapper.from(mongoProperties::getFieldNamingStrategy).as(BeanUtils::instantiateClass)
				.to(configurer::setFieldNamingStrategy);

		if(mongoProperties.getCluster() != null){
			final MongoConfigurer.Cluster cluster = new MongoConfigurer.Cluster();

			propertyMapper.from(mongoProperties.getCluster()::getConnectionMode).to(cluster::setConnectionMode);
			propertyMapper.from(mongoProperties.getCluster()::getClusterType).to(cluster::setClusterType);
			propertyMapper.from(mongoProperties.getCluster()::getServerSelector).as(BeanUtils::instantiateClass)
					.to(cluster::setServerSelector);
			propertyMapper.from(mongoProperties.getCluster()::getServerSelectionTimeout)
					.to(cluster::setServerSelectionTimeout);
			propertyMapper.from(mongoProperties.getCluster()::getLocalThreshold).to(cluster::setLocalThreshold);

			configurer.setCluster(cluster);
		}

		if(mongoProperties.getServer() != null){
			final MongoConfigurer.Server server = new MongoConfigurer.Server();

			propertyMapper.from(mongoProperties.getServer()::getHeartbeatFrequency).to(server::setHeartbeatFrequency);
			propertyMapper.from(mongoProperties.getServer()::getMinHeartbeatFrequency)
					.to(server::setMinHeartbeatFrequency);
			propertyMapper.from(mongoProperties.getServer()::getServerMonitoringMode)
					.to(server::setServerMonitoringMode);
		}

		configurer.setPool(mongoProperties.getPool());

		return configurer;
	}

	@Bean(name = "loggingMongoCustomConversions")
	@ConditionalOnMissingBean(name = "loggingMongoCustomConversions")
	@Override
	public MongoCustomConversions customConversions() {
		return super.customConversions();
	}

	@Bean(name = "loggingMongoMappingContext")
	@ConditionalOnMissingBean(name = "loggingMongoMappingContext")
	@Override
	public MongoMappingContext mongoMappingContext(
			@Qualifier("loggingMongoCustomConversions") MongoCustomConversions customConversions)
			throws ClassNotFoundException {
		return super.mongoMappingContext(customConversions);
	}

	@Bean(name = "loggingMongoDatabaseFactory")
	@ConditionalOnMissingBean(name = "loggingMongoDatabaseFactory")
	public MongoDatabaseFactory mongoDbFactory() {
		return super.mongoDbFactory();
	}

	@Bean(name = "loggingMappingMongoConverter")
	@ConditionalOnMissingBean(name = "loggingMappingMongoConverter")
	public MappingMongoConverter mappingMongoConverter(
			@Qualifier("loggingMongoDatabaseFactory") MongoDatabaseFactory databaseFactory,
			@Qualifier("loggingMongoCustomConversions") MongoCustomConversions customConversions,
			@Qualifier("loggingMongoMappingContext") MongoMappingContext mappingContext) {
		return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
	}

	@Bean(name = "loggingMongoClient")
	@ConditionalOnMissingBean(name = "loggingMongoClient")
	@Override
	public MongoClient mongoClient() {
		return super.mongoClient();
	}

	@Bean(name = "loggingMongoTemplate")
	@ConditionalOnMissingBean(name = "loggingMongoTemplate")
	public MongoTemplate mongoTemplate(@Qualifier("loggingMongoDatabaseFactory") MongoDatabaseFactory databaseFactory,
									   @Qualifier("loggingMappingMongoConverter") MappingMongoConverter converter) {
		return super.mongoTemplate(databaseFactory, converter);
	}

}
