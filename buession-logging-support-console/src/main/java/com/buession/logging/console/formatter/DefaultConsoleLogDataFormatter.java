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
package com.buession.logging.console.formatter;

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.lang.Constants;
import com.buession.lang.Geo;
import com.buession.logging.core.LogData;

import java.util.Map;

/**
 * 默认控制台日志格式化
 *
 * @author Yong.Teng
 * @since 0.0.4
 */
public class DefaultConsoleLogDataFormatter implements ConsoleLogDataFormatter<String> {

	@Override
	public String format(final LogData logData) {
		return logData == null ? null : logData.toString();
	}

	@Override
	public String format(final String template, final LogData logData) {
		if(logData == null){
			return null;
		}

		String message = template;

		message = replace(message, "principal", logData.getPrincipal().toString());
		message = replace(message, "uid", logData.getPrincipal().getId());
		message = replace(message, "username", logData.getPrincipal().getUserName());

		String loginDateTime = logData.getDateTime().toString();
		message = replace(message, "date_time", loginDateTime);
		message = replace(message, "datetime", loginDateTime);

		message = replace(message, "businessType", logData.getBusinessType());
		message = replace(message, "event", logData.getEvent());
		message = replace(message, "description", logData.getDescription());
		message = replace(message, "trace_id", logData.getTraceId());
		message = replace(message, "traceId", logData.getTraceId());
		message = replace(message, "url", logData.getUrl());
		message = replace(message, "requestMethod", logData.getRequestMethod().name());
		message = replace(message, "request_method", logData.getRequestMethod().name());
		message = replace(message, "method", logData.getRequestMethod().name());
		message = replace(message, "request_parameters", buildMap(logData.getRequestParameters()));
		message = replace(message, "request_body", logData.getRequestBody());
		message = replace(message, "client_ip", logData.getClientIp());
		message = replace(message, "remoteAddr", logData.getRemoteAddr());
		message = replace(message, "remote_addr", logData.getRemoteAddr());
		message = replace(message, "userAgent", logData.getUserAgent());
		message = replace(message, "user-agent", logData.getUserAgent());
		message = replace(message, "user_agent", logData.getUserAgent());

		String operatingSystem = logData.getOperatingSystem().toString();
		message = replace(message, "operating_system", operatingSystem);
		message = replace(message, "os", operatingSystem);
		message = replace(message, "operating_system_name", logData.getOperatingSystem().getName());
		message = replace(message, "os_name", logData.getOperatingSystem().getName());
		message = replace(message, "operating_system_version", logData.getOperatingSystem().getVersion());
		message = replace(message, "os_version", logData.getOperatingSystem().getVersion());

		message = replace(message, "device_type", logData.getDeviceType().getName());

		message = replace(message, "browser", logData.getBrowser().toString());
		message = replace(message, "browser_name", logData.getBrowser().getName());
		message = replace(message, "browser_version", logData.getBrowser().getVersion());
		message = replace(message, "browser_type", logData.getBrowser().getType().name());

		message = replace(message, "location", logData.getLocation().toString());
		message = replace(message, "geo", buildGeo(logData.getLocation().getGeo()));
		message = replace(message, "country", logData.getLocation().getCountry().toString());
		message = replace(message, "country_code", logData.getLocation().getCountry().getCode());
		message = replace(message, "country_name", logData.getLocation().getCountry().getName());
		message = replace(message, "country_full_name", logData.getLocation().getCountry().getFullName());
		message = replace(message, "district", logData.getLocation().getDistrict().toString());
		message = replace(message, "district_name", logData.getLocation().getDistrict().getName());
		message = replace(message, "district_full_name", logData.getLocation().getDistrict().getFullName());

		message = replace(message, "status", logData.getStatus() == null ? Constants.EMPTY_STRING :
				logData.getStatus().name());

		message = replace(message, "extra", buildMap(logData.getExtra()));

		return message;
	}

	protected static String replace(final String message, final String varName, final String value) {
		return Validate.hasText(varName) ? StringUtils.replace(message, "${" + varName + '}', value)
				: message;
	}

	protected static String buildMap(final Map<String, Object> parameters) {
		if(parameters == null){
			return Constants.EMPTY_STRING;
		}else{
			final StringBuilder sb = new StringBuilder();
			int i = 0;

			for(Map.Entry<String, Object> parameter : parameters.entrySet()){
				if(i++ > 0){
					sb.append('&');
				}

				sb.append(parameter.getKey()).append('=').append(parameter.getValue());
			}

			return sb.toString();
		}
	}

	protected static String buildGeo(final Geo geo) {
		return geo == null ? Constants.EMPTY_STRING : geo.toString();
	}

}
