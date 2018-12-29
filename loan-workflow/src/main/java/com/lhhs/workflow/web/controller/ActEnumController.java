package com.lhhs.workflow.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.workflow.common.enumeration.ActCategory;

/**
 * 工作流枚举
 * @author dongf
 *
 */
@Controller
@RequestMapping(value = "/workflow/enum")
public class ActEnumController {
	@ResponseBody
	@RequestMapping(value = "categoryList")
	public List<Map<String, Object>> getActCategoryList() {
		return ActCategory.getList();
	}
}
