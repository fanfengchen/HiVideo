package com.ebeijia.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.contstant.RespCode;
import com.ebeijia.mapper.VerChannelMapper;
import com.ebeijia.models.VerChannel;
@Service
@Transactional
public class VerChannelServiceImpl implements VerChannelService{
	@Autowired
	private VerChannelMapper verChannelMapper;
	
	public  Map<String, String> selectVerChannelByChnlId(String chanlId) {
		 Map<String, String> channlmap = new HashMap<String, String>();
		if("".equals(chanlId.trim())){
			channlmap.put("resCode", RespCode.PARAM_ERROR);
			return channlmap;
		}
		 
		 //获取渠道记录
		 VerChannel chan=verChannelMapper.selectVerChannelByChnlId(chanlId);	
		//判断记录是否为空
		 if(chan==null){
			channlmap.put("resCode", RespCode.RESULT_NULL);
		 }
		 //判断渠道是否禁用
		else if(chan.getState().equals("02")){
			channlmap.put("resCode", RespCode.CHANNEL_FORBIDDEN);
		}
		else{
			channlmap.put("resCode", RespCode.SUCCESS_CODE);
		}
		return channlmap;
	}
}
