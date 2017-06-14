package com.ebeijia.robot.service.impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.util.TokenUtil;
import com.ebeijia.robot.service.IUserTokenService;

@Service
public class UserTokenServiceImpl implements IUserTokenService{
	
	@Override
	public TAllocationUser getUserToken(String token) {
		
		Map currInfo = TokenUtil.getToken(token);			
		TAllocationUser userInfo = (TAllocationUser)currInfo.get("userInfo");
		
		return userInfo;
	}

}
