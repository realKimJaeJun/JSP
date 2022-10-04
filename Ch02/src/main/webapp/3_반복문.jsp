<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>3_반복문</title>
		<%-- 
			날짜 : 2022/10/04
			이름 : 김재준
			내용 : JSP 반복문 실습하기
			
		 --%>
	</head>
	<body>
		<h3>반복문</h3>
		
		<h4>for</h4>
		<%
			for(int i=1; i<=3; i++){
				out.println("<p>i : "+i+"</p>");
			}
		%>
		
		<% for(int k=1; k<=3; k++){%>
			<p>k : <%= k %></p>
		<% } %>
		
		<h4>while</h4>
		<%
			int j=1;
			while(j<=3){
		%>
			<p>j : <%= j %></p>
		<%
				j++;
			}
		%>
		
		<h4>구구단</h4>
		<table border="1">
			<tr>
			<%
				for(int i=2; i<10; i++){
					out.print("<th width=200>"+i+"단</th>");
				}
			%>
			</tr>
			<%
				for(int i=1; i<10; i++){
			%>
			<tr>
					<%for(int h=2; h<10; h++){
						out.print("<td align=center>"+h+" x "+i+" = "+h*i+"</td>");
					}%>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>