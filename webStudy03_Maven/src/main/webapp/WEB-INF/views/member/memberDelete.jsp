<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 완료</title>
</head>
<body>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<form action="<%=request.getContextPath() %>/member/memberDelete.do" method="post">
<h6>회원탈퇴가 완료 되었습니다.</h6>
<pre>
안내사항
<%=member.getMem_name() %> 회원님 탈퇴 후 1주일간은 같은 아이디로 가입할수없습니다.
남아 있는 포인트 <%=member.getMem_mileage() %> 점은 소멸되는 점 유의해주길 바랍니다.
그동안 이용해 주셔서 감사합니다.
</pre>
</form>
</body>
</html>