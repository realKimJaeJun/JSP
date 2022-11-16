<%@page import="kr.co.farmstory1.dao.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String group   = request.getParameter("group");
	String cate    = request.getParameter("cate");
	String no = request.getParameter("no");
	String pg = request.getParameter("pg");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	ArticleDAO.getInstance().updateArticle(no, title, content);

	response.sendRedirect("/Farmstory1/board/view.jsp?group="+group+"&cate="+cate+"&pg="+pg+"&no="+no+"&result=201");
%>