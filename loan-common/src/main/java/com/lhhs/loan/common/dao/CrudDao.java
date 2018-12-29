package com.lhhs.loan.common.dao;

import java.util.List;

/**
 * DAO支持类实现
 * @author dongfei
 * @version 2014-05-16
 * @param <T>
 */
public interface CrudDao<T> extends BaseDao {


	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> queryList(T entity);

	/**
	 * 获得查询结果总数
	 * @param entity
	 * @return
	 */
	public int queryCount(T entity);
	/**
	 * 插入数据 全部字段
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	/**
	 * 
	 * 插入数据 有值的字段
	 *
	 */
    int insertSelective(T entity);
	/**
	 * 根据主键更新数据实体中有值的字段
	 * @param entity
	 * @return
	 */
	public int updateByPrimaryKeySelective(T entity);
	/**
	 * 根据主键更新数据
	 * @param entity
	 * @return
	 */
    int updateByPrimaryKey(T entity);
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * 删除数据 按条件删除
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

	
}