<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sub">
    <div>
        <img src="/Farmstory2/img/sub_top_tit5.png" alt="COMMUNITY">
    </div>
    <section class="cate5">
        <aside>
            <img src="/Farmstory2/img/sub_aside_cate5_tit.png" alt="커뮤니티">
            <ul class="lnb">
                <li class="${ param.tit eq 1 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=5&tit=1">공지사항</a></li>
                <li class="${ param.tit eq 2 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=5&tit=2">오늘의식단</a></li>
                <li class="${ param.tit eq 3 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=5&tit=3">나도요리사</a></li>
                <li class="${ param.tit eq 4 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=5&tit=4">1:1고객문의</a></li>
                <li class="${ param.tit eq 5 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=5&tit=5">자주묻는질문</a></li>
            </ul>
        </aside>
        <article>
            <nav>
                <img src="/Farmstory2/img/sub_nav_tit_cate5_tit${ param.tit }.png" alt="공지사항">
                <p>HOME > 커뮤니티 > 
                	<c:choose>
	                	<c:when test="${ param.tit eq 1 }">
	                		<em>공지사항</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 2 }">
	                		<em>오늘의식단</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 3 }">
	                		<em>나도요리사</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 4 }">
	                		<em>1:1고객문의</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 5 }">
	                		<em>자주묻는질문</em>
	                	</c:when>
					</c:choose>
                </p>
            </nav>