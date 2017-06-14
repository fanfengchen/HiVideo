package com.ebeijia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.common.RollPage;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.PreviewImgUrl;
import com.ebeijia.dto.VerSoftDto;
import com.ebeijia.mapper.VerSoftverMapper;
import com.ebeijia.models.MdiFileinfo;
import com.ebeijia.models.VerSoftver;
import com.ebeijia.util.Propert;
@Service
public class GetSoftVerInfoServiceImpl implements GetSoftVerInfoService{
	@Autowired
	private VerSoftverMapper verSoftverMapper;
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Transactional
	@Override
	public RollPage<VerSoftDto> selectByPrimaryKey(String softId) throws Exception{
		String imgDominUrl=Propert.getInstances().pro("img.Path");//获取服务器域名 
		RollPage rollPage = new RollPage();
		 rollPage.setSuccess(false);//失败
		 if("".equals(softId.trim())){
			 rollPage.setResMsg(RespCode.PARAM_ERROR);
			 return rollPage; 
			}
		 Integer sofId = new Integer(softId);
		 List<MdiFileinfo> mdiFileinfo=verSoftverMapper.selectpreviewImgUrl(sofId);
		 
		 List<PreviewImgUrl> imgUrl =new ArrayList();//图片地址列表
		 for (MdiFileinfo fileInfo : mdiFileinfo) {
				PreviewImgUrl url =new PreviewImgUrl();
				url.setPreviewImgUrl(imgDominUrl+fileInfo.getUrlPath());
				imgUrl.add(url);
			}

		 //查询版本内容
		 VerSoftver verSoft=verSoftverMapper.selectByPrimaryKey(sofId);
		 if(verSoft==null){
			 rollPage.setResMsg(RespCode.RESULT_NULL);
             return rollPage;  
         }
		List<MdiFileinfo> filelist=new ArrayList<>();
		VerSoftDto verSoftDto = new VerSoftDto();//返回数据新对象
		
		verSoftDto.setName(verSoft.getName());
		verSoftDto.setPackName(verSoft.getPackName());
		verSoftDto.setVerNo(verSoft.getVerNo());
		verSoftDto.setPackUrl(verSoft.getPackUrl());
		verSoftDto.setImgUrl(imgDominUrl+verSoft.getMdiFileinfo().getUrlPath());
		verSoftDto.setIsuTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(verSoft.getIsuTime()));
		verSoftDto.setIsForce(verSoft.getIsForce());
		verSoftDto.setUpdateLog(verSoft.getUpdateLog());
		verSoftDto.setAbout(verSoft.getAbout());
		verSoftDto.setName(verSoft.getName());
		verSoftDto.setList(imgUrl);
		 
		 if(verSoftDto==null){
			  rollPage.setResMsg(RespCode.GET_DATA_FILED);
              return rollPage;  
         }else{
			  rollPage.setVerSoftDto(verSoftDto);
			  rollPage.setSuccess(true);//成功
			  return rollPage;
		 }
	}
}
