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
package com.buession.logging.elasticsearch.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.elasticsearch.handler.ElasticsearchLogHandler;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchLogHandlerFactoryBeanConfigurer;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

/**
 * Elasticsearch 日志处理器 {@link ElasticsearchLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ElasticsearchLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<ElasticsearchLogHandler> {

	/**
	 * {@link ElasticsearchTemplate}
	 */
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 索引名称
	 */
	private String indexName;

	/**
	 * 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	private Boolean autoCreateIndex = true;

	/**
	 * 构造函数
	 */
	public ElasticsearchLogHandlerFactoryBean() {
	}

	/**
	 * 构造函数
	 *
	 * @param configurer
	 *        {@link  ElasticsearchLogHandlerFactoryBeanConfigurer}
	 */
	public ElasticsearchLogHandlerFactoryBean(final ElasticsearchLogHandlerFactoryBeanConfigurer configurer) {
		propertyMapper.from(configurer.getIndexName()).to(this::setIndexName);
		propertyMapper.from(configurer.getAutoCreateIndex()).to(this::setAutoCreateIndex);
	}

	/**
	 * 返回 {@link ElasticsearchTemplate}
	 *
	 * @return {@link ElasticsearchTemplate}
	 */
	public ElasticsearchTemplate getElasticsearchTemplate() {
		return elasticsearchTemplate;
	}

	/**
	 * 设置 {@link ElasticsearchTemplate}
	 *
	 * @param elasticsearchTemplate
	 *        {@link ElasticsearchTemplate}
	 */
	public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
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

	/**
	 * 返回是否自动创建索引
	 *
	 * @return 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	public Boolean isAutoCreateIndex() {
		return getAutoCreateIndex();
	}

	/**
	 * 返回是否自动创建索引
	 *
	 * @return 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	public Boolean getAutoCreateIndex() {
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
	public void setAutoCreateIndex(Boolean autoCreateIndex) {
		this.autoCreateIndex = autoCreateIndex;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getElasticsearchTemplate(), "Property 'elasticsearchTemplate' is required");
		Assert.isBlank(getIndexName(), "Property 'indexName' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = new ElasticsearchLogHandler(getElasticsearchTemplate(), getIndexName());
					if(autoCreateIndex){
						logHandler.setAutoCreateIndex(autoCreateIndex);
					}
				}
			}
		}
	}

}
