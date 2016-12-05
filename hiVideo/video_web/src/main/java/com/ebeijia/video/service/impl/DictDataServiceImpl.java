package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.video.modle.api.DictDataRes;
import com.ebeijia.video.service.DictDataService;
import com.ebeijia.videocore.mapper.TDictdataMapper;
import com.ebeijia.videocore.pojo.TDictdata;

/**
 * 数据字典信息
 * @author lijm
 * @date 2016-11-11
 *
 */
@Service
public class DictDataServiceImpl implements DictDataService{

	@Autowired
	private TDictdataMapper dictDataMapper;
	
	/**
	 * 根据查询条件返回数据字典信息
	 */
	@Override
	public List<DictDataRes> getDict(String dictId) {
		
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dictId", dictId);
		List<TDictdata> dList = dictDataMapper.selectListByParams(params, null);
		
		List<DictDataRes> tRes = new ArrayList<DictDataRes>();
		if(dList!=null&&dList.size()>0){
			
			for(TDictdata data:dList){
				
				DictDataRes res = new DictDataRes();
				res.setId(data.getDictKey());
				res.setName(data.getDictValue());
				tRes.add(res);
			}
		}
		return tRes;
	}

}
