<%@page import="javafx.util.converter.DateStringConverter"%>
<%@page import="com.sun.javafx.binding.StringFormatter"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%> <!--  static을 붙이고 맨뒤에 *을 붙여주면 static형태의 Calendar.을 안붙여도
                                     메서드나 변수를 호출할수있다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
   .sunday{
      background-color: red;
   }
   .saturday{
      background-color: blue;
   }
   table{
      width: 100%;
      height: 500px;
      border-collapse: collapse;
   }
   td,th{
      border: 1px solid black;
   }
</style>
<script>
   function eventHandler(year, month){
      var form = document.calForm //이렇게하면 현재 문서에 들어있는 form태그의 모든 레퍼런스를 들고온다.
      if((year && month) || month==0){ //년이거나 월 이어야하고 또 월이 0이면
         form.year.value = year;	//form의 year값에 년도값을 준다
         form.month.value = month;	//form의 month값에 월값을 준다.
      }
      form.submit();
      return false;
   }
</script>

   <%
      request.setCharacterEncoding("UTF-8");	//요청하는 char를 UTF-8로 인코딩
      String yearStr = request.getParameter("year"); //요청하는 year파라미터를 담아준다
      String monthStr = request.getParameter("month"); //요청하는 파라미터 month를 담아준다
      String language = request.getParameter("language"); //요청하는 파라미터language를 가져와 string타입의 변수에 담아준다 
      
      //String[] dayStr = new String[]{"일", "월"}; 하드코딩말고 아래와 같이 사용할 것
      Locale clientLocale = request.getLocale(); //요청하는 클라이언트을 가져와 locale정보를 꺼낼수 있다.
      if(language!=null && language.trim().length()>0){ 
         clientLocale = Locale.forLanguageTag(language); //language가 있을시 해당 클라이언트언어를 가져온다
      }
      DateFormatSymbols symbols = new DateFormatSymbols(clientLocale); //클라이언트에 따라 생성해준다.
      
      Calendar cal = Calendar.getInstance(); //protected로 되어있어서 메서드를 호출해서 객체생성을 해야한다.
      if(yearStr!=null && yearStr.matches("\\d{4}") && monthStr!=null && monthStr.matches("1[0-1]|\\d")){ //정규식
         cal.set(YEAR, Integer.parseInt(yearStr)); //calendar에 년도를 설정함
         cal.set(MONTH, Integer.parseInt(monthStr)); //calendar에 달을 설정함
      }
      int currentYear = cal.get(Calendar.YEAR); //Calendar.YEAR이렇게 하면 67이라던지 잘못된 데이터를 막을수가 없다.
                                     			 //따라서 enum 열거형 상수를 써서 그 이외에는 데이터 전달을 못하도록 막아야한다.
                                    			 //Calendar.YEAR로안쓰고  .Year로 가능하다 맨위에 import에 
                                     			 //static으로 미리 선언했기때문에 가능.
      int currentMonth = cal.get(MONTH);	//해당 월을 가져와 금월을 설정한다
      cal.set(DAY_OF_MONTH, 1);//한달에서 몇번째 날로 셋팅을 하겠다.
      int firstDayOfWeek = cal.get(DAY_OF_WEEK); //현재 월의 날짜를 가져온다.
      int offset = firstDayOfWeek-1; //현재요일 즉 일주일을 셋팅하는것으로 일요일은 1 토요일은 7로 배정되어있어 -1을 해서 0부터 6까지 배정함
      int lastDate = cal.getActualMaximum(DAY_OF_MONTH); //해당 달의 최대일수를 가져오는것으로 31이면 그날짜만큼 최대 수를 가져온다.
      cal.add(MONTH, -1); //before을 적용할시 해당달의 -1을 해 이전달로 간다.
      int beforeYear = cal.get(YEAR); //이전달의 년도를 가져온다
      int beforeMonth = cal.get(MONTH); //이전달의 월을 가져온다.
      cal.add(MONTH, 2); //next를 적용할시 해당달의 +2을 해 다음달로 간다. -1을 먼저 before할때 뺏으므로 next는 +2를 하여 다음달로 간다.
      int nextYear = cal.get(YEAR); //다음달의 년도를 가져온다
      int nextMonth = cal.get(MONTH); //다음달의 달을 가져온다.
      
      Locale[] locales = Locale.getAvailableLocales(); //자바자체에서 사용가능 지역/국가 정보를 가져온다.
   %>
   <form name="calForm" method="post">
   <input type="hidden" name="command" value="calendar" /> 
   <h4><%--달력에 전달 후달을 주기위한것 버튼액션 --%>
   <a href="javascript:eventHandler(<%=beforeYear%>,<%=beforeMonth%>);">이전달</a> <!-- calendar.jsp는 상대경로이기 때문에 
                        클라이언트 사이드 방식으로 작성해줘야 한다.  -->
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <%-- <%=String.format("%d년 %d월", currentYear, currentMonth+1)%> 쓸필요없음--%>
      <input type="number" name="year" value="<%=currentYear%>"
         onblur="eventHandler();" <%--onblur는 포커스(마우스깜박이는거)가 나갔을때 이벤트가 발생한다. --%>
      />년
      <select name="month" onchange="eventHandler();"> <!-- select에서 사용하는건 change이벤트이다. -->
         <%//달력에 월을 주기 위한것
         String[] monthStrings = symbols.getShortMonths();	//1월 2월 등의 짧은문자열을 가져와 배열에 넣준다, Jan, Feb 이런거
            for(int idx = 0; idx < monthStrings.length-1; idx++){ //월은 0부터 시작하여 길이에서 -1을한만큼 돌려준다
               out.println(String.format("<option value='%d' %s>%s</option>", //HTML형식을채워주며
                     idx, idx==currentMonth?"selected":"",monthStrings[idx])); //해당 월의 값을 넣주고 현재달을 처음에 선택하여 볼수있게 해주며 monthString으로 월을 글자로 표시한다.
            }
         %>
      </select>
      <select name="language" onchange="eventHandler();">
         <%//지역/국가를 select를 넣기위한것
            for(Locale tmp: locales){	//지역/국가에서 해당 지역/국가를 select로 선택하는것을 만드는것
               out.println(String.format(
                     "<option value='%s' %s>%s</option>", 
                     tmp.toLanguageTag(), 						//해당 선택된 지역/국가 정보의값을 넣어준다
                     tmp.equals(clientLocale)?"selected":"",	//선택된 지역/국가가 클라이언트의 지역/국가와 같으면 선택하여 select에 표시
                     tmp.getDisplayLanguage(clientLocale)      //클라이언트의 지역/국가를 표시해준다.
               ));
            }
         %>
      </select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <a onclick="eventHandler(<%=nextYear%>,<%=nextMonth%>);">다음달</a> <%-- href를 생략해놓으면 브라우저의 기본주소가 찍히기 때문에 <%=request.getContextPath()%>/04/calendar.jsp을 의미한다. 
                                                즉 생략하면 http://localhost/webStudy01/04/calendar.jsp이 찍힌다.그래서 현재는 생략함--%>
   </h4>
   </form>
   <table>
   <thead>
      <tr>
         <%
           //달력에 요일을 표시하기 위한것 
            String[] dateStrings =symbols.getShortWeekdays(); //String[]크기는 8(0~7)이다 . 일요일은 1이고 즉 0번째는 비어있기 때문에 //일주일을 가져오는것 월화수목금토일의 문자를 말이다.
            for(int idx = Calendar.SUNDAY; idx<=Calendar.SATURDAY; idx++){ //달력의 일요일 부터 토요일까지 차례로 돌려주며 표시 1~7까지 0은 비었음
               out.println(String.format(
                     "<th>%s</th>", dateStrings[idx])); //가져온 요일을 표시
            }
            
         %>
      </tr>
   </thead>
   <tbody>
         <%//해당 달력에 토요일과 일요일에 색깔을 주기위한 것
         int dayCount = 1;	//달력의 1일 
         for(int row=1; row<=6; row++){	//달력의 틀, 달력이 들어갈곳
         %>
               <tr>
            <% 
            for(int col=1; col<=7; col++){	//달력의 줄,일주일이 들어갈 곳, 1주 2주 3주 4주 같은것
               int dateChar = dayCount++ -offset;	//일을 갯수에서 일주일(주일을 배정한것에서) 빼서 표시? 즉 전체 갯수가 있는데 거기서 offset만큼뺀다 빼면 줄어드는데 그게 그주의 총 일수가 된다.
               if(dateChar<1 || dateChar >lastDate){
                  out.println("<td>&nbsp;</td>");	//만약 그 총일수가 1보다 작으며 또 마지막 일(31일같은거)보다 크면 빈것을 표시하고 즉 1~31까지만 표시
               }else{
                  String clzValue = "normal"; //그냥 구분을위해 normal을 주는것같다.??
                  if(col==1){
                     clzValue = "sunday"; //1이면 일요일 준다
                  }else if(col==7){
                     clzValue = "saturday"; //7이면 토요일준다
                  }
                  out.println(String.format("<td class='%s'>%d</td>", clzValue, dateChar));     //class가 sunday면 빨간색, saturday면 파란색을 준다. 그리고 날짜도 맞게 넣어준다.       
               }
            }
            %>
            </tr>
            <%
         }
            %>
   </tbody>
   </table>
