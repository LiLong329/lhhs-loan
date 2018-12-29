/**
 * Project Name:loan-web
 * File Name:WebConfigBeans.java
 * Package Name:com.lhhs.loan.config
 * Date:2017年7月10日下午7:37:36
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * ClassName:WebConfigBeans 日期的转换器<br/>
 * Function:  web层传日期时候的转换操作 <br/>
 * Date:     2017年7月10日 下午7:37:36 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Configuration
public class WebConfigBeans {
	private static final Logger LOGGER = Logger.getLogger(WebConfigBeans.class);
	
	@Bean
    public Converter<String, Date> dateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
            	Date date = null;
                try {
                	if(StringUtils.isBlank(source)){
                		return null;
                	}
                	
                	if (source.contains("-")) {
                        SimpleDateFormat sdf;
                        if (source.contains(":")) {
                        	sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        } else {
                        	sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        date = sdf.parse(source);
                    } else if (source.matches("^\\d+$")) {
                        Long lDate = new Long(source);
                        date = new Date(lDate);
                    }
                } catch (Exception e) {
                	LOGGER.debug("日期转换失败，传入参数：" + source);
                    e.printStackTrace();
                }
                return date;
            }
        };
    }
}

