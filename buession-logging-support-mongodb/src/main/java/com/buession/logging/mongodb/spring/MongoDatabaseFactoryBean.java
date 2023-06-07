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
package com.buession.logging.mongodb.spring;

import com.buession.core.utils.Assert;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * {@link org.springframework.data.mongodb.MongoDatabaseFactory} 工厂 Bean
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class MongoDatabaseFactoryBean extends MongoDatabaseFactory
		implements FactoryBean<org.springframework.data.mongodb.MongoDatabaseFactory>,
		InitializingBean, DisposableBean {

	private org.springframework.data.mongodb.MongoDatabaseFactory mongoDatabaseFactory;

	@Override
	public org.springframework.data.mongodb.MongoDatabaseFactory getObject() throws Exception {
		return mongoDatabaseFactory;
	}

	@Override
	public Class<org.springframework.data.mongodb.MongoDatabaseFactory> getObjectType() {
		return org.springframework.data.mongodb.MongoDatabaseFactory.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isNull(getMongoClient(), "MongoClient is null.");
		Assert.isNull(getDatabaseName(), "Database is blank, empty or null.");
		mongoDatabaseFactory = createMongoDatabaseFactory();
	}

	@Override
	public void destroy() throws Exception {
		if(mongoDatabaseFactory instanceof DisposableBean){
			((DisposableBean) mongoDatabaseFactory).destroy();
		}
	}

}