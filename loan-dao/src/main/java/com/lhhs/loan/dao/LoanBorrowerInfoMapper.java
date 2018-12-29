package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanBorrowerInfo;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.vo.LoanBorrowerInfoVo;

public interface LoanBorrowerInfoMapper {
    int deleteByPrimaryKey(String custId);

    int insert(LoanBorrowerInfoWithBLOBs record);

    int insertSelective(LoanBorrowerInfoWithBLOBs record);
    
    LoanBorrowerInfoWithBLOBs selectByPrimaryKey(String custId);

    int updateByPrimaryKeySelective(LoanBorrowerInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LoanBorrowerInfoWithBLOBs record);
    
    /**
     * 通过借款人的 custId 更新借款信息表
     * @param record
     * @return
     */
    int insertBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs record);

    int updateByPrimaryKey(LoanBorrowerInfo record);

    /*************************** 星火项目--碰碰旺系统对接模块接口 START ***************************/
    /**
     * 通过借款人的 custId 查询当前借款人是否存在
     */
    int selectBorrowerInfoWithBLOBsCount(String custId);
    
    /**
     * 新增碰碰旺系统中的借款人信息到贷前系统
     */
    int insertBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs record);
    
    /**
     * 根据custId更新碰碰旺系统中的借款人信息到贷前系统
     */
    int updateBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs record);
    /*************************** 星火项目--碰碰旺系统对接模块接口 END ***************************/
    
    /*************************** 贷前系统 II 期  START ***************************/
    
    /**
     * selectBorrowerInfo:客户信息管理-借款人信息管理
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    List<LoanBorrowerInfoVo> selectBorrowerInfo(LoanBorrowerInfoVo entity);
    
    /**
     * selectBorrowerInfo:客户信息管理-借款人信息管理总记录数查询
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int selectBorrowerInfoCount(LoanBorrowerInfoVo entity);
    
    /**
     * selectBorrowerBasicInfo:根据customer_id查询当前客户的基本信息和抵押物信息之基本信息查询
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
    LoanBorrowerInfoVo selectBorrowerBasicInfo(String customerId);
    
    /**
     * updateBorrowerInfoWithBLOBs:通过客户基本信息的 LoanBorrowerInfoWithBLOBs 对象更新数据库表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int updateBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs entity);

    /**
     * 
     * insertSelectiveByborrower:理财人信息（银主）新增数据同时插入个人基本信息
     * @author Administrator
     * @param borrowerInfo
     * @return
     * @since JDK 1.8
     */
	int insertSelectiveByborrower(LoanBorrowerInfo borrowerInfo);
    
    /*************************** 贷前系统 II 期  END ***************************/
	
	LoanBorrowerInfoWithBLOBs selectByCustomerId(String customerId);
}