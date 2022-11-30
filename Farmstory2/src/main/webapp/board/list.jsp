<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../_header.jsp"/>
<jsp:include page="./_${group}.jsp"/>
<main id="board">
    <section class="list">                
        <form action="/Farmstory2/board/list.do">
            <input type="text" name="search" placeholder="제목 키워드, 글쓴이 검색">
            <input type="submit" value="검색">
        </form>
        
        <table border="0">
            <caption>글목록</caption>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>날짜</th>
                <th>조회</th>
            </tr>
            <c:forEach var="article" items="${articles}">           
            <tr>
                <td>${pageStartNum = pageStartNum - 1}</td>
                <td><a href="./view.do?group=${group}&cate=${cate}">${article.title}[${article.comment}]</a></td>
                <td>${article.nick}</td>
                <td>${article.rdate.substring(2, 10)}</td>
                <td>${article.hit}</td>
            </tr>
            </c:forEach>
        </table>

        <div class="page">
        	<c:if test="${pageGroupStart > 1 }">
            	<a href="./list.do?group=${group}&cate=${cate}&pg=${pageGroupStart}&search=${search}" class="prev">이전</a>
            </c:if>
            <c:forEach var="i" begin="${pageGroupStart}" end="${pageGroupEnd}">
            <a href="./list.do?group=${group}&cate=${cate}&pg=${i}&search=${search}" class="num ${currentPage == i?'current':'off'}">${i}</a>
            </c:forEach>
            <c:if test="${pageGroupEnd < lastPageNum}">
            <a href="./list.do?group=${group}&cate=${cate}&pg=${pageGroupEnd + 1}&search=${search}" class="next">다음</a>
            </c:if>
        </div>

        <a href="./write.do?group=${group}&cate=${cate}" class="btn btnWrite">글쓰기</a>
        
    </section>
</main>
</article>
    </section>
</div>
<jsp:include page="../_footer.jsp"/>