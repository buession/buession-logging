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

import com.buession.core.utils.Assert;
import com.buession.logging.core.mgt.LogManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 日志工厂 Bean
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class LogManagerFactoryBean extends LogManagerFactory implements FactoryBean<LogManager>, InitializingBean {

	/**
	 * 日志管理器
	 */
	private volatile LogManager logManager;

	@Override
	public LogManager getObject() throws Exception {
		return logManager;
	}

	@Override
	public Class<? extends LogManager> getObjectType() {
		return logManager.getClass();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getRequestContext(), "Property 'requestContext' is required");

		if(logManager == null){
			synchronized(this){
				if(logManager == null){
					logManager = createLogManager();
				}
			}
		}
	}

}
