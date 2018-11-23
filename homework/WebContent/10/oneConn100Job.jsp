<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/oneConnOneJob.jsp</title>
</head>
<body>
<%
	long startTime = System.currentTimeMillis();
	
		
		try(
			Connection conn = ConnectionFactory.getConnection(); //가져오기위해 한번 컨넥션을 연결
			Statement stmt = conn.createStatement(); //한번 실행한것
		){
			String sql = "SELECT MEM_NAME FROM MEMBER WHERE MEM_ID='a001'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				for(int count = 1; count < 101; count++){
				out.println(rs.getString(1));
				}
			}
		}
	
	long endTime = System.currentTimeMillis();

%>
소요시간 : <%=endTime - startTime %>ms
</body>
</html>