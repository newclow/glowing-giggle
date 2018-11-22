package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	
	//의존객체를 직접 생성하는 방식 : 결합력 최상
	IMemberDAO memberDAO = new MemberDAOImpl();
	
	//id의 중복여부를 확인, 중복되지 않으면 insert를 시작, 성공시 ok나 failed를 준다
	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = null;
		if (memberDAO.selectMember(member.getMem_id()) == null ) {
			int cnt = memberDAO.insertMember(member); 
			if (cnt > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAILED;
			}
		} else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}

	@Override
	public List<MemberVO> retrieveMemberList() {
		List<MemberVO> memberList = memberDAO.selectMemberList();
		return memberList;
	}

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO member = memberDAO.selectMember(mem_id);
		if (member == null) {
			throw new CommonException("ID가 존재하지않습니다.");
		}
		return member;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		return null;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		return null;
	}
}
