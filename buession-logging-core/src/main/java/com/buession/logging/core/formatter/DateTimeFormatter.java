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
package com.buession.logging.core.formatter;

import com.buession.core.utils.Assert;

import java.time.ZoneId;
import java.util.Date;

/**
 * 对日期时间对象 {@link Date} 格式化为与数据库相匹配的值
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class DateTimeFormatter implements Formatter<Date, Object> {

	private final String format;

	private java.time.format.DateTimeFormatter formatter;

	public DateTimeFormatter() {
		this("yyyy-MM-dd HH:mm:ss");
	}

	public DateTimeFormatter(final String format) {
		Assert.isNull(format, "Date time format is null.");
		this.format = format;
	}

	@Override
	public Object format(final Date date) {
		if(date == null){
			return null;
		}

		if("T".equals(format)){
			return date.getTime();
		}else if("t".equals(format)){
			return date.getTime() / 1000L;
		}

		return getDateTimeFormatter().format(date.toInstant());
	}

	private java.time.format.DateTimeFormatter getDateTimeFormatter() {
		if(formatter == null){
			formatter = java.time.format.DateTimeFormatter.ofPattern(format)
					.withZone(ZoneId.systemDefault());
		}

		return formatter;
	}

}
