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
package com.buession.logging.core.request;

import com.buession.core.utils.Assert;
import com.buession.core.utils.EnumUtils;
import com.buession.core.validator.Validate;
import com.buession.logging.core.RequestMethod;
import com.buession.web.servlet.http.request.RequestUtils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Servlet 请求对象
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ServletRequest extends AbstractRequest {

	private final HttpServletRequest request;

	/**
	 * 构造函数
	 */
	public ServletRequest() {
		final ServletRequestAttributes requestAttributes =
				(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		this.request = requestAttributes.getRequest();
	}

	/**
	 * 构造函数
	 *
	 * @param request
	 *        {@link ServletRequest}
	 */
	public ServletRequest(final HttpServletRequest request) {
		Assert.isNull(request, "HttpServletRequest cloud not be null.");
		this.request = request;
	}

	/**
	 * 构造函数
	 *
	 * @param request
	 *        {@link ServletRequest}
	 * @param clientIpHeaderName
	 * 		客户端 IP 请求头名称
	 */
	public ServletRequest(final HttpServletRequest request, final String clientIpHeaderName) {
		this(request);
		setClientIpHeaderName(clientIpHeaderName);
	}

	@Override
	public String getUrl() {
		String url = request.getRequestURL().toString();

		if(Validate.hasText(url)){
			String queryString = request.getQueryString();

			if(Validate.hasText(queryString)){
				url += "?" + queryString;
			}
		}

		return url;
	}

	@Override
	public RequestMethod getRequestMethod() {
		try{
			return EnumUtils.getEnumIgnoreCase(RequestMethod.class, request.getMethod());
		}catch(Exception e){
			return RequestMethod.GET;
		}
	}

	@Override
	public String getRequestBody() {
		return "";
	}

	@Override
	public Multimap<String, String> getRequestParameters() {
		Map<String, String[]> originalParameters = request.getParameterMap();

		if(originalParameters == null){
			return null;
		}

		Multimap<String, String> parameters = HashMultimap.create();
		originalParameters.forEach((name, value)->{
			if(value != null){
				for(String v : value){
					parameters.put(name, v);
				}
			}else{
				parameters.put(name, null);
			}
		});

		return parameters;
	}

	@Override
	public String getClientIp() {
		String clientIp = null;

		if(Validate.isNotBlank(getClientIpHeaderName())){
			clientIp = request.getHeader(getClientIpHeaderName());
		}
		return Validate.hasText(clientIp) && "unknown".equalsIgnoreCase(clientIp) == false ? clientIp :
				RequestUtils.getClientIp(request);
	}

	@Override
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}

	@Override
	public String getUserAgent() {
		return request.getHeader("User-Agent");
	}

}
