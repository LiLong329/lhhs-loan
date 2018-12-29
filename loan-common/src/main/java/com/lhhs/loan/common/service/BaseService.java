package com.lhhs.loan.common.service;

import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;
import com.lhhs.loan.common.shared.page.Page;
/**
 * Service 接口基类
 * ClassName: BaseService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年7月28日 上午11:13:05 <br/>
 *
 * @author dongfei
 * @version @param <T>
 * @since JDK 1.8
 */
public interface BaseService <T extends BaseObject> {
		
		/**
		 * 获取单条数据
		 * @param id
		 * @return
		 */
		public T get(String id) ;

		
		/**
		 * 获取单条数据
		 * @param entity
		 * @return
		 */
		public T get(T entity) ;
		
		/**
		 * 查询数据列表
		 * @param entity
		 * @return
		 */
		public List queryList(T entity);
		
		/**
		 * 查询分页数据
		 * @param entity
		 * @return
		 */
		public Page queryListPage(T entity);
	
		/**
		 * 保存数据（插入或更新）
		 * @param entity
		 */
		public int save(T entity) ;
		
		/**
		 * 更新数据
		 * @param entity
		 */
		public int update(T entity) ;
		
		/**
		 * 删除数据
		 * @param entity
		 */
		public int delete(T entity) ;
		/**
		 * 查询总记录数
		 * @param entity
		 */
		public int queryCount(T entity) ;
}
