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
package com.buession.logging.jdbc.handler;

import com.buession.core.collect.Arrays;
import com.buession.core.id.IdGenerator;
import com.buession.core.id.SnowflakeIdGenerator;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.jdbc.core.FieldConfiguration;
import com.buession.logging.jdbc.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultDateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.jdbc.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.jdbc.formatter.MapFormatter;
import com.buession.logging.jdbc.support.LoggingJdbcDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * JDBC 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JdbcLogHandler implements LogHandler {

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
	private DateTimeFormatter dateTimeFormatter = new DefaultDateTimeFormatter();

	/**
	 * 请求参数格式化为字符串
	 */
	private MapFormatter requestParametersFormatter = new JsonMapFormatter();

	/**
	 * Geo 格式化
	 */
	private GeoFormatter geoFormatter = new DefaultGeoFormatter();

	/**
	 * 附加参数格式化为字符串
	 */
	private MapFormatter extraFormatter = new JsonMapFormatter();

	private final LoggingJdbcDaoSupport daoSupport;

	private final static Logger logger = LoggerFactory.getLogger(JdbcLogHandler.class);

	/**
	 * 构造函数
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 * @param tableName
	 * 		数据表名称
	 */
	public JdbcLogHandler(final JdbcTemplate jdbcTemplate, final String tableName) {
		this(jdbcTemplate, null, tableName);
	}

	/**
	 * 构造函数
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 * @param transactionTemplate
	 * 		The {@link TransactionTemplate}
	 * @param tableName
	 * 		数据表名称
	 */
	public JdbcLogHandler(final JdbcTemplate jdbcTemplate, final TransactionTemplate transactionTemplate,
						  final String tableName) {
		this(jdbcTemplate, transactionTemplate, tableName, null);
	}

	/**
	 * 构造函数
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 * @param tableName
	 * 		数据表名称
	 * @param fieldConfiguration
	 * 		字段配置
	 */
	public JdbcLogHandler(final JdbcTemplate jdbcTemplate, final String tableName,
						  final FieldConfiguration fieldConfiguration) {
		this(jdbcTemplate, tableName);
		setFieldConfiguration(fieldConfiguration);
	}

	/**
	 * 构造函数
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 * @param transactionTemplate
	 * 		The {@link TransactionTemplate}
	 * @param tableName
	 * 		数据表名称
	 * @param fieldConfiguration
	 * 		字段配置
	 */
	public JdbcLogHandler(final JdbcTemplate jdbcTemplate, final TransactionTemplate transactionTemplate,
						  final String tableName, final FieldConfiguration fieldConfiguration) {
		Assert.isNull(jdbcTemplate, "JdbcTemplate is null.");
		Assert.isBlank(tableName, "Table name is blank, empty or null.");
		this.tableName = tableName;
		setFieldConfiguration(fieldConfiguration);
		daoSupport = new LoggingJdbcDaoSupport(jdbcTemplate, transactionTemplate, buildLogSql());
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
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	public void setIdGenerator(IdGenerator<?> idGenerator) {
		this.idGenerator = idGenerator;
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
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(MapFormatter requestParametersFormatter) {
		this.requestParametersFormatter = requestParametersFormatter;
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
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(MapFormatter extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

	@Override
	public Status handle(final LogData logData) {
		try{
			daoSupport.execute(buildData(logData));
			return Status.SUCCESS;
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

	private String buildLogSql() {
		final StringBuilder sql = new StringBuilder();
		final String[] fields = new String[0];

		// 主键
		if(Validate.isNotBlank(fieldConfiguration.getIdFieldName())){
			Arrays.add(fields, fieldConfiguration.getIdFieldName());
		}

		// 用户 ID 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserIdFieldName())){
			Arrays.add(fields, fieldConfiguration.getUserIdFieldName());
		}

		// 用户名字段
		if(Validate.isNotBlank(fieldConfiguration.getUserNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getUserNameFieldName());
		}

		// 真实姓名字段
		if(Validate.isNotBlank(fieldConfiguration.getRealNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getRealNameFieldName());
		}

		// 日期时间字段
		if(Validate.isNotBlank(fieldConfiguration.getDateTimeFieldName())){
			Arrays.add(fields, fieldConfiguration.getDateTimeFieldName());
		}

		// 业务类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBusinessTypeFieldName())){
			Arrays.add(fields, fieldConfiguration.getBusinessTypeFieldName());
		}

		// 事件字段
		if(Validate.isNotBlank(fieldConfiguration.getEventFieldName())){
			Arrays.add(fields, fieldConfiguration.getEventFieldName());
		}

		// 描述字段
		if(Validate.isNotBlank(fieldConfiguration.getDescriptionFieldName())){
			Arrays.add(fields, fieldConfiguration.getDescriptionFieldName());
		}

		// 客户端 IP 字段
		if(Validate.isNotBlank(fieldConfiguration.getClientIpFieldName())){
			Arrays.add(fields, fieldConfiguration.getClientIpFieldName());
		}

		// Remote Addr 字段
		if(Validate.isNotBlank(fieldConfiguration.getRemoteAddrFieldName())){
			Arrays.add(fields, fieldConfiguration.getRemoteAddrFieldName());
		}

		// 请求地址字段
		if(Validate.isNotBlank(fieldConfiguration.getUrlFieldName())){
			Arrays.add(fields, fieldConfiguration.getUrlFieldName());
		}

		// 请求方式字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestMethodFieldName())){
			Arrays.add(fields, fieldConfiguration.getRequestMethodFieldName());
		}

		// 请求参数字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestParametersFieldName())){
			Arrays.add(fields, fieldConfiguration.getRequestParametersFieldName());
		}

		// 请求体字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestBodyFieldName())){
			Arrays.add(fields, fieldConfiguration.getRequestBodyFieldName());
		}

		// 响应体字段
		if(Validate.isNotBlank(fieldConfiguration.getResponseBodyFieldName())){
			Arrays.add(fields, fieldConfiguration.getResponseBodyFieldName());
		}

		// User-Agent 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserAgentFieldName())){
			Arrays.add(fields, fieldConfiguration.getUserAgentFieldName());
		}

		// 操作系统名称字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getOperatingSystemNameFieldName());
		}

		// 操作系统版本字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemVersionFieldName())){
			Arrays.add(fields, fieldConfiguration.getOperatingSystemVersionFieldName());
		}

		// 设备类型字段
		if(Validate.isNotBlank(fieldConfiguration.getDeviceTypeFieldName())){
			Arrays.add(fields, fieldConfiguration.getDeviceTypeFieldName());
		}

		// 浏览器名称字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getBrowserNameFieldName());
		}

		// 浏览器l类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserTypeFieldName())){
			Arrays.add(fields, fieldConfiguration.getBrowserTypeFieldName());
		}

		// 浏览器版本字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserVersionFieldName())){
			Arrays.add(fields, fieldConfiguration.getBrowserVersionFieldName());
		}

		// 地理位置信息字段
		if(Validate.isNotBlank(fieldConfiguration.getLocationFieldName())){
			Arrays.add(fields, fieldConfiguration.getLocationFieldName());
		}

		// 国家 Code 字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryCodeFieldName())){
			Arrays.add(fields, fieldConfiguration.getCountryCodeFieldName());
		}

		// 国家名称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getCountryNameFieldName());
		}

		// 国家名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryFullNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getCountryFullNameFieldName());
		}

		// 地区名称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getDistrictNameFieldName());
		}

		// 地区名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictFullNameFieldName())){
			Arrays.add(fields, fieldConfiguration.getDistrictFullNameFieldName());
		}

		// 结果字段
		if(Validate.isNotBlank(fieldConfiguration.getStatusFieldName())){
			Arrays.add(fields, fieldConfiguration.getStatusFieldName());
		}

		// 附加参数字段
		if(Validate.isNotBlank(fieldConfiguration.getExtraFieldName())){
			Arrays.add(fields, fieldConfiguration.getExtraFieldName());
		}

		sql.append("INSERT ")
				.append(tableName)
				.append(" (")
				.append(Arrays.toString(fields, ", "))
				.append(") VALUES (")
				.append(Arrays.repeat('?', fields.length))
				.append(')');

		return sql.toString();
	}

	private Object[] buildData(final LogData logData) {
		final String[] values = new String[0];

		// 主键
		if(Validate.isNotBlank(fieldConfiguration.getIdFieldName())){
			if(idGenerator == null){
				idGenerator = new SnowflakeIdGenerator();
			}
			Arrays.add(values, idGenerator.nextId());
		}

		// 用户 ID 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserIdFieldName())){
			Arrays.add(values, logData.getPrincipal().getId());
		}

		// 用户名字段
		if(Validate.isNotBlank(fieldConfiguration.getUserNameFieldName())){
			Arrays.add(values, logData.getPrincipal().getUserName());
		}

		// 真实姓名字段
		if(Validate.isNotBlank(fieldConfiguration.getRealNameFieldName())){
			Arrays.add(values, logData.getPrincipal().getRealName());
		}

		// 日期时间字段
		if(Validate.isNotBlank(fieldConfiguration.getDateTimeFieldName())){
			Arrays.add(values, dateTimeFormatter.format(logData.getDateTime()));
		}

		// 业务类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBusinessTypeFieldName())){
			Arrays.add(values, logData.getBusinessType());
		}

		// 事件字段
		if(Validate.isNotBlank(fieldConfiguration.getEventFieldName())){
			Arrays.add(values, logData.getEvent());
		}

		// 描述字段
		if(Validate.isNotBlank(fieldConfiguration.getDescriptionFieldName())){
			Arrays.add(values, logData.getDescription());
		}

		// 客户端 IP 字段
		if(Validate.isNotBlank(fieldConfiguration.getClientIpFieldName())){
			Arrays.add(values, logData.getClientIp());
		}

		// Remote Addr 字段
		if(Validate.isNotBlank(fieldConfiguration.getRemoteAddrFieldName())){
			Arrays.add(values, logData.getRemoteAddr());
		}

		// 请求地址字段
		if(Validate.isNotBlank(fieldConfiguration.getUrlFieldName())){
			Arrays.add(values, logData.getUrl());
		}

		// 请求方式字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestMethodFieldName())){
			Arrays.add(values, logData.getRequestMethod().name());
		}

		// 请求参数字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestParametersFieldName())){
			Arrays.add(values, requestParametersFormatter.format(logData.getRequestParameters()));
		}

		// 请求体字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestBodyFieldName())){
			Arrays.add(values, logData.getRequestBody());
		}

		// 响应体字段
		if(Validate.isNotBlank(fieldConfiguration.getResponseBodyFieldName())){
			Arrays.add(values, logData.getResponseBody());
		}

		// User-Agent 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserAgentFieldName())){
			Arrays.add(values, logData.getUserAgent());
		}

		// 操作系统名称字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemNameFieldName())){
			Arrays.add(values, logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getName());
		}

		// 操作系统版本字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemVersionFieldName())){
			Arrays.add(values, logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getVersion());
		}

		// 设备类型字段
		if(Validate.isNotBlank(fieldConfiguration.getDeviceTypeFieldName())){
			Arrays.add(values, logData.getDeviceType() == null ? null : logData.getDeviceType().name());
		}

		// 浏览器名称字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserNameFieldName())){
			Arrays.add(values, logData.getBrowser() == null ? null : logData.getBrowser().getName());
		}

		// 浏览器类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserTypeFieldName())){
			Arrays.add(values, logData.getBrowser() == null ? null : logData.getBrowser().getType().name());
		}

		// 浏览器版本字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserVersionFieldName())){
			Arrays.add(values, logData.getBrowser() == null ? null : logData.getBrowser().getVersion());
		}

		// 地理位置信息字段
		if(Validate.isNotBlank(fieldConfiguration.getLocationFieldName())){
			Arrays.add(values, logData.getLocation() == null ? null :
					geoFormatter.format(logData.getLocation().getGeo()));
		}

		// 国家 Code 字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryCodeFieldName())){
			Arrays.add(values, logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getCode());
		}

		// 国家名称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryNameFieldName())){
			Arrays.add(values, logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getName());
		}

		// 国家名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryFullNameFieldName())){
			Arrays.add(values, logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getFullName());
		}

		// 地区名称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictNameFieldName())){
			Arrays.add(values, logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
					logData.getLocation().getDistrict().getName());
		}

		// 地区名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictFullNameFieldName())){
			Arrays.add(values, logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
					logData.getLocation().getDistrict().getFullName());
		}

		// 结果字段
		if(Validate.isNotBlank(fieldConfiguration.getStatusFieldName())){
			Arrays.add(values, logData.getStatus() == null ? null : logData.getStatus().name());
		}

		// 附加参数字段
		if(Validate.isNotBlank(fieldConfiguration.getExtraFieldName())){
			Arrays.add(values, extraFormatter.format(logData.getExtra()));
		}

		return values;
	}

}
