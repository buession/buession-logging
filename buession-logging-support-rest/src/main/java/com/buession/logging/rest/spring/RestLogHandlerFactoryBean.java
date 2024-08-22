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
package com.buession.logging.rest.spring;

import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;;
import com.buession.logging.core.RequestMethod;
import com.buession.logging.rest.core.JsonRequestBodyBuilder;
import com.buession.logging.rest.core.RequestBodyBuilder;
import com.buession.logging.rest.handler.RestLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;

/**
 * Rest 日志处理器 {@link RestLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RestLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<RestLogHandler> {

	/**
	 * {@link HttpClient}
	 *
	 * @since 1.0.0
	 */
	private HttpClient httpClient;

	/**
	 * {@link HttpAsyncClient}
	 *
	 * @since 1.0.0
	 */
	private HttpAsyncClient httpAsyncClient;

	/**
	 * Rest Url
	 */
	private String url;

	/**
	 * 请求方式 {@link RequestMethod}
	 */
	private RequestMethod requestMethod = RequestMethod.POST;

	/**
	 * 请求体构建器
	 */
	private RequestBodyBuilder requestBodyBuilder = new JsonRequestBodyBuilder();

	/**
	 * 访问 {@link HttpClient}
	 *
	 * @return {@link HttpClient}
	 *
	 * @since 1.0.0
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * 设置 {@link HttpClient}
	 *
	 * @param httpClient
	 *        {@link HttpClient}
	 *
	 * @since 1.0.0
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * 返回 {@link HttpAsyncClient}
	 *
	 * @return {@link HttpAsyncClient}
	 *
	 * @since 1.0.0
	 */
	public HttpAsyncClient getHttpAsyncClient() {
		return httpAsyncClient;
	}

	/**
	 * 设置 {@link HttpAsyncClient}
	 *
	 * @param httpAsyncClient
	 *        {@link HttpAsyncClient}
	 *
	 * @since 1.0.0
	 */
	public void setHttpAsyncClient(HttpAsyncClient httpAsyncClient) {
		this.httpAsyncClient = httpAsyncClient;
	}

	/**
	 * 返回 Rest Url
	 *
	 * @return Rest Url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 Rest Url
	 *
	 * @param url
	 * 		Rest Url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回请求方式 {@link RequestMethod}
	 *
	 * @return 请求方式 {@link RequestMethod}
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求方式 {@link RequestMethod}
	 *
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * 返回请求体构建器
	 *
	 * @return 请求体构建器
	 */
	public RequestBodyBuilder getRequestBodyBuilder() {
		return requestBodyBuilder;
	}

	/**
	 * 设置请求体构建器
	 *
	 * @param requestBodyBuilder
	 * 		请求体构建器
	 */
	public void setRequestBodyBuilder(RequestBodyBuilder requestBodyBuilder) {
		this.requestBodyBuilder = requestBodyBuilder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isBlank(getUrl(), "Property 'url' is required");
		Assert.isTrue(getHttpClient() == null && getHttpAsyncClient() == null,
				"Property 'httpClient' or 'httpAsyncClient' is required");

		if(logHandler == null){
			if(getHttpAsyncClient() != null){
				logHandler = getRequestMethod() == null ? new RestLogHandler(getHttpAsyncClient(), getUrl()) :
						new RestLogHandler(getHttpAsyncClient(), getUrl(), getRequestMethod());
			}else{
				logHandler = getRequestMethod() == null ? new RestLogHandler(getHttpClient(), getUrl()) :
						new RestLogHandler(getHttpClient(), getUrl(), getRequestMethod());
			}

			if(getRequestBodyBuilder() != null){
				logHandler.setRequestBodyBuilder(getRequestBodyBuilder());
			}
		}
	}

}
