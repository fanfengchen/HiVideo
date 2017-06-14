<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主界面</title>
	
  </head>
  
  <body>
  	<form action="login.html" method="post">
  		名称：${ userName}<br/>
  		用户名:${user.userName}<br/>
        邮箱:${user.email}<br/>
  	</form>
  </body>
<script>
    var resInfo="${resInfo}";
    if(resInfo!=null && resInfo!=""){
        alert(resInfo)
    }
</script>
</html>
