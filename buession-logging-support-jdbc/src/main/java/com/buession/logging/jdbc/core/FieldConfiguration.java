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
package com.buession.logging.jdbc.core;

import java.io.Serializable;

/**
 * 字段配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class FieldConfiguration implements Serializable {

	private final static long serialVersionUID = -7995010538645770290L;

	/**
	 * 主键字段名称
	 */
	private String idFieldName;

	/**
	 * 用户 ID 字段名称
	 */
	private String userIdFieldName;

	/**
	 * 用户名字段名称
	 */
	private String userNameFieldName;

	/**
	 * 真实姓名字段名称
	 */
	private String realNameFieldName;

	/**
	 * 日期时间字段名称
	 */
	private String dateTimeFieldName;

	/**
	 * 业务类型字段名称
	 */
	private String businessTypeFieldName;

	/**
	 * 事件字段名称
	 */
	private String eventFieldName;

	/**
	 * 描述字段名称
	 */
	private String descriptionFieldName;

	/**
	 * 客户端 IP 字段名称
	 */
	private String clientIpFieldName;

	/**
	 * Remote Addr 字段名称
	 */
	private String remoteAddrFieldName;

	/**
	 * 请求地址字段名称
	 */
	private String urlFieldName;

	/**
	 * 请求方式字段名称
	 */
	private String requestMethodFieldName;

	/**
	 * 请求参数字段名称
	 */
	private String requestParametersFieldName;

	/**
	 * 请求体字段名称
	 */
	private String requestBodyFieldName;

	/**
	 * 响应体字段名称
	 */
	private String responseBodyFieldName;

	/**
	 * User-Agent 字段名称
	 */
	private String userAgentFieldName;

	/**
	 * 操作系统名称字段名称
	 */
	private String operatingSystemNameFieldName;

	/**
	 * 操作系统版本字段名称
	 */
	private String operatingSystemVersionFieldName;

	/**
	 * 设备类型字段名称
	 */
	private String deviceTypeFieldName;

	/**
	 * 浏览器名称字段名称
	 */
	private String browserNameFieldName;

	/**
	 * 浏览器类型字段名称
	 */
	private String browserTypeFieldName;

	/**
	 * 浏览器版本字段名称
	 */
	private String browserVersionFieldName;

	/**
	 * 地理位置信息字段名称
	 */
	private String locationFieldName;

	/**
	 * 国家 Code 字段名称
	 */
	private String countryCodeFieldName;

	/**
	 * 国家名称字段名称
	 */
	private String countryNameFieldName;

	/**
	 * 国家名称全称字段名称
	 */
	private String countryFullNameFieldName;

	/**
	 * 地区名称字段名称
	 */
	private String districtNameFieldName;

	/**
	 * 地区名称全称字段名称
	 */
	private String districtFullNameFieldName;

	/**
	 * 结果字段名称
	 */
	private String statusFieldName;

	/**
	 * 附加参数字段名称
	 */
	private String extraFieldName;

	/**
	 * 返回主键字段名称
	 *
	 * @return 主键字段名称
	 */
	public String getIdFieldName() {
		return idFieldName;
	}

	/**
	 * 返回主键字段名称
	 *
	 * @param idFieldName
	 * 		主键字段名称
	 */
	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}

	/**
	 * 设置用户 ID 字段名称
	 *
	 * @return 用户 ID 字段名称
	 */
	public String getUserIdFieldName() {
		return userIdFieldName;
	}

	/**
	 * 返回用户 ID 字段名称
	 *
	 * @param userIdFieldName
	 * 		用户 ID 字段名称
	 */
	public void setUserIdFieldName(String userIdFieldName) {
		this.userIdFieldName = userIdFieldName;
	}

	/**
	 * 返回用户名字段名称
	 *
	 * @return 用户名字段名称
	 */
	public String getUserNameFieldName() {
		return userNameFieldName;
	}

	/**
	 * 设置用户名字段名称
	 *
	 * @param userNameFieldName
	 * 		用户名字段名称
	 */
	public void setUserNameFieldName(String userNameFieldName) {
		this.userNameFieldName = userNameFieldName;
	}

	/**
	 * 返回真实姓名字段名称
	 *
	 * @return 真实姓名字段名称
	 */
	public String getRealNameFieldName() {
		return realNameFieldName;
	}

	/**
	 * 返回真实姓名字段名称
	 *
	 * @param realNameFieldName
	 * 		真实姓名字段名称
	 */
	public void setRealNameFieldName(String realNameFieldName) {
		this.realNameFieldName = realNameFieldName;
	}

	/**
	 * 返回日期时间字段名称
	 *
	 * @return 日期时间字段名称
	 */
	public String getDateTimeFieldName() {
		return dateTimeFieldName;
	}

	/**
	 * 设置日期时间字段名称
	 *
	 * @param dateTimeFieldName
	 * 		日期时间字段名称
	 */
	public void setDateTimeFieldName(String dateTimeFieldName) {
		this.dateTimeFieldName = dateTimeFieldName;
	}

	/**
	 * 返回业务类型字段名称
	 *
	 * @return 业务类型字段名称
	 */
	public String getBusinessTypeFieldName() {
		return businessTypeFieldName;
	}

	/**
	 * 设置业务类型字段名称
	 *
	 * @param businessTypeFieldName
	 * 		业务类型字段名称
	 */
	public void setBusinessTypeFieldName(String businessTypeFieldName) {
		this.businessTypeFieldName = businessTypeFieldName;
	}

	/**
	 * 返回事件字段名称
	 *
	 * @return 事件字段名称
	 */
	public String getEventFieldName() {
		return eventFieldName;
	}

	/**
	 * 设置事件字段名称
	 *
	 * @param eventFieldName
	 * 		事件字段名称
	 */
	public void setEventFieldName(String eventFieldName) {
		this.eventFieldName = eventFieldName;
	}

	/**
	 * 返回描述字段名称
	 *
	 * @return 描述字段名称
	 */
	public String getDescriptionFieldName() {
		return descriptionFieldName;
	}

	/**
	 * 设置描述字段名称
	 *
	 * @param descriptionFieldName
	 * 		描述字段名称
	 */
	public void setDescriptionFieldName(String descriptionFieldName) {
		this.descriptionFieldName = descriptionFieldName;
	}

	/**
	 * 返回客户端 IP 字段名称
	 *
	 * @return 客户端 IP 字段名称
	 */
	public String getClientIpFieldName() {
		return clientIpFieldName;
	}

	/**
	 * 设置客户端 IP 字段名称
	 *
	 * @param clientIpFieldName
	 * 		客户端 IP 字段名称
	 */
	public void setClientIpFieldName(String clientIpFieldName) {
		this.clientIpFieldName = clientIpFieldName;
	}

	/**
	 * 返回 Remote Addr 字段名称
	 *
	 * @return Remote Addr 字段名称
	 */
	public String getRemoteAddrFieldName() {
		return remoteAddrFieldName;
	}

	/**
	 * 设置 Remote Addr 字段名称
	 *
	 * @param remoteAddrFieldName
	 * 		Remote Addr 字段名称
	 */
	public void setRemoteAddrFieldName(String remoteAddrFieldName) {
		this.remoteAddrFieldName = remoteAddrFieldName;
	}

	/**
	 * 返回请求地址字段名称
	 *
	 * @return 请求地址字段名称
	 */
	public String getUrlFieldName() {
		return urlFieldName;
	}

	/**
	 * 设置请求地址字段名称
	 *
	 * @param urlFieldName
	 * 		请求地址字段名称
	 */
	public void setUrlFieldName(String urlFieldName) {
		this.urlFieldName = urlFieldName;
	}

	/**
	 * 返回请求方式字段名称
	 *
	 * @return 请求方式字段名称
	 */
	public String getRequestMethodFieldName() {
		return requestMethodFieldName;
	}

	/**
	 * 设置请求方式字段名称
	 *
	 * @param requestMethodFieldName
	 * 		请求方式字段名称
	 */
	public void setRequestMethodFieldName(String requestMethodFieldName) {
		this.requestMethodFieldName = requestMethodFieldName;
	}

	/**
	 * 返回请求参数字段名称
	 *
	 * @return 请求参数字段名称
	 */
	public String getRequestParametersFieldName() {
		return requestParametersFieldName;
	}

	/**
	 * 设置请求参数字段名称
	 *
	 * @param requestParametersFieldName
	 * 		请求参数字段名称
	 */
	public void setRequestParametersFieldName(String requestParametersFieldName) {
		this.requestParametersFieldName = requestParametersFieldName;
	}

	/**
	 * 返回请求体字段名称
	 *
	 * @return 请求体字段名称
	 */
	public String getRequestBodyFieldName() {
		return requestBodyFieldName;
	}

	/**
	 * 设置请求体字段名称
	 *
	 * @param requestBodyFieldName
	 * 		请求体字段名称
	 */
	public void setRequestBodyFieldName(String requestBodyFieldName) {
		this.requestBodyFieldName = requestBodyFieldName;
	}

	/**
	 * 返回响应体字段名称
	 *
	 * @return 响应体字段名称
	 */
	public String getResponseBodyFieldName() {
		return responseBodyFieldName;
	}

	/**
	 * 设置响应体字段名称
	 *
	 * @param responseBodyFieldName
	 * 		响应体字段名称
	 */
	public void setResponseBodyFieldName(String responseBodyFieldName) {
		this.responseBodyFieldName = responseBodyFieldName;
	}

	/**
	 * 返回 User-Agent 字段名称
	 *
	 * @return User-Agent 字段名称
	 */
	public String getUserAgentFieldName() {
		return userAgentFieldName;
	}

	/**
	 * 设置 User-Agent 字段名称
	 *
	 * @param userAgentFieldName
	 * 		User-Agent 字段名称
	 */
	public void setUserAgentFieldName(String userAgentFieldName) {
		this.userAgentFieldName = userAgentFieldName;
	}

	/**
	 * 返回操作系统名称字段名称
	 *
	 * @return 操作系统名称字段名称
	 */
	public String getOperatingSystemNameFieldName() {
		return operatingSystemNameFieldName;
	}

	/**
	 * 设置操作系统名称字段名称
	 *
	 * @param operatingSystemNameFieldName
	 * 		操作系统名称字段名称
	 */
	public void setOperatingSystemNameFieldName(String operatingSystemNameFieldName) {
		this.operatingSystemNameFieldName = operatingSystemNameFieldName;
	}

	/**
	 * 返回操作系统版本字段名称
	 *
	 * @return 操作系统版本字段名称
	 */
	public String getOperatingSystemVersionFieldName() {
		return operatingSystemVersionFieldName;
	}

	/**
	 * 设置操作系统版本字段名称
	 *
	 * @param operatingSystemVersionFieldName
	 * 		操作系统版本字段名称
	 */
	public void setOperatingSystemVersionFieldName(String operatingSystemVersionFieldName) {
		this.operatingSystemVersionFieldName = operatingSystemVersionFieldName;
	}

	/**
	 * 返回设备类型字段名称
	 *
	 * @return 设备类型字段名称
	 */
	public String getDeviceTypeFieldName() {
		return deviceTypeFieldName;
	}

	/**
	 * 设置设备类型字段名称
	 *
	 * @param deviceTypeFieldName
	 * 		设备类型字段名称
	 */
	public void setDeviceTypeFieldName(String deviceTypeFieldName) {
		this.deviceTypeFieldName = deviceTypeFieldName;
	}

	/**
	 * 返回浏览器名称字段名称
	 *
	 * @return 浏览器名称字段名称
	 */
	public String getBrowserNameFieldName() {
		return browserNameFieldName;
	}

	/**
	 * 设置浏览器名称字段名称
	 *
	 * @param browserNameFieldName
	 * 		浏览器名称字段名称
	 */
	public void setBrowserNameFieldName(String browserNameFieldName) {
		this.browserNameFieldName = browserNameFieldName;
	}

	/**
	 * 返回浏览器类型字段名称
	 *
	 * @return 浏览器类型字段名称
	 */
	public String getBrowserTypeFieldName() {
		return browserTypeFieldName;
	}

	/**
	 * 设置浏览器类型字段名称
	 *
	 * @param browserTypeFieldName
	 * 		浏览器类型字段名称
	 */
	public void setBrowserTypeFieldName(String browserTypeFieldName) {
		this.browserTypeFieldName = browserTypeFieldName;
	}

	/**
	 * 返回浏览器版本字段名称
	 *
	 * @return 浏览器版本字段名称
	 */
	public String getBrowserVersionFieldName() {
		return browserVersionFieldName;
	}

	/**
	 * 设置浏览器版本字段名称
	 *
	 * @param browserVersionFieldName
	 * 		浏览器版本字段名称
	 */
	public void setBrowserVersionFieldName(String browserVersionFieldName) {
		this.browserVersionFieldName = browserVersionFieldName;
	}


	/**
	 * 返回地理位置信息字段名称
	 *
	 * @return 地理位置信息字段名称
	 */
	public String getLocationFieldName() {
		return locationFieldName;
	}

	/**
	 * 设置地理位置信息字段名称
	 *
	 * @param locationFieldName
	 * 		地理位置信息字段名称
	 */
	public void setLocationFieldName(String locationFieldName) {
		this.locationFieldName = locationFieldName;
	}

	/**
	 * 返回国家 Code 字段名称
	 *
	 * @return 国家 Code 字段名称
	 */
	public String getCountryCodeFieldName() {
		return countryCodeFieldName;
	}

	/**
	 * 设置国家 Code 字段名称
	 *
	 * @param countryCodeFieldName
	 * 		国家 Code 字段名称
	 */
	public void setCountryCodeFieldName(String countryCodeFieldName) {
		this.countryCodeFieldName = countryCodeFieldName;
	}

	/**
	 * 返回国家名称字段名称
	 *
	 * @return 国家名称字段名称
	 */
	public String getCountryNameFieldName() {
		return countryNameFieldName;
	}

	/**
	 * 设置国家名称字段名称
	 *
	 * @param countryNameFieldName
	 * 		国家名称字段名称
	 */
	public void setCountryNameFieldName(String countryNameFieldName) {
		this.countryNameFieldName = countryNameFieldName;
	}

	/**
	 * 返回国家名称全称字段名称
	 *
	 * @return 国家名称全称字段名称
	 */
	public String getCountryFullNameFieldName() {
		return countryFullNameFieldName;
	}

	/**
	 * 设置国家名称全称字段名称
	 *
	 * @param countryFullNameFieldName
	 * 		国家名称全称字段名称
	 */
	public void setCountryFullNameFieldName(String countryFullNameFieldName) {
		this.countryFullNameFieldName = countryFullNameFieldName;
	}

	/**
	 * 返回地区名称字段名称
	 *
	 * @return 地区名称字段名称
	 */
	public String getDistrictNameFieldName() {
		return districtNameFieldName;
	}

	/**
	 * 设置地区名称字段名称
	 *
	 * @param districtNameFieldName
	 * 		地区名称字段名称
	 */
	public void setDistrictNameFieldName(String districtNameFieldName) {
		this.districtNameFieldName = districtNameFieldName;
	}

	/**
	 * 返回地区名称全称字段名称
	 *
	 * @return 地区名称全称字段名称
	 */
	public String getDistrictFullNameFieldName() {
		return districtFullNameFieldName;
	}

	/**
	 * 设置地区名称全称字段名称
	 *
	 * @param districtFullNameFieldName
	 * 		地区名称全称字段名称
	 */
	public void setDistrictFullNameFieldName(String districtFullNameFieldName) {
		this.districtFullNameFieldName = districtFullNameFieldName;
	}

	/**
	 * 返回结果字段名称
	 *
	 * @return 结果字段名称
	 */
	public String getStatusFieldName() {
		return statusFieldName;
	}

	/**
	 * 设置结果字段名称
	 *
	 * @param status
	 * 		结果字段名称
	 */
	public void setStatusFieldName(String status) {
		this.statusFieldName = statusFieldName;
	}

	/**
	 * 返回附加参数字段名称
	 *
	 * @return 附加参数字段名称
	 */
	public String getExtraFieldName() {
		return extraFieldName;
	}

	/**
	 * 设置附加参数字段名称
	 *
	 * @param extraFieldName
	 * 		附加参数字段名称
	 */
	public void setExtraFieldName(String extraFieldName) {
		this.extraFieldName = extraFieldName;
	}

}
