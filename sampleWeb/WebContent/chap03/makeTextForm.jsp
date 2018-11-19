<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>form create</title>
</head>
<body>
폼에 데이터를 입력한후 '전송'버튼을 클릭하세요
<form action="/chap03/viewParameter.jsp" method="post"><%--입력할 데이터를 전달할 페이지와 방식을 지정 --%>
이름 : <input type="text" name="name" size="10"><br>
주소 : <input type="text" name="addr" size="20"><br>
좋아하는 동물 :
	<input type="checkbox" name="pet" value="dog">강아지 <%--이름이 같으면 그룹이됨 --%>
	<input type="checkbox" name="pet" value="cat">고양이
	<input type="checkbox" name="pet" value="pig">돼지
	<br>
	<input type="submit" value="전송">
</form>
</body>
</html>