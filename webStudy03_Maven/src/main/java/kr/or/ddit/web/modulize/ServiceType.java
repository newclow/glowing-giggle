package kr.or.ddit.web.modulize;

public enum ServiceType {
	GUGUDAN("/01/gugudanForm.html"), 
	GUGUDANPROCESS("/gugudan.do"),
	LYRICS("/02/musicForm.jsp"), 
	CALENDAR("/04/calendar.jsp"), 
	IMAGE("/imageForm"); //java는 주소가 없어서 web.xml에 선언해서 식별을 생성한다
	
	private String servicePage;

	private ServiceType(String servicePage) {
		this.servicePage = servicePage;
	}

	public String getServicePage() {
		return servicePage;
	}
	
	
}
