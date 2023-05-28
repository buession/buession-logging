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
package com.buession.logging.core.mgt;

import com.buession.geoip.Resolver;
import com.buession.lang.Status;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.request.Request;

/**
 * 日志管理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public interface LogManager {

	/**
	 * 返回请求对象
	 *
	 * @return 请求对象
	 */
	Request getRequest();

	/**
	 * 设置请求对象
	 *
	 * @param request
	 * 		请求对象
	 */
	void setRequest(Request request);

	/**
	 * 返回用户凭证处理器
	 *
	 * @return 用户凭证处理器
	 */
	PrincipalHandler<?> getPrincipalHandler();

	/**
	 * 设置用户凭证处理器
	 *
	 * @param principalHandler
	 * 		用户凭证处理器
	 */
	void setPrincipalHandler(PrincipalHandler<?> principalHandler);

	/**
	 * 返回日期处理器
	 *
	 * @return 日期处理器
	 */
	LogHandler getLogHandler();

	/**
	 * 设置日期处理器
	 *
	 * @param logHandler
	 * 		日期处理器
	 */
	void setLogHandler(LogHandler logHandler);

	/**
	 * 返回 Geo 解析器
	 *
	 * @return Geo 解析器
	 */
	Resolver getGeoResolver();

	/**
	 * 设置 Geo 解析器
	 *
	 * @param geoResolver
	 * 		Geo 解析器
	 */
	void setGeoResolver(Resolver geoResolver);

	/**
	 * 执行
	 *
	 * @return 执行结果
	 */
	Status execute();

}
