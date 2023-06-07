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
import com.buession.logging.core.handler.LogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka 日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class KafkaLogHandler implements LogHandler {

	/**
	 * {@link KafkaTemplate}
	 */
	private final KafkaTemplate<String, Object> kafkaTemplate;

	/**
	 * Topic 名称
	 */
	private final String topic;

	private final static Logger logger = LoggerFactory.getLogger(KafkaLogHandler.class);

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
	public Status handle(final LogData logData) {
		final Map<String, Object> data = new HashMap<>();

		if(logData.getPrincipal() != null){
			data.put("principal", logData.getPrincipal());
		}
		if(logData.getDateTime() != null){
			data.put("dateTime", logData.getDateTime());
		}
		if(logData.getBusinessType() != null){
			data.put("businessType", logData.getBusinessType().toString());
		}
		if(logData.getEvent() != null){
			data.put("event", logData.getEvent().toString());
		}
		if(logData.getDescription() != null){
			data.put("description", logData.getDescription());
		}
		if(logData.getUrl() != null){
			data.put("url", logData.getUrl());
		}
		if(logData.getRequestMethod() != null){
			data.put("requestMethod", logData.getRequestMethod());
		}
		if(logData.getRequestParameters() != null){
			data.put("requestParameters", logData.getRequestParameters());
		}
		if(logData.getRequestBody() != null){
			data.put("requestBody", logData.getRequestBody());
		}
		if(logData.getClientIp() != null){
			data.put("clientIp", logData.getClientIp());
		}
		if(logData.getRemoteAddr() != null){
			data.put("remoteAddr", logData.getRemoteAddr());
		}
		if(logData.getUserAgent() != null){
			data.put("userAgent", logData.getUserAgent());
		}
		if(logData.getOperatingSystem() != null){
			data.put("operatingSystem", logData.getOperatingSystem());
		}
		if(logData.getDeviceType() != null){
			data.put("deviceType", logData.getDeviceType());
		}
		if(logData.getBrowser() != null){
			data.put("browser", logData.getBrowser());
		}
		if(logData.getLocation() != null){
			data.put("location", logData.getLocation());
		}
		if(logData.getStatus() != null){
			data.put("status", logData.getStatus());
		}
		if(logData.getExtra() != null){
			data.put("extra", logData.getExtra());
		}

		try{
			kafkaTemplate.send(topic, data);
			return Status.SUCCESS;
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

}
