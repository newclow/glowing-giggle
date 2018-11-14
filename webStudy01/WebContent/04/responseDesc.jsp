<%@page import="java.util.Calendar"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
//	response.setHeader("Content-Type", "text/html;charset=UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04/responseDesc.jsp</title>
</head>
<body>
<h4>Http Response</h4>
<pre>
	Http의 response 패키징방식
	1) Response Line : Response Status code, protocol/ver
		-100 : ing...(처리중) HTTP1.1부터 하위 프로토콜로 websocket이 사용됨(connectful)
			**HTTP : Connectless, Stateless
		-200 : ok, success(정상처리)
		-300 : 클라이언트의 추가 액션이 필요한 경우
			304(not modified) : 캐시라는 데이터가 브라우저상에 저장
			302/307(moved) : 변경된 위치로 새로운 요청을 발신시킬때
		-400 : client 쪽 문제로 처리 실패 
			404(not found), 400(Bad Request) , 405(Method not allowed), 
			401(UnAuthroized), 403(Forbidden) : 보안쪽
		-500 : server 쪽 문제로 처리 실패 
			500(Internal Server Error) : 서버쪽 문제를 클라이언트에 보내면 안되기때문에 1개만있음
	2) Response Header : 서버나 응답데이터에 대한 부가정보(metadata)
		response header name : header value
		
	3) Response Body(Message body) : 응답 컨텐츠
	
	**HttpServletResponse를 통한 응답 제어
		: 서버에서 클라이언트로 전송되는 응답에 대한 모든데이터르 가진 객체
		1) 응답데이터를 전송하기 위한 출력 스트림 확보
			char stream(printWriter) getWriter(), 
			byte stream(ServletOutputStream) getOutPutStream(),
		2) setStatus(status_code) : 200/300 사용
			sendError(status_code) : 400/500 사용, 서버의 에러페이지로 자동연결
		3) set[add]Header(header_name, header_value)
		   set[add]IntHeader(header_name, header_value)
		   set[add]DateHeader(header_name, long header_value)
		
		    응답헤더를 설정하는 경우
		   a) MIME설정 : Content-Type
		   		setHeader, setContentTypem page 지시자의 contentType속성
		   b) cache제어 : Cache-Control(HTTP/1.1), Pragma(HTTP/1.0), Expires(모든버전) 
		   <%
		   //캐시를 저장하지 않도록 설정
// 		   		response.setHeader("Pragma", "no-cache"); //캐시를 저장안해서 은행업무등을 했을때 내역이 안남게한다
// 		   		response.setHeader("Cache-control", "no-cache");
// 		   		response.addHeader("Cache-control", "no-store");
// 		   		response.setDateHeader("Expires", 0);
			//캐시를 저장하도록 설정
				int maxAge = 60*60*24*7;
				response.setHeader("pragma", "public;max-age="+maxAge);
				response.setHeader("Cache-control", "public;max-age="+maxAge);
				Calendar today = Calendar.getInstance();
				today.add(Calendar.SECOND, maxAge);	
				response.setDateHeader("Expires", today.getTimeInMillis());
		   %>
		   c) auto request : Refresh
		   d) page move(flow control) : Location
</pre>
</body>
</html>