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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.rest.spring.config;

import com.buession.httpclient.conn.nio.IOReactorConfig;
import com.buession.httpclient.core.Configuration;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;

import java.util.concurrent.ThreadFactory;

/**
 * Configures {@link RestLogHandlerFactoryBean} with sensible defaults.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class HttpClientConfigurer extends Configuration {

	private ApacheClient apacheClient;

	private OkHttp okHttp;

	public ApacheClient getApacheClient() {
		return apacheClient;
	}

	public void setApacheClient(ApacheClient apacheClient) {
		this.apacheClient = apacheClient;
	}

	public OkHttp getOkHttp() {
		return okHttp;
	}

	public void setOkHttp(OkHttp okHttp) {
		this.okHttp = okHttp;
	}

	public final static class ApacheClient {

		private IOReactorConfig ioReactor;

		private ThreadFactory threadFactory;

		public IOReactorConfig getIoReactor() {
			return ioReactor;
		}

		public void setIoReactor(IOReactorConfig ioReactor) {
			this.ioReactor = ioReactor;
		}

		public ThreadFactory getThreadFactory() {
			return threadFactory;
		}

		public void setThreadFactory(ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
		}

	}

	public final static class OkHttp {

	}

}
