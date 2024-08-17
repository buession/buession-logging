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
package com.buession.logging.elasticsearch.spring;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.buession.core.utils.Assert;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * {@link ElasticsearchTemplate} 工厂
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class ElasticsearchTemplateFactory {

	/**
	 * {@link ElasticsearchClient}
	 */
	private ElasticsearchClient client;

	/**
	 * {@link ElasticsearchConverter}
	 */
	private ElasticsearchConverter converter;

	/**
	 * 返回 {@link ElasticsearchClient}
	 *
	 * @return {@link ElasticsearchClient}
	 */
	public ElasticsearchClient getClient() {
		return client;
	}

	/**
	 * 设置 {@link ElasticsearchClient}
	 *
	 * @param client
	 *        {@link ElasticsearchClient}
	 */
	public void setClient(ElasticsearchClient client) {
		this.client = client;
	}

	/**
	 * 返回 {@link ElasticsearchConverter}
	 *
	 * @return {@link ElasticsearchConverter}
	 */
	public ElasticsearchConverter getConverter() {
		return converter;
	}

	/**
	 * 设置 {@link ElasticsearchConverter}
	 *
	 * @param converter
	 *        {@link ElasticsearchConverter}
	 */
	public void setConverter(ElasticsearchConverter converter) {
		this.converter = converter;
	}

	protected ElasticsearchTemplate createElasticsearchTemplate() {
		Assert.isNull(getClient(), "Property 'client' is required");
		return new ElasticsearchTemplate(getClient(), null);
	}

}
