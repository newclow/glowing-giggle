<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String failedId = request.getParameter("id");
	String message = (String)session.getAttribute("message"); //error속성을 가져오는데 값으로 1을 줬으므로 Integer로 캐스팅한다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>
<script type="text/javascript">
	<%
		if(StringUtils.isNotBlank(message)){	//Integer이 숫자이므로 null만 비교 trim삭제
	%>
		alert("<%=message %>");
	<%
			session.removeAttribute("message"); //에러의 속성을 지워준다
		}
	%>
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/login/loginCheck.jsp" method="post">
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="<%=Objects.toString(failedId, "") %>" /> 
		</li>
		<li>
			비밀번호 : <input type="password" name="mem_pass" />
			<input type="submit" value="로그인" /> 
		</li>
	</ul>
</form>
</body>
</html>