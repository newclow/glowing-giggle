package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.model2.SampleModelGenerator;

@WebServlet("/fileBrewser.do")
public class ServerFileBrowser extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		브라우저상에 webcontent목록을 보여줘야됨
//		디렉토리라면 그걸 두번클릭하면 그에 해당하는 디렉토리목록이 보여야됨
//		그리고 ..을 누르면 상위폴더로 이동할수있게
//		
//		파라미터를 설계하여 서버에 날려야됨
		SampleModelGenerator generator = new SampleModelGenerator();
		
		
		
		String view = "/WEB-INF/views/fileBrewser.jsp";
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
}


//		//1. 요청분석
//		//2. 의존객체생성
//		SampleModelGenerator generator = new SampleModelGenerator();
//		//3. 로직 메소드를 호출
//		String model = generator.generate();
//		//4. 데이터 공유
//		req.setAttribute("model", model);
//		//5. 뷰레이어를 선택
//		String view = "/WEB-INF/views/Model2SimpleView.jsp";
//		//6. 이동
//		RequestDispatcher rd =req.getRequestDispatcher(view);
//		rd.forward(req, resp);
