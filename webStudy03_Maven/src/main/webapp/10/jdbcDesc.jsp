<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.ddit.vo.DataBasePropertyVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc.jsp</title>
</head>
<body>
<h4>JDBC(java DataBase Connectivity)</h4>
<pre>
	데이터베이스 연결 프로그래밍 단계
	1. 드라이버를 빌드패스에 추가
	2. 드라이버 클래스 로딩
	3. 연결 객체(connection) 생성(socket)
	4. 쿼리 객체를 생성
		Statement : 
		PreparedStatement : 
		CallableStatement : 절차적 모듈
	5. 쿼리 실행(CRUD)
		ResultSet executeQuery : Select
		int(실행에 영향을 받은 레코드수) executeUpdate : insert/update/delete
	6. 결과 집합 사용
	7. 자원의 해제 : finally블럭 / try~with~resource구문
	<%
		String[] headers = null;
		List<DataBasePropertyVO> dataList = new ArrayList<>();
		try(
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		){
			StringBuffer sql = new StringBuffer();
			sql.append(" Select * from database_properties "); //앞뒤로 공백을 안주면 다음문장과 연결되어 안된다
			ResultSet rs = stmt.executeQuery(sql.toString()); //
			ResultSetMetaData rsmd = rs.getMetaData(); //컬럼명은 metadata이므로 그걸로 가져옴
			headers = new String[rsmd.getColumnCount()];
			for(int idx = 1; idx <= rsmd.getColumnCount(); idx++){
				headers[idx-1] = rsmd.getColumnName(idx); //조회된 컬럼을 순차적으로 가져옴
		 	}
			
			while(rs.next()){
				//레코드한건 == vo
				DataBasePropertyVO vo = new DataBasePropertyVO();
				vo.setProperty_name(rs.getString("property_name"));
				vo.setProperty_value(rs.getString("property_value"));
				vo.setDescription(rs.getString("description"));
				dataList.add(vo);
			} //while end
		}//try block end
	%>
</pre>
<table>
	<thead>
		<tr>
			<%
				for(String head : headers){
					%>
					<th><%=head %></th>
					<%
				}
			%>
		</tr>
	</thead>
	<tbody>
		<%
			for(DataBasePropertyVO vo : dataList){
				%>
					<tr>
						<td><%=vo.getProperty_name() %></td>
						<td><%=vo.getProperty_value() %></td>
						<td><%=vo.getDescription() %></td>
					</tr>
				<%
			}
		%>
	</tbody>
</table>
</body>
</html>











