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
package com.buession.logging.springboot.autoconfigure.mongo;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.mongodb.handler.MongoLogHandler;
import com.buession.logging.mongodb.spring.MongoHandlerFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.MongoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({MongoLogHandler.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "mongo.enabled", havingValue = "true")
public class MongoLogHandlerConfiguration extends AbstractLogHandlerConfiguration<MongoProperties,
		MongoHandlerFactoryBean> {

	public MongoLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getMongo());
	}

	@Bean
	@Override
	public MongoHandlerFactoryBean logHandlerFactoryBean() {
		final MongoHandlerFactoryBean logHandlerFactoryBean = new MongoHandlerFactoryBean();

		propertyMapper.from(handlerProperties::getHost).to(logHandlerFactoryBean::setHost);
		propertyMapper.from(handlerProperties::getPort).to(logHandlerFactoryBean::setPort);
		propertyMapper.from(handlerProperties::getUsername).to(logHandlerFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(logHandlerFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getUrl).to(logHandlerFactoryBean::setUrl);
		propertyMapper.from(handlerProperties::getReplicaSetName).to(logHandlerFactoryBean::setReplicaSetName);
		propertyMapper.from(handlerProperties::getDatabaseName).to(logHandlerFactoryBean::setDatabaseName);
		propertyMapper.from(handlerProperties::getAuthenticationDatabase)
				.to(logHandlerFactoryBean::setAuthenticationDatabase);
		propertyMapper.from(handlerProperties::getCollectionName).to(logHandlerFactoryBean::setCollectionName);
		propertyMapper.from(handlerProperties::getUuidRepresentation).to(logHandlerFactoryBean::setUuidRepresentation);
		propertyMapper.from(handlerProperties::getAutoIndexCreation).to(logHandlerFactoryBean::setAutoIndexCreation);
		propertyMapper.from(handlerProperties::getFieldNamingStrategy)
				.to(logHandlerFactoryBean::setFieldNamingStrategy);
		propertyMapper.from(handlerProperties::getClientSettings).to(logHandlerFactoryBean::setClientSettings);

		return logHandlerFactoryBean;
	}

}
