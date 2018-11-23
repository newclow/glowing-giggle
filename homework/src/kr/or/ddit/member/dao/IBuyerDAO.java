package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;

/**
 * @author 서신원
 * @since 2018. 11. 21.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 21.      서신원        거래처관리를 위한 Persistence Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IBuyerDAO {
	
	/**
	 * 거래처 목록 조회
	 * @return 존재하지 않으며 size() == 0
	 */
	public List<BuyerVO> selectBuyerList();
	
	
	
}
