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
package com.buession.logging.core.mgt;

import com.buession.geoip.Resolver;
import com.buession.geoip.model.Location;
import com.buession.lang.Constants;
import com.buession.lang.Geo;
import com.buession.lang.Status;
import com.buession.logging.core.Browser;
import com.buession.logging.core.GeoLocation;
import com.buession.logging.core.LogData;
import com.buession.logging.core.OperatingSystem;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.request.Request;
import com.buession.logging.core.request.RequestContext;
import com.buession.web.utils.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

/**
 * 日志管理器抽象类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class AbstractLogManager implements LogManager {

	/**
	 * {@link RequestContext}
	 */
	private RequestContext requestContext;

	/**
	 * 用户凭证处理器
	 */
	private PrincipalHandler<?> principalHandler;

	/**
	 * 日期处理器
	 */
	private LogHandler logHandler;

	/**
	 * Geo 解析器
	 */
	private Resolver geoResolver;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public RequestContext getRequestContext() {
		return requestContext;
	}

	@Override
	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	@Override
	public PrincipalHandler<?> getPrincipalHandler() {
		return principalHandler;
	}

	@Override
	public void setPrincipalHandler(PrincipalHandler<?> principalHandler) {
		this.principalHandler = principalHandler;
	}

	@Override
	public LogHandler getLogHandler() {
		return logHandler;
	}

	@Override
	public void setLogHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}

	@Override
	public Resolver getGeoResolver() {
		return geoResolver;
	}

	@Override
	public void setGeoResolver(Resolver geoResolver) {
		this.geoResolver = geoResolver;
	}

	@Override
	public Status save(final LogData logData) {
		final Request request = requestContext.createRequest();

		logData.setDateTime(new Date());
		logData.setUrl(request.getUrl());
		logData.setRequestMethod(request.getRequestMethod());
		logData.setRequestParameters(logData.getRequestParameters());
		logData.setClientIp(request.getClientIp());
		logData.setRemoteAddr(request.getRemoteAddr());
		logData.setUserAgent(request.getUserAgent());

		if(getGeoResolver() != null){
			parseLocation(logData);
		}

		parseUserAgent(logData, request);

		return getLogHandler().handle(logData);
	}

	protected void parseLocation(final LogData logData) {
		try{
			final Location location = getGeoResolver().location(logData.getClientIp());

			if(location == null){
				return;
			}

			final GeoLocation geoLocation = new GeoLocation();

			if(location.getGeo() != null && location.getGeo().getLongitude() != null &&
					location.getGeo().getLatitude() != null){
				geoLocation.setGeo(new Geo(location.getGeo().getLongitude(), location.getGeo().getLatitude()));
			}

			final GeoLocation.Country country = new GeoLocation.Country();
			country.setCode(Optional.ofNullable(location.getCountry().getCode()).orElse(Constants.EMPTY_STRING));
			country.setName(Optional.ofNullable(location.getCountry().getName()).orElse(Constants.EMPTY_STRING));
			country.setFullName(
					Optional.ofNullable(location.getCountry().getFullName()).orElse(Constants.EMPTY_STRING));
			geoLocation.setCountry(country);

			final GeoLocation.District district = new GeoLocation.District();
			district.setName(Optional.ofNullable(location.getDistrict().getName()).orElse(Constants.EMPTY_STRING));
			district.setFullName(
					Optional.ofNullable(location.getDistrict().getFullName()).orElse(Constants.EMPTY_STRING));
			geoLocation.setDistrict(district);

			logData.setLocation(geoLocation);
		}catch(Exception e){
			if(logger.isWarnEnabled()){
				logger.warn("Parse ip: {} to get location error: {}", logData.getClientIp(), e.getMessage());
			}
		}
	}

	protected void parseUserAgent(final LogData logData, final Request request) {
		logData.setUserAgent(request.getUserAgent());

		final UserAgent userAgent = new UserAgent(logData.getUserAgent());

		final OperatingSystem operatingSystem = new OperatingSystem();
		operatingSystem.setName(
				Optional.ofNullable(userAgent.getOperatingSystem().name()).orElse(Constants.EMPTY_STRING));
		operatingSystem.setVersion(
				Optional.ofNullable(userAgent.getOperatingSystem().getVersion()).orElse(Constants.EMPTY_STRING));
		logData.setOperatingSystem(operatingSystem);

		logData.setDeviceType(userAgent.getOperatingSystem().getDeviceType());

		final Browser browser = new Browser();
		browser.setName(Optional.ofNullable(userAgent.getBrowser().name()).orElse(Constants.EMPTY_STRING));
		browser.setType(userAgent.getBrowser().getBrowserType());
		browser.setVersion(Optional.ofNullable(userAgent.getBrowser().getVersion()).orElse(Constants.EMPTY_STRING));
		logData.setBrowser(browser);
	}

}
