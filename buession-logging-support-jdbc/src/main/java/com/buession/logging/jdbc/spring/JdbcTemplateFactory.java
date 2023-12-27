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
import com.buession.core.utils.Assert;
import com.buession.jdbc.datasource.config.Dbcp2PoolConfiguration;
import com.buession.jdbc.datasource.config.DruidPoolConfiguration;
import com.buession.jdbc.datasource.config.HikariPoolConfiguration;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.jdbc.datasource.config.TomcatPoolConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link JdbcTemplate} 工厂
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JdbcTemplateFactory {

	private final static Map<String, Setting> DATA_SOURCE_MAP = new LinkedHashMap<>(4);

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
	 * 连接池配置
	 */
	private PoolConfiguration poolConfiguration;

	private ClassLoader classLoader;

	private Setting dataSourceSetting;

	private DataSource dataSource;

	static {
		DATA_SOURCE_MAP.put("com.zaxxer.hikari.HikariDataSource",
				new Setting(com.buession.jdbc.datasource.HikariDataSource.class, HikariPoolConfiguration.class));
		DATA_SOURCE_MAP.put("org.apache.commons.dbcp2.BasicDataSource",
				new Setting(com.buession.jdbc.datasource.Dbcp2DataSource.class, Dbcp2PoolConfiguration.class));
		DATA_SOURCE_MAP.put("com.alibaba.druid.pool.DruidDataSource",
				new Setting(com.buession.jdbc.datasource.DruidDataSource.class, DruidPoolConfiguration.class));
		DATA_SOURCE_MAP.put("org.apache.tomcat.jdbc.pool.DataSource",
				new Setting(com.buession.jdbc.datasource.TomcatDataSource.class, TomcatPoolConfiguration.class));
	}

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
	 * 返回连接池配置
	 *
	 * @return 连接池配置
	 */
	public PoolConfiguration getPoolConfiguration() {
		return poolConfiguration;
	}

	/**
	 * 设置连接池配置
	 *
	 * @param poolConfiguration
	 * 		连接池配置
	 */
	public void setPoolConfiguration(PoolConfiguration poolConfiguration) {
		this.poolConfiguration = poolConfiguration;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	protected JdbcTemplate createJdbcTemplate() {
		Assert.isBlank(getDriverClassName(), "Property 'driverClassName' is required");
		Assert.isBlank(getUrl(), "Property 'url' is required");

		return new JdbcTemplate(createDataSource());
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected DataSource createDataSource() {
		if(this.dataSource != null){
			return this.dataSource;
		}

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final Setting dataSourceSetting = getDataSourceSetting();
		final com.buession.jdbc.datasource.DataSource dataSource = BeanUtils.instantiateClass(
				dataSourceSetting.getDataSourceType());

		propertyMapper.from(driverClassName).to(dataSource::setDriverClassName);
		propertyMapper.from(url).to(dataSource::setUrl);
		propertyMapper.from(username).to(dataSource::setUsername);
		propertyMapper.from(password).to(dataSource::setPassword);
		propertyMapper.from(poolConfiguration).to(dataSource::setPoolConfiguration);

		this.dataSource = dataSource.createDataSource();
		return this.dataSource;
	}

	protected Setting getDataSourceSetting() {
		if(dataSourceSetting != null){
			return dataSourceSetting;
		}

		dataSourceSetting = getDataSourceSetting(classLoader);
		if(dataSourceSetting != null){
			return dataSourceSetting;
		}

		throw new IllegalStateException("No supported javax.sql.DataSource type found");
	}

	protected static Setting getDataSourceSetting(final ClassLoader classLoader) {
		for(Map.Entry<String, Setting> e : DATA_SOURCE_MAP.entrySet()){
			try{
				ClassUtils.forName(e.getKey(), classLoader);
				return e.getValue();
			}catch(Exception ex){
				// Swallow and continue
			}
		}

		return null;
	}

	protected final static class Setting {

		private final Class<? extends com.buession.jdbc.datasource.DataSource> dataSourceType;

		private final Class<? extends PoolConfiguration> poolConfiguration;

		public Setting(final Class<? extends com.buession.jdbc.datasource.DataSource> dataSourceType,
					   final Class<? extends PoolConfiguration> poolConfiguration) {
			this.dataSourceType = dataSourceType;
			this.poolConfiguration = poolConfiguration;
		}

		public Class<? extends com.buession.jdbc.datasource.DataSource> getDataSourceType() {
			return dataSourceType;
		}

		public Class<? extends PoolConfiguration> getPoolConfiguration() {
			return poolConfiguration;
		}

	}

}
