<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	File webStudy01 = new File(application.getRealPath("/"));
	File[] fileList = webStudy01.listFiles();
	File[] fileListAttr = (File[]) request.getAttribute("fileList");
	
	if (fileListAttr != null) {
		fileList = fileListAttr;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>views/fileBrowser.jsp</title>
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function () {
		var fileForm = document.fileForm
		$("#fileList>li").on("dblclick", function() {
			var parentPath = $(this).attr("value") + "\\"
			var fileName = $(this).text()
			if(fileName=="상위폴더"){
				fileName=""
			}
			fileForm.parentPath.value = parentPath
			fileForm.fileName.value = fileName
			
			fileForm.submit()
		})
		
		$('#fileList>li').on("click", function () {
			$(".fileBtn").remove()
			$("<input type='button' class='fileBtn moveFile' value='이동' />").appendTo($(this))
			$("<input type='button' class='fileBtn copyFile' value='복사' />").appendTo($(this))
			$("<input type='button' class='fileBtn deleteFile' value='삭제' />").appendTo($(this))
		})
		
		$("#fileList>li").on("click", ".fileBtn", function () {
			$(this).parent().attr("value")
		})
	})
</script>
</head>
<body>
<ul id="fileList">
	<li value="<%=new File(fileList[0].getParent()).getParent() %>">상위폴더</li>
	<%
		for(File file : fileList){
			%>
			<li value="<%=file.getParent() %>"><%=file.getName() %></li>
			<%
		}
	%>
</ul>
<form name="fileForm" action="fileBrowser.do">
	<input type="hidden" name="parentPath" value="" />
	<input type="hidden" name="fileName" value="" />
</form>
</body>
</html>