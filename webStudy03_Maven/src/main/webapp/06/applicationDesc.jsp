<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/application.jsp</title>
</head>
<body>
<h4>ServletContext</h4>
<pre>
	<%=application.hashCode() %>
	<a href="<%=request.getContextPath() %>/06/implicitObject.jsp">implicitObject.jsp 로 이동</a>
	<a href="<%=request.getContextPath() %>/desc">DescriptionServlet.jsp 로 이동</a>
	: 컨텍스트와 해당 컨텍스트를 운영 중인 혹인 관리중인 서버뎅 대한 정보를 가진 객체
	1. 서버에 대한 정보 획득
	<%=application.getServerInfo()%>
	<%=application.getMajorVersion() %>.<%=application.getMinorVersion() %> <%--현재 서버의 서블릿버전을 확인하는 코드 --%>
	<%=application.getMimeType("test.hwp") %>
	2. 로그기록(logging)
	<%
		application.log("명시적으로 기록한 로그 메시지"); //log4j를 사용하여 서버의 로그는 사용안함
	%>
	3. 컨텍스트 파라미터(어플리케이션의 초기화 파라미터) 획득(파라미터란 명령을 하는자가 명령을 수행하는자에게 명령을 내릴때 넘겨주는 데이터)
	(web.xml에서 파라미터를 넣어줄수있음)
		<%=application.getInitParameter("param1") %>
		<%
			Enumeration<String> names = application.getInitParameterNames();
			while(names.hasMoreElements()){
				out.println(application.getInitParameter(names.nextElement()));
			}
		%>
	4. 웹리소스를 획득(시작점은 도메인, webContent) : http://localhost/webStudy01/images/Penguins.jpg
		<%=application.getRealPath("/images/Penguins.jpg") %>
		<%
 			String fileSystemPath = application.getRealPath("/images/Penguins.jpg");
 			File srcfile = new File(fileSystemPath);
			File targetFolder = new File(application.getRealPath("/06"));
 			File targetFile = new File(targetFolder, srcfile.getName());
 			System.out.println(targetFile.exists());
			int pointer = -1;
			byte[] buffer = new byte[1024];
			try(
// 				FileInputStream fis = new FileInputStream(srcfile);
				InputStream fis = application.getResourceAsStream("/images/Penguins.jpg");
				FileOutputStream fos = new FileOutputStream(targetFile);
				
			){
				while((pointer = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, pointer);
				}
			}
			System.out.println(targetFile.exists());
// 			try{
// 				FileInputStream fis = new FileInputStream(srcfile);
// 				FileOutputStream fos = new FileOutputStream("/06/Penguins.jpg");
				
// 			}catch(Exception e){
// 				e.printStackTrace();
// 			}
		%>
</pre>
<img src="<%=request.getContextPath() %>/06/Penguins.jpg" />
<img src="<%=request.getContextPath() %>/images/Penguins.jpg" />
</body>
</html>