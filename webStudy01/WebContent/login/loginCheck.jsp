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
	String goPage = null;
	boolean redirect = false;
	if(id == null || id.trim().length() == 0 || id.matches("^[A-Za-z0-9]$") || pass == null || pass.trim().length()==0){
		goPage = "/login/loginForm.jsp";
		redirect = true;
		session.setAttribute("message", "아이디나 비번 누락");
	} else{
		if(id.equals(pass)){
			goPage = "/";
			redirect = true;
			session.setAttribute("authMember", id);
		}else{//인증실패
			goPage = "/login/loginForm.jsp?error=1";
			redirect = true;
			session.setAttribute("message", "비번 오류로 인증 실패");
		}
	}
	if(idSaved != null){ //idsaved 체크시 null이 아닐때 쿠키값을 만들어서 추가해줌
		String idCookieValue = new CookieUtil(request).getCookieValue("id"); //이거모름 노이해
		Cookie cookie = CookieUtil.createCookie("idSave", idCookieValue , 
				request.getContextPath(), TextType.PATH, 60*60*24*7);
		response.addCookie(cookie);
	}
	if(redirect){
		response.sendRedirect(request.getContextPath()+ goPage); //원본 요청을 제거 하므로 redirect를 이용
	}else{
		RequestDispatcher rd = request.getRequestDispatcher(goPage);
		rd.forward(request, response);
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