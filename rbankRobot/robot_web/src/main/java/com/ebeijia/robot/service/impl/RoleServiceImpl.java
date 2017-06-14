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
import com.ebeijia.robot.core.mapper.TAllocationUserMapper;
import com.ebeijia.robot.core.mapper.TRoleAccMapper;
import com.ebeijia.robot.core.mapper.TRoleMapper;
import com.ebeijia.robot.core.pojo.TRole;
import com.ebeijia.robot.core.pojo.TRoleAccKey;
import com.ebeijia.robot.modle.api.RoleList;
import com.ebeijia.robot.modle.api.RoleRes;
import com.ebeijia.robot.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	protected @Value("${global.pageSize}") Integer pageSizeDefault;

	@Autowired
	private TRoleMapper roleMapper;

	@Autowired
	private TRoleAccMapper accMapper;
	@Autowired
	private TAllocationUserMapper userMapper;

	@Override
	public RoleList findRoleList(Map<String, Object> params, Order order,
			Integer pageNum, Integer pageSize) throws ServiceException {

		RoleList rList = new RoleList();// 返回集合
		try {

			String orderParam = (order == null) ? null : order.toString();
			Integer recordSum = roleMapper.selectCountByParams(params);// 获取总行数

			RollPage<TRole> rollPage = new RollPage<TRole>();
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

				rollPage.setRecordList(roleMapper.selectListByParams(params,
						pageOffset, pageSize, orderParam));
				// 返回客户端信息进行业务处理
				rList.setCount(rollPage.getRecordSum().toString());
				List<RoleRes> resList = new ArrayList<RoleRes>();
				for (TRole role : rollPage.getRecordList()) {
					RoleRes roleRes = new RoleRes();
					roleRes.setId(role.getRoleId().toString());
					roleRes.setName(role.getName());
					resList.add(roleRes);

				}
				rList.setList(resList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("获取角色列表异常", ex.getMessage());
		}
		return rList;
	}

	/**
	 * 删除角色操作
	 */
	@Transactional
	@Override
	public void delRole(String id) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", id);
		
		if (userMapper.selectCountByParams(params)>0) {
			throw new ServiceException(ApiResultCode.Err_0001.toString(),
					"角色已绑定用户，无法删除！");
		}

		TRoleAccKey accKey = new TRoleAccKey();
		accKey.setAccId(id);
		accMapper.deleteByPrimaryKey(accKey);

		roleMapper.deleteByPrimaryKey(Integer.parseInt(id));

	}

	/**
	 * 设置角色操作
	 */
	@Transactional
	@Override
	public void updateRole(String id, String name) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		List<TRole> list = roleMapper.selectByParams(params);
		if (list != null && list.size() > 0) {
			throw new ServiceException(ApiResultCode.Err_0001.toString(),
					"角色名称不能重复");
		} else {
			TRole trole = new TRole();
			trole.setName(name);

			// 判断 Id是否为空
			if (id != null && !"".equals(id)) {
				trole.setRoleId(Integer.parseInt(id));
				roleMapper.updateByPrimaryKeySelective(trole);// Id 不為空 更新
			} else {
				roleMapper.insertSelective(trole);// Id為空 添加
			}
		}

	}

	@Override
	public List<RoleRes> getRoleIdList() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<TRole> tList = roleMapper
				.selectListByParam(params, "ROLE_ID desc");
		List<RoleRes> resList = new ArrayList<RoleRes>();
		if (tList != null && tList.size() > 0) {
			for (TRole tRole : tList) {
				RoleRes rRes = new RoleRes();
				rRes.setId(tRole.getRoleId().toString());
				rRes.setName(tRole.getName());
				resList.add(rRes);
			}
		}
		return resList;
	}
}
