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
package com.buession.logging.springboot.autoconfigure.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.ElasticsearchCredentialsProvider;
import com.buession.logging.elasticsearch.RestClientBuilderCustomizer;
import com.buession.logging.elasticsearch.TransportOptionsCustomizer;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.ElasticsearchProperties;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.net.URI;
import java.time.Duration;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({ElasticsearchLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = ElasticsearchProperties.PREFIX, name = "enabled", havingValue = "true")
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

	private final ElasticsearchProperties elasticsearchProperties;

	public ElasticsearchConfiguration(LogProperties logProperties) {
		this.elasticsearchProperties = logProperties.getElasticsearch();
	}

	@Bean(name = "loggingElasticsearchConfigurer")
	@ConditionalOnMissingBean(name = "loggingElasticsearchConfigurer")
	public ElasticsearchConfigurer elasticsearchConfigurer() {
		final ElasticsearchConfigurer configurer = new ElasticsearchConfigurer();

		configurer.setUrls(elasticsearchProperties.getUrls());
		configurer.setPathPrefix(elasticsearchProperties.getPathPrefix());
		configurer.setHeaders(elasticsearchProperties.getHeaders());
		configurer.setParameters(elasticsearchProperties.getParameters());
		propertyMapper.from(elasticsearchProperties::getEntityCallbacks).as(BeanUtils::instantiateClass)
				.to(configurer::setEntityCallbacks);

		return configurer;
	}

	@Bean(name = "loggingElasticsearchConverter")
	@ConditionalOnMissingBean(name = "loggingElasticsearchConverter")
	@Override
	public ElasticsearchConverter elasticsearchEntityMapper(
			SimpleElasticsearchMappingContext elasticsearchMappingContext,
			ElasticsearchCustomConversions elasticsearchCustomConversions) {
		return super.elasticsearchEntityMapper(elasticsearchMappingContext, elasticsearchCustomConversions);
	}

	@Bean(name = "loggingElasticsearchMappingContext")
	@ConditionalOnMissingBean(name = "loggingElasticsearchMappingContext")
	@Override
	public SimpleElasticsearchMappingContext elasticsearchMappingContext(
			ElasticsearchCustomConversions elasticsearchCustomConversions) {
		return super.elasticsearchMappingContext(elasticsearchCustomConversions);
	}

	@Bean(name = "loggingElasticsearchCustomConversions")
	@ConditionalOnMissingBean(name = "loggingElasticsearchCustomConversions")
	@Override
	public ElasticsearchCustomConversions elasticsearchCustomConversions() {
		return super.elasticsearchCustomConversions();
	}

	@Bean(name = "loggingElasticsearchRestClientBuilderCustomizer")
	@ConditionalOnMissingBean(name = "loggingElasticsearchRestClientBuilderCustomizer")
	@Override
	public RestClientBuilderCustomizer restClientBuilderCustomizer() {
		return new DefaultRestClientBuilderCustomizer(elasticsearchProperties);
	}

	@Bean(name = "loggingElasticsearchRestClient")
	@ConditionalOnMissingBean(name = "loggingElasticsearchRestClient")
	@Override
	public RestClient restClient(@Qualifier("loggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
								 @Qualifier("loggingElasticsearchRestClientBuilderCustomizer") ObjectProvider<RestClientBuilderCustomizer> restClientBuilderCustomizer) {
		return super.restClient(configurer, restClientBuilderCustomizer);
	}

	@Bean(name = "loggingElasticsearchClient")
	@Override
	public ElasticsearchClient elasticsearchClient(
			@Qualifier("loggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
			@Qualifier("loggingElasticsearchRestClient") RestClient restClient,
			@Qualifier("loggingElasticsearchTransportOptionsCustomizer") ObjectProvider<TransportOptionsCustomizer> transportOptionsCustomizer) {
		return super.elasticsearchClient(configurer, restClient, transportOptionsCustomizer);
	}

	@Bean(name = "loggingElasticsearchTemplate")
	@Override
	public ElasticsearchTemplate elasticsearchTemplate(
			@Qualifier("loggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
			@Qualifier("loggingElasticsearchClient") ElasticsearchClient elasticsearchClient,
			@Qualifier("loggingElasticsearchConverter") ObjectProvider<ElasticsearchConverter> elasticsearchConverter) {
		return super.elasticsearchTemplate(configurer, elasticsearchClient, elasticsearchConverter);
	}

	@Override
	protected RefreshPolicy refreshPolicy() {
		return elasticsearchProperties.getRefreshPolicy();
	}

	static class DefaultRestClientBuilderCustomizer implements RestClientBuilderCustomizer,
			org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer {

		private final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		private final ElasticsearchProperties elasticsearchProperties;

		DefaultRestClientBuilderCustomizer(ElasticsearchProperties elasticsearchProperties) {
			this.elasticsearchProperties = elasticsearchProperties;
		}

		@Override
		public void customize(RestClientBuilder builder) {
		}

		@Override
		public void customize(HttpAsyncClientBuilder builder) {
			final CredentialsProvider credentialsProvider = new ElasticsearchCredentialsProvider(
					elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword());

			builder.setDefaultCredentialsProvider(credentialsProvider);

			elasticsearchProperties.getUrls()
					.stream()
					.map(this::toUri)
					.filter(this::hasUserInfo)
					.forEach((uri)->this.addUserInfoCredentials(uri, credentialsProvider));
		}

		@Override
		public void customize(RequestConfig.Builder builder) {
			propertyMapper.from(elasticsearchProperties::getConnectionTimeout).asInt(Duration::toMillis)
					.to(builder::setConnectTimeout);
			propertyMapper.from(elasticsearchProperties::getReadTimeout).asInt(Duration::toMillis)
					.to(builder::setSocketTimeout);
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

		private void addUserInfoCredentials(final URI uri, final CredentialsProvider credentialsProvider) {
			AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
			Credentials credentials = createUserInfoCredentials(uri.getUserInfo());
			credentialsProvider.setCredentials(authScope, credentials);
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
