package com.lhhs.loan.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import com.lhhs.loan.common.enums.EnumType;
import com.lhhs.loan.common.enums.EnumUtil;
import com.lhhs.loan.config.WebConfigBeans;

@WebListener
public class IndexListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(WebConfigBeans.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("初始化系统里的枚举类.....");
		Map<String, List<?>> enumMap = new HashMap<String, List<?>>();
		
		Class<?>[] clazzArray = EnumType.class.getClasses();
   	    for (Class<?> clazz : clazzArray) {
   		        String key = StringUtils.uncapitalize(clazz.getSimpleName()) +"List";
   		        enumMap.put(key, EnumUtil.parseEnum(clazz));
		}

		// 加载启动变量
		sce.getServletContext().setAttribute("enumMap", enumMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		LOGGER.info("系统停止...");
	}
}