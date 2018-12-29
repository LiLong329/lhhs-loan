/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.lhhs.workflow.dao.domain;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * 流程变量对象
 * @author ThinkGem
 * @version 2013-11-03
 */
public class Variable implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5089705155066682107L;

	private Map<String, Object> map = Maps.newHashMap();
	
	private String keys;
	private String values;
	private String types;

	public Variable (){
		
	}
	
	public Variable (Map<String, Object> map){
		this.map = map;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	@JsonIgnore
	public Map<String, Object> getVariableMap() {

		
		return map;
	}

	public Map<String, Object> getMap() {
		return map;
	}

}
