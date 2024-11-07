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

import com.buession.core.utils.Assert;
import com.buession.logging.mongodb.handler.MongoLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDB 日志处理器 {@link MongoLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.3
 */
public class MongoLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<MongoLogHandler> {

	/**
	 * {@link MongoTemplate}
	 */
	private MongoTemplate mongoTemplate;

	/**
	 * Collection 名称
	 */
	private String collectionName;

	private final static Logger logger = LoggerFactory.getLogger(MongoLogHandlerFactoryBean.class);

	/**
	 * 返回 {@link MongoTemplate}
	 *
	 * @return {@link MongoTemplate}
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * 设置 {@link MongoTemplate}
	 *
	 * @param mongoTemplate
	 *        {@link MongoTemplate}
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
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

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getMongoTemplate(), "Property 'mongoTemplate' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					createCollection();

					logHandler = new MongoLogHandler(getMongoTemplate(), getCollectionName());
				}
			}
		}
	}

	private void createCollection() {
		if(mongoTemplate.collectionExists(getCollectionName()) == false){
			logger.trace("Creating database collection: [{}]", getCollectionName());
			mongoTemplate.createCollection(getCollectionName());
		}
	}

}
