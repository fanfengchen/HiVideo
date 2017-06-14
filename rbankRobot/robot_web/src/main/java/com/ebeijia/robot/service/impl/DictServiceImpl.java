package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.common.RollPage;
import com.ebeijia.robot.core.mapper.TDictMapper;
import com.ebeijia.robot.core.mapper.TDictdataMapper;
import com.ebeijia.robot.core.pojo.TDict;
import com.ebeijia.robot.core.pojo.TDictdata;
import com.ebeijia.robot.modle.api.DictListRes;
import com.ebeijia.robot.modle.api.DictRes;
import com.ebeijia.robot.service.IDictService;

@Service
public class DictServiceImpl implements IDictService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;
	
	@Autowired
	private TDictMapper dictMapper;

	@Autowired
	private TDictdataMapper dictDataMapper;

	/**
	 * 获取数据字典数据
	 */
	@Override
	public DictListRes getDict(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize) throws Exception {	

		DictListRes rList = new DictListRes();
		String orderParam = (order == null) ? null : order.toString();
		Integer recordSum = dictMapper.selectCountByParams(params);// 获取总行数

		RollPage<TDict> rollPage = new RollPage<TDict>();
		rollPage.setRecordSum(recordSum);

		if (pageSize == null)
			rollPage.setPageSize(pageSizeDefault);
		else
			rollPage.setPageSize(pageSize);

		pageNum = (pageNum == null) ? 1 : pageNum;
		rollPage.setPageNum(pageNum);
		Integer pageOffset = (rollPage.getPageNum() - 1)
				* rollPage.getPageSize();
		if (recordSum > 0) {

			rollPage.setRecordList(dictMapper.selectListByPageParams(params, pageOffset, pageSize, orderParam));
			// 返回客户端信息进行业务处理
			List<DictRes> tRes = new ArrayList<DictRes>();			
			for (TDict dict : rollPage.getRecordList()) {
				
				DictRes dictres = new DictRes();
				dictres.setId(dict.getDictId());
				dictres.setName(dict.getName());
				tRes.add(dictres);
			}
			rList.setList(tRes);
		}
		rList.setCount(rollPage.getRecordSum().toString());

		return rList;
	}

	/**
	 * 设置数据字典数据
	 */
	@Override
	public void setDict(TDict tDict) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dictId", tDict.getDictId());
		// 判断数据是不是存在，存在更新，不存在添加
		if (dictMapper.selectByParams(params) != null) {
			
			dictMapper.updateSelective(tDict);

		} else {
			dictMapper.insertSelective(tDict);
		}

	}

	/**
	 * 删除数据字典
	 */
	@Override
	public void delRebot(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dictId", id);
		List<TDictdata> tdList = dictDataMapper
				.selectListByParams(params, null);
		// 删除数据字典之前，先把对应的字典数据删除
		if (tdList != null && tdList.size() > 0) {
			// 暂时不用判断字表中是不是有数据，删除父表数据之前先删除下子表数据
			dictDataMapper.deleteBySelective(id);
		}
		dictMapper.deleteBySelective(id);
	}

	@Override
	public List<DictRes> getDict() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		List<TDict> reList = dictMapper.selectListByParams(params, "LAST_TIME desc");

		List<DictRes> tRes = new ArrayList<DictRes>();
		if (reList != null && reList.size() > 0) {
			for (TDict dict : reList) {
				DictRes dictres = new DictRes();
				dictres.setId(dict.getDictId());
				dictres.setName(dict.getName());
				tRes.add(dictres);
			}
		}

		return tRes;
	}
}
