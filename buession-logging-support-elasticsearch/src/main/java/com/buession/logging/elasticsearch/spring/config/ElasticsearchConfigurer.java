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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.elasticsearch.spring.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.data.mapping.callback.EntityCallbacks;

import java.util.List;
import java.util.Map;

/**
 * Configures {@link ElasticsearchClient} with sensible defaults.
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
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * Token
	 */
	private String token;

	/**
	 * API key
	 */
	private String apiKey;

	/**
	 * 请求头
	 */
	private Map<String, String> headers;

	/**
	 * 请求参数
	 */
	private Map<String, String> parameters;

	/**
	 * 是否压缩
	 */
	private boolean useCompression;

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
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回 Token
	 *
	 * @return Token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置 Token
	 *
	 * @param token
	 * 		Token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 返回 API key
	 *
	 * @return API key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * 设置 API key
	 *
	 * @param apiKey
	 * 		API key
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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
	 * 返回是否压缩
	 *
	 * @return 是否压缩
	 */
	public boolean isUseCompression() {
		return getUseCompression();
	}

	/**
	 * 返回是否压缩
	 *
	 * @return 是否压缩
	 */
	public boolean getUseCompression() {
		return useCompression;
	}

	/**
	 * 设置是否压缩
	 *
	 * @param useCompression
	 * 		是否压缩
	 */
	public void setUseCompression(boolean useCompression) {
		this.useCompression = useCompression;
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
