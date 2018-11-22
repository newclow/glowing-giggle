package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")
public class MemberInsertServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "/WEB-INF/views/member/memberForm.jsp";
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        MemberVO member = new MemberVO();
        req.setAttribute("member", member);
//        member.setMem_id(req.getParameter("mem_id")); //beanutils를 사용하면 일일이 입력안해도됨
        try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
        String goPage = null;
        boolean redirect = false;
        String message = null;
        Map<String, String> errors = new LinkedHashMap<>();
        req.setAttribute("errors", errors);
        boolean valid = validate(member, errors);
        if(valid){
           IMemberService service = new MemberServiceImpl();
           ServiceResult result = service.registMember(member);
           switch(result){
              case PKDUPLICATED :
                 goPage = "/WEB-INF/views/member/memberForm.jsp";
                 message = "아이디 중복, 바꾸셈";
                 break;
              case FAILED :
                 goPage = "/WEB-INF/views/member/memberForm.jsp";
                 message = "서버 오류로 인한 실패, 잠시 뒤 다시 하셈";
                 break;
              case OK :
                 goPage = "/member/memberList.do";
                 redirect = true;
                 break;
           }
           req.setAttribute("message", message);
        }else{
           goPage = "/WEB-INF/views/member/memberForm.jsp";
        }
        if(redirect){
           resp.sendRedirect(req.getContextPath()+goPage);
        }else{
           RequestDispatcher rd = req.getRequestDispatcher(goPage);
           rd.forward(req, resp);
        }

	}
	
	private boolean validate(MemberVO member, Map<String, String> errors){
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
	
	
}
