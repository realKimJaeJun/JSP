<%@page import="java.sql.PreparedStatement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
		String bookId = request.getParameter("bookId");
		String bookName = request.getParameter("bookName");
		String publisher = request.getParameter("publisher");
		String price = request.getParameter("price");
	
	try{
		Connection conn = DBCP.getConnection();		
		String sql = "UPDATE `book` SET `bookName`=?, `publisher`=?, `price`=? WHERE `bookId`=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, bookName);
		psmt.setString(2, publisher);
		psmt.setString(3, price);
		psmt.setString(4, bookId);
		
		psmt.executeUpdate();
		
		psmt.close();
		conn.close(); 
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	response.sendRedirect("./list.jsp");
%>