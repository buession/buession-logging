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
package com.buession.logging.rest.handler;

import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.core.RequestBody;
import com.buession.httpclient.core.Response;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rest.core.JsonRequestBodyBuilder;
import com.buession.logging.rest.core.RequestBodyBuilder;
import com.buession.logging.rest.core.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RestLogHandler implements LogHandler {

	/**
	 * Http 客户端 {@link HttpClient}
	 */
	private final HttpClient httpClient;

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

	private final static Logger logger = LoggerFactory.getLogger(RestLogHandler.class);

	/**
	 * 构造函数
	 *
	 * @param httpClient
	 * 		Http 客户端 {@link HttpClient}
	 * @param url
	 * 		Rest Url
	 */
	public RestLogHandler(final HttpClient httpClient, final String url){
		this(httpClient, url, RequestMethod.POST);
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
	public RestLogHandler(final HttpClient httpClient, final String url, final RequestMethod requestMethod){
		Assert.isNull(httpClient, "HttpClient is null.");
		Assert.isBlank(url, "Rest url is blank, empty or null.");
		this.httpClient = httpClient;
		this.url = url;
		setRequestMethod(requestMethod);
	}

	/**
	 * 设置请求方式 {@link RequestMethod}
	 *
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 */
	public void setRequestMethod(RequestMethod requestMethod){
		this.requestMethod = requestMethod == null ? RequestMethod.POST : requestMethod;
	}

	/**
	 * 设置请求体构建器
	 *
	 * @param requestBodyBuilder
	 * 		请求体构建器
	 */
	public void setRequestBodyBuilder(RequestBodyBuilder requestBodyBuilder){
		this.requestBodyBuilder = requestBodyBuilder;
	}

	@Override
	public Status handle(final LogData logData){
		final RequestBody<?> requestBody = requestBodyBuilder.build(logData);
		Response response;

		try{
			if(requestMethod == RequestMethod.PUT){
				response = httpClient.put(url, requestBody);
			}else{
				response = httpClient.post(url, requestBody);
			}
			return response != null && response.isSuccessful() ? Status.SUCCESS : Status.FAILURE;
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

}
