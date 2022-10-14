<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 전송 데이터 수신
	request.setCharacterEncoding("utf-8");
	
		String uid = request.getParameter("uid");
		String name = request.getParameter("name");
		String hp = request.getParameter("hp");
		String pos = request.getParameter("pos");
		String dep = request.getParameter("dep");
					
	String host = "jdbc:mysql://127.0.0.1:3306/java2db";
	String user = "root";
	String pass = "1234";
	
	// 데이터베이스 작업
	try{
		// 1단계
		Class.forName("com.mysql.cj.jdbc.Driver");
			
		// 2단계
		Connection conn = DriverManager.getConnection(host, user, pass);
		
		// 3단계
		String sql = "UPDATE `user3` SET `name`=?, `hp`=?, `pos`=?";
			   sql += "WHERE `uid`=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, name);
		psmt.setString(2, hp);
		psmt.setString(3, pos);
		psmt.setString(4, dep);
		psmt.setString(5, uid);
		
		// 4단계
		psmt.executeUpdate();
		
		// 5단계
		
		// 6단계
		psmt.close();
		conn.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
	// 리다이렉트
	response.sendRedirect("./list.jsp");
%>