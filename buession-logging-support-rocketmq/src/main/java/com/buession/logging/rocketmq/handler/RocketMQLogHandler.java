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
package com.buession.logging.rocketmq.handler;

import com.buession.core.utils.Assert;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.AbstractLogHandler;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketTemplate;

/**
 * RocketMQ 日志处理器
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class RocketMQLogHandler extends AbstractLogHandler {

	/**
	 * {@link RocketTemplate}
	 */
	private final RocketTemplate rocketTemplate;

	/**
	 * Topic 名称
	 */
	private final String topic;

	/**
	 * 是否同步
	 */
	private boolean sync = false;

	/**
	 * 构造函数
	 *
	 * @param rocketTemplate
	 *        {@link RocketTemplate}
	 * @param topic
	 * 		Topic 名称
	 */
	public RocketMQLogHandler(final RocketTemplate rocketTemplate, final String topic) {
		Assert.isNull(rocketTemplate, "RocketMQTemplate is null.");
		Assert.isBlank(topic, "Topic name is blank, empty or null.");
		this.rocketTemplate = rocketTemplate;
		this.topic = topic;
	}

	/**
	 * 构造函数
	 *
	 * @param rocketTemplate
	 *        {@link RocketTemplate}
	 * @param topic
	 * 		Topic 名称
	 * @param sync
	 * 		是否异步
	 */
	public RocketMQLogHandler(final RocketTemplate rocketTemplate, final String topic, final boolean sync) {
		this(rocketTemplate, topic);
		this.sync = sync;
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
	protected Status doHandle(final LogData logData) throws Exception {
		if(sync){
			SendResult result = rocketTemplate.syncSend(topic, logData);
			return result.getSendStatus() == SendStatus.SEND_OK ? Status.SUCCESS : Status.FAILURE;
		}else{
			rocketTemplate.convertAndSend(topic, logData);
			return Status.SUCCESS;
		}
	}

}
