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
package com.buession.logging.jdbc.spring.config;

import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.support.config.AbstractLogHandlerConfiguration;
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
public abstract class AbstractJdbcLogHandlerConfiguration extends AbstractLogHandlerConfiguration {

	@Bean
	public JdbcLogHandlerFactoryBean logHandlerFactoryBean(JdbcLogHandlerFactoryBeanConfigurer configurer,
														   JdbcTemplate jdbcTemplate) {
		final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();

		logHandlerFactoryBean.setJdbcTemplate(jdbcTemplate);
		logHandlerFactoryBean.setSql(configurer.getSql());

		propertyMapper.from(configurer::getIdGenerator).to(logHandlerFactoryBean::setIdGenerator);
		propertyMapper.from(configurer::getDateTimeFormat).to(logHandlerFactoryBean::setDateTimeFormat);
		propertyMapper.from(configurer::getRequestParametersFormatter)
				.to(logHandlerFactoryBean::setRequestParametersFormatter);
		propertyMapper.from(configurer::getExtraFormatter).to(logHandlerFactoryBean::setExtraFormatter);
		propertyMapper.from(configurer::getDataConverter).to(logHandlerFactoryBean::setLogDataConverter);

		return logHandlerFactoryBean;
	}

}
