package com.ebeijia.robot.service;

import java.util.List;

import com.ebeijia.robot.core.pojo.TRoleAcc;
import com.ebeijia.robot.modle.api.RoleAccRes;

public interface IRoleAccService {

	List<RoleAccRes> getRoleAccList(String id) throws Exception;

	void setRoleAcc(String id, List<TRoleAcc> list) throws Exception;

}
