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
package com.buession.logging.elasticsearch.spring.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.DefaultTransportOptions;
import co.elastic.clients.transport.TransportOptions;
import co.elastic.clients.transport.http.HeaderMap;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.logging.elasticsearch.RestClientBuilderCustomizer;
import com.buession.logging.elasticsearch.TransportOptionsCustomizer;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public abstract class AbstractElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	public abstract RestClientBuilderCustomizer restClientBuilderCustomizer();

	public RestClient restClient(ElasticsearchConfigurer configurer,
								 ObjectProvider<RestClientBuilderCustomizer> restClientBuilderCustomizer) {
		Assert.isEmpty(configurer.getUrls(), "Property 'urls' is required");

		final RestClientBuilder restClientBuilder = createRestClientBuilder(configurer, restClientBuilderCustomizer);
		return restClientBuilder.build();
	}

	public ElasticsearchClient elasticsearchClient(ElasticsearchConfigurer configurer, RestClient restClient,
												   ObjectProvider<TransportOptionsCustomizer> transportOptionsCustomizer) {
		final HeaderMap headers = configurer.getHeaders() == null ? null : new HeaderMap(configurer.getHeaders());
		final TransportOptions transportOptions = new DefaultTransportOptions(headers, configurer.getParameters(),
				null);

		transportOptionsCustomizer.orderedStream().forEach((customizer)->customizer.customize(transportOptions));

		return ElasticsearchClients.createImperative(restClient, transportOptions);
	}

	public ElasticsearchTemplate elasticsearchTemplate(ElasticsearchConfigurer configurer,
													   ElasticsearchClient elasticsearchClient,
													   ObjectProvider<ElasticsearchConverter> elasticsearchConverter) {
		final ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(elasticsearchClient,
				elasticsearchConverter.getIfAvailable());

		propertyMapper.from(configurer::getEntityCallbacks).to(elasticsearchTemplate::setEntityCallbacks);
		elasticsearchTemplate.setRefreshPolicy(refreshPolicy());

		return elasticsearchTemplate;
	}

	private RestClientBuilder createRestClientBuilder(final ElasticsearchConfigurer configurer,
													  final ObjectProvider<RestClientBuilderCustomizer> restClientBuilderCustomizer) {
		final Node[] nodes = configurer.getUrls()
				.stream()
				.map((url)->new Node(this.createHttpHost(url)))
				.toArray(Node[]::new);
		final RestClientBuilder builder = RestClient.builder(nodes);

		builder.setHttpClientConfigCallback((httpClientBuilder)->{
			restClientBuilderCustomizer.orderedStream().forEach((customizer)->customizer.customize(httpClientBuilder));
			return httpClientBuilder;
		});

		builder.setRequestConfigCallback((requestConfigBuilder)->{
			restClientBuilderCustomizer.orderedStream()
					.forEach((customizer)->customizer.customize(requestConfigBuilder));
			return requestConfigBuilder;
		});

		propertyMapper.from(configurer::getPathPrefix).to(builder::setPathPrefix);

		restClientBuilderCustomizer.orderedStream().forEach((customizer)->customizer.customize(builder));

		return builder;
	}

	private HttpHost createHttpHost(final String uri) {
		try{
			return createHttpHost(URI.create(uri));
		}catch(IllegalArgumentException ex){
			return HttpHost.create(uri);
		}
	}

	private HttpHost createHttpHost(final URI uri) {
		if(Validate.isBlank(uri.getUserInfo())){
			return HttpHost.create(uri.toString());
		}

		try{
			return HttpHost.create(new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(),
					uri.getQuery(), uri.getFragment()).toString());
		}catch(URISyntaxException ex){
			throw new IllegalStateException(ex);
		}
	}

}
