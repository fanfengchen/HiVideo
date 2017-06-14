package com.ebeijia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.common.ResponseMessage;
import com.ebeijia.common.RollPage;
import com.ebeijia.contstant.MessageConstant;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.VerSofttypeDto;
import com.ebeijia.service.VerSofttypeService;

/**
 * 
 * @author ff
 * @date 创建时间：2016年10月21日 下午2:50:18
 * @version 1.0
 * @parameter
 * @return
 */
@Controller
public class VerSofttypeController {
	@Autowired
	private VerSofttypeService verSofttypeService;

	@RequestMapping(value = "/getSoftTypeList")
	@ResponseBody
	public ResponseMessage getSoftTypeList() {
		Map<String, Object> map = new HashMap<String,Object>();
		RollPage<VerSofttypeDto> resultList = verSofttypeService.findInfoList();
		List<VerSofttypeDto> listT = new ArrayList<VerSofttypeDto>();
		if ("-1".equals(resultList.getResMsg())) {
			return ResponseMessage.error(MessageConstant.ERR_PARAMETER.getRspCode());
		}
		if ("0".equals(resultList.getResMsg())) {
			for (VerSofttypeDto verSofttypeDto : resultList.getRecordList()) {
				listT.add(verSofttypeDto);
			}
			map.put("list", listT);
			return ResponseMessage.success(map);
		}
		return null;

	}
}
