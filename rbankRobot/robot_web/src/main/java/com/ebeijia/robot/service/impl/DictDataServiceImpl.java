package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.common.RollPage;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TAllocationRobotMapper;
import com.ebeijia.robot.core.mapper.TDictdataMapper;
import com.ebeijia.robot.core.pojo.TDictdata;
import com.ebeijia.robot.modle.api.DictDataListRes;
import com.ebeijia.robot.modle.api.DictDataRes;
import com.ebeijia.robot.service.IDictDataService;

/**
 * 数据字典信息
 * 
 * @author ff
 * @date 2016-12-13
 *
 */
@Service
public class DictDataServiceImpl implements IDictDataService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;

	@Autowired
	private TDictdataMapper dictDataMapper;
	@Autowired
	private TAllocationRobotMapper robotMapper;

	@Override
	public List<DictDataRes> getDictData(String dictId) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dictId", dictId);
		List<TDictdata> dList = dictDataMapper.selectListByParams(params,
				"DICT_KEY desc");

		List<DictDataRes> tRes = new ArrayList<DictDataRes>();
		if (dList != null && dList.size() > 0) {

			for (TDictdata data : dList) {

				DictDataRes res = new DictDataRes();
				res.setId(data.getDictKey());
				res.setName(data.getDictValue());
				res.setReadOnly(data.getIsReadOnly());
				tRes.add(res);
			}
		}
		return tRes;
	}

	/**
	 * 根据查询条件返回数据字典信息
	 */
	@Override
	public DictDataListRes getDictData(Map<String, Object> params, Order order,
			Integer pageNum, Integer pageSize) throws Exception {

		DictDataListRes res = new DictDataListRes();
		String orderParam = (order == null) ? null : order.toString();
		Integer recordSum = dictDataMapper.selectCountByParams(params);// 获取总行数

		RollPage<TDictdata> rollPage = new RollPage<TDictdata>();
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

			rollPage.setRecordList(dictDataMapper.selectListByPageParams(
					params, pageOffset, pageSize, orderParam));

			List<DictDataRes> tRes = new ArrayList<DictDataRes>();

			for (TDictdata data : rollPage.getRecordList()) {

				DictDataRes res1 = new DictDataRes();
				res1.setId(data.getDictKey());
				res1.setName(data.getDictValue());
				res1.setReadOnly(data.getIsReadOnly());
				tRes.add(res1);
			}
			res.setList(tRes);
		}
		res.setCount(rollPage.getRecordSum().toString());
		return res;
	}

	/**
	 * 设置子字典数据
	 * 
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setDictData(String dictId, List<TDictdata> list)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (list != null && list.size() > 0) {
			dictDataMapper.deleteBySelective(dictId);

			try {
				for (TDictdata ddr : list) {
					params.put("dictId", ddr.getDictId());
					params.put("dictKey", ddr.getDictKey());

					dictDataMapper.insertSelective(ddr);
					params.clear();
				}
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				throw new ServiceException("编号重复！", ex.getMessage());
			}
		}

	}

	/**
	 * 删除数据字典数据
	 * 
	 * @throws ServiceException
	 */
	@Transactional
	@Override
	public void delDictData(String dictId, String dictDataKey)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (dictId != null && "BRANCH".equals(dictId)) {
			params.put("branch", dictDataKey);
			if (robotMapper.selectCountByParams(params) > 0) {
				throw new ServiceException(ApiResultCode.Err_0001.toString(),
						"该营业厅已经绑定机器人，不能删除");
			}
			params.clear();
		}

		params.put("dictId", dictId);
		params.put("dictKey", dictDataKey);

		dictDataMapper.deleteByParams(params);
	}
}
