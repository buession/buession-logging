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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.logging.rocketmq.spring;

import com.buession.core.utils.Assert;
import com.buession.logging.rocketmq.handler.RocketMQLogHandler;
import com.buession.logging.rocketmq.spring.config.RocketMQLogHandlerFactoryBeanConfigurer;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.apache.rocketmq.spring.core.RocketTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * RocketMQ 日志处理器 {@link RocketMQLogHandler} 工厂 Bean 基类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class RocketMQLogHandlerFactoryBean extends BaseLogHandlerFactoryBean<RocketMQLogHandler> {

	private RocketTemplate rocketTemplate;

	/**
	 * Topic 名称
	 */
	private String topic;

	private Charset charset = StandardCharsets.UTF_8;

	/**
	 * 是否同步
	 */
	private boolean sync = false;

	/**
	 * 构造函数
	 */
	public RocketMQLogHandlerFactoryBean() {

	}

	/**
	 * 构造函数
	 *
	 * @param configurer
	 *        {@link RocketMQLogHandlerFactoryBeanConfigurer}
	 */
	public RocketMQLogHandlerFactoryBean(final RocketMQLogHandlerFactoryBeanConfigurer configurer) {
		if(configurer != null){
			setTopic(configurer.getTopic());
		}
	}

	public RocketTemplate getRocketTemplate() {
		return rocketTemplate;
	}

	public void setRocketTemplate(RocketTemplate rocketTemplate) {
		this.rocketTemplate = rocketTemplate;
	}

	/**
	 * 返回 Topic 名称
	 *
	 * @return Topic 名称
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 设置 Topic 名称
	 *
	 * @param topic
	 * 		Topic 名称
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * 返回是否同步
	 *
	 * @return 是否同步
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * 设置是否同步
	 *
	 * @param sync
	 * 		是否同步
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getRocketTemplate(), "Property 'rocketTemplate' is required");
		Assert.isBlank(getTopic(), "Property 'topic' is required");

		if(logHandler == null){
			synchronized(this){
				if(logHandler == null){
					logHandler = new RocketMQLogHandler(getRocketTemplate(), getTopic(), isSync());
				}
			}
		}
	}

}
