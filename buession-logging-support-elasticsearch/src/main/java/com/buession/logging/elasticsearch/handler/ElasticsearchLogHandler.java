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
package com.buession.logging.elasticsearch.handler;

import com.buession.core.id.IdGenerator;
import com.buession.core.id.SnowflakeIdGenerator;
import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.LogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

/**
 * Elasticsearch 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ElasticsearchLogHandler implements LogHandler {

	/**
	 * {@link ElasticsearchRestTemplate}
	 */
	private final ElasticsearchRestTemplate restTemplate;

	private final IndexOperations indexOperations;

	private final IndexCoordinates indexCoordinates;

	/**
	 * ID 生成器
	 */
	private IdGenerator<?> idGenerator = new SnowflakeIdGenerator();

	private final static Logger logger = LoggerFactory.getLogger(ElasticsearchLogHandler.class);

	/**
	 * 构造函数
	 *
	 * @param restTemplate
	 *        {@link ElasticsearchRestTemplate}
	 * @param indexName
	 * 		索引名称
	 */
	public ElasticsearchLogHandler(final ElasticsearchRestTemplate restTemplate, final String indexName){
		Assert.isNull(restTemplate, "ElasticsearchRestTemplate cloud not be null.");
		Assert.isNull(indexName, "Index name cloud not be blank, empty or null.");

		this.restTemplate = restTemplate;
		this.indexCoordinates = IndexCoordinates.of(indexName);
		this.indexOperations = restTemplate.indexOps(this.indexCoordinates);

		createIndex();
	}

	/**
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	public void setIdGenerator(IdGenerator<?> idGenerator){
		Assert.isNull(idGenerator, "IdGenerator cloud not be null.");
		this.idGenerator = idGenerator;
	}

	@Override
	public Status handle(final LogData logData){
		try{
			restTemplate.save(logData, indexCoordinates);
			return Status.SUCCESS;
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

	protected void createIndex(){
		if(indexOperations.exists() == false){
			indexOperations.create();
		}
	}

}
