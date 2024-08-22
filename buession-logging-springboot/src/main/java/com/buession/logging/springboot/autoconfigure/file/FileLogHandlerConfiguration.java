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
package com.buession.logging.springboot.autoconfigure.file;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.file.spring.FileLogHandlerFactoryBean;
import com.buession.logging.file.spring.config.AbstractFileLogHandlerConfiguration;
import com.buession.logging.file.spring.config.FileLogHandlerFactoryBeanConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.FileProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 文件日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({FileLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = FileProperties.PREFIX, name = "enabled", havingValue = "true")
public class FileLogHandlerConfiguration extends AbstractFileLogHandlerConfiguration {

	private final FileProperties fileProperties;

	public FileLogHandlerConfiguration(LogProperties logProperties) {
		this.fileProperties = logProperties.getFile();
	}

	@Bean(name = "loggingFileLogHandlerFactoryBeanConfigurer")
	@ConditionalOnMissingBean(name = "loggingFileLogHandlerFactoryBeanConfigurer")
	public FileLogHandlerFactoryBeanConfigurer fileLogHandlerFactoryBeanConfigurer() {
		final FileLogHandlerFactoryBeanConfigurer configurer = new FileLogHandlerFactoryBeanConfigurer();

		configurer.setPath(fileProperties.getPath());
		propertyMapper.from(fileProperties::getFormatter).as(BeanUtils::instantiateClass).to(configurer::setFormatter);

		return configurer;
	}

	@Bean
	@Override
	public FileLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingFileLogHandlerFactoryBeanConfigurer") FileLogHandlerFactoryBeanConfigurer configurer) {
		return super.logHandlerFactoryBean(configurer);
	}

}
