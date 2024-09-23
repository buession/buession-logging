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
package com.buession.logging.rest.spring.config;

import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;
import com.buession.logging.support.config.AbstractLogHandlerConfiguration;
import org.springframework.beans.factory.ObjectProvider;

/**
 * Rest 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class AbstractRestLogHandlerConfiguration extends AbstractLogHandlerConfiguration {

	public RestLogHandlerFactoryBean logHandlerFactoryBean(RestLogHandlerFactoryBeanConfigurer configurer,
														   ObjectProvider<HttpClient> httpClient,
														   ObjectProvider<HttpAsyncClient> httpAsyncClient) {
		final RestLogHandlerFactoryBean logHandlerFactoryBean = new RestLogHandlerFactoryBean();

		httpClient.ifAvailable(logHandlerFactoryBean::setHttpClient);
		httpAsyncClient.ifAvailable(logHandlerFactoryBean::setHttpAsyncClient);
		logHandlerFactoryBean.setUrl(configurer.getUrl());
		logHandlerFactoryBean.setRequestMethod(configurer.getRequestMethod());
		logHandlerFactoryBean.setRequestBodyBuilder(configurer.getRequestBodyBuilder());

		return logHandlerFactoryBean;
	}

}
