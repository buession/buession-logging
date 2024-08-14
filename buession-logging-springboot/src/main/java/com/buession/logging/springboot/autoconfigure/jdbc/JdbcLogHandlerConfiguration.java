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
import com.buession.logging.jdbc.converter.DefaultLogDataConverter;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.spring.DataSourceFactoryBean;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.JdbcTemplateFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.JdbcProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({JdbcLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "jdbc.enabled", havingValue = "true")
public class JdbcLogHandlerConfiguration extends AbstractLogHandlerConfiguration<JdbcProperties> {

	public JdbcLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getJdbc());
	}

	@Bean(name = "loggingDataSource")
	public DataSourceFactoryBean dataSourceFactoryBean() {
		final DataSourceFactoryBean dataSourceFactoryBean = new DataSourceFactoryBean();

		propertyMapper.from(handlerProperties::getDriverClassName).to(dataSourceFactoryBean::setDriverClassName);
		propertyMapper.from(handlerProperties::getUrl).to(dataSourceFactoryBean::setUrl);
		propertyMapper.from(handlerProperties::getUsername).to(dataSourceFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(dataSourceFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getLoginTimeout).to(dataSourceFactoryBean::setLoginTimeout);
		propertyMapper.from(handlerProperties::getConnectionProperties)
				.to(dataSourceFactoryBean::setConnectionProperties);

		if(dataSourceFactoryBean.getDataSourceType().isAssignableFrom(Dbcp2DataSource.class)){
			propertyMapper.from(handlerProperties::getDbcp2).to(dataSourceFactoryBean::setDataSourceConfiguration);
		}else if(dataSourceFactoryBean.getDataSourceType().isAssignableFrom(DruidDataSource.class)){
			propertyMapper.from(handlerProperties::getDruid).to(dataSourceFactoryBean::setDataSourceConfiguration);
		}else if(dataSourceFactoryBean.getDataSourceType().isAssignableFrom(HikariDataSource.class)){
			propertyMapper.from(handlerProperties::getHikari).to(dataSourceFactoryBean::setDataSourceConfiguration);
		}else if(dataSourceFactoryBean.getDataSourceType().isAssignableFrom(OracleDataSource.class)){
			propertyMapper.from(handlerProperties::getOracle).to(dataSourceFactoryBean::setDataSourceConfiguration);
		}else if(dataSourceFactoryBean.getDataSourceType().isAssignableFrom(TomcatDataSource.class)){
			propertyMapper.from(handlerProperties::getTomcat).to(dataSourceFactoryBean::setDataSourceConfiguration);
		}

		return dataSourceFactoryBean;
	}

	@Bean(name = "loggingJdbcJdbcTemplate")
	public JdbcTemplateFactoryBean jdbcTemplateFactoryBean(ObjectProvider<DataSourceFactoryBean> dataSourceFactory) {
		final JdbcTemplateFactoryBean jdbcTemplateFactoryBean = new JdbcTemplateFactoryBean();

		dataSourceFactory.ifAvailable((obj)->{
			try{
				jdbcTemplateFactoryBean.setDataSource(obj.getObject());
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		});

		return jdbcTemplateFactoryBean;
	}

	@Bean(name = "loggingJdbcDataConverter")
	public LogDataConverter logDataConverter() {
		return new DefaultLogDataConverter();
	}

	@Bean
	public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingJdbcJdbcTemplate") ObjectProvider<JdbcTemplate> jdbcTemplate) {
		final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();

		jdbcTemplate.ifUnique(logHandlerFactoryBean::setJdbcTemplate);

		propertyMapper.from(handlerProperties::getSql).to(logHandlerFactoryBean::setSql);
		propertyMapper.from(handlerProperties::getIdGenerator).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setIdGenerator);
		propertyMapper.from(handlerProperties::getDateTimeFormat).to(logHandlerFactoryBean::setDateTimeFormat);
		propertyMapper.from(handlerProperties::getRequestParametersFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setRequestParametersFormatter);
		propertyMapper.from(handlerProperties::getExtraFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setExtraFormatter);

		return logHandlerFactoryBean;
	}

}
