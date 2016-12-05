package com.ebeijia.videocore.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ebeijia.videocore.exception.DaoException;

@Repository("daoSupport")
public class DaoSupport implements DAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 保存对象
	 * @param str :mapperId
	 * @param obj :预计需要保存的obj对象
	 * @return
	 * @throws DaoException
	 */
	public Object save(String str, Object obj) throws DaoException {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoDaoException
	 */
	public Object batchSave(String str, List objs )throws DaoException{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object update(String str, Object obj) throws DaoException {
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public void batchUpdate(String str, List objs) throws DaoException {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object batchDelete(String str, List objs )throws DaoException{
		return sqlSessionTemplate.delete(str, objs);
	}
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object delete(String str, Object obj) throws DaoException {
		return sqlSessionTemplate.delete(str, obj);
	}
	 
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object findForObject(String str, Object obj) throws DaoException {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	public Object findForList(String str, Object obj) throws DaoException {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value) throws DaoException {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}
	
}


