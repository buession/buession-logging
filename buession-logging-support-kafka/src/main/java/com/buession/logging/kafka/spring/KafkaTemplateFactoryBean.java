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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * {@link KafkaTemplate} 工厂 Bean
 *
 * @param <K>
 * 		Key 类型
 * @param <V>
 * 		值类型
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class KafkaTemplateFactoryBean<K, V> extends KafkaTemplateFactory<K, V>
		implements FactoryBean<KafkaTemplate<K, V>>, InitializingBean {

	protected KafkaTemplate<K, V> kafkaTemplate;

	@Override
	public KafkaTemplate<K, V> getObject() throws Exception {
		return kafkaTemplate;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public Class<KafkaTemplate> getObjectType() {
		return KafkaTemplate.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(kafkaTemplate == null){
			kafkaTemplate = createKafkaTemplate();
		}
	}

}
