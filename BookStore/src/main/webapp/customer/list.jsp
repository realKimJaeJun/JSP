<%@page import="bean.CustBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<CustBean> custs = new ArrayList<>();

	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from `prodomer`");
		
		while(rs.next()){
		CustBean ub = new CustBean();
		ub.setCustId(rs.getInt(1));
		ub.setName(rs.getString(2));
		ub.setAddress(rs.getString(3));
		ub.setPhone(rs.getString(4));
	
		custs.add(ub);
		}

		rs.close();
		stmt.close();
		conn.close();		
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>list</title>
	</head>
	<body>
		<h3>고객목록</h3>
		
		<a href="../index.jsp">처음으로</a>
		<a href="./register.jsp">고객등록</a>
		<table border="1">
			<tr>
				<th>번호</th>
				<th>이름</th>
				<th>주소</th>
				<th>휴대폰</th>
				<th>관리</th>
			</tr>
			<% for(CustBean ub : custs){ %>
			<tr>
				<td><%= ub.getCustId() %></td>
				<td><%= ub.getName() %></td>
				<td><%= ub.getAddress() %></td>
				<td><%= ub.getPhone() %></td>
				<td>
					<a href="./modify.jsp?custId=<%= ub.getCustId() %>">수정</a>
					<a href="./delete.jsp?custId=<%= ub.getCustId() %>">삭제</a>
				</td>
			</tr>
			<% } %>
		</table>
	</body>
</html>