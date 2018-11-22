<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String goLink = (String)session.getAttribute("goLink");
	String message = (String)session.getAttribute("message");
	if(StringUtils.isNotBlank(goLink) && StringUtils.isNotBlank(message)){
		%>
		<%=message %><br/>
		<a href="<%=request.getContextPath() %><%=goLink %>">요기로 가세요~</a>
		<%
		session.removeAttribute("goLink");
		session.removeAttribute("message");
	}
	if(new Boolean(true).equals(session.getAttribute("isRemoved"))){
		session.invalidate();
	}
%>
</body>
</html>