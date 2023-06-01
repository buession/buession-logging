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
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.mongodb.core.converter.BusinessTypeConverter;
import com.buession.logging.mongodb.core.converter.EventConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mapping.model.FieldNamingStrategy;
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
 * @author Yong.Teng
 * @since 0.0.1
 */
class MongoTemplateFactory {

	private final com.buession.logging.mongodb.spring.MongoDatabaseFactory mongoDatabaseFactory;

	private final Boolean autoIndexCreation;

	private final Class<? extends FieldNamingStrategy> fieldNamingStrategy;

	private final MongoCustomConversions conversions;

	MongoTemplateFactory(final com.buession.logging.mongodb.spring.MongoDatabaseFactory mongoDatabaseFactory,
						 final Boolean autoIndexCreation,
						 final Class<? extends FieldNamingStrategy> fieldNamingStrategy) {
		this.mongoDatabaseFactory = mongoDatabaseFactory;
		this.autoIndexCreation = autoIndexCreation;
		this.fieldNamingStrategy = fieldNamingStrategy;
		conversions =
				new MongoCustomConversions(
						ListBuilder.create().add(new BusinessTypeConverter()).add(new EventConverter()).build());
	}

	public MongoTemplate createMongoTemplate() {
		final MongoDatabaseFactory mongoDatabaseFactory = this.mongoDatabaseFactory.createMongoDatabaseFactory();
		final MongoConverter mongoConverter = createMongoConverter(mongoDatabaseFactory);

		return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
	}

	protected MongoMappingContext createMongoMappingContext() {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final MongoMappingContext context = new MongoMappingContext();

		propertyMapper.from(autoIndexCreation).to(context::setAutoIndexCreation);
		propertyMapper.from(fieldNamingStrategy).as(BeanUtils::instantiateClass).to(context::setFieldNamingStrategy);

		context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
		context.afterPropertiesSet();

		return context;
	}

	private MongoConverter createMongoConverter(final MongoDatabaseFactory factory) {
		final MongoMappingContext mappingContext = createMongoMappingContext();
		final DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		final DefaultMongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null, mappingContext);
		final MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);

		converter.setCustomConversions(conversions);
		converter.setCodecRegistryProvider(factory);
		converter.setTypeMapper(typeMapper);
		converter.afterPropertiesSet();

		return converter;
	}

}
