package com.ebeijia.videocore.util;

import java.util.UUID;
import org.apache.commons.lang.StringUtils;

import com.ebeijia.videocore.cache.service.IMemcachedService;
import com.ebeijia.videocore.constant.MemcachedConstant;
import com.ebeijia.videocore.exception.WebInfoException;

public class VideoUtil {
	
	private static int time = Integer.parseInt(StringUtils.isEmpty(PropertiesUtils
			.getProperties("memcache.token.timeout")) ? "10"
			: PropertiesUtils.getProperties("memcache.token.timeout").trim())*60;
	
	/**
	 * 设置视频的K值
	 * @param data
	 * @return
	 * @throws WebInfoException
	 */
	public static String setMdiUrl(String k) throws WebInfoException {
		String uuid = UUID.randomUUID().toString();
		String token = Md5.md5Upper(uuid);
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			memcachedService.set(MemcachedConstant.video + token,
					time, k);
			return token;
		} catch (Exception e) {
			throw new WebInfoException(e.getMessage(), e);
		}
	}
	
	/**
	 * 从缓存中获取K值
	 * @param k
	 * @return
	 */
	public static String getToken(String k) {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			String data = memcachedService.get(MemcachedConstant.video
					+ k);
			if (data != null) {
				memcachedService.set(
						MemcachedConstant.video + k, time, data);
			}
			return data;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 清除缓存的K
	 * @param k
	 */
	public static void clearGetToken(String k) {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			
		  memcachedService.delete(MemcachedConstant.video+ k);			
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(),e);			
		}
	}
}
