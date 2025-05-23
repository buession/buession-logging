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
package com.buession.logging.rest.handler;

import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.core.RequestBody;
import com.buession.httpclient.core.Response;
import com.buession.httpclient.core.concurrent.Callback;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.RequestMethod;
import com.buession.logging.core.handler.AbstractLogHandler;
import com.buession.logging.rest.core.JsonRequestBodyBuilder;
import com.buession.logging.rest.core.RequestBodyBuilder;

/**
 * Rest 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RestLogHandler extends AbstractLogHandler {

	/**
	 * Http 客户端 {@link HttpClient}
	 */
	private final HttpClient httpClient;

	/**
	 * Http 异步客户端 {@link HttpAsyncClient}
	 *
	 * @since 1.0.0
	 */
	private final HttpAsyncClient httpAsyncClient;

	/**
	 * Rest Url
	 */
	private final String url;

	/**
	 * 请求方式 {@link RequestMethod}
	 */
	private RequestMethod requestMethod;

	/**
	 * 请求体构建器
	 */
	private RequestBodyBuilder requestBodyBuilder = new JsonRequestBodyBuilder();

	/**
	 * 构造函数
	 *
	 * @param httpClient
	 * 		Http 客户端 {@link HttpClient}
	 * @param url
	 * 		Rest Url
	 */
	public RestLogHandler(final HttpClient httpClient, final String url) {
		this(httpClient, url, RequestMethod.POST);
	}

	/**
	 * 构造函数
	 *
	 * @param httpAsyncClient
	 * 		Http 异步客户端 {@link HttpAsyncClient}
	 * @param url
	 * 		Rest Url
	 *
	 * @since 1.0.0
	 */
	public RestLogHandler(final HttpAsyncClient httpAsyncClient, final String url) {
		this(httpAsyncClient, url, RequestMethod.POST);
	}

	/**
	 * 构造函数
	 *
	 * @param httpClient
	 * 		Http 客户端 {@link HttpClient}
	 * @param url
	 * 		Rest Url
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 */
	public RestLogHandler(final HttpClient httpClient, final String url, final RequestMethod requestMethod) {
		Assert.isNull(httpClient, "HttpClient is null.");
		Assert.isBlank(url, "Rest url is blank, empty or null.");
		this.httpClient = httpClient;
		this.httpAsyncClient = null;
		this.url = url;
		setRequestMethod(requestMethod);
	}

	/**
	 * 构造函数
	 *
	 * @param httpAsyncClient
	 * 		Http 异步客户端 {@link HttpAsyncClient}
	 * @param url
	 * 		Rest Url
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 *
	 * @since 1.0.0
	 */
	public RestLogHandler(final HttpAsyncClient httpAsyncClient, final String url, final RequestMethod requestMethod) {
		Assert.isNull(httpAsyncClient, "HttpAsyncClient is null.");
		Assert.isBlank(url, "Rest url is blank, empty or null.");
		this.httpClient = null;
		this.httpAsyncClient = httpAsyncClient;
		this.url = url;
		setRequestMethod(requestMethod);
	}

	/**
	 * 设置请求方式 {@link RequestMethod}
	 *
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod == null ? RequestMethod.POST : requestMethod;
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
	protected Status doHandle(final LogData logData) throws Exception {
		final RequestBody<?> requestBody = requestBodyBuilder.build(logData);
		return httpAsyncClient == null ? doSyncHandle(requestBody) : doAsyncHandle(requestBody);
	}

	protected Status doSyncHandle(final RequestBody<?> requestBody) throws Exception {
		Response response;

		if(requestMethod == RequestMethod.PUT){
			response = httpClient.put(url, requestBody);
		}else{
			response = httpClient.post(url, requestBody);
		}

		return response != null && response.isSuccessful() ? Status.SUCCESS : Status.FAILURE;
	}

	protected Status doAsyncHandle(final RequestBody<?> requestBody) throws Exception {
		Callback callback = new Callback() {

			@Override
			public void completed(Response response) {

			}

			@Override
			public void failed(Exception ex) {

			}

			@Override
			public void cancelled() {

			}

		};

		if(requestMethod == RequestMethod.PUT){
			httpAsyncClient.put(url, requestBody, callback);
		}else{
			httpAsyncClient.post(url, requestBody, callback);
		}

		return Status.SUCCESS;
	}

}
