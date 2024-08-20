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
package com.buession.logging.springboot.autoconfigure.jdbc;

import com.buession.core.utils.Assert;
import com.buession.jdbc.config.*;
import com.buession.jdbc.datasource.*;
import com.buession.jdbc.datasource.pool.*;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.JdbcProperties;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({JdbcLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = JdbcProperties.PREFIX, name = "enabled", havingValue = "true")
public class JdbcLogHandlerConfiguration extends AbstractLogHandlerConfiguration<JdbcProperties> {

	private final static Map<String, Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>>> DATA_SOURCE_MAP =
			new LinkedHashMap<>(5);

	private Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>> dataSourceType;

	private com.buession.jdbc.datasource.DataSource<?, ?> dataSource;

	static {
		DATA_SOURCE_MAP.put("org.apache.commons.dbcp2.BasicDataSource",
				com.buession.jdbc.datasource.Dbcp2DataSource.class);
		DATA_SOURCE_MAP.put("com.alibaba.druid.pool.DruidDataSource",
				com.buession.jdbc.datasource.DruidDataSource.class);
		DATA_SOURCE_MAP.put("com.zaxxer.hikari.HikariDataSource", com.buession.jdbc.datasource.HikariDataSource.class);
		DATA_SOURCE_MAP.put("oracle.ucp.jdbc.PoolDataSource", com.buession.jdbc.datasource.OracleDataSource.class);
		DATA_SOURCE_MAP.put("org.apache.tomcat.jdbc.pool.DataSource",
				com.buession.jdbc.datasource.TomcatDataSource.class);
	}

	public JdbcLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getJdbc());
	}

	@Bean(name = "loggingJdbcDataSource")
	public DataSource dataSource() {
		Assert.isBlank(properties.getDriverClassName(), "Property 'driverClassName' is required");
		Assert.isBlank(properties.getUrl(), "Property 'url' is required");

		final Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>> dataSourceClazz = getDataSourceType();
		try{
			final Constructor<? extends com.buession.jdbc.datasource.DataSource<?, ?>> constructor =
					dataSourceClazz.getConstructor(String.class, String.class, String.class, String.class);

			this.dataSource = BeanUtils.instantiateClass(constructor, properties.getDriverClassName(),
					properties.getUrl(), properties.getUsername(), properties.getPassword());

			this.dataSource.setLoginTimeout(properties.getLoginTimeout());

			this.dataSource.setInitSQL(properties.getInitSQL());

			this.dataSource.setConnectionProperties(properties.getConnectionProperties());

			if(dataSourceClazz.isAssignableFrom(Dbcp2DataSource.class)){
				final Dbcp2Config dbcp2Config = properties.getDbcp2();

				if(dbcp2Config != null){
					Dbcp2DataSource dbcp2DataSource = (Dbcp2DataSource) this.dataSource;
					Dbcp2PoolConfiguration poolConfiguration = new Dbcp2PoolConfiguration();

					dbcp2DataSourceConfig(dbcp2DataSource, dbcp2Config, poolConfiguration);
				}
			}else if(dataSourceClazz.isAssignableFrom(DruidDataSource.class)){
				final DruidConfig druidConfig = properties.getDruid();

				if(druidConfig != null){
					DruidDataSource druidDataSource = (DruidDataSource) this.dataSource;
					DruidPoolConfiguration poolConfiguration = new DruidPoolConfiguration();

					druidDataSourceConfig(druidDataSource, druidConfig, poolConfiguration);
				}
			}else if(dataSourceClazz.isAssignableFrom(HikariDataSource.class)){
				final HikariConfig hikariConfig = properties.getHikari();

				if(hikariConfig != null){
					HikariDataSource hikariDataSource = (HikariDataSource) this.dataSource;
					HikariPoolConfiguration poolConfiguration = new HikariPoolConfiguration();

					hikariDataSourceConfig(hikariDataSource, hikariConfig, poolConfiguration);
				}
			}else if(dataSourceClazz.isAssignableFrom(OracleDataSource.class)){
				final OracleConfig oracleConfig = properties.getOracle();

				if(oracleConfig != null){
					OracleDataSource oracleDataSource = (OracleDataSource) this.dataSource;
					OraclePoolConfiguration poolConfiguration = new OraclePoolConfiguration();

					oracleDataSourceConfig(oracleDataSource, oracleConfig, poolConfiguration);
				}
			}else if(dataSourceClazz.isAssignableFrom(TomcatDataSource.class)){
				final TomcatConfig tomcatConfig = properties.getTomcat();

				if(tomcatConfig != null){
					TomcatDataSource tomcatDataSource = (TomcatDataSource) this.dataSource;
					TomcatPoolConfiguration poolConfiguration = new TomcatPoolConfiguration();

					tomcatDataSourceConfig(tomcatDataSource, tomcatConfig, poolConfiguration);
				}
			}

			return this.dataSource.createDataSource();
		}catch(NoSuchMethodException e){
			throw new BeanInstantiationException(dataSourceClazz,
					"Can't specify more arguments than constructor parameters");
		}
	}

	@Bean(name = "loggingJdbcJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("loggingJdbcDataSource") ObjectProvider<DataSource> dataSource) {
		return new JdbcTemplate(dataSource.getIfAvailable());
	}

	@Bean
	public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingJdbcJdbcTemplate") ObjectProvider<JdbcTemplate> jdbcTemplate) {
		final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();

		logHandlerFactoryBean.setJdbcTemplate(jdbcTemplate.getIfAvailable());
		logHandlerFactoryBean.setSql(properties.getSql());

		propertyMapper.from(properties::getIdGenerator).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setIdGenerator);
		propertyMapper.from(properties::getDateTimeFormat).to(logHandlerFactoryBean::setDateTimeFormat);
		propertyMapper.from(properties::getRequestParametersFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setRequestParametersFormatter);
		propertyMapper.from(properties::getExtraFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setExtraFormatter);
		propertyMapper.from(properties.getDataConverter()).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setLogDataConverter);

		return logHandlerFactoryBean;
	}

	private Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>> getDataSourceType() {
		if(this.dataSourceType != null){
			return this.dataSourceType;
		}

		for(Map.Entry<String, Class<? extends com.buession.jdbc.datasource.DataSource<?, ?>>> e : DATA_SOURCE_MAP.entrySet()){
			try{
				ClassUtils.forName(e.getKey(), JdbcLogHandlerConfiguration.class.getClassLoader());
				this.dataSourceType = e.getValue();
				return this.dataSourceType;
			}catch(Exception ex){
				// Swallow and continue
			}
		}

		throw new IllegalStateException("No supported javax.sql.DataSource type found");
	}

	protected void baseDataSourceConfig(final com.buession.jdbc.datasource.DataSource<?, ?> dataSource,
										final BaseConfig dataSourceConfig, final PoolConfiguration poolConfiguration) {
		dataSource.setQueryTimeout(dataSourceConfig.getQueryTimeout());
		dataSource.setDefaultTransactionIsolation(dataSourceConfig.getDefaultTransactionIsolation());
		dataSource.setDefaultAutoCommit(dataSourceConfig.getDefaultAutoCommit());
		dataSource.setAccessToUnderlyingConnectionAllowed(dataSourceConfig.getAccessToUnderlyingConnectionAllowed());
	}

	protected void dbcp2DataSourceConfig(final Dbcp2DataSource dataSource, final Dbcp2Config dataSourceConfig,
										 final Dbcp2PoolConfiguration poolConfiguration) {
		baseDataSourceConfig(dataSource, dataSourceConfig, poolConfiguration);

		dataSource.setConnectionFactoryClassName(dataSourceConfig.getConnectionFactoryClassName());

		dataSource.setFastFailValidation(dataSourceConfig.getFastFailValidation());
		dataSource.setLogExpiredConnections(dataSourceConfig.getLogExpiredConnections());

		dataSource.setAutoCommitOnReturn(dataSourceConfig.getAutoCommitOnReturn());
		dataSource.setRollbackOnReturn(dataSourceConfig.getRollbackOnReturn());

		dataSource.setCacheState(dataSourceConfig.getCacheState());
		dataSource.setDefaultAutoCommit(dataSourceConfig.getDefaultAutoCommit());

		poolConfiguration(poolConfiguration, dataSourceConfig);

		poolConfiguration.setMaxConnLifetime(dataSourceConfig.getMaxConnLifetime());

		poolConfiguration.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
		poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
		poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
		poolConfiguration.setClearStatementPoolOnReturn(dataSourceConfig.getClearStatementPoolOnReturn());

		poolConfiguration.setRemoveAbandonedOnBorrow(dataSourceConfig.getRemoveAbandonedOnBorrow());
		poolConfiguration.setRemoveAbandonedOnMaintenance(dataSourceConfig.getRemoveAbandonedOnMaintenance());
		poolConfiguration.setAbandonedUsageTracking(dataSourceConfig.getAbandonedUsageTracking());

		poolConfiguration.setSoftMinEvictableIdle(dataSourceConfig.getSoftMinEvictableIdle());

		poolConfiguration.setEvictionPolicyClassName(dataSourceConfig.getEvictionPolicyClassName());

		poolConfiguration.setLifo(dataSourceConfig.getLifo());
	}

	protected void druidDataSourceConfig(final DruidDataSource dataSource, final DruidConfig dataSourceConfig,
										 final DruidPoolConfiguration poolConfiguration) {
		baseDataSourceConfig(dataSource, dataSourceConfig, poolConfiguration);

		dataSource.setUserCallbackClassName(dataSourceConfig.getUserCallbackClassName());
		dataSource.setPasswordCallbackClassName(dataSourceConfig.getPasswordCallbackClassName());

		dataSource.setConnectTimeout(dataSourceConfig.getConnectTimeout());
		dataSource.setSocketTimeout(dataSourceConfig.getSocketTimeout());

		dataSource.setTimeBetweenConnectError(dataSourceConfig.getTimeBetweenConnectError());
		dataSource.setKillWhenSocketReadTimeout(dataSourceConfig.getKillWhenSocketReadTimeout());

		dataSource.setPhyTimeout(dataSourceConfig.getPhyTimeout());
		dataSource.setPhyMaxUseCount(dataSourceConfig.getPhyMaxUseCount());

		dataSource.setAsyncInit(dataSourceConfig.getAsyncInit());

		dataSource.setInitVariants(dataSourceConfig.getInitVariants());
		dataSource.setInitGlobalVariants(dataSourceConfig.getInitGlobalVariants());

		dataSource.setValidConnectionCheckerClassName(dataSourceConfig.getValidConnectionCheckerClassName());
		dataSource.setConnectionErrorRetryAttempts(dataSourceConfig.getConnectionErrorRetryAttempts());

		dataSource.setInitExceptionThrow(dataSourceConfig.getInitExceptionThrow());
		dataSource.setExceptionSorterClassName(dataSourceConfig.getExceptionSorterClassName());

		dataSource.setUseOracleImplicitCache(dataSourceConfig.getUseOracleImplicitCache());
		dataSource.setAsyncCloseConnectionEnable(dataSourceConfig.getAsyncCloseConnectionEnable());

		dataSource.setTransactionQueryTimeout(dataSourceConfig.getTransactionQueryTimeout());
		dataSource.setTransactionThreshold(dataSourceConfig.getTransactionThreshold());

		dataSource.setFairLock(dataSourceConfig.getFairLock());

		dataSource.setFailFast(dataSourceConfig.getFailFast());

		dataSource.setCheckExecuteTime(dataSourceConfig.getCheckExecuteTime());

		dataSource.setUseGlobalDataSourceStat(dataSourceConfig.getUseGlobalDataSourceStat());
		dataSource.setStatLoggerClassName(dataSourceConfig.getStatLoggerClassName());

		dataSource.setMaxSqlSize(dataSourceConfig.getMaxSqlSize());
		dataSource.setResetStatEnable(dataSourceConfig.getResetStatEnable());

		dataSource.setFilters(dataSourceConfig.getFilters());
		dataSource.setLoadSpifilterSkip(dataSourceConfig.getLoadSpifilterSkip());
		dataSource.setClearFiltersEnable(dataSourceConfig.getClearFiltersEnable());

		dataSource.setEnable(dataSourceConfig.getEnable());

		poolConfiguration(poolConfiguration, dataSourceConfig);
		poolConfiguration.setMaxActive(dataSourceConfig.getMaxActive());

		poolConfiguration.setKeepAlive(dataSourceConfig.getKeepAlive());
		poolConfiguration.setKeepAliveBetweenTime(dataSourceConfig.getKeepAliveBetweenTime());

		poolConfiguration.setUsePingMethod(dataSourceConfig.getUsePingMethod());
		poolConfiguration.setKeepConnectionUnderlyingTransactionIsolation(
				dataSourceConfig.getKeepConnectionUnderlyingTransactionIsolation());

		poolConfiguration.setMaxCreateTaskCount(dataSourceConfig.getMaxCreateTaskCount());
		poolConfiguration.setMaxWaitThreadCount(dataSourceConfig.getMaxWaitThreadCount());

		poolConfiguration.setOnFatalErrorMaxActive(dataSourceConfig.getOnFatalErrorMaxActive());
		poolConfiguration.setBreakAfterAcquireFailure(dataSourceConfig.getBreakAfterAcquireFailure());

		poolConfiguration.setNotFullTimeoutRetryCount(dataSourceConfig.getNotFullTimeoutRetryCount());

		poolConfiguration.setUseLocalSessionState(dataSourceConfig.getUseLocalSessionState());
		poolConfiguration.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
		poolConfiguration.setSharePreparedStatements(dataSourceConfig.getSharePreparedStatements());
		poolConfiguration.setMaxPoolPreparedStatementPerConnectionSize(
				dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
		poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());

		poolConfiguration.setRemoveAbandoned(dataSourceConfig.getRemoveAbandoned());

		poolConfiguration.setTimeBetweenLogStats(dataSourceConfig.getTimeBetweenLogStats());

		poolConfiguration.setDupCloseLogEnable(dataSourceConfig.getDupCloseLogEnable());
		poolConfiguration.setLogDifferentThread(dataSourceConfig.getLogDifferentThread());
	}

	protected void hikariDataSourceConfig(final HikariDataSource dataSource, final HikariConfig dataSourceConfig,
										  final HikariPoolConfiguration poolConfiguration) {
		dataSource.setQueryTimeout(dataSourceConfig.getQueryTimeout());
		baseDataSourceConfig(dataSource, dataSourceConfig, poolConfiguration);

		dataSource.setJndiName(dataSourceConfig.getJndiName());

		dataSource.setConnectionTimeout(dataSourceConfig.getConnectionTimeout());

		dataSource.setIsolateInternalQueries(dataSourceConfig.getIsolateInternalQueries());

		poolConfiguration(poolConfiguration, dataSourceConfig);
		poolConfiguration.setInitializationFailTimeout(dataSourceConfig.getInitializationFailTimeout());

		poolConfiguration.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());

		poolConfiguration.setConnectionTestQuery(dataSourceConfig.getConnectionTestQuery());
		poolConfiguration.setValidationTimeout(dataSourceConfig.getValidationTimeout());

		poolConfiguration.setIdleTimeout(dataSourceConfig.getIdleTimeout());
		poolConfiguration.setMaxLifetime(dataSourceConfig.getMaxLifetime());
		poolConfiguration.setKeepaliveTime(dataSourceConfig.getKeepaliveTime());

		poolConfiguration.setLeakDetectionThreshold(dataSourceConfig.getLeakDetectionThreshold());
		poolConfiguration.setAllowPoolSuspension(dataSourceConfig.getAllowPoolSuspension());

		poolConfiguration.setMetricsTrackerFactoryClassName(dataSourceConfig.getMetricsTrackerFactoryClassName());
		poolConfiguration.setMetricRegistryClassName(dataSourceConfig.getMetricRegistryClassName());

		poolConfiguration.setHealthCheckRegistryClassName(dataSourceConfig.getHealthCheckRegistryClassName());
		poolConfiguration.setHealthCheckProperties(dataSourceConfig.getHealthCheckProperties());
	}

	protected void oracleDataSourceConfig(final OracleDataSource dataSource, final OracleConfig dataSourceConfig,
										  final OraclePoolConfiguration poolConfiguration) {
		baseDataSourceConfig(dataSource, dataSourceConfig, poolConfiguration);

		dataSource.setNetworkProtocol(dataSourceConfig.getNetworkProtocol());
		dataSource.setServerName(dataSourceConfig.getServerName());
		dataSource.setPortNumber(dataSourceConfig.getPortNumber());
		dataSource.setServiceName(dataSourceConfig.getServiceName());
		dataSource.setDataSourceName(dataSourceConfig.getDataSourceName());
		dataSource.setDataSourceDescription(dataSourceConfig.getDataSourceDescription());
		dataSource.setDatabaseName(dataSourceConfig.getDatabaseName());

		dataSource.setRoleName(dataSourceConfig.getRoleName());
		dataSource.setPdbRoles(dataSourceConfig.getPdbRoles());

		dataSource.setConnectionFactoryClassName(dataSourceConfig.getConnectionFactoryClassName());
		dataSource.setFastConnectionFailoverEnabled(dataSourceConfig.getFastConnectionFailoverEnabled());

		dataSource.setOnsConfiguration(dataSourceConfig.getOnsConfiguration());

		dataSource.setShardingMode(dataSourceConfig.getShardingMode());
		dataSource.setMaxConnectionsPerShard(dataSourceConfig.getMaxConnectionsPerShard());

		dataSource.setMaxConnectionsPerService(dataSourceConfig.getMaxConnectionsPerService());

		poolConfiguration(poolConfiguration, dataSourceConfig);
		poolConfiguration.setMinPoolSize(dataSourceConfig.getMinPoolSize());
		poolConfiguration.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());

		poolConfiguration.setMaxIdleTime(dataSourceConfig.getMaxIdleTime());
		poolConfiguration.setTimeToLiveConnectionTimeout(dataSourceConfig.getTimeToLiveConnectionTimeout());

		poolConfiguration.setTrustIdleConnection(dataSourceConfig.getTrustIdleConnection());
		poolConfiguration.setMaxConnectionReuseTime(dataSourceConfig.getMaxConnectionReuseTime());
		poolConfiguration.setMaxConnectionReuseCount(dataSourceConfig.getMaxConnectionReuseCount());

		poolConfiguration.setConnectionLabelingHighCost(dataSourceConfig.getConnectionLabelingHighCost());
		poolConfiguration.setHighCostConnectionReuseThreshold(dataSourceConfig.getHighCostConnectionReuseThreshold());
		poolConfiguration.setConnectionRepurposeThreshold(dataSourceConfig.getConnectionRepurposeThreshold());

		poolConfiguration.setTimeoutCheckInterval(dataSourceConfig.getTimeoutCheckInterval());

		poolConfiguration.setMaxStatements(dataSourceConfig.getMaxStatements());

		poolConfiguration.setConnectionHarvestTriggerCount(dataSourceConfig.getConnectionHarvestTriggerCount());
		poolConfiguration.setConnectionHarvestMaxCount(dataSourceConfig.getConnectionHarvestMaxCount());

		poolConfiguration.setReadOnlyInstanceAllowed(dataSourceConfig.getReadOnlyInstanceAllowed());
		poolConfiguration.setCreateConnectionInBorrowThread(dataSourceConfig.getCreateConnectionInBorrowThread());
	}

	protected void tomcatDataSourceConfig(final TomcatDataSource dataSource, final TomcatConfig dataSourceConfig,
										  final TomcatPoolConfiguration poolConfiguration) {
		baseDataSourceConfig(dataSource, dataSourceConfig, poolConfiguration);

		dataSource.setJndiName(dataSourceConfig.getJndiName());

		dataSource.setAlternateUsernameAllowed(dataSourceConfig.getAlternateUsernameAllowed());

		dataSource.setCommitOnReturn(dataSourceConfig.getCommitOnReturn());
		dataSource.setRollbackOnReturn(dataSourceConfig.getRollbackOnReturn());

		dataSource.setValidatorClassName(dataSourceConfig.getValidatorClassName());
		dataSource.setValidationInterval(dataSourceConfig.getValidationInterval());

		dataSource.setJdbcInterceptors(dataSourceConfig.getJdbcInterceptors());

		poolConfiguration(poolConfiguration, dataSourceConfig);
		poolConfiguration.setMaxActive(dataSourceConfig.getMaxActive());
		poolConfiguration.setMaxAge(dataSourceConfig.getMaxAge());

		poolConfiguration.setTestOnConnect(dataSourceConfig.getTestOnConnect());

		poolConfiguration.setUseDisposableConnectionFacade(dataSourceConfig.getUseDisposableConnectionFacade());
		poolConfiguration.setIgnoreExceptionOnPreLoad(dataSourceConfig.getIgnoreExceptionOnPreLoad());

		poolConfiguration.setFairQueue(dataSourceConfig.getFairQueue());
		poolConfiguration.setUseStatementFacade(dataSourceConfig.getUseStatementFacade());

		poolConfiguration.setRemoveAbandoned(dataSourceConfig.getRemoveAbandoned());
		poolConfiguration.setSuspectTimeout(dataSourceConfig.getSuspectTimeout());
		poolConfiguration.setAbandonWhenPercentageFull(dataSourceConfig.getAbandonWhenPercentageFull());

		poolConfiguration.setPropagateInterruptState(dataSourceConfig.getPropagateInterruptState());
		poolConfiguration.setLogValidationErrors(dataSourceConfig.getLogValidationErrors());

		poolConfiguration.setUseLock(dataSourceConfig.getUseLock());
		poolConfiguration.setUseEquals(dataSourceConfig.getUseEquals());
	}

	protected void poolConfiguration(final PoolConfiguration poolConfiguration, final Config dataSourceConfig) {
		poolConfiguration.setPoolName(dataSourceConfig.getPoolName());

		poolConfiguration.setInitialSize(dataSourceConfig.getInitialSize());
		poolConfiguration.setMinIdle(dataSourceConfig.getMinIdle());
		poolConfiguration.setMaxIdle(dataSourceConfig.getMaxIdle());
		poolConfiguration.setMaxTotal(dataSourceConfig.getMaxTotal());
		poolConfiguration.setMaxWait(dataSourceConfig.getMaxWait());

		poolConfiguration.setTestOnCreate(dataSourceConfig.getTestOnCreate());
		poolConfiguration.setTestOnBorrow(dataSourceConfig.getTestOnBorrow());
		poolConfiguration.setTestOnReturn(dataSourceConfig.getTestOnReturn());
		poolConfiguration.setTestWhileIdle(dataSourceConfig.getTestWhileIdle());

		poolConfiguration.setValidationQuery(dataSourceConfig.getValidationQuery());
		poolConfiguration.setValidationQueryTimeout(dataSourceConfig.getValidationQueryTimeout());

		poolConfiguration.setMinEvictableIdle(dataSourceConfig.getMinEvictableIdle());
		poolConfiguration.setMaxEvictableIdle(dataSourceConfig.getMaxEvictableIdle());

		poolConfiguration.setNumTestsPerEvictionRun(dataSourceConfig.getNumTestsPerEvictionRun());
		poolConfiguration.setTimeBetweenEvictionRuns(dataSourceConfig.getTimeBetweenEvictionRuns());

		poolConfiguration.setRemoveAbandonedTimeout(dataSourceConfig.getRemoveAbandonedTimeout());
		poolConfiguration.setLogAbandoned(dataSourceConfig.getLogAbandoned());

		poolConfiguration.setJmx(dataSourceConfig.getJmx());
	}

}