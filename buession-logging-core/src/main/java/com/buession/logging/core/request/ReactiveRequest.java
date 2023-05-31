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
import com.buession.core.validator.Validate;
import com.buession.logging.core.RequestMethod;
import com.buession.web.reactive.context.request.ReactiveRequestAttributes;
import com.buession.web.reactive.http.request.RequestUtils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Objects;

/**
 * WebFlux 请求对象
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ReactiveRequest extends AbstractRequest {

	private final ServerHttpRequest request;

	/**
	 * 构造函数
	 */
	public ReactiveRequest() {
		final ReactiveRequestAttributes requestAttributes =
				(ReactiveRequestAttributes) RequestContextHolder.currentRequestAttributes();
		this.request = requestAttributes.getRequest();
	}

	/**
	 * 构造函数
	 *
	 * @param request
	 *        {@link ServletRequest}
	 */
	public ReactiveRequest(final ServerHttpRequest request) {
		Assert.isNull(request, "ServerHttpRequest cloud not be null.");
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
	public ReactiveRequest(final ServerHttpRequest request, final String clientIpHeaderName) {
		this(request);
		setClientIpHeaderName(clientIpHeaderName);
	}

	@Override
	public String getUrl() {
		return request.getURI().toString();
	}

	@Override
	public RequestMethod getRequestMethod() {
		try{
			switch(Objects.requireNonNull(request.getMethod())){
				case GET:
					return RequestMethod.GET;
				case HEAD:
					return RequestMethod.HEAD;
				case POST:
					return RequestMethod.POST;
				case PUT:
					return RequestMethod.PUT;
				case PATCH:
					return RequestMethod.PATCH;
				case DELETE:
					return RequestMethod.DELETE;
				case OPTIONS:
					return RequestMethod.OPTIONS;
				case TRACE:
					return RequestMethod.TRACE;
				default:
					return RequestMethod.GET;
			}
		}catch(Exception e){
			return RequestMethod.GET;
		}
	}

	@Override
	public String getRequestBody() {
		return request.getBody().toString();
	}

	@Override
	public Multimap<String, String> getRequestParameters() {
		MultiValueMap<String, String> originalParameters = request.getQueryParams();

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
		if(Validate.isNotBlank(getClientIpHeaderName())){
			List<String> values = request.getHeaders().get(getClientIpHeaderName());
			if(values != null){
				for(String ip : values){
					if(Validate.hasText(ip) && "unknown".equalsIgnoreCase(ip) == false){
						return ip;
					}
				}
			}
		}

		return RequestUtils.getClientIp(request);
	}

	@Override
	public String getRemoteAddr() {
		return request.getRemoteAddress() == null ? "127.0.0.1" :
				request.getRemoteAddress().getAddress().getHostAddress();
	}

	@Override
	public String getUserAgent() {
		return request.getHeaders().get("User-Agent").get(0);
	}

}
