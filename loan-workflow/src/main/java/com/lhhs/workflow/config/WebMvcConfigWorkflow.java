package com.lhhs.workflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigWorkflow extends WebMvcConfigurerAdapter{


	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/workflow").setViewName("/workflow/index");
	}

}