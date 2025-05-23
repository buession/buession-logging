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
package com.buession.logging.console.formatter;

import com.buession.logging.core.LogData;
import com.buession.logging.core.formatter.LogDataFormatter;

/**
 * 控制台日志数据格式化
 *
 * @author Yong.Teng
 * @since 0.0.4
 */
public interface ConsoleLogDataFormatter extends LogDataFormatter<String> {

	/**
	 * 日志参数数据格式化为 T 类型对象数据
	 *
	 * @param template
	 * 		日志模板
	 * @param logData
	 * 		待格式化数据
	 *
	 * @return 日志参数数据格式化结果
	 */
	String format(final String template, final LogData logData);

}
