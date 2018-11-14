package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MusicLyricsServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		ServletContext context = req.getServletContext();
		
		String contentFolder = getServletContext().getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		String[] filenames = folder.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String mime = context.getMimeType(name);
				return mime.startsWith("text/");
			}
		});
		
		InputStream in = this.getClass().getResourceAsStream("songsForm.html");
		InputStreamReader isr = new InputStreamReader(in,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null) {
			html.append(temp+"\n");
		}
		
		StringBuffer sb = new StringBuffer();
		
		String pattern = "<option>%s</option>\n";
		for (String name : filenames) {
			sb.append(String.format(pattern, name));
		}
		
		int start = html.indexOf("@option");
		int end = start + "@option".length();
		String replacetext = sb.toString();
		
		html.replace(start, end, replacetext);
		PrintWriter out = resp.getWriter();
		out.println(html.toString());
		out.close();
		
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //요청값을 UTF-8로 인코딩
		resp.setContentType("text/html;charset=UTF-8"); //응답값을 mimetext로 UTF-8인코딩
		String songName = req.getParameter("song"); //select의 name값을 가져온다
		if (songName == null || songName.trim().length() == 0) { //songName이 널 또는 처음과 끝의 공백을 없애준다
			resp.sendError(400);
			return;
		}
		
		
		String contentFolder = getServletContext().getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		File songFile = new File(folder, songName);
		
		if (!songFile.exists()) {
			resp.sendError(404);
			return;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(songFile), "EUC-KR"));
		
		StringBuffer sb = new StringBuffer();
		String line =null;
		sb.append("<html>");
		sb.append("<body>");
		while ((line = br.readLine()) != null) {
			sb.append("<p>"+line+"</p>");
		}
		sb.append("</body>");
		sb.append("</html>");
	
		PrintWriter out = resp.getWriter();
		out.println(sb.toString());
		out.close();
		
	}
}
