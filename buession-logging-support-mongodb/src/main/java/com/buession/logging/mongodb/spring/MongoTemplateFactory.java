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

import com.buession.core.builder.ListBuilder;
import com.buession.core.utils.Assert;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * {@link MongoTemplate} 工厂
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class MongoTemplateFactory {

	private MongoDatabaseFactory mongoDatabaseFactory;

	private MongoMappingContext mongoMappingContext;

	public MongoDatabaseFactory getMongoDatabaseFactory() {
		return mongoDatabaseFactory;
	}

	public void setMongoDatabaseFactory(MongoDatabaseFactory mongoDatabaseFactory) {
		this.mongoDatabaseFactory = mongoDatabaseFactory;
	}

	public MongoMappingContext getMongoMappingContext() {
		return mongoMappingContext;
	}

	public void setMongoMappingContext(MongoMappingContext mongoMappingContext) {
		this.mongoMappingContext = mongoMappingContext;
	}

	protected MongoTemplate createMongoTemplate() {
		Assert.isNull(getMongoDatabaseFactory(), "Property 'mongoDatabaseFactory' is required");
		return new MongoTemplate(mongoDatabaseFactory, createMongoConverter());
	}

	protected MongoConverter createMongoConverter() {
		final DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
		final DefaultMongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null, mongoMappingContext);
		final MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		final MongoCustomConversions conversions = new MongoCustomConversions(ListBuilder.empty());

		converter.setCustomConversions(conversions);
		converter.setCodecRegistryProvider(mongoDatabaseFactory);
		converter.setTypeMapper(typeMapper);
		converter.afterPropertiesSet();

		return converter;
	}

}
