package com.lhhs.workflow.bs.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.bs.inf.ActModelService;
import com.lhhs.workflow.dao.domain.ResultEasyuiVO;

/**
 * 流程模型相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service("ActModelService")
@Transactional(readOnly = true)
public class ActModelServiceImpl implements ActModelService{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RepositoryService repositoryService;
	
	protected ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 流程模型列表
	 * @param page 分页参数 
	 * @param category 模型分类
	 * @return List<Mode>列表和总数
	 */
	public Page modelList(Page page, String category) {
		if(page==null){
			page=new Page();
		}
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		
		if (StringUtils.isNotEmpty(category)){
			modelQuery.modelCategory(category);
		}
		page.setTotalCount((int)modelQuery.count());
		List list =modelQuery.listPage(page.getPageStartIndex(), page.getPageEndIndex());
		page.setResultList(list);
		return page;
	}

	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model create(String name, String key, String description, String category) throws UnsupportedEncodingException {
		
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode properties = objectMapper.createObjectNode();
		properties.put("process_author", "jeesite");
		editorNode.put("properties", properties);
		ObjectNode stencilset = objectMapper.createObjectNode();
		stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilset);
		
		Model modelData = repositoryService.newModel();
		description = StringUtils.defaultString(description);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setName(name);
		modelData.setCategory(category);
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		
		return modelData;
	}

	/**
	 * 根据Model部署流程
	 */
	@Transactional(readOnly = false)
	public String deploy(String id) {
		String message = "";
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			
			String processName = modelData.getName();
			if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
				processName += ".bpmn20.xml";
			}
//			System.out.println("========="+processName+"============"+modelData.getName());
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addInputStream(processName, in).deploy();
//					.addString(processName, new String(bpmnBytes)).deploy();
			
			// 设置流程分类
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : list) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
				message = "部署成功，流程ID=" + processDefinition.getId();
			}
			if (list.size() == 0){
				message = "部署失败，没有流程。";
			}
		} catch (Exception e) {
			throw new ActivitiException("设计模型图不正确，检查模型正确性，模型ID="+id, e);
		}
		return message;
	}
	
	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void export(String id, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
			logger.error(filename+"报单导出:"+in.toString());
		} catch (Exception e) {
			throw new ActivitiException("导出model的xml文件失败，模型ID="+id, e);
		}
		
	}

	/**
	 * 更新Model分类
	 */
	@Transactional(readOnly = false)
	public void updateCategory(String id, String category) {
		org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
		modelData.setCategory(category);
		repositoryService.saveModel(modelData);
	}
	
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		repositoryService.deleteModel(id);
	}
}
