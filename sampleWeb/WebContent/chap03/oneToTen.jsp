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
	int sum = 0;
	for(int i = 1; i < 11; i++){
		sum += i;
	}

%>
<%=sum %>
<%
	int sum2 = 0;
	for(int i = 11; i < 21; i++){
		sum2 += i;
	}
%>
<%= sum2 %>
</body>
</html>