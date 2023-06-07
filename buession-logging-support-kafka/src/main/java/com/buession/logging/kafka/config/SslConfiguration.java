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
import com.buession.core.utils.StringUtils;
import com.buession.logging.kafka.core.Properties;
import org.apache.kafka.common.config.SslConfigs;

import java.io.Serializable;
import java.util.Map;

/**
 * SSL 配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class SslConfiguration extends com.buession.logging.core.SslConfiguration
		implements KafkaConfiguration, Serializable {

	private final static long serialVersionUID = -6202862395424350713L;

	@Override
	public Map<String, Object> buildProperties() {
		final Properties properties = new Properties();
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(this::getProtocol).to(properties.in(SslConfigs.SSL_PROTOCOL_CONFIG));
		propertyMapper.from(this::getKeyStorePath).to(properties.in(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG));
		propertyMapper.from(this::getKeyStoreType).to(properties.in(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG));
		propertyMapper.from(this::getKeyPassword).to(properties.in(SslConfigs.SSL_KEY_PASSWORD_CONFIG));
		propertyMapper.from(this::getKeyStorePassword).to(properties.in(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG));
		propertyMapper.from(this::getTrustStorePath).to(properties.in(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG));
		propertyMapper.from(this::getTrustStoreType).to(properties.in(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG));
		propertyMapper.from(this::getTrustStorePassword).to(properties.in(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG));
		propertyMapper.from(this::getAlgorithms).as((v)->StringUtils.join(v, ','))
				.to(properties.in(SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG));

		return properties;
	}

}
