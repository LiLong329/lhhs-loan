package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanDictionary;

public interface LoanDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanDictionary record);

    int insertSelective(LoanDictionary record);

    LoanDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanDictionary record);

    int updateByPrimaryKey(LoanDictionary record);
    
    List<LoanDictionary>  queryByCategory(String category);
    
	List<LoanDictionary> getCustPropertyByTypeId(Map<String, Object> params);
	
	/**
	 * 根据分类名称查询客户性质的名称
	 */
	List<LoanDictionary> selectCustNatureByCategory();
	
	List<LoanDictionary> queryAllcustomerNature();
}