<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>기본객체(내장객체)</h4>
<pre>
	**pageContext(PageContext) : jsp페이지와 관련된 모든 정보를 가진 객체, 제일 먼저 생성되는 객체, 모든정보를 가지고 있다(기본객체포함: 즉 이걸로 다른객체를 만듬), 생명주기가 명확
	request(HttpServletRequest) : 요청과 클라이언트에 대한 정보를 캡슐화한 객체, stateless로 생성시 요청이 사라짐, 생명주기가 명확
	response(HttpServletResPonse) : 응답과 관랸된 모든 정보를 캡슐화한 객체
	out(JSPWriter) : 출력버퍼에 데이터를 기록하거나 버퍼를 제어하기 위해 사용되는 출력스트림
	session(HttpSession) : 한세션내에서 발생하는 모든 정보를 캡슐화한 객체, 하나의 어플리케이션의 시작하고나서 종료할떄 사라짐, 생명주기가 명확
	application(ServletContext) : 컨텍스트(어플리케이션)와 서버에 대한 정보를 가진 객체, 가장 범위가 큰녀석이며 서버가 켜지는순간 켜지고 셧다운되면 꺼진다(제일 오래산다), 생명주기가 명확
	<%=application.hashCode() %>
	config(ServletConfig) : 서블릿 등록과 관련된 정보를 가진 객체 , jsp가 서블릿이라는 증거
	page(Object) : 현재 JSP페이지에 대한 래퍼런스 , was가 만들어논 singleton이라는것  this와 비슷하여 this를 쓰는게 나음
	exception(Throwable) : 발생한 예외에 대한 정보를 가진 객체
		:예외(에러)가 발생한 경우 에러를 처리하는 페이지에서 사용됨(page지시자의 isErrorPage로 활성화함)
	<%=exception %>
</pre>
</body>
</html>