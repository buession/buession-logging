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
package com.buession.logging.core;

import com.buession.lang.DeviceType;
import com.buession.lang.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 日志数据
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class LogData implements Serializable {

	private final static long serialVersionUID = -8050941904962347751L;

	/**
	 * 用户凭证
	 */
	private Principal principal;

	/**
	 * 日期时间
	 */
	private Date dateTime;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 事件
	 */
	private String event;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * Trace ID
	 */
	private String traceId;

	/**
	 * 请求地址
	 */
	private String url;

	/**
	 * 请求方式
	 */
	private RequestMethod requestMethod;

	/**
	 * 请求参数
	 */
	private Map<String, Object> requestParameters;

	/**
	 * 请求体
	 */
	private String requestBody;

	/**
	 * 客户端 IP
	 */
	private String clientIp;

	/**
	 * Remote Addr
	 */
	private String remoteAddr;

	/**
	 * User-Agent
	 */
	private String userAgent;

	/**
	 * 操作系统信息
	 */
	private OperatingSystem operatingSystem;

	/**
	 * 设备类型
	 */
	private DeviceType deviceType;

	/**
	 * 浏览器信息
	 */
	private Browser browser;

	/**
	 * 地理位置信息
	 */
	private GeoLocation location;

	/**
	 * 结果
	 */
	private Status status;

	/**
	 * 附加参数
	 */
	private Map<String, Object> extra;

	/**
	 * 返回用户凭证
	 *
	 * @return 用户凭证
	 */
	public Principal getPrincipal() {
		return principal;
	}

	/**
	 * 设置用户凭证
	 *
	 * @param principal
	 * 		用户凭证
	 */
	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	/**
	 * 返回日期时间
	 *
	 * @return 日期时间
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * 设置日期时间
	 *
	 * @param dateTime
	 * 		日期时间
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * 返回业务类型
	 *
	 * @return 业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置业务类型
	 *
	 * @param businessType
	 * 		业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 返回事件
	 *
	 * @return 事件
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * 设置事件
	 *
	 * @param event
	 * 		事件
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * 返回描述
	 *
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 *
	 * @param description
	 * 		描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 Trace ID
	 *
	 * @return Trace ID
	 */
	public String getTraceId() {
		return traceId;
	}

	/**
	 * 设置 Trace ID
	 *
	 * @param traceId
	 * 		Trace ID
	 */
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	/**
	 * 返回请求地址
	 *
	 * @return 请求地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置请求地址
	 *
	 * @param url
	 * 		请求地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回请求方式
	 *
	 * @return 请求方式
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求方式
	 *
	 * @param requestMethod
	 * 		请求方式
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * 返回请求参数
	 *
	 * @return 请求参数
	 */
	public Map<String, Object> getRequestParameters() {
		return requestParameters;
	}

	/**
	 * 设置请求参数
	 *
	 * @param requestParameters
	 * 		请求参数
	 */
	public void setRequestParameters(Map<String, Object> requestParameters) {
		this.requestParameters = requestParameters;
	}

	/**
	 * 返回请求体
	 *
	 * @return 请求体
	 */
	public String getRequestBody() {
		return requestBody;
	}

	/**
	 * 设置请求体
	 *
	 * @param requestBody
	 * 		请求体
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	/**
	 * 返回客户端 IP
	 *
	 * @return 客户端 IP
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * 设置客户端 IP
	 *
	 * @param clientIp
	 * 		客户端 IP
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * 返回 Remote Addr
	 *
	 * @return Remote Addr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * 设置 Remote Addr
	 *
	 * @param remoteAddr
	 * 		Remote Addr
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	/**
	 * 返回 User-Agent
	 *
	 * @return User-Agent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * 设置 User-Agent
	 *
	 * @param userAgent
	 * 		User-Agent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * 返回操作系统信息
	 *
	 * @return 操作系统信息
	 */
	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	/**
	 * 设置操作系统信息
	 *
	 * @param operatingSystem
	 * 		操作系统信息
	 */
	public void setOperatingSystem(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	/**
	 * 返回设备类型
	 *
	 * @return 设备类型
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * 设置设备类型
	 *
	 * @param deviceType
	 * 		设备类型
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 返回浏览器信息
	 *
	 * @return 浏览器信息
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * 设置浏览器信息
	 *
	 * @param browser
	 * 		浏览器信息
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * 返回地理位置信息
	 *
	 * @return 地理位置信息
	 */
	public GeoLocation getLocation() {
		return location;
	}

	/**
	 * 设置地理位置信息
	 *
	 * @param location
	 * 		地理位置信息
	 */
	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	/**
	 * 返回结果
	 *
	 * @return 结果
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置结果
	 *
	 * @param status
	 * 		结果
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 返回附加参数
	 *
	 * @return 附加参数
	 */
	public Map<String, Object> getExtra() {
		return extra;
	}

	/**
	 * 设置附加参数
	 *
	 * @param extra
	 * 		附加参数
	 */
	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", "LogData => {", "}")
				.add("principal=" + principal)
				.add("dateTime=" + dateTime)
				.add("businessType=" + businessType)
				.add("event=" + event)
				.add("description=" + description)
				.add("traceId=" + traceId)
				.add("url=" + url)
				.add("requestMethod=" + requestMethod)
				.add("requestParameters=" + requestParameters)
				.add("requestBody=" + requestBody)
				.add("clientIp=" + clientIp)
				.add("remoteAddr=" + remoteAddr)
				.add("userAgent=" + userAgent)
				.add("operatingSystem=" + operatingSystem)
				.add("deviceType=" + deviceType)
				.add("browser=" + browser)
				.add("location=" + location)
				.add("status=" + status)
				.add("extra=" + extra)
				.toString();
	}

}
