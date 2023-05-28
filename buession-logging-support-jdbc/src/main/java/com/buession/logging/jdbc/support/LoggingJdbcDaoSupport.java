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
package com.buession.logging.jdbc.support;

import com.buession.core.utils.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.constraints.NotNull;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public class LoggingJdbcDaoSupport extends NamedParameterJdbcDaoSupport {

	private final TransactionTemplate transactionTemplate;

	private final String sql;

	public LoggingJdbcDaoSupport(final JdbcTemplate jdbcTemplate, final TransactionTemplate transactionTemplate,
								 final String sql){
		Assert.isNull(jdbcTemplate, "JdbcTemplate cloud not be null.");
		Assert.isBlank(sql, "Log sql cloud not be null or empty.");

		this.transactionTemplate = transactionTemplate;
		this.sql = sql;
		setJdbcTemplate(jdbcTemplate);
	}

	public void execute(final Object[] arguments){
		if(transactionTemplate == null){
			getJdbcTemplate().update(sql, arguments);
		}else{
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(@NotNull TransactionStatus transactionStatus){
					getJdbcTemplate().update(sql, arguments);
				}

			});
		}
	}

}
