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

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.kafka.ProducerFactoryCustomizer;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.kafka.spring.config.AbstractKafkaConfiguration;
import com.buession.logging.kafka.spring.config.KafkaConfigurer;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.KafkaProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({KafkaLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = KafkaProperties.PREFIX, name = "enabled", havingValue = "true")
public class KafkaConfiguration extends AbstractKafkaConfiguration {

	private final KafkaProperties kafkaProperties;

	public KafkaConfiguration(LogProperties logProperties) {
		this.kafkaProperties = logProperties.getKafka();
	}

	@Bean(name = "loggingKafkaConfigurer")
	@ConditionalOnMissingBean(name = "loggingKafkaConfigurer")
	public KafkaConfigurer kafkaConfigurer() {
		final KafkaConfigurer configurer = new KafkaConfigurer();

		configurer.setBootstrapServers(kafkaProperties.getBootstrapServers());
		configurer.setConfigs(kafkaProperties.buildProperties());
		configurer.setTransactionIdPrefix(kafkaProperties.getTransactionIdPrefix());

		return configurer;
	}

	@Bean(name = "loggingKafkaProducerFactory")
	@ConditionalOnMissingBean(name = "loggingKafkaProducerFactory")
	@Override
	public ProducerFactory<String, Object> producerFactory(
			@Qualifier("loggingKafkaConfigurer") KafkaConfigurer configurer,
			@Qualifier("loggingKafkaProducerFactoryCustomizer") ObjectProvider<ProducerFactoryCustomizer> producerFactoryCustomizers) {
		return super.producerFactory(configurer, producerFactoryCustomizers);
	}

	@Bean(name = "loggingKafkaProducerListener")
	@ConditionalOnMissingBean(name = "loggingKafkaProducerListener")
	@Override
	public LoggingProducerListener<String, Object> kafkaProducerListener() {
		return super.kafkaProducerListener();
	}

	@Bean(name = "loggingKafkaTemplate")
	@Override
	public KafkaTemplate<String, Object> kafkaTemplate(@Qualifier("loggingKafkaConfigurer") KafkaConfigurer configurer,
													   @Qualifier("loggingKafkaProducerFactory")
													   ProducerFactory<String, Object> producerFactory,
													   @Qualifier("loggingKafkaProducerListener") ProducerListener<String, Object> kafkaProducerListener) {
		return super.kafkaTemplate(configurer, producerFactory, kafkaProducerListener);
	}

}
