<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="bean.StudentBean"%>
<%@page import="config.DB"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	String stdno = request.getParameter("stdno");
	
	StudentBean sb = null;
	
	try{
		Connection conn = DB.getInstance().getConnection();
		
		String sql = "SELECT * FROM `student` WHERE `stdno`=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, stdno);
		
		ResultSet rs = psmt.executeQuery();
		
		if(rs.next()){
			sb = new StudentBean();
			sb.setStdno(rs.getString(1));
			sb.setStdname(rs.getString(2));
			sb.setStdhp(rs.getString(3));
			sb.setStdyear(rs.getInt(4));
			sb.setStdaddress(rs.getString(5));
		}
		
		rs.close();
		conn.close();
		psmt.close();
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>student::modify</title>
	</head>
	<body>
		<h3>student 수정</h3>
		<a href="../1_JDBCTest.jsp">처음으로</a>
		<a href="./list.jsp">student 목록</a>
		
		<form action="./modifyProc.jsp" method="post">
			<table border="1">
				<tr>
					<td>학번</td>
					<td><input type="text" name="stdno" readonly value="<%= sb.getStdno() %>"/></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="stdname" value="<%= sb.getStdname() %>"/></td>
				</tr>
				<tr>
					<td>휴대폰</td>
					<td><input type="text" name="stdhp" value="<%= sb.getStdhp() %>"/></td>
				</tr>
				<tr>
					<td>학년</td>
					<td>
						<select name="stdyear">
							<option value="1">1학년</option>
							<option value="2">2학년</option>
							<option value="3">3학년</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="stdaddress" value="<%= sb.getStdaddress() %>"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" value="수정하기"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>