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

import com.buession.lang.BrowserType;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class Browser implements Serializable {

	private final static long serialVersionUID = 186623803706402397L;

	/**
	 * 浏览器名称
	 */
	private String name;

	/**
	 * 浏览器类型
	 */
	private BrowserType type;

	/**
	 * 浏览器版本
	 */
	private String version;

	/**
	 * 返回浏览器名称
	 *
	 * @return 浏览器名称
	 */
	public String getName(){
		return name;
	}

	/**
	 * 设置浏览器名称
	 *
	 * @param name
	 * 		浏览器名称
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * 返回浏览器类型
	 *
	 * @return 浏览器类型
	 */
	public BrowserType getType(){
		return type;
	}

	/**
	 * 设置浏览器类型
	 *
	 * @param type
	 * 		浏览器类型
	 */
	public void setType(BrowserType type){
		this.type = type;
	}

	/**
	 * 返回浏览器版本
	 *
	 * @return 浏览器版本
	 */
	public String getVersion(){
		return version;
	}

	/**
	 * 设置浏览器版本
	 *
	 * @param version
	 * 		浏览器版本
	 */
	public void setVersion(String version){
		this.version = version;
	}

	@Override
	public String toString(){
		return new StringJoiner(", ", "browser => {", "}")
				.add("name=" + name)
				.add("type=" + type)
				.add("version=" + version)
				.toString();
	}

}
