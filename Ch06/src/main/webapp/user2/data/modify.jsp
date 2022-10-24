<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8" 
trimDirectiveWhitespaces="true"%>
<%
	// 데이터 수신
	request.setCharacterEncoding("utf-8");
	String uid = request.getParameter("uid");
	String name = request.getParameter("name");
	String hp = request.getParameter("hp");
	String age = request.getParameter("age");
	
	
	// 데이터베이스 작업
	int result = 0;

	try{
		Connection con = DBCP.getConnection();
		String sql = "UPDATE `user2` SET `name`=?, `hp`=?, `age`=? WHERE `uid`=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		
		psmt.setString(1, name);
		psmt.setString(2, hp);
		psmt.setString(3, age);
		psmt.setString(4, uid);
		
		result = psmt.executeUpdate();
		
		con.close();
		psmt.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	JsonObject json = new JsonObject();
	json.addProperty("result", result);
	String jsonData = json.toString();
	
	out.print(jsonData);
%>