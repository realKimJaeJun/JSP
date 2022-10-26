<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link rel="stylesheet" href="/Jboard1/css/style.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
<%
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie tempCookie : cookies) {
			if (tempCookie.getName().equals("uid")) {
				response.sendRedirect("/Jboard1/list.jsp");
			}
		}
	}
%>
    <div id="wrapper">
        <header>
            <h3>Board System v1.0</h3>
        </header>