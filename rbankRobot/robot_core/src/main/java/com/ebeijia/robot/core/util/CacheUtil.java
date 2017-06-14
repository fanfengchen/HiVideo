package com.ebeijia.robot.core.util;

import org.apache.commons.lang.StringUtils;
import com.ebeijia.robot.core.cache.service.IMemcachedService;
import com.ebeijia.robot.core.constant.MemcachedConstant;

public class CacheUtil {
	public static IMemcachedService getMemcachedService() {
		return (IMemcachedService) SpringContextUtil
				.getBean("memcachedService");
	}

	public static synchronized String getFileNameSeq() throws Exception {
		IMemcachedService memcachedService = CacheUtil.getMemcachedService();
		memcachedService.get(MemcachedConstant.FILE_NAME_TMP);
		String date = DateUtil.getDays();
		String num = memcachedService.get(MemcachedConstant.FILE_NAME_TMP
				+ date);
		if (StringUtils.isEmpty(num)) {
			num = "1";

		} else {
			num = String.valueOf(Long.valueOf(num) + 1);
		}
		memcachedService.set(MemcachedConstant.FILE_NAME_TMP + date, 24 * 60,
				num);
		return DateUtil.getTimeString() + StringUtils.leftPad(num, 6, '0');
	}
}
