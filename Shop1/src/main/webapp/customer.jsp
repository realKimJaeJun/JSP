<%@page import="config.DBCP"%>
<%@page import="bean.CustBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

	List<CustBean> custs = new ArrayList<>();

	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from `customer`");
		
		while(rs.next()){
		CustBean cb = new CustBean();
		cb.setName(rs.getString(2));
	
		custs.add(cb);
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
		<title>customer</title>
	</head>
	<body>
		<h3>고객목록</h3>
		
		<a href="./customer.jsp">고객목록</a>
		<a href="./order.jsp">주문목록</a>
		<a href="./product.jsp">상품목록</a>
		
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>휴대폰</th>
				<th>주소</th>
				<th>가입일</th>
				<th>관리</th>
			</tr>
			<% for(CustBean ub : custs){ %>
			<tr>
				<td><%= ub.getCustid() %></td>
				<td><%= ub.getName() %></td>
				<td><%= ub.getHp() %></td>
				<td><%= ub.getAddr() %></td>
				<td><%= ub.getRdate() %></td>
			</tr>
			<% } %>
		</table>
	</body>
</html>