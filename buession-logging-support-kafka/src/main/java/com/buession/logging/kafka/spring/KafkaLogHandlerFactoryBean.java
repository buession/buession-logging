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
package com.buession.logging.kafka.spring;

import com.buession.logging.kafka.handler.KafkaLogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka 日志处理器 {@link KafkaLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class KafkaLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<KafkaLogHandler> {

	/**
	 * {@link KafkaTemplate}
	 */
	private KafkaTemplate<?, Object> kafkaTemplate;

	/**
	 * Topic 名称
	 */
	private String topic;

	/**
	 * 返回 {@link KafkaTemplate}
	 *
	 * @return {@link KafkaTemplate}
	 */
	public KafkaTemplate<?, Object> getKafkaTemplate() {
		return kafkaTemplate;
	}

	/**
	 * 设置 {@link KafkaTemplate}
	 *
	 * @param kafkaTemplate
	 *        {@link KafkaTemplate}
	 */
	public void setKafkaTemplate(KafkaTemplate<?, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	/**
	 * 返回 Topic 名称
	 *
	 * @return Topic 名称
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 设置 Topic 名称
	 *
	 * @param topic
	 * 		Topic 名称
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logHandler = new KafkaLogHandler(kafkaTemplate, topic);
	}

}
