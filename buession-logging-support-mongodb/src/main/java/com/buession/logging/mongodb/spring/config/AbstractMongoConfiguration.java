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
package com.buession.logging.mongodb.spring.config;

import com.buession.core.validator.Validate;
import com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
public abstract class AbstractMongoLogHandlerConfiguration {

	@Bean
	public MongoClientFactoryBean mongoClient() {
		final MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();

		if(Validate.hasText(properties.getUrl())){
			propertyMapper.from(properties::getUrl).as(ConnectionString::new)
					.to(mongoClientFactoryBean::setConnectionString);
		}else{
			mongoClientFactoryBean.setHost(properties.getHost());
			propertyMapper.from(properties::getPort).to(mongoClientFactoryBean::setPort);
		}
		propertyMapper.from(properties::getReplicaSetName).to(mongoClientFactoryBean::setReplicaSet);

		if(properties.getUsername() != null && properties.getPassword() != null){
			final String database =
					properties.getAuthenticationDatabase() ==
							null ? properties.getDatabaseName() : properties.getAuthenticationDatabase();
			final MongoCredential credential = MongoCredential.createCredential(properties.getUsername(),
					database, properties.getPassword().toCharArray());

			mongoClientFactoryBean.setCredential(new MongoCredential[]{credential});
		}

		final MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();

		propertyMapper.from(properties::getReadPreference).as(ReadPreference::getValue)
				.to(mongoClientSettingsBuilder::readPreference);
		propertyMapper.from(properties::getReadConcern).as(
				ReadConcern::getValue).to(mongoClientSettingsBuilder::readConcern);
		propertyMapper.from(properties::getWriteConcern).as(
				WriteConcern::getValue).to(mongoClientSettingsBuilder::writeConcern);
		propertyMapper.from(properties::getUuidRepresentation)
				.to(mongoClientSettingsBuilder::uuidRepresentation);

		mongoClientSettingsBuilder.applyToSocketSettings(($builder)->{
			final SocketSettings.Builder socketBuilder = SocketSettings.builder();

			if(properties.getConnectionTimeout() != null){
				socketBuilder.connectTimeout((int) properties.getConnectionTimeout().toMillis(),
						TimeUnit.MILLISECONDS);
			}
			if(properties.getReadTimeout() != null){
				socketBuilder.readTimeout((int) properties.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS);
			}

			$builder.applySettings(socketBuilder.build());
		}).applyToConnectionPoolSettings(($builder)->{
			if(properties.getPool() != null){
				final ConnectionPoolSettings.Builder poolBuilder = ConnectionPoolSettings.builder();

				propertyMapper.from(properties.getPool()::getMinSize).to(poolBuilder::minSize);
				propertyMapper.from(properties.getPool()::getMaxSize).to(poolBuilder::maxSize);

				if(properties.getPool().getMaxWaitTime() != null){
					poolBuilder.maxWaitTime(properties.getPool().getMaxWaitTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(properties.getPool().getMaxConnectionLifeTime() != null){
					poolBuilder.maxConnectionLifeTime(properties.getPool().getMaxConnectionLifeTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(properties.getPool().getMaxConnectionIdleTime() != null){
					poolBuilder.maxConnectionIdleTime(properties.getPool().getMaxConnectionIdleTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(properties.getPool().getMaintenanceInitialDelay() != null){
					poolBuilder.maintenanceInitialDelay(
							properties.getPool().getMaintenanceInitialDelay().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(properties.getPool().getMaintenanceFrequency() != null){
					poolBuilder.maintenanceFrequency(properties.getPool().getMaintenanceFrequency().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(properties.getPool().getMaxConnecting() > 0){
					poolBuilder.maxConnecting(properties.getPool().getMaxConnecting());
				}

				$builder.applySettings(poolBuilder.build());
			}
		});

		return mongoClientFactoryBean;
	}

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory(ObjectProvider<MongoClient> mongoClient) {
		String databaseName;

		if(Validate.hasText(properties.getDatabaseName())){
			databaseName = properties.getDatabaseName();
		}else{
			databaseName = new ConnectionString(properties.getUrl()).getDatabase();
		}

		return new SimpleMongoClientDatabaseFactory(mongoClient.getIfAvailable(), databaseName);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Collections.emptyList());
	}

	@Bean
	public MongoMappingContext mongoMappingContext(ObjectProvider<MongoCustomConversions> mongoCustomConversion) {
		final MongoMappingContext mappingContext = new MongoMappingContext();

		propertyMapper.from(properties::getAutoIndexCreation).to(mappingContext::setAutoIndexCreation);
		propertyMapper.from(properties::getFieldNamingStrategy).as(BeanUtils::instantiateClass
																  ).to(mappingContext::setFieldNamingStrategy);
		mappingContext.setSimpleTypeHolder(mongoCustomConversion.getIfAvailable().getSimpleTypeHolder());

		return mappingContext;

	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(ObjectProvider<MongoDatabaseFactory> mongoDatabaseFactory,
													   ObjectProvider<MongoMappingContext> mongoMappingContext,
													   ObjectProvider<MongoCustomConversions> mongoCustomConversion) {
		final DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory.getIfAvailable());
		final MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
				mongoMappingContext.getIfAvailable());

		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null, mongoMappingContext.getIfAvailable()));
		mappingConverter.setCustomConversions(mongoCustomConversion.getIfAvailable());
		return mappingConverter;
	}

	@Bean
	public MongoTemplate mongoTemplate(ObjectProvider<MongoDatabaseFactory> mongoDatabaseFactory,
									   ObjectProvider<MappingMongoConverter> mappingMongoConverter) {
		return new MongoTemplate(mongoDatabaseFactory.getIfAvailable(), mappingMongoConverter.getIfAvailable());
	}

	@Bean
	public MongoLogHandlerFactoryBean logHandlerFactoryBean(ObjectProvider<MongoTemplate> mongoTemplate) {
		final MongoLogHandlerFactoryBean logHandlerFactoryBean = new MongoLogHandlerFactoryBean();

		mongoTemplate.ifAvailable(logHandlerFactoryBean::setMongoTemplate);

		logHandlerFactoryBean.setCollectionName(getCollectionName());

		return logHandlerFactoryBean;
	}

	protected abstract String getCollectionName();

}
