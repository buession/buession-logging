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
import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public class ZonedDateTimeCodecProvider implements CodecProvider {

	public ZonedDateTimeCodecProvider() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry codecRegistry) {
		return ZonedDateTime.class.isAssignableFrom(clazz) ? (Codec<T>) new ZonedDateTimeCodec() : null;
	}

	private static class ZonedDateTimeCodec implements Codec<ZonedDateTime> {

		private ZonedDateTimeCodec() {
		}

		@Override
		public ZonedDateTime decode(final BsonReader reader, final DecoderContext decoderContext) {
			BsonTimestamp timestamp = reader.readTimestamp();
			Date date = new Date(timestamp.getTime());
			return DateTimeUtils.zonedDateTimeOf(date);
		}

		@Override
		public void encode(final BsonWriter writer, final ZonedDateTime zonedDateTime,
						   final EncoderContext encoderContext) {
			writer.writeTimestamp(new BsonTimestamp(DateTimeUtils.dateOf(zonedDateTime).getTime()));
		}

		@Override
		public Class<ZonedDateTime> getEncoderClass() {
			return ZonedDateTime.class;
		}

	}

}
