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

import com.buession.core.utils.Assert;
import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.kafka.core.Properties;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.KafkaProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.unit.DataSize;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({KafkaLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = KafkaProperties.PREFIX, name = "enabled", havingValue = "true")
public class KafkaLogHandlerConfiguration extends AbstractLogHandlerConfiguration<KafkaProperties> {

	public KafkaLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getKafka());
	}

	@Bean(name = "loggingKafkaProducerFactory")
	@ConditionalOnMissingBean(name = "loggingKafkaProducerFactory")
	public ProducerFactory<String, Object> producerFactory() {
		Assert.isNull(properties.getBootstrapServers(), "Property 'bootstrapServers' is required");

		final Properties properties = new Properties();

		propertyMapper.from(this.properties::getBootstrapServers)
				.as((bootstrapServers)->StringUtils.join(bootstrapServers, ','))
				.to(properties.in(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
		propertyMapper.from(this.properties::getClientId).to(properties.in(ProducerConfig.CLIENT_ID_CONFIG));
		propertyMapper.from(this.properties::getAcks).to(properties.in(ProducerConfig.ACKS_CONFIG));
		propertyMapper.from(this.properties::getBatchSize).asInt(DataSize::toBytes)
				.to(properties.in(ProducerConfig.BATCH_SIZE_CONFIG));
		propertyMapper.from(this.properties::getBufferMemory).as(DataSize::toBytes)
				.to(properties.in(ProducerConfig.BUFFER_MEMORY_CONFIG));
		propertyMapper.from(this.properties::getCompressionType)
				.to(properties.in(ProducerConfig.COMPRESSION_TYPE_CONFIG));
		propertyMapper.from(this.properties::getRetries).to(properties.in(ProducerConfig.RETRIES_CONFIG));
		propertyMapper.from(this.properties::getSslConfiguration)
				.to(properties.in(ProducerConfig.TRANSACTIONAL_ID_CONFIG));
		propertyMapper.from(StringSerializer.class.getName())
				.to(properties.in(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
		propertyMapper.from(JsonSerializer.class.getName())
				.to(properties.in(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));

		if(this.properties.getSslConfiguration() != null){
			properties.putAll(this.properties.getSslConfiguration().buildProperties());
		}

		if(this.properties.getSecurityConfiguration() != null){
			properties.putAll(this.properties.getSecurityConfiguration().buildProperties());
		}

		if(Validate.isNotEmpty(this.properties.getProperties())){
			properties.putAll(this.properties.getProperties());
		}

		final DefaultKafkaProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(
				properties);

		propertyMapper.from(this.properties.getTransactionIdPrefix()).to(producerFactory::setTransactionIdPrefix);

		return producerFactory;
	}

	@Bean(name = "loggingKafkaKafkaTemplate")
	public KafkaTemplate<String, Object> kafkaTemplate(@Qualifier("loggingKafkaProducerFactory")
													   ObjectProvider<ProducerFactory<String, Object>> producerFactory) {
		final KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory.getIfAvailable());

		kafkaTemplate.setProducerListener(new LoggingProducerListener<>());
		kafkaTemplate.setMessageConverter(new JsonMessageConverter());

		return kafkaTemplate;
	}

	@Bean
	public KafkaLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingKafkaKafkaTemplate") ObjectProvider<KafkaTemplate<String, Object>> kafkaTemplate) {
		final KafkaLogHandlerFactoryBean logHandlerFactoryBean = new KafkaLogHandlerFactoryBean();

		kafkaTemplate.ifAvailable(logHandlerFactoryBean::setKafkaTemplate);

		logHandlerFactoryBean.setTopic(properties.getTopic());

		return logHandlerFactoryBean;
	}

}
