<%@page import="java.sql.PreparedStatement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%


	request.setCharacterEncoding("utf-8");

		String stdno = request.getParameter("stdno");
		String stdname = request.getParameter("stdname");
		String stdhp = request.getParameter("stdhp");
		String stdyear = request.getParameter("stdyear");
		String stdaddress = request.getParameter("stdaddress");
		
		try{
	Connection conn = DBCP.getConnection();
	
	String sql = "INSERT INTO `student` VALUES (?, ?, ?, ?, ?)";
	PreparedStatement psmt = conn.prepareStatement(sql);
	psmt.setString(1, stdno);
	psmt.setString(2, stdname);
	psmt.setString(3, stdhp);
	psmt.setString(4, stdyear);
	psmt.setString(5, stdaddress);
	
	psmt.executeUpdate();
	
	psmt.close();
	conn.close();
	
		}catch(Exception e){
	e.printStackTrace();
		}
		
	response.sendRedirect("./list.jsp");
%>