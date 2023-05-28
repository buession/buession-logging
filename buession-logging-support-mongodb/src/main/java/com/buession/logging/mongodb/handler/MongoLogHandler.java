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
package com.buession.logging.mongodb.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.LogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDb 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class MongoLogHandler implements LogHandler {

	/**
	 * {@link MongoTemplate}
	 */
	private final MongoTemplate mongoTemplate;

	/**
	 * Collection 名称
	 */
	private final String collectionName;

	private final static Logger logger = LoggerFactory.getLogger(MongoLogHandler.class);

	/**
	 * 构造函数
	 *
	 * @param mongoTemplate
	 *        {@link MongoTemplate}
	 * @param collectionName
	 * 		Collection 名称
	 */
	public MongoLogHandler(final MongoTemplate mongoTemplate, final String collectionName){
		Assert.isNull(mongoTemplate, "MongoTemplate is null.");
		Assert.isBlank(collectionName, "Collection name is blank, empty or null.");
		this.mongoTemplate = mongoTemplate;
		this.collectionName = collectionName;
	}

	@Override
	public Status handle(final LogData logData){
		try{
			mongoTemplate.save(logData, collectionName);
			return Status.SUCCESS;
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

}
