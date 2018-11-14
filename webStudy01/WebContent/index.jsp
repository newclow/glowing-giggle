<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = (String)session.getAttribute("authMember");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/index</title>
</head>
<body>
<h4>웰컴 페이지</h4>
<pre>
	처음부터 웰컴페이지로 접속하거나 
	로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음
	<%
		if(StringUtils.isNotBlank(id)){
	%>
	<%=id %>님 로그인 상태, <a href="<%=request.getContextPath() %>/login/logout.jsp">로그아웃</a>
	<%
		}else{
	%>
		<a href="<%=request.getContextPath() %>/login/loginForm.jsp">로그인하러 가기</a>
	<%
		}
	%>
</pre>
</body>
</html>