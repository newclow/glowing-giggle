package kr.or.ddit.member.dao;

import java.sql.SQLException;

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
}
