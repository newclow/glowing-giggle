package kr.or.ddit.member.dao;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.BuyerVO;

public class BuyerDAOImpl implements IBuyerDAO {
	
	SqlMapClient sql = CustomSqlMapClientBuilder.getSqlMapClient();

	@Override
	public List<BuyerVO> selectBuyerList() {
		return null;
	}

}
