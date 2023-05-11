<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sub">
    <div>
        <img src="/Farmstory2/img/sub_top_tit3.png" alt="CROP TALK">
    </div>
    <section class="cate3">
        <aside>
            <img src="/Farmstory2/img/sub_aside_cate3_tit.png" alt="농작물이야기">
            <ul class="lnb">
                <li class="${ param.tit eq 1 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=3&tit=1">농작물이야기</a></li>
                <li class="${ param.tit eq 2 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=3&tit=2">텃밭가꾸기</a></li>
                <li class="${ param.tit eq 3 ? 'on' : 'off' }"><a href="/Farmstory2/board/list.do?cate=3&tit=3">귀농학교</a></li>
            </ul>
        </aside> 
        <article>
            <nav>
                <img src="/Farmstory2/img/sub_nav_tit_cate3_tit${ param.tit }.png" alt="농작물이야기">
                <p>HOME > 농작물이야기 > 
	                <c:choose>
	                	<c:when test="${ param.tit eq 1 }">
	                		<em>농작물이야기</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 2 }">
	                		<em>텃밭가꾸기</em>
	                	</c:when>
	                	<c:when test="${ param.tit eq 3 }">
	                		<em>귀농학교</em>
	                	</c:when>
					</c:choose>
                </p>
            </nav>