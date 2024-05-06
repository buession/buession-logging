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

import com.buession.core.id.IdGenerator;
import com.buession.logging.core.formatter.DateTimeFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.core.formatter.MapFormatter;

/**
 * @author Yong.Teng
 * @since 2.3.3
 */
public abstract class AbstractLogDataConverter implements LogDataConverter {

	/**
	 * ID 生成器
	 */
	private IdGenerator<?> idGenerator;

	/**
	 * 日期时间格式化对象
	 */
	private DateTimeFormatter dateTimeFormatter;

	/**
	 * 请求参数格式化为字符串
	 */
	private MapFormatter<Object> requestParametersFormatter;

	/**
	 * Geo 格式化
	 */
	private GeoFormatter geoFormatter;

	/**
	 * 附加参数格式化为字符串
	 */
	private MapFormatter<Object> extraFormatter;

	@Override
	public IdGenerator<?> getIdGenerator() {
		return idGenerator;
	}

	@Override
	public void setIdGenerator(IdGenerator<?> idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	@Override
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	@Override
	public MapFormatter<Object> getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	@Override
	public void setRequestParametersFormatter(MapFormatter<Object> requestParametersFormatter) {
		this.requestParametersFormatter = requestParametersFormatter;
	}

	@Override
	public GeoFormatter getGeoFormatter() {
		return geoFormatter;
	}

	@Override
	public void setGeoFormatter(GeoFormatter geoFormatter) {
		this.geoFormatter = geoFormatter;
	}

	@Override
	public MapFormatter<Object> getExtraFormatter() {
		return extraFormatter;
	}

	@Override
	public void setExtraFormatter(MapFormatter<Object> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

}
