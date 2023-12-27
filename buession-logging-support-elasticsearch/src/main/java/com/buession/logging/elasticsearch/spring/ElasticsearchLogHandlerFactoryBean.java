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
package com.buession.logging.elasticsearch.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.elasticsearch.handler.ElasticsearchLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * Elasticsearch 日志处理器 {@link ElasticsearchLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ElasticsearchLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<ElasticsearchLogHandler> {

	private ElasticsearchRestTemplate restTemplate;

	/**
	 * 索引名称
	 */
	private String indexName;

	public ElasticsearchRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(ElasticsearchRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 返回索引名称
	 *
	 * @return 索引名称
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * 设置索引名称
	 *
	 * @param indexName
	 * 		索引名称
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getRestTemplate(), "Property 'restTemplate' is required");
		Assert.isBlank(getIndexName(), "Property 'indexName' is required");

		if(logHandler == null){
			logHandler = new ElasticsearchLogHandler(getRestTemplate(), getIndexName());
		}
	}

}
