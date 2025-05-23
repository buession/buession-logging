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
package com.buession.logging.elasticsearch;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;

/**
 * Callback interface that can be implemented by beans wishing to further customize the
 * {@link org.elasticsearch.client.RestClient} through a {@link RestClientBuilder} whilst
 * retaining default auto-configuration.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@FunctionalInterface
public interface RestClientBuilderCustomizer {

	/**
	 * Customize the {@link RestClientBuilder}.
	 * <p>
	 * Possibly overrides customizations made with the {@code "spring.elasticsearch.rest"}
	 * configuration properties namespace. For more targeted changes, see
	 * {@link #customize(HttpAsyncClientBuilder)} and
	 * {@link #customize(RequestConfig.Builder)}.
	 *
	 * @param builder
	 * 		the builder to customize
	 */
	void customize(RestClientBuilder builder);

	/**
	 * Customize the {@link HttpAsyncClientBuilder}.
	 *
	 * @param builder
	 * 		the builder
	 */
	default void customize(HttpAsyncClientBuilder builder) {
	}

	/**
	 * Customize the {@link RequestConfig.Builder}.
	 *
	 * @param builder
	 * 		the builder
	 */
	default void customize(RequestConfig.Builder builder) {
	}

}
