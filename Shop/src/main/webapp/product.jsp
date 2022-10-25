<%@page import="config.SQL"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="config.DBCP"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.ProdBean"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

	List<ProdBean> prods = new ArrayList<>();

	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL.SELECT_PRODUCT);
		
		while(rs.next()){
		ProdBean pb = new ProdBean();
		pb.setProdNo(rs.getInt(1));
		pb.setProdName(rs.getString(2));
		pb.setStock(rs.getInt(3));
		pb.setPrice(rs.getInt(4));
		pb.setCompany(rs.getString(5));
	
		prods.add(pb);
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
		<title>product</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
		<script src="/Shop/js/prodorder.js"></script>
		<script>
		
		// 주문 눌렀을 경우
		
	$(document).ready(function(){
		
		$(document).on('click', '#btnOrder', function(){
			
			let ordno = $(this).parent().parent().children('td:eq(0)').text();		
                prodorder(ordno);
		
		
		$(document).on('click', '#btnOrder2', function(){
			
			let orderProduct  = $('input[name=orderProduct]').val();
			let ordercount = $('input[name=orderCount]').val();
			let orderid   = $('input[name=orderId]').val();
			
			let jsonData = {
					"orderProduct":orderProduct,
					"orderCount":orderCount,
					"orderId":orderId
					};
			
			console.log(jsonData);
			
			$.ajax({
				url: '/Shop/js/prodorder.jsp',
				method: 'post',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					if(data.result == 1){
					alert('주문완료!');
						}
					}
				});
			});
		});
	});
		</script>
	</head>
	<body>
		<h3>상품목록</h3>
		
		<a href="/Shop/customer.jsp">고객목록</a>
		<a href="/Shop/order.jsp">주문목록</a>
		<a href="/Shop/product.jsp">상품목록</a>
		
		<table border="1">
			<tr>
				<th>상품번호</th>
				<th>상품명</th>
				<th>재고량</th>
				<th>가격</th>
				<th>제조사</th>
				<th>주문</th>
				
			</tr>
			<% for(ProdBean pb : prods){ %>
			<tr>
				<td><%= pb.getProdNo() %></td>
				<td><%= pb.getProdName() %></td>
				<td><%= pb.getStock() %></td>
				<td><%= pb.getPrice() %></td>
				<td><%= pb.getCompany() %></td>
				<td><button type="button" id="btnOrder">주문</button></td>
			</tr>
			<% } %>
		</table>
	</body>
</html>