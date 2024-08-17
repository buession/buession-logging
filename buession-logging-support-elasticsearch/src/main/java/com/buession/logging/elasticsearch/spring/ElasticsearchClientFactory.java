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

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.buession.core.builder.ListBuilder;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.data.elasticsearch.client.elc.AutoCloseableElasticsearchClient;

import java.net.URI;
import java.util.List;

/**
 * {@link ElasticsearchClient} 工厂
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class ElasticsearchClientFactory {

	public final static List<String> DEFAULT_URLS = ListBuilder.of("http://localhost:9200");

	/**
	 * Elasticsearch URL 地址
	 */
	private List<String> urls = DEFAULT_URLS;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 请求选项
	 */
	private RequestOptions requestOptions;

	/**
	 * JSONP Mapper {@link JsonpMapper}
	 */
	private JsonpMapper jsonpMapper = new JacksonJsonpMapper();

	/**
	 * 是否自动关闭连接
	 */
	private boolean autoClose;

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
	 * 返回请求选项
	 *
	 * @return 请求选项
	 */
	public RequestOptions getRequestOptions() {
		return requestOptions;
	}

	/**
	 * 设置请求选项
	 *
	 * @param requestOptions
	 * 		请求选项
	 */
	public void setRequestOptions(RequestOptions requestOptions) {
		this.requestOptions = requestOptions;
	}

	/**
	 * 返回 JSONP Mapper {@link JsonpMapper}
	 *
	 * @return JSONP Mapper {@link JsonpMapper}
	 */
	public JsonpMapper getJsonpMapper() {
		return jsonpMapper;
	}

	/**
	 * 设置 JSONP Mapper {@link JsonpMapper}
	 *
	 * @param jsonpMapper
	 * 		JSONP Mapper {@link JsonpMapper}
	 */
	public void setJsonpMapper(JsonpMapper jsonpMapper) {
		this.jsonpMapper = jsonpMapper;
	}

	/**
	 * 返回是否自动关闭连接
	 *
	 * @return true / false
	 */
	public boolean isAutoClose() {
		return autoClose;
	}

	/**
	 * 设置是否自动关闭连接
	 *
	 * @param autoClose
	 * 		是否自动关闭连接
	 */
	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	protected HttpHost[] buildHttpHosts() {
		final HttpHost[] hosts = new HttpHost[getUrls().size()];

		for(int i = 0, l = getUrls().size(); i < l; i++){
			String url = getUrls().get(i);

			try{
				hosts[i] = HttpHost.create(URI.create(url).toString());
			}catch(IllegalArgumentException e){
				hosts[i] = HttpHost.create(url);
			}
		}

		return hosts;
	}

	protected RestClient createRestClient() {
		final RestClientBuilder restClientBuilder = RestClient.builder(buildHttpHosts());

		if(Validate.hasText(getUsername()) && Validate.hasText(getPassword())){
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(getUsername(),
					getPassword()));

			restClientBuilder.setHttpClientConfigCallback(
					(httpClientBuilder)->httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		}

		return restClientBuilder.build();
	}

	protected ElasticsearchTransport createElasticsearchTransport(final RestClientOptions restClientOptions) {
		final RestClient restClient = createRestClient();
		return new RestClientTransport(restClient, getJsonpMapper(), restClientOptions);
	}

	protected ElasticsearchClient createElasticsearchClient() {
		Assert.isEmpty(getUrls(), "Property 'urls' is required");
		final RestClientOptions restClientOptions = getRequestOptions() == null ? null :
				new RestClientOptions(getRequestOptions());
		final ElasticsearchTransport elasticsearchTransport = createElasticsearchTransport(restClientOptions);

		return isAutoClose() ? new AutoCloseableElasticsearchClient(elasticsearchTransport) :
				new ElasticsearchClient(elasticsearchTransport, restClientOptions);
	}

}
