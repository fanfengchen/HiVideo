package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.common.RollPage;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TDictateRobotMapper;
import com.ebeijia.robot.core.pojo.TDictateRobot;
import com.ebeijia.robot.core.util.PropertiesUtils;
import com.ebeijia.robot.modle.api.DicateRobRes;
import com.ebeijia.robot.modle.api.DicateRobotList;
import com.ebeijia.robot.modle.api.DictateRobotRes;
import com.ebeijia.robot.service.IDictateRobotService;

@Service
public class DictateRobotServiceImpl implements IDictateRobotService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;

	@Autowired
	private TDictateRobotMapper dictateMapper;

	/**
	 * 获取指令集
	 */
	@Override
	public List<DictateRobotRes> getDictsteRobot(Map<String, Object> params) {

		List<TDictateRobot> dictate = dictateMapper.selectRobotByParams(params);

		List<DictateRobotRes> rList = new ArrayList<DictateRobotRes>();
		if (dictate != null && dictate.size() > 0) {

			for (TDictateRobot robot : dictate) {

				DictateRobotRes res = new DictateRobotRes();
				res.setDictateCode(robot.getDictateCode());
				res.setDictateName(robot.getDictateName());
				res.setDictateContext(robot.getDictateContext());
				res.setdType(robot.getdType());
				res.setdName(robot.getdName());
				res.setdContext(robot.getRes2());
				res.setUrlPath(PropertiesUtils.getProperties("down_url")+robot.getUrlPath());
				rList.add(res);
			}
		}
		return rList;
	}

	/**
	 * 后台接口 添加配置机器人指令
	 */
	@Transactional
	@Override
	public void insertDictate(TDictateRobot dictateRobot) {
		dictateMapper.insertSelective(dictateRobot);// 添加配置机器人指令
	}

	/**
	 * 后台接口 更新配置机器人指令
	 */
	@Transactional
	@Override
	public void updateDictate(TDictateRobot dictateRobot) {

		dictateMapper.updateByPrimaryKeySelective(dictateRobot);// 更新数据
	}

	/**
	 * 后台接口删除配置机器人指令
	 */
	@Transactional
	@Override
	public void delDictate(String dId) {

		dictateMapper.deleteByPrimaryKey(Integer.parseInt(dId));// 删除数据
	}

	/**
	 * 获取配置机器人指令列表
	 */
	@Override
	public DicateRobotList findDictateList(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)
			throws ServiceException {
		DicateRobotList dList = new DicateRobotList();// 返回集合
		try {

			String orderParam = (order == null) ? null : order.toString();
			Integer recordSum = dictateMapper.selectCountByParams(params);// 获取总行数

			RollPage<TDictateRobot> rollPage = new RollPage<TDictateRobot>();
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

				rollPage.setRecordList(dictateMapper.selectListByParams(params,
						pageOffset, pageSize, orderParam));
				// 返回客户端信息进行业务处理

				List<DicateRobRes> resList = new ArrayList<DicateRobRes>();
				for (TDictateRobot robot : rollPage.getRecordList()) {
					DicateRobRes res = new DicateRobRes();
					res.setdId(robot.getdId().toString());
					res.setDeviceId(robot.getDeviceId());
					res.setDictateCode(robot.getDictateCode());
					res.setdType(robot.getdType());
					res.setDictateName(robot.getDictateName());
					res.setDictateContext(robot.getDictateContext());
					resList.add(res);
				}
				dList.setList(resList);
			}
			dList.setCount(rollPage.getRecordSum().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("获取配置机器人列表异常", ex.getMessage());
		}
		return dList;
	}

}
