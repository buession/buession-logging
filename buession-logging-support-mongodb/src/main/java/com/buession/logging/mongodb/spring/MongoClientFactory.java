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
import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.MongoDriverInformation;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ClusterSettings;
import org.bson.UuidRepresentation;

import java.util.Collections;
import java.util.function.BiFunction;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class MongoClientFactory {

	public final static int DEFAULT_PORT = 27017;

	/**
	 * MongoDB 主机地址
	 */
	private final String host;

	/**
	 * MongoDB 端口
	 */
	private final int port;

	/**
	 * 用户名
	 */
	private final String username;

	/**
	 * 密码
	 */
	private final char[] password;

	/**
	 * Mongo database URI.
	 */
	private final String url;

	/**
	 * 副本集名称
	 */
	private final String replicaSetName;

	/**
	 * 数据库名称
	 */
	private final String databaseName;

	/**
	 * 认证数据库名称
	 */
	private final String authenticationDatabase;

	/**
	 * Representation to use when converting a UUID to a BSON binary value.
	 */
	private final UuidRepresentation uuidRepresentation;

	private final MongoClientSettings clientSettings;

	private final BiFunction<MongoClientSettings, MongoDriverInformation, MongoClient> clientCreator;

	MongoClientFactory(final String host, final int port, final String username, final char[] password,
					   final String url, final String replicaSetName, final String databaseName,
					   final String authenticationDatabase, final UuidRepresentation uuidRepresentation,
					   final MongoClientSettings clientSettings,
					   final BiFunction<MongoClientSettings, MongoDriverInformation, MongoClient> clientCreator){
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.url = url;
		this.replicaSetName = replicaSetName;
		this.databaseName = databaseName;
		this.authenticationDatabase = authenticationDatabase;
		this.uuidRepresentation = uuidRepresentation;
		this.clientSettings = clientSettings;
		this.clientCreator = clientCreator;
	}

	public MongoClient createMongoClient(){
		final MongoClientSettings targetSettings = computeClientSettings(clientSettings);
		return clientCreator.apply(targetSettings, driverInformation());
	}

	private MongoClientSettings computeClientSettings(final MongoClientSettings settings){
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final MongoClientSettings.Builder settingsBuilder = settings != null ? MongoClientSettings.builder(settings)
				: MongoClientSettings.builder();

		propertyMapper.from(this.uuidRepresentation).to(settingsBuilder::uuidRepresentation);
		applyHostAndPort(settingsBuilder);

		propertyMapper.from(this.replicaSetName).whenHasText()
				.as((v)->(Block<ClusterSettings.Builder>) builder->builder.requiredReplicaSetName(replicaSetName)
				   ).to(settingsBuilder::applyToClusterSettings);

		return settingsBuilder.build();
	}

	private void applyHostAndPort(final MongoClientSettings.Builder builder){
		if(hasCustomAddress()){
			int port = this.port > 0 ? this.port : DEFAULT_PORT;
			final ServerAddress serverAddress = new ServerAddress(host, port);

			builder.applyToClusterSettings((cluster)->cluster.hosts(Collections.singletonList(serverAddress)));
			applyCredentials(builder);
			return;
		}

		builder.applyConnectionString(new ConnectionString(url));
	}

	private void applyCredentials(final MongoClientSettings.Builder builder){
		if(username != null && password != null){
			final String database = authenticationDatabase == null ? databaseName : authenticationDatabase;
			final MongoCredential credential = MongoCredential.createCredential(username, database, password);

			builder.credential(credential);
		}
	}

	private boolean hasCustomAddress(){
		return host != null;
	}

	private static MongoDriverInformation driverInformation(){
		final MongoDriverInformation.Builder mongoDriverInformationBuilder = MongoDriverInformation.builder();
		return mongoDriverInformationBuilder.driverName("buession-logging").build();
	}

}
