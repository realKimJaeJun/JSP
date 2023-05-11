<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../_header.jsp"></jsp:include>
<script>
	$(()=>{
		$('.btnNext').click(()=>{
			if($('input[class=terms]').is(':checked') && 
				$('input[class=privacy]').is(':checked')) {
				return true;
			} else {
				alert('동의 체크를 하셔야 합니다.');
				return false;
			}
		});
	});
</script>
<main id="user">
    <section class="terms">
        <table border="1">
            <caption>사이트 이용약관</caption>
            <tr>
                <td>
                    <textarea name="terms" readonly="readonly">${ tvo.terms }</textarea>
                    <label><input type="checkbox" class="terms">&nbsp;동의합니다.</label>
                </td>
            </tr>
        </table>

        <table border="1">
            <caption>개인정보 취급방침</caption>
            <tr>
                <td>
                    <textarea name="privacy" readonly="readonly">${ tvo.privacy }</textarea>
                    <label><input type="checkbox" class="privacy">&nbsp;동의합니다.</label>
                </td>
            </tr>
        </table>
        
        <div>
            <a href="/Farmstory2/user/login.do" class="btn btnCancel">취소</a>
            <a href="/Farmstory2/user/register.do" class="btn btnNext">다음</a>
        </div>

    </section>
</main>
<jsp:include page="../_footer.jsp"></jsp:include>