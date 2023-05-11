<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../_header.jsp"></jsp:include>
<main id="board">
    <section class="modify">

        <form action="/Farmstory2/board/modify.do" method="post">
        	<input type="hidden" name="cate" value="${ param.cate }">
        	<input type="hidden" name="tit" value="${ param.tit }">
        	<input type="hidden" name="search" value="${ param.search }">
        	<input type="hidden" name="pg" value="${ param.pg }">
        	<input type="hidden" name="no" value="${ param.no }">
            <table>
                <caption>글수정</caption>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" value="${ avo.title }" placeholder="제목을 입력하세요" /></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea name="content">${ avo.content }</textarea>
                    </td>
                </tr>
            </table>
            
            <div>
                <a href="/Farmstory2/board/view.do?cate=${ param.cate }&tit=${ param.tit }&search=${ param.search }&pg=${ param.pg }&no=${ param.no }" class="btn btnCancel">취소</a>
                <input type="submit" value="작성완료" class="btn btnComplete"/>
            </div>
        </form>

    </section>
</main>
<jsp:include page="../_footer.jsp"></jsp:include>