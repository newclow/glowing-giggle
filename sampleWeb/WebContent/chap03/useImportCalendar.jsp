<%@page import="java.util.Calendar"%>
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
	Calendar cal = Calendar.getInstance();
%>
오늘은 
	<%=cal.get(Calendar.YEAR) %>년
	<%=cal.get(Calendar.MONTH) +1 %>월 <%--월은 0부터 시작해서 +1을 해줘야한다 --%>
	<%=cal.get(Calendar.DAY_OF_MONTH) %>일
입니다
</body>
</html>