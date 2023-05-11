<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../_header.jsp"></jsp:include>
<script>
	const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,255}$/;
	
	$(()=>{
		// 변경하기
		$('.btnNext').click(()=>{
			const pass1 = $('input[name=pass1]').val();
			const pass2 = $('input[name=pass2]').val();
			
			if(pass2.match(rePass)) {
				if(pass1 == pass2) {
					$('.passResult').css('color', 'green').text('');
				} else {
					$('.passResult').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
					return false;
				}
			} else {
				$('.passResult').css('color', 'red').text('비밀번호는 숫자,영문,특수문자 포함 8자리 이상 이어야 합니다.');
				return false;
			}
			
			const uid = $('input[name=uid]').val();
			
			$.ajax({
				url: '/Farmstory2/user/findPwChange.do',
				method: 'post',
				data: {"uid":uid,"pass":pass2},
				dataType: 'json',
				success: (data)=>{
					if(data.result > 0) {
						// 비밀번호 변경 성공
						alert("비밀변호 변경에 성공 했습니다.");
					} else {
						// 비밀번호 변경 실패
						return false;
					}
				}
			});
		});
	});
</script>
<main id="user">
    <section class="find findPwChange">
        <form action="#">
        	<input type="hidden" name="uid" value="${ vo.uid }">
            <table>
                <caption>비밀번호 변경</caption>                        
                <tr>
                    <td>아이디</td>
                    <td>${ vo.uid }</td>
                </tr>
                <tr>
                    <td>새 비밀번호</td>
                    <td>
                        <input type="password" name="pass1" placeholder="새 비밀번호 입력"/>
                    </td>
                </tr>
                <tr>
                    <td>새 비밀번호 확인</td>
                    <td>
                        <input type="password" name="pass2" placeholder="새 비밀번호 입력"/>
                        <br/><span class="passResult"></span>
                    </td>
                </tr>
            </table>                                        
        </form>
        
        <p>
            비밀번호를 변경해 주세요.<br>
            영문, 숫자, 특수문자를 사용하여 8자 이상 입력해 주세요.                    
        </p>

        <div>
            <a href="/Farmstory2/user/login.do" class="btn btnCancel">취소</a>
            <a href="/Farmstory2/user/login.do" class="btn btnNext">다음</a>
        </div>
    </section>
</main>
<jsp:include page="../_footer.jsp"></jsp:include>