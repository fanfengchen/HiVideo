package com.ebeijia.video.service.impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.ebeijia.video.service.UserTokenService;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.util.TokenUtil;

@Service
public class UserTokenServiceImpl implements UserTokenService{
	
	@Override
	public TUser getUserToken(String token) {
		
		Map currInfo = TokenUtil.getToken(token);			
		TUser userInfo = (TUser)currInfo.get("userInfo");	
		
		return userInfo;
	}

}
