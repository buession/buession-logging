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
package com.buession.logging.jdbc.converter;

import com.buession.core.converter.Converter;
import com.buession.core.id.IdGenerator;
import com.buession.logging.core.LogData;
import com.buession.logging.core.formatter.DateTimeFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.core.formatter.MapFormatter;

import java.util.Map;

/**
 * 日志数据转换
 *
 * @author Yong.Teng
 * @since 2.3.3
 */
public interface LogDataConverter extends Converter<LogData, Map<String, Object>> {

	/**
	 * 返回 ID 生成器
	 *
	 * @return ID 生成器
	 */
	IdGenerator<?> getIdGenerator();

	/**
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	void setIdGenerator(IdGenerator<?> idGenerator);

	/**
	 * 返回日期时间格式化对象
	 *
	 * @return 日期时间格式化对象
	 */
	DateTimeFormatter getDateTimeFormatter();

	/**
	 * 设置日期时间格式化对象
	 *
	 * @param dateTimeFormatter
	 * 		日期时间格式化对象
	 */
	void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter);

	/**
	 * 返回请求参数格式化为字符串
	 *
	 * @return 请求参数格式化为字符串
	 */
	MapFormatter<Object> getRequestParametersFormatter();

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	void setRequestParametersFormatter(MapFormatter<Object> requestParametersFormatter);

	/**
	 * 返回 Geo 格式化
	 *
	 * @return Geo 格式化
	 */
	GeoFormatter getGeoFormatter();

	/**
	 * 设置 Geo 格式化
	 *
	 * @param geoFormatter
	 * 		Geo 格式化
	 */
	void setGeoFormatter(GeoFormatter geoFormatter);

	/**
	 * 返回附加参数格式化为字符串
	 *
	 * @return 附加参数格式化为字符串
	 */
	MapFormatter<Object> getExtraFormatter();

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	void setExtraFormatter(MapFormatter<Object> extraFormatter);

}
