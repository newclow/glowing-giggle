<%@page import="kr.or.ddit.utils.CookieUtil"%>
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
	<% 
		String idCookieValue = new CookieUtil(request).getCookieValue("idSave");  //쿠키를 가져오는 것
	%>
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/login/loginCheck.jsp" method="post">
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="<%=StringUtils.isNotBlank(idCookieValue) ? idCookieValue : Objects.toString(failedId, "") %>" />
			<%--쿠키값을 가져와서 쿠키가 null이 아니거나 whitespaces가 아니면 idCookieValue값이 들어오고 null이거나 whitespaces면 failedId를 주거나 null이면 ""을 준다.  --%> 
			<label>
				<input type="checkbox" name="idChecked" value="idSaved" <%=StringUtils.isNotBlank(idCookieValue)? "checked": "" %> />아이디 기억하기 
			<%-- 체크를 하면 값이 3개가 넘어가고 안하면 값이 2개가 넘어간다 --%>
			
		</li>
		<li>
			비밀번호 : <input type="password" name="mem_pass" />
			<input type="submit" value="로그인" /> 
		</li>
	</ul>
</form>
</body>
</html>