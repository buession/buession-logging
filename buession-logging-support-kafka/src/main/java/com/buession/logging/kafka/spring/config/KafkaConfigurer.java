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
package com.buession.logging.kafka.spring.config;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Map;

/**
 * Configures {@link KafkaTemplate} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class KafkaConfigurer {

	/**
	 * Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster. Applies to all components unless overridden.
	 */
	private List<String> bootstrapServers;

	/**
	 * 配置
	 */
	private Map<String, Object> configs;

	/**
	 * 事务 ID 前缀
	 */
	private String transactionIdPrefix;

	/**
	 * Return Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 *
	 * @return Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 */
	public List<String> getBootstrapServers() {
		return bootstrapServers;
	}

	/**
	 * Sets Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 *
	 * @param bootstrapServers
	 * 		Comma-delimited list of host:port pairs to use for establishing the initial
	 * 		connections to the Kafka cluster.
	 */
	public void setBootstrapServers(List<String> bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	/**
	 * 返回配置
	 *
	 * @return 配置
	 */
	public Map<String, Object> getConfigs() {
		return configs;
	}

	/**
	 * 设置配置
	 *
	 * @param configs
	 * 		配置
	 */
	public void setConfigs(Map<String, Object> configs) {
		this.configs = configs;
	}

	/**
	 * 返回事务 ID 前缀
	 *
	 * @return 事务 ID 前缀
	 */
	public String getTransactionIdPrefix() {
		return transactionIdPrefix;
	}

	/**
	 * 设置事务 ID 前缀
	 *
	 * @param transactionIdPrefix
	 * 		事务 ID 前缀
	 */
	public void setTransactionIdPrefix(String transactionIdPrefix) {
		this.transactionIdPrefix = transactionIdPrefix;
	}

}
