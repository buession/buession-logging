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
package com.buession.logging.support.spring;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.core.handler.LogHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 日志处理器 {@link LogHandler} 工厂 Bean 基类
 *
 * @param <T>
 * 		日志处理器类型
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class BaseLogHandlerFactoryBean<T extends LogHandler>
		implements LogHandlerFactory, FactoryBean<T>, InitializingBean {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	/**
	 * 日志处理器
	 */
	protected volatile T logHandler;

	@Override
	public T getObject() throws Exception {
		return logHandler;
	}

	@Override
	public Class<? extends LogHandler> getObjectType() {
		return logHandler.getClass();
	}

}
