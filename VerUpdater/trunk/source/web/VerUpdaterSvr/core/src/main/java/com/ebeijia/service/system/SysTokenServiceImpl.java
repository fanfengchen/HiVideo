package com.ebeijia.service.system;

import org.springframework.stereotype.Service;

import com.ebeijia.common.PackInspector;

@Service
public class SysTokenServiceImpl implements SysTokenSerivce {
	public static final String conToken = "QYVD8W2JJW4KYDP2YMKWFX36";

	@Override
	public Boolean judgeToken(String token) {
		Boolean flag = false;
		if (token != null && token.equals(conToken)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Boolean judgeSign(String sendTime, String reqData,String sign) {
		String newSign = "sendTime=" + sendTime + "data=" + reqData;
		System.out.println(newSign);

		return PackInspector.getInstances().md5CompareByBuffer(newSign,sign,false);
	}

}
