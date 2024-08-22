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

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.dao.mongodb.core.ReadConcern;
import com.buession.dao.mongodb.core.ReadPreference;
import com.buession.dao.mongodb.core.WriteConcern;
import com.buession.logging.mongodb.core.PoolConfiguration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.connection.SocketSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
public abstract class AbstractMongoConfiguration extends AbstractMongoClientConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	private final MongoConfigurer mongoConfigurer;

	public AbstractMongoConfiguration(MongoConfigurer mongoConfigurer) {
		this.mongoConfigurer = mongoConfigurer;
	}

	@Bean
	@Override
	public MongoClient mongoClient() {
		return super.mongoClient();
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory,
													   MongoCustomConversions customConversions,
													   MongoMappingContext mappingContext) {
		final MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter(databaseFactory,
				customConversions, mappingContext);

		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null, mappingContext));

		return mappingMongoConverter;
	}

	@Override
	protected void configureClientSettings(MongoClientSettings.Builder builder) {
		if(Validate.hasText(mongoConfigurer.getUrl())){
			builder.applyConnectionString(new ConnectionString(mongoConfigurer.getUrl()));
		}else if(Validate.hasText(mongoConfigurer.getHost())){
			ServerAddress serverAddress = mongoConfigurer.getPort() == null ?
					new ServerAddress(mongoConfigurer.getHost()) : new ServerAddress(mongoConfigurer.getHost(),
					mongoConfigurer.getPort());
			builder.applyToClusterSettings((cluster)->cluster.hosts(Collections.singletonList(serverAddress)));
		}

		if(Validate.isBlank(mongoConfigurer.getUrl()) && Validate.hasText(mongoConfigurer.getUsername())
				&& Validate.hasText(mongoConfigurer.getPassword())){
			String database = Validate.hasText(mongoConfigurer.getAuthenticationDatabase())
					? mongoConfigurer.getAuthenticationDatabase() : mongoConfigurer.getDatabaseName();
			final MongoCredential credential = MongoCredential.createCredential(mongoConfigurer.getUsername(),
					database, mongoConfigurer.getPassword().toCharArray());

			builder.credential(credential);
		}

		if(Validate.hasText(mongoConfigurer.getReplicaSetName())){
			builder.applyToClusterSettings(
					(cluster)->cluster.requiredReplicaSetName(mongoConfigurer.getReplicaSetName()));
		}

		builder.applyToClusterSettings(($builder)->{
			MongoConfigurer.Cluster cluster = mongoConfigurer.getCluster();

			if(cluster != null){
				final ClusterSettings.Builder clusterBuilder = ClusterSettings.builder();

				propertyMapper.from(cluster::getConnectionMode).to(clusterBuilder::mode);
				propertyMapper.from(cluster::getClusterType).to(clusterBuilder::requiredClusterType);
				propertyMapper.from(cluster::getServerSelector).to(clusterBuilder::serverSelector);
				applySettings((value)->clusterBuilder.serverSelectionTimeout(value.toMillis(), TimeUnit.MILLISECONDS),
						cluster.getServerSelectionTimeout());
				applySettings((value)->clusterBuilder.localThreshold(value.toMillis(), TimeUnit.MILLISECONDS),
						cluster.getLocalThreshold());
			}
		}).applyToSocketSettings(($builder)->{
			final SocketSettings.Builder socketBuilder = SocketSettings.builder();

			applySettings((value)->socketBuilder.connectTimeout(value.toMillis(), TimeUnit.MILLISECONDS),
					mongoConfigurer.getConnectionTimeout());
			applySettings((value)->socketBuilder.readTimeout(value.toMillis(), TimeUnit.MILLISECONDS),
					mongoConfigurer.getReadTimeout());

			$builder.applySettings(socketBuilder.build());
		}).applyToServerSettings(($builder)->{
			MongoConfigurer.Server server = mongoConfigurer.getServer();

			if(server != null){
				final ServerSettings.Builder serverBuilder = ServerSettings.builder();

				if(server.getHeartbeatFrequency() != null){
					applySettings((value)->serverBuilder.heartbeatFrequency(value.toMillis(), TimeUnit.MILLISECONDS),
							server.getHeartbeatFrequency());
					applySettings((value)->serverBuilder.minHeartbeatFrequency(value.toMillis(), TimeUnit.MILLISECONDS),
							server.getMinHeartbeatFrequency());
					propertyMapper.from(server::getServerMonitoringMode).to(serverBuilder::serverMonitoringMode);
				}
			}
		}).applyToSslSettings(($builder)->{

		}).applyToConnectionPoolSettings(($builder)->{
			PoolConfiguration poolConfiguration = mongoConfigurer.getPool();

			if(poolConfiguration != null){
				final ConnectionPoolSettings.Builder poolBuilder = ConnectionPoolSettings.builder();

				propertyMapper.from(poolConfiguration::getMinSize).to(poolBuilder::minSize);
				propertyMapper.from(poolConfiguration::getMaxSize).to(poolBuilder::maxSize);

				applySettings((value)->poolBuilder.maxWaitTime(value.toMillis(), TimeUnit.MILLISECONDS),
						poolConfiguration.getMaxWaitTime());
				applySettings((value)->poolBuilder.maxConnectionLifeTime(value.toMillis(), TimeUnit.MILLISECONDS),
						poolConfiguration.getMaxConnectionLifeTime());
				applySettings((value)->poolBuilder.maxConnectionIdleTime(value.toMillis(), TimeUnit.MILLISECONDS),
						poolConfiguration.getMaxConnectionIdleTime());
				applySettings((value)->poolBuilder.maintenanceInitialDelay(value.toMillis(), TimeUnit.MILLISECONDS),
						poolConfiguration.getMaintenanceInitialDelay());
				applySettings((value)->poolBuilder.maintenanceFrequency(value.toMillis(), TimeUnit.MILLISECONDS),
						poolConfiguration.getMaintenanceFrequency());

				propertyMapper.from(poolConfiguration::getMaxConnecting).to(poolBuilder::maxConnecting);

				$builder.applySettings(poolBuilder.build());
			}
		});

		propertyMapper.from(mongoConfigurer::getUuidRepresentation).to(builder::uuidRepresentation);
		propertyMapper.from(mongoConfigurer::getReadPreference).as(ReadPreference::getValue)
				.to(builder::readPreference);
		propertyMapper.from(mongoConfigurer::getReadConcern).as(ReadConcern::getValue).to(builder::readConcern);
		propertyMapper.from(mongoConfigurer::getWriteConcern).as(WriteConcern::getValue).to(builder::writeConcern);
	}

	@Override
	protected String getDatabaseName() {
		if(Validate.hasText(mongoConfigurer.getDatabaseName())){
			return mongoConfigurer.getDatabaseName();
		}else{
			return new ConnectionString(mongoConfigurer.getUrl()).getDatabase();
		}
	}

	@Override
	protected boolean autoIndexCreation() {
		return mongoConfigurer.getAutoIndexCreation();
	}

	@Override
	protected FieldNamingStrategy fieldNamingStrategy() {
		return Optional.ofNullable(mongoConfigurer.getFieldNamingStrategy()).orElse(super.fieldNamingStrategy());
	}

	protected <T> void applySettings(Consumer<T> settingsBuilder, @Nullable T value) {
		if(value != null){
			settingsBuilder.accept(value);
		}
	}

}
