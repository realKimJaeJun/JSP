<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	request.setCharacterEncoding("utf-8");

	String uid = request.getParameter("uid");
	
	String host = "jdbc:mysql://127.0.0.1:3306/java2db";
	String user = "root";
	String pass = "1234";
	
	try{
		Connection conn = DriverManager.getConnection(host, user, pass);
		
		String sql = "DELETE FROM `member` WHERE `uid`=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		
		psmt.setString(1, uid);
		psmt.executeUpdate();
		
		psmt.close();
		conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	
	response.sendRedirect("./list.jsp");
			
%>