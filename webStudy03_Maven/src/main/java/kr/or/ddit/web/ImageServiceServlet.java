package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;

@WebServlet("/imageService")
public class ImageServiceServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요청 파라미터 확보 : 파라미터명(image)
		String imgName = req.getParameter("image"); //요청파라미터로 image를 가져온다
		if (imgName == null || imgName.trim().length() == 0) { //null이거나 trim으로 공백이면 응답으로 400에러를 띄움
			resp.sendError(400);
			return;
		}
		
		File folder = (File)getServletContext().getAttribute("contentFolder"); //servletcontext의 속성을 가져오고 contentfolder를 선언한다
		File imgFile = new File(folder, imgName); //폴더의 imgname을 imgfile에 객체생성해준다
		
		if (!imgFile.exists()) { //imgfile이 존재하지 않으면 찾을수 없다는 에러는 뜨운다.
			resp.sendError(404);
			return;
		}
		
		//쿠키값 : A,B               여기부터
		String imageCookieValue = new CookieUtil(req).getCookieValue("imageCookie");
		String[] CookieValues = null;
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isBlank(imageCookieValue)) {
			CookieValues = new String[] {imgFile.getName()};
		}else {
			//unmarcha
			String[] cValues = mapper.readValue(imageCookieValue, String[].class);
			CookieValues = new String[cValues.length +1 ]; //사진이 있어서 추가될때 cValues의길이에서 +1만큼의 새로운 배열을만들어서 넣어줌
			System.arraycopy(cValues, 0, CookieValues, 0, cValues.length);
			CookieValues[CookieValues.length-1] = imgFile.getName();
		}
		
//		imageCookieValue = Arrays.toString(CookieValues);
//		imageCookieValue = imageCookieValue.replaceAll("[\\[\\]\\s]", ""); //정규표현식의 \\[여는것  \\]닫는것 \\s 를 공백으로 한다
		
		//marshalling
		
		imageCookieValue = mapper.writeValueAsString(CookieValues);
		System.out.println(imageCookieValue);
			
		Cookie imageCookie = CookieUtil.createCookie("imageCookie", imageCookieValue , req.getContextPath(), TextType.PATH, 60*60*24*3);
		resp.addCookie(imageCookie);
		//여기까지 쿠키
		
		
		ServletContext context = req.getServletContext(); //요청된 값의 servletcontext를 가져오고 context에 선언한
		resp.setContentType(context.getMimeType(imgName)); //imgname의 mimetype을 응답에 contenttype으로 set해준다
		
		
		
		//이미지 스트리밍
		byte[] buffer = new byte[1024]; //버퍼를 1kbtye로 줘서 이미지 갱신속도를 설정해준다
		FileInputStream fis = new FileInputStream(imgFile); //fileinputstream으로 이미지파일을 인풋해주고
		OutputStream os = resp.getOutputStream(); //응답으로 아웃풋스트림을 아웃풋스트림에 선언해준다.
		int pointer = -1; //pointer를 -1로준다
		while((pointer = fis.read(buffer)) != -1) { // -1 : EOF문자 
			//pointer에 읽어줄 버퍼만큼 값을 넣는다 1025라면
			//이미지파일 읽어올때 버퍼가 1025(1~1025)이면 버퍼는 0~1023까지 있는데 1번 읽고 1이남는다 -1이 아니라서 
			//버퍼를 0부터 pointer인 1만큼 1바퀴더 돌아주며 0까지 읽으면(0부터시작하므로) pointer는 다읽는다 그러면 EOF문자인 -1이 되어 while이 끝난다  
			os.write(buffer, 0, pointer); //즉 1킬로바이트보다 높을 경우 용량만큼 더 돌며 남는것도 있으므로 제한을 0부터 남는 pointer만큼만 돌수있게둠
		}
		
		fis.close();
		os.close();
		
		
	}
}
