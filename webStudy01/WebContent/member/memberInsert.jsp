<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.ServiceResult"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.dao.MemberDAOImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 1. 파라미터확보(특수문자 고려) -->
<!-- 2. 검증(검증룰 : member테이블의 스키마를 확인, 필수데이터 검증 반드시!) -->
<!-- 3. 통과 -->
<!-- 	1) 로직객체와 의존관계형성 -->
<!-- 	2) 로직선택(registMember) -->
<!-- 		PKDUPLICATED : memberForm.jsp로 이동,forward(메세지, 기존 입력데이터 공유) -->
<!-- 		OK : memberList.jsp로 이동 redirect -->
<!-- 		FAILED : memberForm.jsp로 이동,forward(메세지, 기존 입력데이터 공유) -->
<!-- 4. 불통 -->
<!-- 	memberForm.jsp로 이동,forward(검증결과 메세지, 기존 입력데이터 공유) -->
<%!private boolean validate(MemberVO member, Map<String, String> errors){
		boolean valid = true;
		//검증룰
		if(StringUtils.isBlank(member.getMem_id())){
        	valid = false;errors.put("mem_id", "회원아이디 누락");
        }
		if(StringUtils.isBlank(member.getMem_pass())){
		    valid = false;
		    errors.put("mem_pass", "비밀번호 누락");
		   }
		if(StringUtils.isBlank(member.getMem_name())){
		        valid = false;
		        errors.put("mem_name", "회원명 누락");
		        }
		if(StringUtils.isBlank(member.getMem_zip())){
		        valid = false;
		        errors.put("mem_zip", "우편번호 누락");
		        }
		if(StringUtils.isBlank(member.getMem_add1())){
		        valid = false;
		        errors.put("mem_add1", "주소1 누락");
		        }
		if(StringUtils.isBlank(member.getMem_add2())){
		        valid = false;
		        errors.put("mem_add2", "주소2 누락");
		        }
		if(StringUtils.isBlank(member.getMem_mail())){
       		 valid = false;
      		  errors.put("mem_mail", "이메일 누락");
        }
		if(StringUtils.isNotBlank(member.getMem_bir())){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			//formatting : 특정타입의 데이터를 일정형식의 문자열로 변환.
			//parsing : 일정한 형식의 문자열을 특정타입의 데이터로 변환.
			try{
				formatter.parse(member.getMem_bir());
			}catch(ParseException e){
				valid = false;
				errors.put("mem_bir","날짜형식확인");		
			}
		}
		return valid;
}
%>

<%
	request.setCharacterEncoding("UTF-8");

%>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<jsp:setProperty property="*" name="member" />
<%
	String goPage = null;
	boolean redirect = false;
	String message = null;
	Map<String, String> errors = new LinkedHashMap<>();
	request.setAttribute("errors", errors);
	boolean valid = validate(member, errors);
	if(valid){
		IMemberService service = new MemberServiceImpl();
		ServiceResult result = service.registMember(member);
		switch(result){
		case PKDUPLICATED:
			goPage = "/member/memberForm.jsp";
			message = "아이디 중복";
			break;
		case FAILED :
			goPage = "/member/memberForm.jsp";
			message = "서버 오류로 인한 실패 다시 접속해주세요";
			break;
		case OK :
			goPage = "/member/memberList.jsp";
			redirect = true;
			break;
		}
		request.setAttribute("message", message);
	} else{
		goPage = "/member/memberForm.jsp";
	}
	if(redirect){ //구조를 먼저 만든후 세부적인 코딩을 하자
		response.sendRedirect(request.getContextPath()+goPage);
	}else{
		RequestDispatcher rd = request.getRequestDispatcher(goPage);
		rd.forward(request, response);
	}

%>