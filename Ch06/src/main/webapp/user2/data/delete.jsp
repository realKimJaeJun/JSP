<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8" 
trimDirectiveWhitespaces="true"%>
<%
	// 전송 데이터 수신
	request.setCharacterEncoding("utf-8");
	String uid = request.getParameter("uid");
	
	// 데이터베이스 작업
	int result = 0;
	try{
		Connection con = DBCP.getConnection();
		String sql = "DELETE FROM `user2` WHERE `uid`=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		
		psmt.setString(1, uid);
		
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