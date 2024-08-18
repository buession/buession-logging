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
import com.buession.core.validator.Validate;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.spring.ElasticsearchClientFactoryBean;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.ElasticsearchTemplateFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.ElasticsearchProperties;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({ElasticsearchLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
public class ElasticsearchLogHandlerConfiguration extends AbstractLogHandlerConfiguration<ElasticsearchProperties> {

	public ElasticsearchLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getElasticsearch());
	}

	@Bean(name = "loggingElasticsearchClient")
	public ElasticsearchClientFactoryBean elasticsearchClientFactoryBean() {
		final ElasticsearchClientFactoryBean elasticsearchClientFactoryBean = new ElasticsearchClientFactoryBean();

		elasticsearchClientFactoryBean.setUrls(properties.getUrls());
		propertyMapper.from(properties::getUsername).to(elasticsearchClientFactoryBean::setUsername);
		propertyMapper.from(properties::getPassword).to(elasticsearchClientFactoryBean::setPassword);

		RequestOptions.Builder requestOptionsBuilder = RequestOptions.DEFAULT.toBuilder();
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		propertyMapper.from(properties::getConnectionTimeout).as((v)->(int) v.toMillis())
				.to(requestConfigBuilder::setConnectTimeout);
		propertyMapper.from(properties::getReadTimeout).as((v)->(int) v.toMillis())
				.to(requestConfigBuilder::setSocketTimeout);

		if(Validate.isNotEmpty(properties.getHeaders())){
			properties.getHeaders().forEach(requestOptionsBuilder::addHeader);
		}

		if(Validate.isNotEmpty(properties.getParameters())){
			properties.getParameters().forEach(requestOptionsBuilder::addParameter);
		}

		requestOptionsBuilder.setRequestConfig(requestConfigBuilder.build());

		elasticsearchClientFactoryBean.setRequestOptions(requestOptionsBuilder.build());

		return elasticsearchClientFactoryBean;
	}

	@Bean(name = "loggingElasticsearchTemplate")
	public ElasticsearchTemplateFactoryBean elasticsearchTemplateFactoryBean(
			@Qualifier("loggingElasticsearchClient") ObjectProvider<ElasticsearchClient> elasticsearchClient) {
		final ElasticsearchTemplateFactoryBean elasticsearchTemplateFactoryBean =
				new ElasticsearchTemplateFactoryBean();

		elasticsearchClient.ifAvailable(elasticsearchTemplateFactoryBean::setClient);

		return elasticsearchTemplateFactoryBean;
	}

	@Bean
	public ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingElasticsearchTemplate") ObjectProvider<ElasticsearchTemplate> elasticsearchTemplate) {
		final ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean = new ElasticsearchLogHandlerFactoryBean();

		elasticsearchTemplate.ifAvailable(logHandlerFactoryBean::setElasticsearchTemplate);
		propertyMapper.from(properties::getIndexName).to(logHandlerFactoryBean::setIndexName);

		return logHandlerFactoryBean;
	}

}
