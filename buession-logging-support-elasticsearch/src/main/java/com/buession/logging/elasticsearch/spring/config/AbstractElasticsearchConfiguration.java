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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.elasticsearch.spring.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.DefaultTransportOptions;
import co.elastic.clients.transport.ElasticsearchTransportConfig;
import co.elastic.clients.transport.TransportOptions;
import co.elastic.clients.transport.http.HeaderMap;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.logging.elasticsearch.ElasticsearchTransportConfigBuilderCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.net.URI;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public abstract class AbstractElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	public ElasticsearchClient elasticsearchClient(ElasticsearchConfigurer configurer,
	                                               ObjectProvider<ElasticsearchTransportConfigBuilderCustomizer> transportConfigBuilderCustomizer) {
		Assert.isEmpty(configurer.getUrls(), "Property 'urls' is required");
		final ElasticsearchTransportConfig.Builder transportConfigBuilder = new ElasticsearchTransportConfig.Builder();
		final HeaderMap headers = configurer.getHeaders() == null ? null : new HeaderMap(configurer.getHeaders());
		final TransportOptions transportOptions = new DefaultTransportOptions(headers, configurer.getParameters(),
				null);
		final List<URI> nodes = configurer.getUrls().stream().map(URI::create).toList();

		transportConfigBuilder.hosts(nodes)
				.usernameAndPassword(configurer.getUsername(), configurer.getPassword())
				.token(configurer.getToken())
				.apiKey(configurer.getApiKey())
				.useCompression(configurer.isUseCompression())
				.jsonMapper(new JacksonJsonpMapper());

		transportConfigBuilderCustomizer.orderedStream()
				.forEach((customizer)->customizer.customize(transportConfigBuilder));

		return new ElasticsearchClient(transportConfigBuilder.build().buildTransport(), transportOptions);
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

}
