package kr.or.ddit.db.ibatis;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class CustomSqlMapClientBuilder { //sql이 싱글톤으로 관리된다
	private static SqlMapClient sqlMapClient; //접근할수없게 하려고 private로 함
	
	static {
		try (
			Reader rd = Resources.getResourceAsReader("kr/or/ddit/db/ibatis/SqlMapConfig.xml"); //sqlmapconfig가 문자열이라 charString타입인 reader로 읽었다(다른것도 됨)
		){
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(rd); //환경설정을 객체로서 담아 사용한다. 
		} catch (IOException e) {
			throw new RuntimeException(e);	//실시간으로예외처리한다.
		}
	}

	public static SqlMapClient getSqlMapClient() { //객체를 다른 클래스에서 생성하지않도록 이미만들어진 객체를 넘겨줌
		return sqlMapClient;
	}
}
