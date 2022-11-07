<%@page import="com.google.gson.JsonObject"%>
<%@page import="config.SQL"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String stdno  = request.getParameter("stdno");
	String stdname  = request.getParameter("stdname");
	String stdhp  = request.getParameter("stdhp");
	String stdyear  = request.getParameter("stdyear");
	String stdaddress  = request.getParameter("stdaddress");

	int result = 0;
	
	try{
		Connection conn = DBCP.getConnection();
		PreparedStatement psmt = conn.prepareStatement(SQL.INSERT_STUDENT);
		psmt.setString(1, stdno);
		psmt.setString(2, stdname);
		psmt.setString(3, stdhp);
		psmt.setString(4, stdyear);
		psmt.setString(5, stdaddress);
		
		
		result = psmt.executeUpdate();
		
		psmt.close();
		conn.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	JsonObject json = new JsonObject();
	json.addProperty("result", result);
	
	String jsonData = json.toString();
	
	out.print(jsonData);
%>