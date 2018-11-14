package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.calculate.MimeType;
import kr.or.ddit.web.calculate.Operator;

public class CalculateServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		String contentFolder = application.getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		application.setAttribute("contentFolder", folder);
		System.out.println(getClass().getSimpleName()+"초기화");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//파라미터 확보(입력태그의 name 속성값으로 이름결정)
		
		String leftOpStr = req.getParameter("leftOp");
		String rightOpStr = req.getParameter("rightOp");
		String operatorStr = req.getParameter("operator");
		//검증
		int leftOp, rightOp;
		boolean valid = true;
//		if (leftOpStr != null && leftOpStr.matches("[0-9]+") ) {
//			left = Integer.parseInt(leftOpStr);
//		}
//		if (rightOpStr != null && rightOpStr.matches("[0-9]+")) {
//			right = Integer.parseInt(rightOpStr);
//		}
		if (leftOpStr == null || !leftOpStr.matches("\\d{1,6}") || rightOpStr == null || !rightOpStr.matches("\\d{1,6}")) {
			valid = false;
		}
		if (operatorStr == null) {
			valid = false;
		}
		
		Operator operator = null;
		try {
			operator = Operator.valueOf(operatorStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			valid = false;
		}
		
		if (!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		
		//통과
		//	연산자에 따라 연산 수행
		//	일반텍스트의 형태로 연산결과를 제공
		//	연산결과 : 2 * 3 = 6
//		int result = 0;
//		if (operator.equals("+")) {
//			result = left + right;
//		} else if (operator.equals("-") ) {
//			result = left - right;
//		} else if (operator.equals("*")) {
//			result = left * right;
//		} else if (operator.equals("/")) {
//			result = left / right;
//		}
		
		leftOp = Integer.parseInt(leftOpStr);
		rightOp = Integer.parseInt(rightOpStr);
		String pattern = "%d %s %d = %d";
		String result = String.format(pattern, leftOp, operator.getSign(), rightOp, operator.operate(leftOp, rightOp));;
//		switch (operator) {
//		case ADD:
//			
//			break;
//		case MINUS:
//			result = String.format(pattern, leftOp, operator.getSign(), rightOp, (leftOp - rightOp));
//			break;
//		case MULTIPLY:
//			result = String.format(pattern, leftOp, operator.getSign(), rightOp, (leftOp * rightOp));
//			break;
//		case DIVIDE:
//			result = String.format(pattern, leftOp, operator.getSign(), rightOp, (leftOp / rightOp));
//			break;
//		}
		
		
		//enum적용해보기
//		String accept = req.getHeader("Accept"); //클라이언트에서 요청하는 데이터타입을 잡을수있음
//		String mime = null;
//		if (accept.contains("plain")) {
//			mime = "text/plain;charset=UTF-8";
//		}else if (accept.contains("json")) {
//			mime = "application/json;charset=UTF-8";
//			result = "{\"result\":\""+result+"\"}";
//		}else {
//			mime = "text/html;charset=UTF-8";
//			result = "<p>"+result+"</p>";
//		}
		
/*		Operator operator = null;
		try {
			operator = Operator.valueOf(operatorStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			valid = false;
		}
		
		if (!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}*/
		
		
		String accept = req.getHeader("Accept");
		int start = accept.indexOf("/")+1;
		int end = accept.indexOf(",");
		String acc = accept.substring(start, end);
		MimeType mimeType = null;
		try {
			mimeType = MimeType.valueOf(acc.toUpperCase());
		} catch (IllegalArgumentException e) {
			valid = false;
		}
		
		if (!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		
		
		
		resp.setContentType(mimeType.getSign());
		PrintWriter out = resp.getWriter();
		out.println(mimeType.mimeType(result));
		out.close();
		
	}
}
