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
package com.buession.logging.springboot.autoconfigure;

import com.buession.logging.springboot.config.ConsoleProperties;
import com.buession.logging.springboot.config.ElasticsearchProperties;
import com.buession.logging.springboot.config.FileProperties;
import com.buession.logging.springboot.config.JdbcProperties;
import com.buession.logging.springboot.config.KafkaProperties;
import com.buession.logging.springboot.config.MongoProperties;
import com.buession.logging.springboot.config.RabbitProperties;
import com.buession.logging.springboot.config.RestProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
@ConfigurationProperties(prefix = LogProperties.PREFIX)
public class LogProperties {

	public final static String PREFIX = "spring.logging";

	/**
	 * 客户端 IP 请求头名称
	 */
	private String clientIpHeaderName;

	/**
	 * 控制台日志配置
	 *
	 * @since 0.0.4
	 */
	private ConsoleProperties console;

	/**
	 * Elasticsearch 日志配置
	 */
	private ElasticsearchProperties elasticsearch;

	/**
	 * 文件日志配置
	 */
	private FileProperties file;

	/**
	 * JDBC 日志配置
	 */
	private JdbcProperties jdbc;

	/**
	 * Kafka 日志配置
	 */
	private KafkaProperties kafka;

	/**
	 * MongoDB 日志配置
	 */
	private MongoProperties mongo;

	/**
	 * RabbitMQ 日志配置
	 */
	private RabbitProperties rabbit;

	/**
	 * Rest 日志配置
	 */
	private RestProperties rest;

	/**
	 * 返回客户端 IP 请求头名称
	 *
	 * @return 客户端 IP 请求头名称
	 */
	public String getClientIpHeaderName() {
		return clientIpHeaderName;
	}

	/**
	 * 设置客户端 IP 请求头名称
	 *
	 * @param clientIpHeaderName
	 * 		客户端 IP 请求头名称
	 */
	public void setClientIpHeaderName(String clientIpHeaderName) {
		this.clientIpHeaderName = clientIpHeaderName;
	}

	/**
	 * 返回控制台日志配置
	 *
	 * @return 控制台日志配置
	 */
	public ConsoleProperties getConsole() {
		return console;
	}

	/**
	 * 设置控制台日志配置
	 *
	 * @param console
	 * 		控制台日志配置
	 */
	public void setConsole(ConsoleProperties console) {
		this.console = console;
	}

	/**
	 * 返回 Elasticsearch 日志配置
	 *
	 * @return Elasticsearch 日志配置
	 */
	public ElasticsearchProperties getElasticsearch() {
		return elasticsearch;
	}

	/**
	 * 设置 Elasticsearch 日志配置
	 *
	 * @param elasticsearch
	 * 		Elasticsearch 日志配置
	 */
	public void setElasticsearch(ElasticsearchProperties elasticsearch) {
		this.elasticsearch = elasticsearch;
	}

	/**
	 * 返回文件日志配置
	 *
	 * @return 文件日志配置
	 */
	public FileProperties getFile() {
		return file;
	}

	/**
	 * 设置文件日志配置
	 *
	 * @param file
	 * 		文件日志配置
	 */
	public void setFile(FileProperties file) {
		this.file = file;
	}

	/**
	 * 返回 JDBC 日志配置
	 *
	 * @return JDBC 日志配置
	 */
	public JdbcProperties getJdbc() {
		return jdbc;
	}

	/**
	 * 设置 JDBC 日志配置
	 *
	 * @param jdbc
	 * 		JDBC 日志配置
	 */
	public void setJdbc(JdbcProperties jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 返回 Kafka 日志配置
	 *
	 * @return Kafka 日志配置
	 */
	public KafkaProperties getKafka() {
		return kafka;
	}

	/**
	 * 设置 Kafka 日志配置
	 *
	 * @param kafka
	 * 		Kafka 日志配置
	 */
	public void setKafka(KafkaProperties kafka) {
		this.kafka = kafka;
	}

	/**
	 * 返回 MongoDB 日志配置
	 *
	 * @return MongoDB 日志配置
	 */
	public MongoProperties getMongo() {
		return mongo;
	}

	/**
	 * 设置 MongoDB 日志配置
	 *
	 * @param mongo
	 * 		MongoDB 日志配置
	 */
	public void setMongo(MongoProperties mongo) {
		this.mongo = mongo;
	}

	/**
	 * 返回 RabbitMQ 日志配置
	 *
	 * @return RabbitMQ 日志配置
	 */
	public RabbitProperties getRabbit() {
		return rabbit;
	}

	/**
	 * 设置 RabbitMQ 日志配置
	 *
	 * @param rabbit
	 * 		RabbitMQ 日志配置
	 */
	public void setRabbit(RabbitProperties rabbit) {
		this.rabbit = rabbit;
	}

	/**
	 * 返回 Rest 日志配置
	 *
	 * @return Rest 日志配置
	 */
	public RestProperties getRest() {
		return rest;
	}

	/**
	 * 设置 Rest 日志配置
	 *
	 * @param rest
	 * 		Rest 日志配置
	 */
	public void setRest(RestProperties rest) {
		this.rest = rest;
	}

}
