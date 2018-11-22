<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%>
<%@page import="kr.or.ddit.vo.AlbasengVO"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/albaList.jsp</title>
</head>
<body>
<table>
	<thead>
		<tr>
			<th>알바생코드</th>
			<th>이름</th>
			<th>주소</th>
			<th>연락처</th>
		</tr>
	</thead>
	<tbody>
		<%
		
			//규연이가 넣은거
			Map<String, AlbasengVO> alba = (Map<String, AlbasengVO>)getServletContext().getAttribute("albasengs"); //속성에 있는것들중 albasengs에 해당하는 것을 가져와 alba에 Map형식으로 선언해준다
			for(Entry<String, AlbasengVO> entry : alba.entrySet()){	//for문으로 작동하여 entry에서 해당 값의 코드에해당되는 이름 주소 연락처를 HTML형식으로 표시해준다.
				%>
					<tr>
						<td><%=entry.getKey() %></td>
						<td><%=entry.getValue().getName() %></td>
						<td><%=entry.getValue().getAddress() %></td>
						<td><%=entry.getValue().getTel() %></td>
					<tr>
				<%
			}
		%>
<%-- <%-- 				<% --%>
<!-- // 					Map<String, AlbasengVO> alba = SimpleFormProcessServlet.albasengs; //SimpleFormProcessServlet에서 가져온다. -->
<!-- // 					String pattern = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>"; -->
<!-- // 					for(Entry<String, AlbasengVO> entry : alba.entrySet()){ -->
<!-- // 						out.println(String.format(pattern, entry.getValue().getCode(),  -->
<!-- // 								entry.getValue().getName(), -->
<!-- // 								entry.getValue().getAddress(), -->
<!-- // 								entry.getValue().getTel())); -->
<!-- // 					} -->
<%-- <%-- 				%> --%>
	</tbody>
</table>
</body>
</html>