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
import com.buession.core.utils.StringUtils;
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

import java.util.ArrayList;
import java.util.List;

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
		final List<String> fields = new ArrayList<>();

		// 主键
		if(Validate.isNotBlank(fieldConfiguration.getIdFieldName())){
			fields.add(fieldConfiguration.getIdFieldName());
		}

		// 用户 ID 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserIdFieldName())){
			fields.add(fieldConfiguration.getUserIdFieldName());
		}

		// 用户名字段
		if(Validate.isNotBlank(fieldConfiguration.getUserNameFieldName())){
			fields.add(fieldConfiguration.getUserNameFieldName());
		}

		// 真实姓名字段
		if(Validate.isNotBlank(fieldConfiguration.getRealNameFieldName())){
			fields.add(fieldConfiguration.getRealNameFieldName());
		}

		// 日期时间字段
		if(Validate.isNotBlank(fieldConfiguration.getDateTimeFieldName())){
			fields.add(fieldConfiguration.getDateTimeFieldName());
		}

		// 业务类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBusinessTypeFieldName())){
			fields.add(fieldConfiguration.getBusinessTypeFieldName());
		}

		// 事件字段
		if(Validate.isNotBlank(fieldConfiguration.getEventFieldName())){
			fields.add(fieldConfiguration.getEventFieldName());
		}

		// 描述字段
		if(Validate.isNotBlank(fieldConfiguration.getDescriptionFieldName())){
			fields.add(fieldConfiguration.getDescriptionFieldName());
		}

		// 客户端 IP 字段
		if(Validate.isNotBlank(fieldConfiguration.getClientIpFieldName())){
			fields.add(fieldConfiguration.getClientIpFieldName());
		}

		// Remote Addr 字段
		if(Validate.isNotBlank(fieldConfiguration.getRemoteAddrFieldName())){
			fields.add(fieldConfiguration.getRemoteAddrFieldName());
		}

		// 请求地址字段
		if(Validate.isNotBlank(fieldConfiguration.getUrlFieldName())){
			fields.add(fieldConfiguration.getUrlFieldName());
		}

		// 请求方式字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestMethodFieldName())){
			fields.add(fieldConfiguration.getRequestMethodFieldName());
		}

		// 请求参数字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestParametersFieldName())){
			fields.add(fieldConfiguration.getRequestParametersFieldName());
		}

		// 请求体字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestBodyFieldName())){
			fields.add(fieldConfiguration.getRequestBodyFieldName());
		}

		// 响应体字段
		if(Validate.isNotBlank(fieldConfiguration.getResponseBodyFieldName())){
			fields.add(fieldConfiguration.getResponseBodyFieldName());
		}

		// User-Agent 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserAgentFieldName())){
			fields.add(fieldConfiguration.getUserAgentFieldName());
		}

		// 操作系统名称字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemNameFieldName())){
			fields.add(fieldConfiguration.getOperatingSystemNameFieldName());
		}

		// 操作系统版本字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemVersionFieldName())){
			fields.add(fieldConfiguration.getOperatingSystemVersionFieldName());
		}

		// 设备类型字段
		if(Validate.isNotBlank(fieldConfiguration.getDeviceTypeFieldName())){
			fields.add(fieldConfiguration.getDeviceTypeFieldName());
		}

		// 浏览器名称字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserNameFieldName())){
			fields.add(fieldConfiguration.getBrowserNameFieldName());
		}

		// 浏览器l类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserTypeFieldName())){
			fields.add(fieldConfiguration.getBrowserTypeFieldName());
		}

		// 浏览器版本字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserVersionFieldName())){
			fields.add(fieldConfiguration.getBrowserVersionFieldName());
		}

		// 地理位置信息字段
		if(Validate.isNotBlank(fieldConfiguration.getLocationFieldName())){
			fields.add(fieldConfiguration.getLocationFieldName());
		}

		// 国家 Code 字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryCodeFieldName())){
			fields.add(fieldConfiguration.getCountryCodeFieldName());
		}

		// 国家名称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryNameFieldName())){
			fields.add(fieldConfiguration.getCountryNameFieldName());
		}

		// 国家名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryFullNameFieldName())){
			fields.add(fieldConfiguration.getCountryFullNameFieldName());
		}

		// 地区名称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictNameFieldName())){
			fields.add(fieldConfiguration.getDistrictNameFieldName());
		}

		// 地区名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictFullNameFieldName())){
			fields.add(fieldConfiguration.getDistrictFullNameFieldName());
		}

		// 结果字段
		if(Validate.isNotBlank(fieldConfiguration.getStatusFieldName())){
			fields.add(fieldConfiguration.getStatusFieldName());
		}

		// 附加参数字段
		if(Validate.isNotBlank(fieldConfiguration.getExtraFieldName())){
			fields.add(fieldConfiguration.getExtraFieldName());
		}

		sql.append("INSERT ")
				.append(tableName)
				.append(" (")
				.append(StringUtils.join(fields, ','))
				.append(") VALUES (")
				.append(StringUtils.join(Arrays.repeat('?', fields.size()), ','))
				.append(')');

		return sql.toString();
	}

	private Object[] buildData(final LogData logData) {
		final List<String> values = new ArrayList<>();

		// 主键
		if(Validate.isNotBlank(fieldConfiguration.getIdFieldName())){
			if(idGenerator == null){
				idGenerator = new SnowflakeIdGenerator();
			}
			values.add(String.valueOf(idGenerator.nextId()));
		}

		// 用户 ID 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserIdFieldName())){
			values.add(logData.getPrincipal().getId());
		}

		// 用户名字段
		if(Validate.isNotBlank(fieldConfiguration.getUserNameFieldName())){
			values.add(logData.getPrincipal().getUserName());
		}

		// 真实姓名字段
		if(Validate.isNotBlank(fieldConfiguration.getRealNameFieldName())){
			values.add(logData.getPrincipal().getRealName());
		}

		// 日期时间字段
		if(Validate.isNotBlank(fieldConfiguration.getDateTimeFieldName())){
			values.add(dateTimeFormatter.format(logData.getDateTime()).toString());
		}

		// 业务类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBusinessTypeFieldName())){
			values.add(logData.getBusinessType() == null ? null : logData.getBusinessType().toString());
		}

		// 事件字段
		if(Validate.isNotBlank(fieldConfiguration.getEventFieldName())){
			values.add(logData.getEvent() == null ? null : logData.getEvent().toString());
		}

		// 描述字段
		if(Validate.isNotBlank(fieldConfiguration.getDescriptionFieldName())){
			values.add(logData.getDescription());
		}

		// 客户端 IP 字段
		if(Validate.isNotBlank(fieldConfiguration.getClientIpFieldName())){
			values.add(logData.getClientIp());
		}

		// Remote Addr 字段
		if(Validate.isNotBlank(fieldConfiguration.getRemoteAddrFieldName())){
			values.add(logData.getRemoteAddr());
		}

		// 请求地址字段
		if(Validate.isNotBlank(fieldConfiguration.getUrlFieldName())){
			values.add(logData.getUrl());
		}

		// 请求方式字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestMethodFieldName())){
			values.add(logData.getRequestMethod().name());
		}

		// 请求参数字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestParametersFieldName())){
			values.add(requestParametersFormatter.format(logData.getRequestParameters()));
		}

		// 请求体字段
		if(Validate.isNotBlank(fieldConfiguration.getRequestBodyFieldName())){
			values.add(logData.getRequestBody());
		}

		// 响应体字段
		if(Validate.isNotBlank(fieldConfiguration.getResponseBodyFieldName())){
			values.add(logData.getResponseBody());
		}

		// User-Agent 字段
		if(Validate.isNotBlank(fieldConfiguration.getUserAgentFieldName())){
			values.add(logData.getUserAgent());
		}

		// 操作系统名称字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemNameFieldName())){
			values.add(logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getName());
		}

		// 操作系统版本字段
		if(Validate.isNotBlank(fieldConfiguration.getOperatingSystemVersionFieldName())){
			values.add(logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getVersion());
		}

		// 设备类型字段
		if(Validate.isNotBlank(fieldConfiguration.getDeviceTypeFieldName())){
			values.add(logData.getDeviceType() == null ? null : logData.getDeviceType().name());
		}

		// 浏览器名称字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserNameFieldName())){
			values.add(logData.getBrowser() == null ? null : logData.getBrowser().getName());
		}

		// 浏览器类型字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserTypeFieldName())){
			values.add(logData.getBrowser() == null ? null : logData.getBrowser().getType().name());
		}

		// 浏览器版本字段
		if(Validate.isNotBlank(fieldConfiguration.getBrowserVersionFieldName())){
			values.add(logData.getBrowser() == null ? null : logData.getBrowser().getVersion());
		}

		// 地理位置信息字段
		if(Validate.isNotBlank(fieldConfiguration.getLocationFieldName())){
			values.add(logData.getLocation() == null ? null :
					geoFormatter.format(logData.getLocation().getGeo()));
		}

		// 国家 Code 字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryCodeFieldName())){
			values.add(logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getCode());
		}

		// 国家名称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryNameFieldName())){
			values.add(logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getName());
		}

		// 国家名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getCountryFullNameFieldName())){
			values.add(logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
					logData.getLocation().getCountry().getFullName());
		}

		// 地区名称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictNameFieldName())){
			values.add(logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
					logData.getLocation().getDistrict().getName());
		}

		// 地区名称全称字段
		if(Validate.isNotBlank(fieldConfiguration.getDistrictFullNameFieldName())){
			values.add(logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
					logData.getLocation().getDistrict().getFullName());
		}

		// 结果字段
		if(Validate.isNotBlank(fieldConfiguration.getStatusFieldName())){
			values.add(logData.getStatus() == null ? null : logData.getStatus().name());
		}

		// 附加参数字段
		if(Validate.isNotBlank(fieldConfiguration.getExtraFieldName())){
			values.add(extraFormatter.format(logData.getExtra()));
		}

		return values.toArray(new String[]{});
	}

}
