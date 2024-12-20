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
package com.buession.logging.file.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.core.formatter.DefaultLogDataFormatter;
import com.buession.logging.core.formatter.LogDataFormatter;
import com.buession.logging.file.handler.FileLogHandler;
import com.buession.logging.file.spring.config.FileLogHandlerFactoryBeanConfigurer;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;

import java.io.File;

/**
 * 文件日志处理器 {@link FileLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class FileLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<FileLogHandler> {

	/**
	 * 日志文件对象
	 */
	private File file;

	/**
	 * 日志格式化
	 */
	private LogDataFormatter<String> formatter = new DefaultLogDataFormatter();

	/**
	 * 构造函数
	 */
	public FileLogHandlerFactoryBean() {
	}

	/**
	 * 构造函数
	 *
	 * @param configurer
	 *        {@link FileLogHandlerFactoryBeanConfigurer}
	 */
	public FileLogHandlerFactoryBean(final FileLogHandlerFactoryBeanConfigurer configurer) {
		propertyMapper.alwaysApplyingWhenHasText().from(configurer::getPath).as(File::new).to(this::setFile);
		propertyMapper.from(configurer::getFormatter).to(this::setFormatter);
	}

	/**
	 * 返回日志文件对象
	 *
	 * @return 日志文件对象
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 设置日志文件对象
	 *
	 * @param file
	 * 		日志文件对象
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 返回日志格式化
	 *
	 * @return 日志格式化
	 */
	public LogDataFormatter<String> getFormatter() {
		return formatter;
	}

	/**
	 * 设置日志格式化
	 *
	 * @param formatter
	 * 		日志格式化
	 */
	public void setFormatter(LogDataFormatter<String> formatter) {
		this.formatter = formatter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getFile(), "Property 'file' is required");
		Assert.isTrue(getFile().isDirectory(), getFile() + " is a directory.");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = new FileLogHandler(getFile(), getFormatter());
				}
			}
		}
	}

}
