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
package com.buession.logging.springboot.autoconfigure.rest;

import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpClient;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import com.buession.httpclient.conn.OkHttpNioClientConnectionManager;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;
import com.buession.logging.rest.spring.config.AbstractHttpClientConfiguration;
import com.buession.logging.rest.spring.config.HttpClientConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.RestProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Rest 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({RestLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "rest.enabled", havingValue = "true")
public class HttpClientConfiguration extends AbstractHttpClientConfiguration {

	private final RestProperties restProperties;

	public HttpClientConfiguration(LogProperties logProperties) {
		this.restProperties = logProperties.getRest();
	}

	@Bean(name = "loggingHttpClientConfigurer")
	@ConditionalOnMissingBean(name = "loggingHttpClientConfigurer")
	public HttpClientConfigurer httpClientConfigurer() {
		final HttpClientConfigurer configurer = new HttpClientConfigurer();
		final RestProperties.HttpClientProperties httpClientProperties = restProperties.getHttpClient();

		propertyMapper.from(httpClientProperties::getConnectionManagerShared)
				.to(configurer::setConnectionManagerShared);
		propertyMapper.from(httpClientProperties::getRetryOnConnectionFailure)
				.to(configurer::setRetryOnConnectionFailure);
		propertyMapper.from(httpClientProperties::getMaxConnections).to(configurer::setMaxConnections);
		propertyMapper.from(httpClientProperties::getMaxPerRoute).to(configurer::setMaxPerRoute);
		propertyMapper.from(httpClientProperties::getMaxRequests).to(configurer::setMaxRequests);
		propertyMapper.from(httpClientProperties::getIdleConnectionTime).to(configurer::setIdleConnectionTime);
		propertyMapper.from(httpClientProperties::getConnectTimeout).to(configurer::setConnectTimeout);
		propertyMapper.from(httpClientProperties::getConnectionRequestTimeout)
				.to(configurer::setConnectionRequestTimeout);
		propertyMapper.from(httpClientProperties::getConnectionTimeToLive).to(configurer::setConnectionTimeToLive);
		propertyMapper.from(httpClientProperties::getReadTimeout).to(configurer::setReadTimeout);
		propertyMapper.from(httpClientProperties::getWriteTimeout).to(configurer::setWriteTimeout);
		propertyMapper.from(httpClientProperties::getExpectContinueEnabled).to(configurer::setExpectContinueEnabled);
		propertyMapper.from(httpClientProperties::getAllowRedirects).to(configurer::setAllowRedirects);
		propertyMapper.from(httpClientProperties::getRelativeRedirectsAllowed)
				.to(configurer::setRelativeRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getCircularRedirectsAllowed)
				.to(configurer::setCircularRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getMaxRedirects).to(configurer::setMaxRedirects);
		propertyMapper.from(httpClientProperties::getHardCancellationEnabled)
				.to(configurer::setHardCancellationEnabled);
		propertyMapper.from(httpClientProperties::getAuthenticationEnabled).to(configurer::setAuthenticationEnabled);
		propertyMapper.from(httpClientProperties::getTargetPreferredAuthSchemes)
				.to(configurer::setTargetPreferredAuthSchemes);
		propertyMapper.from(httpClientProperties::getProxyPreferredAuthSchemes)
				.to(configurer::setProxyPreferredAuthSchemes);
		propertyMapper.from(httpClientProperties::getContentCompressionEnabled)
				.to(configurer::setContentCompressionEnabled);
		propertyMapper.from(httpClientProperties::getNormalizeUri).to(configurer::setNormalizeUri);
		propertyMapper.from(httpClientProperties::getCookieSpec).to(configurer::setCookieSpec);
		propertyMapper.from(httpClientProperties::getSslConfiguration).to(configurer::setSslConfiguration);
		propertyMapper.from(httpClientProperties::getProxy).to(configurer::setProxy);

		if(httpClientProperties.getApacheClient() != null){
			final HttpClientConfigurer.ApacheClient apacheClient = new HttpClientConfigurer.ApacheClient();

			propertyMapper.from(httpClientProperties.getApacheClient()::getIoReactor).to(apacheClient::setIoReactor);
			propertyMapper.from(httpClientProperties.getApacheClient()::getThreadFactory)
					.as(BeanUtils::instantiateClass).to(apacheClient::setThreadFactory);

			configurer.setApacheClient(apacheClient);
		}

		if(httpClientProperties.getOkHttp() != null){
			final HttpClientConfigurer.OkHttp okHttp = new HttpClientConfigurer.OkHttp();
			configurer.setOkHttp(okHttp);
		}

		return configurer;
	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient",
			"org.apache.http.nio.client.HttpAsyncClient"})
	@ConditionalOnMissingBean(name = "loggingHttpClient")
	static class AsyncApacheHttpClientConfiguration extends AbstractAsyncApacheHttpClientConfiguration {

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient"})
		@Override
		public com.buession.httpclient.apache.ApacheNioClientConnectionManager apache5NioClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.apache5NioClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		@ConditionalOnClass(name = {"org.apache.http.nio.client.HttpAsyncClient"})
		@Override
		public com.buession.httpclient.apache.ApacheNioClientConnectionManager apacheNioClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.apacheNioClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingHttpAsyncClient")
		@ConditionalOnMissingBean(name = "loggingHttpAsyncClient")
		public ApacheHttpAsyncClient httpAsyncClient(
				@Qualifier("loggingConnectionManager") ObjectProvider<com.buession.httpclient.apache.ApacheNioClientConnectionManager> clientConnectionManager) {
			return super.httpAsyncClient(clientConnectionManager);
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient", "org.apache.http.client.HttpClient"})
	@ConditionalOnMissingBean(name = "loggingHttpClient")
	static class ApacheHttpClientConfiguration extends AbstractApacheHttpClientConfiguration {

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient"})
		@Override
		public com.buession.httpclient.apache.ApacheClientConnectionManager apache5ClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.apache5ClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
		@Override
		public com.buession.httpclient.apache.ApacheClientConnectionManager apacheClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.apacheClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingHttpClient")
		@ConditionalOnMissingBean(name = "loggingHttpClient")
		@Override
		public com.buession.httpclient.ApacheHttpClient httpClient(
				@Qualifier("loggingConnectionManager") ObjectProvider<com.buession.httpclient.apache.ApacheClientConnectionManager> clientConnectionManager) {
			return super.httpClient(clientConnectionManager);
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
	@ConditionalOnMissingBean(name = "loggingHttpClient")
	static class AsyncOkHttpClientConfiguration extends AbstractAsyncOkHttpClientConfiguration {

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		public OkHttpNioClientConnectionManager okHttpNioClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.okHttpNioClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingHttpClient")
		@ConditionalOnMissingBean(name = "loggingHttpClient")
		public OkHttpHttpAsyncClient httpClient(
				@Qualifier("loggingConnectionManager") ObjectProvider<OkHttpNioClientConnectionManager> clientConnectionManager) {
			return super.httpClient(clientConnectionManager);
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
	@ConditionalOnMissingBean(name = "loggingHttpClient")
	static class OkHttpClientConfiguration extends AbstractOkHttpClientConfiguration {

		@Bean(name = "loggingConnectionManager")
		@ConditionalOnMissingBean(name = "loggingConnectionManager")
		public OkHttpClientConnectionManager okHttpClientConnectionManager(
				@Qualifier("loggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
			return super.okHttpClientConnectionManager(httpClientConfigurer);
		}

		@Bean(name = "loggingHttpClient")
		@ConditionalOnMissingBean(name = "loggingHttpClient")
		public OkHttpHttpClient httpClient(
				@Qualifier("loggingConnectionManager") ObjectProvider<OkHttpClientConnectionManager> clientConnectionManager) {
			return super.httpClient(clientConnectionManager);
		}

	}

}
