package com.ebeijia.video.service;

import com.ebeijia.videocore.pojo.TUser;

/**
 * 公共的方法
 * @author lijm
 * @date 2016-11-10
 *
 */
public interface UserTokenService {

	TUser getUserToken(String token);
}
