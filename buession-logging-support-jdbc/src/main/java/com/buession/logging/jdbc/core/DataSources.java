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
package com.buession.logging.jdbc.core;

import com.buession.jdbc.datasource.config.AbstractPoolConfiguration;
import com.buession.jdbc.datasource.config.Dbcp2PoolConfiguration;
import com.buession.jdbc.datasource.config.DruidPoolConfiguration;
import com.buession.jdbc.datasource.config.GenericPoolConfiguration;
import com.buession.jdbc.datasource.config.HikariPoolConfiguration;
import com.buession.jdbc.datasource.config.TomcatPoolConfiguration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public final class DataSources {

	private DataSources() {

	}

	private static void clearConfiguration(final AbstractPoolConfiguration configuration) {
		if(configuration != null){
			configuration.setUrl(null);
			configuration.setDriverClassName(null);
			configuration.setUsername(null);
			configuration.setPassword(null);
		}
	}

	public final static class HikariDataSource extends com.buession.jdbc.datasource.HikariDataSource {

		public HikariDataSource(String driverClassName, String url, String username, String password,
								HikariPoolConfiguration poolConfiguration) {
			super(driverClassName, url, username, password);
			clearConfiguration(getPoolConfiguration());
			setPoolConfiguration(poolConfiguration);
		}

		@Override
		protected void initialize(final com.zaxxer.hikari.HikariDataSource dataSource) {
			super.initialize(dataSource);
			dataSource.setPoolName("buession-logging");
		}
		
	}

	public final static class Dbcp2DataSource extends com.buession.jdbc.datasource.Dbcp2DataSource {

		public Dbcp2DataSource(String driverClassName, String url, String username, String password,
							   Dbcp2PoolConfiguration poolConfiguration) {
			super(driverClassName, url, username, password);
			clearConfiguration(getPoolConfiguration());
			setPoolConfiguration(poolConfiguration);
		}

	}

	public final static class DruidDataSource extends com.buession.jdbc.datasource.DruidDataSource {

		public DruidDataSource(String driverClassName, String url, String username, String password,
							   DruidPoolConfiguration poolConfiguration) {
			super(driverClassName, url, username, password);
			clearConfiguration(getPoolConfiguration());
			setPoolConfiguration(poolConfiguration);
		}

	}

	public final static class TomcatDataSource extends com.buession.jdbc.datasource.TomcatDataSource {

		public TomcatDataSource(String driverClassName, String url, String username, String password,
								TomcatPoolConfiguration poolConfiguration) {
			super(driverClassName, url, username, password);
			clearConfiguration(getPoolConfiguration());
			setPoolConfiguration(poolConfiguration);
		}

	}

	public final static class GenericDataSource extends com.buession.jdbc.datasource.GenericDataSource {

		public GenericDataSource(String driverClassName, String url, String username, String password,
								 GenericPoolConfiguration poolConfiguration) {
			super(driverClassName, url, username, password);
			clearConfiguration(getPoolConfiguration());
			setPoolConfiguration(poolConfiguration);
		}

	}

}
