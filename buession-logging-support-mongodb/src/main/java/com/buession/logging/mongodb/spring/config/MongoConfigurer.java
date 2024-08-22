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
package com.buession.logging.mongodb.spring.config;

import com.buession.dao.mongodb.core.ReadConcern;
import com.buession.dao.mongodb.core.ReadPreference;
import com.buession.dao.mongodb.core.WriteConcern;
import com.buession.logging.mongodb.core.PoolConfiguration;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterType;
import com.mongodb.connection.ServerMonitoringMode;
import com.mongodb.selector.ServerSelector;
import org.bson.UuidRepresentation;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Duration;

/**
 * Configures {@link MongoTemplate} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class MongoConfigurer {

	/**
	 * MongoDB 主机地址
	 */
	private String host;

	/**
	 * MongoDB 端口
	 */
	private Integer port;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 认证数据库名称
	 */
	private String authenticationDatabase;

	/**
	 * Mongo database URI.
	 */
	private String url;

	/**
	 * 副本集名称
	 */
	private String replicaSetName;

	/**
	 * 数据库名称
	 */
	private String databaseName;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout;

	/**
	 * 读取超时
	 */
	private Duration readTimeout;

	/**
	 * Representation to use when converting a UUID to a BSON binary value.
	 */
	private UuidRepresentation uuidRepresentation;

	/**
	 * {@link ReadPreference}
	 */
	private ReadPreference readPreference;

	/**
	 * {@link ReadConcern}
	 */
	private ReadConcern readConcern;

	/**
	 * {@link WriteConcern}
	 */
	private WriteConcern writeConcern;

	/**
	 * Whether to enable auto-index creation.
	 */
	private Boolean autoIndexCreation;

	/**
	 * Fully qualified name of the FieldNamingStrategy to use.
	 */
	private FieldNamingStrategy fieldNamingStrategy;

	/**
	 * 集群配置
	 */
	private Cluster cluster;

	/**
	 * 服务端配置
	 */
	private Server server;

	/**
	 * 连接池配置
	 */
	private PoolConfiguration pool;

	/**
	 * 返回 MongoDB 主机地址
	 *
	 * @return MongoDB 主机地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 MongoDB 主机地址
	 *
	 * @param host
	 * 		MongoDB 主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 MongoDB 端口
	 *
	 * @return MongoDB 端口
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * 设置 MongoDB 端口
	 *
	 * @param port
	 * 		MongoDB 端口
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回认证数据库名称
	 *
	 * @return 认证数据库名称
	 */
	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}

	/**
	 * 设置认证数据库名称
	 *
	 * @param authenticationDatabase
	 * 		认证数据库名称
	 */
	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	/**
	 * Return Mongo database URI.
	 *
	 * @return Mongo database URI.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets Mongo database URI.
	 *
	 * @param url
	 * 		Mongo database URI.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回副本集名称
	 *
	 * @return 副本集名称
	 */
	public String getReplicaSetName() {
		return replicaSetName;
	}

	/**
	 * 设置副本集名称
	 *
	 * @param replicaSetName
	 * 		副本集名称
	 */
	public void setReplicaSetName(String replicaSetName) {
		this.replicaSetName = replicaSetName;
	}

	/**
	 * 返回数据库名称
	 *
	 * @return 数据库名称
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置数据库名称
	 *
	 * @param databaseName
	 * 		数据库名称
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectionTimeout
	 * 		连接超时
	 */
	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 返回读取超时
	 *
	 * @return 读取超时
	 */
	public Duration getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 设置读取超时
	 *
	 * @param readTimeout
	 * 		读取超时
	 */
	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * Return representation to use when converting a UUID to a BSON binary value.
	 *
	 * @return Representation to use when converting a UUID to a BSON binary value.
	 */
	public UuidRepresentation getUuidRepresentation() {
		return uuidRepresentation;
	}

	/**
	 * Sets representation to use when converting a UUID to a BSON binary value.
	 *
	 * @param uuidRepresentation
	 * 		Representation to use when converting a UUID to a BSON binary value.
	 */
	public void setUuidRepresentation(UuidRepresentation uuidRepresentation) {
		this.uuidRepresentation = uuidRepresentation;
	}

	/**
	 * 返回 {@link ReadPreference}
	 *
	 * @return {@link ReadPreference}
	 */
	public ReadPreference getReadPreference() {
		return readPreference;
	}

	/**
	 * 设置 {@link ReadPreference}
	 *
	 * @param readPreference
	 *        {@link ReadPreference}
	 */
	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	/**
	 * 返回 {@link ReadConcern}
	 *
	 * @return {@link ReadConcern}
	 */
	public ReadConcern getReadConcern() {
		return readConcern;
	}

	/**
	 * 设置 {@link ReadConcern}
	 *
	 * @param readConcern
	 *        {@link ReadConcern}
	 */
	public void setReadConcern(ReadConcern readConcern) {
		this.readConcern = readConcern;
	}

	/**
	 * 返回 {@link WriteConcern}
	 *
	 * @return {@link WriteConcern}
	 */
	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	/**
	 * 设置 {@link WriteConcern}
	 *
	 * @param writeConcern
	 *        {@link WriteConcern}
	 */
	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}

	/**
	 * Return enable auto-index creation.
	 *
	 * @return true / false
	 */
	public Boolean isAutoIndexCreation() {
		return getAutoIndexCreation();
	}

	/**
	 * Return enable auto-index creation.
	 *
	 * @return true / false
	 */
	public Boolean getAutoIndexCreation() {
		return autoIndexCreation;
	}

	/**
	 * Sets enable auto-index creation.
	 *
	 * @param autoIndexCreation
	 * 		enable auto-index creation.
	 */
	public void setAutoIndexCreation(Boolean autoIndexCreation) {
		this.autoIndexCreation = autoIndexCreation;
	}

	/**
	 * Return fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @return Fully qualified name of the FieldNamingStrategy to use.
	 */
	public FieldNamingStrategy getFieldNamingStrategy() {
		return fieldNamingStrategy;
	}

	/**
	 * Sets fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @param fieldNamingStrategy
	 * 		Fully qualified name of the FieldNamingStrategy to use.
	 */
	public void setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	/**
	 * 返回集群配置
	 *
	 * @return 集群配置
	 */
	public Cluster getCluster() {
		return cluster;
	}

	/**
	 * 设置集群配置
	 *
	 * @param cluster
	 * 		集群配置
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	/**
	 * 返回服务端配置
	 *
	 * @return 服务端配置
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * 设置服务端配置
	 *
	 * @param server
	 * 		服务端配置
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * 返回连接池配置
	 *
	 * @return 连接池配置
	 */
	public PoolConfiguration getPool() {
		return pool;
	}

	/**
	 * 设置连接池配置
	 *
	 * @param pool
	 * 		连接池配置
	 */
	public void setPool(PoolConfiguration pool) {
		this.pool = pool;
	}

	/**
	 * 集群配置
	 *
	 * @since 1.0.0
	 */
	public final static class Cluster {

		/**
		 * 集群连接模式
		 */
		private ClusterConnectionMode connectionMode;

		/**
		 * 集群类型
		 */
		private ClusterType clusterType;

		/**
		 * Server 选择器
		 */
		private ServerSelector serverSelector;

		/**
		 * Server 选择超时
		 */
		private Duration serverSelectionTimeout;

		/**
		 * 客户端在选择合适的服务器（节点）时的本地阈值
		 */
		private Duration localThreshold;

		/**
		 * 返回集群连接模式
		 *
		 * @return 集群连接模式
		 */
		public ClusterConnectionMode getConnectionMode() {
			return connectionMode;
		}

		/**
		 * 设置集群连接模式
		 *
		 * @param connectionMode
		 * 		集群连接模式
		 */
		public void setConnectionMode(ClusterConnectionMode connectionMode) {
			this.connectionMode = connectionMode;
		}

		/**
		 * 返回集群类型
		 *
		 * @return 集群类型
		 */
		public ClusterType getClusterType() {
			return clusterType;
		}

		/**
		 * 设置集群类型
		 *
		 * @param clusterType
		 * 		集群类型
		 */
		public void setClusterType(ClusterType clusterType) {
			this.clusterType = clusterType;
		}

		/**
		 * 返回 Server 选择器
		 *
		 * @return Server 选择器
		 */
		public ServerSelector getServerSelector() {
			return serverSelector;
		}

		/**
		 * 设置 Server 选择器
		 *
		 * @param serverSelector
		 * 		Server 选择器
		 */
		public void setServerSelector(ServerSelector serverSelector) {
			this.serverSelector = serverSelector;
		}

		/**
		 * 返回 Server 选择超时
		 *
		 * @return Server 选择超时
		 */
		public Duration getServerSelectionTimeout() {
			return serverSelectionTimeout;
		}

		/**
		 * 设置 Server 选择超时
		 *
		 * @param serverSelectionTimeout
		 * 		Server 选择超时
		 */
		public void setServerSelectionTimeout(Duration serverSelectionTimeout) {
			this.serverSelectionTimeout = serverSelectionTimeout;
		}

		/**
		 * 返回客户端在选择合适的服务器（节点）时的本地阈值
		 *
		 * @return 客户端在选择合适的服务器（节点）时的本地阈值
		 */
		public Duration getLocalThreshold() {
			return localThreshold;
		}

		/**
		 * 设置客户端在选择合适的服务器（节点）时的本地阈值
		 *
		 * @param localThreshold
		 * 		客户端在选择合适的服务器（节点）时的本地阈值
		 */
		public void setLocalThreshold(Duration localThreshold) {
			this.localThreshold = localThreshold;
		}

	}

	/**
	 * 服务端配置
	 *
	 * @since 1.0.0
	 */
	public final static class Server {

		/**
		 * 节点之间的心跳检查频率
		 */
		private Duration heartbeatFrequency;

		/**
		 * 客户端发送心跳请求的最小频率
		 */
		private Duration minHeartbeatFrequency;

		/**
		 * 服务端监控模式
		 */
		private ServerMonitoringMode serverMonitoringMode;

		/**
		 * 返回节点之间的心跳检查频率
		 *
		 * @return 节点之间的心跳检查频率
		 */
		public Duration getHeartbeatFrequency() {
			return heartbeatFrequency;
		}

		/**
		 * 设置节点之间的心跳检查频率
		 *
		 * @param heartbeatFrequency
		 * 		节点之间的心跳检查频率
		 */
		public void setHeartbeatFrequency(Duration heartbeatFrequency) {
			this.heartbeatFrequency = heartbeatFrequency;
		}

		/**
		 * 返回客户端发送心跳请求的最小频率
		 *
		 * @return 客户端发送心跳请求的最小频率
		 */
		public Duration getMinHeartbeatFrequency() {
			return minHeartbeatFrequency;
		}

		/**
		 * 设置客户端发送心跳请求的最小频率
		 *
		 * @param minHeartbeatFrequency
		 * 		客户端发送心跳请求的最小频率
		 */
		public void setMinHeartbeatFrequency(Duration minHeartbeatFrequency) {
			this.minHeartbeatFrequency = minHeartbeatFrequency;
		}

		/**
		 * 返回服务端监控模式
		 *
		 * @return 服务端监控模式
		 */
		public ServerMonitoringMode getServerMonitoringMode() {
			return serverMonitoringMode;
		}

		/**
		 * 设置服务端监控模式
		 *
		 * @param serverMonitoringMode
		 * 		服务端监控模式
		 */
		public void setServerMonitoringMode(ServerMonitoringMode serverMonitoringMode) {
			this.serverMonitoringMode = serverMonitoringMode;
		}

	}

}
