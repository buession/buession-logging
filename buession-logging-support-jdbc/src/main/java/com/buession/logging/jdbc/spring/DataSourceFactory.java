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
import com.buession.jdbc.datasource.config.Dbcp2PoolConfiguration;
import com.buession.jdbc.datasource.config.DruidPoolConfiguration;
import com.buession.jdbc.datasource.config.HikariPoolConfiguration;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.jdbc.datasource.config.TomcatPoolConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
class DataSourceFactory {

	private final static Map<String, Setting> DATA_SOURCE_MAP = new LinkedHashMap<>(4);

	private final ClassLoader classLoader;

	/**
	 * 数据库驱动类名
	 */
	private final String driverClassName;

	/**
	 * JDBC URL
	 */
	private final String url;

	/**
	 * 数据库账号
	 */
	private final String username;

	/**
	 * 数据库密码
	 */
	private final String password;

	/**
	 * 连接池配置
	 */
	private final PoolConfiguration poolConfiguration;

	private DataSource dataSource;

	private Setting dataSourceSetting;

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

	public DataSourceFactory(final ClassLoader classLoader, final String driverClassName, final String url,
							 final String username, final String password, final PoolConfiguration poolConfiguration) {
		this.classLoader = classLoader;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.poolConfiguration = poolConfiguration;
	}

	@SuppressWarnings({"unchecked"})
	public DataSource createDataSource() {
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

	private Setting getDataSourceSetting() {
		if(dataSourceSetting != null){
			return dataSourceSetting;
		}

		dataSourceSetting = getDataSourceSetting(classLoader);
		if(dataSourceSetting != null){
			return dataSourceSetting;
		}

		throw new IllegalStateException("No supported javax.sql.DataSource type found");
	}

	public static Setting getDataSourceSetting(final ClassLoader classLoader) {
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

	private final static class Setting {

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
