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

import com.buession.core.validator.Validate;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.net.URI;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class RestHighLevelClientFactory {

	public final static int DEFAULT_PORT = 9200;

	/**
	 * Elasticsearch URL 地址
	 */
	private final List<String> urls;

	/**
	 * Elasticsearch 地址
	 */
	private final String host;

	/**
	 * Elasticsearch 端口
	 */
	private final int port;

	RestHighLevelClientFactory(final List<String> urls, final String host, final int port){
		this.urls = urls;
		this.host = host;
		this.port = port;
	}

	public RestHighLevelClient createRestHighLevelClient(){
		return new RestHighLevelClient(RestClient.builder(createHttpHost()));
	}

	protected HttpHost[] createHttpHost(){
		if(Validate.hasText(host)){
			return new HttpHost[]{new HttpHost(host, port > 0 ? port : DEFAULT_PORT)};
		}else{
			return urls.stream().map(RestHighLevelClientFactory::createHttpHost).toArray(HttpHost[]::new);
		}
	}

	private static HttpHost createHttpHost(String uri){
		try{
			return HttpHost.create(URI.create(uri).toString());
		}catch(IllegalArgumentException e){
			return HttpHost.create(uri);
		}
	}

}
