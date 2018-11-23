package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;

public class ImagesFormServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8"); // 인코딩은 출력스트림개방전에 셋팅
		ServletContext context = req.getServletContext(); //request의 servletcontext를 가져와 선언해준다

		File folder = (File) context.getAttribute("contentFolder"); //context에 contentFolder에 해당하는 속성을 가져와 folder에 선언 content내의 폴더를 갖오기위함
		String[] filenames = folder.list(new FilenameFilter() { //폴더 리스트의 이름을 가져옴

			@Override
			public boolean accept(File dir, String name) { //폴더들의 mimetype중 이미지파일만을 가져오기 위함이며 해당되면 return되어 값을 반환
				String mime = context.getMimeType(name); //이름을 이용하여 mime타입을 가져옴
				return mime.startsWith("image/"); //mime타입이 image로 시작하는것을 반환
			}
		});

		StringBuffer sb = new StringBuffer(); //객체를 선언하여 1객체에 값을 넣어줌으로써 메모리를 아낄수있음
//		for(int i = 0; i < filenames.length; i++){
//			sb.append("<option>");
//			sb.append(filenames[i]);
//			sb.append("</option>");
//		}
		String pattern = "<option>%s</option>\n"; //HTML형식의 패턴
		for (String name : filenames) { //향상된 for문으로 오른쪽이 위에서 선언된값이고 왼쪽은 선언 된값의 하위 즉 이름들이 있다면 그 이름들중 한 이름을 가져옴 결국for문임
			sb.append(String.format(pattern, name)); //for문이 돌면서 패턴에맞춰 값을 넣어줌
		}

		req.setAttribute("optionsAttr", sb.toString()); //선언된 버퍼의 toString로 String으로 만들어 속성을 set해준다.
//		int start = html.indexOf("@option");
//		int end = start + "@option".length();
//		String replacetext = sb.toString();
//		html.replace(start, end, replacetext);

		// JSON형태기록
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie"); //jar로 만드 cookieutil에 request를 넣은 후 imageCookie으 이름 검색후 값을 가져옴 
		StringBuffer imgTags = new StringBuffer(); //imgTags를 StringBuffer로 선언해 메모리를 아낌
		if (imgCookieValue != null) { 
			// unmarshalling     
			ObjectMapper mapper = new ObjectMapper(); //Jackson ObjectMapper선언
			String[] imgNames = mapper.readValue(imgCookieValue, String[].class); //Json데이터(String형식)과 변환할 Class를 파라미터에 입력
			String imgPattern = "<img src='imageService?image=%s' />"; //패턴을 넣어줌
			for (String imgName : imgNames) { //이미지 이름들에서 해당 이미지이름을 가져옴
				imgTags.append(String.format(imgPattern, imgName)); //Stringbuffer에 패턴형식으로 이미지의 이름을 첨부해줌
			}
		}

		req.setAttribute("imgTags", imgTags); //request에 imgTags라는 이름으로 imgsTags의 stringbuffer를 셋팅해줌
//		start = html.indexOf("@images");
//		end = start + "@images".length();
//		html.replace(start, end, imgTags.toString());

		String view = "/WEB-INF/views/ImageForm.jsp"; //이미지뷰의 경로르 지정해주고
		RequestDispatcher rd = req.getRequestDispatcher(view); //request에 dispatch형식으로 view를 지정
		rd.include(req, resp); //dispatch를 include형식으로 진행

//		PrintWriter out = resp.getWriter();
//		out.println(html.toString());
//		out.close();

	}
}
