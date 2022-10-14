<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		String name = request.getParameter("name");
		String hp = request.getParameter("hp");
		String pos = request.getParameter("pos");
		String dep = request.getParameter("dep");

	String host = "jdbc:mysql://127.0.0.1:3306/java2db";
	String user = "root";
	String pass = "1234";
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(host, user, pass);
		
		String sql = "INSERT INTO `member` VALUES (?,?,?,?,?,now)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, uid);
		psmt.setString(2, name);
		psmt.setString(3, hp);
		psmt.setString(4, pos);
		psmt.setString(5, dep);
		
		psmt.executeUpdate();
		
		
		psmt.close();
		conn.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		response.sendRedirect("./list.jsp");
%>
