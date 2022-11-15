<%@page import="bean.StudBean"%>
<%@page import="config.SQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="config.DBCP"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

	List<StudBean> studs = new ArrayList<>();

	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL.SELECT_STUDENT);
		
		while(rs.next()){
		StudBean sb = new StudBean();
		sb.setStdno(rs.getString(1));
		sb.setStdname(rs.getString(2));
		sb.setStdhp(rs.getString(3));
		sb.setStdyear(rs.getInt(4));
		sb.setStdaddress(rs.getString(5));
	
		studs.add(sb);
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
		<title>student</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
		<script>
		
		// 등록 눌렀을 경우
		
		$(function(){
			$('.btnRegister').click(function(){
				$('section').show().find('input[name=stdno]').val(stdno);				
				
			});
			$('.btnClose').click(function(){
				$('section').hide();
			});
			
			$('input[type=submit]').click(function(){
				
				let stdno = $('input[name=stdno]').val();
				
				let jsonData = {
						"stdno":stdno,
						"stdname":stdname,
						"stdhp":stdhp,
						"stdyear":stdyear,
						"stdaddress":stdaddress
				}
				
				$.post('./StudregisterProc.jsp', jsonData, function(data){
					
					if(data.result > 0){
						alert('등록완료!');
					}else{
						alert('등록실패!');
					}
				});
			});
			
		});
		
		</script>
	</head>
	<body>
		<h3>학생관리</h3>
		
		<a href="/College/lecture.jsp">강좌관리</a>
		<a href="/College/register.jsp">수강관리</a>
		<a href="/College/student.jsp">학생관리</a>
		
		<h4>학생목록</h4>
		
		<button class="btnRegister">등록</button>
		<table border="1">
			<tr>
				<th>학번</th>
				<th>이름</th>
				<th>휴대폰</th>
				<th>학년</th>
				<th>주소</th>
				
			</tr>
			<% for(StudBean sb : studs){ %>
			<tr>
				<td><%= sb.getStdno() %></td>
				<td><%= sb.getStdname() %></td>
				<td><%= sb.getStdhp() %></td>
				<td><%= sb.getStdyear() %></td>
				<td><%= sb.getStdaddress() %></td>
			</tr>
			<% } %>
		</table>
		<section style="display:none;">
			<h4>학생등록</h4>
			<table border="1">
				<button class="btnClose">닫기</button>
				<tr>
					<td>학번</td>
					<td><input type="text" name="stdno"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="stdname"></td>
				</tr>
				<tr>
					<td>휴대폰</td>
					<td><input type="text" name="stdhp"></td>
				</tr>
				<tr>
					<td>학년</td>
					<td><select name="stdyear" id="stdyear">
					<option value="1">1학년</option>
					<option value="2">2학년</option>
					<option value="3">3학년</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="stdaddress"></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" value="추가"/></td>
				</tr>
			</table>
		</section>
	</body>
</html>