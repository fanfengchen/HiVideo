package com.ebeijia.robot.service;

import com.ebeijia.robot.core.pojo.TAllocationUser;


/**
 * 公共的方法
 * @author lijm
 * @date 2016-12-14
 *
 */
public interface IUserTokenService {

	TAllocationUser getUserToken(String token);
}
