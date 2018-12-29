/**
 * Project Name:loan-service
 * File Name:AccountInOutServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月1日下午5:41:39
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-service
 * File Name:AccountInOutServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月1日下午5:41:39
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.dao.ActCommentSelfMapper;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanBankMapper;
import com.lhhs.loan.dao.LoanDictionaryMapper;
import com.lhhs.loan.dao.LoanPayCertificatesInfoMapper;
import com.lhhs.loan.dao.domain.ActComment;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanPayCertificatesInfo;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.service.AccountInOutService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:AccountInOutServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月1日 下午5:41:39 <br/>
 * @author   zhanghui
 * @version
 * @since    JDK 1.8
 * @see
 */
/**
 * ClassName: AccountInOutServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年8月1日 下午5:41:39 <br/>
 *
 * @author zhanghui
 * @version
 * @since JDK 1.8
 */
@Service
public class AccountInOutServiceImpl implements AccountInOutService {

	private static final Logger logger = LoggerFactory.getLogger(AccountInOutServiceImpl.class);
	@Autowired
	private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
	@Autowired
	private LoanDictionaryMapper loanDictionaryMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanBankMapper loanBankMapper;
	@Autowired
	private LoanPayCertificatesInfoMapper loanPayCertificatesInfoMapper;
	@Autowired
	private ActCommentSelfMapper actCommentMapper;
	@Autowired
	private AccountTransactionService accountTransactionService;

	/**
	 * TODO 查询充值、提现、审核等记录
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryDepositRecord(java.util.Map,
	 *      com.lhhs.loan.common.shared.page.Page)
	 */
	@Override
	public List<Map<String, Object>> queryTransRecord(Map<String, Object> params, Page page) {

		List<Map<String, Object>> recordList = loanAccountInOutInfoMapper.queryTransRecord(params);
		Integer count = loanAccountInOutInfoMapper.queryTransRecordTotal(params);
		if(page!=null){
			page.setResultList(recordList);
			page.setTotalCount(count);
		}
		return recordList;
	}

	/**
	 * TODO 保存充值、提现申请
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#insertTransApply(com.lhhs.loan.dao.domain.LoanAccountInOutInfo)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertTransApply(LoanAccountInOutInfo loanAccountInOutInfo, String[] imageUrl)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (loanAccountInOutInfo == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "申请失败!");
			return result;
		}
		String transType = loanAccountInOutInfo.getTransType();
		String bankId = loanAccountInOutInfo.getBankId();
		String acountId = loanAccountInOutInfo.getAccountId();
		if (StringUtils.isEmpty(transType) || StringUtils.isEmpty(bankId)) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "申请失败!");
			return result;
		}
		if (StringUtils.isEmpty(acountId)) {
			Map<String, Object> accountInfo = getAccountsByMobileOrOwnerId(loanAccountInOutInfo.getMobile(),
					loanAccountInOutInfo.getCustomerType(), null);
			if (accountInfo == null || SystemConst.FAIL.equals(accountInfo.get(SystemConst.retCode))
					|| StringUtils.isEmpty((String) accountInfo.get("accountId"))) {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "客户信息不存在!");
				return result;
			}
			loanAccountInOutInfo.setAccountId((String) accountInfo.get("accountId"));
			loanAccountInOutInfo.setCustomerId((String) accountInfo.get("owner_id"));
		}

//		// 随机生成32位交易单号(充值：1005 提现：1006)
//		String transNo = "";
//		if (transType.equals(SystemConst.TransType.TYPEID1005)) {
//			transNo = SystemConst.TransType.TYPEID1005 + GetUniqueNoUtil.getOrderNo();
//		} else {
//			transNo = SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo();
//		}
		String mobileOrId = loanAccountInOutInfo.getMobile();
		if (!isMobile(mobileOrId)) {
			LoanAccountInfo  account =loanAccountInfoMapper.selectByOwnerId(mobileOrId);
			if(account!=null){
				loanAccountInOutInfo.setMobile(account.getMobile());
			}else{
				loanAccountInOutInfo.setMobile(null);
			}
			loanAccountInOutInfo.setCustomerId(mobileOrId);
		}
		LoanAccountCard card = loanAccountCardMapper.selectByPrimaryKey(bankId);
		if (card != null) {
			loanAccountInOutInfo.setCoAccountId(card.getAccountId());
		}
		loanAccountInOutInfo.setTransNo(loanAccountInOutInfo.getTransNo());
		loanAccountInOutInfo.setStatus(SystemConst.Status.STATUS93);
		loanAccountInOutInfo.setLastModifyTime(new Date());
		int count=0;
		if(transType.equals(SystemConst.TransType.TYPEID1005)){//充值：1005
			String transNo = SystemConst.TransType.TYPEID1005 + GetUniqueNoUtil.getOrderNo();
			loanAccountInOutInfo.setTransNo(transNo);
			loanAccountInOutInfo.setCreateTime(new Date());
			count = loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
		}else{
			count = loanAccountInOutInfoMapper.updateByPrimaryKeySelective(loanAccountInOutInfo);
		}
		// 保存凭证图片
		this.saveImage(imageUrl, loanAccountInOutInfo.getTransNo());
		if (count == 0) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "申请失败!");
			return result;
		}
		// 提现需调用冻结接口
		if (transType.equals(SystemConst.TransType.TYPEID1006)) {
			LoanAccountsTrans loanAccountsTrans = new LoanAccountsTrans();
			loanAccountsTrans.setAmount(loanAccountInOutInfo.getAmount());
			loanAccountsTrans.setAccountId(loanAccountInOutInfo.getAccountId());
			loanAccountsTrans.setUnionId(loanAccountInOutInfo.getUnionId());
			loanAccountsTrans.setCompanyId(loanAccountInOutInfo.getCompanyId());
			loanAccountsTrans.setTransType(SystemConst.TransType.TYPEID1011);
			loanAccountsTrans.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_FREEZEWITHDRAWALS);
			loanAccountsTrans.setOrderNo(loanAccountInOutInfo.getTransNo());
			loanAccountsTrans.setCreateTime(new Date());
			String retCode = accountTransactionService.accountFreezeTrans(loanAccountsTrans);
			if ("01".equals(retCode)) {
				throw new Exception();

			}
		}
		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		result.put(SystemConst.retMsg, "申请成功!");
		return result;
	}

	/**
	 * saveImage:(保存凭证图片). <br/>
	 * 
	 * @author zhanghui
	 * @param imageUrl
	 * @param transNo
	 * @since JDK 1.8
	 */
	public void saveImage(String[] imageUrl, String transNo) {
		if (imageUrl != null) {
			LoanPayCertificatesInfo loanPayCertificatesInfo = new LoanPayCertificatesInfo();
			for (String url : imageUrl) {
				loanPayCertificatesInfo.setTransNo(transNo);
				loanPayCertificatesInfo.setCertificatesUrl(url);
				loanPayCertificatesInfo.setStatus(SystemConst.Status.STATUS03);
				loanPayCertificatesInfoMapper.insertSelective(loanPayCertificatesInfo);
			}

		}
	}

	/**
	 * TODO 查询客户类型
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryCustomerTypeList()
	 */
	@Override
	public List<LoanDictionary> queryCustomerTypeList() {

		List<LoanDictionary> list = loanDictionaryMapper.queryByCategory("customer_type");
		return list;
	}

	/**
	 * TODO 查询客户性质
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryCustomerNatureList()
	 */
	@Override
	public List<LoanDictionary> queryCustomerNatureList() {
		List<LoanDictionary> list = loanDictionaryMapper.queryAllcustomerNature();
		return list;
	}

	/**
	 * TODO 根据手机号或机构ID查询账户信息
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#getAccountsByMobileOrOwnerId(java.util.Map)
	 */
	@Override
	public Map<String, Object> getAccountsByMobileOrOwnerId(String mobileOrOwnerId, String customerType,
			String transType) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(customerType)) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请选择客户类型！");
			return result;
		}
		if (StringUtils.isEmpty(mobileOrOwnerId)) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请输入手机号或机构ID！");
			return result;
		}
//		// 判断手机或机构ID
//		if (isMobile(mobileOrOwnerId)) {
//			params.put("mobile", mobileOrOwnerId);
//		} else {
//			params.put("ownerId", mobileOrOwnerId);
//		}
		//判断客户类型 机构：30，个人：10
		if("10".equals(customerType)){
			params.put("mobile", mobileOrOwnerId);
		}else{
			params.put("ownerId", mobileOrOwnerId);
		}
		params.put("customerType", customerType);
		Map<String, Object> info = loanAccountInfoMapper.getAccountsByMobileOrOwnerId(params);
		if (info != null) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("ownerName", info.get("owner_name"));
			result.put("certificateNo", info.get("certificate_no"));
			result.put("accountId", info.get("account_id"));
			result.put("customerId", info.get("owner_id"));
			result.put("bankName", info.get("bank_name"));
			result.put("bankCardNo", info.get("bank_card_no"));
			result.put("accountHolder", info.get("account_holder"));
			if (StringUtils.isNotEmpty(transType)&&SystemConst.AccountType.COMPANY.equals(customerType)) {
				if (transType.equals(SystemConst.TransType.TYPEID1005)) {
					params.put("kind", SystemConst.InOutCome.OUT);
				} else {
					params.put("kind", SystemConst.InOutCome.IN);
				}
				Map<String, Object> kardInfo = loanAccountInfoMapper.getAccountsByMobileOrOwnerId(params);
				if (kardInfo != null) {
					result.put("bankName", kardInfo.get("bank_name"));
					result.put("bankCardNo", kardInfo.get("bank_card_no"));
					result.put("accountHolder", kardInfo.get("account_holder"));

				}
			}
			
			return result;
		}
		result.put(SystemConst.retCode, SystemConst.FAIL);
		result.put(SystemConst.retMsg, "客户信息不存在");
		return result;
	}

	public static boolean isMobile(String str) {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(14[5,6,7,8,9])|(19[8,9])|(166))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * TODO 根据公司查询银行卡信息.
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryCardByCompany(java.lang.String)
	 */
	@Override
	public List<LoanAccountCard> queryCardByCompany(String companyId, String kind) {
		List<LoanAccountCard> list = loanAccountCardMapper.queryCardByCompany(companyId, kind);
		return list;
	}

	/**
	 * TODO 查询所有银行
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryAllBank()
	 */
	@Override
	public List<LoanBank> queryAllBank() {
		return loanBankMapper.queryAllBank();
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryDetailByTransNo(java.lang.String)
	 */

	@Override
	public Map<String, Object> queryDetailByTransNo(String transNo) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> detail = loanAccountInOutInfoMapper.queryDetailByTransNo(transNo);
		List<LoanPayCertificatesInfo> list = loanPayCertificatesInfoMapper.queryInfoByTransNo(transNo);
		result.put("detail", detail);
		result.put("certificatesInfoList", list);
		return result;
	}

	/**
	 * TODO 充值、提现审核
	 * 
	 * @throws Exception
	 * @see com.lhhs.loan.service.AccountInOutService#saveDepositApprove(com.lhhs.loan.dao.domain.ActComment)
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveTransApprove(ActComment actComment, String transType) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		if (actComment == null || StringUtils.isEmpty(transType)) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "审核失败！");
			return result;
		}
		String transNo = actComment.getProcInsId();
		Map<String, Object> detail = loanAccountInOutInfoMapper.queryDetailByTransNo(transNo);
		if (detail == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "审核失败！");
			return result;
		}
		LoanAccountsTrans loanAccountsTrans = new LoanAccountsTrans();
		loanAccountsTrans.setAmount((BigDecimal) detail.get("amount"));
		loanAccountsTrans.setAccountId((String) detail.get("account_id"));
		loanAccountsTrans.setUnionId((String)detail.get("union_id"));
		loanAccountsTrans.setCompanyId((String)detail.get("company_id"));
		loanAccountsTrans.setOrderNo(transNo);
		loanAccountsTrans.setCreateUser(actComment.getAssigneeName());
		loanAccountsTrans.setCreateTime(new Date());
		loanAccountsTrans.setTransType(transType);
		loanAccountsTrans.setTransRemark(actComment.getMsg());

		LoanAccountInOutInfo loanAccountInOutInfo = new LoanAccountInOutInfo();
		loanAccountInOutInfo.setTransNo(transNo);
		loanAccountInOutInfo.setStatus(SystemConst.Status.STATUS03);
		int flag = actCommentMapper.insertSelective(actComment);
		int count = loanAccountInOutInfoMapper.updateByPrimaryKeySelective(loanAccountInOutInfo);
		if (flag > 0 && count > 0 && SystemConst.Status.STATUS90.equals(actComment.getStatus())) {
			// 调用接口
			String retCode = accountTransactionService.accountInAndOutTrans(loanAccountsTrans);
			if (!"00".equals(retCode)) {
				throw new Exception();
			}
		}
		if (flag > 0 && count > 0 && SystemConst.Status.STATUS91.equals(actComment.getStatus())&&SystemConst.TransType.TYPEID1006.equals(transType)) {
			// 提现不通过调用解冻接口
			String retCode = accountTransactionService.accountFreezeOutTrans(loanAccountsTrans);
			if (!"00".equals(retCode)) {
				throw new Exception();
			}
		}

		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		result.put(SystemConst.retMsg, "审核成功！");
		return result;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see com.lhhs.loan.service.AccountInOutService#queryTransTotal(java.lang.String)
	 */
	@Override
	public Map<String, Object> queryTransTotal(Map<String, Object> params) {

		// TODO Auto-generated method stub
		return loanAccountInOutInfoMapper.queryTransTotal(params);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.AccountInOutService#convertProperties(java.util.List)
	 */
	 
	@Override
	public List<Map<String, Object>> convertProperties(List<Map<String, Object>> param) {
		
		if(param!=null&&param.size()>0){
			
			DecimalFormat  format = new DecimalFormat("0.00");
			for(Map<String, Object> obj :param){
				
				BigDecimal  amount =(BigDecimal)obj.get("amount");
				String  status = (String) obj.get("status");
				if(SystemConst.Status.STATUS90.equals(status)){
					obj.put("status", "成功");
					
				}else if(SystemConst.Status.STATUS91.equals(status)){
					obj.put("status", "失败");
				}
				obj.put("amount", format.format(amount));
				
			}
			
			return param;
		}
		
		return null;
	}

	@Override
	public Page queryDepositApplyList(LoanAccountInOutInfo entity) {
		Page page = entity.getPage();
		page.setResultList(loanAccountInOutInfoMapper.queryDepositApplyList(entity));
		page.setTotalCount(loanAccountInOutInfoMapper.queryDepositApplyCount(entity));
		return page;
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmount(LoanAccountInOutInfo entity) {
		return loanAccountInOutInfoMapper.queryTotalAmount(entity);
	}

}
