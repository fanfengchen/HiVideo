package com.ebeijia.robot.core.dao;

import com.ebeijia.robot.core.exception.DaoException;

public interface DAO {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object save(String str, Object obj) throws DaoException;
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object update(String str, Object obj) throws DaoException;
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object delete(String str, Object obj) throws DaoException;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object findForObject(String str, Object obj) throws DaoException;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object findForList(String str, Object obj) throws DaoException;
	
	/**
	 * 查找对象封装成Map
	 * @param s
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws DaoException;
	
}
