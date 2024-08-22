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

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchLogHandlerConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchLogHandlerFactoryBeanConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.ElasticsearchProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({ElasticsearchLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = ElasticsearchProperties.PREFIX, name = "enabled", havingValue = "true")
public class ElasticsearchLogHandlerConfiguration extends AbstractElasticsearchLogHandlerConfiguration {

	private final ElasticsearchProperties elasticsearchProperties;

	public ElasticsearchLogHandlerConfiguration(LogProperties logProperties) {
		this.elasticsearchProperties = logProperties.getElasticsearch();
	}

	@Bean(name = "loggingElasticsearchLogHandlerFactoryBeanConfigurer")
	@ConditionalOnMissingBean(name = "loggingElasticsearchLogHandlerFactoryBeanConfigurer")
	public ElasticsearchLogHandlerFactoryBeanConfigurer elasticsearchLogHandlerFactoryBeanConfigurer() {
		final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = new ElasticsearchLogHandlerFactoryBeanConfigurer();

		configurer.setIndexName(elasticsearchProperties.getIndexName());
		configurer.setAutoCreateIndex(elasticsearchProperties.getAutoCreateIndex());

		return configurer;
	}

	@Bean
	@Override
	public ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingElasticsearchTemplate") ElasticsearchTemplate elasticsearchTemplate,
			@Qualifier("loggingElasticsearchLogHandlerFactoryBeanConfigurer") ElasticsearchLogHandlerFactoryBeanConfigurer configurer) {
		return super.logHandlerFactoryBean(elasticsearchTemplate, configurer);
	}

}
