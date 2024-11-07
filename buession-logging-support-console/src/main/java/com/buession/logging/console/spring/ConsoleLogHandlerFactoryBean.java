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
package com.buession.logging.console.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.formatter.DefaultConsoleLogDataFormatter;
import com.buession.logging.console.handler.ConsoleLogHandler;
import com.buession.logging.console.spring.config.ConsoleLogHandlerFactoryBeanConfigurer;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;

import java.util.Optional;

/**
 * 控制台日志处理器 {@link ConsoleLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.4
 */
public class ConsoleLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<ConsoleLogHandler> {

	/**
	 * 日志模板
	 */
	private String template;

	/**
	 * 日志格式化
	 */
	private ConsoleLogDataFormatter formatter = new DefaultConsoleLogDataFormatter();

	/**
	 * 构造函数
	 */
	public ConsoleLogHandlerFactoryBean() {
	}

	/**
	 * 构造函数
	 *
	 * @param configurer
	 *        {@link  ConsoleLogHandlerFactoryBeanConfigurer}
	 */
	public ConsoleLogHandlerFactoryBean(final ConsoleLogHandlerFactoryBeanConfigurer configurer) {
		setTemplate(configurer.getTemplate());
		Optional.ofNullable(configurer.getFormatter()).ifPresent(this::setFormatter);
	}

	/**
	 * 返回日志模板
	 *
	 * @return 日志模板
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * 设置日志模板
	 *
	 * @param template
	 * 		日志模板
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 返回日志格式化
	 *
	 * @return 日志格式化
	 */
	public ConsoleLogDataFormatter getFormatter() {
		return formatter;
	}

	/**
	 * 设置日志格式化
	 *
	 * @param formatter
	 * 		日志格式化
	 */
	public void setFormatter(ConsoleLogDataFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getTemplate(), "Property 'template' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = getFormatter() == null ? new ConsoleLogHandler(getTemplate()) : new ConsoleLogHandler(
							getTemplate(), getFormatter());
				}
			}
		}
	}

}
