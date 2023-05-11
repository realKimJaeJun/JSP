<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../_header.jsp"></jsp:include>
<jsp:include page="./_cate${ param.cate }.jsp"></jsp:include>
<main id="board">
    <section class="list">                
        <form method="get">
        	<input type="hidden" name="cate" value="${ param.cate }">
        	<input type="hidden" name="tit" value="${ param.tit }">
            <input type="text" name="search" placeholder="제목 키워드, 글쓴이 검색" value="${ param.search }">
            <input type="submit" value="검색">
        </form>
        
        <table>
            <caption>글목록</caption>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>날짜</th>
                <th>조회</th>
            </tr>     
			<c:forEach var="vo" items="${ vos }">
	            <tr>
	                <td>${ pageStartNum }</td>
	                <c:set var="pageStartNum" value="${ pageStartNum - 1 }"/>
	                <td><a href="/Farmstory2/board/view.do?cate=${ param.cate }&tit=${ param.tit }&no=${ vo.no }&pg=${ currentPage }&search=${ param.search }">${ vo.title }&nbsp;[${ vo.comment }]</a></td>
	                <td>${ vo.nick }</td>
	                <td>${ vo.rdate }</td>
	                <td>${ vo.hit }</td>
	            </tr>
			</c:forEach>
        </table>

        <div class="page">
        	<c:if test="${ pageGroupStart gt 1 }">
            	<a href="/Farmstory2/board/list.do?cate=${ param.cate }&tit=${ param.tit }&pg=${ pageGroupStart - 1 }&search=${ param.search }" class="prev">이전</a>
            </c:if>
            <c:forEach var="i" begin="${ pageGroupStart }" end="${ pageGroupEnd }" step="1">
            	<a href="/Farmstory2/board/list.do?cate=${ param.cate }&tit=${ param.tit }&pg=${ i }&search=${ param.search }" class="num ${ currentPage eq i ? 'current' : 'off' }">${ i }</a>
            </c:forEach>
            <c:if test="${ pageGroupEnd lt lastPageNum }">
            	<a href="/Farmstory2/board/list.do?cate=${ param.cate }$tit=${ param.tit }&pg=${ pageGroupEnd+1 }&search=${ param.search }" class="next">다음</a>
            </c:if>
        </div>

        <a href="/Farmstory2/board/write.do?cate=${ param.cate }&tit=${ param.tit }" class="btn btnWrite">글쓰기</a>
        
    </section>
</article>
</section>
</div>
</main>
<jsp:include page="../_footer.jsp"></jsp:include>