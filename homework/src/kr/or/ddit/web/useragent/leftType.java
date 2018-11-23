package kr.or.ddit.web.useragent;

public enum leftType {
	GUGUDAN("gugudan","/01/gugudanForm.html"), 
	LYRICS("lyrics","/02/musicForm.jsp"), 
	CALENDER("calender","/04/calendar.jsp"), 
	IMAGE("image","ImagesFormServlet.java");
	
	private String leftName;
	private String url;
	
	private leftType(String leftName, String url){
		this.leftName = leftName;
		this.url = url;
	}
	
	public String getLeftName() { //값의 첫번째 네임값을 가져오는메서드
		return leftName;
	}
	
	public String getUrl() { //값의 두번째 url값을 가져오는 메서드
		return url;
	}
	
	public static leftType getLeftType(String userAgent) { //파라미터가 enum에 입력되는 메서드
		leftType result = null;
		leftType[] types = values();
		for (leftType tmp : types) {
			result = tmp;
			break;
		};
		return result;
	}
	
	
	
}

