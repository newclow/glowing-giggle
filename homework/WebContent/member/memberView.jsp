<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 	누구인지 파라미터잡고
// 	있는지 없는지확인해서 요청이 잘못되면 400번에러
// 	그사람이 있는지 가져와서 ui로만든다
	String mem_id = request.getParameter("who");
	
	IMemberService service = new MemberServiceImpl();
	MemberVO member = service.retrieveMember(mem_id);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberView.jsp</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<table class="table">
	<thead class="thead-dark">
		<tr>
			<th>회원아이디</th>
			<th>회원명</th>
			<th>생일</th>
			<th>우편번호</th>
			<th>주소</th>
			<th>집전화번호</th>
			<th>회사전화번호</th>
			<th>휴대폰</th>
			<th>이메일</th>
			<th>직업</th>
			<th>취미</th>
			<th>기념일</th>
			<th>기념일자</th>
			<th>마일리지</th>
		</tr>
	</thead>
	<tbody>
				<tr>
					<td><%=member.getMem_id() %></td>
					<td><%=member.getMem_name() %></td>
					<td><%=member.getMem_bir() %></td>
					<td><%=member.getMem_zip() %></td>
					<td><%=member.getAddress() %></td>
					<td><%=member.getMem_hometel() %></td>
					<td><%=member.getMem_comtel() %></td>
					<td><%=member.getMem_hp() %></td>
					<td><%=member.getMem_mail() %></td>
					<td><%=member.getMem_job() %></td>
					<td><%=member.getMem_like() %></td>
					<td><%=member.getMem_memorial() %></td>
					<td><%=member.getMem_memorialday() %></td>
					<td><%=member.getMem_mileage() %></td>
				</tr>
	</tbody>
</table>
</body>
</html>