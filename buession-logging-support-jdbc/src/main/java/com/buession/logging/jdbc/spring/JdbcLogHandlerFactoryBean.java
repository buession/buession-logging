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
package com.buession.logging.jdbc.spring;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.id.IdGenerator;
import com.buession.core.utils.Assert;
import com.buession.logging.core.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.converter.DefaultLogDataConverter;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.jdbc.handler.JdbcLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC 日志处理器 {@link JdbcLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JdbcLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<JdbcLogHandler> {

	public final static MapFormatter<Object> DEFAULT_REQUEST_PARAMETERS_FORMATTER = new JsonMapFormatter();

	public final static GeoFormatter DEFAULT_GEO_FORMATTER = new DefaultGeoFormatter();

	public final static MapFormatter<Object> DEFAULT_EXTRA_FORMATTER = new JsonMapFormatter();

	/**
	 * {@link JdbcTemplate}
	 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * SQL
	 */
	private String sql;

	/**
	 * ID 生成器
	 */
	private IdGenerator<?> idGenerator;

	/**
	 * 日期时间格式
	 */
	private String dateTimeFormat;

	/**
	 * 请求参数格式化为字符串
	 */
	private MapFormatter<Object> requestParametersFormatter = DEFAULT_REQUEST_PARAMETERS_FORMATTER;

	/**
	 * Geo 格式化
	 */
	private GeoFormatter geoFormatter = DEFAULT_GEO_FORMATTER;

	/**
	 * 附加参数格式化为字符串
	 */
	private MapFormatter<Object> extraFormatter = DEFAULT_EXTRA_FORMATTER;

	/**
	 * 日志数据转换
	 *
	 * @since 2.3.3
	 */
	private LogDataConverter logDataConverter = new DefaultLogDataConverter();

	/**
	 * 返回 {@link JdbcTemplate}
	 *
	 * @return {@link JdbcTemplate}
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 设置 {@link JdbcTemplate}
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 返回 SQL
	 *
	 * @return SQL
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 设置 SQL
	 *
	 * @param sql
	 * 		SQL
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 返回 ID 生成器
	 *
	 * @return ID 生成器
	 */
	public IdGenerator<?> getIdGenerator() {
		return idGenerator;
	}

	/**
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	public void setIdGenerator(IdGenerator<?> idGenerator) {
		this.idGenerator = idGenerator;
	}

	/**
	 * 返回日期时间格式
	 *
	 * @return 日期时间格式
	 */
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	/**
	 * 设置日期时间格式
	 *
	 * @param dateTimeFormat
	 * 		日期时间格式
	 */
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * 返回请求参数格式化为字符串
	 *
	 * @return 请求参数格式化为字符串
	 */
	public MapFormatter<Object> getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(MapFormatter<Object> requestParametersFormatter) {
		this.requestParametersFormatter = requestParametersFormatter;
	}

	/**
	 * 返回 Geo 格式化
	 *
	 * @return Geo 格式化
	 */
	public GeoFormatter getGeoFormatter() {
		return geoFormatter;
	}

	/**
	 * 设置 Geo 格式化
	 *
	 * @param geoFormatter
	 * 		Geo 格式化
	 */
	public void setGeoFormatter(GeoFormatter geoFormatter) {
		this.geoFormatter = geoFormatter;
	}

	/**
	 * 返回附加参数格式化为字符串
	 *
	 * @return 附加参数格式化为字符串
	 */
	public MapFormatter<Object> getExtraFormatter() {
		return extraFormatter;
	}

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(MapFormatter<Object> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

	/**
	 * 返回日志数据转换
	 *
	 * @return 日志数据转换
	 *
	 * @since 2.3.3
	 */
	public LogDataConverter getLogDataConverter() {
		return logDataConverter;
	}

	/**
	 * 设置日志数据转换
	 *
	 * @param logDataConverter
	 * 		日志数据转换
	 *
	 * @since 2.3.3
	 */
	public void setLogDataConverter(LogDataConverter logDataConverter) {
		this.logDataConverter = logDataConverter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getJdbcTemplate(), "Property 'jdbcTemplate' is required");
		Assert.isBlank(getSql(), "Property 'sql' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = new JdbcLogHandler(getJdbcTemplate(), getSql());

					final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

					propertyMapper.from(getIdGenerator()).to(logHandler::setIdGenerator);
					propertyMapper.from(getDateTimeFormat()).whenHasText().as(DateTimeFormatter::new)
							.to(logHandler::setDateTimeFormatter);
					propertyMapper.from(getRequestParametersFormatter()).to(logHandler::setRequestParametersFormatter);
					propertyMapper.from(getGeoFormatter()).to(logHandler::setGeoFormatter);
					propertyMapper.from(getExtraFormatter()).to(logHandler::setExtraFormatter);
					propertyMapper.from(getLogDataConverter()).to(logHandler::setLogDataConverter);
				}
			}
		}
	}

}
