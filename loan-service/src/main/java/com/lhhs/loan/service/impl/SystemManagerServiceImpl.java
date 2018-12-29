package com.lhhs.loan.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.encryption.MD5;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountCardReviseMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAuthgroupMapper;
import com.lhhs.loan.dao.LoanAuthgroupUserMapper;
import com.lhhs.loan.dao.LoanAuthorityGroupMapper;
import com.lhhs.loan.dao.LoanAuthorityMapper;
import com.lhhs.loan.dao.LoanBankMapper;
import com.lhhs.loan.dao.LoanChildProductMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanEmpQuartersMapper;
import com.lhhs.loan.dao.LoanEmpRoleMapper;
import com.lhhs.loan.dao.LoanGroupMapper;
import com.lhhs.loan.dao.LoanParentProductMapper;
import com.lhhs.loan.dao.LoanProductCredentialsMapper;
import com.lhhs.loan.dao.LoanQuartersMapper;
import com.lhhs.loan.dao.LoanRoleAuthorityMapper;
import com.lhhs.loan.dao.LoanRoleCompanyMapper;
import com.lhhs.loan.dao.LoanRoleMapper;
import com.lhhs.loan.dao.LoanRoleNodeMapper;
import com.lhhs.loan.dao.LoanUnionCompanyMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountCardRevise;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAuthgroup;
import com.lhhs.loan.dao.domain.LoanAuthgroupUser;
import com.lhhs.loan.dao.domain.LoanAuthority;
import com.lhhs.loan.dao.domain.LoanAuthorityGroup;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanEmpQuarters;
import com.lhhs.loan.dao.domain.LoanEmpRole;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanProductCredentials;
import com.lhhs.loan.dao.domain.LoanQuarters;
import com.lhhs.loan.dao.domain.LoanRole;
import com.lhhs.loan.dao.domain.LoanRoleAuthority;
import com.lhhs.loan.dao.domain.LoanRoleCompany;
import com.lhhs.loan.dao.domain.LoanRoleNode;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.account.AccountManagerService;

/**
 * 系统设置相关Service
 * 
 * @author Administrator
 *
 */
@Service("systemManager")
public class SystemManagerServiceImpl implements SystemManagerService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private LoanEmpRoleMapper loanEmpRoleMapper;
	@Autowired
	private LoanRoleMapper loanRoleMapper;
	@Autowired
	private LoanDeptMapper loanDeptMapper;
	@Autowired
	private LoanRoleAuthorityMapper loanRoleAuthorityMapper;
	@Autowired
	private LoanAuthorityMapper loanAuthorityMapper;
	@Autowired
	private LoanParentProductMapper loanParentProductMapper;
	@Autowired
	private LoanChildProductMapper loanChildProductMapper;
	@Autowired
	private LoanQuartersMapper loanQuartersMapper;
	@Autowired
	private LoanGroupMapper loanGroupMapper;
	@Autowired
	private LoanProductCredentialsMapper loanProductCredentialsMapper;
	@Autowired
	private LoanRoleNodeMapper loanRoleNodeMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanUnionCompanyMapper loanUnionCompanyMapper;
	@Autowired
	private LoanBankMapper loanBankMapper;
	@Autowired
	private LoanAccountCardReviseMapper loanAccountCardReviseMapper;
	@Autowired
	private LoanRoleCompanyMapper loanRoleCompanyMapper;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanEmpQuartersMapper loanEmpQuartersMapper;
	@Autowired
	private LoanAuthgroupMapper loanAuthgroupMapper;
	@Autowired
	private LoanAuthgroupUserMapper loanAuthgroupUserMapper;
	@Autowired
	private  LoanAuthorityGroupMapper loanAuthorityGroupMapper;
	@Reference
	private UnionCompanyApi unionCompanyApi;

	@Override
	public int groupUpdateById(LoanGroup group) {
		return loanGroupMapper.updateByPrimaryKeySelective(group);
	}

	@Override
	public Map<String, Object> empPasswordModify(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = loanEmpMapper.updateEmpPassword(map);
		if (count >= 1) {
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u5bc6\u7801\u4fee\u6539\u6210\u529f");// 密码修改成功
		} else {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u5bc6\u7801\u4fee\u6539\u5931\u8d25");// 密码修改失败
		}
		return resultMap;
	}

	@Override
	public List<LoanRole> getRoles(Map<String, Object> params, Page page) {
		List<LoanRole> roleList = loanRoleMapper.getRoles(params);
		Integer roleCount = loanRoleMapper.getRolesCount(params);
		if (page != null) {
			page.setTotalCount(roleCount);
			page.setResultList(roleList);

		}
		return roleList;
	}

	@Override
	public List<String> getRoleNames() {
		List<String> roleNameList = loanRoleMapper.getRoleNames();
		return roleNameList;
	}

	@Override
	public List<LoanAuthority> getAuthoritys(int type, int roleId) {
		List<LoanAuthority> loanAuthorityList = loanAuthorityMapper.getAuthoritys(type, roleId);
		if (type == 1) {
			return treeMenuList(loanAuthorityList, 0);
		}
		if (type == 0) {
			return treeMenuList(loanAuthorityList, 0);
		}
		return loanAuthorityList;
	}

	@Override
	public Map<String, Object> checkRoleByRoleName(String roleName) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		LoanRole loanRole = loanRoleMapper.selectByRoleName(roleName);
		if (loanRole != null) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "角色已存在！");
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "角色不存在！");
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	@Transactional()
	public Map<String, Object> insertLoanRole(LoanRole loanRole, String[] authIds, String[] companyId)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (loanRole == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "添加角色失败!");
			return result;
		}
		LoanRole role = loanRoleMapper.selectByRoleName(loanRole.getLrName());
		if (role != null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "角色名称已存在！");
			return result;
		}

		// 保存到角色表
		loanRole.setLrTime(new Date());
		loanRole.setLrStatus("1");// 新增时默认为启用状态
		loanRoleMapper.insertSelective(loanRole);

		// 保存到角色权限表
		Integer lrRoleId = loanRole.getLrRoleId();
		insertRoleAuth(authIds, lrRoleId);
		// 保存到角色节点表
		insertRoleNode(authIds, lrRoleId);
		// 保存到角色分公司关系表
		insertRoleCompany(companyId, lrRoleId);
		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		result.put(SystemConst.retMsg, "添加角色成功!");
		return result;
	}

	// 保存角色关系表
	private void insertRoleAuth(String[] autuIds, Integer roleId) {
		if (autuIds != null) {

			for (String autuId : autuIds) {
				LoanRoleAuthority roleAuth = new LoanRoleAuthority();
				roleAuth.setLraRoleId(roleId);
				roleAuth.setLraAuthorityId(Integer.parseInt(autuId));
				loanRoleAuthorityMapper.insertSelective(roleAuth);
			}
		}

	}

	// 保存角色公司关系表
	private void insertRoleCompany(String[] companyId, Integer roleId) {
		if (companyId != null) {
			for (String company : companyId) {
				LoanRoleCompany roleCompany = new LoanRoleCompany();
				roleCompany.setRoleId(String.valueOf(roleId));
				roleCompany.setCompanyId(company);
				loanRoleCompanyMapper.insertSelective(roleCompany);
			}
		}

	}

	// 保存角色节点表
	private void insertRoleNode(String[] autuIds, Integer roleId) {
		if (autuIds != null) {
			LoanRoleNode loanRoleNode = new LoanRoleNode();
			loanRoleNode.setRoleId(String.valueOf(roleId));
			for (String autuId : autuIds) {
				LoanAuthority auth = loanAuthorityMapper.selectByPrimaryKey(Integer.valueOf(autuId));
				if (auth != null) {
					if ("ROLE_CP".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("0");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;
					} else if ("ROLE_XH".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("1");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_MS".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("2");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_ZS".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("3");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;
					} else if ("ROLE_LOANAPPLY".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("4");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_LOANAUDIT".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("5");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_QYGZ".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("6");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_QZDY".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("7");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_LOANCONFIRM".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("8");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					} else if ("ROLE_CWFK".equals(auth.getLaAuthority())) {
						loanRoleNode.setNodeId("9");
						loanRoleNodeMapper.insert(loanRoleNode);
						continue;

					}
				}
			}
		}

	}

	public List<LoanAuthority> treeMenuList(List<LoanAuthority> LoanAuthorityList, int parentId) {
		List<LoanAuthority> loanAuthoritys = new ArrayList<LoanAuthority>();
		for (LoanAuthority loanAuthority : LoanAuthorityList) {
			if (loanAuthority != null) {
				int laAuthorityId = loanAuthority.getLaAuthorityId();// 获取菜单的id
				int laFatherNode = loanAuthority.getLaFatherNode();// 获取父节点id
				if (parentId == laFatherNode) {
					List<LoanAuthority> LoanAuthorityFruit = treeMenuList(LoanAuthorityList, laAuthorityId);
					loanAuthority.setLoanAuthorityList(LoanAuthorityFruit);
					loanAuthoritys.add(loanAuthority);
				}
			}

		}
		return loanAuthoritys;
	}

	@Override
	@Transactional
	public Map<String, Object> updateRole(LoanRole loanRole, String[] authIds, String[] companyId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoanRole role = loanRoleMapper.selectByPrimaryKey(loanRole.getLrRoleId());

			if (role != null && role.getLrName() != null && !role.getLrName().equals(loanRole.getLrName())) {
				LoanRole lr = loanRoleMapper.selectByRoleName(loanRole.getLrName());
				if (lr != null) {
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "角色名称已存在!");
					return result;
				}
			}
			loanRoleMapper.updateByPrimaryKeySelective(loanRole);
			Integer lrRoleId = loanRole.getLrRoleId();
			// 先删除角色权限关系表
			loanRoleAuthorityMapper.delByRoleId(lrRoleId);
			// 保存角色权限关系表
			this.insertRoleAuth(authIds, lrRoleId);

			// 删除角色节点表
			loanRoleNodeMapper.deleteByRoleId(String.valueOf(lrRoleId));
			// 保存角色节点表
			this.insertRoleNode(authIds, lrRoleId);

			// 删除角色分公司表
			loanRoleCompanyMapper.deleteByRoleId(String.valueOf(lrRoleId));
			this.insertRoleCompany(companyId, lrRoleId);

			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "修改角色成功!");
		} catch (Exception e) {
			logger.error("Error" + e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "修改角色失败!");

		}

		return result;
	}

	@Override
	public Map<String, Object> roleIfEnable(LoanRole role) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前为禁用状态
		if (role != null && role.getLrStatus().equals("0")) {
			// 设置为启用状态
			role.setLrStatus("1");
			int count = loanRoleMapper.updateByPrimaryKeySelective(role);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "角色启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "角色启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			role.setLrStatus("0");
			int count = loanRoleMapper.updateByPrimaryKeySelective(role);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "角色禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "角色禁用失败!");
			}
		}

		return result;
	}
	@Override
	public Map<String, Object> authorityGroupChange(LoanAuthgroup authGroup) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前为禁用状态(失效)
		if (null != authGroup && "99".equals(authGroup.getStatus())) {
			// 设置为启用状态
			authGroup.setStatus("03");
			int count = loanAuthgroupMapper.updateByPrimaryKeySelective(authGroup);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "数据权限组启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "数据权限组启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			authGroup.setStatus("99");
			int count = loanAuthgroupMapper.updateByPrimaryKeySelective(authGroup);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "数据权限组禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "数据权限组禁用失败!");
			}
		}
		return result;
	}

	@Override
	public List<LoanGroup> groupList(Map<String, Object> map, Page page) {
		List<LoanGroup> list = loanGroupMapper.selectGroupList(map);
		Integer Count = loanGroupMapper.selectGroupListCount(map);
		if(null != page){
			page.setResultList(list);
			page.setTotalCount(Count);
		}
		return list;
	}

	@Override
	public int insertGroup(LoanGroup group) {
		return loanGroupMapper.insertSelective(group);
	}

	@Override
	public Map<String, Object> updateGroupByStuts(LoanGroup group) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前为禁用状态
		if (group != null && group.getLgStatus().equals("0")) {
			// 设置为启用状态
			group.setLgStatus("1");
			int count = loanGroupMapper.updateByPrimaryKeySelective(group);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "组别启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "组别启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			group.setLgStatus("0");
			int count = loanGroupMapper.updateByPrimaryKeySelective(group);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "组别禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "组别禁用失败!");
			}
		}
		return result;
	}

	@Override
	public List<LoanDept> deptList(Map<String, Object> map, Page page) {
		List<LoanDept> list = loanDeptMapper.selectDeptList(map);
		Integer Count = loanDeptMapper.selectDeptListCount(map);
		if (page != null) {
			page.setResultList(list);
			page.setTotalCount(Count);
		}

		return list;
	}

	@Override
	public int insertDept(LoanDept dept) {
		return loanDeptMapper.insertSelective(dept);
	}

	@Override
	public int deptUpdateById(LoanDept dept) {
		return loanDeptMapper.updateByPrimaryKeySelective(dept);
	}

	@Override
	public Map<String, Object> updateDeptByStuts(LoanDept dept) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前为禁用状态
		if (dept != null && dept.getLdStatus().equals("0")) {
			// 设置为启用状态
			dept.setLdStatus("1");
			int count = loanDeptMapper.updateByPrimaryKeySelective(dept);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "部门启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "部门启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			dept.setLdStatus("0");
			int count = loanDeptMapper.updateByPrimaryKeySelective(dept);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "部门禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "部门禁用失败!");
			}
		}

		return result;
	}

	@Override
	public List<LoanParentProduct> queryProductList(String productNo) {
		return loanParentProductMapper.queryProductList(productNo);
	}

	@Override
	public LoanParentProduct queryProductInfo(String productNo) {
		return loanParentProductMapper.selectByPrimaryKey(productNo);
	}

	@Override
	public List<LoanChildProduct> queryProductById(Map<String, Object> map) {
		return loanChildProductMapper.queryProductById(map);
	}

	@Override
	public Map<String, Object> queryChildProductInfo(String productId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoanChildProductWithBLOBs product = loanChildProductMapper.queryProductInfoById(productId);
		List<LoanProductCredentials> credentials = loanChildProductMapper.queryProductCredentInfo(productId);
		product.setProductAdvantage(product.getProductAdvantage().replaceAll("\r\n", "<br/>"));
		product.setFeeDescription(product.getFeeDescription().replaceAll("\r\n", "<br/>"));
		product.setApplicationConditions(product.getApplicationConditions().replaceAll("\r\n", "<br/>"));
		product.setMaterialRequested(product.getMaterialRequested().replaceAll("\r\n", "<br/>"));
		resultMap.put("product", product);
		resultMap.put("credentials", credentials);
		return resultMap;
	}

	@Override
	public LoanGroup selectGroupInfo(Integer lgGroupId) {

		return loanGroupMapper.selectByPrimaryKey(lgGroupId);
	}

	@Override
	public LoanRole findRoleById(Integer roleId) {
		// TODO Auto-generated method stub
		return loanRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public List<Map<String, Object>> queryAllDept() {

		return loanDeptMapper.queryAllDept();
	}

	@Override
	public List<Map<String, Object>> queryAllProduct() {
		return loanParentProductMapper.queryAllProduct();
	}

	@Override
	public List<LoanDept> getDepartments(Map<String, Object> map, Page page) {
		List<LoanDept> list = loanDeptMapper.selectDeptList(map);
		Integer Count = loanDeptMapper.selectDeptListCount(map);
		if (page != null) {
			page.setResultList(list);
			page.setTotalCount(Count);
		}
		return list;
	}

	@Override
	public Map<String, Object> insertDepartment(LoanDept dept) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoanEmp  loanEmp = CommonUtils.getEmpFromSession();
		int deptCount = loanDeptMapper.queryDeptByName(dept);
		if (deptCount >= 1) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg,
					"\u90e8\u95e8\u5df2\u5b58\u5728\uff0c\u8bf7\u52ff\u91cd\u590d\u6dfb\u52a0\uff01");
			return resultMap;
		}
		dept.setLdTime(new Date());
		dept.setLdStatus("1");
		dept.setLayer(1);
		if(StringUtils.isEmpty(dept.getLdUnion())){
			dept.setLdUnion(loanEmp.getCompanyId());
		}
		if(StringUtils.isEmpty(dept.getLdCompany())){
			dept.setLdCompany(loanEmp.getBranchCompanyId());
		}
		if(StringUtils.isEmpty(dept.getParentId())){
		     dept.setParentId(0);
		     dept.setParentIds("0");
		}else{
			 LoanDept ld =loanDeptMapper.queryDept(dept.getParentId());
			 if(ld!=null){
				 dept.setLayer(ld.getLayer()+1);
				 if(StringUtils.isEmpty(ld.getParentIds())){
					 dept.setParentIds("0,"+String.valueOf(dept.getParentId()));
				 }else{
					 dept.setParentIds(ld.getParentIds()+","+String.valueOf(dept.getParentId()));
				 }
			 }
		}
		int count = loanDeptMapper.insertSelective(dept);
		if (count >= 1) {
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u65b0\u589e\u90e8\u95e8\u6210\u529f");
		} else {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u65b0\u589e\u90e8\u95e8\u5931\u8d25");
		}
		return resultMap;
	}

	@Override
	public LoanDept findDeptById(Map<String, Object> map) {
		return loanDeptMapper.queryDeptInfo(map);
	}

	@Override
	public List<LoanQuarters> getQuarters(Map<String, Object> params, Page page) {

		List<LoanQuarters> quartersList = loanQuartersMapper.getQuarters(params);
		Integer totalCount = loanQuartersMapper.getQuartersCount(params);
		if (page != null) {
			page.setResultList(quartersList);
			page.setTotalCount(totalCount);
		}
		return quartersList;
	}

	@Override
	public List<LoanQuarters> getQuartersByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanQuartersMapper.getQuartersByParams(params);
	}

	@Override
	public Map<String, Object> insertQuarters(LoanQuarters quarters) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
	
		quarters.setLqStatus("1");// 创建岗位默认为启用状态
		quarters.setLqTime(new Date());
		Integer parentId = quarters.getLqParentId();
		if (parentId == null) {
			quarters.setLqGrade(1);
		} else {
			LoanQuarters quarter = loanQuartersMapper.selectByPrimaryKey(parentId);
			Integer grade = quarter.getLqGrade() == null ? 1 : quarter.getLqGrade();
			quarters.setLqGrade(grade + 1);
		}

		try {
			params.put("lqName", quarters.getLqName());
			List<LoanQuarters> quartersList = loanQuartersMapper.getQuartersByParams(params);
			if (quartersList.size() != 0) {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位名称已存在!");
				return result;
			}
			params.put("lqName", null);
			params.put("lqEnglishName", quarters.getLqEnglishName());
			List<LoanQuarters> loanQuarters= loanQuartersMapper.getQuartersByParams(params);
			if (loanQuarters.size() != 0) {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位英文名称已存在!");
				return result;
			}

			int count = loanQuartersMapper.insertSelective(quarters);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "岗位添加成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位添加失败!");
			}
		} catch (Exception e) {
			logger.error("Exception" + e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "岗位添加失败!");
		}

		return result;
	}

	@Override
	public LoanQuarters getQuartersById(Integer quartersId) {
		LoanQuarters quarters = loanQuartersMapper.selectByQuartersId(quartersId);
		return quarters;
	}

	@Override
	public Map<String, Object> updateQuarters(LoanQuarters quarters) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer parentId = quarters.getLqParentId();
		if (parentId == null) {
			quarters.setLqGrade(1);
		} else {
			LoanQuarters quarter = loanQuartersMapper.selectByPrimaryKey(parentId);
			Integer grade = quarter.getLqGrade() == null ? 1 : quarter.getLqGrade();
			quarters.setLqGrade(grade + 1);
		}
		try {
			LoanQuarters loanQuarters = getQuartersById(quarters.getLqQuartersId());
			if (loanQuarters != null && loanQuarters.getLqName() != null
					&& !loanQuarters.getLqName().equals(quarters.getLqName())) {
				params.put("lqName", quarters.getLqName());
				List<LoanQuarters> quartersList = loanQuartersMapper.getQuartersByParams(params);
				if (quartersList.size() != 0) {
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "岗位名称已存在!");
					return result;
				}
			}
			if (loanQuarters != null && loanQuarters.getLqName() != null
					&& !loanQuarters.getLqEnglishName().equals(quarters.getLqEnglishName())) {
				params.put("lqName", null);
				params.put("lqEnglishName", quarters.getLqEnglishName());
				List<LoanQuarters> loanQuartersList= loanQuartersMapper.getQuartersByParams(params);
				if (loanQuartersList.size() != 0) {
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "岗位英文名称已存在!");
					return result;
				}
			}
			int count = loanQuartersMapper.updateByQuartersId(quarters);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "岗位修改成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位修改失败!");
			}
		} catch (Exception e) {
			logger.info("Exception" + e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "岗位修改失败!");
		}
		return result;
	}

	@Override
	public Map<String, Object> quartersEnabled(LoanQuarters quarters) {

		Map<String, Object> result = new HashMap<String, Object>();
		// 当前为禁用状态
		if (quarters != null && "0".equals(quarters.getLqStatus())) {
			// 设置为启用状态
			quarters.setLqStatus("1");
			int count = loanQuartersMapper.updateByPrimaryKeySelective(quarters);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "岗位启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			quarters.setLqStatus("0");
			int count = loanQuartersMapper.updateByPrimaryKeySelective(quarters);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "岗位禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "岗位禁用失败!");
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> updateDepartment(LoanDept dept) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<LoanDept> deptList= loanDeptMapper.queryDeptListByName(dept);
		if (null!=deptList&&deptList.size()>0&&null!=deptList.get(0).getLdDeptId()
				&&deptList.get(0).getLdDeptId()==dept.getLdDeptId()) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg,
					"\u90e8\u95e8\u5df2\u5b58\u5728\uff0c\u8bf7\u52ff\u91cd\u590d\u6dfb\u52a0\uff01");
			return resultMap;
		}
		try {
			if(StringUtils.isEmpty(dept.getParentId())){
				 dept.setLayer(1);
			     dept.setParentId(0);
			     dept.setParentIds("0");
			}else{
				 LoanDept ld =loanDeptMapper.queryDept(dept.getParentId());
				 if(ld!=null){
					 dept.setLayer(ld.getLayer()+1);
					 if(StringUtils.isEmpty(ld.getParentIds())){
						 dept.setParentIds("0,"+String.valueOf(dept.getParentId()));
					 }else{
						 dept.setParentIds(ld.getParentIds()+","+String.valueOf(dept.getParentId()));
					 }
				 }
			}
			
			int count = loanDeptMapper.updateByPrimaryKeySelective(dept);
			if (count >= 1) {
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
				resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u6210\u529f");// 成功
			} else {
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u5931\u8d25");// 失败
			}
		} catch (Exception e) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u63cf\u8ff0\u8fc7\u957f\u002c\u7cfb\u7edf\u9519\u8bef");// 失败
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> proCredList(String productId, String proCrendentialsType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("proCrendentialsType", proCrendentialsType);
		resultMap.put("productId", productId);
		String proCredNameN = " ";
		String proCredNameY = " ";
		List<LoanProductCredentials> list = loanProductCredentialsMapper.proCredList(resultMap);
		resultMap.clear();
		int size = list.size();
		if (list.size() == 0) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			return resultMap;

		} else {
			for (LoanProductCredentials loanProductCredentials : list) {
				String required = loanProductCredentials.getProRequired();
				if (required.equals("0")) {
					proCredNameN += loanProductCredentials.getProCredentialsName() + "<br/>";
				} else if (required.equals("1")) {
					proCredNameY += loanProductCredentials.getProCredentialsName() + "<br/>";
				}
			}
			resultMap.put("proCredNameN", proCredNameN);
			resultMap.put("proCredNameY", proCredNameY);
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			return resultMap;
		}
	}

	@Override
	public List<LoanAuthority> findAuthListByEmpId(Integer type, Integer leEmpId) {
		List<LoanAuthority> authList = loanAuthorityMapper.findAuthListByEmpId(type, leEmpId);
		if (type == 1) {
			return treeMenuList(authList, 0);
		}
		return authList;
	}

	@Override
	public List<LoanEmp> employeeList(Map<String, Object> map, Page page) {
		List<LoanEmp> list = loanEmpMapper.queryEmployeeList(map);
		Integer Count = loanEmpMapper.queryEmployeeCount(map);
		if(list!=null&&list.size()>0){
			for(LoanEmp emp :list){
			  List<String> quartersName = loanEmpQuartersMapper.selectQuartersNamesByEmpId(emp.getLeEmpId());
			  emp.setQuartersNames(quartersName);
			}
		}
		if(page != null){
			page.setResultList(list);
			page.setTotalCount(Count);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAllCity() {
		return loanEmpMapper.queryAllCity();
	}

	@Override
	public List<Map<String, Object>> queryDeptAll() {
		return loanEmpMapper.queryAllDept();
	}

	@Override
	public List<Map<String, Object>> queryAllQuarters(Map<String, Object> map) {
		return loanEmpMapper.queryAllQuarters(map);
	}

	@Override
	public LoanEmp queryEmpInfo(Integer leEmpId) {

		return loanEmpMapper.queryEmpInfo(leEmpId);
	}

	@Override
	public List<Map<String, Object>> getAllRole() {
		return loanRoleMapper.getAllRole();
	}

	@Override
	public List<Map<String, Object>> queryAllGroup(int lgDeptId) {
		return loanEmpMapper.queryAllGroup(lgDeptId);
	}

	@Override
	public List<Map<String, Object>> queryEmpRole(int leEmpId) {
		return loanEmpMapper.queryEmpRole(leEmpId);
	}

	@Override
	public Map<String, Object> empEnableOrDisable(String leStatus, Integer leEmpId) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoanEmp emp = new LoanEmp();
		emp.setLeEmpId(leEmpId);
		// 当前为禁用状态
		if (leStatus.equals("0")) {
			// 设置为启用状态
			emp.setLeStatus("1");
			int count = loanEmpMapper.updateByPrimaryKeySelective(emp);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "账号启用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "账号启用失败!");
			}
		} else {
			// 否则为启用状态设置为禁用状态
			emp.setLeStatus("0");
			int count = loanEmpMapper.updateByPrimaryKeySelective(emp);
			if (count > 0) {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "账号禁用成功!");
			} else {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "账号禁用失败!");
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> empUpdateOrAdd(LoanEmp loanEmp) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<LoanEmpRole> empRoles = null;
		List<Integer> roleList = loanEmp.getLrRoleId();
		List<Integer> quartersList = loanEmp.getLqQuartersId();
		List<Integer> authgroupIds = loanEmp.getAuthgroupIds();
		Integer empId = loanEmp.getLeEmpId();
		int count = 0;
		if (empId != null) {// 有empId为更新操作
			List<LoanEmp> emps = loanEmpMapper.queryEmpMobileIsExistence(loanEmp);
			if(emps!=null&&emps.size()>0){
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "手机号已存在");
				return resultMap;
			}
			count = loanEmpMapper.updateByPrimaryKeySelective(loanEmp);
			//更新用户角色关系表
			for (Integer role : roleList) {
				LoanEmpRole emp = loanEmpRoleMapper.isEmpRole(loanEmp.getLeEmpId(), role);
				if (emp == null) {
					LoanEmpRole empRole = new LoanEmpRole();
					empRole.setLerEmpId(loanEmp.getLeEmpId());
					empRole.setLerRoleId(role);
					loanEmpRoleMapper.insertSelective(empRole);
				}
			}
			empRoles = loanEmpRoleMapper.allMyEmpRole(loanEmp.getLeEmpId());
			for (LoanEmpRole roleemp : empRoles) {
				boolean flag = true;
				for (Integer role : roleList) {
					if (roleemp.getLerRoleId().compareTo(role) == 0) {
						flag = false;
						break;
					}
				}
				if (flag) {
					loanEmpRoleMapper.deleteByPrimaryKey(roleemp.getLerErId());
				}
			}
			//更新用户岗位关系表
			loanEmpQuartersMapper.deleteByEmpId(empId);
			LoanEmpQuarters  empQuarters = new LoanEmpQuarters();
			for(Integer quarters : quartersList){
				LoanQuarters  lq = loanQuartersMapper.selectByPrimaryKey(quarters);
				if(lq!=null){
					empQuarters.setLeqEnglishName(lq.getLqEnglishName());
				}
				empQuarters.setLeqEmpId(empId);
				empQuarters.setLeqQuartersId(quarters);
				loanEmpQuartersMapper.insertSelective(empQuarters);
			}
			//更新用户数据权限组表
			loanAuthgroupUserMapper.deleteByUserId(empId);
			LoanAuthgroupUser loanAuthgroupUser=new LoanAuthgroupUser();
			for(Integer groupId : authgroupIds){
				loanAuthgroupUser.setUserId(empId);
				loanAuthgroupUser.setGroupId(groupId);
				loanAuthgroupUserMapper.insertSelective(loanAuthgroupUser);
			}
			
		} else {

			LoanEmp emp = loanEmpMapper.queryEmpByAccount(loanEmp.getLeAccount());
			if (emp != null) {
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u8be5\u8d26\u53f7\u5df2\u5b58\u5728");
				return resultMap;
			}
			List<LoanEmp> employee = loanEmpMapper.queryEmpByMobile(loanEmp.getLeMobile());
			if (employee.size() >= 1 && employee != null) {
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u8be5\u624b\u673a\u53f7\u4e3a\u5df2\u6ce8\u518c\u8d26\u6237");
				return resultMap;
			}

			loanEmp.setLeTime(new Date());// 新增员工时间戳
			loanEmp.setLeStatus("1");// 默认员工启用状态
			loanEmp.setLePassword(MD5.MD5(loanEmp.getLePassword()));// 密码加密
			count = loanEmpMapper.insertSelective(loanEmp);
			LoanEmp loan = loanEmpMapper.queryEmpByAccount(loanEmp.getLeAccount());
			//保存用户角色关系表
			LoanEmpRole empRole = new LoanEmpRole();
			for (Integer role : roleList) {
				empRole.setLerEmpId(loan.getLeEmpId());
				empRole.setLerRoleId(role);
				loanEmpRoleMapper.insertSelective(empRole);
			}
			//保存用户岗位关系表
			LoanEmpQuarters  empQuarters = new LoanEmpQuarters();
			for(Integer quarters : quartersList){
				LoanQuarters  lq = loanQuartersMapper.selectByPrimaryKey(quarters);
				if(lq!=null){
					empQuarters.setLeqEnglishName(lq.getLqEnglishName());
				}
				empQuarters.setLeqEmpId(loan.getLeEmpId());
				empQuarters.setLeqQuartersId(quarters);
				loanEmpQuartersMapper.insertSelective(empQuarters);
			}
			//保存用户数据权限组表
			LoanAuthgroupUser loanAuthgroupUser=new LoanAuthgroupUser();
			for(Integer groupId : authgroupIds){
				loanAuthgroupUser.setUserId(loan.getLeEmpId());
				loanAuthgroupUser.setGroupId(groupId);
				loanAuthgroupUserMapper.insertSelective(loanAuthgroupUser);
			}
		}
		if (count >= 1) {
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
		} else {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
		}
		return resultMap;
	}

	@Override
	public LoanEmp selectByAccount(String account) {
		return loanEmpMapper.queryEmpByAccount(account);
	}

	@Override
	public Map<String, Object> resetPassword(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		LoanEmp employee = loanEmpMapper.selectByPrimaryKey(id);
		if (employee == null) {
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "\u8BE5\u7528\u6237\u4E0D\u5B58\u5728");// 该用户不存在
			return ret;
		}
		try {
			employee.setLePassword((MD5.MD5("111111")));
			;
			int count = loanEmpMapper.updateByPrimaryKeySelective(employee);
			if (count == 1) {
				ret.put(SystemConst.retCode, SystemConst.SUCCESS);
				ret.put("password", "111111");
			} else {
				ret.put(SystemConst.retCode, SystemConst.FAIL);
				ret.put(SystemConst.retMsg, "\u91CD\u7F6E\u5BC6\u7801\u5931\u8D25");// 重置密码失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "\u91CD\u7F6E\u5BC6\u7801\u5931\u8D25");// 重置密码失败
		}
		return ret;
	}

	@Override
	public List<LoanEmp> employeeExport(Map<String, Object> map) {

		List<LoanEmp> list = loanEmpMapper.queryEmployeeListExport(map);
		if (list != null & list.size() > 0) {
			for (LoanEmp loanEmp : list) {
				if (loanEmp.getLeStatus().equals("0")) {
					loanEmp.setLeStatus("禁用");
				} else {
					loanEmp.setLeStatus("正常");
				}
				if (loanEmp.getLeSex().equals("0")) {
					loanEmp.setLeSex("女");
				} else {
					loanEmp.setLeSex("男");
				}
				List<String> quartersName = loanEmpQuartersMapper.selectQuartersNamesByEmpId(loanEmp.getLeEmpId());
				loanEmp.setLqName(quartersName.toString().substring(1,quartersName.toString().length()-1));
			}
		}
		return list;
	}

	@Override
	public List<LoanAuthority> getAllAuthoritys() {
		// TODO Auto-generated method stub
		List<LoanAuthority> list = loanAuthorityMapper.getAuthList();
		return treeMenuList(list, 0);
	}

	@Override
	public LoanGroup queryGroupCount(Map<String, Object> map) {
		return loanGroupMapper.queryGroupCount(map);
	}

	@Override
	public LoanGroup selectByPrimaryKey(Integer lgGroupId) {
		return loanGroupMapper.selectByPrimaryKey(lgGroupId);
	}

	@Override
	public int queryGroupbyName(Map<String, Object> map) {
		return loanGroupMapper.queryGroupbyName(map);
	}

	@Override
	public List<LoanGroup> allGroup() {
		return loanGroupMapper.allGroup();
	}

	@Override
	public List<Map<String, Object>> queryDeptThree() {
		return loanEmpMapper.queryDeptAll();
	}

	@Override
	public List<LoanAccountCard> queryAllAccount(Map<String, Object> map, Page page) {
		List<LoanAccountCard> accountInfos = loanAccountInfoMapper.queryCompanyAccount(map);
		
		SecurityUser emp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		for (LoanAccountCard account : accountInfos) {
			if(emp.getCompanyId()!=null && emp.getUnionId()!=null){
				if(emp.getUnionId().equals(account.getCompanyId())){
					account.setIsGroup("isGroup");
				}else{
					account.setIsGroup("notGroup");
				}
			}
		}
		int count = loanAccountInfoMapper.queryCompanyAccountCount(map);
		page.setResultList(accountInfos);
		page.setTotalCount(count);
		return accountInfos;
	}

	@Override
	public List<LoanAccountCard> queryAllCard(Map<String, Object> map) {
		return loanAccountCardMapper.queryAccountCard(map);
	}

	@Override
	public LoanAccountInfo accountDetail(String accountId) {
		return loanAccountInfoMapper.queryCompanyAccountDetail(accountId);
	}

	@Override
	public int addAccount(LoanAccountInfo loanAccountInfo) {
		return loanAccountInfoMapper.insertSelective(loanAccountInfo);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> accountAddandModify(LoanAccountInfo loanAccountInfo, List<LoanAccountCard> cards) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoanBank bank = null;//銀行字典類
		LoanAccountCard cc = null;
		LoanAccountCardRevise revise = null;//修改記錄
		//校验是否存在该公司
		UnionCompany company = unionCompanyApi.get(loanAccountInfo.getOwnerId());
		if (company == null || StringUtils.isEmpty(company.getCompanyId())) {
			logger.debug("不存在所属公司");
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "所属公司不存在，请重新选择");
			return resultMap;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountId", loanAccountInfo.getAccountId());
		param.put("accountHolder", loanAccountInfo.getAccountHolder());
		List<LoanAccountCard> allCards = loanAccountCardMapper.queryAccountCard(param);
		for (LoanAccountCard c : allCards) {
			boolean flag = true;
			for (LoanAccountCard ccc : cards) {
				if (c.getBankCardNo().compareTo(ccc.getBankCardNo()) == 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				loanAccountCardMapper.deleteByPrimaryKey(c.getId());
			}
		}
		//查詢賬戶是否存在
	    LoanAccountInfo account = loanAccountInfoMapper.selectByOwnerId(loanAccountInfo.getOwnerId());
		if (account != null) {
			loanAccountInfo.setAccountId(account.getAccountId());
		} else {
			loanAccountInfo.setUseType("00");
			loanAccountInfo.setLevel("1");
			loanAccountInfo.setCompanyId(loanAccountInfo.getOwnerId());
			loanAccountInfo.setStatus(SystemConst.Status.STATUS03);
			loanAccountInfo.setAccountType(SystemConst.AccountType.COMPANY);
			loanAccountInfo.setProvienceNo(loanAccountInfo.getProvienceName());
			loanAccountInfo.setCityNo(loanAccountInfo.getCityName());
			loanAccountInfo.setCreateTime(new Date());
			loanAccountInfo.setOwnerName(company.getCompanyName());
			loanAccountInfo.setUnionId(company.getUnionId());
		}
		int counts = 0;
		for (LoanAccountCard card : cards) {
			bank = loanBankMapper.selectByPrimaryKey(card.getBankId());
			//插入修改记录
			cc = loanAccountCardMapper.selectByPrimaryKey(card.getId());
			if (cc != null) {
				if (!card.getBankId().equals(cc.getBankId()) || !card.getBranchName().equals(cc.getBranchName())
						|| !card.getBankCardNo().equals(cc.getBankCardNo())) {
					revise = new LoanAccountCardRevise();
					revise.setCardId(card.getId());
					revise.setAccountId(card.getAccountId());
					revise.setOwnerId(card.getOwnerId());
					revise.setOwnerName(card.getOwnerName());
					revise.setAccountType(card.getAccountType());
					revise.setBankCardNo(card.getBankAb());
					revise.setAccountType(SystemConst.AccountType.COMPANY);
					revise.setBankCardNoNew(card.getBankCardNo());
					revise.setBankName(bank.getBankName());
					revise.setBankId(card.getBankId());
					revise.setBranchName(card.getBranchName());
					revise.setCreateTime(new Date());
					revise.setLastModifyTime(new Date());
					revise.setReviseTime(new Date());
					loanAccountCardReviseMapper.insertSelective(revise);
				}

			}
			
			BeanUtils.copyProperties(loanAccountInfo, card);
			if(cc==null){
				card.setCreateTime(new Date());
				card.setStatus("03");
			}
			card.setAccountType(SystemConst.AccountType.COMPANY);
			card.setBankAb(bank.getBankAb());
			card.setBankName(bank.getBankName());
		    card.setAccountName(company.getCompanyName());
			card.setAccountHolder(loanAccountInfo.getAccountHolder());
			card.setMobile(loanAccountInfo.getMobile());
			card.setBankCity(company.getCityName());
			card.setBankProvince(company.getProvinceName());
			card.setKind(card.getKind());
			String code = accountManagerService.saveOrUpdateCard(card);
			if (code.equals(SystemConst.SUCCESS)) {
				counts++;
			}
		}
		
		if (counts == cards.size()) {
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
		} else {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
		}
		return resultMap;
	}

	@Override
	public int delCardInfo(Long cardId) {
		return loanAccountCardMapper.deleteByPrimaryKey(cardId);
	}

	@Override
	public List<LoanUnionCompany> queryAllCompany() {
		return loanUnionCompanyMapper.queryAllCompany();
	}

	@Override
	public List<LoanBank> queryAllBank() {
		return loanBankMapper.queryAllBank();
	}

	@Override
	public List<LoanAccountCardRevise> queryAllRevise(Map<String, Object> map) {

		return loanAccountCardReviseMapper.queryReviseDetail(map);
	}

	@Override
	public List<LoanUnionCompany> queryGroupCompany() {
		return loanUnionCompanyMapper.queryGroupCompany();
	}

	@Override
	public List<LoanUnionCompany> queryCompany(String companyId) {
		return loanUnionCompanyMapper.queryCompany(companyId);
	}
	@Override
	public List<LoanUnionCompany> queryCompanySelective(Map<String,Object> params) {
		return loanUnionCompanyMapper.queryCompanySelective(params);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see com.lhhs.loan.service.SystemManagerService#queryRoleCompany(java.lang.String)
	 */

	@Override
	public List<LoanRoleCompany> queryRoleCompany(String roleId) {

		// TODO Auto-generated method stub
		return loanRoleCompanyMapper.queryRoleCompany(roleId);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryCompanyByListRoleId(java.util.List)
	 */
	 
	@Override
	public List<String> queryCompanyByListRoleId(List<Integer> roleIds) {
		
		// TODO Auto-generated method stub
		return loanRoleCompanyMapper.queryCompanyByListRoleId(roleIds);
	}
	
	@Override
	public LoanUnionCompany selectConpanyByPK(String companyId) {
		return loanUnionCompanyMapper.selectByPrimaryKey(companyId);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#selectProvinceByCompanyList(java.util.Map)
	 */
	 
	@Override
	public List<LoanUnionCompany> selectProvinceByCompanyList(Map<String, Object> map) {
		
		// TODO Auto-generated method stub
		return loanUnionCompanyMapper.selectProvinceByCompanyList(map);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#selectCompanyByProvinceCity(java.util.Map)
	 */
	 
	@Override
	public List<LoanUnionCompany> selectCompanyByProvinceCity(Map<String, Object> map) {
		
		// TODO Auto-generated method stub
		return loanUnionCompanyMapper.selectCompanyByProvinceCity(map);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryEmpByCompanyId(java.lang.String)
	 */
	 
	@Override
	public List<LoanEmp> queryEmpByCompanyId(String companyId) {
		
		// TODO Auto-generated method stub
		return loanEmpMapper.queryEmpByCompanyId(companyId);
	}
	
	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryEmpQuartersById(java.lang.String)
	 */
	 
	@Override
	public List<Integer> queryEmpQuartersById(Integer empId) {
		
		// TODO Auto-generated method stub
		return loanEmpQuartersMapper.selectQuartersIdByEmpId(empId);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryAllUnion()
	 */
	 
	@Override
	public List<LoanUnionCompany> queryAllUnion() {
		
		// TODO Auto-generated method stub
		return loanUnionCompanyMapper.queryAllUnion();
	}
	
	@Override
	public List<LoanEmp> queryEmpListByEmp(LoanEmp loanEmp) {
		return loanEmpMapper.queryEmpListByEmp(loanEmp);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryGroupIdByDept(int)
	 */
	 
	@Override
	public List<String> queryGroupIdByDept(int lgDeptId) {
		
		// TODO Auto-generated method stub
		return loanGroupMapper.queryGroupIdByDept(lgDeptId);
	}

	
	/**
	 * queryCustManagerByDeptId:查询部门下所有客户经理信息
	 */
	public List<LoanEmp> queryCustManagerByDeptId(Map<String, Object> paramsMap){
		return loanEmpMapper.queryCustManagerByDeptId(paramsMap);
	}
	/**
	 * queryCustManagerByDeptId:查询部门下所有客户经理信息
	 */
	public LoanEmp selectLoanEmpByPrimaryKey(Integer leEmpId){
		return loanEmpMapper.selectByPrimaryKey(leEmpId);
	}

	@Override
	public List<LoanEmp> queryEmpByMobile(String mobile) {
		List<LoanEmp> queryEmpByMobile = loanEmpMapper.queryEmpByMobile(mobile);
		return queryEmpByMobile;
	}

	@Override
	public List<LoanDept> queryOneLevelDeptByCompanyId(String ldCompany) {
		return loanEmpMapper.queryOneLevelDeptByCompanyId(ldCompany);
	}

	@Override
	public List<LoanDept> querySubDeptByDeptId(String ldDeptId) {
		return loanEmpMapper.querySubDeptByDeptId(ldDeptId);
	}
	/**
	 * TODO 根据公司查询全部部门
	 * @see com.lhhs.loan.service.SystemManagerService#queryDeptByCompanyId(java.lang.String)
	 */
	@Override
	public List<LoanDept> queryDeptByCompanyId(String ldCompany) {
		return loanDeptMapper.queryDeptByCompanyId(ldCompany);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.SystemManagerService#queryDeptName()
	 */
	 
	@Override
	public List<LoanDept> queryDeptName(Map<String, Object> params) {
		
		// TODO Auto-generated method stub
		return loanDeptMapper.queryDeptName(params);
	}

	@Override
	public List<LoanAuthgroup> queryAuthGroupList(Map<String, Object> map) {
		return loanAuthgroupMapper.queryAuthGroupList(map);
	}

	@Override
	public List<Integer> selectAuthgroupIdsByUserId(Integer userId) {
		return loanAuthgroupUserMapper.selectAuthgroupIdsByUserId(userId);
	}

	@Override
	public List<LoanAuthority> findAuthListByEmpId(Integer leEmpId) {
		List<LoanAuthority> authList = loanAuthorityMapper.findAuthListByEmpId(1, leEmpId);
		return authList;
	}
	
	@Override
	public List<LoanAuthority> findAuthListByEmpList(List<LoanAuthority> authList) {
		return treeMenuList(authList, 0);
	}
	
	@Override
	public List<LoanAuthgroup> getLoanAuthgroupList(Map<String, Object> params, Page page) {
		List<LoanAuthgroup> loanAuthgroupList = loanAuthgroupMapper.getAuthorityGroups(params);
		Integer loanAuthgroupCount = loanAuthgroupMapper.getAuthorityCount(params);
		if (page != null) {
			page.setTotalCount(loanAuthgroupCount);
			page.setResultList(loanAuthgroupList);
		}
		return loanAuthgroupList;
	}

	@Override
	public List<String> getAuthgroupNames() {
		return loanAuthgroupMapper.getAuthgroupNames();	
	}

	@Override
	public List<LoanUnionCompany> getAllCompanyTree(Map<String,Object> param) {
		List<LoanUnionCompany> allCompanyList = loanUnionCompanyMapper.getAllCompanyByUnionId(param);
		if(null!=allCompanyList && allCompanyList.size()>0){
			for (LoanUnionCompany loanUnionCompany : allCompanyList) {
				param.put("ldCompany", loanUnionCompany.getCompanyId());
				List<LoanDept> deptList = loanDeptMapper.queryDeptByUnionId(param);
				deptList = this.transLateDeptTree(deptList, 0);
				loanUnionCompany.setLoanDeptList(deptList);
			}
		}
		return allCompanyList;
	}
	
	
	 private List<LoanDept> transLateDeptTree(List<LoanDept> deptList, int parentId) {
			List<LoanDept> returnList = new ArrayList<LoanDept>();
			if(null != deptList && deptList.size()>0){
				for (LoanDept dept : deptList) {
					int ldDeptId = dept.getLdDeptId();//获取部门的id
					int laFatherNode = dept.getParentId();//获取父id
					if(parentId == laFatherNode ){
						List<LoanDept> lhhsAuthorityFruit = transLateDeptTree(deptList, ldDeptId);
						Collections.sort(lhhsAuthorityFruit, new Comparator<LoanDept>() {
							@Override
							public int compare(LoanDept o1, LoanDept o2) {
								return o1.getSort().compareTo(o2.getSort());
							}
						});
						dept.setSubDeptList(lhhsAuthorityFruit);
						returnList.add(dept);
					}
				}
			}
			return returnList ;
	   	}

	@Override
	public List<LoanAuthority> getAllLastAuthyList() {
		 return treeMenuList(loanAuthorityMapper.getMenuAuthList(),0);
	}

	@Override
	@Transactional
	public Map<String, Object> authorityGroupInsert(List<LoanAuthorityGroup> loanAuthorityGroupList,LoanAuthgroup loanAuthgroupVo) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
	    param.put("groupName", loanAuthgroupVo.getGroupName());
	    List<LoanAuthgroup> queryAuthGroupList = loanAuthgroupMapper.queryAuthGroupList(param);
	    if(queryAuthGroupList!=null && queryAuthGroupList.size()>0){
		   returnMap.put(SystemConst.retCode, SystemConst.FAIL);
		   returnMap.put(SystemConst.retMsg, "该名称已经存在");
		   return returnMap;
	    }
		loanAuthgroupVo.setStatus("03");//状态（03：有效，99无效）
		loanAuthgroupVo.setType("define");
		loanAuthgroupVo.setCreatTime(new Date());
		//新增数据组表
		Integer count1 = loanAuthgroupMapper.insertSelective(loanAuthgroupVo);
		for (LoanAuthorityGroup loanAuthorityGroup : loanAuthorityGroupList) {
			loanAuthorityGroup.setGroupId(loanAuthgroupVo.getGroupId());
			loanAuthorityGroup.setStatus("03");
			loanAuthorityGroup.setCreatTime(new Date());
		}
		//新增数据组权限表
		int count = loanAuthorityGroupMapper.insertList(loanAuthorityGroupList);
		if(count1>0){
		  returnMap.put(SystemConst.retCode, SystemConst.SUCCESS);
		  returnMap.put(SystemConst.retMsg, "保存成功");
		  return returnMap;
		}else{
		  returnMap.put(SystemConst.retCode, SystemConst.FAIL);
		  returnMap.put(SystemConst.retMsg, "保存失败");
		  return returnMap;
		}
	}

	@Override
	public LoanAuthgroup toAuthorityGroupDetail(Integer groupId) {
		
		return loanAuthgroupMapper.authorityGroupDetail(groupId);
	}

	@Override
	@Transactional
	public Map<String, Object> authorityGroupUpdate(List<LoanAuthorityGroup> loanAuthorityGroupList,LoanAuthgroup loanAuthgroupVo) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
	    LoanAuthgroup vo = loanAuthgroupMapper.selectByPrimaryKey(loanAuthgroupVo.getGroupId());
	    if(!vo.getGroupName().equals(loanAuthgroupVo.getGroupName())){
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	param.put("groupName", loanAuthgroupVo.getGroupName());
	    	List<LoanAuthgroup> queryAuthGroupList = loanAuthgroupMapper.queryAuthGroupList(param);
	    	if(queryAuthGroupList!=null && queryAuthGroupList.size()>0){
	    		returnMap.put(SystemConst.retCode, SystemConst.FAIL);
	    		returnMap.put(SystemConst.retMsg, "该名称已经存在");
	    		return returnMap;
	    	}
	    }
	    
		loanAuthgroupVo.setStatus("03");//状态（03：有效，99无效）
		loanAuthgroupVo.setType("define");
		//修改数据组表
		int count = loanAuthgroupMapper.updateByPrimaryKeySelective(loanAuthgroupVo);
		//删除数据权限组
		int count2 = loanAuthorityGroupMapper.delByGroupId(loanAuthgroupVo.getGroupId());
		//新增数据权限组
		for (LoanAuthorityGroup loanAuthorityGroup : loanAuthorityGroupList) {
			loanAuthorityGroup.setGroupId(loanAuthgroupVo.getGroupId());
			loanAuthorityGroup.setStatus("03");
			loanAuthorityGroup.setCreatTime(new Date());
		}
		int count3 = loanAuthorityGroupMapper.insertList(loanAuthorityGroupList);
		if(count>0){
		  returnMap.put(SystemConst.retCode, SystemConst.SUCCESS);
		  returnMap.put(SystemConst.retMsg, "修改成功");
		  return returnMap;
		}else{
		  returnMap.put(SystemConst.retCode, SystemConst.FAIL);
		  returnMap.put(SystemConst.retMsg, "修改失败");
		  return returnMap;
		}
		
	}

	@Override
	public boolean authorityGroupValidate(LoanAuthgroup loanAuthgroup) {
		if(null !=loanAuthgroup.getGroupId()){//修改
			LoanAuthgroup vo = loanAuthgroupMapper.selectByPrimaryKey(loanAuthgroup.getGroupId());
			if(!vo.getGroupName().equals(loanAuthgroup.getGroupName())){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("groupName", loanAuthgroup.getGroupName());
				List<LoanAuthgroup> queryAuthGroupList = loanAuthgroupMapper.queryAuthGroupList(param);
				if(queryAuthGroupList!=null && queryAuthGroupList.size()>0){
					return false;
				}
			}
			
		}else{//新增
			Map<String,Object> param = new HashMap<String,Object>();
		    param.put("groupName", loanAuthgroup.getGroupName());
		    List<LoanAuthgroup> queryAuthGroupList = loanAuthgroupMapper.queryAuthGroupList(param);
		    if(queryAuthGroupList!=null && queryAuthGroupList.size()>0){
			  return false;
		    }
		}
		return true;
	}

	@Override
	public List<LoanAuthorityGroup> getSelectAutGupByGupId(Integer groupId) {
		return loanAuthorityGroupMapper.getSelectAutGupByGupId(groupId);
	}

	@Override
	public List<LoanUnionCompany> getCompanyByCity(LoanUnionCompany vo) {
		return loanUnionCompanyMapper.queryList(vo);
	}

	@Override
	public List<LoanParentProduct> queryProductListByType(String productType) {
		return loanParentProductMapper.queryProductListByType(productType);
	}

	@Override
	public List<User> queryCustManagerBySaas(Map<String, Object> paramsMap) {
		return loanEmpMapper.queryCustManagerBySaas(paramsMap);
	}
}