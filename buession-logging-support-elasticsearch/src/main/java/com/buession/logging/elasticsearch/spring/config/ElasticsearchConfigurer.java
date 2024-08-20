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
package com.buession.logging.elasticsearch.spring.config;

import org.elasticsearch.client.RestClient;
import org.springframework.data.mapping.callback.EntityCallbacks;

import java.util.List;
import java.util.Map;

/**
 * Configures {@link RestClient} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class ElasticsearchConfigurer {

	/**
	 * Elasticsearch URL 地址
	 */
	private List<String> urls;

	/**
	 * 路径前缀
	 */
	private String pathPrefix;

	/**
	 * 请求头
	 */
	private Map<String, String> headers;

	/**
	 * 请求参数
	 */
	private Map<String, String> parameters;

	/**
	 * {@link EntityCallbacks}
	 */
	private EntityCallbacks entityCallbacks;

	/**
	 * 返回 Elasticsearch URL 地址
	 *
	 * @return Elasticsearch URL 地址
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * 设置 Elasticsearch URL 地址
	 *
	 * @param urls
	 * 		Elasticsearch URL 地址
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * 返回路径前缀
	 *
	 * @return 路径前缀
	 */
	public String getPathPrefix() {
		return pathPrefix;
	}

	/**
	 * 设置路径前缀
	 *
	 * @param pathPrefix
	 * 		路径前缀
	 */
	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	/**
	 * 返回请求头
	 *
	 * @return 请求头
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * 设置请求头
	 *
	 * @param headers
	 * 		请求头
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * 返回请求参数
	 *
	 * @return 请求参数
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * 设置请求参数
	 *
	 * @param parameters
	 * 		请求参数
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 返回 {@link EntityCallbacks}
	 *
	 * @return {@link EntityCallbacks}
	 */
	public EntityCallbacks getEntityCallbacks() {
		return entityCallbacks;
	}

	/**
	 * 设置  {@link EntityCallbacks}
	 *
	 * @param entityCallbacks
	 *        {@link EntityCallbacks}
	 */
	public void setEntityCallbacks(EntityCallbacks entityCallbacks) {
		this.entityCallbacks = entityCallbacks;
	}

}
