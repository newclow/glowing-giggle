<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.Objects"%>
<%@page import="kr.or.ddit.web.modulize.ServiceType"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   MemberVO authMember = (MemberVO) session.getAttribute("authMember");

   String cmdParam = request.getParameter("command");
   int statusCode = 0;
   String includePage = null;
   if(StringUtils.isNotBlank(cmdParam)){
      try{
         ServiceType sType = ServiceType.valueOf(cmdParam.toUpperCase()); //cmdparam를 대문자로 지정하고  ServiceType에 있는 상수에 그 값이 있으면 주고 없으면 null이된다. 
         includePage = sType.getServicePage(); //위에서 값을 주었을때 get으로 가져와 string변수에 값을 넣어준다.
      }catch(IllegalArgumentException e){
         statusCode = HttpServletResponse.SC_NOT_FOUND;
      }
   }
   if(statusCode!=0){
      response.sendError(statusCode);
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/index.jsp</title>
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous">
</script>
</head>
<body>
   <div id="top">
      <jsp:include page="/includee/header.jsp"></jsp:include>
   </div>
   <div id="left">
      <jsp:include page="/includee/left.jsp"></jsp:include>
   </div>
   <div id="body">
   <%
      if(StringUtils.isNotBlank(includePage)){
         pageContext.include(includePage);
      }else{
         %>
         <h4>웰컴 페이지</h4>
            <pre>
               처음부터 웰컴 페이지로 접속하거나, 
               로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음.
               
                  	 <%
			            if (authMember != null) {
			         %>
			         		<%=authMember.getMem_name()%>님 로그인 상태, <a href="<%=request.getContextPath()%>/login/logout.jsp">로그아웃</a>
			         <%
			            } else {
			         %>
			         		<a href="<%=request.getContextPath()%>/login/loginForm.jsp">로그인하러가기</a>
			         <%
			            }
			         %>
            
            </pre>
         <%
      }
   %>
      




   </div>
   <div id="footer">
      <%
         pageContext.include("/includee/footer.jsp");
      %>

   </div>
</body>
</html>