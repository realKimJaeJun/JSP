<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../_header.jsp"/>
<jsp:include page="./_${group}.jsp"/>
<script>

	$(document).ready(function(){
		
		// 글 삭제
		$('.btnRemove').click(function(){
			let isDelete = confirm('정말 삭제 하시겠습니까?');
			if(isDelete){
				return true;
			}else{
				return false;
			}
		});
		
		
		// 댓글 삭제
		$(document).on('click', '.remove', function(e){
			e.preventDefault();
			
			let isDeleteOk = confirm('정말 삭제 하시겠습니까?');
			
			if(isDeleteOk){
				
				let article = $(this).closest('article');
				let no = $(this).attr('data-no');
				let parent = $(this).attr('data-parent');
				
				let jsonData = {"no": no, "parent":parent};
				
				$.ajax({
					url: '/Farmstory2/commentDelete.do',
					type: 'GET',
					data: jsonData,
					dataType: 'json',
					success: function(data){
						if(data.result == 1){
							alert('댓글이 삭제되었습니다.');							
							article.hide();
						}
					}
				});
			}
		});
		
		// 댓글 수정
		$(document).on('click', '.modify', function(e){
			e.preventDefault();
			
			let txt   = $(this).text();
			let p_tag = $(this).parent().prev();
			
			if(txt == '수정'){
				// 수정모드
				$(this).text('수정완료');				
				p_tag.attr('contentEditable', true);
				p_tag.focus();	
			}else{
				// 수정완료
				$(this).text('수정');	
				
				let no = $(this).attr('data-no');
				let content = p_tag.text();
				
				let jsonData = {
					"no": no,
					"content": content
				};
				
				console.log(jsonData);
				
				$.ajax({
					url: '/Farmstory2/commentModify.do',
					type: 'POST',
					data: jsonData,
					dataType: 'json',
					success: function(data){
						
						if(data.result == 1){
							alert('댓글이 수정되었습니다.');
							p_tag.attr('contentEditable', false);
						}
					}
				});
			}
		});
		
		// 댓글 작성
		$('.commentForm > form').submit(function(){
			
			let no       = $(this).children('input[name=no]').val();
			let uid      = $(this).children('input[name=uid]').val();
			let textarea = $(this).children('textarea[name=content]');
			let content  = textarea.val();
			
			if(content == ''){
				alert('댓글을 작성하세요.');
				return false;
			}
			
			let jsonData = {
				"no": no,
				"uid": uid,
				"content": content
			};
			
			$.ajax({
				url: '/Farmstory2/commentWrite.do',
				method: 'POST',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					console.log(data);
					
					if(data.result > 0){
						
						let article = "<article>";
							article += "<span class='nick'>"+data.nick+"</span>";
							article += "<span class='date'>"+data.date+"</span>";
							article += "<p class='content'>"+data.content+"</p>";
							article += "<div>";
							article += "<a href='#' class='remove' data-no='"+data.no+"' data-parent='"+data.parent+"'>삭제</a>";
							article += "<a href='#' class='modify' data-no='"+data.no+"'>수정</a>";
							article += "</div>";
							article += "</article>";
						
							
						$('.commentList > .empty').hide();
						$('.commentList').append(article);
						textarea.val('');
					}
				}
			});

			return false;
		});
	});
</script>
<main id="board">
    <section class="view">
        
        <table border="0">
            <caption>글보기</caption>
            <tr>
                <th>제목</th>
                <td><input type="text" name="title" readonly value="${article.title}" /></td>
            </tr>
            <c:if test="${article.file > 0}">
            <tr>
                <th>파일</th>
                <td><a href="/Farmstory2/board/view.do?group=${group}&cate=${cate}&parent=${article.no}">${article.oriName}</a>&nbsp;<span>${article.download}</span>회 다운로드</td>
            </tr>
            </c:if>
            <tr>
                <th>내용</th>
                <td>
                    <textarea name="content" readonly>${article.content}</textarea>
                </td>
            </tr>                    
        </table>
        
        <div>
        	<c:if test="${sessUser.uid.equals(article.uid)}">
	            <a href="#" class="btn btnRemove">삭제</a>
	            <a href="/Farmstory2/board/modify.do?group=${group}&cate=${cate}&no=${article.no}&pg=${article.pg}" class="btn btnModify">수정</a>
            </c:if>
            <a href="/Farmstory2/board/list.do?group=${group}&cate=${cate}" class="btn btnList">목록</a>
        </div>

        <!-- 댓글목록 -->
        <section class="commentList">
            <h3>댓글목록</h3>                   
			<c:forEach var="comment" items="${comments}">
	            <article>
	                <span class="nick">${comment.nick}</span>
	                <span class="date">${comment.rdate.substring(2, 10)}</span>
	                <p class="content">${comment.content}</p>
	                <c:if test="">                        
		                <div>
		                    <a href="#" class="remove">${comment.no}</a>
		                    <a href="#" class="modify">${comment.no}</a>
		                </div>
	                </c:if>
	            </article>
            </c:forEach>
			
			<c:if test="${comment.size == 0}">
            <p class="empty">등록된 댓글이 없습니다.</p>
            </c:if>

        </section>

        <!-- 댓글쓰기 -->
        <section class="commentForm">
            <h3>댓글쓰기</h3>
            <form action="#">
            	<input type="hidden" name="no" value="${comment.no}"/>
            	<input type="hidden" name="uid" value="${sessUser.uid}"/>
                <textarea name="content" placeholder="댓글을 입력하세요"></textarea>
                <div>
                    <a href="#" class="btn btnCancel">취소</a>
                    <input type="submit" value="작성완료" class="btn btnComplete"/>
                </div>
            </form>
        </section>

    </section>
</main>
</article>
    </section>
</div>
<jsp:include page="../_footer.jsp"/>