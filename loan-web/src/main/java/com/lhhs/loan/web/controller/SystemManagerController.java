package com.lhhs.loan.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lhhs.bump.api.AuthgroupApi;
import com.lhhs.bump.api.AuthgroupUserApi;
import com.lhhs.bump.api.AuthorityApi;
import com.lhhs.bump.api.AuthorityGroupApi;
import com.lhhs.bump.api.DeptApi;
import com.lhhs.bump.api.QuartersApi;
import com.lhhs.bump.api.RoleApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.api.UserQuartersApi;
import com.lhhs.bump.api.UserRoleApi;
import com.lhhs.bump.domain.Authgroup;
import com.lhhs.bump.domain.Authority;
import com.lhhs.bump.domain.AuthorityGroup;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.Quarters;
import com.lhhs.bump.domain.Role;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.bump.domain.User;
import com.lhhs.bump.domain.UserRole;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.encryption.MD5;
import com.lhhs.loan.common.shared.enums.CredentialsType;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.SecurityUtil;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.common.utils.systemConst;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountCardRevise;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanOperateRecordWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CredentialsService;
import com.lhhs.loan.service.OperateRecordService;
import com.lhhs.loan.service.OrganizationService;
import com.lhhs.loan.service.ProductService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.server.api.UserBaseApi;
import com.lhhs.server.domain.TkServerConst;
import com.lhhs.server.domain.UserBase;
import com.pathcurve.security.CryptoUtils;



/**
 * @author 
 *该Controller是系统设置模块Controller
 */
@Controller
@RequestMapping("/systemManager")
public class SystemManagerController {
	
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	private static final Logger LOGGER = Logger.getLogger(SystemManagerController.class);

	@Autowired
	private SystemManagerService systemManager;
	@Reference
	private AuthorityApi authorityApi;
	@Reference
	private UserApi userApi;
	@Reference
	private RoleApi roleApi;
	@Reference
	private UnionCompanyApi unionCompanyApi;
	@Reference
	private UserQuartersApi userQuartersApi;
	@Reference
	private AuthgroupApi authgroupApi;
	@Reference
	private AuthgroupUserApi authgroupUserApi;
	@Reference
	private AuthorityGroupApi authorityGroupApi;
	@Reference
	private DeptApi deptApi;
	@Reference
	private UserRoleApi userRoleApi;
	@Reference
	private QuartersApi quartersApi;
	@Autowired
	private UserBaseApi userBaseApi;
	@Autowired
	private OperateRecordService operateRecordService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private OrganizationService organizationService;
	
	/********************************角色管理****************************************/
	/**
	 * 查询所有角色
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param roleName
	 * @param status
	 * @return
	 */
	@RequestMapping("/getRoles")
	public ModelAndView getRoles(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "roleName", required = false) String roleName,
			@RequestParam(value = "status", required = false) String status
			){
		ModelAndView modelAndView = new ModelAndView("system/roleManager");
		pageNumber = pageNumber == null ? 1 : pageNumber;
		Role entity = new Role();
		entity.setServerId("lhhs_spark");
		entity.setName(roleName);
		entity.setStatus(status);
		entity.setPageIndex(pageNumber);
		entity.setField1("2");
		CommonUtils.fillBumpCompany(entity);
		com.lhhs.bump.Page<Role>  pageList = roleApi.queryListPage(entity);
		entity.setName(null);
		List<Role> roleNameList =  roleApi.queryList(entity);
		modelAndView.addObject("roleNameList", roleNameList);
		modelAndView.addObject("page", pageList);
		modelAndView.addObject("rname", roleName);
		modelAndView.addObject("status", status);
		return modelAndView;
	}
	
	/**
	 * 新增角色页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toRolesInsert")
	public ModelAndView  toRolesInsert(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("system/roleAdd");
		//查询所有的权限
		Role role = new Role();
		role.setServerId("lhhs_spark");
		List<Map<String,Object>> authList=authorityApi.queryAllAuthTreeJson(role);
		mav.addObject("authList", authList);
		return mav;
	}

	/**
	 * 新增角色
	 * @param LoanRole 角色
	 * @param authIds  权限ids
	 * @return
	 */
	@RequestMapping("/roleAdd")
	@ResponseBody
	public Map<String,Object> roleAdd(Role role){
		Map<String, Object> result=  new HashMap<String, Object>();
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		try {
			role.setServerId("lhhs_spark");
			role.setLgUnionId(user.getUnionId());
			role.setLgCompanyId(user.getCompanyId());
			role.setLgUserId(String.valueOf(user.getUserId()));
		    result = roleApi.saveRoleAuthority(role);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "添加角色失败!");
		}
		return result;
	}
	
	/**
	 * 修改角色页面
	 * @return
	 */
	@RequestMapping("/toRoleUpdate")
	public ModelAndView toRoleUpdate(HttpServletRequest request,Integer roleId){
		ModelAndView model = new ModelAndView("system/roleUpdate");
		//查询角色
		Role role = roleApi.get(String.valueOf(roleId));
		//查询所有的权限
		role.setServerId("lhhs_spark");
		List<Map<String,Object>> authList=authorityApi.queryAllAuthTreeJson(role);
		model.addObject("role", role==null?new Role():role);
		model.addObject("authList", authList);
		return model;
	}
	
	/**
	 * 修改角色
	 * @param LoanRole 角色
	 * @param authIds  权限ids
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String,Object> updateRole(Role role){
		role.setServerId("lhhs_spark");
		Map<String, Object> result = roleApi.updateRoleAuthority(role);
		return result;
	}
	
	/**
	 * 角色禁用、启用
	 */
	@RequestMapping("/roleIfEnable")
	@ResponseBody
	public Map<String,Object> roleIfEnable(HttpServletRequest request,String ifEnable,String roleId){
		Map<String,Object> result = new HashMap<String,Object>();
		Role role = new Role();
		role.setRoleId(roleId);
		role.setStatus(ifEnable);
		int  count = roleApi.update(role);
		if(count>0){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "操作成功");
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "操作失败");
		}
		return result;
	}
	
/********************************部门管理****************************************/
	
	/**
	 * 部门列表
	 * @return
	 * 
	 */
	@RequestMapping("/getDepartments")
	public ModelAndView getDepartments(HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "ldDeptId", required = false) Integer ldDeptId,
			@RequestParam(value = "ldName", required = false) String ldName,
			@RequestParam(value = "companyId", required = false) String companyId,
			@RequestParam(value = "status", required = false) String status
			){
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		ModelAndView modelAndView = new ModelAndView("system/deptmanager");
		Map<String,Object> params = new HashMap<String,Object>();
 		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		params.put("companyId", companyId);
		params.put("ldDeptId", ldDeptId);
		params.put("ldName", ldName);
		params.put("status", status);
		params.put("page", page);
		CommonUtils.fillCompanyToMap(params);
		com.lhhs.bump.Page<UnionCompany> unionCompanyList=null;
		List<UnionCompany> companyList=null;
		if(user.getUserId().longValue()==1){
		    companyList = CommonUtils.getCompanyListToMap(params);
			modelAndView.addObject("isAdmin", "admin");
		}
		Dept saasDept=new Dept();
		saasDept.setStatus(status);
		saasDept.setDeptId(ldDeptId);
		if(!"admin".equals(user.getUsername())) saasDept.setUnionId(user.getUnionId());
		if(StringUtils.isNotEmpty(companyId)){
			saasDept.setCompanyId(companyId);
		}else if(!"admin".equals(user.getUsername())){
			saasDept.setCompanyId(user.getCompanyId());
		}
		saasDept.setPageIndex(page.getPageIndex());
		com.lhhs.bump.Page<Dept> loanDepartments=deptApi.queryListPage(saasDept);
		List<Dept> loanDepartList=new ArrayList<Dept>();
		if(null!=loanDepartments){
			loanDepartList=(List<Dept>) loanDepartments.getResultList();
		}
		modelAndView.addObject("LoanDepartmentsList", loanDepartList);
		if(StringUtils.isNotEmpty(companyId)&&user.getUserId().longValue()==1){
			List<Dept> deptList = CommonUtils.getDeptList(companyId);
			if(null!=deptList&&deptList.size()>0){
				for(Dept dept:deptList){
					if(StringUtils.isNotEmpty(dept.getName())&&dept.getName().contains("&nbsp;")){
						dept.setName(dept.getName().replaceAll("&nbsp;", ""));
					}
				}
			}
			modelAndView.addObject("searchList", deptList);
		}else{
			modelAndView.addObject("searchList", CommonUtils.getDeptListByManage(""));
		}
		modelAndView.addObject("companys", companyList);
		modelAndView.addObject("companyId", companyId);
		modelAndView.addObject("ldDeptId", ldDeptId);
		modelAndView.addObject("ldName", ldName);
		modelAndView.addObject("status", status);
		modelAndView.addObject("page", loanDepartments);
		return modelAndView;
	}
	/**
	 * 新增部门-页面
	 * @return
	 */
	@RequestMapping("/toDeptInsert")
	public ModelAndView toDeptInsert(){
		SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		ModelAndView model = new ModelAndView("system/newdeptadd");
		if("admin".equals(user.getUsername())){
			model.addObject("isAdmin",true);
			UnionCompany company=new UnionCompany();
			company.setParentCompanyId("0");
			model.addObject("cliqueList", unionCompanyApi.queryAllUnion(company));
		}else{
			model.addObject("isAdmin",false);
			List<Dept> deptList = CommonUtils.getDeptList(user.getCompanyId());
			model.addObject("searchList", deptList);
		}
		return model;
	}
	
	/**
	 * 根据公司ID查询所属部门
	 * @return
	 */
	@RequestMapping("/queryDeptByCompanyId")
	@ResponseBody
	public  Map<String, Object> queryDeptByCompanyId(String companyId){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<LoanDept> list=systemManager.queryDeptByCompanyId(companyId);
		resultMap.put("deptList", list);
		return resultMap;
	}
	
	/**
	 *  新增部门
	 * @return
	 */
	@RequestMapping("/departmentsInsert")
	@ResponseBody
	public Map<String,Object> departmentsInsert(HttpServletRequest request,	LoanDept dept){
		Map<String, Object> result=new HashMap<String, Object>();
		SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		Dept saasDept=new Dept();
		if(StringUtils.isNotEmpty(dept.getLdUnion())){
			saasDept.setUnionId(dept.getLdUnion());
		}else{
			saasDept.setUnionId(user.getUnionId());
		}
		if(StringUtils.isNotEmpty(dept.getLdCompany())){
			saasDept.setCompanyId(dept.getLdCompany());
		}else{
			saasDept.setCompanyId(user.getCompanyId());
		}
		if(null!=dept.getParentId()){
			saasDept.setParentId(dept.getParentId());
		}else{
			saasDept.setParentId(0);
		}
		if(StringUtils.isNotEmpty(dept.getLdName())){
			saasDept.setName(dept.getLdName());
		}
		if(StringUtils.isNotEmpty(dept.getLdDepict())){
			saasDept.setDepict(dept.getLdDepict());
		}
		if(StringUtils.isNotEmpty(dept.getLdRemark())){
			saasDept.setRemark(dept.getLdRemark());
		}
		
		Dept deptCount = deptApi.get(saasDept);
		if (null!=deptCount) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg,"\u90e8\u95e8\u5df2\u5b58\u5728\uff0c\u8bf7\u52ff\u91cd\u590d\u6dfb\u52a0\uff01");
			return result;
		}
		int count=deptApi.save(saasDept);
		if (count >= 1) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u65b0\u589e\u90e8\u95e8\u6210\u529f");
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u65b0\u589e\u90e8\u95e8\u5931\u8d25");
		}
		return result;
	}
	
	/**
	 * 修改部门-页面
	 * @return
	 */
	@RequestMapping("/toDeptUpdate")
	public ModelAndView toDeptUpdate(
			HttpServletRequest request,
			@RequestParam(value="deptId",required=false)Integer deptId
			){
		Map<String, Object> map=new HashMap<String,Object>();
		SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		Dept dept=new Dept();
		dept.setDeptId(deptId);
		dept=deptApi.get(dept);
		ModelAndView model = new ModelAndView("system/deptmodify");
		if("admin".equals(user.getUsername())){
			model.addObject("isAdmin",true);
			UnionCompany company=new UnionCompany();
			company.setParentCompanyId("0");
			model.addObject("cliqueList", unionCompanyApi.queryAllUnion(company));//集团列表
			model.addObject("companyList", unionCompanyApi.queryCompany(dept.getUnionId()));//公司列表
		}else{
			model.addObject("companyList", unionCompanyApi.queryCompany(dept.getUnionId()));//公司列表
			model.addObject("isAdmin",false);
		}
		dept.setServerId("dept");
		List<Dept> deptList = CommonUtils.getDeptListByDept(dept);
		List<Dept> deptList2=new ArrayList<Dept>();;
		if(null!=deptList){
			for (Dept dept2 : deptList) {
				if(null!=dept2.getParentIds()&&dept2.getParentIds().indexOf(deptId.toString())< 0){
						deptList2.add(dept2);
				}
			}
		}
		model.addObject("searchList", deptList2);
		model.addObject("deptId", deptId);
		model.addObject("department", dept);
		return model;
	}	
	/**
	 * 修改部门
	 * @return
	 */
	@RequestMapping("/departmentsUpdate")
	@ResponseBody
	public Map<String,Object> departmentsUpdate(HttpServletRequest request,Dept dept){
		Map<String, Object> result=new HashMap<String, Object>();
		int count=deptApi.save(dept);
		if (count >= 1) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u90e8\u95e8\u4fee\u6539\u6210\u529f");
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u90e8\u95e8\u4fee\u6539\u5931\u8d25");
		}
		return result;
	}
	
	/**
	 * 修改部门状态-禁用/启用
	 * @return
	 */
	@RequestMapping("/ifDeptEnable")
	@ResponseBody
	public Map<String,Object> ifDeptEnable(HttpServletRequest request,String ldStatus,Integer ldDeptId){
		Dept dept=new Dept();
		Map<String,Object> result = new HashMap<String,Object>();
		dept.setDeptId(ldDeptId);
		if("1".equals(ldStatus)) dept.setStatus("0"); else dept.setStatus("1");
		int count=deptApi.save(dept);
		if (count >= 1) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
		}
	    return result;
	}
	
	/******************************************岗位管理**************************************************/
	
	/**
	 * 岗位列表
	 * @return
	 */
	@RequestMapping("/getQuarters")
	public ModelAndView getQuarters(Quarters params){
		ModelAndView modelAndView = new ModelAndView("system/quartersManager");
		params.setServerId("lhhs_spark");
		com.lhhs.bump.Page<Quarters> page = quartersApi.queryListPage(params);
		Quarters quarters = new Quarters();
		quarters.setServerId("lhhs_spark");
		List<Quarters> allList = quartersApi.queryList(quarters);
		modelAndView.addObject("page", page);
		modelAndView.addObject("allList", allList);
		modelAndView.addObject("params", params);
		return modelAndView;
	}
	/**
	 * 跳转岗位新增页面
	 * @return
	 */
	@RequestMapping("/toQuartersAdd")
	public ModelAndView toQuarterInsert() {
		ModelAndView model = new ModelAndView("system/quartersAdd");
		SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		if("admin".equals(user.getUsername())){
			UnionCompany unionCompany = new UnionCompany();
			unionCompany.setParentCompanyId("0");
			List<UnionCompany> companyList = unionCompanyApi.queryAllUnion(unionCompany);
			model.addObject("isAdmin",true);
			model.addObject("companyList",companyList);
		}else{
			model.addObject("isAdmin",false);
		}
		return model;
	}
	
	/**
	 * 新增岗位
	 * @return
	 */
	@RequestMapping("/quarterAdd")
	@ResponseBody
	public Map<String,Object> quarterAdd(Quarters quarters) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			if(quarters!=null&&StringUtils.isEmpty(quarters.getUnionId())){
				SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
				quarters.setUnionId(user.getCompanyId());
			}
			quarters.setServerId("lhhs_spark");
			resultMap = quartersApi.insertQuarters(quarters);
		} catch (Exception e) {
			LOGGER.error("系统异常：",e);
			resultMap.put("retCode", "01");
			resultMap.put("retMsg", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 跳转修改岗位
	 * @return
	 */
	@RequestMapping("/toQuarterUpdate")
	public ModelAndView toQuarterUpdate(String quartersId) {
		ModelAndView model = new ModelAndView("system/quartersUpdate");
		SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		Quarters quarters =quartersApi.get(quartersId);
		if("admin".equals(user.getUsername())){
			UnionCompany unionCompany = new UnionCompany();
			unionCompany.setParentCompanyId("0");
			List<UnionCompany> companyList = unionCompanyApi.queryAllUnion(unionCompany);
			model.addObject("isAdmin",true);
			model.addObject("companyList",companyList);
		}else{
			model.addObject("isAdmin",false);
		}
		model.addObject("quarters", quarters);
		return model;
	}
	
	/**
	 * 修改岗位
	 * @param request
	 * @param quarters
	 * @return
	 */
	@RequestMapping("/quartersUpdate")
	@ResponseBody
	public Map<String,Object> quartersUpdate(Quarters quarters) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			if(quarters!=null&&StringUtils.isEmpty(quarters.getUnionId())){
				SecurityUser user = (SecurityUser)SecurityUserHolder.getCurrentUser();//获得当前登录用户
				quarters.setUnionId(user.getCompanyId());
			}
			resultMap = quartersApi.updateQuarters(quarters);
		} catch (Exception e) {
			LOGGER.error("系统异常：",e);
			resultMap.put("retCode", "01");
			resultMap.put("retMsg", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 修改岗位禁用/启用状态
	 * @return
	 */
	@RequestMapping("/quartersEnable")
	@ResponseBody
	public Map<String,Object> quartersEnable(Quarters quarters){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			resultMap = quartersApi.quartersEnabled(quarters);
		} catch (Exception e) {
			LOGGER.error("系统异常：",e);
			resultMap.put("retCode", "01");
			resultMap.put("retMsg", e.getMessage());
		}
		return resultMap;
	}
	
/*******************************************************密码修改*********************************************************************/	
	
	/**
	 *  系统设置-修改密码-进入页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/passwordModify")
	public ModelAndView passwordModify(
		HttpServletRequest request
			){
		ModelAndView model=new ModelAndView("system/passwordmodify");
		return model;
	}
	
	/**
	 * 系统设置-修改密码-新密码验证
	 * @param oldPassword
	 * @param newPassword
	 * @param newPasswordTwo
	 * @return
	 */
	@RequestMapping("/newPassSet")
	@ResponseBody
	public Map<String, Object> newPassSet(User user){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(SystemConst.retCode, SystemConst.FAIL);
		map.put(SystemConst.retMsg, "修改失败");//登陆成功
		if(user==null ||StringUtils.isEmpty(user.getPassword())
				||StringUtils.isEmpty(user.getNewPassword())){
			map.put(SystemConst.retMsg, "原始密码或者新密码不能为空!");
			return map;
		}
		User  cur = (User)SecurityUserHolder.getCurrentUser();//获得当前登录用户
		
		//查询用户信息
		User temp=userApi.get(cur.getUserId().toString());
		if(temp==null){
			map.put(SystemConst.retMsg, "用户不存在!");
			return map;
		}
		user.setUsername(temp.getUsername());
		/*//验证旧密码是否正确
		String oldpassword=SecurityUtil.encryptDes(temp.getEncryption(), user.getPassword());
		//原始密码不正确
		if(!temp.getPassword().equals(oldpassword)){
			map.put(SystemConst.retMsg, "原始密码不正确!");
			return map;
		}*/
		/*//secretKey应送用户登录密码的MD516进制字符串，
        //inputPass应送分散因子，
        //storePass为数据库中存储的密码
		boolean rfc_unsafe_convert = false;
        try {
			rfc_unsafe_convert = CryptoUtils.rfc_unsafe_convert(user.getPassword(), temp.getEncryption(), temp.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(!rfc_unsafe_convert) {
        	map.put(SystemConst.retMsg, "原始密码不正确!");
 			return map;
        }*/
		try {
			String encrypt = CryptoUtils.encrypt(temp.getEncryption(),user.getPassword());
			if(!temp.getPassword().equals(encrypt)){
				map.put(SystemConst.retMsg, "原始密码不正确!");
				return map;
			}
		} catch (Exception e) {
		}
		user.setLastUser(cur.getUserId().toString());
		user.setUserId(cur.getUserId());
		int flag=userApi.resetPassword(user);
	 	if(flag>0){
	 		map.put(SystemConst.retCode, SystemConst.SUCCESS);
	 		map.put(SystemConst.retMsg, "修改成功");
	 	}
		return map;
		
	}

/*******************************************组别模块**************************************************************************/
	/**
	 * 系统管理-权限管理-组别列表
	 * @param request
	 * @param deptId
	 * @param groupName
	 * @param status
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/groupList")
	public String groupList(
			HttpServletRequest request,
			@RequestParam(value="deptId",required=false)Integer deptId,
			@RequestParam(value="lgGroupId",required=false)Integer lgGroupId,
			@RequestParam(value="status",required=false)String status,
			@RequestParam(value="pageNumber",required=false)Integer pageNumber
			){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		paramMap.put("deptId", deptId);
		paramMap.put("lgGroupId", lgGroupId);
		paramMap.put("status", status);
		paramMap.put("page", page);
		List<LoanGroup> list = systemManager.groupList(paramMap, page);
		List<Map<String,Object>> deptList = systemManager.queryDeptThree();
		LoanGroup loangroup=systemManager.selectByPrimaryKey(lgGroupId);
		if(loangroup!=null){
		   request.setAttribute("groupname", loangroup.getLgName()==null?"":loangroup.getLgName());
		}
		request.setAttribute("allDept", systemManager.queryDeptAll());
		request.setAttribute("allGroup", systemManager.allGroup());
		request.setAttribute("groupList", list);
		request.setAttribute("deptList", deptList);
		request.setAttribute("deptId", deptId);
		request.setAttribute("status", status);
		request.setAttribute("page", page);
		return "system/groupmanager";
	}
		
	/**
	 * 系统管理-权限管理-组别修改(信息回显)
	 * @param request
	 * @param lgGroupId
	 * @return
	 */
	@RequestMapping("/toGroupUpdate")
	public String toGroupUpdate(
			HttpServletRequest request,
			@RequestParam(value="lgGroupId",required=false)Integer lgGroupId
			){
		List<Map<String,Object>> deptList = systemManager.queryAllDept();
		LoanGroup group = systemManager.selectGroupInfo(lgGroupId);
		request.setAttribute("deptList", deptList);
		request.setAttribute("group", group);
		request.setAttribute("lgGroupId", lgGroupId);
		return "system/groupmodify";
	}	
	/**
	 * 系统管理-权限管理-组别信息修改
	 * @param request
	 * @param deptId
	 * @param groupId
	 * @param groupName
	 * @param groupDept
	 * @param groupRemark
	 * @return
	 */
	@RequestMapping("/groupModify")
	@ResponseBody
	public Map<String, Object> groupModify(
			HttpServletRequest request,
			//@RequestParam(value="group",required=false)LoanGroup group,
			@RequestParam(value="lgDeptId",required=false)Integer lgDeptId,
			@RequestParam(value="lgGroupId",required=false)Integer lgGroupId,
			@RequestParam(value="lgName",required=false)String lgName,
			@RequestParam(value="lgDepict",required=false)String lgDepict,//描述
			@RequestParam(value="lgRemark",required=false)String lgRemark//备注
			){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		resultMap.put("lgDeptId", lgDeptId);
		resultMap.put("lgGroupId", lgGroupId);
		resultMap.put("lgName", lgName);
		LoanGroup grp=systemManager.queryGroupCount(resultMap);
		if(grp!=null && !grp.getLgName().equals(lgName)){
			int groupcount=systemManager.queryGroupbyName(resultMap);
			if(groupcount>=1){
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u8bf7\u52ff\u6dfb\u52a0\u91cd\u590d\u7ec4\u522b");
				return resultMap;
			}
		}
		LoanGroup group=new LoanGroup();
		group.setLgGroupId(lgGroupId);
		group.setLgDeptId(lgDeptId);
		group.setLgName(lgName);
		group.setLgDepict(lgDepict);
		group.setLgRemark(lgRemark);
		int count = systemManager.groupUpdateById(group);
		if(count>=1){
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u4fee\u6539\u6210\u529f");
		}else{
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u4fee\u6539\u5931\u8d25");
		}
		return resultMap;
	}
	/**
	 * 系统管理-权限管理-组别管理-新增组别页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/addNewGroup")
	public String addNewGroup(
			HttpServletRequest request			
			){
		   List<Map<String,Object>> deptList = systemManager.queryAllDept();
		   request.setAttribute("deptList",deptList);
		   return "system/groupadd";
	}	
	/**
	 * 系统管理-权限管理-组别管理-新增
	 * @param request
	 * @param deptId
	 * @param groupId
	 * @param groupName
	 * @param groupDept
	 * @param groupRemark
	 * @return
	 */
	@RequestMapping("/newGroupAdd")
	@ResponseBody
	public Map<String, Object> newGroupAdd(
			HttpServletRequest request,
			@RequestParam(value="lgDeptId",required=false)Integer lgDeptId,	
			@RequestParam(value="lgName",required=false)String lgName,
			@RequestParam(value="lgDepict",required=false)String lgDepict,//描述
			@RequestParam(value="lgRemark",required=false)String lgRemark//备注
			){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		resultMap.put("lgDeptId", lgDeptId);
		resultMap.put("lgName", lgName);
		int groupcount=systemManager.queryGroupbyName(resultMap);
		if(groupcount>=1){
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u8bf7\u52ff\u6dfb\u52a0\u91cd\u590d\u7ec4\u522b");
			return resultMap;
		}
		LoanGroup group=new LoanGroup();
		group.setLgDeptId(lgDeptId);
		group.setLgName(lgName);
		group.setLgDepict(lgDepict);
		group.setLgStatus("1"); //默认启用		
		group.setLgTime(new Date());
		group.setLgRemark(lgRemark);
		int count = systemManager.insertGroup(group);
		if(count>=1){
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u65b0\u589e\u7ec4\u522b\u6210\u529f");
		}else{
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u65b0\u589e\u7ec4\u522b\u5931\u8d25");
		}
		return resultMap;
	}
	
	@RequestMapping("/enableOrDisableSet")
	@ResponseBody
	public Map<String,Object> enableOrDisableSet(
			HttpServletRequest request,
			String lgStatus,
			Integer lgGroupId
			){
	    LoanGroup group=new LoanGroup();
		group.setLgGroupId(lgGroupId);
		group.setLgStatus(lgStatus);
		return systemManager.updateGroupByStuts(group);
	}
	
	
/*******************************************产品管理***************************************************************************/	
	/**
	 * 系统管理-产品信息管理
	 * @param request
	 * @param productType
	 * @return
	 */
	@RequestMapping("/productInfoList")
	public String productInfoList(
			HttpServletRequest request,
			@RequestParam(name="productNo",required=false)String productNo
			){		
		List<LoanParentProduct> list = systemManager.queryProductList(productNo);
		request.setAttribute("allProduct", systemManager.queryAllProduct());
		request.setAttribute("productList", list);
		request.setAttribute("productNo", productNo);
		return "system/parentproductlist";
	}
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/jumpProduct")
	public String jumproduct(HttpServletRequest request){
		return "system/addParentProduct";
	}
	
	/**
	 * 添加一级产品
	 * @param request
	 * @param productType 产品类型
	 * @param productMoney 一级产品金额
	 * @param productTerm 产品期限
	 * @param productTermUnit 产品期限单位
	 * @param productInterest一级产品利率
	 * @param thumbnailPicture一级产品缩略图url
	 * @return
	 */
	@RequestMapping("/addParentProduct")
	@ResponseBody
	public Map<String,Object> addParentProduct(LoanParentProduct product){
		Map<String,Object> result = new HashMap<String,Object>();
		List<LoanParentProduct> list = systemManager.queryProductListByType(product.getProductType());
		if (list!=null&&list.size()>0) {
			result.put(systemConst.retCode, systemConst.FAIL);
			result.put(systemConst.retMsg, "产品类型已存在");
		}else {
			Integer count = productService.addParentProduct(product);
			if(count!=null && count>0){
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u6dfb\u52a0\u4ea7\u54c1\u6210\u529f");//添加产品成功
			}else{
				result.put(systemConst.retCode, systemConst.FAIL);
				result.put(systemConst.retMsg, "\u6dfb\u52a0\u4ea7\u54c1\u5931\u8d25");//失败
			}
		}
		return result;
	}
	
	/**
	 * 禁用、启用
	 * @param request
	 * @param productNo 一级产品编号
	 * @param ifEnable 状态标识
	 * @return
	 */
	@RequestMapping("productIfEnable")
	@ResponseBody
	public Map<String,Object> productIfEnable(String productNo,String ifEnable){
		return productService.productIfEnable(productNo,ifEnable);
	}
	
	/**
	 * 系统管理-产品管理-产品管理-一级产品查看/修改
	 * @param request
	 * @param product_no
	 * @return
	 */
	@RequestMapping("/productChildrenList/{isUpdate}")
	public String productChildrenList(
			HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(name="productNo",required=true)String productNo,
			@RequestParam(name="productName",required=false)String productName,
			@PathVariable("isUpdate") String isUpdate
			){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		LoanParentProduct product = systemManager.queryProductInfo(productNo);
		page.setPageIndex(pageNumber);
		
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("product_no", productNo);
		paramMap.put("product_name", productName);
		List<LoanChildProduct> productChildrenList=systemManager.queryProductById(paramMap);
		page.setTotalCount(productChildrenList.size());
		paramMap.put("page", page);
		List<LoanChildProduct> productChildrenListFy=systemManager.queryProductById(paramMap);
		request.setAttribute("page", page);
		request.setAttribute("product", product);
		request.setAttribute("productName", productName);
		request.setAttribute("productList", productChildrenListFy);
		if("1".equals(isUpdate)){
			return "system/updateProduct";
		}
		return "system/parentproductinfo";
	}
	
	/**
	 * 更新一级产品
	 * @param product
	 * @return
	 */
	@RequestMapping("/updateProduct")
	@ResponseBody
	public Map<String,Object> updateProduct(LoanParentProduct product){
		Map<String,Object> result = new HashMap<String,Object>();
		
		LoanParentProduct parentProduct =  systemManager.queryProductInfo(product.getProductNo());
		
		List<LoanParentProduct> list = systemManager.queryProductListByType(product.getProductType());
		if (list!=null&&list.size()>0&&(!parentProduct.getProductType().equals(product.getProductType()))) {
			result.put(systemConst.retCode, systemConst.FAIL);
			result.put(systemConst.retMsg, "产品类型已存在");
		}else {
			Integer count = productService.updateParentProduct(product);
			if(count!=null && count>0){
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u66f4\u65b0\u4ea7\u54c1\u6210\u529f");//更新产品成功
			}else{
				result.put(systemConst.retCode, systemConst.FAIL);
				result.put(systemConst.retMsg, "\u66f4\u65b0\u4ea7\u54c1\u5931\u8d25");//失败
			}
		}
		return result;
	}
	
	/**
	 * 添加二级产品
	 */
	@RequestMapping(value = "/addChildrenProduct")	
	public String addChildrenProduct(String productNo,ModelMap modelMap ){
		modelMap.put("productParentNo", productNo);
		LoanCredentials credentials = new LoanCredentials();
		credentials.setStatus("1");
		modelMap.put("credentialList", credentialsService.queryList(credentials));
		LoanOrganization o = new LoanOrganization();
		o.setStatus("03");
		modelMap.put("fundSideList", organizationService.queryList(o));
		return "system/childproductAdd";
	}
	
	/**
	 * 保存二级产品
	 */
	@RequestMapping(value = "/saveChildrenProduct")	
	@ResponseBody
	public Map<String, Object> saveChildrenProduct(LoanChildProductWithBLOBs childProduct){
		Map<String,Object> result = new HashMap<String,Object>();	
		int count = productService.saveChildProductByEntity(childProduct);
		if(count>0){
			result.put(systemConst.retCode, systemConst.SUCCESS);
			result.put(systemConst.retMsg, "操作成功");
		}else {
			result.put(systemConst.retCode, systemConst.FAIL);
			if (count==-2) {
				result.put(systemConst.retMsg, "二级产品编号重复");
			} else {
				result.put(systemConst.retMsg, "操作失败");
			}
		}
		return result;
	}
	/**
	 * 根据资金方id获取资金方支持的省
	 */
	@RequestMapping(value = "/getOrgSupportProvince")	
	@ResponseBody
	public List<LoanOrgSupportAreas> getOrgSupportProvince(LoanOrgSupportAreas orgSupportAreas){
		return productService.getOrgSupportProvince(orgSupportAreas);
	}
	/**
	 * 根据资金方id获取资金方支持的市
	 */
	@RequestMapping(value = "/getOrgSupportCity")	
	@ResponseBody
	public List<LoanOrgSupportAreas> getOrgSupportCity(LoanOrgSupportAreas orgSupportAreas){
		return productService.getOrgSupportCity(orgSupportAreas);
	}
	/**
	 * 修改二级产品
	 */
	@RequestMapping(value = "/editChildrenProduct")	
	public String editChildrenProduct(String id,String productNo,ModelMap modelMap ){
		LoanChildProductWithBLOBs entity = productService.getCredentialsProductById(id);
		modelMap.put("entity", entity);
		modelMap.put("productNo", productNo);
		LoanOrganization o = new LoanOrganization();
		o.setStatus("03");
		modelMap.put("fundSideList", organizationService.queryList(o));
		return "system/childproductEdit";
	}
	/**
	 * 保存修改二级产品
	 */
	@RequestMapping(value = "/updateChildrenProduct")	
	@ResponseBody
	public Map<String, Object> updateChildrenProduct(LoanChildProductWithBLOBs childProduct){
		Map<String,Object> result = new HashMap<String,Object>();	
		int count = productService.updateChildProduct(childProduct);
		if(count>0){
			result.put(systemConst.retCode, systemConst.SUCCESS);
			result.put(systemConst.retMsg, "操作成功");
		}else {
			result.put(systemConst.retCode, systemConst.FAIL);
			if (count==-2) {
				result.put(systemConst.retMsg, "二级产品编号重复");
			} else {
				result.put(systemConst.retMsg, "操作失败");
			}
		}
		return result;
	}
	
	/**
	 * 二级产品禁用、启用
	 */
	@RequestMapping("/enabled")
	@ResponseBody
	public Map<String,Object> enabled(LoanChildProduct childProduct){
		return productService.updateChildProductByEntity(childProduct);
	}
	
	/**
	 * 系统管理-产品管理-产品管理-查看二级产品详情
	 * @param product_id
	 * @return
	 */
	@RequestMapping("/productChildInfo")
	public String productChildInfo(
			HttpServletRequest request,
			@RequestParam(name="productId",required=true)String productId
			){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> credentZero=systemManager.proCredList(productId,CredentialsType.BASE.getIndex());//基本资质
		if(credentZero.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
			request.setAttribute("credentZero", credentZero);
		}
		Map<String, Object> credentOne=systemManager.proCredList(productId,CredentialsType.HOUSE.getIndex());//房产资质
        if(credentOne.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
        	request.setAttribute("credentOne", credentOne);
		}
		Map<String, Object> credentTwo=systemManager.proCredList(productId,CredentialsType.CAR.getIndex());//车产资质
        if(credentTwo.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
        	request.setAttribute("credentTwo", credentTwo);
		}
		Map<String, Object> credentThree=systemManager.proCredList(productId,CredentialsType.CREDIT.getIndex());//信用资质
        if( credentThree.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
        	request.setAttribute("credentThree",  credentThree);
		}
		Map<String, Object>  credentFour=systemManager.proCredList(productId,CredentialsType.AGREEMENT.getIndex());//合同资质
        if( credentFour.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
        	request.setAttribute("credentFour",  credentFour);
		}
		Map<String, Object> credentFive=systemManager.proCredList(productId,CredentialsType.OTHER.getIndex());//其他资质
        if(credentFive.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
        	request.setAttribute("credentFive", credentFive);
		}
		resultMap = systemManager.queryChildProductInfo(productId);
		request.setAttribute("productInfo", resultMap.get("product"));
		request.setAttribute("credentialsList", resultMap.get("credentials"));
		return "system/chilrenproductinfo";
	}
	
/*********************************************后台账号管理模块******************************************************************/	
  
	/**
	 * 系统管理-权限设置-后台账号管理
	 * @param request
	 * @param leCity
	 * @param leStaffName
	 * @param leDeptId
	 * @param leQuartersId
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/employeeList")
	public ModelAndView employeeList(
			HttpServletRequest request,
			@RequestParam(name="leCity",required=false)String leCity,
			@RequestParam(name="leStaffName",required=false)String leStaffName,
			@RequestParam(name="companyId",required=false)String companyId,
			@RequestParam(name="leDeptId",required=false)String leDeptId,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber			
			){
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		Map<String, Object> map=new HashMap<String,Object>();
		ModelAndView model=new ModelAndView("system/employeeManager");
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("leCity", leCity);
		map.put("leStaffName", leStaffName);
		map.put("leDeptId", leDeptId);
		//查询不用现在管理员城市
		map.put("page", page);
		CommonUtils.fillCompanyToMap(map);
		map.put("companyId", companyId);
		List<UnionCompany> companyList = CommonUtils.getCompanyListToMap(map);
		User user = new User();
		user.setCityName(leCity);
		user.setStaffName(leStaffName);
		user.setCompanyId(companyId);
		user.setDeptId(leDeptId);
		user.setPageIndex(pageNumber);
		/**
		if(!"admin".equals(loanEmp.getUsername())){
			user.setUnionId(loanEmp.getUnionId());
			user.setCompanyId(loanEmp.getCompanyId());
		}
		**/
		user.setServerId("lhhs_spark");
//		CommonUtils.fillBumpCompany(user);
		//		com.lhhs.bump.Page<User> empList=userApi.employeeList(user);
		com.lhhs.bump.Page<User> empList=userApi.queryListPageXd(user);
		if(loanEmp.getUserId()== 1){
			model.addObject("isAdmin", "admin");
		//	model.addObject("allDept", userApi.queryDeptAll());
		}else{
			model.addObject("isAdmin", "noadmin");
			//model.addObject("allDept", CommonUtils.getDeptList(null));
		}
		if(StringUtils.isNotEmpty(companyId)&&loanEmp.getUserId().longValue()==1){
			List<Dept> deptList = CommonUtils.getDeptList(companyId);
			if(null!=deptList&&deptList.size()>0){
				for(Dept dept:deptList){
					if(StringUtils.isNotEmpty(dept.getName())&&dept.getName().contains("&nbsp;")){
						dept.setName(dept.getName().replaceAll("&nbsp;", ""));
					}
				}
			}
			model.addObject("searchList", deptList);
		}else{
			model.addObject("searchList", CommonUtils.getDeptListByManage(""));
		}
		model.addObject("companys", companyList);
		model.addObject("allCity", userApi.queryAllCity());
		model.addObject("page", empList);
		model.addObject("leCity", leCity);
		model.addObject("leStaffName", leStaffName);
		model.addObject("leDeptId", leDeptId);
		model.addObject("companyId", companyId);
	//	model.addObject("page", page);
		return model;
	}
	/**
	 * 系统管理-权限设置-后台账号管理-修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/toEmpUpdate")
	public String toEmpUpdate(
			HttpServletRequest request,
			Integer leEmpId
			){
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		User user =new User();
		user.setUserId(Long.valueOf(leEmpId));
		UnionCompany unionCompany=new UnionCompany();
		unionCompany.setParentCompanyId("0");
		User emp=userApi.get(user);
		Role entity = new  Role();
		entity.setServerId("lhhs_spark");
		if(!"admin".equals(loanEmp.getUsername())) {
		/*	entity.setUnitId(loanEmp.getUnionId());
			entity.setCompanyId(loanEmp.getCompanyId());*/
			unionCompany.setUnionId(loanEmp.getUnionId());
			unionCompany.setCompanyId(loanEmp.getCompanyId());
		}
		List<Role> roleList = roleApi.queryList(entity);
		UserRole userRole =new UserRole();
		userRole.setUserId(""+emp.getUserId());
		List<UserRole> myRoles = userRoleApi.queryList(userRole);
		
		List<UnionCompany> companyList=null;
		for(int i=0;i<roleList.size();i++){ //遍历为每一个 角色添加标示
			roleList.get(i).setIsSelect("0");
		}
		for (UserRole ur : myRoles) { 
			for(int i=0;i<roleList.size();i++){ 
				String roleId= ur.getRoleId();
			    String lrRole= roleList.get(i).getRoleId();
			    if(roleId.equals(lrRole)){
			    	roleList.get(i).setIsSelect("1");
					break;
				}
				
			}
		 }
		if(loanEmp.getUserId()==1){
			request.setAttribute("isAdmin", "admin");
			UnionCompany unionCompanyN=new UnionCompany();
			unionCompanyN.setUnionId(emp.getUnionId());
			unionCompanyN.setStatus("03");
			companyList=unionCompanyApi.queryList(unionCompanyN);
		}else{
			request.setAttribute("isAdmin", "noadmin");
			companyList=new ArrayList<UnionCompany>();
			companyList.add(unionCompanyApi.get(emp.getCompanyId()));
			if(StringUtil.isNotEmpty(emp.getCompanyId())
					&&StringUtil.isNotEmpty(loanEmp.getCompanyId())
					&&!emp.getCompanyId().equals(loanEmp.getCompanyId()))
			companyList.add(unionCompanyApi.get(loanEmp.getCompanyId()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serverId", "lhhs_spark");
		List<Map<String, Object>> allQuart  =userApi.queryAllQuarters(map);
		List<Integer>  myQuarter = userQuartersApi.queryEmpQuartersById(leEmpId);
		if(myQuarter.size()==0){
			for(Map<String,Object> quarter :allQuart){
				quarter.put("isQuarter", 0);
			}
			
		}else{
			for(Map<String,Object> quarter :allQuart){
				
				for(Object qu:myQuarter){
					Integer  quarterId =(Integer) quarter.get("quarters_id");
					if(Integer.parseInt((String) qu)==quarterId){
						quarter.put("isQuarter", 1);
						break;
					}else{
						quarter.put("isQuarter", 0);
					}
				}
				
			}
		}
		//所有数据权限组
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("status", "03");
		CommonUtils.fillBumpCompanyToMap(param);
		List<Authgroup> authgroupList=authgroupApi.queryAuthGroupList(param);
		//用户的数据权限组
		//List<Integer> myAuth = systemManager.selectAuthgroupIdsByUserId(leEmpId);
		List<Integer> myAuth = authgroupUserApi.selectAuthgroupIdsByUserId(leEmpId);
		if(myAuth.size()!=0){
			for(Authgroup auth :authgroupList){
				for(Integer a : myAuth){
					Integer groupId = auth.getGroupId();
					if(a.intValue() == groupId.intValue()){
						auth.setIsSelect("1");
						break;
					}
				}
				
			}
		}
		request.setAttribute("emp", emp);
		request.setAttribute("allRole", roleList);
		request.setAttribute("clique", unionCompanyApi.queryAllUnion(unionCompany));
		request.setAttribute("company", companyList);
		request.setAttribute("allCity", userApi.queryAllCity());
		request.setAttribute("allDept", userApi.queryDeptAll());
		request.setAttribute("allQuart", allQuart);
		request.setAttribute("authgroupList", authgroupList);
		return "system/employeeUpdate";
	}
	
	@RequestMapping("/companyLink")
	@ResponseBody
	public Map<String, Object> companyLink(HttpServletRequest request, String companyId){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<UnionCompany> list=null;
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		if("admin".equals(user.getUsername())||"yunying".equals(user.getUsername())) {
			UnionCompany unionCompany=new UnionCompany();
			unionCompany.setUnionId(companyId);
			unionCompany.setStatus("03");
			list=unionCompanyApi.queryList(unionCompany);
//			list=unionCompanyApi.queryCompany(companyId);
		}else{
			UnionCompany company=unionCompanyApi.get(user.getCompanyId());
			list=new ArrayList<UnionCompany>();
			list.add(company);
		}
		resultMap.put("companyList", list);
		return resultMap;
	}
	
	@RequestMapping("/deptGroupLink")
	@ResponseBody
	public Map<String, Object> deptGroupLink(
			HttpServletRequest request,
			Integer lgDeptId
			){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<Map<String, Object>> list=userApi.queryAllGroup(lgDeptId);
		resultMap.put("groupList", list);
		return resultMap;
	}
	
	/**
	 * 系统管理-权限设置-后台账号管理-新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toEmployeeAdd")
	public String toEmployeeAdd(HttpServletRequest request){
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		UnionCompany unionCompany=new UnionCompany();
		unionCompany.setParentCompanyId("0");
		Role entity = new  Role();
		entity.setServerId("lhhs_spark");
		if(!"admin".equals(loanEmp.getUsername())) {
			/*entity.setUnitId(loanEmp.getUnionId());
			entity.setCompanyId(loanEmp.getCompanyId());*/
			unionCompany.setUnionId(loanEmp.getUnionId());
			unionCompany.setCompanyId(loanEmp.getCompanyId());
		}
		List<Role> role = roleApi.queryList(entity);
	//	List<Map<String, Object>> role = roleApi.getAllRole();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", "03");
		CommonUtils.fillBumpCompanyToMap(map);
//		CommonUtils.fillCompanyToMap(map);
		if(loanEmp.getUserId()==1){
			request.setAttribute("isAdmin", "admin");
		}else{
			request.setAttribute("isAdmin", "noadmin");
		}
		request.setAttribute("allRole", role);
		request.setAttribute("clique", unionCompanyApi.queryAllUnion(unionCompany));
		request.setAttribute("allCity", userApi.queryAllCity());
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("serverId", "lhhs_spark");
		request.setAttribute("allQuart",userApi.queryAllQuarters(map1));
		request.setAttribute("authgroupList", authgroupApi.queryAuthGroupList(map));
		return "system/employeeAdd";
	}
	/**88888888888888888888888888888888888888888888888888888888888888888888
	 * 系统管理-权限设置-后台账号管理-更新或新增
	 * @param request
	 * @param loanEmp
	 * @return
	 */
	@RequestMapping("/empAddOrUpdate")
	@ResponseBody
	public Map<String, Object> empAddOrUpdate(HttpServletRequest request,User loanEmp){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			SecurityUser leEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
			if(loanEmp.getCityName()==null || loanEmp.getCityName().equals("")){
				  loanEmp.setCityName(leEmp.getCityName());
			}
			if(loanEmp.getUnionId()==null|| loanEmp.getUnionId().equals("")
					||loanEmp.getCompanyId()==null|| loanEmp.getCompanyId().equals("")){
				  loanEmp.setUnionId(leEmp.getUnionId());
				  loanEmp.setCompanyId(leEmp.getCompanyId());
			}
		//	resultMap=systemManager.empUpdateOrAdd(loanEmp);
		//	resultMap=userApi.empUpdateOrAdd(loanEmp);
			
		 	 //检查账号、手机、
		 	 Map<String,Object> map_temp =userApi.checkUser(loanEmp);
		 	if(!SystemConst.SUCCESS.equals(map_temp.get(SystemConst.retCode))){
		 		return map_temp;
		 	}
			loanEmp.setCompanyName(unionCompanyApi.get(loanEmp.getCompanyId()).getCompanyName());
			loanEmp.setServerId("lhhs_spark");
			int count=0;
			
			if(loanEmp.getUserId()!=null){
				count=userApi.update(loanEmp);
			}else{
				User user=new User();
				user.setUsername(loanEmp.getUsername());
				User emp = userApi.get(user);
				if (emp != null) {
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u8be5\u8d26\u53f7\u5df2\u5b58\u5728");
					return resultMap;
				}
		//		List<LoanEmp> employee = loanEmpMapper.queryEmpByMobile(loanEmp.getLeMobile());
				User userM=new User();
				userM.setMobile(loanEmp.getMobile());
				User employee = userApi.get(userM);
		//		List<User> employee = userApi.queryUser(userM);
				if (employee != null) {
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u8be5\u624b\u673a\u53f7\u4e3a\u5df2\u6ce8\u518c\u8d26\u6237");
					return resultMap;
				}
				loanEmp.setUserType("1");
				count=userApi.save(loanEmp);
			}
			if(count>0){
				LOGGER.info("保存本地用户成功，开始调用tk服务");
				UserBase userBase = new UserBase();
				List<String> listServerId = new ArrayList<>();
				listServerId.add("lhhs_spark");
				listServerId.add("lhhs_saas");
				userBase.setListServerId(listServerId );
				BeanUtils.copyProperties(loanEmp, userBase);
				
				userBase.setServerId("lhhs_spark");
				userBase.setUserName(loanEmp.getUsername());
				userBase.setName(loanEmp.getStaffName());
				userBase.setLgUserName(loanEmp.getLgUsername());
				userBase.setActionType(TkServerConst.ActionType.SAVEUSER);
				
				Map<String, Object> map = userBaseApi.serviceTrans(userBase );
				LOGGER.debug("保存用户" + loanEmp.toString() +"\t 返回的结果：\t"+ resultMap);
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
			}else{
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/empAcountCheck")
	@ResponseBody
	public Map<String, Object> empAcountCheck(HttpServletRequest request,String account){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		User emp=userApi.selectByAccount(account);
		if(emp!=null){
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u8be5\u8d26\u53f7\u5df2\u5b58\u5728");
		}else{
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
		}
		return resultMap;
	}
	
	/**
	 * 重置密码
	 * @param empId 需要重置的员工ID
	 * @return
	 */
	@RequestMapping("/resetEmpPass")
	@ResponseBody
	public Map<String,Object> resetEmpPass(User user){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(SystemConst.retCode, SystemConst.FAIL);
		map.put(SystemConst.retMsg, "更新失败");//登陆成功
		//查询用户信息
		User temp=userApi.get(user.getUserId().toString());
		
		if(temp==null){
			map.put(SystemConst.retMsg, "用户不存在!");
			return map;
		}
		SecurityUser cur = (SecurityUser) SecurityUserHolder.getCurrentUser();
		//随机6位密码
		String newpassword=SecurityUtil.getRandomChar(6);
		//MD5加密
		user.setNewPassword(MD5.MD5(newpassword));
		user.setLastUser(cur.getUserId().toString());
		user.setUsername(temp.getUsername());
	 	int flag=userApi.resetPassword(user);
	 	if(flag>0){
	 		map.put(SystemConst.retCode, SystemConst.SUCCESS);
	 		map.put(SystemConst.retMsg, "新密码为："+newpassword);
	 	}
		return map;
	}

	/**
	 * 系统管理-权限设置-后台账号管理-禁用启用
	 * @param request
	 * @param leStatus
	 * @param leEmpId
	 * @return
	 */
	@RequestMapping("/empEnbleDisable")
	@ResponseBody
	public Map<String, Object> empEnbleDisable(
			HttpServletRequest request,
			String leStatus,
			Integer leEmpId
			){
		Map<String,Object> result = userApi.empEnableOrDisable(leStatus, leEmpId);
		return result;
		}
	/**
	 * 员工信息导出
	 * @param request
	 * @param response
	 * @param leCity
	 * @param leStaffName
	 * @param leDeptId
	 * @param leQuartersId
	 */
	@RequestMapping("/employeeExport")
	@ResponseBody
	public void employeeExport(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="leCity",required=false)String leCity,
			@RequestParam(name="companyId",required=false)String companyId,
			@RequestParam(name="leStaffName",required=false)String leStaffName,
			@RequestParam(name="leDeptId",required=false)Integer leDeptId
			){
		Map<String, Object> map=new HashMap<String,Object>();
	    SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
//	       暂时注释掉获取当前admin 用户的公司
//	    if(loanEmp!=null && loanEmp.getCompanyId()!=null && loanEmp.getCompanyId()!=null){
//	          map.put("companyId", loanEmp.getCompanyId());
//	          }
	    map.put("cityName", leCity);
	    map.put("companyId", companyId);
	    map.put("staffName", leStaffName);
	    map.put("deptId", leDeptId);
		if(!"admin".equals(loanEmp.getUsername())){
			 map.put("unionId", loanEmp.getUnionId());
			 map.put("companyId", loanEmp.getCompanyId());
		}
		 map.put("serverId", "lhhs_spark");
	    List<User> empList=userApi.employeeExport(map);   

	    String fileName="员工信息"; 
	      Map<String, String> titles=new LinkedHashMap<String, String>();
	      titles.put("cityName", "城市");
	      titles.put("userId", "ID");
	      titles.put("username", "账号");
	      titles.put("staffName", "姓名");
	      titles.put("sex", "性别");
	      titles.put("mobile", "电话");
	      titles.put("ldName", "部门");
	      titles.put("lqName", "岗位");
	      titles.put("createTime", "注册时间");
	      titles.put("status", "状态");
	      AnalyseTransExcel.downLoadExcel(request, response, fileName, empList, User.class, titles);
	      System.out.println(fileName+"下载完成");   
	}
	/**
	 * 根据公司ID查询公司所有部门，返回json
	 * @return
	 */
	@RequestMapping("/queryDeptJsonByCompanyId")
	@ResponseBody
	public List<Dept> queryDeptJsonByCompanyId(String companyId){
		List<Dept> list=deptApi.queryDeptByCompanyId(companyId);
		List<Dept> newList = treeList(list, 0);
		return newList;
	}
	
	public List<Dept> treeList(List<Dept> list, Integer id) {
		List<Dept> tempList = new ArrayList<Dept>();
		for (Dept dept : list) {
			Integer ldDeptId = dept.getDeptId();//获取本身的id
			Integer parentId = dept.getParentId();//获取父节点id
			if(parentId!=null){
				if(id.intValue() == parentId.intValue() ){
					List<Dept> temp = treeList(list, ldDeptId);
					Collections.sort(temp, new Comparator<Dept>() {
						@Override
						public int compare(Dept o1, Dept o2) {
							return o1.getSort().compareTo(o2.getSort());
						}
					});
					dept.setSubDeptList(temp);
					tempList.add(dept);
				}
			}
		}
		return tempList;
   	}



	
	/**************************************公司账户管理*******************************************************/
	/**
	 * 系统管理-公司账户管理-公司放款(收款)账户设置
	 * companyAccount:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * @author Administrator
	 * @param request
	 * @param province
	 * @param cityName
	 * @param company
	 * @param bankcardName
	 * @param pageNumber
	 * @param accountType
	 * @return
	 * @since JDK 1.8
	 */
	
	@RequestMapping("/companyInAccount")
	public ModelAndView companyInAccount(
			HttpServletRequest request,
			@RequestParam(name="province",required=false)String province,
			@RequestParam(name="city",required=false)String city,
			@RequestParam(name="companyname",required=false)String companyname,
			@RequestParam(name="accountHolder",required=false)String accountHolder,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber
			){
		 Map<String, Object> paramsMap=new HashMap<String,Object>();
		 ModelAndView model=new ModelAndView("system/accountgathering");
		 pageNumber=pageNumber==null? 1 : pageNumber;
		 page.setPageIndex(pageNumber);
		 CommonUtils.fillCompanyToMap(paramsMap);
		 paramsMap.put("kind", "01");
		 paramsMap.put("province", province);
		 paramsMap.put("cityName", city);
		 paramsMap.put("companyname", companyname);
		 paramsMap.put("accountHolder", accountHolder);
		 paramsMap.put("page", page);
		 List<LoanAccountCard> accountInfos=systemManager.queryAllAccount(paramsMap, page);
		 LoanEmp emp=CommonUtils.getEmpFromSession();
		 if(emp.getBranchCompanyId()!=null && emp.getCompanyId()!=null){
		   if(!emp.getBranchCompanyId().equals(emp.getCompanyId())){
			 request.setAttribute("group", "nogroup");
		   }
		 }
		 request.setAttribute("accountInfos", accountInfos);
		 request.setAttribute("province", province);
		 request.setAttribute("city", city);
		 request.setAttribute("companyname", companyname);
		 request.setAttribute("accountHolder", accountHolder);
		 request.setAttribute("page", page);
		 return model;
	}
	
	/**
	 * 
	 * companyOutAccount:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * @author Administrator
	 * @param request
	 * @param province
	 * @param city
	 * @param companyname
	 * @param accountHolder
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/companyAccount")
	public ModelAndView companyAccount(
			HttpServletRequest request,
			@RequestParam(name="province",required=false)String province,
			@RequestParam(name="city",required=false)String city,
			@RequestParam(name="companyname",required=false)String companyname,
			@RequestParam(name="accountHolder",required=false)String accountHolder,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber
			){
		 Map<String, Object> paramsMap=new HashMap<String,Object>();
		 ModelAndView model=new ModelAndView("system/accountloan"); 
		 pageNumber=pageNumber==null? 1 : pageNumber;
		 page.setPageIndex(pageNumber);
		 CommonUtils.fillCompanyToMap(paramsMap);
		 paramsMap.put("province", province);
		 paramsMap.put("cityName", city);
		 paramsMap.put("companyname", companyname);
		 paramsMap.put("accountHolder", accountHolder);
		 paramsMap.put("page", page);
		 List<LoanAccountCard> accountInfos=systemManager.queryAllAccount(paramsMap, page);
		 LoanEmp emp=CommonUtils.getEmpFromSession();
		 if(emp.getBranchCompanyId()!=null && emp.getCompanyId()!=null){
		   if(!emp.getBranchCompanyId().equals(emp.getCompanyId())){
			 request.setAttribute("group", "nogroup");
		   }
		 }
		 request.setAttribute("accountInfos", accountInfos);
		 request.setAttribute("province", province);
		 request.setAttribute("city", city);
		 request.setAttribute("companyname", companyname);
		 request.setAttribute("accountHolder", accountHolder);
		 request.setAttribute("page", page);
		 return model;
	}
	/**
	 *  系统管理-公司账户管理-公司放款(收款)账户设置-新增
	 * accountAddView:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param request
	 * @param accountType
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/accountAddView")
	public ModelAndView accountAddView(
			HttpServletRequest request){
		 ModelAndView model=new ModelAndView("system/loanaccountadd");
		 UnionCompany vo=new UnionCompany();
		 CommonUtils.fillBumpCompany(vo);
		 List<UnionCompany> list = unionCompanyApi.queryCompanyProvinceList(vo);
		 model.addObject("companys", list);
		 model.addObject("banks", systemManager.queryAllBank());
		 return model;
	}
	
	
	/**
	 * 系统管理-公司账户管理-公司放款(收款)账户设置-修改
	 * accountModifyView:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param request
	 * @param accountId
	 * @param accountType
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/accountModifyView")
    public ModelAndView accountModifyView(
    		HttpServletRequest request,
    		@RequestParam(name="accountId",required=false)String accountId,
    		@RequestParam(name="accountHolder",required=false)String accountHolder
			){
    	 ModelAndView model=new ModelAndView("system/loanaccountmodify");
    	 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("accountId", accountId);
		 map.put("accountHolder", accountHolder);
		 LoanAccountInfo account=systemManager.accountDetail(accountId);
		 List<LoanAccountCard> accountcard=systemManager.queryAllCard(map);
		 if(accountcard.size()>0){
		 account.setAccountHolder(accountcard.get(0).getAccountHolder());
		 account.setMobile(accountcard.get(0).getMobile());
		 }
		 UnionCompany unionCompany=new UnionCompany();
		 unionCompany.setProvinceNo(account.getProvienceName());
		 unionCompany.setCityNo(account.getCityName());
		 CommonUtils.fillBumpCompany(unionCompany);
		 List<UnionCompany> list = unionCompanyApi.selectCompanyByProvinceCity(unionCompany);
		 model.addObject("companys", list);
		 model.addObject("account", account);
		 model.addObject("accountcard", accountcard);
		 model.addObject("banks", systemManager.queryAllBank());
		 return model;
	}
    /**
     * 系统管理-公司账户管理-公司放款(收款)账户设置-新增和修改方法
     * accountAddOrModify:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator
     * @param request
     * @param response
     * @param loanAccountInfo
     * @return
     * @since JDK 1.8
     */
	@RequestMapping("/accountAddOrModify")
	@ResponseBody
	public Map<String, Object> accountAddOrModify(
			HttpServletRequest request,
			 @RequestParam(value = "loanAccountInfo") String loanAccountInfo,
			 @RequestParam(value = "loanAccountCard") String loanAccountCard
			){
		
		Map<String, Object> map=new HashMap<String, Object>();
   	    try {
   	    	loanAccountInfo=URLDecoder.decode(loanAccountInfo,"UTF-8");
   	    	loanAccountCard=URLDecoder.decode(loanAccountCard,"UTF-8");
		    } catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		    }

	    LoanAccountInfo account=JSONObject.parseObject(loanAccountInfo, LoanAccountInfo.class);
	    List<LoanAccountCard> cards=JSONObject.parseArray(loanAccountCard, LoanAccountCard.class);
		 
		map=systemManager.accountAddandModify(account,cards);
		return map;
	};
	
	
	/**
	 *  系统管理-公司账户管理-公司放款(收款)账户设置-查看
	 * accountDetailView:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param request
	 * @param accountId
	 * @param accountType
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/accountDetailView")
    public ModelAndView accountDetailView(
    		HttpServletRequest request,
    		@RequestParam(name="accountId",required=false)String accountId,
    		@RequestParam(name="accountHolder",required=false)String accountHolder
			){
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("accountId", accountId);
		 map.put("accountHolder", accountHolder);
    	 ModelAndView model=null;
		 model=new ModelAndView("system/loanaccountdetail"); 
		 LoanAccountInfo account=systemManager.accountDetail(accountId);
		 List<LoanAccountCard> accountcard=systemManager.queryAllCard(map);
		 if(accountcard.size()>0){
		 account.setAccountHolder(accountcard.get(0).getAccountHolder());
		 account.setMobile(accountcard.get(0).getMobile());
		 }
		 model.addObject("account", account);
		 model.addObject("accountcard", accountcard);
		 return model;
		
	}
	
	@RequestMapping("/ajaxModifyDetail")
	public String ajaxModifyDetail(
			HttpServletRequest request,
    		@RequestParam(name="cardId",required=false)String cardId
			){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("cardId", cardId);
		List<LoanAccountCardRevise> cardRevises=systemManager.queryAllRevise(paramMap);
		request.setAttribute("cardRevises", cardRevises);
		return "/system/_accountdetail";
	}
	
	@RequestMapping("/ajaxCardInfoDel")
	@ResponseBody
	public Map<String, Object> ajaxCardInfoDel(
			HttpServletRequest request,
    		@RequestParam(name="cardId",required=false)String cardId
			){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		Long id=Long.parseLong(cardId);
		int conut=systemManager.delCardInfo(id);
		if(conut>0){
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
	    	resultMap.put(SystemConst.retMsg, "\u5220\u9664\u6210\u529f");
		   }else{
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u5220\u9664\u5931\u8d25");
		}
		return resultMap;
	}
	
	@RequestMapping("/authorityGroupList")
	public ModelAndView authorityGroupList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber){
		ModelAndView modelAndView = new ModelAndView("system/authorityGroupList");
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Authgroup authgroup=new Authgroup();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		authgroup.setPageIndex(pageNumber);
		authgroup.setServerId("lhhs_spark");
		if(!"admin".equals(user.getUsername())&&!"yunying".equals(user.getUsername())) {
			authgroup.setLgUnionId(user.getUnionId());
			if(!user.getUnionId().equals(user.getCompanyId())){
				authgroup.setLgCompanyId(user.getCompanyId());
			}
		}
		com.lhhs.bump.Page<Authgroup> page = authgroupApi.queryAuthGroupListPage(authgroup);
		modelAndView.addObject("loanAuthgroupList", page.getResultList());
		modelAndView.addObject("page", page);
		return modelAndView;
	}
	
	@RequestMapping("/toAuthorityGroupInsert")
	public ModelAndView toAuthorityGroupInsert(){
		ModelAndView modelAndView = new ModelAndView("system/authorityGroupInsert");
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		UnionCompany vo=new UnionCompany();
		vo.setParentCompanyId("0");
		if(!"admin".equals(user.getUsername())&&!"yunying".equals(user.getUsername())) {
			modelAndView.addObject("isAdmin", "noadmin");
			vo.setUnionId(user.getUnionId());
		}else{
			modelAndView.addObject("isAdmin", "admin");
		}
		Authority authority=new Authority();
		authority.setIsShow("1");
		authority.setType("1");
		authority.setStatus("03");
		authority.setServerId("lhhs_spark");
		authority.setField1("0");
		List<Authority> allLastAuthyList = authorityApi.queryAuthTree(authority);
		modelAndView.addObject("allLastAuthyList", allLastAuthyList);
		modelAndView.addObject("allUnion", unionCompanyApi.queryAllUnion(vo));
		return modelAndView;
	}
	
	/**
	 * 查询公司部门tree
	 * @param request
	 * @param loanAuthgroupStr
	 * @return
	 */
	@RequestMapping("/getCompanyDeptTree")
	@ResponseBody
	public List<UnionCompany> getCompanyDeptTree(HttpServletRequest request,UnionCompany unionCompany) {
		CommonUtils.fillBumpCompany(unionCompany);
		return unionCompanyApi.getAllCompanyTree(unionCompany);
	};
	
	@RequestMapping("/authorityGroupInsert")
	@ResponseBody
	public Map<String, Object> authorityGroupInsert(HttpServletRequest request,
			String loanAuthorityGroupListStr, 
			String loanAuthgroupStr) {
	    Map<String, Object> result=new HashMap<String,Object>();
		try {
			Authgroup loanAuthgroupVo = JSON.parseObject(loanAuthgroupStr, Authgroup.class);
			SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		    if("unionALL".equals(loanAuthgroupVo.getUnionId())){
		    	loanAuthgroupVo.setUnionId(null);
			}else if(StringUtils.isEmpty(loanAuthgroupVo.getUnionId())){
				loanAuthgroupVo.setUnionId(loanEmp.getUnionId());
			}
		    if("companyAll".equals(loanAuthgroupVo.getCompanyId())){
		    	loanAuthgroupVo.setCompanyId(null);
		    }else if(StringUtils.isEmpty(loanAuthgroupVo.getCompanyId())){
		    	loanAuthgroupVo.setCompanyId(loanEmp.getCompanyId());
		    }
		    loanAuthgroupVo.setCreateUser(loanEmp.getUserId().toString());
		    loanAuthgroupVo.setLastUser(loanEmp.getUserId().toString());
		    loanAuthgroupVo.setServerId("lhhs_spark");
		    List<AuthorityGroup> loanAuthorityGroupList = JSON.parseArray(loanAuthorityGroupListStr, AuthorityGroup.class);
		    result= authgroupApi.authorityGroupInsert(loanAuthorityGroupList,loanAuthgroupVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "系统异常");
		}
		return result;
	};
	
	
	@RequestMapping("/toAuthorityGroupDetail/{opType}")
	public ModelAndView toAuthorityGroupDetail(Integer groupId, @PathVariable("opType") String opType){
		ModelAndView modelAndView = null;
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		UnionCompany unionCompany=new UnionCompany();
		unionCompany.setParentCompanyId("0");
		Authgroup loanAuthgroup = authgroupApi.getAuthGroupDetail(groupId);
		if("update".equals(opType)){
			modelAndView = new ModelAndView("system/toAuthorityGroupToUpdate");
			List<UnionCompany> queryCompany = unionCompanyApi.queryCompany(loanAuthgroup.getUnionId());
			modelAndView.addObject("company", queryCompany);
		}else if("detail".equals(opType)){
			modelAndView = new ModelAndView("system/toAuthorityGroupDetail");
		}
		if(!"admin".equals(loanEmp.getUsername())&&!"yunying".equals(loanEmp.getUsername())) {
			modelAndView.addObject("isAdmin", "noadmin");
			unionCompany.setUnionId(loanEmp.getUnionId());
		}else{
			modelAndView.addObject("isAdmin", "admin");
		}
		AuthorityGroup vo=new AuthorityGroup();
		vo.setGroupId(groupId);
		List<AuthorityGroup> selectAutGupList=  authorityGroupApi.queryList(vo);//已经选中的数据组权限
		Authority authority=new Authority();
		authority.setIsShow("1");
		authority.setType("1");
		authority.setStatus("03");
		authority.setServerId("lhhs_spark");
		authority.setField1("0");
		List<Authority> allLastAuthyList = authorityApi.queryAuthTree(authority);
		modelAndView.addObject("allLastAuthyList", allLastAuthyList);
		modelAndView.addObject("loanAuthgroup", loanAuthgroup);
		modelAndView.addObject("selectAutGupList", selectAutGupList);
		modelAndView.addObject("groupId", groupId);
		modelAndView.addObject("opType", opType);
		modelAndView.addObject("allUnion", unionCompanyApi.queryAllUnion(unionCompany));
		return modelAndView;
	}
	
	@RequestMapping("/authorityGroupUpdate")
	@ResponseBody
	public Map<String, Object> authorityGroupUpdate(HttpServletRequest request,
			String loanAuthorityGroupListStr,
			String loanAuthgroupStr) {
	    Map<String, Object> result=new HashMap<String,Object>();
	    try {
	    	Authgroup loanAuthgroupVo = JSON.parseObject(loanAuthgroupStr, Authgroup.class);
	    	SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
	    	if("unionALL".equals(loanAuthgroupVo.getUnionId())){
		    	loanAuthgroupVo.setUnionId(null);
			}else if(StringUtils.isEmpty(loanAuthgroupVo.getUnionId())){
				loanAuthgroupVo.setUnionId(loanEmp.getUnionId());
			}
		    if("companyAll".equals(loanAuthgroupVo.getCompanyId())){
		    	loanAuthgroupVo.setCompanyId(null);
		    }else if(StringUtils.isEmpty(loanAuthgroupVo.getCompanyId())){
		    	loanAuthgroupVo.setCompanyId(loanEmp.getCompanyId());
		    }
		    loanAuthgroupVo.setLastUser(loanEmp.getUserId().toString());
	    	List<AuthorityGroup> loanAuthorityGroupList = JSON.parseArray(loanAuthorityGroupListStr, AuthorityGroup.class);
	 	    result= authgroupApi.authorityGroupUpdate(loanAuthorityGroupList,loanAuthgroupVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "系统异常");
		}
		return result;
	};
	
	@RequestMapping("/authorityGroupValidate")
	@ResponseBody
	public boolean authorityGroupValidate(HttpServletRequest request,Authgroup authgroup) {
	    return authgroupApi.authGroupNameValid(authgroup);
	};
	
	@RequestMapping("/selectCompanyByProvinceCity")
	@ResponseBody
	public Map<String, Object> selectCompanyByProvinceCity(
			HttpServletRequest request,
			 @RequestParam(value = "provinceNo") String provinceNo,
			 @RequestParam(value = "cityNo") String cityNo
			){
	    Map<String, Object> result=new HashMap<String,Object>();
	    UnionCompany unionCompany=new UnionCompany();
	    unionCompany.setProvinceNo(provinceNo);
	    unionCompany.setCityNo(cityNo);
	    CommonUtils.fillBumpCompany(unionCompany);
	    List<UnionCompany> list = unionCompanyApi.selectCompanyByProvinceCity(unionCompany);
	    result.put("companyList", list);
		return result;
	};
	
	@RequestMapping("/getDeptListByCompanyId")
	@ResponseBody
	public List<Dept> getDeptListByCompanyId(HttpServletRequest request, String companyId, Integer layer){
		Dept dept=new Dept();
		dept.setCompanyId(companyId);
		dept.setLayer(layer);
		List<Dept> deptList=CommonUtils.getDeptListByDept(dept);
		return deptList;
	};
	
	/**
	 * 数据权限组--禁用、启用
	 */
	@RequestMapping("/authorityGroupChange")
	@ResponseBody
	public Map<String,Object> authorityGroupChange(HttpServletRequest request,String ifEnable,String roleId){
		Map<String, Object> result=new HashMap<String,Object>();
		Authgroup authGroup = new Authgroup();
		authGroup.setGroupId(Integer.valueOf(roleId));
		if("03".equals(ifEnable)){
			authGroup.setStatus("99");
		}
		if("99".equals(ifEnable)){
			authGroup.setStatus("03");
		}
		int i = authgroupApi.update(authGroup);
		if(i==1){
			result.put(systemConst.retCode, systemConst.SUCCESS);
			result.put(systemConst.retMsg, "更新状态成功");
		}else{
			result.put(systemConst.retCode, systemConst.FAIL);
			result.put(systemConst.retMsg, "更新状态失败");
		}
		return result;
	}
	
	/*****************************************  操作日志 start  **********************************************/
	
	/**
	 * 操作日志
	 */
	@RequestMapping("/operateRecord")
	public ModelAndView operateRecord(HttpServletRequest request, LoanOperateRecordWithBLOBs record){
		ModelAndView mav = new ModelAndView("operateRecord/operateRecord");
		CommonUtils.fillCompany(record);
		Page page = operateRecordService.queryListPage(new Page(record.getPageIndex(),record.getPageSize()), record);
		mav.addObject("page", page);
		return mav;
	}
	
	/**
	 * 操作日志异步查询
	 */
	@RequestMapping("/ajaxOperateRecord")
	public ModelAndView ajaxOperateRecord(HttpServletRequest request, LoanOperateRecordWithBLOBs record){
		ModelAndView mav = new ModelAndView("operateRecord/_operateRecordTemp");
		CommonUtils.fillCompany(record);
		Page page = operateRecordService.queryListPage(new Page(record.getPageIndex(),record.getPageSize()), record);
		mav.addObject("page", page);
		return mav;
	}
	
	/*****************************************  操作日志 end  **********************************************/
	
}