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
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * {@link MongoMappingContext} 工厂
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class MongoMappingContextFactory {

	public final static Class<? extends FieldNamingStrategy> DEFAULT_FIELD_NAMING_STRATEGY =
			SnakeCaseFieldNamingStrategy.class;

	private Boolean autoIndexCreation;

	private FieldNamingStrategy fieldNamingStrategy;

	public Boolean getAutoIndexCreation() {
		return autoIndexCreation;
	}

	public void setAutoIndexCreation(Boolean autoIndexCreation) {
		this.autoIndexCreation = autoIndexCreation;
	}

	public FieldNamingStrategy getFieldNamingStrategy() {
		return fieldNamingStrategy;
	}

	public void setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	protected MongoMappingContext createMongoMappingContext() {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final MongoMappingContext mappingContext = new MongoMappingContext();

		propertyMapper.from(getAutoIndexCreation()).to(mappingContext::setAutoIndexCreation);
		propertyMapper.from(getFieldNamingStrategy()).to(mappingContext::setFieldNamingStrategy);
		//mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());

		return mappingContext;
	}

}
