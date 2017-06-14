<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
	
  </head>
  
  <body>
  	<form action="loginToJson.html" method="post">
  		<table border="1">
  			<tr>
  				<td>用户名：</td>
  				<td><input type="text" value="" name="userName"></td>
  			</tr>
  			<tr>
  				<td>密&nbsp;&nbsp;码:</td>
  				<td><input type="text" value="" name="password"></td>
  			</tr>
  			<tr>
  				<td colspan="2" align="center">
  					<input type="submit" value="登录(view)">
                    <input type="submit" value="登录(json)">
  				</td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>
