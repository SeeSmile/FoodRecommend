<%@page import="helper.AprioriHelper"%>
<%@page import="data.HaodouEntity"%>
<%@page import="db.HaodouDB"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
    	String id = request.getParameter("id");
    	HaodouDB db = new HaodouDB();
    	HaodouEntity entity = db.getDetailFood(id);
    	List<Set<String>> jj = AprioriHelper.getRecommend(id);
    	if(jj.size() > 0) {
     %>
     	<%=jj.get(jj.size() - 1).toString() %>
     <% } else { %>
    <a>暂无推荐</a>
     <% } %>
     
  </body>
</html>
