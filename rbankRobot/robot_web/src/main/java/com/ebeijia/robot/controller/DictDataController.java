package com.ebeijia.robot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TDictdata;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.DataMap;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.DictDataRes;
import com.ebeijia.robot.service.IDictDataService;

@RequestMapping("/")
@Controller
public class DictDataController {

	@Autowired
	private IDictDataService dataService;

	/**
	 * 
	 * 
	 * @author ff
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDictDataOfFull")
	@ResponseBody
	public ResponseMessage getDictDataOfFull(RequestMessage resMessage)
			throws Exception {

		Map<String, Object> map = resMessage.getDataMap();

		String id = (String) map.get("id");

		List<DictDataRes> dList = null;
		if (StringUtil.checkNull(false, id)) {

			dList = dataService.getDictData(id);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("list", dList);
		return ResponseMessage.success(res);
	}

	/**
	 * 设置子数据字典
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setDictData")
	@ResponseBody
	public ResponseMessage setDictData(RequestMessage resMessage)
			throws ServiceException {
		DataMap<String, Object> data = resMessage.getDataMap();
		String dictId = (String) data.get("id");
		if (StringUtil.checkNull(false, dictId)) {
			// 获取请求报文中的集合
			List<Map<String, Object>> maps = data.getValueObAsList("list");
			if (maps != null) {
				List<TDictdata> list = new ArrayList<TDictdata>();
				for (Map<String, Object> m : maps) {
					TDictdata td = new TDictdata();
					td.setDictId(dictId);
					td.setDictKey((String) m.get("id"));
					td.setDictValue((String) m.get("name"));
					td.setIsReadOnly((String) m.get("readOnly"));
					list.add(td);
				}
				dataService.setDictData(dictId,list);
			}

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());

		}

		return ResponseMessage.success();
	}

	/**
	 * 删除数据字典数据
	 * 
	 * @param resMessage
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping("delDictData")
	@ResponseBody
	public ResponseMessage delDictData(RequestMessage resMessage) throws ServiceException {
		Map<String, Object> data = resMessage.getDataMap();
		String dictId = (String) data.get("dictId");
		String dictDataKey = (String) data.get("dictDataKey");
		if (StringUtil.checkNull(false, dictId, dictDataKey)) {
			dataService.delDictData(dictId, dictDataKey);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

}
