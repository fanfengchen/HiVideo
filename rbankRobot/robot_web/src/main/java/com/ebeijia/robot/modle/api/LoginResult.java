package com.ebeijia.robot.modle.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆接口返回字段
 * @author lijm
 *
 */
public class LoginResult {
	
	private String authToken;//用户Token
	private String serKey;//服务器KEY
	private String roleId;//角色
	private String roleName;//角色名称
	List<RoleResult> roleAcc = new ArrayList<RoleResult>();
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getSerKey() {
		return serKey;
	}
	public void setSerKey(String serKey) {
		this.serKey = serKey;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<RoleResult> getRoleAcc() {
		return roleAcc;
	}
	public void setRoleAcc(List<RoleResult> roleAcc) {
		this.roleAcc = roleAcc;
	}
	

}
