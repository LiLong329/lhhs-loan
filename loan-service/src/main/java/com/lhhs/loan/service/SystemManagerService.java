package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountCardRevise;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAuthgroup;
import com.lhhs.loan.dao.domain.LoanAuthority;
import com.lhhs.loan.dao.domain.LoanAuthorityGroup;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanQuarters;
import com.lhhs.loan.dao.domain.LoanRole;
import com.lhhs.loan.dao.domain.LoanRoleCompany;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanParentProduct;
public interface SystemManagerService {
	

	/**
	 * 查询系统所有角色
	 * @param params
	 * @param page
	 * @return
	 */
	public List<LoanRole> getRoles(Map<String, Object> params, Page page);
	/**
	 * 查询系统所有角色名称
	 * @return
	 */
	public List<String> getRoleNames();
	
	/**
	 * 查询后台权限菜单
	 * @param type	菜单AND功能 0全部  1菜单   2功能
	 * @param roleId	角色ID
	 * @return
	 */
	public List<LoanAuthority> getAuthoritys(int type,int roleId);
	
	/**
	 * 获取所有功能节点
	 * @return
	 */
	List<LoanAuthority> getAllAuthoritys();
	
	/**
	 * 根据用户id查找所拥有的全部权限
	 * @param type
	 * @param leEmployeeId
	 * @return
	 */
	public List<LoanAuthority> findAuthListByEmpId(Integer type, Integer leEmpId);
	
	/**
	 * 根据用户id查找所拥有的全部权限
	 * @param type
	 * @param leEmployeeId
	 * @return
	 */
	public List<LoanAuthority> findAuthListByEmpId(Integer leEmpId);
	
	/**
	 * 
	 * queryCompanyByListRoleId:(根据角色列表查询支持分公司列表). <br/>
	 * @author zhanghui
	 * @param roleIds
	 * @return
	 * @since JDK 1.8
	 */
	List<String>  queryCompanyByListRoleId(List<Integer> roleIds);
	
	/**
	 * 根据roleId查询角色
	 * @param roleId
	 * @return
	 */
	public  LoanRole findRoleById(Integer roleId);
	
	/**
	 * 根据角色名称查询角色
	 * @param rloeName
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> checkRoleByRoleName(String roleName) throws Exception;
	/**
	 * 添加角色
	 * @param loanRole
	 * @param authIds
	 * @return
	 * @throws Exception
	 */
	
	public Map<String, Object> insertLoanRole(LoanRole loanRole, String[] authIds,String[] companyId) throws Exception;
	/**
	 * 修改角色
	 * @param loanRole
	 * @param authIds
	 * @return
	 */
	public Map<String, Object> updateRole(LoanRole loanRole, String[] authIds,String[] companyId);
	/**
	 * 启用 、禁用角色
	 * @param role
	 * @return
	 */
	public Map<String, Object> roleIfEnable(LoanRole role);
	
	/**
	 * 查询所有岗位
	 * @param params
	 * @param page
	 * @return
	 */
	public List<LoanQuarters> getQuarters(Map<String, Object> params, Page page);
	/**
	 * 根据条件查询岗位
	 * @param params
	 * @param page
	 * @return
	 */
	public List<LoanQuarters> getQuartersByParams(Map<String, Object> params);

	/**
	 * 增加岗位
	 * @param quarters
	 * @return
	 */
	public Map<String, Object> insertQuarters(LoanQuarters quarters);

	/**
	 * 根据岗位id获取岗位
	 * @param quartersId
	 * @return
	 */
	public LoanQuarters getQuartersById(Integer quartersId);

	/**
	 * 修改岗位
	 * @param quarters
	 * @return
	 */
	public Map<String, Object> updateQuarters(LoanQuarters quarters);

	/**
	 * 岗位的禁用启用
	 * @param quarters
	 * @return
	 */
	public Map<String, Object> quartersEnabled(LoanQuarters quarters);
    /**
     * 员工密码修改
     * @param map
     * @return
     */
	public Map<String, Object> empPasswordModify(Map<String, Object> map);
	/**
	 * 组别列表
	 * @param map
	 * @param page
	 * @return
	 */
	public List<LoanGroup> groupList(Map<String, Object> map,Page page);
	/**
	 * 根据id修改组别
	 * @param group
	 * @return
	 */
	public int groupUpdateById(LoanGroup group);
	/**
	 * 新增组别
	 * @param group
	 * @return
	 */
	public int insertGroup(LoanGroup group);
	/**
	 * 根据状态修改组别
	 * @return
	 */
	public Map<String,Object> updateGroupByStuts(LoanGroup group);
	/**
	 * 部门列表查询
	 * @param map
	 * @param page
	 * @return
	 */
	public List<LoanDept> deptList(Map<String, Object> map,Page page);
	/**
	 * 插入新部门
	 * @param dept
	 * @return
	 */
	public int insertDept(LoanDept dept);
	/**
	 * 根据id修改部门
	 * @param dept
	 * @return
	 */
	public int deptUpdateById(LoanDept dept);
	/*
	 * 修改部门根据状态(启用禁用)
	 */
	public Map<String, Object> updateDeptByStuts(LoanDept dept);
	/**
	 * 查询一级产品列表
	 * @param productNo
	 * @return
	 */
	public List<LoanParentProduct> queryProductList(String productNo);
	/**
	 * 查询一级产品列表
	 * @param productNo
	 * @return
	 */
	public List<LoanParentProduct> queryProductListByType(String productType);
	/**
	 * 查询一级产品详情以及挂靠在一级产品下的所有二级产品
	 * @param productNo
	 * @return
	 */
	public LoanParentProduct queryProductInfo(String productNo);
	/**
	 * 查询二级产品详情根据ID
	 * @param map
	 * @return
	 */
	List<LoanChildProduct> queryProductById(Map<String,Object> map);
	/**
	 * 查询二级产品详情
	 * @param product_id
	 * @return
	 */
	Map<String,Object> queryChildProductInfo(String product_id);
	/**
	 * 查询组别
	 * @param lgGroupId
	 * @return
	 */
	LoanGroup selectGroupInfo(Integer lgGroupId);
	/*
	 * 查询全部部门
	 */
	List<Map<String, Object>> queryAllDept();
	/**
	 * 查询全部产品
	 * @return
	 */
	List<Map<String, Object>> queryAllProduct();
	/**
	 * 获取全部部门
	 * @param map
	 * @param page
	 * @return
	 */
	List<LoanDept> getDepartments(Map<String, Object> map,Page page);
	/**
	 * 新增部门
	 * @param dept
	 * @return
	 */
	Map<String,Object> insertDepartment(LoanDept dept);
	/**
	 * 查找部门
	 * @param map
	 * @return
	 */
	LoanDept findDeptById(Map<String, Object> map);
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	Map<String, Object> updateDepartment(LoanDept dept);
	/**
	 * 查询产品的资质列表
	 * @param productId
	 * @param proCrendentialsType
	 * @return
	 */
	Map<String, Object> proCredList(String productId,String proCrendentialsType);
	/**
	 * 查询账号列表
	 * @param map
	 * @param page
	 * @return
	 */
	List<LoanEmp> employeeList(Map<String, Object> map,Page page);
	/**
	 * 查询所有城市 部门 角色 组别 及其联动
	 * @return
	 */
	List<Map<String, Object>> queryAllCity();
	
	List<Map<String, Object>> queryDeptAll();
	
	List<Map<String, Object>> queryAllQuarters(Map<String, Object> map);
	
	List<Map<String, Object>> queryAllGroup(int lgDeptId);
	
	List<LoanGroup> allGroup();
	
	List<Map<String, Object>> queryEmpRole(int leEmpId);
	
    LoanEmp queryEmpInfo(Integer leEmpId);
    
    List<Map<String, Object>> getAllRole();
    
    /**
     * 禁用启用 Emp
     */
    public Map<String, Object> empEnableOrDisable(String leStatus,Integer leEmpId);
    
    /**
     * 员工信息更新Or新增
     */
	
    Map<String, Object> empUpdateOrAdd(LoanEmp loanEmp) throws Exception;
    /**
     * 账号唯一性查询校验
     * @param account
     * @return
     */
    LoanEmp selectByAccount(String account);
    /**
     * 重置密码
     * @param id
     * @return
     */
    Map<String,Object> resetPassword(Integer id);
    /**
     * 账号信息导出
     * @param map
     * @return
     */
    List<LoanEmp> employeeExport(Map<String, Object> map);
    /**
     * 查询组别唯一性
     */
    LoanGroup queryGroupCount(Map<String, Object> map);
    
    LoanGroup selectByPrimaryKey(Integer lgGroupId);
    
    int queryGroupbyName(Map<String, Object> map);
    
    List<Map<String, Object>> queryDeptThree();
    
    
    List<LoanAccountCard> queryAllAccount(Map<String, Object> map,Page page);
    
    List<LoanAccountCard> queryAllCard(Map<String, Object> map);
    
    LoanAccountInfo accountDetail(String accountId);
    
    int addAccount(LoanAccountInfo loanAccountInfo);
    
    Map<String, Object> accountAddandModify(LoanAccountInfo loanAccountInfo,List<LoanAccountCard> cards);  
    
	List<LoanUnionCompany> queryAllCompany();
	
	List<LoanUnionCompany> queryAllUnion();
	
	List<LoanBank> queryAllBank();
	
	List<LoanAccountCardRevise> queryAllRevise(Map<String, Object> map);
	/**
	 * 查询所有集团
	 */
	List<LoanUnionCompany> queryGroupCompany();
	
	List<LoanUnionCompany> queryCompany(String companyId);
	
	List<LoanUnionCompany> queryCompanySelective(Map<String,Object> params);
	
	List<LoanRoleCompany> queryRoleCompany(String roleId);
	
	int delCardInfo(Long cardId);
	
	LoanUnionCompany selectConpanyByPK(String companyId);
	
	List<LoanUnionCompany> selectProvinceByCompanyList(Map<String, Object> map);
	
	List<LoanUnionCompany> selectCompanyByProvinceCity(Map<String, Object> map);
	
	/**
	 * 根据公司查询员工列表
	 * @param companyId
	 * @return
	 */
	List<LoanEmp> queryEmpByCompanyId(String companyId);
	
	/**
	 * 根据员工编号查询岗位
	 * @param empId
	 * @return
	 */
	List<Integer> queryEmpQuartersById(Integer empId);
	/**
	 * 根据当前登录用户查询账号列表
	 * @param loanEmp
	 * @return
	 */
	List<LoanEmp> queryEmpListByEmp(LoanEmp loanEmp);
	/**
	 * 根据部门ID查询组别ID列表
	 * @param loanEmp
	 * @return
	 */
	List<String> queryGroupIdByDept(int lgDeptId);
	/**
	 * queryCustManagerByDeptId:查询部门下所有客户经理信息
	 */
	List<LoanEmp> queryCustManagerByDeptId(Map<String, Object> paramsMap);
	
	/**
	 * selectLoanEmpByPrimaryKey:根据主键查询操作员
	 */
	LoanEmp selectLoanEmpByPrimaryKey(Integer leEmpId);
	
	
	List<LoanEmp> queryEmpByMobile(String mobile);
	/**
	 * 根据公司查询一级部门
	 */
	List<LoanDept> queryOneLevelDeptByCompanyId(String ldCompany);
	
	List<LoanDept> querySubDeptByDeptId(String ldDeptId);
	/**
	 * 根据公司查询所有部门
	 */
	List<LoanDept> queryDeptByCompanyId(String ldCompany);
	
	/**
	 * 查询部门名称列表
	 */
	List<LoanDept> queryDeptName(Map<String, Object> params);
	
	List<LoanAuthgroup> queryAuthGroupList(Map<String, Object> map);
	
	List<Integer> selectAuthgroupIdsByUserId(Integer userId);
	
	List<LoanAuthority> findAuthListByEmpList(List<LoanAuthority> authList); 
	
	public List<LoanAuthgroup> getLoanAuthgroupList(Map<String, Object> params, Page page);
	
	public List<String> getAuthgroupNames();
	/**
	 * 查找各个公司下的部门及部门的子部门
	 * @param param  集团id
	 * @return
	 */
	public List<LoanUnionCompany> getAllCompanyTree(Map<String,Object> param);
	/**
	 * 查找所有的菜单
	 * @return
	 */
	public List<LoanAuthority> getAllLastAuthyList();
	/**
	 * 新增数据权限组
	 * @param loanAuthgroup
	 * @return
	 */
	public Map<String, Object> authorityGroupInsert(List<LoanAuthorityGroup> loanAuthorityGroupList,LoanAuthgroup loanAuthgroupVo);
	
	public LoanAuthgroup toAuthorityGroupDetail(Integer groupId);
	
	public Map<String, Object> authorityGroupUpdate(List<LoanAuthorityGroup> loanAuthorityGroupList,LoanAuthgroup loanAuthgroupVo);
	/**
	 * 验证数据组名是否重复
	 * @param loanAuthgroup
	 * @return
	 */
	public boolean authorityGroupValidate(LoanAuthgroup loanAuthgroup);
	public List<LoanAuthorityGroup> getSelectAutGupByGupId(Integer groupId);
	/**
	 * 根据条件查询分公司
	 * @param vo
	 * @return
	 */
	public List<LoanUnionCompany> getCompanyByCity(LoanUnionCompany vo);
	
	Map<String, Object> authorityGroupChange(LoanAuthgroup authGroup);
	List<User> queryCustManagerBySaas(Map<String, Object> paramsMap);
	
}
