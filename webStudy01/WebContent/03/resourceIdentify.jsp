<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/resourceIdentify.jsp</title>
</head>
<body>
<h4>자원의식별</h4>
<pre>
해당 자원을 접근하는방법
1. 파일 시스템자원 : 파일시스템 경로를 통한 식별
2. 클래스패스(classpath) 자원 : classpath기준 경로를 통한 식별 (식별을 지정해서 경로설정하는것)
3. 웹리소스 식별(URI, URL로 접근하는것)
	URI(Uniform Resource Indentifier) : 소스에게 식별을 주는것
		URL(Uniform Resource Locator) : 소스의 위치를 기준으로
		URN(Uniform Resource Name) : 소스의 이름을 기준으로 
		URC(Uniform Resource Content) : 소스의 내용을 기준으로 (출판사, 작가 등등)
	URL
	scheme://domain:port/context/depth1/deth2..../resource_name
	
	절대경로 : http://localhost:80/webStudy01/images/Penguins.jpg
		client-side : /webStudy01/images/Penguins.jpg
		server-side : /desc(contextPath를 제외한 이후 경로 표기)
	상대경로 : 경로를 판단할 기분 위치 : 현재 브라우저의 주소값
		../images/Penguins.jpg
</pre>
	<img src="<%=request.getContextPath() %>/images/Penguins.jpg" />
	<img src="http://localhost:80/webStudy01/images/Penguins.jpg" />
	<img src="../images/Penguins.jpg" />
</body>
</html>