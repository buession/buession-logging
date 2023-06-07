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

import com.buession.core.id.IdGenerator;
import com.buession.core.id.SnowflakeIdGenerator;
import com.buession.core.utils.Assert;
import com.buession.core.utils.StringUtils;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import com.buession.logging.core.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.jdbc.support.LoggingJdbcDaoSupport;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * JDBC 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JdbcLogHandler extends AbstractLogHandler {

	private final String sql;

	/**
	 * ID 生成器
	 */
	private IdGenerator<?> idGenerator;

	/**
	 * 日期时间格式化对象
	 */
	private DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();

	/**
	 * 请求参数格式化为字符串
	 */
	private MapFormatter<Object> requestParametersFormatter = new JsonMapFormatter();

	/**
	 * Geo 格式化
	 */
	private GeoFormatter geoFormatter = new DefaultGeoFormatter();

	/**
	 * 附加参数格式化为字符串
	 */
	private MapFormatter<Object> extraFormatter = new JsonMapFormatter();

	private final LoggingJdbcDaoSupport daoSupport;

	/**
	 * 构造函数
	 *
	 * @param jdbcTemplate
	 *        {@link JdbcTemplate}
	 * @param sql
	 * 		SQL 语句
	 */
	public JdbcLogHandler(final JdbcTemplate jdbcTemplate, final String sql) {
		Assert.isNull(jdbcTemplate, "JdbcTemplate is null.");
		Assert.isBlank(sql, "SQL is blank, empty or null.");

		this.sql = sql;
		daoSupport = new LoggingJdbcDaoSupport(jdbcTemplate, sql);
	}

	/**
	 * 设置SQL
	 *
	 * @param idGenerator
	 * 		SQL
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
	public void setRequestParametersFormatter(MapFormatter<Object> requestParametersFormatter) {
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
	public void setExtraFormatter(MapFormatter<Object> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

	@Override
	protected Status doHandle(final LogData logData) throws Exception {
		daoSupport.execute(buildData(logData));
		return Status.SUCCESS;
	}

	private Map<String, Object> buildData(final LogData logData) {
		final Map<String, Object> data = new HashMap<>(32);

		// 主键
		if(StringUtils.contains(sql, ":id")){
			if(idGenerator == null){
				idGenerator = new SnowflakeIdGenerator();
			}

			data.put("id", String.valueOf(idGenerator.nextId()));
		}

		// 用户 ID 字段
		data.put("userId", logData.getPrincipal() == null ? null : logData.getPrincipal().getId());

		// 用户名字段
		data.put("userName", logData.getPrincipal() == null ? null : logData.getPrincipal().getUserName());

		// 真实姓名字段
		data.put("realName", logData.getPrincipal() == null ? null : logData.getPrincipal().getRealName());

		// 日期时间字段
		data.put("dateTime", dateTimeFormatter.format(logData.getDateTime()).toString());

		// 业务类型字段
		data.put("businessType", logData.getBusinessType() == null ? null : logData.getBusinessType().toString());

		// 事件字段
		data.put("event", logData.getEvent() == null ? null : logData.getEvent().toString());

		// 描述字段
		data.put("description", logData.getDescription());

		// 客户端 IP 字段
		data.put("clientIp", logData.getClientIp());

		// Remote Addr 字段
		data.put("remoteAddr", logData.getRemoteAddr());

		// 请求地址字段
		data.put("url", logData.getUrl());

		// 请求方式字段
		data.put("requestMethod", logData.getRequestMethod().name());

		// 请求参数字段
		data.put("requestParameters", requestParametersFormatter.format(logData.getRequestParameters()));

		// 请求体字段
		data.put("requestBody", logData.getRequestBody());

		// User-Agent 字段
		data.put("userAgent", logData.getUserAgent());

		// 操作系统名称字段
		data.put("operatingSystemName",
				logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getName());

		// 操作系统版本字段
		data.put("operatingSystemVersion",
				logData.getOperatingSystem() == null ? null : logData.getOperatingSystem().getVersion());

		// 设备类型字段
		data.put("deviceType", logData.getDeviceType() == null ? null : logData.getDeviceType().name());

		// 浏览器名称字段
		data.put("browserName", logData.getBrowser() == null ? null : logData.getBrowser().getName());

		// 浏览器类型字段
		data.put("browserType", logData.getBrowser() == null ? null : logData.getBrowser().getType().name());

		// 浏览器版本字段
		data.put("browserVersion", logData.getBrowser() == null ? null : logData.getBrowser().getVersion());

		// 地理位置信息字段
		data.put("geo", logData.getLocation() == null ? null : geoFormatter.format(logData.getLocation().getGeo()));

		// 国家 Code 字段
		data.put("countryCode", logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
				logData.getLocation().getCountry().getCode());

		// 国家名称字段
		data.put("countryName", logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
				logData.getLocation().getCountry().getName());

		// 国家名称全称字段
		data.put("countryFullName", logData.getLocation() == null || logData.getLocation().getCountry() == null ? null :
				logData.getLocation().getCountry().getFullName());

		// 地区名称字段
		data.put("districtName", logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
				logData.getLocation().getDistrict().getName());

		// 地区名称全称字段
		data.put("districtFullName",
				logData.getLocation() == null || logData.getLocation().getDistrict() == null ? null :
						logData.getLocation().getDistrict().getFullName());

		// 结果字段
		data.put("status", logData.getStatus() == null ? null : logData.getStatus().name());

		// 附加参数字段
		data.put("extra", logData.getExtra() == null ? null : extraFormatter.format(logData.getExtra()));

		return data;
	}

}
