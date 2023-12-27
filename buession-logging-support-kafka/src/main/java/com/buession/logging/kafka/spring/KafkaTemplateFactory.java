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

import com.buession.core.utils.Assert;
import com.buession.logging.kafka.core.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;

/**
 * {@link KafkaTemplate} 工厂
 *
 * @param <K>
 * 		Key 类型
 * @param <V>
 * 		值类型
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class KafkaTemplateFactory<K, V> {

	private ProducerFactory<K, V> producerFactory;

	public ProducerFactory<K, V> getProducerFactory() {
		return producerFactory;
	}

	public void setProducerFactory(ProducerFactory<K, V> producerFactory) {
		this.producerFactory = producerFactory;
	}

	protected KafkaTemplate<K, V> createKafkaTemplate() {
		Assert.isNull(getProducerFactory(), "Property 'producerFactory' is required");
		
		final KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(getProducerFactory());

		kafkaTemplate.setDefaultTopic(Constants.DEFAULT_TOPIC);
		kafkaTemplate.setProducerListener(new LoggingProducerListener<>());
		kafkaTemplate.setMessageConverter(new JsonMessageConverter());

		return kafkaTemplate;
	}

}
