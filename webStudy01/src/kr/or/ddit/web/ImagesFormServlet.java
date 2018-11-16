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
		ServletContext context = req.getServletContext();

		File folder = (File) context.getAttribute("contentFolder");
		String[] filenames = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				String mime = context.getMimeType(name);
				return mime.startsWith("image/");
			}
		});

		StringBuffer sb = new StringBuffer();
//		for(int i = 0; i < filenames.length; i++){
//			sb.append("<option>");
//			sb.append(filenames[i]);
//			sb.append("</option>");
//		}
		String pattern = "<option>%s</option>\n";
		for (String name : filenames) {
			sb.append(String.format(pattern, name));
		}

		req.setAttribute("optionsAttr", sb.toString());
//		int start = html.indexOf("@option");
//		int end = start + "@option".length();
//		String replacetext = sb.toString();
//		html.replace(start, end, replacetext);

		// JSON형태기록
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie"); // 여기
		StringBuffer imgTags = new StringBuffer();
		if (imgCookieValue != null) {
			// unmarshalling
			ObjectMapper mapper = new ObjectMapper();
			String[] imgNames = mapper.readValue(imgCookieValue, String[].class);
			String imgPattern = "<img src='imageService?image=%s' />"; // 여기
			for (String imgName : imgNames) {
				imgTags.append(String.format(imgPattern, imgName));
			}
		}

		req.setAttribute("imgTags", imgTags);
//		start = html.indexOf("@images");
//		end = start + "@images".length();
//		html.replace(start, end, imgTags.toString());

		String view = "/WEB-INF/views/ImageForm.jsp";
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.include(req, resp);

//		PrintWriter out = resp.getWriter();
//		out.println(html.toString());
//		out.close();

	}
}
