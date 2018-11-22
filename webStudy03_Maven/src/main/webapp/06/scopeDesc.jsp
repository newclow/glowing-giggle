<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/scopeDesc.jsp</title>
</head>
<body>
<h4>Scope(영역)</h4>
<pre>
	: 웹어플리케이션에서 데이터를 공유할때 사용할수 있는 메모리
	: 각 영역은 해당 영역을 제어할때 기본객체를 사용하고 그 기본객체와 동일한 생명주기를 가짐
		속성(attribute) : scope를 통해 공유되는 데이터(이름:값)
	1. page Scope (pageContext) : 하나의 JSP페이지 내에서 유효한 영역
	2. request Scope (request) : 하나의 request와 동일 생명주기로 관리되는 영역.  dispatch방식으로 이용할때는 page보다 더 넓게 활용가능
	3. session Scope (session) : 한 세션내에서 관리되는 영역
	4. application Scope (application) : 웹어플리케이션에서 전역 데이터 관리 영역
	<%
		pageContext.setAttribute("pageContextAttr", "페이지영역속성"); // 그 페이지만 사용
		request.setAttribute("requestAttr", "리퀘스트 영역 속성"); //적절한 생명주기 즉 응답하면 사라짐
		session.setAttribute("sessionAttr", "세션 영역 속성");	//모든 세션에서 동일한 스코프를 사용한다. //로그인하여 자신만 볼때 //자신만 보는용도
		application.setAttribute("applicationAttr", "어플리케이션 영역 속성");   //모든 이용자가 볼때
		
// 		pageContext.forward("/05/destination.jsp");
		response.sendRedirect(request.getContextPath()+"/05/destination.jsp");
	%>
	<%=pageContext.getAttribute("pageContextAttr") %>
	<%=request.getAttribute("requestAttr") %>
	<%=session.getAttribute("sessionAttr") %>
	<%=application.getAttribute("applicationAttr") %> 
	
</pre>
</body>
</html>