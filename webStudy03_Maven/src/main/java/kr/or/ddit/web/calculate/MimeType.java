package kr.or.ddit.web.calculate;

public enum MimeType {
	
//	String accept = req.getHeader("Accept"); //클라이언트에서 요청하는 데이터타입을 잡을수있음
//	String mime = null;
//	if (accept.contains("plain")) {
//		mime = "text/plain;charset=UTF-8";
//	}else if (accept.contains("json")) {
//		mime = "application/json;charset=UTF-8";
//		result = "{\"result\":\""+result+"\"}";
//	}else {
//		mime = "text/html;charset=UTF-8";
//		result = "<p>"+result+"</p>";
//	}
	
	PLAIN("text/plain;charset=UTF-8", (result) -> {return result;}),
	JSON("application/json;charset=UTF-8", (result) -> {return "{\"result\":\""+result+"\"}";}),
	HTML("text/html;charset=UTF-8", (result) -> {return "<p>"+result+"</p>";}),
	XML("application/xml; charset=UTF-8", (result) -> {return result;});
	
	private String sign;
	private RealMimeType realMimeType;
	private MimeType(String sign, RealMimeType realMimeType){
		this.sign = sign;
		this.realMimeType = realMimeType;
	}
	public String getSign(){
		return this.sign;
	}
	public String mimeType(String result) {
		return realMimeType.mimeType(result);
	}
	
	
	
	
	
	
	
//	
//	ADD("+", new RealOperator() {
//
//		@Override
//		public int operate(int leftOp, int rightOp) {
//			return leftOp + rightOp;
//		}
//		
//	}), 
//	MINUS("-", (leftOp, rightOp) ->{return leftOp - rightOp;}), 
//	MULTIPLY("*", (a, b)->{return a * b;}), 
//	DIVIDE("/", (c, d)->{return c / d;});
//	private String sign;
//	private RealOperator realOperator;
//	Operator(String sign, RealOperator realOperator){
//		this.sign = sign;
//		this.realOperator = realOperator;
//	}
//	public String getSign(){
//		return this.sign;
//	}
//	public int operate(int leftOp, int rightOp) {
//		return realOperator.operate(leftOp,rightOp);
//	}
}
