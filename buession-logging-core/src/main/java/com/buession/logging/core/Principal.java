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

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class Principal implements Serializable {

	private final static long serialVersionUID = -1422997030727384108L;

	/**
	 * 用户 ID
	 */
	private String id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 返回用户 ID
	 *
	 * @return 用户 ID
	 */
	public String getId(){
		return id;
	}

	/**
	 * 设置用户 ID
	 *
	 * @param id
	 * 		用户 ID
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUserName(){
		return userName;
	}

	/**
	 * 设置用户名
	 *
	 * @param userName
	 * 		用户名
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * 返回真实姓名
	 *
	 * @return 真实姓名
	 */
	public String getRealName(){
		return realName;
	}

	/**
	 * 设置真实姓名
	 *
	 * @param realName
	 * 		真实姓名
	 */
	public void setRealName(String realName){
		this.realName = realName;
	}

	@Override
	public String toString(){
		return new StringJoiner(", ", "Principal => {", "}")
				.add("userId=" + id)
				.add("userName=" + userName)
				.add("realName=" + realName)
				.toString();
	}

}
