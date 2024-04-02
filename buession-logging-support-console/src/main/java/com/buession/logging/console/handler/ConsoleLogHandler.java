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
package com.buession.logging.console.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.formatter.DefaultConsoleLogDataFormatter;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;

/**
 * 控制台日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.4
 */
public class ConsoleLogHandler extends AbstractLogHandler {

	/**
	 * 日志模板
	 */
	private final String template;

	/**
	 * 日志格式化
	 */
	private ConsoleLogDataFormatter<String> formatter = new DefaultConsoleLogDataFormatter();

	/**
	 * 构造函数
	 *
	 * @param template
	 * 		日志模板
	 */
	public ConsoleLogHandler(final String template) {
		Assert.isBlank(template, "Log message template cloud not be null.");
		this.template = template;
	}

	/**
	 * 构造函数
	 *
	 * @param template
	 * 		日志模板
	 * @param formatter
	 * 		日志格式化
	 */
	public ConsoleLogHandler(final String template, final ConsoleLogDataFormatter<String> formatter) {
		this(template);
		Assert.isNull(formatter, "Formatter is null.");
		this.formatter = formatter;
	}

	@Override
	protected Status doHandle(final LogData logData) throws Exception {
		System.out.println(formatter.format(template, logData));
		return Status.SUCCESS;
	}

}
