package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.robot.core.mapper.TRoleAccMapper;
import com.ebeijia.robot.core.pojo.TRoleAcc;
import com.ebeijia.robot.modle.api.RoleAccRes;
import com.ebeijia.robot.service.IRoleAccService;

@Service
public class RoleAccServiceImpl implements IRoleAccService {

	@Autowired
	private TRoleAccMapper accMapper;

	/**
	 * 获取角色权限操作
	 */
	@Override
	public List<RoleAccRes> getRoleAccList(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", id);
		List<TRoleAcc> aList = accMapper.selectListByParams(params, "ACC_ID desc");
		List<RoleAccRes> list = new ArrayList<RoleAccRes>();
		if (aList != null && aList.size() > 0) {
			for (TRoleAcc roleAcc : aList) {
				RoleAccRes accRes = new RoleAccRes();

				accRes.setId(roleAcc.getAccId());
				accRes.setName(roleAcc.getAccName());
				list.add(accRes);
			}
		}

		return list;
	}

	/**
	 * 设置角色权限操作
	 */
	@Transactional
	@Override
	public void setRoleAcc(String id, List<TRoleAcc> list) throws Exception {
    //判断id 是不是为空
		if (id != null && !"".equals(id)) {
			accMapper.deleteByRoleId(Integer.parseInt(id));
			if (list != null && list.size() > 0) {
				for (TRoleAcc tacc : list) {
					TRoleAcc acc = new TRoleAcc();
					acc.setRoleId(id);
					acc.setAccId(tacc.getAccId());
					acc.setAccName(tacc.getAccName());
					accMapper.insertSelective(acc);
				}
			}

		}

	}
}
