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
package com.buession.logging.aop.servlet.handler;

import com.buession.aop.MethodInvocation;
import com.buession.logging.aop.handler.AbstractAuditLogAnnotationHandler;
import com.buession.logging.core.BusinessType;
import com.buession.logging.core.Event;
import com.buession.logging.core.LogData;
import com.buession.logging.core.annotation.AuditLog;
import com.buession.logging.core.request.ReactiveRequest;
import com.buession.logging.core.request.Request;
import com.buession.web.reactive.aop.handler.ReactiveContentTypeAnnotationHandler;
import com.buession.web.reactive.context.request.ReactiveRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class ServletAuditLogAnnotationHandler extends AbstractAuditLogAnnotationHandler {

	private final static Logger logger = LoggerFactory.getLogger(ReactiveContentTypeAnnotationHandler.class);

	public ServletAuditLogAnnotationHandler() {
		super();
	}

	@Override
	public void execute(MethodInvocation mi, AuditLog log) {
		ReactiveRequestAttributes requestAttributes = (ReactiveRequestAttributes) RequestContextHolder.getRequestAttributes();
		Request request = new ReactiveRequest(requestAttributes.getRequest());


		final LogData logData = new LogData();

		//logData.setPrincipal();
		logData.setDateTime(new Date());
		logData.setBusinessType(new BusinessType() {

			@Override
			public String toString() {
				return log.businessType();
			}

		});
		logData.setEvent(new Event() {

			@Override
			public String toString() {
				return log.event();
			}

		});
		logData.setDescription(log.description());
		/*
		if(response == null){
			if(logger.isWarnEnabled()){
				logger.warn("ServerHttpResponse is null");
			}
			return;
		}

		String mime = contentType.mime();
		int i = mime.indexOf('/');
		Charset charset = Charset.forName(contentType.charset());
		String type = mime.substring(0, i - 1);
		String subType = mime.substring(i);

		response.getHeaders().setContentType(new MediaType(type, subType, charset));

		 */
	}

}
