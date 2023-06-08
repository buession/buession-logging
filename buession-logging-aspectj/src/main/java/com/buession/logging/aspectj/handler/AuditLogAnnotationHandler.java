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
package com.buession.logging.aspectj.handler;

import com.buession.aop.MethodInvocation;
import com.buession.aop.handler.AbstractAnnotationHandler;
import com.buession.core.utils.Assert;
import com.buession.logging.annotation.AuditLog;
import com.buession.logging.core.BusinessType;
import com.buession.logging.core.Event;
import com.buession.logging.core.LogData;
import com.buession.logging.core.mgt.LogManager;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class AuditLogAnnotationHandler extends AbstractAnnotationHandler<AuditLog> {

	private LogManager logManager;

	public AuditLogAnnotationHandler() {
		super(AuditLog.class);
	}

	public AuditLogAnnotationHandler(LogManager logManager) {
		this();
		setLogManager(logManager);
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		Assert.isNull(logManager, "LogManager cloud not be null.");
		this.logManager = logManager;
	}

	@Override
	public void execute(MethodInvocation mi, final AuditLog log) {
		final LogData logData = new LogData();

		//logData.setPrincipal();
		logData.setBusinessType(log.businessType());
		logData.setEvent(log.event());
		logData.setDescription(log.description());

		logManager.save(logData);
	}

}
