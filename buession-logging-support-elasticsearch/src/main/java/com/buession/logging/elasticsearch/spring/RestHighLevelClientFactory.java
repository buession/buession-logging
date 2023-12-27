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

import com.buession.core.builder.ListBuilder;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.net.URI;
import java.time.Duration;
import java.util.List;

/**
 * {@link RestHighLevelClient} 工厂
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RestHighLevelClientFactory {

	public final static List<String> DEFAULT_URLS = ListBuilder.of("http://localhost:9200");

	public final static int DEFAULT_PORT = 9200;

	public final static Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(1);

	public final static Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(30);

	/**
	 * Elasticsearch URL 地址
	 */
	private List<String> urls = DEFAULT_URLS;

	/**
	 * Elasticsearch 地址
	 */
	private String host;

	/**
	 * Elasticsearch 端口
	 */
	private int port;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

	/**
	 * 读取超时
	 */
	private Duration readTimeout = DEFAULT_READ_TIMEOUT;

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
	 * 返回 Elasticsearch 地址
	 *
	 * @return Elasticsearch 地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 Elasticsearch 地址
	 *
	 * @param host
	 * 		Elasticsearch 地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 Elasticsearch 端口
	 *
	 * @return Elasticsearch 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 Elasticsearch 端口
	 *
	 * @param port
	 * 		Elasticsearch 端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 返回 Elasticsearch 用户名
	 *
	 * @return Elasticsearch 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置 Elasticsearch 用户名
	 *
	 * @param username
	 * 		Elasticsearch 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回 Elasticsearch 密码
	 *
	 * @return Elasticsearch 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置 Elasticsearch 密码
	 *
	 * @param password
	 * 		Elasticsearch 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectionTimeout
	 * 		连接超时
	 */
	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 返回读取超时
	 *
	 * @return 读取超时
	 */
	public Duration getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 设置读取超时
	 *
	 * @param readTimeout
	 * 		读取超时
	 */
	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}

	protected RestHighLevelClient createRestHighLevelClient() {
		Assert.isTrue(Validate.isBlank(getHost()) && Validate.isEmpty(getUrls()),
				"Property 'host' or 'urls' is required");

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final RestClientBuilder builder = RestClient.builder(createHttpHost());

		builder.setHttpClientConfigCallback((httpClientBuilder)->{
					httpClientBuilder.setDefaultCredentialsProvider(
							new ElasticSearchBasicCredentialsProvider(username, password, urls));

					return httpClientBuilder;
				})
				.setRequestConfigCallback((requestConfigBuilder)->{
					propertyMapper.from(this::getConnectionTimeout).whenNonNull().asInt(Duration::toMillis)
							.to(requestConfigBuilder::setConnectTimeout);
					propertyMapper.from(this::getReadTimeout).whenNonNull().asInt(Duration::toMillis)
							.to(requestConfigBuilder::setSocketTimeout);
					return requestConfigBuilder;
				});

		return new RestHighLevelClient(builder);
	}

	protected HttpHost[] createHttpHost() {
		if(Validate.hasText(host)){
			return new HttpHost[]{new HttpHost(host, port > 0 ? port : DEFAULT_PORT)};
		}else{
			return urls.stream().map(RestHighLevelClientFactory::createHttpHost).toArray(HttpHost[]::new);
		}
	}

	private static HttpHost createHttpHost(String uri) {
		try{
			return HttpHost.create(URI.create(uri).toString());
		}catch(IllegalArgumentException e){
			return HttpHost.create(uri);
		}
	}

	private static class ElasticSearchBasicCredentialsProvider extends BasicCredentialsProvider {

		ElasticSearchBasicCredentialsProvider(final String username, final String password, final List<String> urls) {
			if(Validate.hasText(username) && Validate.hasText(password)){
				Credentials credentials = new UsernamePasswordCredentials(username, password);
				setCredentials(AuthScope.ANY, credentials);
			}

			if(Validate.isNotEmpty(urls)){
				urls.stream().map(this::toUri).filter(this::hasUserInfo).forEach(this::addUserInfoCredentials);
			}
		}

		private URI toUri(final String uri) {
			try{
				return URI.create(uri);
			}catch(IllegalArgumentException ex){
				return null;
			}
		}

		private boolean hasUserInfo(final URI uri) {
			return uri != null && Validate.hasText(uri.getUserInfo());
		}

		private void addUserInfoCredentials(final URI uri) {
			final AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
			final Credentials credentials = createUserInfoCredentials(uri.getUserInfo());

			setCredentials(authScope, credentials);
		}

		private Credentials createUserInfoCredentials(final String userInfo) {
			int delimiter = userInfo.indexOf(":");
			if(delimiter == -1){
				return new UsernamePasswordCredentials(userInfo, null);
			}

			String username = userInfo.substring(0, delimiter);
			String password = userInfo.substring(delimiter + 1);
			return new UsernamePasswordCredentials(username, password);
		}

	}

}
