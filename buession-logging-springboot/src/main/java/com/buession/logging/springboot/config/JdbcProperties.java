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
package com.buession.logging.springboot.config;

import com.buession.core.id.IdGenerator;
import com.buession.jdbc.config.Dbcp2Config;
import com.buession.jdbc.config.DruidConfig;
import com.buession.jdbc.config.GenericConfig;
import com.buession.jdbc.config.HikariConfig;
import com.buession.jdbc.config.OracleConfig;
import com.buession.jdbc.config.TomcatConfig;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.support.config.HandlerProperties;

import java.io.Serializable;
import java.time.Duration;
import java.util.Properties;

/**
 * JDBC 日志配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JdbcProperties implements HandlerProperties, Serializable {

	private final static long serialVersionUID = -8823778313817319882L;

	/**
	 * 数据库驱动类名
	 */
	private String driverClassName;

	/**
	 * JDBC URL
	 */
	private String url;
	/**
	 * 数据库账号
	 */
	private String username;

	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 登录超时
	 *
	 * @since 1.0.0
	 */
	private Duration loginTimeout;

	/**
	 * 设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句
	 *
	 * @since 1.0.0
	 */
	private String initSQL;

	/**
	 * 连接属性
	 *
	 * @since 1.0.0
	 */
	private Properties connectionProperties;

	/**
	 * DBCP2 数据源配置
	 *
	 * @since 1.0.0
	 */
	private Dbcp2Config dbcp2;

	/**
	 * Druid 数据源配置
	 *
	 * @since 1.0.0
	 */
	private DruidConfig druid;

	/**
	 * Hikari 数据源配置
	 *
	 * @since 1.0.0
	 */
	private HikariConfig hikari;

	/**
	 * Oracle 数据源配置
	 *
	 * @since 1.0.0
	 */
	private OracleConfig oracle;

	/**
	 * Tomcat 数据源配置
	 *
	 * @since 1.0.0
	 */
	private TomcatConfig tomcat;

	/**
	 * Generic 数据源配置
	 *
	 * @since 1.0.0
	 */
	private GenericConfig generic;

	/**
	 * SQL
	 */
	private String sql;

	/**
	 * ID 生成器
	 */
	private Class<? extends IdGenerator<?>> idGenerator;

	/**
	 * 日期时间格式
	 */
	private String dateTimeFormat;

	/**
	 * 请求参数格式化为字符串
	 */
	private Class<? extends MapFormatter<Object>> requestParametersFormatter = JsonMapFormatter.class;

	/**
	 * Geo 格式化
	 */
	private Class<? extends GeoFormatter> geoFormatter = DefaultGeoFormatter.class;

	/**
	 * 附加参数格式化为字符串
	 */
	private Class<? extends MapFormatter<Object>> extraFormatter = JsonMapFormatter.class;

	/**
	 * 返回数据库驱动类名
	 *
	 * @return 数据库驱动类名
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * 设置数据库驱动类名
	 *
	 * @param driverClassName
	 * 		数据库驱动类名
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * 返回 JDBC URL
	 *
	 * @return JDBC URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 JDBC URL
	 *
	 * @param url
	 * 		JDBC URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回数据库账号
	 *
	 * @return 数据库账号
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置数据库账号
	 *
	 * @param username
	 * 		数据库账号
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回数据库密码
	 *
	 * @return 数据库密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置数据库密码
	 *
	 * @param password
	 * 		数据库密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回登录超时
	 *
	 * @return 登录超时
	 *
	 * @since 1.0.0
	 */
	public Duration getLoginTimeout() {
		return loginTimeout;
	}

	/**
	 * 设置登录超时
	 *
	 * @param loginTimeout
	 * 		登录超时
	 *
	 * @since 1.0.0
	 */
	public void setLoginTimeout(Duration loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

	/**
	 * 返回在将每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @return 每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @since 1.0.0
	 */
	public String getInitSQL() {
		return initSQL;
	}

	/**
	 * 设置每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @param initSQL
	 * 		每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @since 1.0.0
	 */
	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

	/**
	 * 返回连接属性
	 *
	 * @return 连接属性
	 *
	 * @since 1.0.0
	 */
	public Properties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * 设置连接属性
	 *
	 * @param connectionProperties
	 * 		连接属性
	 *
	 * @since 1.0.0
	 */
	public void setConnectionProperties(Properties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * 返回 Dbcp2 数据源配置
	 *
	 * @return Dbcp2 数据源配置
	 *
	 * @since 1.0.0
	 */
	public Dbcp2Config getDbcp2() {
		return dbcp2;
	}

	/**
	 * 设置 Dbcp2 数据源配置
	 *
	 * @param dbcp2
	 * 		Dbcp2 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setDbcp2(Dbcp2Config dbcp2) {
		this.dbcp2 = dbcp2;
	}

	/**
	 * 返回 Druid 数据源配置
	 *
	 * @return Druid 数据源配置
	 *
	 * @since 1.0.0
	 */
	public DruidConfig getDruid() {
		return druid;
	}

	/**
	 * 设置 Druid 数据源配置
	 *
	 * @param druid
	 * 		Druid 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setDruid(DruidConfig druid) {
		this.druid = druid;
	}

	/**
	 * 返回 Hikari 数据源配置
	 *
	 * @return Hikari 数据源配置
	 *
	 * @since 1.0.0
	 */
	public HikariConfig getHikari() {
		return hikari;
	}

	/**
	 * 设置 Hikari 数据源配置
	 *
	 * @param hikari
	 * 		Hikari 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setHikari(HikariConfig hikari) {
		this.hikari = hikari;
	}

	/**
	 * 返回 Oracle 数据源配置
	 *
	 * @return Oracle 数据源配置
	 *
	 * @since 1.0.0
	 */
	public OracleConfig getOracle() {
		return oracle;
	}

	/**
	 * 设置 Oracle 数据源配置
	 *
	 * @param oracle
	 * 		Oracle 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setOracle(OracleConfig oracle) {
		this.oracle = oracle;
	}

	/**
	 * 返回 Tomcat 数据源配置
	 *
	 * @return Tomcat 数据源配置
	 *
	 * @since 1.0.0
	 */
	public TomcatConfig getTomcat() {
		return tomcat;
	}

	/**
	 * 设置 Tomcat 数据源配置
	 *
	 * @param tomcat
	 * 		Tomcat 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setTomcat(TomcatConfig tomcat) {
		this.tomcat = tomcat;
	}

	/**
	 * 返回 Generic 数据源配置
	 *
	 * @return Generic 数据源配置
	 *
	 * @since 1.0.0
	 */
	public GenericConfig getGeneric() {
		return generic;
	}

	/**
	 * 设置 Generic 数据源配置
	 *
	 * @param generic
	 * 		Generic 数据源配置
	 *
	 * @since 1.0.0
	 */
	public void setGeneric(GenericConfig generic) {
		this.generic = generic;
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
	public Class<? extends IdGenerator<?>> getIdGenerator() {
		return idGenerator;
	}

	/**
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	public void setIdGenerator(Class<? extends IdGenerator<?>> idGenerator) {
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
	public Class<? extends MapFormatter<Object>> getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(Class<? extends MapFormatter<Object>> requestParametersFormatter) {
		this.requestParametersFormatter = requestParametersFormatter;
	}

	/**
	 * 返回 Geo 格式化
	 *
	 * @return Geo 格式化
	 */
	public Class<? extends GeoFormatter> getGeoFormatter() {
		return geoFormatter;
	}

	/**
	 * 设置 Geo 格式化
	 *
	 * @param geoFormatter
	 * 		Geo 格式化
	 */
	public void setGeoFormatter(Class<? extends GeoFormatter> geoFormatter) {
		this.geoFormatter = geoFormatter;
	}

	/**
	 * 返回附加参数格式化为字符串
	 *
	 * @return 附加参数格式化为字符串
	 */
	public Class<? extends MapFormatter<Object>> getExtraFormatter() {
		return extraFormatter;
	}

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(Class<? extends MapFormatter<Object>> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

}
