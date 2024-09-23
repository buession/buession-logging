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
package com.buession.logging.rest.spring.config;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpClient;
import com.buession.httpclient.conn.Apache5ClientConnectionManager;
import com.buession.httpclient.conn.Apache5NioClientConnectionManager;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.ApacheNioClientConnectionManager;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import com.buession.httpclient.conn.OkHttpNioClientConnectionManager;
import org.springframework.beans.factory.ObjectProvider;

/**
 * Rest 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class AbstractHttpClientConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	protected abstract static class AbstractApacheHttpClientConfiguration extends AbstractHttpClientConfiguration {

		public com.buession.httpclient.apache.ApacheClientConnectionManager apache5ClientConnectionManager(
				HttpClientConfigurer httpClientConfigurer) {
			return new Apache5ClientConnectionManager(httpClientConfigurer);
		}

		public com.buession.httpclient.apache.ApacheClientConnectionManager apacheClientConnectionManager(
				HttpClientConfigurer httpClientConfigurer) {
			return new ApacheClientConnectionManager(httpClientConfigurer);
		}

		public com.buession.httpclient.ApacheHttpClient httpClient(
				ObjectProvider<com.buession.httpclient.apache.ApacheClientConnectionManager> clientConnectionManager) {
			final com.buession.httpclient.ApacheHttpClient apacheHttpClient = new com.buession.httpclient.ApacheHttpClient();

			clientConnectionManager.ifAvailable(apacheHttpClient::setConnectionManager);

			return apacheHttpClient;
		}

	}

	protected abstract static class AbstractAsyncApacheHttpClientConfiguration extends AbstractHttpClientConfiguration {

		public com.buession.httpclient.apache.ApacheNioClientConnectionManager apache5NioClientConnectionManager(
				HttpClientConfigurer httpClientConfigurer) {
			final Apache5NioClientConnectionManager clientConnectionManager = new Apache5NioClientConnectionManager(
					httpClientConfigurer);
			final HttpClientConfigurer.ApacheClient apacheClient = httpClientConfigurer.getApacheClient();

			if(apacheClient != null){
				propertyMapper.from(apacheClient::getIoReactor).to(clientConnectionManager::setIoReactorConfig);
				propertyMapper.from(apacheClient::getThreadFactory).to(clientConnectionManager::setThreadFactory);
			}

			return clientConnectionManager;
		}

		public com.buession.httpclient.apache.ApacheNioClientConnectionManager apacheNioClientConnectionManager(
				HttpClientConfigurer httpClientConfigurer) {
			final ApacheNioClientConnectionManager clientConnectionManager = new ApacheNioClientConnectionManager(
					httpClientConfigurer);
			final HttpClientConfigurer.ApacheClient apacheClient = httpClientConfigurer.getApacheClient();

			if(apacheClient != null){
				propertyMapper.from(apacheClient::getIoReactor).to(clientConnectionManager::setIoReactorConfig);
				propertyMapper.from(apacheClient::getThreadFactory).to(clientConnectionManager::setThreadFactory);
			}

			return clientConnectionManager;
		}

		public ApacheHttpAsyncClient httpAsyncClient(
				ObjectProvider<com.buession.httpclient.apache.ApacheNioClientConnectionManager> clientConnectionManager) {
			final ApacheHttpAsyncClient apacheHttpAsyncClient = new ApacheHttpAsyncClient();

			clientConnectionManager.ifAvailable(apacheHttpAsyncClient::setConnectionManager);

			return apacheHttpAsyncClient;
		}

	}

	protected abstract static class AbstractOkHttpClientConfiguration extends AbstractHttpClientConfiguration {

		public OkHttpClientConnectionManager okHttpClientConnectionManager(HttpClientConfigurer httpClientConfigurer) {
			return new OkHttpClientConnectionManager(httpClientConfigurer);
		}

		public OkHttpHttpClient httpClient(ObjectProvider<OkHttpClientConnectionManager> clientConnectionManager) {
			final OkHttpHttpClient okHttpClient = new OkHttpHttpClient();

			clientConnectionManager.ifAvailable(okHttpClient::setConnectionManager);

			return okHttpClient;
		}

	}

	protected static abstract class AbstractAsyncOkHttpClientConfiguration extends AbstractHttpClientConfiguration {

		public OkHttpNioClientConnectionManager okHttpNioClientConnectionManager(
				HttpClientConfigurer httpClientConfigurer) {
			return new OkHttpNioClientConnectionManager(httpClientConfigurer);
		}

		public OkHttpHttpAsyncClient httpAsyncClient(
				ObjectProvider<OkHttpNioClientConnectionManager> clientConnectionManager) {
			final OkHttpHttpAsyncClient okHttpHttpAsyncClient = new OkHttpHttpAsyncClient();

			clientConnectionManager.ifAvailable(okHttpHttpAsyncClient::setConnectionManager);

			return okHttpHttpAsyncClient;
		}

	}

}
