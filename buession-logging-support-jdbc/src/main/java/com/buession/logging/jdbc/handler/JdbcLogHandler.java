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
package com.buession.logging.jdbc.handler;

import com.buession.core.id.IdGenerator;
import com.buession.core.id.SnowflakeIdGenerator;
import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import com.buession.logging.core.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.converter.DefaultLogDataConverter;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

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
	private IdGenerator<?> idGenerator = new SnowflakeIdGenerator();

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

	/**
	 * 日志数据转换
	 *
	 * @since 2.3.3
	 */
	private LogDataConverter logDataConverter = new DefaultLogDataConverter();

	private final NamedParameterJdbcDaoSupport daoSupport = new NamedParameterJdbcDaoSupport();

	private final static Logger logger = LoggerFactory.getLogger(JdbcLogHandler.class);

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

		this.daoSupport.setJdbcTemplate(jdbcTemplate);
		this.sql = sql;
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
	protected Status doHandle(final LogData logData) throws Exception {
		logDataConverter.setIdGenerator(idGenerator);
		logDataConverter.setDateTimeFormatter(dateTimeFormatter);
		logDataConverter.setGeoFormatter(geoFormatter);
		logDataConverter.setRequestParametersFormatter(requestParametersFormatter);
		logDataConverter.setExtraFormatter(extraFormatter);

		Map<String, Object> args = logDataConverter.convert(logData);

		if(logger.isDebugEnabled()){
			logger.debug("Log sql: [{}], arguments: {}", sql, args);
		}

		daoSupport.getNamedParameterJdbcTemplate().update(sql, args);
		return Status.SUCCESS;
	}

}
