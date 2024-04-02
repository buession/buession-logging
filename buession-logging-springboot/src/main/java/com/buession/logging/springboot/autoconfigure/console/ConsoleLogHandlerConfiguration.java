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
package com.buession.logging.springboot.autoconfigure.console;

import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.ConsoleProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.4
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({ConsoleLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "console.enabled", havingValue = "true")
public class ConsoleLogHandlerConfiguration extends AbstractLogHandlerConfiguration<ConsoleProperties> {

	public ConsoleLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getConsole());
	}

	@Bean
	public ConsoleLogHandlerFactoryBean logHandlerFactoryBean() {
		final ConsoleLogHandlerFactoryBean logHandlerFactoryBean = new ConsoleLogHandlerFactoryBean();

		propertyMapper.from(handlerProperties::getTemplate).to(logHandlerFactoryBean::setTemplate);
		propertyMapper.from(handlerProperties::getFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setFormatter);

		return logHandlerFactoryBean;
	}

}
