package com.ebeijia.robot.core.cache.service;

/**
 * memcached 服务接口
 * 
 */
public interface IMemcachedService {
	/**
	 * 根据 key 删除 memcached 中的 键值对
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	boolean delete(String key) throws Exception;

	/**
	 * 清空所有 memcached中 数据
	 * 
	 * @throws Exception
	 */
	void flushAll() throws Exception;

	/**
	 * 向memcached 中添加数据，如缓存中没有key，则添加；如缓存中已有 key ,则覆盖
	 * 
	 * @param key
	 * @param exp 过期时间 以分为单位
	 * @param vlaue 放入缓存的对象
	 * @return
	 * @throws Exception
	 */
	boolean set(String key, int exp, Object vlaue) throws Exception;

	/**
	 * 根据 key 获取 memcached 中 值
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public <T> T get(String key) throws Exception;

}
