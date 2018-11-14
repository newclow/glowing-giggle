package kr.or.ddit.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.AlbasengVO;


@WebServlet(value="/albamon", loadOnStartup=1)
public class SimpleFormProcessServlet extends HttpServlet {
	public Map<String, String> gradeMap;
	public Map<String, String> licenseMap;
	 {
		gradeMap = new HashMap<>();
		licenseMap = new LinkedHashMap<>();
		
		gradeMap.put("G001", "고졸");
		gradeMap.put("G002", "대졸");
		gradeMap.put("G003", "석사");
		gradeMap.put("G004", "박사");
		
		licenseMap.put("L001", "정보처리산업기사");
		licenseMap.put("L002", "정보처리기사");
		licenseMap.put("L003", "정보보안산업기사");
		licenseMap.put("L004", "정보보안기사");
		licenseMap.put("L005", "SQLD");
		licenseMap.put("L006", "SQLP");
	}
	public Map<String, AlbasengVO> albasengs = new LinkedHashMap<>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		getServletContext().setAttribute("gradeMap", gradeMap);
		getServletContext().setAttribute("licenseMap", licenseMap);
		getServletContext().setAttribute("albasengs", albasengs);
		System.out.println(getClass().getSimpleName()+"초기화");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		resp.setContentType("text/html;charset=UTF-8");
		
//		String nameStr = req.getParameter("name");
//		String ageStr =req.getParameter("age");
//		String telStr = req.getParameter("tel");
//		String addressStr = req.getParameter("address");
//		String genderStr = req.getParameter("gender");
//		String gradeStr = req.getParameter("grade");
//		String careerStr = req.getParameter("career");
//		String[] licenseStr = req.getParameterValues("license");
		
//		System.out.printf("%s : $s\n", "name", nameStr);
//		System.out.printf("%s : $s\n", "license", Arrays.toString(licenseStr));
//	vo객체생성 파라미터할당
//	vo를 대상으로 검증
//	(이름, 주소, 전화번호 필수데이터 검증)
//	1)통과
//	code생성("alba_001")
//	map.put(code,vo)
//	이동(/05/albaList.jsp, 요청 처리 완료 후 이동 (redirect)))
//	2)불통
//	이동(/01/simpleForm.jsp, 기존 입력데이터를 전달한채 이동)
		
		AlbasengVO vo = new AlbasengVO();
		vo.setName(req.getParameter("name"));
		String age = req.getParameter("age");
		if (age != null && age.matches("\\d{1,2}")) {
			vo.setAge(new Integer(age));
		}
		vo.setTel(req.getParameter("tel"));
		vo.setAddress(req.getParameter("address"));
		vo.setGender(req.getParameter("gender"));
		vo.setGrade(req.getParameter("grade"));
		vo.setLicense(req.getParameterValues("license"));
		vo.setCareer(req.getParameter("career"));
		req.setAttribute("albaVO", vo);
		
		
		boolean valid =true;
		Map<String, String>errors = new LinkedHashMap<>();
		req.setAttribute("error", errors);
		if (StringUtils.isBlank(vo.getName())) {
			valid = false;
			errors.put("name", "이름누락");
		}
		if (StringUtils.isBlank(vo.getTel())) {
			valid = false;
			errors.put("tel", "연락처누락");
		}
		if (StringUtils.isBlank(vo.getAddress())) {
			valid = false;
			errors.put("address", "주소누락");
		}
		
		boolean redirect = false;
		String goPage =null;
		if (valid) {
			vo.setCode(String.format("alba_%03d", albasengs.size()+1)); //3은 3자리수, 패딩문자로 0을 준다
			albasengs.put(vo.getCode(), vo);
			goPage = "/05/albaList.jsp";
			redirect = true;
			resp.sendRedirect(req.getContextPath()+goPage); //응답값을 표시해주는 albaList에 보내준다
		}else {
			goPage = "/01/simpleForm.jsp";
		}
		if (redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
		
		
//		if (vo.getName() == null || vo.getName().trim().length() ==0 ||
//			vo.getAddress() == null || vo.getAddress().trim().length() == 0 ||
//			vo.getTel() == null || vo.getTel().trim().length() == 0) {
//			RequestDispatcher rd = req.getRequestDispatcher(req.getContextPath()+"/01/simpleForm.jsp");
//			rd.forward(req, resp);
//			return;
//		}
		

		
		 
		
		
		
	
//		//2)getParameterMap
//		Enumeration<String> names = req.getParameterNames();
//		while (names.hasMoreElements()) { //있는지 확인하는것
//			String name = (String) names.nextElement(); //있는지 확인됬으면 파라미터명 하나가져옴
//			String[] values = req.getParameterValues(name);	//파라미터를 가져오는데 여러개가 있을수있어 values로 
//			System.out.printf("%s : %s", name, Arrays.toString(values)); //printf로 출력
//		}
//		
//		Map<String, String[]> parameterMap = req.getParameterMap();
//		for ( Entry<String, String[]> entry : parameterMap.entrySet()) { //오른쪽은 반복의 대상이 들어감  //map이라는 요소라는 하나는 키와 벨류 2가지를 가지고 있어 entry로 가져온다
//			//왼쪽은 오른쪽의 값 하나를 가져오므로 그대상이되는 entry가 옴
//			String name = entry.getKey();
//			String[] value = entry.getValue();
//			System.out.printf("%s : %s\n", name, Arrays.toString(value));
//		}
		
	}

}
