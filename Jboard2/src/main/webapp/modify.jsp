<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="./_header.jsp"/>
<main id="board">
    <section class="modify">
        <form action="/Jboard2/modify.do" method="post">
        	<input type="hidden" name="no" value="${article.no}"/>
        	<input type="hidden" name="pg" value="${article.pg}"/>
            <table border="0">
                <caption>글수정</caption>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" placeholder="제목을 입력하세요." value="${article.title}"/></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea name="content">${article.content}</textarea>
                    </td>
                </tr>
            </table>
            
            <div>
                <a href="/Jboard2/view.do?no=${article.no}&pg=${article.pg}" class="btn btnCancel">취소</a>
                <input type="submit" value="작성완료" class="btn btnComplete"/>
            </div>
        </form>

    </section>
</main>
<jsp:include page="./_footer.jsp"/>