package kr.or.ddit.web.useragent;

public enum BrowserType {
//	IPHONE(,(result)->{return "당신의시스템은 IOS입니다.";}),
//	WONDOWS(,(result)->{return "당신의시스템은 데스크탑입니다.";}),
//	CHROME(,(result)->{return "당신의 브라우저는 크롬입니다.";}),
//	FIREFOX(,(result)->{return "당신의 브라우저는 파이어폭스입니다.";});
	
	CHROME("크롬"), FIREFOX("파이어폭스"), TRIDENT("익스플로러"), OTHER("기타");
	
	private String browerName;
	private BrowserType(String browerName) {
		this.browerName = browerName;
	}
	public String getBrowserName() {
		return browerName;
	}
	public static BrowserType getBrowerType(String userAgent){
		BrowserType result = BrowserType.OTHER;
		BrowserType[] types = values();
		for (BrowserType tmp : types) {
			if (userAgent.toUpperCase().contains(tmp.name())) {
				result = tmp;
				break;
			};
		}
		return result;
	}

	
	

}
