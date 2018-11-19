<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chap03/first.jsp</title>
</head>
<body>
<%
	String bookTile = "JSP programming";
	String anthor = "SSW";
%>
<b><%=bookTile %></b><%=anthor %>입니다.
</body>
</html>