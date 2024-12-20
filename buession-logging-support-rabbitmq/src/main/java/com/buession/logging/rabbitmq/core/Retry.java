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
package com.buession.logging.rabbitmq.core;

import com.buession.logging.rabbitmq.support.RabbitRetryTemplateConfigurer;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

/**
 * 重试配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class Retry extends com.buession.lang.Retry {

	private final static long serialVersionUID = -8889282111487270647L;

	/**
	 * 是否启用重试
	 */
	private boolean enabled;

	/**
	 * {@link RetryTemplate} 配置器
	 *
	 * @since 1.0.0
	 */
	private List<RabbitRetryTemplateConfigurer> retryConfigurers;

	/**
	 * 返回是否启用重试
	 *
	 * @return 是否启用重试
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * 设置是否启用重试
	 *
	 * @param enabled
	 * 		是否启用重试
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 返回 {@link RetryTemplate} 配置器
	 *
	 * @return {@link RetryTemplate} 配置器
	 *
	 * @since 1.0.0
	 */
	public List<RabbitRetryTemplateConfigurer> getRetryCustomizers() {
		return retryConfigurers;
	}

	/**
	 * 设置 {@link RetryTemplate} 配置器
	 *
	 * @param retryConfigurers
	 *        {@link RetryTemplate} 配置器
	 *
	 * @since 1.0.0
	 */
	public void setRetryCustomizers(List<RabbitRetryTemplateConfigurer> retryConfigurers) {
		this.retryConfigurers = retryConfigurers;
	}

}
