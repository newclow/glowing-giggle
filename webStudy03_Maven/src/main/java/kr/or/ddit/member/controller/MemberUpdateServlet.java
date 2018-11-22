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
import org.apache.commons.lang3.Validate;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		요청 검증
//		통과
//		불통시 memberview.jsp로돌아감 각상황마다 에러메시지와 입력했던 기존데이터
//		수정성공(redirect) 사항과 수성실패(dispetcher)시 memberview가는 방식이 다르다  
		req.setCharacterEncoding("UTF-8");
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		String goPage = null;
		String message = null;
		boolean redirect = false;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(member, errors);
		if (valid) {
			IMemberService service = new MemberServiceImpl();
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case OK:
//				goPage = "/member/memberView.do?who="+member.getMem_id();    //redirect형식으로 server에서 server로 갈수 없어 (/WEB-INF/는 serverSide) webservlet으로 형식을 지정해주고 그 형식을 불러서 이동한다
				goPage = "/member/mypage.do";
				redirect = true;					
				break;
			case FAILED:
				goPage = "/WebContent/WEB-INF/views/member/memberView.jsp"; //디스패치형식이므로 server에서 server로 갈수있어서 이런형식임
				message = "수정에 실패했습니다.";
				break;
			case INVALIDPASSWORD:
				goPage = "/WebContent/WEB-INF/views/member/memberView.jsp";
				message = "비밀번호가 같지 않습니다.";
				break;
			}
			req.setAttribute("message", message);
		}else {
			goPage = "/WebContent/WEB-INF/views/member/memberView.jsp";
		}
		if (redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
	}
	
	private boolean validate(MemberVO member, Map<String, String> errors){
		boolean valid = true;
		//검증룰
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
		if (StringUtils.isBlank(member.getMem_hometel())) {
			valid = false;
			errors.put("mem_hometel", "집전화 누락");
		}
		if (StringUtils.isBlank(member.getMem_comtel())) {
			valid = false;
			errors.put("mem_comtel", "회사전화 누락");
		}
		if (StringUtils.isBlank(member.getMem_hp())) {
			valid = false;
			errors.put("mem_hp", "휴대폰 누락");
		}
		if(StringUtils.isBlank(member.getMem_mail())){
       		 valid = false;
      		  errors.put("mem_mail", "이메일 누락");
        }
		if(StringUtils.isBlank(member.getMem_job())){
			valid = false;
			errors.put("mem_job", "직업 누락");
		}
		if(StringUtils.isBlank(member.getMem_like())){
			valid = false;
			errors.put("mem_like", "취미 누락");
		}
		if(StringUtils.isBlank(member.getMem_memorial())){
			valid = false;
			errors.put("mem_memorial", "기념일 누락");
		}
		if(StringUtils.isNotBlank(member.getMem_memorialday())){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			//formatting : 특정타입의 데이터를 일정형식의 문자열로 변환.
			//parsing : 일정한 형식의 문자열을 특정타입의 데이터로 변환.
			try{
				formatter.parse(member.getMem_memorialday());
			}catch(ParseException e){
				valid = false;
				errors.put("mem_memorialday","날짜형식확인");		
			}
		}
		return valid;
	}
}
