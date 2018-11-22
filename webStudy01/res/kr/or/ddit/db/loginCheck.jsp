<%@page import="kr.or.ddit.member.service.AuthenticateServiceImpl.ServiceResult"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.member.service.AuthenticateServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IAuthenticateService"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="kr.or.ddit.utils.CookieUtil.TextType"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="kr.or.ddit.utils.CookieUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 1. 파라미터 확보 -->
<!-- 2. 검증(필수 데이터) -->
<!-- 3. 불통 -->
<!-- 	이동(loginForm.jsp, 기존에 입력했던 아이디를 그래도 전달할 수 있는 방식)  -->
<!-- 4. 통과 -->
<!-- 	4-1. 인증(아이디 ==비번) -->
<!-- 		4-2. 인증 성공 : 웰컴페이지로 이동(원본 요청을 제거하고 이동) -->
<!-- 		4-3. 인증 실패 : 이동(loginForm.jsp, 기존에 입력했던 아이디를 그대로 전달) -->
<%
	request.setCharacterEncoding("UTF-8"); //처음에 특수문자를 생각해 인코딩해야됨
	String id = request.getParameter("mem_id");
	String pass = request.getParameter("mem_pass");
	String idSaved = request.getParameter("idChecked");
	String goPage = null;	//가야될페이지
	boolean redirect = false; //true시 redirect진행
	if(id == null || id.trim().length() == 0 || id.matches("^[A-Za-z0-9]$") || pass == null || pass.trim().length()==0){
		goPage = "/login/loginForm.jsp"; //if문에 해당 될시 로그인폼으로 돌아간다
		redirect = true; //돌아갈시 입력된 아이디를 redirect형식으로 응답해준다.
		session.setAttribute("message", "아이디나 비번 누락"); //속성으로 넣어주며 메시지에 들어갈 value를 입력한다.
	} else{
		IAuthenticateService service = new AuthenticateServiceImpl();	//서비스를 선언하여 서비스구현 객체를 가져온다
		Object result = service.authenticate(new MemberVO(id, pass)); //선언된 서비스의 메서드에 id와 pass 값을 넣어 해당 결과값을 가져온다 >serviceimpl로
		if(result instanceof MemberVO){ //위의 if문에 해당안될시 진행되며 id와 pass가 같으면 진행
			goPage = "/"; //contentpath에서 진행되며(지정한 시작점)
			redirect = true; //redirect형식으로 진행
			session.setAttribute("authMember", result); //속성 authMember를 value값으로 id를주고
			int maxAge = 0;	//만료시간 선언
			if(StringUtils.isNotBlank(idSaved)){ //null이 아니거나 whitespaces가 없으면
				maxAge = 60*60*24*7; //만료시간을 7일설정
			}
			Cookie cookie = CookieUtil.createCookie("idSave", id , request.getContextPath(), TextType.PATH, maxAge); //쿠키를 만들어준다 (이름 지정,value값으로 id,contextpath를 지정, path(/)를지정, 만료기간지정)
			response.addCookie(cookie); //쿠키를 추가한다
		}else if(result == ServiceResult.PKNOTFOUND){//인증실패
			goPage = "/login/loginForm.jsp"; 
			redirect = true; //redirect형식으로 진행
			session.setAttribute("message", "존재하지 않는 회원"); //속성으로 메시지를 인증 실패를 준다.
		}else{
			goPage = "/login/loginForm.jsp"; 
			redirect = true; //redirect형식으로 진행
			session.setAttribute("message", "비번 오류로 인증 실패"); //속성으로 메시지를 비번오류로 인증 실패를 준다.
		}
	}
		
		
	if(redirect){
		response.sendRedirect(request.getContextPath()+ goPage); //원본 요청을 제거 하므로 redirect를 이용
	}else{
		RequestDispatcher rd = request.getRequestDispatcher(goPage); //디스패쳐로 진행되며
		rd.forward(request, response); //포워드형식으로 진행
	}
	
// 	if(id == null || id.trim().length() == 0 || id.matches("^[A-Za-z0-9]$") || pass == null || pass.trim().length()==0){
// 		RequestDispatcher rd = request.getRequestDispatcher("/login/loginForm.jsp");
// 		rd.forward(request, response);
// 		return;
// 	}
// 	if(id.equals(pass)){
// 		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
// 		rd.forward(request, response);
// 		return;
// 	} else if(!id.equals(pass) || id == null){
// 		RequestDispatcher rd = request.getRequestDispatcher("/login/loginForm.jsp");
// 		rd.forward(request, response);
// 		return;
// 	}
	
	
%>