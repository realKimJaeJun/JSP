<%@page import="bean.RegBean"%>
<%@page import="config.SQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="config.DBCP"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

	List<RegBean> regs = new ArrayList<>();

	try{
		Connection conn = DBCP.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL.SELECT_REGISTER);
		
		while(rs.next()){
		RegBean rb = new RegBean();
		rb.setRegstdno(rs.getString(1));
		rb.setReglecno(rs.getString(2));
		rb.setRegmidscore(rs.getInt(3));
		rb.setRegfinalscore(rs.getInt(4));
		rb.setRegtotalscore(rs.getInt(5));
		rb.setReggrade(rs.getString(6));
		rb.setLecname(rs.getString(7));
		rb.setStdname(rs.getString(8));
	
		regs.add(rb);
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
		<title>register</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
		<script>
		
		// 학번 검색
		
		// 수강신청
		
		$(function(){
			$('.btnRegister').click(function(){
				$('section').show().find('input[name=regstdno]').val(regstdno);				
				
			});
			$('.btnClose').click(function(){
				$('section').hide();
			});
			
			$('input[type=submit]').click(function(){
				
				let stdno = $('input[name=regstdno]').val();
				
				let jsonData = {
						"regstdno":stdno,
						"regstdname":stdname,
						"reglecname":lecname
				}
				
				$.post('./RegregisterProc.jsp', jsonData, function(data){
					
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
		<h3>수강관리</h3>
		
		<a href="/College/lecture.jsp">강좌관리</a>
		<a href="/College/register.jsp">수강관리</a>
		<a href="/College/student.jsp">학생관리</a>
		
		<h4>수강현황</h4>
		<input type="text" name="stdsearch" value=""/>
		<input type="button" onclick="registerSearch()" class="btnStdSearch" value="찾기"></input>
		<button class="btnRegister">수강신청</button>
		<table border="1" id="stdList">
			<tr>
				<th>학번</th>
				<th>이름</th>
				<th>강좌명</th>
				<th>강좌코드</th>
				<th>중간시험</th>
				<th>기말시험</th>
				<th>총점</th>
				<th>등급</th>
			</tr>
			<% for(RegBean rb : regs){ %>
			<tr>
				<td><%= rb.getRegstdno() %></td>
				<td><%= rb.getStdname() %></td>
				<td><%= rb.getLecname() %></td>
				<td><%= rb.getReglecno() %></td>
				<td><%= rb.getRegmidscore() %></td>
				<td><%= rb.getRegfinalscore() %></td>
				<td><%= rb.getRegtotalscore() %></td>
				<td><%= rb.getReggrade() %></td>
			</tr>
			<% } %>
		</table>
		<section style="display:none;">
			<h4>수강신청</h4>
			<table border="1">
				<button class="btnClose">닫기</button>
				<tr>
					<td>학번</td>
					<td><input type="text" name="regstdno"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="regstdname"></td>
				</tr>
				<tr>
					<td>신청강좌</td>
					<td><select name="reglecname">
					<option value=""></option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" value="신청"/></td>
				</tr>
			</table>
		</section>
	</body>
</html>