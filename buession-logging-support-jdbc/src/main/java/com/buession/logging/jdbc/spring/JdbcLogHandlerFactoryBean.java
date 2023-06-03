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
package com.buession.logging.jdbc.spring;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.id.IdGenerator;
import com.buession.logging.jdbc.core.FieldConfiguration;
import com.buession.logging.jdbc.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultDateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.jdbc.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.jdbc.formatter.MapFormatter;
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

	public final static DateTimeFormatter DEFAULT_DATETIME_FORMATTER = new DefaultDateTimeFormatter();

	public final static MapFormatter DEFAULT_REQUEST_PARAMETERS_FORMATTER = new JsonMapFormatter();

	public final static GeoFormatter DEFAULT_GEO_FORMATTER = new DefaultGeoFormatter();

	public final static MapFormatter DEFAULT_EXTRA_FORMATTER = new JsonMapFormatter();

	/**
	 * {@link JdbcTemplate}
	 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * 数据表名称
	 */
	private String tableName;

	/**
	 * 字段配置
	 */
	private FieldConfiguration fieldConfiguration;

	/**
	 * ID 生成器
	 */
	private IdGenerator<?> idGenerator;

	/**
	 * 日期时间格式化对象
	 */
	private DateTimeFormatter dateTimeFormatter = DEFAULT_DATETIME_FORMATTER;

	/**
	 * 请求参数格式化为字符串
	 */
	private MapFormatter requestParametersFormatter = DEFAULT_REQUEST_PARAMETERS_FORMATTER;

	/**
	 * Geo 格式化
	 */
	private GeoFormatter geoFormatter = DEFAULT_GEO_FORMATTER;

	/**
	 * 附加参数格式化为字符串
	 */
	private MapFormatter extraFormatter = DEFAULT_EXTRA_FORMATTER;

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
	 * 返回数据表名称
	 *
	 * @return 数据表名称
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置数据表名称
	 *
	 * @param tableName
	 * 		数据表名称
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回字段配置
	 *
	 * @return 字段配置
	 */
	public FieldConfiguration getFieldConfiguration() {
		return fieldConfiguration;
	}

	/**
	 * 设置字段配置
	 *
	 * @param fieldConfiguration
	 * 		字段配置
	 */
	public void setFieldConfiguration(FieldConfiguration fieldConfiguration) {
		this.fieldConfiguration = fieldConfiguration;
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
	 * 返回日期时间格式化对象
	 *
	 * @return 日期时间格式化对象
	 */
	public DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	/**
	 * 设置日期时间格式化对象
	 *
	 * @param dateTimeFormatter
	 * 		日期时间格式化对象
	 */
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	/**
	 * 返回请求参数格式化为字符串
	 *
	 * @return 请求参数格式化为字符串
	 */
	public MapFormatter getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(MapFormatter requestParametersFormatter) {
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
	public MapFormatter getExtraFormatter() {
		return extraFormatter;
	}

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(MapFormatter extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		logHandler = new JdbcLogHandler(jdbcTemplate, tableName, fieldConfiguration);

		propertyMapper.from(idGenerator).to(logHandler::setIdGenerator);
		propertyMapper.from(dateTimeFormatter).to(logHandler::setDateTimeFormatter);
		propertyMapper.from(requestParametersFormatter).to(logHandler::setRequestParametersFormatter);
		propertyMapper.from(geoFormatter).to(logHandler::setGeoFormatter);
		propertyMapper.from(extraFormatter).to(logHandler::setExtraFormatter);
	}

}
