/*
 *  @File: ConfigUtils.java
 *  @Description:TODO(用一句话描述该文件做什么) 
 *  @Copyright 2012-2018 ComCredit Corporation. All rights reserved.
 *  @Create Date:2015年7月24日
 *  @Author:zhangpenghong
 *  @version V1.0
 *  @Modify Date:2015年7月24日
 *  @Modify By:zhangpenghong
 */
package com.lhhs.loan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


 /**  
 *  @Description: 属性文件加载工具类      
 */

public class ConfigUtils {

	 private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	    private static  final String DEFAULT_PROPERTIES = "config.properties";
	    private static final String SYSTEM_CONFIG = "config";
	    public static boolean isNotEmpty(String value) {
	        return ! isEmpty(value);
	    }
		
		public static boolean isEmpty(String value) {
			return value == null || value.length() == 0 
	    			|| "false".equalsIgnoreCase(value) 
	    			|| "0".equalsIgnoreCase(value) 
	    			|| "null".equalsIgnoreCase(value) 
	    			|| "N/A".equalsIgnoreCase(value);
		}
		
		public static boolean isDefault(String value) {
			return "true".equalsIgnoreCase(value) 
					|| "default".equalsIgnoreCase(value);
		}
		
		

	    private static Pattern VARIABLE_PATTERN = Pattern.compile(
	            "\\$\\s*\\{?\\s*([\\._0-9a-zA-Z]+)\\s*\\}?");
	    
		public static String replaceProperty(String expression, Map<String, String> params) {
	        if (expression == null || expression.length() == 0 || expression.indexOf('$') < 0) {
	            return expression;
	        }
	        Matcher matcher = VARIABLE_PATTERN.matcher(expression);
	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) { // 逐个匹配
	            String key = matcher.group(1);
	            String value = System.getProperty(key);
	            if (value == null && params != null) {
	                value = params.get(key);
	            }
	            if (value == null) {
	                value = "";
	            }
	            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
	        }
	        matcher.appendTail(sb);
	        return sb.toString();
	    }
		
	    private static volatile Properties PROPERTIES;
	    
	    public static Properties getProperties() {
	        if (PROPERTIES == null) {
	            synchronized (ConfigUtils.class) {
	                if (PROPERTIES == null) {
	                    String path = System.getProperty(SYSTEM_CONFIG);
	                    if (path == null || path.length() == 0) {
	                        path = System.getenv(SYSTEM_CONFIG);
	                        if (path == null || path.length() == 0) {
	                            path = DEFAULT_PROPERTIES;
	                        }
	                    }
	                    PROPERTIES = ConfigUtils.loadProperties(path);
	                }
	            }
	        }
	        return PROPERTIES;
	    }
	    
	    public static void addProperties(Properties properties) {
	        if (properties != null) {
	            getProperties().putAll(properties);
	        }
	    }
	    
	    public static void setProperties(Properties properties) {
	        if (properties != null) {
	            PROPERTIES = properties;
	        }
	    }
	    
		public static String getProperty(String key) {
		    return getProperty(key, null);
		}
		
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    public static String getProperty(String key, String defaultValue) {
	        String value = System.getProperty(key);
	        if (value != null && value.length() > 0) {
	            return value;
	        }
	        Properties properties = getProperties();
	        return replaceProperty(properties.getProperty(key, defaultValue), (Map)properties);
	    }
	    
	   
	    
		/**
		 * 
		 * @Title: loadProperties  
		 * @Description: 加载属性文件  
		 * @param fileName
		 * @return  
		 * @return Properties    返回类型  
		 * @throws
		 */
	    public static Properties loadProperties(String fileName) {
	        Properties properties = new Properties();
	            try {
	            	File configFile = new File(fileName);
	            	logger.info("the config file path "+configFile.getAbsolutePath());
	                FileInputStream input = new FileInputStream(configFile);
	                try {
	                    properties.load(input);
	                } finally {
	                    input.close();
	                }
	            } catch (Throwable e) {
	                logger.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
	            }
	        return properties;
	    }

	    private static int PID = -1;
	    
	    public static int getPid() {
	        if (PID < 0) {
	            try {
	                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
	                String name = runtime.getName(); // format: "pid@hostname"  
	                PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
	            } catch (Throwable e) {
	                PID = 0;
	            }
	        }
	        return PID;  
	    }

		private ConfigUtils() {}
}
