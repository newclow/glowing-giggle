package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
@WebServlet("/member/memberDelete.do")
public class MemberDeleteServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		아이디 패스워드가져오고 검증
//		탈퇴진행 (로직만들기 )
//		req.setCharacterEncoding("UTF-8");
//		MemberVO member = new MemberVO();
//		req.setAttribute("member", member);
//		try {
//			BeanUtils.populate(member, req.getParameterMap());
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new CommonException(e);
//		}
		
		String mem_id = req.getParameter("mem_id");
		String mem_pass = req.getParameter("mem_pass");
		if (StringUtils.isBlank(mem_id) || StringUtils.isBlank(mem_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		
		
		String goPage = null;
		String message = null;
		boolean redirect = false;
//		Map<String, String> errors = new LinkedHashMap<>();
//		req.setAttribute("errors", errors);
		
		IMemberService service = new MemberServiceImpl();
		ServiceResult result = service.removeMember(new MemberVO(mem_id, mem_pass));
		switch (result) {
		case OK:
//			goPage = "/member/memberList.do";
			goPage = "/common/message.jsp";
			message = "탈퇴약관:일주일이내에는 같은아이디로 가입불가";
			req.getSession().setAttribute("goLink", "/");
			req.getSession().setAttribute("isRemoved", new Boolean(true));
//			req.getSession().invalidate(); //세션을 만료하여 로그아웃되게 만듬
			redirect = true;
			break;
		case FAILED:
//			goPage = "/member/memberView.do?who="+mem_id;
			goPage = "/member/mypage.do";
			message = "회원탈퇴에 실패했습니다.";
			redirect = true;
			break;
		case INVALIDPASSWORD:
//			goPage = "/member/memberView.do?who="+mem_id;
			goPage = "/member/mypage.do";
			message = "패스워드가 틀렸습니다.";
			redirect = true;
			break;
		}
		req.getSession().setAttribute("message", message);
		if (redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
		
		
	}
}
