package com.lhhs.loan.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.common.shared.page.BaseObject;
import com.lhhs.loan.common.shared.page.Page;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */

public abstract class CrudService<D extends CrudDao<T>, T extends BaseObject>  {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		Page page=new Page();
		page.setPageIndex(1);
		entity.setPage(page);
		List<T> list=queryList(entity);
		if(list==null || list.size() == 0)return null;
		return list.get(0);
	}
	
	/**
	 * 查询列表数据不分页
	 * @param entity
	 * @return
	 */
	public List<T> queryList(T entity) {
		Page page =entity.getPage();
		if(page!=null){
			page.setPageStartIndex(0);
			page.setStartNum(1);
			page.setPageSize(Integer.MAX_VALUE);
			entity.setPage(page);
		}
		return dao.queryList(entity);
	}
	
	/**
	 * 查询列表数据 ，可分页
	 * @param entity
	 * @return
	 */
	public Page queryListPage(T entity) {
		Page result =entity.getPage();
		if(entity.getPage()==null)result =new Page();
		result.setResultList(dao.queryList(entity));
		result.setTotalCount(queryCount(entity));
		return result;
	}

	/**
	 * 保存数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int save(T entity) {
		if(entity==null)return -1;
		if(entity.getCreateTime()==null)entity.setCreateTime(new Date());
		if(entity.getLastModifyTime()==null)entity.setLastModifyTime(new Date());
		if(entity.getStatus()==null ||"".equals(entity.getStatus()))entity.setStatus("03");
		return dao.insertSelective(entity);

	}
	/**
	 * 保存数据（更新或者插入）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int saveOrUpdate(T entity) {
		if(entity==null)return -1;
		if(entity.getLastModifyTime()==null)entity.setLastModifyTime(new Date());
		if (entity.get_id()!=null&&!entity.get_id().equals("")){
			return dao.updateByPrimaryKeySelective(entity);
		}else{
			if(entity.getCreateTime()==null)entity.setCreateTime(new Date());
			return dao.insertSelective(entity);
		}
	}
	/**
	 * 更新数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int update(T entity) {
		if(entity==null)return -1;
		if(entity.getLastModifyTime()==null)entity.setLastModifyTime(new Date());
		return dao.updateByPrimaryKeySelective(entity);
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int delete(T entity) {
		return dao.delete(entity);
	}
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int deleteByPrimaryKey(T entity) {
		return dao.delete(entity);
	}
	/**
	 * 获取记录总数
	 * @param entity
	 */
	public int queryCount(T entity) {
		return dao.queryCount(entity);
	}

}
