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
		gradeMap = new HashMap<>();	//hashmap은 입력 순서가 섞여서 사용안하는게 좋음
		licenseMap = new LinkedHashMap<>(); //입력순서 대로 목록출력해줌
		
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
	public Map<String, AlbasengVO> albasengs = new LinkedHashMap<>(); //VO를 map에 인스턴스한다.
	
	@Override
	public void init(ServletConfig config) throws ServletException { //서버가 시작하면 init가 실행하는데 속성을 위에서 셋팅한 값들을 set해준다.
		super.init(config);
		getServletContext().setAttribute("gradeMap", gradeMap); //gradeMap을 같은 이름으로 attribute에 넣어준다
		getServletContext().setAttribute("licenseMap", licenseMap); //licenseMap을 같은 이름으로 attribute에 넣어준다
		getServletContext().setAttribute("albasengs", albasengs); //albasengs을 같은 이름으로 attribute에 넣어준다
		System.out.println(getClass().getSimpleName()+"초기화"); //서버가 시작하면 초기화를 했다고 console창에 출력
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //요청값들의 인코딩을 UTF-8로 설정해준다.
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
		
		AlbasengVO vo = new AlbasengVO(); //vo의 객체를 선언하고 
		vo.setName(req.getParameter("name")); //vo의 Name에 요청값의 파라미터인 name을 넣어준다.
		String age = req.getParameter("age");
		if (age != null && age.matches("\\d{1,2}")) { //age가 null이 아니거나 정규식이 맞을 경우 값을 integer로 한뒤 넣어준다 *null과 trim을 두개를 합친 StringUtils의 isblank를 이용해도 된다
			vo.setAge(new Integer(age));
		}
		vo.setTel(req.getParameter("tel"));
		vo.setAddress(req.getParameter("address"));
		vo.setGender(req.getParameter("gender"));
		vo.setGrade(req.getParameter("grade"));
		vo.setLicense(req.getParameterValues("license"));
		vo.setCareer(req.getParameter("career"));
		req.setAttribute("albaVO", vo); //vo는 요청값의 속성으로 넣어준다.
		
		
		boolean valid =true; //valid를 true로 선언
		Map<String, String> errors = new LinkedHashMap<>(); //errors를 Map으로 선언, Linkedhashmap은 입력된 순서로대로 순서에 맞춰 배치된다
		req.setAttribute("error", errors);	//요청값에 error를 속성값으로 넣어준다.
		if (StringUtils.isBlank(vo.getName())) { //vo.getName이 whitespace거나 null이면 if문으로 들어가 errors에 이름누락으로 넣어짐
			valid = false;	// valid는 true에서 false로 바뀜
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
		
		boolean redirect = false;	//redirect를 false로 선언함
		String goPage =null;
		if (valid) {	//valid가 true일시 조건문이 작동함
			vo.setCode(String.format("alba_%03d", albasengs.size()+1)); //3은 3자리수, 패딩문자로 0을 준다 //vo의 code를 셋팅해주며 alba_001형식으로 생성하며 생성될때마다 1을 더해 증가한다
			albasengs.put(vo.getCode(), vo); //albasengs에 code에 맞춰 vo값들을 넣어준다
			goPage = "/05/albaList.jsp"; //albaList로 간다
			redirect = true;	//redirect가 true로 바뀐다
		}else {
			goPage = "/01/simpleForm.jsp";	//입력값이 틀릴시 다시 가입페이지의 경로를 넣어줌
		}
		if (redirect) {	//redirect가 true일시
			resp.sendRedirect(req.getContextPath()+goPage); //응답값을 표시해주고 albaList에 보내준다
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);	//가입페이지로 가능 경로값이 dispatcher로 들어가 포워드 형식으로 가입페이지로 돌아간다. redirect는 stateless가 작용하여 입력값들이 다 사라지기 때문
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
