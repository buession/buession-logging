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
package com.buession.logging.mongodb.core;

import com.buession.core.datetime.DateTimeUtils;
import com.buession.core.validator.Validate;
import com.mongodb.DBObject;
import org.apache.commons.logging.Log;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.lang.ref.ReferenceQueue;
import java.security.cert.CertPath;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public interface Converters {

	class BsonTimestampToStringConverter implements Converter<BsonTimestamp, String> {

		public BsonTimestampToStringConverter() {
		}

		public String convert(final BsonTimestamp source) {
			return String.valueOf(source.getTime());
		}

	}

	@WritingConverter
	class PatternToStringConverter implements Converter<Pattern, String> {

		public PatternToStringConverter() {
		}

		public String convert(final Pattern source) {
			return source.pattern();
		}

	}

	@ReadingConverter
	class StringToPatternConverter implements Converter<String, Pattern> {

		public StringToPatternConverter() {
		}

		public Pattern convert(final String source) {
			return Validate.isBlank(source) ? null : Pattern.compile(source, 2);
		}

	}

	class BsonTimestampToDateConverter implements Converter<BsonTimestamp, Date> {

		public BsonTimestampToDateConverter() {
		}

		public Date convert(final BsonTimestamp source) {
			return new Date(source.getTime());
		}

	}

	@WritingConverter
	class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

		public ZonedDateTimeToStringConverter() {
		}

		public String convert(final ZonedDateTime source) {
			return Optional.ofNullable(source).map(ZonedDateTime::toString).orElse(null);
		}

	}

	class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

		public ZonedDateTimeToDateConverter() {
		}

		public Date convert(final ZonedDateTime source) {
			return Optional.ofNullable(source).map((zonedDateTime)->Date.from(zonedDateTime.toInstant())).orElse(null);
		}

	}

	@ReadingConverter
	class ObjectIdToLongConverter implements Converter<ObjectId, String> {

		public ObjectIdToLongConverter() {
		}

		public String convert(final ObjectId source) {
			return source.toHexString();
		}

	}

	@ReadingConverter
	class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

		public StringToZonedDateTimeConverter() {
		}

		public ZonedDateTime convert(final String source) {
			return Validate.isBlank(source) ? null : DateTimeUtils.zonedDateTimeOf(source);
		}

	}

	class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

		public DateToZonedDateTimeConverter() {
		}

		public ZonedDateTime convert(final Date source) {
			return Optional.ofNullable(source)
					.map((date)->ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())).orElse(null);
		}

	}

	class NullConverter<I, O> implements Converter<I, O> {

		public NullConverter() {
		}

		public O convert(final I i) {
			return null;
		}

	}

	class CertPathConverter extends NullConverter<CertPath, DBObject> {

		public CertPathConverter() {
		}

	}

	class ThreadLocalConverter extends NullConverter<ThreadLocal, DBObject> {

		public ThreadLocalConverter() {
		}

	}

	class ReferenceQueueConverter extends NullConverter<ReferenceQueue, DBObject> {

		public ReferenceQueueConverter() {
		}

	}

	class RunnableConverter extends NullConverter<Runnable, DBObject> {

		public RunnableConverter() {
		}

	}

	class CacheConverter extends NullConverter<com.google.common.cache.Cache, DBObject> {

		public CacheConverter() {
		}

	}

	class CacheLoaderConverter extends NullConverter<com.google.common.cache.CacheLoader, DBObject> {

		public CacheLoaderConverter() {
		}

	}

	class CommonsLogConverter extends NullConverter<Log, DBObject> {

		public CommonsLogConverter() {
		}

	}

	class ClassConverter extends NullConverter<Class, DBObject> {

		public ClassConverter() {
		}

	}

	class LoggerConverter extends NullConverter<Logger, DBObject> {

		public LoggerConverter() {
		}

	}

}
