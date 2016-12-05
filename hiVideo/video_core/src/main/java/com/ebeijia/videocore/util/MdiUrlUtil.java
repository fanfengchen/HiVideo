package com.ebeijia.videocore.util;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.ebeijia.videocore.cache.service.IMemcachedService;
import com.ebeijia.videocore.constant.MemcachedConstant;
import com.ebeijia.videocore.exception.WebInfoException;

/**
 * 将视屏放入缓存
 * @author lijm
 *
 */
public class MdiUrlUtil {

	private static int time = Integer.parseInt(StringUtils.isEmpty(PropertiesUtils
			.getProperties("memcache.token.timeout")) ? "10"
			: PropertiesUtils.getProperties("memcache.token.timeout").trim())*60;
	
	public static String setMdiUrl(Map data) throws WebInfoException {
		String uuid = UUID.randomUUID().toString();
		String token = Md5.md5Upper(uuid);
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		try {
			memcachedService.set(MemcachedConstant.mdiUrl + token,
					time, data);
			return token;
		} catch (Exception e) {
			throw new WebInfoException(e.getMessage(), e);
		}
	}
}
