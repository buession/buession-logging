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
package com.buession.logging.springboot.autoconfigure.jdbc;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.config.AbstractJdbcLogHandlerConfiguration;
import com.buession.logging.jdbc.spring.config.JdbcLogHandlerFactoryBeanConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.JdbcProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({JdbcLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = JdbcProperties.PREFIX, name = "enabled", havingValue = "true")
public class JdbcLogHandlerConfiguration extends AbstractJdbcLogHandlerConfiguration {

	private final JdbcProperties jdbcProperties;

	public JdbcLogHandlerConfiguration(LogProperties logProperties) {
		this.jdbcProperties = logProperties.getJdbc();
	}

	@Bean(name = "loggingJdbcLogHandlerFactoryBeanConfigurer")
	@ConditionalOnMissingBean(name = "loggingJdbcLogHandlerFactoryBeanConfigurer")
	public JdbcLogHandlerFactoryBeanConfigurer jdbcLogHandlerFactoryBeanConfigurer() {
		final JdbcLogHandlerFactoryBeanConfigurer configurer = new JdbcLogHandlerFactoryBeanConfigurer();

		configurer.setSql(jdbcProperties.getSql());
		propertyMapper.from(jdbcProperties::getIdGenerator).as(BeanUtils::instantiateClass)
				.to(configurer::setIdGenerator);
		configurer.setDateTimeFormat(jdbcProperties.getDateTimeFormat());
		propertyMapper.from(jdbcProperties::getRequestParametersFormatter).as(BeanUtils::instantiateClass)
				.to(configurer::setRequestParametersFormatter);
		propertyMapper.from(jdbcProperties::getExtraFormatter).as(BeanUtils::instantiateClass)
				.to(configurer::setExtraFormatter);
		propertyMapper.from(jdbcProperties.getDataConverter()).as(BeanUtils::instantiateClass)
				.to(configurer::setDataConverter);

		return configurer;
	}

	@Bean
	@Override
	public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingJdbcLogHandlerFactoryBeanConfigurer") JdbcLogHandlerFactoryBeanConfigurer configurer,
			@Qualifier("loggingJdbcTemplate") JdbcTemplate jdbcTemplate) {
		return super.logHandlerFactoryBean(configurer, jdbcTemplate);
	}

}
