package com.ebeijia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.common.ResponseMessage;
import com.ebeijia.common.RollPage;
import com.ebeijia.dto.VerSoftverDto;
import com.ebeijia.service.VerSoftverService;

/**
 * 
 * @author ff
 * @date 创建时间：2016年10月21日 下午1:15:00
 * @version 1.0
 * @parameter
 * @return
 */
@Controller
public class VerSoftverController {
	@Autowired
	private VerSoftverService verSoftverService;

	@RequestMapping(value = "/getSoftVerList")
	@ResponseBody
	public ResponseMessage getSoftVerList(HttpServletRequest request,
			HttpServletResponse res) throws Exception {
		res.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>();
		String data = request.getParameter("data");
		JSONObject obj = JSONObject.fromObject(data);
		String chnlId = obj.getString("chnlId");
		String typeId = null;
		String limit = obj.getString("limit");
		String offset = obj.getString("offset");
		
		if (obj.getString("typeId") != null && !obj.getString("typeId").equals("")) {
			typeId = obj.getString("typeId");
		}
		RollPage<VerSoftverDto> resultList = verSoftverService
				.findListPageByParams(chnlId, typeId, limit, offset);
		List<VerSoftverDto> listT = new ArrayList<VerSoftverDto>();
		// 根据返回码做判断

		if (!resultList.isSuccess()) {
			return ResponseMessage.error(resultList.getResMsg());
		} else {
			for (VerSoftverDto verSoftver : resultList.getRecordList()) {
				listT.add(verSoftver);
			}
			map.put("list", listT);
			map.put("count", resultList.getRecordSum());
			return ResponseMessage.success(map);
		}
	}
}
