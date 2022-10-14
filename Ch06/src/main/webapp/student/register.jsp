<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>student::register</title>
	</head>
	<body>
		<h3>student 등록</h3>
		<a href="../1_JDBCTest.jsp">처음으로</a>
		<a href="./list.jsp">student 목록</a>
		
		<form action="./registerProc.jsp" method="post">
			<table border="1">
				<tr>
					<td>학번</td>
					<td><input type="text" name="stdno" placeholder="학번 입력" ></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="stdname" placeholder="이름 입력" ></td>
				</tr>
				<tr>
					<td>휴대폰</td>
					<td><input type="text" name="stdhp" placeholder="휴대폰 입력" ></td>
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
					<td><input type="text" name="stdaddress" placeholder="주소 입력" ></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" value="등록하기"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>