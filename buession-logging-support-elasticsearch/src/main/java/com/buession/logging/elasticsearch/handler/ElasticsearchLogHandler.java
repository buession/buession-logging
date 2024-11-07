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
package com.buession.logging.elasticsearch.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

/**
 * Elasticsearch 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ElasticsearchLogHandler extends AbstractLogHandler {

	/**
	 * {@link ElasticsearchTemplate}
	 *
	 * @since 1.0.0
	 */
	private final ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	private boolean autoCreateIndex = true;

	private final IndexOperations indexOperations;

	private final IndexCoordinates indexCoordinates;

	private boolean initialized = false;

	/**
	 * 构造函数
	 *
	 * @param elasticsearchTemplate
	 *        {@link ElasticsearchTemplate}
	 * @param indexName
	 * 		索引名称
	 */
	public ElasticsearchLogHandler(final ElasticsearchTemplate elasticsearchTemplate, final String indexName) {
		Assert.isNull(elasticsearchTemplate, "ElasticsearchTemplate cloud not be null.");
		Assert.isNull(indexName, "Index name cloud not be blank, empty or null.");

		this.elasticsearchTemplate = elasticsearchTemplate;
		this.indexCoordinates = IndexCoordinates.of(indexName);
		this.indexOperations = elasticsearchTemplate.indexOps(this.indexCoordinates);
	}

	/**
	 * 返回是否自动创建索引
	 *
	 * @return 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	public boolean isAutoCreateIndex() {
		return autoCreateIndex;
	}

	/**
	 * 设置是否自动创建索引
	 *
	 * @param autoCreateIndex
	 * 		是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	public void setAutoCreateIndex(boolean autoCreateIndex) {
		this.autoCreateIndex = autoCreateIndex;
	}

	@Override
	protected Status doHandle(final LogData logData) throws Exception {
		if(initialized == false){
			synchronized(this){
				if(initialized == false){
					if(autoCreateIndex){
						createIndex();
					}
					initialized = true;
				}
			}
		}
		elasticsearchTemplate.save(logData, indexCoordinates);
		return Status.SUCCESS;
	}

	protected void createIndex() {
		if(indexOperations.exists() == false){
			indexOperations.create();
		}
	}

}
