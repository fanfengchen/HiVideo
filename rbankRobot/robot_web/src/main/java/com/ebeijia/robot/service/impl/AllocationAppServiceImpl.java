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
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TAllocationAppMapper;
import com.ebeijia.robot.core.pojo.TAllocationApp;
import com.ebeijia.robot.core.util.PropertiesUtils;
import com.ebeijia.robot.modle.api.AllocaAppRes;
import com.ebeijia.robot.modle.api.AllocationAppList;
import com.ebeijia.robot.modle.api.AllocationAppsRes;
import com.ebeijia.robot.service.IAllocationAppService;

/**
 * 配置app应用
 * 
 * @author lijm
 * @date 2016-12-16
 *
 */
@Service
public class AllocationAppServiceImpl implements IAllocationAppService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;
	
	@Autowired
	private TAllocationAppMapper appMapper;

	@Override
	public List<AllocaAppRes> getAllApp(String deviceId) {

		List<AllocaAppRes> sList = new ArrayList<AllocaAppRes>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);
		List<TAllocationApp> aList = appMapper.selectListParams(params, null);
		if (aList != null && aList.size() > 0) {

			for (TAllocationApp app : aList) {

				AllocaAppRes res = new AllocaAppRes();
				res.setaName(app.getAname());
				res.setbName(app.getBname());
				res.setVersion(app.getVersion());
				res.setContext(app.getContext());
				res.setState(app.getState());
				res.setUrl(PropertiesUtils.getProperties("down_url")+app.getUrl());
				sList.add(res);
			}
		}
		return sList;
	}

	/**
	 * 后台接口 更新配置应用APP
	 */
	@Transactional
	@Override
	public void updataAllocationApp(TAllocationApp allocationApp)
			throws Exception {
		appMapper.updateByPrimaryKeySelective(allocationApp);// 更新配置应用App

	}

	/**
	 * 后台接口 删除配置应用APP
	 */
	@Transactional
	@Override
	public void delAllocationApp(int aId) throws Exception {
		appMapper.deleteByPrimaryKey(aId);// 删除配置应用App

	}

	/**
	 * 后台接口 获取配置应用APP
	 */
	@Override
	public AllocationAppList findAllocationAppList(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)
			throws ServiceException {
		AllocationAppList rList = new AllocationAppList();// 返回集合
		try {

			String orderParam = (order == null) ? null : order.toString();
			Integer recordSum = appMapper.selectCountByParams(params);// 获取总行数

			RollPage<TAllocationApp> rollPage = new RollPage<TAllocationApp>();
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

				rollPage.setRecordList(appMapper.selectListByParams(params,
						pageOffset, pageSize, orderParam));
				// 返回客户端信息进行业务处理

				List<AllocationAppsRes> resList = new ArrayList<AllocationAppsRes>();
				for (TAllocationApp tapp : rollPage.getRecordList()) {
					AllocationAppsRes res = new AllocationAppsRes();
					res.setaId(tapp.getaId().toString());
					res.setDeviceId(tapp.getDeviceId());
					res.setaName(tapp.getAname());
					res.setbName(tapp.getBname());
					res.setVersion(tapp.getVersion());
					res.setState(tapp.getState());
					res.setContext(tapp.getContext());
					resList.add(res);
				}
				rList.setList(resList);
			}
			rList.setCount(rollPage.getRecordSum().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("获取配置应用APP列表异常", ex.getMessage());
		}
		return rList;
	}

	/**
	 * 后台接口 添加配置应用APP
	 */
	@Transactional
	@Override
	public void insertAllocationApp(TAllocationApp allocationApp)
			throws Exception {
		appMapper.insertSelective(allocationApp);
	}

	/**
	 * 设备应用App的状态
	 */
	@Override
	public void updateState(String aId, String state) {

		TAllocationApp app = new TAllocationApp();
		app.setaId(Integer.parseInt(aId));
		app.setState(state);
		appMapper.updateByPrimaryKeySelective(app);
	}

}
