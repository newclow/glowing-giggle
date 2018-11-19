<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청파라미터출력</title>
</head>
<body>
<b>getParameter사용</b><br>
name = <%=request.getParameter("name") %><br>
addr = <%=request.getParameter("addr") %><br>
<b>getParameterValues사용</b>
<%
	String[] pets = request.getParameterValues("pet");
	if(pets != null){
		for(String pet: pets){
			%>
			<%=pet %>
			<%
		}
	}
%>

</body>
</html>