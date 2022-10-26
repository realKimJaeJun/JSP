<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>logout</title>
	</head>
	<body>
<%
	// session에 등록된 데이터 전체 삭제
		session.invalidate();
	
	// 로그인 관련 쿠키 삭제
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie tempCookie : cookies){
				if(tempCookie.getName().equals("uid")){
					tempCookie.setMaxAge(0);
					tempCookie.setPath("/");
					response.addCookie(tempCookie);
				}
			}
		}
		response.sendRedirect("/Jboard1/user/login.jsp");
%>
		
	</body>
</html>