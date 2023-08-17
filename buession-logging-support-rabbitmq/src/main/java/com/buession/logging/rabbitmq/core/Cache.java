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
package com.buession.logging.rabbitmq.core;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import java.io.Serializable;
import java.time.Duration;

/**
 * 缓存配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class Cache implements Serializable {

	private final static long serialVersionUID = -8073730230057329751L;

	/**
	 * Channel 缓存
	 */
	private Channel channel;

	/**
	 * 连接缓存
	 */
	private Connection connection;

	/**
	 * 返回 Channel 缓存
	 *
	 * @return Channel 缓存
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * 设置 Channel 缓存
	 *
	 * @param channel
	 * 		Channel 缓存
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * 返回连接缓存
	 *
	 * @return 连接缓存
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * 设置连接缓存
	 *
	 * @param connection
	 * 		连接缓存
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Channel 缓存
	 */
	public static class Channel implements Serializable {

		private final static long serialVersionUID = 4445351391691920081L;

		/**
		 * 每个连接的缓存大小，仅 {@link #checkoutTimeout} &lt; 0 时
		 */
		private Integer size;

		/**
		 * Duration to wait to obtain a channel if the cache size has been reached.
		 * If 0, always create a new channel.
		 */
		private Duration checkoutTimeout;

		/**
		 * 返回每个连接的缓存大小
		 *
		 * @return 每个连接的缓存大小
		 */
		public Integer getSize() {
			return this.size;
		}

		/**
		 * 设置每个连接的缓存大小
		 *
		 * @param size
		 * 		每个连接的缓存大小
		 */
		public void setSize(Integer size) {
			this.size = size;
		}

		/**
		 * Return duration to wait to obtain a channel if the cache size has been reached.
		 *
		 * @return Duration to wait to obtain a channel if the cache size has been reached.
		 */
		public Duration getCheckoutTimeout() {
			return this.checkoutTimeout;
		}

		/**
		 * Sets duration to wait to obtain a channel if the cache size has been reached.
		 *
		 * @param checkoutTimeout
		 * 		Duration to wait to obtain a channel if the cache size has been reached.
		 */
		public void setCheckoutTimeout(Duration checkoutTimeout) {
			this.checkoutTimeout = checkoutTimeout;
		}

	}

	/**
	 * 连接缓存
	 */
	public static class Connection implements Serializable {

		private final static long serialVersionUID = -748010261766848625L;

		/**
		 * 连接工厂缓存模式
		 */
		private CachingConnectionFactory.CacheMode mode = CachingConnectionFactory.CacheMode.CHANNEL;

		/**
		 * 缓存大小，仅适用于 {@link CachingConnectionFactory.CacheMode#CONNECTION}
		 */
		private Integer size;

		/**
		 * 返回连接工厂缓存模式
		 *
		 * @return 连接工厂缓存模式
		 */
		public CachingConnectionFactory.CacheMode getMode() {
			return this.mode;
		}

		/**
		 * 设置连接工厂缓存模式
		 *
		 * @param mode
		 * 		连接工厂缓存模式
		 */
		public void setMode(CachingConnectionFactory.CacheMode mode) {
			this.mode = mode;
		}

		/**
		 * 返回缓存大小
		 *
		 * @return 缓存大小
		 */
		public Integer getSize() {
			return this.size;
		}

		/**
		 * 设置缓存大小
		 *
		 * @param size
		 * 		缓存大小
		 */
		public void setSize(Integer size) {
			this.size = size;
		}

	}

}
