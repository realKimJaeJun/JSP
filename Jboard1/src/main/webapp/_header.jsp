<%@page import="kr.co.jboard1.bean.UserBean"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserBean ub = (UserBean)session.getAttribute("sessUser");

	if(ub == null){
		response.sendRedirect("/Jboard1/user/login.jsp?success=101");
		return;
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글목록</title>
    <link rel="stylesheet" href="./css/style.css"/>    
</head>
<body>
<%
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
		for(Cookie tempCookie : cookies){
			if(tempCookie.getName().equals("uid")){
				// 쿠키값으로 자동 로그인
				String ids = tempCookie.getValue();
				session.setAttribute("uid", ids);
			}
		}
	}
%>
	<%
		// object타입이라 캐스팅 해야함 
		String uid = (String)session.getAttribute("uid");
	%>
	
    <div id="wrapper">
        <header>
        
            <h3>Board System v1.0</h3>
            <p>
                <span><%= ub.getNick() %></span>님 반갑습니다.
                <a href="/Jboard1/user/proc/logout.jsp">[로그아웃]</a>
            </p>
        </header>