package kr.or.ddit.member.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements IMemberDAO {
	
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient(); //커스텀해준 sqlmap빌더를 싱글톤으로 가져옴

	@Override
	public MemberVO selectMember(String mem_id) { //service에서 파라미터값을 받는다
		try {
			MemberVO member = (MemberVO)sqlMapClient.queryForObject("Member.selectMember", mem_id); //sql문에 파라미터값을 넣어 실행준다  > sqlmapclient로
			return member;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int insertMember(MemberVO member) {
		try {
				return sqlMapClient.update("Member.insertMember", member);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public List<MemberVO> selectMemberList() {
		try {
			List<MemberVO> list = sqlMapClient.queryForList("Member.selectMemberList");
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		int result;
		try {
			result = sqlMapClient.update("Member.updateMember", member);
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteMember(String mem_id) {
		int result;
		try {
			result = sqlMapClient.delete("Member.deleteMember", mem_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
