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
package com.buession.logging.kafka.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class KafkaLogHandler extends AbstractLogHandler {

	/**
	 * {@link KafkaTemplate}
	 */
	private final KafkaTemplate<String, Object> kafkaTemplate;

	/**
	 * Topic 名称
	 */
	private final String topic;

	/**
	 * 构造函数
	 *
	 * @param kafkaTemplate
	 *        {@link KafkaTemplate}
	 * @param topic
	 * 		Topic 名称
	 */
	public KafkaLogHandler(final KafkaTemplate<String, Object> kafkaTemplate, final String topic) {
		Assert.isNull(kafkaTemplate, "KafkaTemplate is null.");
		Assert.isBlank(topic, "Topic name is blank, empty or null.");
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}

	@Override
	protected Status doHandle(final LogData logData) throws Exception {
		kafkaTemplate.send(topic, logData);
		return Status.SUCCESS;
	}

}
