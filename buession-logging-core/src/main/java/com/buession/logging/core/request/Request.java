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

import com.buession.logging.core.RequestMethod;
import com.google.common.collect.Multimap;

/**
 * 请求对象
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public interface Request {

	/**
	 * 返回请求地址
	 *
	 * @return 请求地址
	 */
	String getUrl();

	/**
	 * 返回请求方法
	 *
	 * @return 请求方法
	 */
	RequestMethod getRequestMethod();

	/**
	 * 返回请求体
	 *
	 * @return 请求体
	 */
	String getRequestBody();

	/**
	 * 返回请求参数
	 *
	 * @return 请求参数
	 */
	Multimap<String, String> getRequestParameters();

	/**
	 * 返回客户端 IP
	 *
	 * @return 客户端 IP
	 */
	String getClientIp();

	/**
	 * 返回 Remote Addr
	 *
	 * @return Remote Addr
	 */
	String getRemoteAddr();

	/**
	 * 返回 User-Agent
	 *
	 * @return User-Agent
	 */
	String getUserAgent();

}
