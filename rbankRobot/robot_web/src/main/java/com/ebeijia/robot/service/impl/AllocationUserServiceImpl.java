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
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TAllocationUserMapper;
import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationUserRes;
import com.ebeijia.robot.modle.api.AllocationUserList;
import com.ebeijia.robot.service.IAllocationUserService;

/**
 * 配置用户信息(基本的增删改查)，供app端使用
 * 
 * @author lijm
 * @date 2016-12-12
 *
 */
@Service
public class AllocationUserServiceImpl implements IAllocationUserService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;

	@Autowired
	private TAllocationUserMapper userMapper;

	/**
	 * 查询配置用户信息
	 */
	@Override
	public AllocationUserList findAllocation(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)
			throws ServiceException {
		AllocationUserList uList = new AllocationUserList();// 返回集合
		try {

			String orderParam = (order == null) ? null : order.toString();
			Integer recordSum = userMapper.selectCountByParams(params);// 获取总行数

			RollPage<TAllocationUser> rollPage = new RollPage<TAllocationUser>();
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

				rollPage.setRecordList(userMapper.selectListByParams(params,
						pageOffset, pageSize, orderParam));
				// 返回客户端信息进行业务处理

				
				List<AllocationUserRes> resList = new ArrayList<AllocationUserRes>();
				for (TAllocationUser user : rollPage.getRecordList()) {

					AllocationUserRes res = new AllocationUserRes();
					res.setUserId(user.getUserId());
					res.setUserName(user.getUserName());
					res.setMobile(user.getMobile());
					res.setBranch(user.getBranch());
					res.setRoleId(user.getRoleId().toString());;
					res.setState(user.getState());
					resList.add(res);
				}
				uList.setList(resList);
			}
			uList.setCount(rollPage.getRecordSum().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("获取配置用户信息异常", ex.getMessage());
		}
		return uList;
	}

	/**
	 * 添加用户配置信息
	 */
	@Transactional
	@Override
	public ResponseMessage addAllocationUser(TAllocationUser locationUser)
			throws Exception {

		if (locationUser != null) {
			if (userMapper.selectByPrimaryKey(locationUser.getUserId()) != null) {
				return ResponseMessage.error(ApiResultCode.Err_0020.getCode(),
						ApiResultCode.Err_0020.getMsg());
			} else {

				userMapper.insertSelective(locationUser);
			}
		}
		return ResponseMessage.success();

	}

	/**
	 * 删除用户配置信息
	 */
	@Transactional
	@Override
	public void delAllocationUser(String userId) throws Exception {
		// 根据传来的userId查找数据库中是否有数据
		TAllocationUser tUser = userMapper.selectByPrimaryKey(userId);
		// 如果有数据，删除数据
		if (tUser != null) {
			userMapper.deleteByPrimaryKey(userId);
		}

	}

	/**
	 * 更新用户配置信息j
	 */
	@Transactional
	@Override
	public void updateAllocationUser(TAllocationUser locationUser)
			throws Exception {
		// 更新
		if (locationUser != null) {
			userMapper.updateByPrimaryKeySelective(locationUser);
		}

	}

}
