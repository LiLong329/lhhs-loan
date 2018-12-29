package com.lhhs.workflow.bs.inf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.dao.domain.ResultEasyuiVO;

/**
 * 流程模型相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
public interface ActModelService {


	/**
	 * 流程模型列表
	 * @param page 分页参数 
	 * @param category 模型分类
	 * @return List<Mode>列表和总数
	 */
	public Page modelList(Page page, String category) ;

	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */

	public Model create(String name, String key, String description, String category) throws UnsupportedEncodingException ;

	/**
	 * 根据Model部署流程
	 */
	public String deploy(String id) ;
	
	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void export(String id, HttpServletResponse response) ;

	/**
	 * 更新Model分类
	 */
	public void updateCategory(String id, String category) ;
	
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	public void delete(String id) ;
}
