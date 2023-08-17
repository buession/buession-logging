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
package com.buession.logging.core;

import com.buession.lang.Geo;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 地理位置
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
public class GeoLocation implements Serializable {

	private final static long serialVersionUID = 951578665709567861L;

	/**
	 * 经纬度
	 */
	private Geo geo;

	/**
	 * 国家或地区
	 */
	private Country country;

	/**
	 * 行政区域
	 */
	private District district;

	/**
	 * 范围经纬度
	 *
	 * @return 经纬度
	 */
	public Geo getGeo(){
		return geo;
	}

	/**
	 * 设置经纬度
	 *
	 * @param geo
	 * 		经纬度
	 */
	public void setGeo(Geo geo){
		this.geo = geo;
	}

	/**
	 * 返回国家或地区
	 *
	 * @return 国家或地区
	 */
	public Country getCountry(){
		return country;
	}

	/**
	 * 设置国家或地区
	 *
	 * @param country
	 * 		国家或地区
	 */
	public void setCountry(Country country){
		this.country = country;
	}

	/**
	 * 返回行政区域
	 *
	 * @return 行政区域
	 */
	public District getDistrict(){
		return district;
	}

	/**
	 * 设置行政区域
	 *
	 * @param district
	 * 		行政区域
	 */
	public void setDistrict(District district){
		this.district = district;
	}

	@Override
	public String toString(){
		return new StringJoiner(", ", "geo location => {", "}")
				.add("geo=" + geo)
				.add("country=" + country)
				.add("district=" + district)
				.toString();
	}

	/**
	 * 国家或地区
	 */
	public final static class Country implements Serializable {

		private final static long serialVersionUID = 2542996497353771071L;

		/**
		 * 国家或地区代码
		 */
		private String code;

		/**
		 * 国家或地区名称
		 */
		private String name;

		/**
		 * 国家或地区全称
		 */
		private String fullName;

		/**
		 * 返回国家或地区代码
		 *
		 * @return 国家或地区代码
		 */
		public String getCode(){
			return code;
		}

		/**
		 * 设置国家或地区代码
		 *
		 * @param code
		 * 		国家或地区代码
		 */
		public void setCode(String code){
			this.code = code;
		}

		/**
		 * 返回国家或地区名称
		 *
		 * @return 国家或地区名称
		 */
		public String getName(){
			return name;
		}

		/**
		 * 设置国家或地区名称
		 *
		 * @param name
		 * 		国家或地区名称
		 */
		public void setName(String name){
			this.name = name;
		}

		/**
		 * 返回国家或地区全称
		 *
		 * @return 国家或地区全称
		 */
		public String getFullName(){
			return fullName;
		}

		/**
		 * 设置国家或地区全称
		 *
		 * @param fullName
		 * 		国家或地区全称
		 */
		public void setFullName(String fullName){
			this.fullName = fullName;
		}

		@Override
		public String toString(){
			return fullName;
		}

	}

	/**
	 * 行政区域
	 */
	public final static class District implements Serializable {

		private final static long serialVersionUID = 8703201935758837505L;

		/**
		 * 行政区域名称
		 */
		private String name;

		/**
		 * 行政区域全称
		 */
		private String fullName;

		/**
		 * 返回行政区域名称
		 *
		 * @return 行政区域名称
		 */
		public String getName(){
			return name;
		}

		/**
		 * 设置行政区域名称
		 *
		 * @param name
		 * 		行政区域名称
		 */
		public void setName(String name){
			this.name = name;
		}

		/**
		 * 返回行政区域全称
		 *
		 * @return 行政区域全称
		 */
		public String getFullName(){
			return fullName;
		}

		/**
		 * 设置行政区域全称
		 *
		 * @param fullName
		 * 		行政区域全称
		 */
		public void setFullName(String fullName){
			this.fullName = fullName;
		}

		@Override
		public String toString(){
			return fullName;
		}

	}

}
