/**
 * Project Name:loan-service
 * File Name:CommonUtils.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月8日上午10:00:22
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.lhhs.bump.BaseEntity;
import com.lhhs.bump.api.DeptApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.AuthgroupUser;
import com.lhhs.bump.domain.Authority;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.page.BaseObject;
import com.lhhs.loan.common.utils.SpringContextHolder;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.domain.LoanAutoIncrement;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.NoticeConfig;
import com.lhhs.loan.dao.domain.NoticeModel;

/**
 * ClassName:CommonUtils <br/>
 * Function: 获取自增主键
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月8日 上午10:00:22 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
public class CommonUtils {
	
	private static final Logger LOGGER = Logger.getLogger(CommonUtils.class);
	
	private static AutoIncrementService autoIncrementService=(AutoIncrementService) SpringContextHolder.getBean(AutoIncrementService.class);
	private static DeptApi deptApi=(DeptApi) SpringContextHolder.getBean(DeptApi.class);
	private static UnionCompanyApi unionCompanyApi=(UnionCompanyApi) SpringContextHolder.getBean(UnionCompanyApi.class);
    /**
	 * getAutoIncrement: 获取自增主键
	 * @author suncj
	 * @param tableName 表名
	 * @param type 类型 : 1:个人; 2:企业;3:机构 ;4:公司
	 * @param length 长度int
	 * @return Long
	 * @since JDK 1.8
	 */
    public static synchronized Long getAutoIncrement(String tableName,String type,int length){
		StringBuilder sbr=new StringBuilder("00000000000000000000000000000000");
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("table_name", tableName);
		long no=0;
		LoanAutoIncrement autoIncrement=autoIncrementService.getAutoIncrement(map);
		if(autoIncrement!=null){
			no=autoIncrement.getAutoIncrement();
		}else{
			return null;
		}
		no=no+1;
		autoIncrement.setAutoIncrement(no);
		try {
			int t=autoIncrementService.updateByPrimaryKeySelective(autoIncrement);
			if(t>0){
				sbr.append(no);
				int startLength=sbr.length()-length;//开始截取位置
				if(!StringUtils.isEmpty(type)) {
					startLength=sbr.length()-length+1; 
				}else{
					type="";
				}
				return Long.parseLong(type+sbr.substring(startLength, sbr.length()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
    }
    /**
	 * getAutoIncrement: 获取自增主键
	 * @author suncj
	 * @param tableName 表名
	 * @param type 类型 : 1:个人; 2:企业;3:机构 ;4:公司
	 * @param length 长度int
	 * @return Long
	 * @since JDK 1.8
	 */
    public static synchronized Long getAutoIncrement(String tableName){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("table_name", tableName);
		long no=0;
		LoanAutoIncrement autoIncrement=autoIncrementService.getAutoIncrement(map);
		if(autoIncrement!=null){
			no=autoIncrement.getAutoIncrement();
		}else{
			return null;
		}
		no=no+1;
		autoIncrement.setAutoIncrement(no);
		try {
			int t=autoIncrementService.updateByPrimaryKeySelective(autoIncrement);
			if(t>0){
				return no;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
    }
    /**
     * fillCompany:
     * 拷贝员工信息中员工ID、所属公司、所属集团、所管理分公司列表、所拥有角色列表 <br/>
     * @param emp
     * @param base
     * @since JDK 1.8
     */
    public  static <T extends BaseObject> void fillCompany(T t){
     	Assert.notNull(t,"传入对象不可为null");
		SecurityUser emp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Assert.notNull(emp,"当前用户没有登录");
		if(!"admin".equals(emp.getUsername())&&!"yunying".equals(emp.getUsername())) {
			t.setLeEmpId(Integer.valueOf(emp.getUserId().toString()));
			t.setUnionId(emp.getUnionId());
			t.setCompanyId(emp.getCompanyId());
			List<Integer> roles=new ArrayList<Integer>();
			if(null!=emp.getRoles()){
				String[] strArrry=emp.getRoles().split(",");
				for (String str : strArrry) {
					roles.add(Integer.valueOf(str));
				}
			}
			t.setLrRoleId(roles);
			t.setEmpMobile(emp.getMobile());
			t.setLeEmpName(emp.getStaffName());
			List<AuthgroupUser> tempList=new ArrayList<AuthgroupUser>();
			List<AuthgroupUser> authgroupList=emp.getTempAuthgroupList();//默认和通用的权限组列表（可确定的）
			List<AuthgroupUser> menuAuthgroupList=emp.getMenuAuthgroupList();//自定义菜单数据权限组
			AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
			loanAuthgroupUser.setOrgId(emp.getCompanyId());
			loanAuthgroupUser.setDataOwner(emp.getUserId().toString());
			tempList.add(loanAuthgroupUser);
			if(authgroupList!=null && authgroupList.size()>0){
				tempList.addAll(authgroupList);
			}
			if(menuAuthgroupList!=null && menuAuthgroupList.size()>0){
				String url=SecurityUserHolder.getServletPath();
				String url2=SecurityUserHolder.getReferer();
				String laAuthorityId = null;
				List<Authority> authList=emp.getAuthorityList();
				for (Authority la : authList) {
					if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(url)
							&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
						laAuthorityId=la.getAuthorityId();
						break;
					}
				}
				if(StringUtil.isEmpty(laAuthorityId)&&StringUtil.isNotEmpty(url2)){
					String temp=url2.substring(url2.indexOf("://")+3);
					temp=temp.substring(temp.indexOf("/"));
					if(temp.indexOf("?")>=0){
						temp=temp.substring(0, temp.indexOf("?"));
					}
					for (Authority la : authList) {
						if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(temp)
								&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
							laAuthorityId=la.getAuthorityId();
							break;
						}
					}
				}
				if(StringUtil.isNotEmpty(laAuthorityId)){
					for (AuthgroupUser lau : menuAuthgroupList) {
						if(null!=lau.getAuthorityId()&&lau.getAuthorityId().equals(laAuthorityId)){
							tempList.add(lau);
							String deptId=lau.getDeptId();
							if(StringUtil.isNotEmpty(deptId)){
								List<Dept> tempDeptList = deptApi.querySubDeptByDeptId(deptId);
								for (Dept ld : tempDeptList) {
									Integer ldDeptId = ld.getDeptId();
									if(ldDeptId!=null){
										AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
										loanAuthgroupUser2.setOrgId(ld.getCompanyId());
										loanAuthgroupUser2.setDeptId(ldDeptId.toString());
										tempList.add(loanAuthgroupUser2);
									}
								}
							}
						}
					}
				}
			}
			if(tempList.size()>0){
				t.setAuthgroupList(tempList);
			}
		}
    }
    
    /**
     * fillCompanyToMap:
     * 拷贝员工信息中员工ID、所属公司、所属集团、所管理分公司列表、所拥有角色列表 <br/>
     * @param emp
     * @param base
     * @since JDK 1.8
     */
    public static void fillCompanyToMap(Map<String,Object> param){
     	Assert.notNull(param,"传入对象不可为null");
		SecurityUser emp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Assert.notNull(emp,"当前用户没有登录");
		if(!"admin".equals(emp.getUsername())&&!"yunying".equals(emp.getUsername())) {
			param.put("leEmpId", emp.getUserId());
			param.put("unionId", emp.getUnionId());
			param.put("companyId", emp.getCompanyId());
			List<Integer> roles=new ArrayList<Integer>();
			if(null!=emp.getRoles()){
				String[] strArrry=emp.getRoles().split(",");
				for (String str : strArrry) {
					roles.add(Integer.valueOf(str));
				}
			}
			param.put("lrRoleId", roles);
			param.put("empMobile", emp.getMobile());
			param.put("leEmpName", emp.getStaffName());
			List<AuthgroupUser> tempList=new ArrayList<AuthgroupUser>();
			List<AuthgroupUser> authgroupList=emp.getTempAuthgroupList();//默认和通用的权限组列表（可确定的）
			List<AuthgroupUser> menuAuthgroupList=emp.getMenuAuthgroupList();//自定义菜单数据权限组
			AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
			loanAuthgroupUser.setOrgId(emp.getCompanyId());
			loanAuthgroupUser.setDataOwner(emp.getUserId().toString());
			tempList.add(loanAuthgroupUser);
			if(authgroupList!=null && authgroupList.size()>0){
				tempList.addAll(authgroupList);
			}
			if(menuAuthgroupList!=null && menuAuthgroupList.size()>0){
				String url=SecurityUserHolder.getServletPath();
				String url2=SecurityUserHolder.getReferer();
				String laAuthorityId = null;
				List<Authority> authList=emp.getAuthorityList();
				for (Authority la : authList) {
					if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(url)
							&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
						laAuthorityId=la.getAuthorityId();
						break;
					}
				}
				if(StringUtil.isEmpty(laAuthorityId)&&StringUtil.isNotEmpty(url2)){
					String temp=url2.substring(url2.indexOf("://")+3);
					temp=temp.substring(temp.indexOf("/"));
					if(temp.indexOf("?")>=0){
						temp=temp.substring(0, temp.indexOf("?"));
					}
					for (Authority la : authList) {
						if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(temp)
								&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
							laAuthorityId=la.getAuthorityId();
							break;
						}
					}
				}
				if(StringUtil.isNotEmpty(laAuthorityId)){
					for (AuthgroupUser lau : menuAuthgroupList) {
						if(null!=lau.getAuthorityId()&&lau.getAuthorityId().equals(laAuthorityId)){
							tempList.add(lau);
							String deptId=lau.getDeptId();
							if(StringUtil.isNotEmpty(deptId)){
								List<Dept> tempDeptList = deptApi.querySubDeptByDeptId(deptId);
								for (Dept ld : tempDeptList) {
									Integer ldDeptId = ld.getDeptId();
									if(ldDeptId!=null){
										AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
										loanAuthgroupUser2.setOrgId(ld.getCompanyId());
										loanAuthgroupUser2.setDeptId(ldDeptId.toString());
										tempList.add(loanAuthgroupUser2);
									}
								}
							}
						}
					}
				}
			}
			if(tempList.size()>0){
				param.put("authgroupList", tempList);
			}else{
				param.put("authgroupList", null);
			}
		}
    }
    
    /**
     * errorMessageHandler:
     * 判断错误消息是不是自己抛出的
     * 通过判断消息中是否含有中文(此方法只是粗略判断) <br/>
     * @param msg
     * @return
     * @since JDK 1.8
     */
    public static String errorMessageHandler(String msg,String defaultMsg){
    	LOGGER.debug("判断消息中是否含有中文");
    	if(StringUtils.isEmpty(defaultMsg)){
    		defaultMsg = "系统错误";
    	}
        if(!StringUtils.isEmpty(msg)){
        	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        	Matcher m = p.matcher(msg);
            if (m.find()) {
            	return msg;
            }
        }
    	return defaultMsg;
    }
    
    public static <T> void excelExport(HttpServletResponse response,String fileName,String title,List<T> exportList,Class<T> clazz){
    	//设置导出Excel的内容
        ExportParams exportParams = new ExportParams();
        exportParams.setTitle(title);
        exportParams.setSheetName(title);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, clazz, exportList);
		//将数据写到输出流中
        try {
            CommonUtils.writeExcel(response, workbook, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * 将excel数据写到HttpServletResponse中，浏览器将提示下载文件。默认的编码格式为UTF-8
     * @param response
     * @param workbook POI wekbook对象
     * @param filename 浏览器下载文件时，显示的文件名字
     * @throws IOException
     * */
    public static void writeExcel(HttpServletResponse response, Workbook workbook, String filename) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        // 清空response
        response.reset();
        // 设置response的Header
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String (filename.getBytes("utf-8"),"iso8859-1"));
        response.addHeader("Content-Length", "" + bytes.length);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
    
    /**
     * 根据数据权限查询公司列表
     * 需要传入fillCompany拷贝后的entity
     * @return List<LoanUnionCompany>
     */
    public static <T extends BaseObject> List<UnionCompany> getCompanyList(T t){
    	List<UnionCompany> companyList = null;
    	if(t!=null){
    		UnionCompany vo = new UnionCompany();
    		if(null!=t.getAuthgroupList()&&t.getAuthgroupList().size()>0){
    			vo.setAuthgroupList(t.getAuthgroupList());
    		}
        	companyList=unionCompanyApi.queryList(vo);
    	}
		return companyList;
    }
    
    /**
     * 根据数据权限查询公司列表
     * 需要传入fillCompany拷贝后的entity
     * @return List<LoanUnionCompany>
     */
    public static <T extends BaseEntity> List<UnionCompany> getBumpCompanyList(T t){
    	List<UnionCompany> companyList = null;
    	if(t!=null){
    		UnionCompany vo = new UnionCompany();
    		if(null!=t.getLgCompanyId()&&"crmIntentLoanUser".equals(t.getLgCompanyId())){
    			vo.setUnionId(t.getLgUnionId());
    			vo.setCompanyId(null);
    		}
    		if(null!=t.getAuthgroupList()&&t.getAuthgroupList().size()>0){
    			vo.setAuthgroupList(t.getAuthgroupList());
    		}
    		companyList=unionCompanyApi.queryList(vo);
    	}
    	return companyList;
    }
    
    /**
     * 根据数据权限查询公司列表
     * 需要传入fillCompanyToMap拷贝后的param
     * @return List<LoanUnionCompany>
     */
    public static List<UnionCompany> getCompanyListToMap(Map<String,Object> param){
    	List<UnionCompany> companyList = null;
    	if(param!=null){
    		UnionCompany vo = new UnionCompany();
        	List<AuthgroupUser> authgroupList=(List<AuthgroupUser>) param.get("authgroupList");
        	if(null!=authgroupList&&authgroupList.size()>0){
        		vo.setAuthgroupList(authgroupList);
        	}
        	vo.setPage(null);
        	companyList=unionCompanyApi.queryList(vo);
    	}
    	return companyList;
    }
    
    /**
     * 根据公司查询部门列表
     * 公司编号companyId
     * @return List<LoanDept>
     */
    public static <T extends BaseObject> List<Dept> getDeptList(String companyId){
    	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
    	Map<String, Object> map=new  HashMap<String, Object>();
    	List<Dept> deptList = null;
    	String type="";
    	String company="";
    	if(StringUtil.isNotEmpty(companyId)){
    		company=companyId;
    	}else if(user.getUserId().longValue()!=1){
    		type="bumen";
    		if(StringUtil.isNotEmpty(user.getUnionId())){
    			map.put("unionId", user.getUnionId());
    		}
    		if(StringUtil.isNotEmpty(user.getCompanyId())){
    			company=user.getCompanyId();
    		}
    	}
    	if(StringUtil.isNotEmpty(company)){
    		Dept dept=new Dept();
    		dept.setPage(null);
//    		dept.setStatus("1");
    		dept.setCompanyId(company);
    		deptList=deptApi.queryList(dept);
    		deptList = treeList(deptList, 0, "");
    	}
    	if("bumen".equals(type)){
    		for(Dept dept:deptList){
    			if(dept.getName().contains("&nbsp;")){
    				dept.setName(dept.getName().replaceAll("&nbsp;", ""));
    			}
    		}
    	}
		return deptList;
    }
    /**
     * 根据公司查询部门列表
     * Dept
     * @return List<Dept>
     */
    public static <T extends BaseObject> List<Dept> getDeptListByDept(Dept dept){
    	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
    	Map<String, Object> map=new  HashMap<String, Object>();
    	List<Dept> deptList = null;
    	String type="";
    	String company="";
    	if(StringUtil.isNotEmpty(dept.getCompanyId())){
    		company=dept.getCompanyId();
    	}else if(user.getUserId().longValue()!=1){
    		type="bumen";
    		if(StringUtil.isNotEmpty(user.getUnionId())){
    			map.put("unionId", user.getUnionId());
    		}
    		if(StringUtil.isNotEmpty(user.getCompanyId())){
    			company=user.getCompanyId();
    		}
    	}
    	if(StringUtil.isNotEmpty(company)){
    		Dept depts=new Dept();
    		depts.setPage(null);
    		depts.setCompanyId(company);
    		depts.setLayer(dept.getLayer());
    		depts.setServerId(dept.getServerId()==null||"".equals(dept.getServerId())?"dept":dept.getServerId());
    		deptList=deptApi.queryList(depts);
    		deptList = treeList(deptList, 0, "");
    	}
    	if("bumen".equals(type)){
    		for(Dept deptTemp:deptList){
    			if(deptTemp.getName().contains("&nbsp;")){
    				deptTemp.setName(deptTemp.getName().replaceAll("&nbsp;", ""));
    			}
    		}
    	}
    	return deptList;
    }
    public static List<Dept> treeList(List<Dept> deptList, Integer id, String layer) {
		List<Dept> tempList = new ArrayList<Dept>();
		for (Dept dept : deptList) {
			Integer ldDeptId = dept.getDeptId();//获取本身的id
			Integer parentId = dept.getParentId();//获取父节点id
			if(null!=parentId && id.intValue() == parentId.intValue() ){
				dept.setName(layer+dept.getName());
				tempList.add(dept);
				List<Dept> temp = treeList(deptList, ldDeptId, "&nbsp;"+layer+"-&nbsp;");
				Collections.sort(temp, new Comparator<Dept>() {
					@Override
					public int compare(Dept o1, Dept o2) {
						return o1.getSort().compareTo(o2.getSort());
					}
				});
				if(null!=temp&&temp.size()>0){
					tempList.addAll(temp);
				}
			}
		}
		return tempList;
   	}
    
    /**
     * 部门管理
     * @return List<LoanDept>
     */
    public static <T extends BaseObject> List<Dept> getDeptListByManage(String companyId){
    	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
    	Map<String, Object> map=new  HashMap<String, Object>();
    	List<Dept> deptList = null;
    	String type="";
    	String company="";
    	if(StringUtil.isNotEmpty(companyId)){
    		company=companyId;
    	}else if(user.getUserId().longValue()!=1){
    		type="bumen";
    		if(StringUtil.isNotEmpty(user.getUnionId())){
    			map.put("unionId", user.getUnionId());
    		}
    		if(StringUtil.isNotEmpty(user.getCompanyId())){
    			company=user.getCompanyId();
    		}
    	}
    	if(StringUtil.isNotEmpty(company)){
    		Dept dept=new Dept();
    		dept.setPage(null);
    		dept.setCompanyId(company);
    		deptList=deptApi.queryList(dept);
    		deptList = treeList(deptList, 0, "");
    	}
    	if("bumen".equals(type)){
    		for(Dept dept:deptList){
    			if(dept.getName().contains("&nbsp;")){
    				dept.setName(dept.getName().replaceAll("&nbsp;", ""));
    			}
    		}
    	}
		return deptList;
    }
    
    /**
     * fillBumpCompany:
     * 拷贝员工信息中员工ID、所属公司、所属集团、所管理分公司列表、所拥有角色列表 <br/>
     * @param emp
     * @param base
     * @since JDK 1.8
     */
    public  static <T extends BaseEntity> void fillBumpCompany(T t){
     	Assert.notNull(t,"传入对象不可为null");
     	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Assert.notNull(user,"当前用户没有登录");
		t.setLgServerId("lhhs_spark");
		if(!"admin".equals(user.getUsername())&&!"yunying".equals(user.getUsername())) {
			t.setLgUserId(user.getUserId().toString());
			t.setLgUnionId(user.getUnionId());
			t.setLgUnionName(user.getUnionName());
			t.setLgCompanyId(user.getCompanyId());
			t.setLgCompanyName(user.getCompanyName());
			t.setLgDepId(user.getDeptId());
			t.setLgStaffName(user.getStaffName());
			t.setLgCityNo(user.getCityNo());
			t.setUserToken(user.getToken());
			t.setCompanyIdList(user.getCompanyIdList());
			t.setDepIdList(user.getDepIdList());
			t.setLgUsername(user.getUsername());
			List<AuthgroupUser> tempList=new ArrayList<AuthgroupUser>();
			List<AuthgroupUser> authgroupList=user.getTempAuthgroupList();//默认和通用的权限组列表（可确定的）
			List<AuthgroupUser> menuAuthgroupList=user.getMenuAuthgroupList();//自定义菜单数据权限组
			AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
			loanAuthgroupUser.setOrgId(user.getCompanyId());
			loanAuthgroupUser.setDataOwner(user.getUserId().toString());
			tempList.add(loanAuthgroupUser);
			if(authgroupList!=null && authgroupList.size()>0){
				tempList.addAll(authgroupList);
			}
			if(menuAuthgroupList!=null && menuAuthgroupList.size()>0){
				String url=SecurityUserHolder.getServletPath();
				String url2=SecurityUserHolder.getReferer();
				String laAuthorityId = null;
				List<Authority> authList=user.getAuthorityList();
				for (Authority la : authList) {
					if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(url)
							&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
						laAuthorityId=la.getAuthorityId();
						break;
					}
				}
				if(StringUtil.isEmpty(laAuthorityId)&&StringUtil.isNotEmpty(url2)){
					String temp=url2.substring(url2.indexOf("://")+3);
					temp=temp.substring(temp.indexOf("/"));
					if(temp.indexOf("?")>=0){
						temp=temp.substring(0, temp.indexOf("?"));
					}
					for (Authority la : authList) {
						if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(temp)
								&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
							laAuthorityId=la.getAuthorityId();
							break;
						}
					}
				}
				if(StringUtil.isNotEmpty(laAuthorityId)){
					for (AuthgroupUser lau : menuAuthgroupList) {
						if(null!=lau.getAuthorityId()&&lau.getAuthorityId().equals(laAuthorityId)){
							tempList.add(lau);
							String deptId=lau.getDeptId();
							if(StringUtil.isNotEmpty(deptId)){
								List<Dept> tempDeptList = deptApi.querySubDeptByDeptId(deptId);
								for (Dept ld : tempDeptList) {
									Integer ldDeptId = ld.getDeptId();
									if(ldDeptId!=null){
										AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
										loanAuthgroupUser2.setOrgId(ld.getCompanyId());
										loanAuthgroupUser2.setDeptId(ldDeptId.toString());
										tempList.add(loanAuthgroupUser2);
									}
								}
							}
						}
					}
				}
			}
			if(tempList.size()>0){
				t.setAuthgroupList(tempList);
			}
		}
    }
    
    /**
     * fillBumpCompanyToMap:
     * 拷贝员工信息中员工ID、所属公司、所属集团、所管理分公司列表、所拥有角色列表 <br/>
     * @param emp
     * @param base
     * @since JDK 1.8
     */
    public static void fillBumpCompanyToMap(Map<String,Object> param){
     	Assert.notNull(param,"传入对象不可为null");
     	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Assert.notNull(user,"当前用户没有登录");
		param.put("lgServerId", "lhhs_spark");
		if(!"admin".equals(user.getUsername())&&!"yunying".equals(user.getUsername())) {
			param.put("lgUserId", user.getUserId().toString());
			param.put("lgUnionId", user.getUnionId());
			param.put("lgUnionName",user.getUnionName());
			param.put("lgCompanyId", user.getCompanyId());
			param.put("lgCompanyName",user.getCompanyName());
			param.put("lgDepId", user.getDeptId());
			param.put("lgStaffName",user.getStaffName());
			param.put("lgCityNo",user.getCityNo());
			param.put("userToken",user.getToken());
			param.put("companyIdList", user.getCompanyIdList());
			param.put("depIdList", user.getDepIdList());
			List<AuthgroupUser> tempList=new ArrayList<AuthgroupUser>();
			List<AuthgroupUser> authgroupList=user.getTempAuthgroupList();//默认和通用的权限组列表（可确定的）
			List<AuthgroupUser> menuAuthgroupList=user.getMenuAuthgroupList();//自定义菜单数据权限组
			AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
			loanAuthgroupUser.setOrgId(user.getCompanyId());
			loanAuthgroupUser.setDataOwner(user.getUserId().toString());
			tempList.add(loanAuthgroupUser);
			if(authgroupList!=null && authgroupList.size()>0){
				tempList.addAll(authgroupList);
			}
			if(menuAuthgroupList!=null && menuAuthgroupList.size()>0){
				String url=SecurityUserHolder.getServletPath();
				String url2=SecurityUserHolder.getReferer();
				String laAuthorityId = null;
				List<Authority> authList=user.getAuthorityList();
				for (Authority la : authList) {
					if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(url)
							&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
						laAuthorityId=la.getAuthorityId();
						break;
					}
				}
				if(StringUtil.isEmpty(laAuthorityId)&&StringUtil.isNotEmpty(url2)){
					String temp=url2.substring(url2.indexOf("://")+3);
					temp=temp.substring(temp.indexOf("/"));
					if(temp.indexOf("?")>=0){
						temp=temp.substring(0, temp.indexOf("?"));
					}
					for (Authority la : authList) {
						if(StringUtil.isNotEmpty(la.getUrl())&&la.getUrl().equals(temp)
								&&StringUtil.isNotEmpty(la.getDataFlag())&&la.getDataFlag().equals("1")){
							laAuthorityId=la.getAuthorityId();
							break;
						}
					}
				}
				if(StringUtil.isNotEmpty(laAuthorityId)){
					for (AuthgroupUser lau : menuAuthgroupList) {
						if(null!=lau.getAuthorityId()&&lau.getAuthorityId().equals(laAuthorityId)){
							tempList.add(lau);
							String deptId=lau.getDeptId();
							if(StringUtil.isNotEmpty(deptId)){
								List<Dept> tempDeptList = deptApi.querySubDeptByDeptId(deptId);
								for (Dept ld : tempDeptList) {
									Integer ldDeptId = ld.getDeptId();
									if(ldDeptId!=null){
										AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
										loanAuthgroupUser2.setOrgId(ld.getCompanyId());
										loanAuthgroupUser2.setDeptId(ldDeptId.toString());
										tempList.add(loanAuthgroupUser2);
									}
								}
							}
						}
					}
				}
			}
			if(tempList.size()>0){
				param.put("authgroupList", tempList);
			}else{
				param.put("authgroupList", null);
			}
		}
    }
    
    public static LoanEmp getEmpFromSession(){
    	LoanEmp emp=new LoanEmp();
    	SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
    	if(user!=null){
    		emp.setLeAccount(user.getUsername());
    		emp.setLeEmpId(user.getUserId().intValue());
    		emp.setLeStaffName(user.getStaffName());
    		emp.setCompanyId(user.getUnionId());
    		emp.setBranchCompanyId(user.getCompanyId());
    		if (user.getDeptId()!=null) {
    			emp.setLeDeptId(Integer.parseInt(user.getDeptId()));
			}
    		emp.setLeMobile(user.getMobile());
    		emp.setLePassword(user.getPassword());
    		if(StringUtil.isNotEmpty(user.getCompanyId())){
    			UnionCompany vo=unionCompanyApi.get(user.getCompanyId());
    			if(vo!=null){
    				emp.setLeCity(vo.getCityName());
    			}
    		}
    	}
    	return emp;
    }
}