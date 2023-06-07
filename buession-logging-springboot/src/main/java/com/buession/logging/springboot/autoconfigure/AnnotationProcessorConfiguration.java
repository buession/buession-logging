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
package com.buession.logging.springboot.autoconfigure;

import com.buession.logging.aspectj.reactive.aopalliance.ReactiveLogAttributeSourcePointcutAdvisor;
import com.buession.logging.aspectj.servlet.aopalliance.ServletLogAttributeSourcePointcutAdvisor;
import com.buession.logging.core.mgt.LogManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({LogConfiguration.class})
public class AnnotationProcessorConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class Servlet {

		@Bean
		@ConditionalOnBean(LogManager.class)
		public ServletLogAttributeSourcePointcutAdvisor logAttributeSourcePointcutAdvisor(
				ObjectProvider<LogManager> logManager) {
			return new ServletLogAttributeSourcePointcutAdvisor(logManager.getIfAvailable());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	static class Reactive {

		@Bean
		@ConditionalOnBean(LogManager.class)
		public ReactiveLogAttributeSourcePointcutAdvisor logAttributeSourcePointcutAdvisor(
				ObjectProvider<LogManager> logManager) {
			return new ReactiveLogAttributeSourcePointcutAdvisor(logManager.getIfAvailable());
		}

	}

}
