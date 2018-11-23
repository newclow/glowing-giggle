package kr.or.ddit.member.service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements IAuthenticateService {
	IMemberDAO memberDAO = new MemberDAOImpl(); // 의존객체를 생성
	
	
	@Override//아이디가 있는경우, 아이디는 맞는데 비번이 틀린경우, 성공한경우
	public Object authenticate(MemberVO member) { //인증여부를 판단하는 로직개체
		Object result = null;
		MemberVO savedMember = memberDAO.selectMember(member.getMem_id()); //memDAO의 selectmember에 memberVO의 id값을 준다 >memberDAO로
		if (savedMember != null) {	//값이 null이 아니고
			if (savedMember.getMem_pass().equals(member.getMem_pass())) {	//비밀번호가 memberVO에서의 값과 같으면
				result = savedMember;	//result에 넣는다.
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
		}else {
			result = ServiceResult.PKNOTFOUND;
		}
		return result;
	}

}
