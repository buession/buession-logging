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
package com.buession.logging.springboot.autoconfigure.kafka;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.kafka.spring.KafkaTemplateFactoryBean;
import com.buession.logging.kafka.spring.ProducerFactoryBean;
import com.buession.logging.springboot.autoconfigure.AbstractLogHandlerConfiguration;
import com.buession.logging.springboot.autoconfigure.LogProperties;
import com.buession.logging.springboot.config.KafkaProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

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
@ConditionalOnProperty(prefix = LogProperties.PREFIX, name = "kafka.enabled", havingValue = "true")
public class KafkaLogHandlerConfiguration extends AbstractLogHandlerConfiguration<KafkaProperties,
		KafkaLogHandlerFactoryBean> {

	public KafkaLogHandlerConfiguration(LogProperties logProperties) {
		super(logProperties.getKafka());
	}

	@Bean(name = "loggingKafkaProducerFactory")
	public ProducerFactoryBean producerFactoryBean() {
		final ProducerFactoryBean producerFactoryBean = new ProducerFactoryBean();

		propertyMapper.from(handlerProperties::getBootstrapServers).to(producerFactoryBean::setBootstrapServers);
		propertyMapper.from(handlerProperties::getClientId).to(producerFactoryBean::setClientId);
		propertyMapper.from(handlerProperties::getTransactionIdPrefix)
				.to(producerFactoryBean::setTransactionIdPrefix);
		propertyMapper.from(handlerProperties::getAcks).to(producerFactoryBean::setAcks);
		propertyMapper.from(handlerProperties::getBatchSize).to(producerFactoryBean::setBatchSize);
		propertyMapper.from(handlerProperties::getBufferMemory).to(producerFactoryBean::setBufferMemory);
		propertyMapper.from(handlerProperties::getCompressionType).to(producerFactoryBean::setCompressionType);
		propertyMapper.from(handlerProperties::getRetries).to(producerFactoryBean::setRetries);
		propertyMapper.from(handlerProperties::getSslConfiguration).to(producerFactoryBean::setSslConfiguration);
		propertyMapper.from(handlerProperties::getSecurityConfiguration)
				.to(producerFactoryBean::setSecurityConfiguration);
		propertyMapper.from(handlerProperties::getTransactionIdPrefix).to(producerFactoryBean::setTransactionIdPrefix);
		propertyMapper.from(handlerProperties::getProperties).to(producerFactoryBean::setProperties);

		return producerFactoryBean;
	}

	@Bean(name = "loggingKafkaKafkaTemplate")
	public KafkaTemplateFactoryBean kafkaTemplateFactoryBean(@Qualifier("loggingKafkaProducerFactory")
																	 ObjectProvider<org.springframework.kafka.core.ProducerFactory> producerFactory) {
		final KafkaTemplateFactoryBean kafkaTemplateFactoryBean = new KafkaTemplateFactoryBean();

		producerFactory.ifUnique(kafkaTemplateFactoryBean::setProducerFactory);

		return kafkaTemplateFactoryBean;
	}

	@Bean
	public KafkaLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingKafkaKafkaTemplate") ObjectProvider<KafkaTemplate> kafkaTemplate) {
		final KafkaLogHandlerFactoryBean logHandlerFactoryBean = new KafkaLogHandlerFactoryBean();

		kafkaTemplate.ifUnique(logHandlerFactoryBean::setKafkaTemplate);

		propertyMapper.from(handlerProperties::getTopic).to(logHandlerFactoryBean::setTopic);

		return logHandlerFactoryBean;
	}

}
