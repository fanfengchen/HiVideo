package com.ebeijia.videocore.cache.service;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Memcachedservice implements IMemcachedService {

	@Autowired
	private MemcachedClient memcachedClient;

	public boolean set(String key, int exp, Object vlaue) throws Exception {
		return memcachedClient.set(key, exp, vlaue);
	}

	public <T> T get(String key) throws Exception {
		return memcachedClient.get(key);
	}

	public boolean delete(String key) throws Exception {
		return memcachedClient.delete(key);
	}

	public void flushAll() throws Exception {
		memcachedClient.flushAll();
	}

}
