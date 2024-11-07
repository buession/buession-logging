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
package com.buession.logging.jdbc.spring.config;

import com.buession.jdbc.config.Config;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

/**
 * Configures {@link JdbcTemplate} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class JdbcConfigurer {

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
	 * 设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句
	 */
	private String initSQL;

	/**
	 * 连接属性
	 */
	private Properties connectionProperties;

	/**
	 * {@link com.buession.jdbc.datasource.DataSource} 配置
	 */
	private Config config;

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
	 * 返回在将每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @return 每个新连接创建后，将其添加到池中之前执行的SQL语句
	 */
	public String getInitSQL() {
		return initSQL;
	}

	/**
	 * 设置每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @param initSQL
	 * 		每个新连接创建后，将其添加到池中之前执行的SQL语句
	 */
	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

	/**
	 * 返回连接属性
	 *
	 * @return 连接属性
	 */
	public Properties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * 设置连接属性
	 *
	 * @param connectionProperties
	 * 		连接属性
	 */
	public void setConnectionProperties(Properties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * 返回 {@link com.buession.jdbc.datasource.DataSource} 配置
	 *
	 * @return {@link com.buession.jdbc.datasource.DataSource} 配置
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * 设置 {@link com.buession.jdbc.datasource.DataSource} 配置
	 *
	 * @param config
	 *        {@link com.buession.jdbc.datasource.DataSource} 配置
	 */
	public void setConfig(Config config) {
		this.config = config;
	}

}
