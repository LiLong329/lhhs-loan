/**
 * Project Name:loan-workflow
 * File Name:ProcessInstanceHighlightsService.java
 * Package Name:com.lhhs.workflow.bs.inf
 * Date:2017年10月23日下午8:42:57
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.workflow.bs.inf;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

/**
 * ClassName:ProcessInstanceHighlightsService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年10月23日 下午8:42:57 <br/>
 * @author   lenovo
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface ProcessInstanceHighlightsService {
	/**
	 * getHighLightedFlows
	 * 
	 * @param processDefinition
	 * @param processInstanceId
	 * @return
	 */
	public List<String> getHighLightedFlows(String processInstanceId,ProcessDefinitionEntity processDefinition) ;
}

