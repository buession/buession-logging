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
package com.buession.logging.file.handler;

import com.buession.core.utils.Assert;
import com.buession.io.file.File;
import com.buession.lang.Status;
import com.buession.logging.core.LogData;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.file.formatter.DefaultFormatter;
import com.buession.logging.file.formatter.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * 文件日志处理器
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class FileLogHandler implements LogHandler {

	/**
	 * 日志文件对象
	 */
	private final com.buession.io.file.File file;

	/**
	 * 日志格式化
	 */
	private Formatter formatter = new DefaultFormatter();

	private final static Logger logger = LoggerFactory.getLogger(FileLogHandler.class);

	/**
	 * 构造函数
	 *
	 * @param file
	 * 		日志文件对象 {@link java.io.File}
	 */
	public FileLogHandler(final java.io.File file){
		Assert.isNull(file, "Log file cloud not be null.");
		this.file = new File(file);
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 * 		日志文件对象路径 {@link Path}
	 */
	public FileLogHandler(final Path path){
		Assert.isNull(path, "Log file path cloud not be null.");
		this.file = new File(path.toFile());
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 * 		日志文件对象路径
	 */
	public FileLogHandler(final String path){
		Assert.isBlank(path, "Log file path cloud not be null or empty.");
		this.file = new File(path);
	}

	/**
	 * 构造函数
	 *
	 * @param file
	 * 		日志文件对象 {@link java.io.File}
	 * @param formatter
	 * 		日志格式化
	 */
	public FileLogHandler(final java.io.File file, final Formatter formatter){
		this(file);
		Assert.isNull(formatter, "Formatter is null.");
		this.formatter = formatter;
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 * 		日志文件对象路径 {@link Path}
	 * @param formatter
	 * 		日志格式化
	 */
	public FileLogHandler(final Path path, final Formatter formatter){
		this(path);
		Assert.isNull(formatter, "Formatter is null.");
		this.formatter = formatter;
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 * 		日志文件对象路径
	 * @param formatter
	 * 		日志格式化
	 */
	public FileLogHandler(final String path, final Formatter formatter){
		this(path);
		Assert.isNull(formatter, "Formatter is null.");
		this.formatter = formatter;
	}

	@Override
	public Status handle(final LogData logData){
		try{
			file.write(formatter.format(logData), true);
			return Status.SUCCESS;
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Save log data failure: {}", e.getMessage());
			}
			return Status.FAILURE;
		}
	}

}
