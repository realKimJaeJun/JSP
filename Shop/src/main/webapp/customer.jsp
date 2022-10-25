<%@page import="config.SQL"%>
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
		ResultSet rs = stmt.executeQuery(SQL.SELECT_CUSTOMER);
		
		while(rs.next()){
		CustBean cb = new CustBean();
		cb.setCustid(rs.getString(1));
		cb.setName(rs.getString(2));
		cb.setHp(rs.getString(3));
		cb.setAddr(rs.getString(4));
		cb.setRdate(rs.getString(5));
	
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
		
		<a href="/Shop/customer.jsp">고객목록</a>
		<a href="/Shop/order.jsp">주문목록</a>
		<a href="/Shop/product.jsp">상품목록</a>
		
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>휴대폰</th>
				<th>주소</th>
				<th>가입일</th>
			</tr>
			<% for(CustBean cb : custs){ %>
			<tr>
				<td><%= cb.getCustid() %></td>
				<td><%= cb.getName() %></td>
				<td><%= cb.getHp() %></td>
				<td><%= cb.getAddr() %></td>
				<td><%= cb.getRdate() %></td>
			</tr>
			<% } %>
		</table>
	</body>
</html>