<%@page import="config.DBCP"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%




	request.setCharacterEncoding("utf-8");

	String stdno = request.getParameter("stdno");
	
	try{
		
		Connection conn = DBCP.getConnection();
		
		String sql = "DELETE FROM `student` WHERE `stdno`=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, stdno);
		psmt.executeUpdate();
		
		psmt.close();
		conn.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
	response.sendRedirect("./list.jsp");
%>