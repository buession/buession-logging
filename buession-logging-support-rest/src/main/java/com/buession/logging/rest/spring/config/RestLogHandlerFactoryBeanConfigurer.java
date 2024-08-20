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
package com.buession.logging.file.spring.config;

import com.buession.logging.core.formatter.LogDataFormatter;
import com.buession.logging.file.spring.FileLogHandlerFactoryBean;

/**
 * Configures {@link FileLogHandlerFactoryBean} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class FileLogHandlerFactoryBeanConfigurer {

	/**
	 * 日志文件路径
	 */
	private String path;

	/**
	 * 日志格式化
	 */
	private LogDataFormatter<String> formatter;

	/**
	 * 返回日志文件路径
	 *
	 * @return 日志文件路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置日志文件路径
	 *
	 * @param path
	 * 		日志文件路径
	 */
	public void setPath(String path) {
		this.path = path;
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

}