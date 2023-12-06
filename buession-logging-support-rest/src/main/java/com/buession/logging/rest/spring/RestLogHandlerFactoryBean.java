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
package com.buession.logging.rest.spring;

import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpClient;;
import com.buession.logging.rest.core.JsonRequestBodyBuilder;
import com.buession.logging.rest.core.RequestBodyBuilder;
import com.buession.logging.rest.core.RequestMethod;
import com.buession.logging.rest.handler.RestLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rest 日志处理器 {@link RestLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RestLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<RestLogHandler> {

	private static final Map<String, String> NATIVE_HTTPCLIENT_TYPES = new LinkedHashMap<>();

	private ClassLoader classLoader;

	private Class<? extends HttpClient> httpClientType;

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

	static {
		NATIVE_HTTPCLIENT_TYPES.put("org.apache.http.client.HttpClient", "com.buession.httpclient.ApacheHttpClient");
		NATIVE_HTTPCLIENT_TYPES.put("okhttp3.OkHttpClient", "com.buession.httpclient.OkHttpHttpClient");
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
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

		if(logHandler == null){
			final Class<? extends HttpClient> httpClientType = findHttpClientType();
			final HttpClient httpClient = BeanUtils.instantiateClass(httpClientType);

			logHandler = new RestLogHandler(httpClient, getUrl(), getRequestMethod());

			if(getRequestBodyBuilder() != null){
				logHandler.setRequestBodyBuilder(getRequestBodyBuilder());
			}
		}
	}

	@SuppressWarnings({"unchecked"})
	private Class<? extends HttpClient> findHttpClientType() {
		if(this.httpClientType != null){
			return this.httpClientType;
		}

		for(Map.Entry<String, String> e : NATIVE_HTTPCLIENT_TYPES.entrySet()){
			try{
				ClassUtils.forName(e.getKey(), classLoader);
				this.httpClientType = (Class<? extends HttpClient>) ClassUtils.forName(e.getValue(), classLoader);
				return this.httpClientType;
			}catch(Exception ex){
				// Swallow and continue
			}
		}

		throw new IllegalStateException("No supported HttpClient type found");
	}

}
