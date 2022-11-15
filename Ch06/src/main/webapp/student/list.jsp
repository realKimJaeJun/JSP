<%@page import="bean.StudentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%




	// 데이터베이스 작업
	List<StudentBean> students = new ArrayList<>();
	
	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `student`");
		
		students = new ArrayList<>();
		while(rs.next()){
	StudentBean sb = new StudentBean();
	sb.setStdno(rs.getString(1));
	sb.setStdname(rs.getString(2));
	sb.setStdhp(rs.getString(3));
	sb.setStdyear(rs.getInt(4));
	sb.setStdaddress(rs.getString(5));
	
	students.add(sb);
		}
		
		stmt.close();
		conn.close();
		rs.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>student::list</title>
	</head>
	<body>
		<h3>student 목록</h3>
		<a href="../1_JDBCTest.jsp">처음으로</a>
		<a href="./register.jsp">student 등록</a>
		
		<table border="1">
			<tr>
				<th>학번</th>
				<th>이름</th>
				<th>휴대폰</th>
				<th>학년</th>
				<th>주소</th>
			</tr>
			<% for(StudentBean sb : students){ %>
			<tr>
				<td><%= sb.getStdno() %></td>
				<td><%= sb.getStdname() %></td>
				<td><%= sb.getStdhp() %></td>
				<td><%= sb.getStdyear() %></td>
				<td><%= sb.getStdaddress() %></td>
				<td>
					<a href="./modify.jsp?stdno=<%= sb.getStdno() %>">수정</a>
					<a href="./delete.jsp?stdno=<%= sb.getStdno() %>">삭제</a>
				</td>
			</tr>
			<% } %>
		</table>
	</body>
</html>