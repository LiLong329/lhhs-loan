package com.lhhs.loan.common.utils;

import java.util.HashMap;
import java.util.Map;

public class SystemConfigCache {

	public static HashMap<String, Object> systemMap = null;
	
	public static void setSettingsMap( Map<String, Object> map )
	{
		systemMap.putAll( map );
	}
	/**
	 * 获取元素值
	 * @param id
	 * @param key
	 * @return
	 */
	public static Object getValue( String id,String key )
	{
		if( systemMap == null || systemMap.isEmpty() )
			return null;
		Map<String,Object> map = (Map<String, Object>) systemMap.get( id );
		if( map.containsKey(key) )
			return map.get(key);
		return null;
	}
	/**
	 * 获取集合元素
	 * @param id
	 * @return
	 */
	public static Object getCategory( String id )
	{
		if( systemMap == null || systemMap.isEmpty() )
			return null;
		return systemMap.get( id );
	}
}
