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
package com.buession.logging.spring;

import com.buession.logging.core.handler.DefaultLogHandler;
import com.buession.logging.core.handler.DefaultPrincipalHandler;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.request.Request;

/**
 * 日志工厂
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class LogFactory {

	/**
	 * 请求对象
	 */
	private Request request;

	/**
	 * 客户端 IP 请求头名称
	 */
	private String clientIpHeaderName;

	/**
	 * 用户凭证处理器
	 */
	private PrincipalHandler<?> principalHandler = new DefaultPrincipalHandler();

	/**
	 * 日期处理器
	 */
	private LogHandler logHandler = new DefaultLogHandler();

	/**
	 * 返回请求对象
	 *
	 * @return 请求对象
	 */
	public Request getRequest(){
		return request;
	}

	/**
	 * 设置请求对象
	 *
	 * @param request
	 * 		请求对象
	 */
	public void setRequest(Request request){
		this.request = request;
	}

	/**
	 * 返回客户端 IP 请求头名称
	 *
	 * @return 客户端 IP 请求头名称
	 */
	public String getClientIpHeaderName(){
		return clientIpHeaderName;
	}

	/**
	 * 设置客户端 IP 请求头名称
	 *
	 * @param clientIpHeaderName
	 * 		客户端 IP 请求头名称
	 */
	public void setClientIpHeaderName(String clientIpHeaderName){
		this.clientIpHeaderName = clientIpHeaderName;
	}

	/**
	 * 返回用户凭证处理器
	 *
	 * @return 用户凭证处理器
	 */
	public PrincipalHandler<?> getPrincipalHandler(){
		return principalHandler;
	}

	/**
	 * 设置用户凭证处理器
	 *
	 * @param principalHandler
	 * 		用户凭证处理器
	 */
	public void setPrincipalHandler(PrincipalHandler<?> principalHandler){
		this.principalHandler = principalHandler;
	}

	/**
	 * 返回日期处理器
	 *
	 * @return 日期处理器
	 */
	public LogHandler getLogHandler(){
		return logHandler;
	}

	/**
	 * 设置日期处理器
	 *
	 * @param logHandler
	 * 		日期处理器
	 */
	public void setLogHandler(LogHandler logHandler){
		this.logHandler = logHandler;
	}

}
