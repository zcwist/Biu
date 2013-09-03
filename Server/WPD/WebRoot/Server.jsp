<%@ page contentType="text/html; charset=utf-8"%>
<%
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/plain");
	
	String result = "{name:'zcw',telephone='15210610910'}";
	response.getWriter().print(result);
%>