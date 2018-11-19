package mvjsp.chap02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/now") //URL요청처리
public class NowServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); //servlet이 생성할 문서가 HTML임을 지정
		Date now = new Date();
		
		PrintWriter out = resp.getWriter(); //응답결과 전송할 출력스트림
		out.println("<html>");
		out.println("<head>");
		out.println("<title>NowServlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("서블릿 생성 현재 시간:");
		out.println(now.toString());
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
