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
package com.buession.logging.rabbitmq.core;

import com.buession.logging.rabbitmq.support.RabbitRetryTemplateCustomizer;
import org.springframework.retry.support.RetryTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * Template 配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class Template implements Serializable {

	private final static long serialVersionUID = -8875227964323087897L;

	/**
	 * 重试配置
	 */
	private Retry retry;

	/**
	 * {@link RetryTemplate} 定制器
	 */
	private List<RabbitRetryTemplateCustomizer> retryCustomizers;

	/**
	 * Whether to enable mandatory messages.
	 */
	private Boolean mandatory;

	/**
	 * Timeout for `receive()` operations.
	 */
	private Duration receiveTimeout;

	/**
	 * Timeout for `sendAndReceive()` operations.
	 */
	private Duration replyTimeout;

	/**
	 * Name of the default queue to receive messages from when none is specified explicitly.
	 */
	private String defaultReceiveQueue;

	/**
	 * 返回重试配置
	 *
	 * @return 重试配置
	 */
	public Retry getRetry(){
		return this.retry;
	}

	/**
	 * 设置重试配置
	 *
	 * @param retry
	 * 		重试配置
	 */
	public void setRetry(Retry retry){
		this.retry = retry;
	}

	/**
	 * 返回 {@link RetryTemplate} 定制器
	 *
	 * @return {@link RetryTemplate} 定制器
	 */
	public List<RabbitRetryTemplateCustomizer> getRetryCustomizers(){
		return retryCustomizers;
	}

	/**
	 * 设置 {@link RetryTemplate} 定制器
	 *
	 * @param retryCustomizers
	 *        {@link RetryTemplate} 定制器
	 */
	public void setRetryCustomizers(List<RabbitRetryTemplateCustomizer> retryCustomizers){
		this.retryCustomizers = retryCustomizers;
	}

	/**
	 * Return Whether to enable mandatory messages.
	 *
	 * @return true / false
	 */
	public Boolean getMandatory(){
		return this.mandatory;
	}

	/**
	 * Sets enable mandatory messages.
	 *
	 * @param mandatory
	 * 		Whether to enable mandatory messages.
	 */
	public void setMandatory(Boolean mandatory){
		this.mandatory = mandatory;
	}

	/**
	 * Return timeout for `receive()` operations.
	 *
	 * @return Timeout for `receive()` operations.
	 */
	public Duration getReceiveTimeout(){
		return this.receiveTimeout;
	}

	/**
	 * Sets timeout for `receive()` operations.
	 *
	 * @param receiveTimeout
	 * 		Timeout for `receive()` operations.
	 */
	public void setReceiveTimeout(Duration receiveTimeout){
		this.receiveTimeout = receiveTimeout;
	}

	/**
	 * Return timeout for `sendAndReceive()` operations.
	 *
	 * @return Timeout for `sendAndReceive()` operations.
	 */
	public Duration getReplyTimeout(){
		return this.replyTimeout;
	}

	/**
	 * Sets timeout for `sendAndReceive()` operations.
	 *
	 * @param replyTimeout
	 * 		Timeout for `sendAndReceive()` operations.
	 */
	public void setReplyTimeout(Duration replyTimeout){
		this.replyTimeout = replyTimeout;
	}

	/**
	 * Return Name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @return Name of the default queue to receive messages from when none is specified explicitly.
	 */
	public String getDefaultReceiveQueue(){
		return this.defaultReceiveQueue;
	}

	/**
	 * Sets name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @param defaultReceiveQueue
	 * 		Name of the default queue to receive messages from when none is specified explicitly.
	 */
	public void setDefaultReceiveQueue(String defaultReceiveQueue){
		this.defaultReceiveQueue = defaultReceiveQueue;
	}

}
