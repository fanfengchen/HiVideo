package com.ebeijia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.common.RollPage;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.SeachSoftVerDto;
import com.ebeijia.mapper.VerChannelMapper;
import com.ebeijia.mapper.VerSoftverMapper;
import com.ebeijia.models.VerSoftver;
import com.ebeijia.util.Propert;

@Service
public class SearcherSoftVerServiceImpl implements SearcherSoftVerService{
	@Autowired
	private VerSoftverMapper verSoftverMapper;
	@Autowired
	private VerChannelMapper verChannelMapper;
	
	@Transactional
	@Override
	public RollPage<SeachSoftVerDto> selectCountBysoftId(
			String content,String pagOffset,String pagSize) throws Exception {
		int pageSize=10;
		int pageSet=0;
		RollPage<SeachSoftVerDto> rollPage = new RollPage<SeachSoftVerDto>();
		rollPage.setSuccess(false);//失败
		if("".equals(content.trim())||"".equals(pagSize.trim())||"".equals(pagOffset.trim())){
			rollPage.setResMsg(RespCode.PARAM_ERROR);
			return rollPage;
		}
		if(!(pagSize==null)){
			pageSize= Integer.valueOf(pagSize).intValue();//返回条数
		}
		if(!(pagOffset==null)){
			pageSet= Integer.valueOf(pagOffset).intValue();//起始数
		}
		rollPage.setPageSize(pageSize);
		rollPage.setPageNum(pageSet);
//		Integer pageSet = (rollPage.getPageNum() - 1) * rollPage.getPageSize();

		String imgDominUrl=Propert.getInstances().pro("img.Path");
		//获取记录
		List<VerSoftver> reqList = verSoftverMapper.selectBysoftId(content.trim(),pageSet, pageSize);
		if(reqList.size()==0){
			rollPage.setResMsg(RespCode.RESULT_NULL);
			return rollPage;
		}
		//获取记录条数
		rollPage.setRecordSum(reqList.size());
		List<SeachSoftVerDto> newList = new ArrayList();
		for (VerSoftver verSof : reqList) {
			SeachSoftVerDto seachSoftVerDto = new SeachSoftVerDto();
			seachSoftVerDto.setSoftId(verSof.getSoftId());
			seachSoftVerDto.setTypeId(verSof.getTypeId());
			seachSoftVerDto.setTypeName((verSof.getVerSoftype()).getName());
			seachSoftVerDto.setName(verSof.getName());
			seachSoftVerDto.setPackName(verSof.getPackName());
			seachSoftVerDto.setVerNo(verSof.getVerNo());
			seachSoftVerDto.setPackUrl(verSof.getPackUrl());
			seachSoftVerDto.setImgUrl(imgDominUrl+(verSof.getMdiFileinfo()).getUrlPath());
			seachSoftVerDto.setIsuTime( new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(verSof.getIsuTime()));
			seachSoftVerDto.setIsForce(verSof.getIsForce());
			seachSoftVerDto.setUpdateLog(verSof.getUpdateLog());
			seachSoftVerDto.setAbout(verSof.getAbout());
			newList.add(seachSoftVerDto);
		}
		rollPage.setRecordList(newList);
		rollPage.setSuccess(true);//成功
				
		return rollPage;
	}
}
