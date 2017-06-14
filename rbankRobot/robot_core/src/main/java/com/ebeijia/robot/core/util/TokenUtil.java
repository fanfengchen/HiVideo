package com.ebeijia.robot.core.util;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.ebeijia.robot.core.cache.service.IMemcachedService;
import com.ebeijia.robot.core.constant.MemcachedConstant;
import com.ebeijia.robot.core.exception.WebInfoException;

public class TokenUtil {
	private static int time = Integer.parseInt(StringUtils.isEmpty(PropertiesUtils
			.getProperties("memcache.token.timeout")) ? "10"
			: PropertiesUtils.getProperties("memcache.token.timeout").trim())*60;

	/**
	 * 获取 access_token中的数据
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map getToken(String token) {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			Map data = memcachedService.get(MemcachedConstant.ACCESS_TOKEN_PRE
					+ token);
			if (data != null) {
				memcachedService.set(
						MemcachedConstant.ACCESS_TOKEN_PRE + token, time, data);
			}
			return data;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(),e);
			return null;
		}
	}

	/**
	 * 清除缓存
	 * @param token
	 * @return
	 */
	public static void clearGetToken(String token) {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			
		  memcachedService.delete(MemcachedConstant.ACCESS_TOKEN_PRE+ token);			
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(),e);			
		}
	}
	/**
	 * 设置token
	 * 
	 * @param cid
	 * @param data
	 * @return
	 */
	public static String setToken(Map data) throws WebInfoException {
		String uuid = UUID.randomUUID().toString();
		String token = Md5.md5Upper(uuid);
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			memcachedService.set(MemcachedConstant.ACCESS_TOKEN_PRE + token,
					time, data);
			return token;
		} catch (Exception e) {
			throw new WebInfoException(e.getMessage(), e);
		}
	}

	/**
	 * 更新token中的数据
	 * 
	 * @param token
	 * @param data
	 */
	public static void UpdateTokenDate(String token, Map data) {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			memcachedService.set(MemcachedConstant.ACCESS_TOKEN_PRE + token,
					time, data);
		} catch (Exception e) {
			throw new WebInfoException(e.getMessage(), e);
		}
	}
}
