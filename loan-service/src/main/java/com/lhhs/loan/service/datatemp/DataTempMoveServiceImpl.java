package com.lhhs.loan.service.datatemp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lhhs.loan.dao.DataTempMoveMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.LoanUnionCompanyMapper;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.SystemManagerService;

/**
 * 部门、组数据迁移
 */
@Service
public class DataTempMoveServiceImpl implements DataTempMoveService {
	private static final Logger logger = Logger.getLogger(DataTempMoveServiceImpl.class);
	@Autowired
	private LoanUnionCompanyMapper loanUnionCompanyMapper;
	@Autowired
	private DataTempMoveMapper dataTempMoveMapper;
	@Autowired
	private LoanDeptMapper loanDeptMapper;
	@Autowired
	private SystemManagerService systemManagerService;
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void dataTempMoveMain() {
		//把各表涉及到部门、组复制到备用字段中
		dataMoveBack();
		//部门和组数据迁移
		dataMoveDepAndGroup();
		//员工数据迁移
		dataMoveEmp();
		//意向客户数据迁移 crm_intent_loan_user crm_intent_loan_user_record
		dataMoveCrmUser();
		//借款人数据迁移 loan_customer_info
		dataMoveCustomerInfo();
		//投资人客户信息 loan_invest_customer_info
		dataMoveInvestCustomerInfo();
		//订单数据迁移 loan_order_info
		dataMoveOrderInfo();
		//审批意见 act_comment
		dataMoveActComment();
		//迁移部门和组领导、职能部门权限
		dataTempMoveMapper.insertAuthgroupUserByEmp();
		//迁移放款主表
		dataMoveLoanTransMain();
		//迁移管理多个分公司(线上无数据)
		
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveDepAndGroup(){
		Map<String, LoanDept> map = new HashMap<String, LoanDept>();
		//删除原部门数据
		dataTempMoveMapper.deleteDeptBack();
		LoanUnionCompany query_company=new LoanUnionCompany();
		query_company.setParentCompanyId("10001");
		List<LoanUnionCompany> list_Company=loanUnionCompanyMapper.queryList(query_company);
		List<LoanDept> list_Dept=dataTempMoveMapper.queryDeptList();
		List<LoanDept> list_Group=dataTempMoveMapper.queryGroupListFromEmp();
		StringBuffer stringBuffer = new StringBuffer();
		//部门数据迁移
		for(LoanUnionCompany temp_com:list_Company){
			for(int i=0;i<list_Dept.size();i++){
				LoanDept temp_det=list_Dept.get(i);
				temp_det.setLdUnion(temp_com.getUnionId());
				temp_det.setLdCompany(temp_com.getCompanyId());
				temp_det.setParentId(0);
				temp_det.setParentIds("0");
				//一级部门（组）
				temp_det.setLayer(1);
				//temp_det.setField1(temp_det.getLdDeptId().toString());
				temp_det.setLdTime(new Date());
				temp_det.setLdDeptId(CommonUtils.getAutoIncrement("loan_dept").intValue());
				systemManagerService.insertDept(temp_det);
				//SET 集团、公司、旧部门MAP
				//map.put(temp_com.getUnionId()+"_"+temp_com.getCompanyId()+"_"+temp_det.getField1(), temp_det);
				//stringBuffer.append("depKey:"+temp_com.getUnionId()+"_"+temp_com.getCompanyId()+"_"+temp_det.getField1()+"+++DEP+++"+temp_det.getLdUnion()+"_"+temp_det.getLdCompany()+"_"+temp_det.getLdDeptId()+"\n");
			}
		}
		logger.error(stringBuffer.toString());
		//组数据迁移
		for(int i=0;i<list_Group.size();i++){
			LoanDept temp_Group=list_Group.get(i);
			Integer parentId=temp_Group.getParentId();
			String depKey=temp_Group.getLdUnion()+"_"+temp_Group.getLdCompany()+"_"+parentId;
			if(temp_Group.getLdDeptId().intValue()==1100003&&temp_Group.getLdCompany().equals("10006")){
				logger.error("updateEmpByDep:"+depKey);
			}
			LoanDept dep_p =new LoanDept ();
			dep_p.setField1(parentId.toString());
			dep_p.setLdUnion(temp_Group.getLdUnion());
			dep_p.setLdCompany(temp_Group.getLdCompany());
			dep_p=dataTempMoveMapper.getDept(dep_p);
			//LoanDept dep_p=map.get(depKey);
			if(dep_p==null)continue;
			temp_Group.setParentId(dep_p.getLdDeptId());
			temp_Group.setParentIds("0,"+dep_p.getLdDeptId().toString());
			//二级部门（组）
			temp_Group.setLayer(2);
			temp_Group.setLdDeptId(CommonUtils.getAutoIncrement("loan_dept").intValue());
			if(StringUtils.isEmpty(temp_Group.getLdName())){
				temp_Group.setLdName(temp_Group.getLdDeptId().toString());
			}
			temp_Group.setLdTime(new Date());
			systemManagerService.insertDept(temp_Group);
		}
	}
	
	/**
	 * 
	 * 员工数据迁移
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveEmp(){
		//员工数据迁移
		int flag=dataTempMoveMapper.updateEmpByDep();
		logger.error("updateEmpByDep:"+flag);
		flag=dataTempMoveMapper.updateEmpByGroup();
		logger.error("updateEmpByGroup:"+flag);
		flag=dataTempMoveMapper.clearEmpGroup();
		//清除无员工的部门
		List<String> listdep= dataTempMoveMapper.queryDeptNotEmp();
		LoanDept record =new LoanDept ();
		record.setOaOrgIdList(listdep);
		flag=dataTempMoveMapper.deleteDeptBackIsEmp(record);
		logger.error("clearEmpGroup:"+flag);
	}
	
	/**
	 * 
	 * 意向客户数据迁移 crm_intent_loan_user crm_intent_loan_user_record
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveCrmUser(){
		//意向客户数据迁移 crm_intent_loan_user crm_intent_loan_user_record
		//迁移创建者的部门，组；迁移最终的部门或者组
		int flag=dataTempMoveMapper.updateCrmUserByCreaterDep();
		logger.error("updateCrmUserByCreaterDep:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByCreaterGroup();
		logger.error("updateCrmUserByCreaterGroup:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByCreaterOrg();
		logger.error("updateCrmUserByCreaterOrg:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByCreaterOrgByGroup();
		logger.error("updateCrmUserByCreaterOrgByGroup:"+flag);
		
		//迁移归属部门或者组；迁移最终的部门或者组
		flag=dataTempMoveMapper.updateCrmUserByAppointDep();
		logger.error("updateCrmUserByAppointDep:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByAppointGroup();
		logger.error("updateCrmUserByAppointGroup:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByAppointOrg();
		logger.error("updateCrmUserByAppointOrg:"+flag);
		flag=dataTempMoveMapper.updateCrmUserByAppointOrgByGroup();
		logger.error("updateCrmUserByAppointOrgByGroup:"+flag);
		//迁移创建者的部门，组；迁移最终的部门或者组
		flag=dataTempMoveMapper.updateCrmUserRecordByCreaterDep();
		logger.error("updateCrmUserRecordByCreaterDep:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByCreaterGroup();
		logger.error("updateCrmUserRecordByCreaterGroup:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByCreaterOrg();
		logger.error("updateCrmUserRecordByCreaterOrg:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByCreaterOrgByGroup();
		logger.error("updateCrmUserRecordByCreaterOrgByGroup:"+flag);
		//迁移归属部门或者组；迁移最终的部门或者组
		flag=dataTempMoveMapper.updateCrmUserRecordByAppointDep();
		logger.error("updateCrmUserRecordByAppointDep:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByAppointGroup();
		logger.error("updateCrmUserRecordByAppointGroup:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByAppointOrg();
		logger.error("updateCrmUserRecordByAppointOrg:"+flag);
		flag=dataTempMoveMapper.updateCrmUserRecordByAppointOrgByGroup();
		logger.error("updateCrmUserRecordByAppointOrgByGroup:"+flag);
	}
	
	/**
	 * 
	 * 借款人数据迁移 loan_customer_info
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveCustomerInfo(){
		int flag=dataTempMoveMapper.updateCustomerInfoDep();
		logger.error("updateCustomerInfoDep:"+flag);
	}
	/**
	 * 
	 * 投资人客户信息 loan_invest_customer_info
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveInvestCustomerInfo(){
		int flag=dataTempMoveMapper.updateInvestCustomerInfoDep();
		logger.error("updateInvestCustomerInfoDep:"+flag);
		flag=dataTempMoveMapper.updateInvestCustomerInfoGroup();
		logger.error("updateInvestCustomerInfoGroup:"+flag);
	}
	/**
	 * 
	 * 订单数据迁移 loan_order_info
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveOrderInfo(){
		int flag=dataTempMoveMapper.updateOrderInfoDep();
		logger.error("updateOrderInfoDep:"+flag);
				
	}
	/**
	 * 
	 * 审批意见 act_comment
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveActComment(){
		int flag=dataTempMoveMapper.updateActCommentDep();
		logger.error("updateActCommentDep:"+flag);	
	}
	/**
	 * 
	 * 更新放款主表 Loan_Trans_Main
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveLoanTransMain(){
		int flag=dataTempMoveMapper.updateLoanTransMain();
		logger.error("updateLoanTransMain:"+flag);	
	}
	
	/**
	 * 
	 * 备份数据
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	private void dataMoveBack(){
		//清除临时表数据
		//dataTempMoveMapper.deleteDeptTemp();
		dataTempMoveMapper.insertDeptBack();
		//员工数据迁移
		dataTempMoveMapper.updateEmpBack();
		//意向客户数据迁移 crm_intent_loan_user crm_intent_loan_user_record
		dataTempMoveMapper.updateCrmUserBack();
		dataTempMoveMapper.updateCrmUserRecordBack();
		//借款人数据
		dataTempMoveMapper.updateCustomerInfoBack();
		//投资人客户信息
		dataTempMoveMapper.updateInvestCustomerInfoBack();
		//订单数据
		dataTempMoveMapper.updateOrderInfoBack();
		//审批意见
		dataTempMoveMapper.updateActCommentBack();
	}

}

