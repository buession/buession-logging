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
package com.buession.logging.mongodb.core;

import com.mongodb.event.ConnectionPoolListener;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * 线程池配置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class PoolConfiguration implements Serializable {

	private final static long serialVersionUID = 6352817004600241253L;

	private Integer minSize;

	private Integer maxSize;

	private Duration maxWaitTime;

	private Duration maxConnectionLifeTime;

	private Duration maxConnectionIdleTime;

	private Duration maintenanceInitialDelay;

	private Duration maintenanceFrequency;

	private Integer maxConnecting;

	private List<ConnectionPoolListener> connectionPoolListeners;

	public Integer getMinSize() {
		return minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Duration getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(Duration maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public Duration getMaxConnectionLifeTime() {
		return maxConnectionLifeTime;
	}

	public void setMaxConnectionLifeTime(Duration maxConnectionLifeTime) {
		this.maxConnectionLifeTime = maxConnectionLifeTime;
	}

	public Duration getMaxConnectionIdleTime() {
		return maxConnectionIdleTime;
	}

	public void setMaxConnectionIdleTime(Duration maxConnectionIdleTime) {
		this.maxConnectionIdleTime = maxConnectionIdleTime;
	}

	public Duration getMaintenanceInitialDelay() {
		return maintenanceInitialDelay;
	}

	public void setMaintenanceInitialDelay(Duration maintenanceInitialDelay) {
		this.maintenanceInitialDelay = maintenanceInitialDelay;
	}

	public Duration getMaintenanceFrequency() {
		return maintenanceFrequency;
	}

	public void setMaintenanceFrequency(Duration maintenanceFrequency) {
		this.maintenanceFrequency = maintenanceFrequency;
	}

	public Integer getMaxConnecting() {
		return maxConnecting;
	}

	public void setMaxConnecting(Integer maxConnecting) {
		this.maxConnecting = maxConnecting;
	}

	public List<ConnectionPoolListener> getConnectionPoolListeners() {
		return connectionPoolListeners;
	}

	public void setConnectionPoolListeners(List<ConnectionPoolListener> connectionPoolListeners) {
		this.connectionPoolListeners = connectionPoolListeners;
	}

}
