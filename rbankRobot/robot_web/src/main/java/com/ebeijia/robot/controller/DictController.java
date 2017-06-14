package com.ebeijia.robot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.pojo.TDict;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.DictRes;
import com.ebeijia.robot.service.IDictService;

@RequestMapping("/")
@Controller
public class DictController {

	@Autowired
	private IDictService dictService;

	/**
	 * 获取数据字典数据
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDictData")
	@ResponseBody
	public ResponseMessage getDictData(RequestMessage resMessage)
			throws Exception {

		List<DictRes> list = dictService.getDict();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return ResponseMessage.success(map);

	}

	/**
	 * 设置数据字典名
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setDict")
	@ResponseBody
	public ResponseMessage setDict(RequestMessage resMessage) throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String id = (String) data.get("id");// id
		String name = (String) data.get("name");// 名称
		String desc = (String) data.get("desc");// 描述
		String readOnly = (String) data.get("readOnly");// 是否只读 是:1 否:0
		TDict tDict = null;
		if (StringUtil.checkNull(false, id, name, readOnly)) {
			tDict = new TDict();
			tDict.setDictId(id);
			tDict.setName(name);
			tDict.setDescCn(desc);
			tDict.setIsReadOnly(readOnly);

			dictService.setDict(tDict);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 删除字典数据
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delDict")
	@ResponseBody
	public ResponseMessage delDict(RequestMessage reMessage) throws Exception {

		Map<String, Object> map = reMessage.getDataMap();

		String id = (String) map.get("id");// id

		if (StringUtil.checkNull(false, id)) {// 验证必填参数

			dictService.delRebot(id);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();
	}

}
