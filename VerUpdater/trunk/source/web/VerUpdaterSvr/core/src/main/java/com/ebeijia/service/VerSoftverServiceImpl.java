package com.ebeijia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.common.RollPage;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.VerSoftverDto;
import com.ebeijia.mapper.VerChannelMapper;
import com.ebeijia.mapper.VerSoftverMapper;
import com.ebeijia.models.VerSoftver;
import com.ebeijia.util.Propert;

/**
 * 
 * @author ff
 * @date 创建时间：2016年10月20日 下午7:24:37
 * @version 1.0
 * @parameter
 * @return
 */
@Service
public class VerSoftverServiceImpl implements VerSoftverService {

	@Autowired
	private VerChannelMapper verChannelMapper;

	@Autowired
	private VerSoftverMapper verSoftverMapper;

	public RollPage<VerSoftverDto> findListPageByParams(String chnlId,
			String typeId, String limit, String offset)
			throws Exception {
		Logger log = LoggerFactory.getLogger(VerSoftverServiceImpl.class);
		RollPage<VerSoftverDto> rollPage = new RollPage<VerSoftverDto>();
		rollPage.setSuccess(false);//失败
		int   pageOffset = Integer.valueOf(limit).intValue();
		
		int pageSize = Integer.valueOf(offset).intValue();
		// 判断chnl_type 是不是可用
		if (verChannelMapper.selectByChnlId(chnlId) == 0) {
			rollPage.setResMsg(RespCode.CHANNEL_ERROR);
			return rollPage;
		}
		
		// 获取总记录数
		Integer recordSum = verSoftverMapper
				.selectCountByParams(chnlId, typeId);
		rollPage.setRecordSum(recordSum);

		if (recordSum == null) {
			//rollPage.setRecordList(new ArrayList<VerSoftverDto>());
			rollPage.setResMsg(RespCode.RESULT_NULL);
			return rollPage;
		}
		// 查找数据
		List<VerSoftver> reqList = verSoftverMapper.selectByPrimarys(chnlId,
				typeId, pageOffset, pageSize);
		List<VerSoftverDto> newList = new ArrayList<VerSoftverDto>();
		String imgUrl = Propert.getInstances().pro("img.Path");
		for (VerSoftver verSof : reqList) {
			VerSoftverDto verSoftverDto = new VerSoftverDto();
			verSoftverDto.setSoftId(verSof.getSoftId().toString());
			verSoftverDto.setTypeId(verSof.getTypeId().toString());
			verSoftverDto.setTypeName(verSof.getVerSoftype().getName());
			verSoftverDto.setName(verSof.getName());
			verSoftverDto.setPackName(verSof.getPackName());
			verSoftverDto.setVerNo(verSof.getVerNo());
			verSoftverDto.setPackUrl(verSof.getPackUrl());
			verSoftverDto.setImgUrl(imgUrl
					+ verSof.getMdiFileinfo().getUrlPath());
			verSoftverDto
					.setIsuTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
							.format(verSof.getIsuTime()));
			verSoftverDto.setIsForce(verSof.getIsForce());
			verSoftverDto.setUpdateLog(verSof.getUpdateLog());
			verSoftverDto.setAbout(verSof.getAbout());
			newList.add(verSoftverDto);
			log.info(verSoftverDto.getSoftId());
		}
		rollPage.setRecordList(newList);
		rollPage.setSuccess(true);//成功

		return rollPage;

	}

}
