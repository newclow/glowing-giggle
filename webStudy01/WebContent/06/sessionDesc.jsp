<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/sessionDesc.jsp</title>
</head>
<body>
<h4>HttpSession</h4>
<pre>
	한세션 내의 모든 정보를 가진 객체를
	세션??(시간과 통로)
		통로 : 클라이언트와 서버사이에 유효한 데이터가 전송될수 있는 연결
		시간 : 클라이언트가 어플리케이션 사용하기 시작한 순간부터 사용종료 이벤트를 발생시킬때까지의 시간
		
		사용시작 : 클라이언트로부터 최초의 요청이 발생했을때 -> session객체생성
			**세션의 대상은 브라우저(즉 사람이 아니라 에이전트)
		사용종료:
			1) 명시적인 로그아웃
			2) 세션 만료시간 이내에 새로운 요청이 발생하지 않을때
			3) 브라우저를 완전히 종료한 경우
		세션 아이디 : <%=session.getId() %>
		세션 생성 시점 : <%=new Date(session.getCreationTime()) %>
		마지막 접속 시간 : <%=new Date(session.getLastAccessedTime()) %>
		세션 만료시간 : <%=session.getMaxInactiveInterval() %>s
		<%
			session.setMaxInactiveInterval(4*60);
		%>
		세션 만료시간 조정 후 : <%=session.getMaxInactiveInterval() %>s
		<a href="sessionDesc.jsp;jsessionid=<%=session.getId() %>">쿠키 없는 상태에서 세션 유지</a>
</pre>
</body>
</html>