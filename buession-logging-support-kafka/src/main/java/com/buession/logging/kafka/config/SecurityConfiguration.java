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
package com.buession.logging.kafka.config;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.kafka.core.Properties;
import org.apache.kafka.clients.CommonClientConfigs;

import java.io.Serializable;
import java.util.Map;

/**
 * 安全配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class SecurityConfiguration implements KafkaConfiguration, Serializable {

	private final static long serialVersionUID = -3308975729408498102L;

	/**
	 * Security protocol used to communicate with brokers.
	 */
	private String protocol;

	/**
	 * Return security protocol used to communicate with brokers.
	 *
	 * @return Security protocol used to communicate with brokers.
	 */
	public String getProtocol(){
		return this.protocol;
	}

	/**
	 * Sets security protocol used to communicate with brokers.
	 *
	 * @param protocol
	 * 		Security protocol used to communicate with brokers.
	 */
	public void setProtocol(String protocol){
		this.protocol = protocol;
	}

	@Override
	public Map<String, Object> buildProperties(){
		Properties properties = new Properties();
		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(this::getProtocol).to(properties.in(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG));

		return properties;
	}

}
