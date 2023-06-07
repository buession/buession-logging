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
package com.buession.logging.springboot.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.logging.core.handler.DefaultLogHandler;
import com.buession.logging.core.handler.DefaultPrincipalHandler;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.spring.LogManagerFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
public class LogConfiguration {

	private final LogProperties logProperties;

	public LogConfiguration(LogProperties logProperties) {
		this.logProperties = logProperties;
	}

	@Bean
	@ConditionalOnMissingBean(PrincipalHandler.class)
	public PrincipalHandler<?> principalHandler() {
		return new DefaultPrincipalHandler();
	}

	@Bean
	@ConditionalOnMissingBean(LogHandler.class)
	public LogHandler logHandler() {
		return new DefaultLogHandler();
	}

	@Bean
	public LogManagerFactoryBean logManagerFactoryBean(ObjectProvider<PrincipalHandler<?>> principalHandler,
													   ObjectProvider<LogHandler> logHandler) {
		final LogManagerFactoryBean logManagerFactoryBean = new LogManagerFactoryBean();

		principalHandler.ifUnique(logManagerFactoryBean::setPrincipalHandler);
		logHandler.ifUnique(logManagerFactoryBean::setLogHandler);

		if(Validate.isNotBlank(logProperties.getClientIpHeaderName())){
			logManagerFactoryBean.setClientIpHeaderName(logProperties.getClientIpHeaderName());
		}

		return logManagerFactoryBean;
	}

}
