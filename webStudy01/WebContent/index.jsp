<%@page import="kr.or.ddit.web.modulize.ServiceType"%>
<%@page import="kr.or.ddit.web.useragent.leftType"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = (String) session.getAttribute("authMember");

	String cmdParam = request.getParameter("command"); //파라미터를 가져와서 변수에 저장
	int statusCode = 0;
	String includePage = null;
	if (StringUtils.isNotBlank(cmdParam)) {
		try {
			ServiceType sType = ServiceType.valueOf(cmdParam.toUpperCase());
			includePage = sType.getServicePage();
		} catch (IllegalArgumentException e) {
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		}
	}
	if (statusCode != 0) {
		response.sendError(statusCode);
		return;
	}

	// 	leftType left = leftType.getLeftType(command); // enum을 가져와서 값이 든 변수를 넣어 해당 값이 enum에서 비교되어 해당입력에 해당하는 값을 가져온다.

	// 	String url = left.getUrl(); //enum의 값을 get으로 가져와준다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/index.jsp</title>
<link href="<%=request.getContextPath()%>/css/main.css"
	rel="stylesheet">
</head>
<body>
	<div id="top">
		<jsp:include page="/includee/header.jsp" />
	</div>
	<div id="left">
		<jsp:include page="/includee/left.jsp" />
	</div>
	<div id="body">
		<%
			if (StringUtils.isBlank(includePage)) {
				pageContext.include(includePage);
			} else {
		%>
			<h4>웰컴 페이지</h4>
			<pre>
			처음부터 웰컴페이지로 접속하거나 로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음

			<%
				if (StringUtils.isNotBlank(id)) {
			%>
			<%=id%>님 로그인 상태, <a href="<%=request.getContextPath()%>/login/logout.jsp">로그아웃</a>
			
			<%
				} else {
			%>
			<a href="<%=request.getContextPath()%>/login/loginForm.jsp">로그인하러가기</a>
			<%
				}
			%>
			

</pre>
	</div>
	<div id="footer">
		<%
			pageContext.include("/includee/footer.jsp");
		%>
	</div>
</body>
</html>