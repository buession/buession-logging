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
package com.buession.logging.rest.core;

import com.buession.httpclient.core.JsonRawRequestBody;
import com.buession.httpclient.core.RequestBody;
import com.buession.logging.core.LogData;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON 请求体构建器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class JsonRequestBodyBuilder implements RequestBodyBuilder {

	@Override
	public RequestBody<?> build(final LogData logData) {
		return new JsonRawRequestBody<>(buildData(logData));
	}

	private Map<String, Object> buildData(final LogData logData) {
		final Map<String, Object> data = new HashMap<>(32);

		// 用户凭证
		data.put("principal", logData.getPrincipal());

		// 日期时间字段
		data.put("dateTime", logData.getDateTime());

		// 业务类型字段
		data.put("businessType", logData.getBusinessType() == null ? null : logData.getBusinessType().toString());

		// 事件字段
		data.put("event", logData.getEvent() == null ? null : logData.getEvent().toString());

		// 描述字段
		data.put("description", logData.getDescription());

		// 客户端 IP 字段
		data.put("clientIp", logData.getClientIp());

		// Remote Addr 字段
		data.put("remoteAddr", logData.getRemoteAddr());

		// 请求地址字段
		data.put("url", logData.getUrl());

		// 请求方式字段
		data.put("requestMethod", logData.getRequestMethod());

		// 请求参数字段
		data.put("requestParameters", logData.getRequestParameters());

		// 请求体字段
		data.put("requestBody", logData.getRequestBody());

		// User-Agent 字段
		data.put("userAgent", logData.getUserAgent());

		// 操作系统
		data.put("operatingSystem", logData.getOperatingSystem());

		// 设备类型字段
		data.put("deviceType", logData.getDeviceType());

		// 浏览器
		data.put("browser", logData.getBrowser());

		// 地理位置信息字段
		data.put("location", logData.getLocation());

		// 结果字段
		data.put("status", logData.getStatus());

		// 附加参数字段
		data.put("extra", logData.getExtra());

		return data;
	}

}
