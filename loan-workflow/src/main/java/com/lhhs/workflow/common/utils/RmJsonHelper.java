package com.lhhs.workflow.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RmJsonHelper {
	private final static Logger log = LogManager.getLogger(RmJsonHelper.class);
	private static SerializeConfig mapping = new SerializeConfig();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	// 将String转换成JSON
	public static String string2json(String key, String value) {
		// Object jsonArray = JSONArray.parseArray(key, value);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = JSONArray.toJSONString(map);
		return jsonArray;
	}

	// 将数组转换成JSON
	public static String array2json(Object object) {
		String jsonArray = JSONArray.toJSONString(object);
		return jsonArray;
	}

	// 将Map转换成JSON
	public static String map2json(Object object) {
		String jsonArray = JSONArray.toJSONString(object);
		return jsonArray;
	}

	// 将domain对象转换成JSON
	public static String bean2json(Object object) {

		String jsonArray = JSONArray.toJSONString(object, mapping);
		return jsonArray;
	}

	// 将domain对象转换成JSON
	public static String beanMapper2json(Object object) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		// 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
		// Include.Include.ALWAYS 默认
		// Include.NON_DEFAULT 属性为默认值不序列化
		// Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
		try {
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			result = mapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("JacksonUtil Exception>>>>>>>:{}" + e.toString());
			throw new RuntimeException(e);
		}
		return result;

	}

	// 将JSON转换成domain对象,其中beanClass为domain对象的Class
	public static Object json2Object(String json, Class beanClass) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		return JSONObject.toJavaObject(jsonObject, beanClass);
	}

	// 将JSON转换成String
	public static String json2String(String json, String key) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		return jsonObject.getString(key);
	}

	// 将JSON转换成数组,其中valueClass为数组中存放对象的Class
	public static Object json2Array(String json, Class valueClass) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		return JSONObject.toJavaObject(jsonObject, valueClass);
	}

	// 将JSON转换成Map,其中valueClass为Map中value的Class,keyArray为Map的key
	public static Map json2Map(Object[] keyArray, String json, Class valueClass) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		Map classMap = new HashMap();

		for (int i = 0; i < keyArray.length; i++) {
			classMap.put(keyArray[i], valueClass);
		}
		return (Map) JSONObject.toJavaObject(jsonObject, Map.class);
	}
}