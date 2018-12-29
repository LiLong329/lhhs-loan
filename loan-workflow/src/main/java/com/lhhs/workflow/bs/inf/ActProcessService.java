package com.lhhs.workflow.bs.inf;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.springframework.web.multipart.MultipartFile;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.dao.domain.ProcessVO;
import com.lhhs.workflow.dao.domain.ResultEasyuiVO;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
public interface ActProcessService {

	/**
	 * 流程定义列表
	 * @param page
	 *            分页对象
	 * @param 流程对象
	 *            流程分类
	 *            流程KEY
	 *            流程名称
	 */
	public Page processList(Page page, ProcessVO processVO);

	/**
	 * 流程实例列表
	 */
	public Page runningList(Page page, ProcessVO processVO) ;
	
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image) 
	 */
	public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception ;
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	public String deploy(String category, MultipartFile file) ;
	
	/**
	 * 设置流程分类
	 */
	public void updateCategory(String procDefId, String category);

	/**
	 * 挂起、激活流程实例
	 */
	public String updateState(String state, String procDefId) ;
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	public org.activiti.engine.repository.Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException ;
	
	/**
	 * 导出图片文件到硬盘
	 */
	public List<String> exportDiagrams(String exportDir) throws IOException ;

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	public void deleteDeployment(String deploymentId) ;
	
	/**
	 * 删除运行的流程实例
	 * @param procInsId 流程实例ID
	 * @param deleteReason 删除原因，可为空
	 */
	public void deleteProcIns(String procInsId, String deleteReason) ;
	
}
