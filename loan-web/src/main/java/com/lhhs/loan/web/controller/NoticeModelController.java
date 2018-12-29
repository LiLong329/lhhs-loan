package com.lhhs.loan.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.QuartersApi;
import com.lhhs.bump.domain.Quarters;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.service.NoticeModelService;
/**

 * 通知模板管理
 * @author HB
 */
@Controller
@RequestMapping("/noticeModel")
public class NoticeModelController {
	
	@Autowired
	private NoticeModelService noticeModelService;
	@Reference
	private QuartersApi quartersApi;
	/**
	 * 模板列表
	 */
	@RequestMapping("/list/{type}")
	public ModelAndView list(NoticeModel noticeModel,@PathVariable("type") String type){
		ModelAndView mav = new ModelAndView("noticeModel/noticeModelList");
		noticeModel.setModelType(type);//模板类型 1：业务 2：资金
		noticeModel.setModelStatus("1");//0:未勾选 1:勾选
		@SuppressWarnings("unchecked")
		List<NoticeModel> noticeModelList = noticeModelService.queryList(noticeModel);
		Map<String, List<NoticeModel>> modelMap = new LinkedHashMap<String, List<NoticeModel>>();
		for (NoticeModel model:noticeModelList) {
			List<NoticeModel> value = modelMap.get(model.getName());
			if (value==null) {
				value = new ArrayList<NoticeModel>();
			}
			value.add(model);
			modelMap.put(model.getName(), value);
		}
		mav.addObject("modelMap", modelMap);
		mav.addObject("type", type);
		return mav;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mav = new ModelAndView("noticeModel/noticeModelAdd");
		Quarters quarters = new Quarters();
		quarters.setServerId("lhhs_spark");//小贷系统岗位标识
		quarters.setStatus("1");//启用的岗位
		List<Quarters> allList = quartersApi.queryList(quarters);
		mav.addObject("allList", allList);
		
		return mav;
	}
	/**
	 * 根据类型获取模板
	 */
	@ResponseBody
	@RequestMapping("/getByType")
	public List<NoticeModel> getByType(NoticeModel noticeModel){
		//获取事件节点信息
		List<NoticeModel> noticeModelList = noticeModelService.getByType(noticeModel);
		return noticeModelList;
	}
	/**
	 * 根据类型获取模板
	 */
	@ResponseBody
	@RequestMapping("/getByName")
	public Map<String, List<NoticeModel>> getByName(NoticeModel noticeModel){
		@SuppressWarnings("unchecked")
		List<NoticeModel> noticeModelList = noticeModelService.queryList(noticeModel);
		Map<String, List<NoticeModel>> modelMap = new LinkedHashMap<String, List<NoticeModel>>();
		for (NoticeModel model:noticeModelList) {
			List<NoticeModel> value = modelMap.get(model.getNoticeType());
			if (value==null) {
				value = new ArrayList<NoticeModel>();
			}
			value.add(model);
			modelMap.put(model.getNoticeType(), value);
		}
		return modelMap;
	}
	
	/**
	 * 根据名称获取小贷已启用岗位
	 */
	@ResponseBody
	@RequestMapping("/getQuarters")
	public List<Quarters> getQuarters(Quarters quarters){
		quarters.setServerId("lhhs_spark");
		quarters.setStatus("1");
		List<Quarters> quartersList = quartersApi.queryList(quarters);
		return quartersList;
	}
	
	/**
	 * 保存模板信息
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,String> save(NoticeModel params,String noticeTypeAll,String receiverAll){
		Map<String,String> map = new HashMap<String,String>();
		String modelStatus = params.getModelStatus();
		if (StringUtils.isEmpty(modelStatus)) {//保存
			params.setModelStatus("1");
			@SuppressWarnings("unchecked")
			List<NoticeModel> noticeModelList = noticeModelService.queryList(params);
			if (noticeModelList!=null&&noticeModelList.size()>0) {
				map.put(SystemConst.retCode, SystemConst.FAIL);
				map.put(SystemConst.retMsg, "模板已存在");
			}else {
				String [] ids = noticeTypeAll.split(",");
//				params.setReceiver(receiverAll);
				params.setIdsArray(ids);
				int code =  noticeModelService.updateModelStatus(params);
				if(code >= 1){
					map.put(SystemConst.retCode, SystemConst.SUCCESS);
				}else{
					map.put(SystemConst.retCode, SystemConst.FAIL);
				}
			}
		}else {//编辑
			//将此类模板状态更新为未选择
			params.setModelStatus("0");
//			params.setReceiver("");
			noticeModelService.updateModelStatus(params);
			if (StringUtils.isEmpty(noticeTypeAll)) {
				params.setIdsArray(null);
			}else {
				params.setModelStatus(modelStatus);
				params.setIdsArray(noticeTypeAll.split(","));
			}
//			params.setReceiver(receiverAll);
			int code =  noticeModelService.updateModelStatus(params);
			if(code >= 1){
				map.put(SystemConst.retCode, SystemConst.SUCCESS);
			}else{
				map.put(SystemConst.retCode, SystemConst.FAIL);
			}
		}
		return map;
		
	}
	
	
	/**
	 * 编辑
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(NoticeModel noticeModel){
		ModelAndView mav = new ModelAndView("noticeModel/noticeModelEdit");
		//根据模板类型英文名称获取所有模板
		NoticeModel paramModel = new NoticeModel();
		paramModel.setModelType(noticeModel.getModelType());
		paramModel.setEnglishName(noticeModel.getEnglishName());
		@SuppressWarnings("unchecked")
		List<NoticeModel> noticeModelList = noticeModelService.queryList(paramModel);
		Map<String, List<NoticeModel>> modelMap = new LinkedHashMap<String, List<NoticeModel>>();
		for (NoticeModel model:noticeModelList) {
			List<NoticeModel> value = modelMap.get(model.getNoticeType());
			if (value==null) {
				value = new ArrayList<NoticeModel>();
			}
			value.add(model);
			modelMap.put(model.getNoticeType(), value);
		}
		mav.addObject("modelMap", modelMap);
/*		//获取所有岗位
		Quarters param = new Quarters();
		param.setServerId("lhhs_spark");//小贷系统岗位标识
		param.setStatus("1");//启用的岗位
		List<Quarters> allList = quartersApi.queryList(param);
		//处理岗位回显
		String receiver = noticeModel.getReceiver();
		if (StringUtils.isNotEmpty(receiver)) {
			String [] receivers = receiver.split(",");
			for (Quarters quarters : allList) {
				if (Arrays.asList(receivers).contains(quarters.getEnglishName())) {
					quarters.setField1("selected");
				}
			}
		}
		mav.addObject("allList", allList);*/
		String receiver = noticeModel.getReceiver();
		if (StringUtils.isNotEmpty(receiver)) {
			String [] receivers = receiver.split(",");
			List<String> allList = quartersApi.getByEnNames(receivers);
			mav.addObject("allList", allList);
		}
		
		mav.addObject("noticeModel", noticeModel);
		
		return mav;
	}
}

