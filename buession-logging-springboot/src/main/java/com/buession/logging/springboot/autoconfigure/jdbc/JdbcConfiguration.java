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

import com.buession.jdbc.datasource.*;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.jdbc.spring.config.AbstractJdbcConfiguration;
import com.buession.logging.jdbc.spring.config.JdbcConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.JdbcProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnProperty(prefix = JdbcProperties.PREFIX, name = "enabled", havingValue = "true")
public class JdbcConfiguration extends AbstractJdbcConfiguration {

	private final JdbcProperties properties;

	public JdbcConfiguration(LogProperties logProperties) {
		this.properties = logProperties.getJdbc();
	}

	@Bean(name = "loggingJdbcConfigurer")
	@ConditionalOnMissingBean(name = "loggingJdbcConfigurer")
	public JdbcConfigurer jdbcConfigurer() {
		final JdbcConfigurer configurer = new JdbcConfigurer();

		configurer.setDriverClassName(properties.getDriverClassName());
		configurer.setUrl(properties.getUrl());
		configurer.setUsername(properties.getUsername());
		configurer.setPassword(properties.getPassword());
		configurer.setInitSQL(properties.getInitSQL());
		configurer.setConnectionProperties(properties.getConnectionProperties());

		final Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>> dataSourceType = getDataSourceType();
		if(dataSourceType.isAssignableFrom(Dbcp2DataSource.class)){
			configurer.setConfig(properties.getDbcp2());
		}else if(dataSourceType.isAssignableFrom(DruidDataSource.class)){
			configurer.setConfig(properties.getDruid());
		}else if(dataSourceType.isAssignableFrom(HikariDataSource.class)){
			configurer.setConfig(properties.getHikari());
		}else if(dataSourceType.isAssignableFrom(OracleDataSource.class)){
			configurer.setConfig(properties.getOracle());
		}else if(dataSourceType.isAssignableFrom(TomcatDataSource.class)){
			configurer.setConfig(properties.getTomcat());
		}

		return configurer;
	}

	@Bean(name = "loggingJdbcDataSource")
	@ConditionalOnMissingBean(name = "loggingJdbcDataSource")
	public DataSource dataSource(@Qualifier("loggingJdbcConfigurer") JdbcConfigurer configurer) {
		return super.dataSource(configurer);
	}

	@Bean(name = "loggingJdbcTemplate")
	@ConditionalOnMissingBean(name = "loggingJdbcDataSource")
	public JdbcTemplate jdbcTemplate(@Qualifier("loggingJdbcDataSource") DataSource dataSource) {
		return super.jdbcTemplate(dataSource);
	}

}
