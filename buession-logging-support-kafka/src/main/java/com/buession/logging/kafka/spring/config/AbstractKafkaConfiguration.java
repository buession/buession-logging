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
package com.buession.logging.springboot.autoconfigure.kafka;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.KafkaProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(prefix = KafkaProperties.PREFIX, name = "enabled", havingValue = "true")
public class KafkaConfiguration {

	private final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	private final KafkaProperties properties;

	public KafkaConfiguration(LogProperties logProperties) {
		this.properties = logProperties.getKafka();
	}

	@Bean(name = "loggingKafkaProducerFactory")
	@ConditionalOnMissingBean(name = "loggingKafkaProducerFactory")
	public ProducerFactory<String, Object> producerFactory(
			ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
		Assert.isNull(properties.getBootstrapServers(), "Property 'bootstrapServers' is required");

		final DefaultKafkaProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(
				properties.buildProperties());

		propertyMapper.from(properties.getTransactionIdPrefix()).to(producerFactory::setTransactionIdPrefix);
		customizers.orderedStream().forEach((customizer)->customizer.customize(producerFactory));

		return producerFactory;
	}

	@Bean(name = "loggingKafkaProducerListener")
	@ConditionalOnMissingBean(name = "loggingKafkaProducerListener")
	public LoggingProducerListener<String, Object> kafkaProducerListener() {
		return new LoggingProducerListener<>();
	}

	@Bean(name = "loggingKafkaTemplate")
	public KafkaTemplate<String, Object> kafkaTemplate(@Qualifier("loggingKafkaProducerFactory")
													   ObjectProvider<ProducerFactory<String, Object>> producerFactory,
													   @Qualifier("loggingKafkaProducerListener") ProducerListener<String, Object> kafkaProducerListener) {
		final KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory.getIfAvailable());

		kafkaTemplate.setProducerListener(kafkaProducerListener);
		kafkaTemplate.setMessageConverter(new JsonMessageConverter());
		propertyMapper.from(properties::getTopic).to(kafkaTemplate::setDefaultTopic);
		propertyMapper.from(properties::getTransactionIdPrefix).to(kafkaTemplate::setTransactionIdPrefix);

		return kafkaTemplate;
	}

}
