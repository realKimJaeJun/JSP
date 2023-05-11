/*
	날짜 : 2022/11/23
	이름 : 김재준
	내용 : 아이디 비밀번호 찾기
 */

const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

let isEmailOk = false; // 이메일 유효성 확인
let isEmailAuthOk = false; // 이메일 인증 확인
let emailChecking = false; // 이메일 체크 확인중
let isEmailCheckSend = false; // 이메일 인증코드 전송 체크
let emailCode = 0; // 이메일 인증 코드

$(()=>{
	// 이메일 입력 변경시 초기화
	$('input[name=email]').change(function(){
		const email = $(this).val();
		
		isEmailAuthOk = false; // 이메일 수정시 인증 초기화
		isEmailCheckSend = false; // 이메일 수정시 전송된 코드의 유효성 해제
		$('input[name=auth]').attr('disabled', true); // 인증 비활성화
		
		if (email.match(reEmail)) {
			isEmailOk = true;
			$('#message').text('');
		} else {
			isEmailOk = false;
			$('#message').css('color','red').text('유효한 메일이 아닙니다.');
		}
	});
	
	$('.btnAuth').click(()=>{
		
		if (emailChecking == true) {
			alert('이메일 확인중 입니다.');
			return;
		} else {
			emailChecking = true;
			$('input[name=email]').attr('readonly', true);
			$('#message').css('color','black').text('이메일 전송 중 입니다.');
		}
		
		const name = $('input[name=name]').val();
		const email = $('input[name=email]').val();
		const uid = $('input[name=uid]').val();
		
		$.ajax({
			url: '/Farmstory2/user/emailAuth.do',
			method: 'get',
			data: {"name":name,"email":email,"uid":uid},
			dataType: 'json',
			success: (data)=>{
				if(data.status == 1) {
					// 메일 발송 성공
					emailCode = data.code;
					isEmailCheckSend = true;
					emailChecking = false;
					$('input[name=email]').attr('readonly', false);
					$('input[name=auth]').attr('disabled', false); // 인증 활성화
					$('#message').css('color','green').text('인증코드를 전송 하였습니다.');
				} else {
					// 메일 발송 실패
					emailChecking = false;
					$('input[name=email]').attr('readonly', false);
					$('#message').css('color','red').text('이메일 전송을 실패 했습니다. 이름과 이메일을 확인 후 다시 시도 하시기 바랍니다.');
				}
			}
		});
	});
	
	// 이메일 인증코드 확인
	$('.btnConfirm').click(function(){
	 	const code = $('input[name=auth]').val();
	 	
	 	// 입력한 코드가 이메일 코드와 맞고 이메일 코드가 전송된 상태일때
	 	if (code == emailCode && isEmailCheckSend == true) {
			isEmailAuthOk = true;
			$('#message').css('color','green').text('이메일 인증이 완료되었습니다.');
	 	} else {
			isEmailAuthOk = false;
			$('#message').css('color','red').text('인증코드가 틀렸습니다.');
	 	}
	});
	
	// 다음 클릭시 인증 확인
	$('.btnNext').click(()=>{
		// 이메일 유효성 검증
		if(!isEmailOk) {
			alert('이메일이 유효하지 않습니다.');
			return false;
		}
		// 이메일 인증 검증
		if(!isEmailAuthOk) {
			alert('이메일을 인증 하셔야 합니다.');
			return false;
		}
		
		const name = $('input[name=name]').val();
		const email = $('input[name=email]').val();
		const uid = $('input[name=uid]').val(); 
		
		if (name != null) {
			location.href = "/Farmstory2/user/findIdResult.do?email="+email;
		} else if (uid != null) {
			location.href = "/Farmstory2/user/findPwChange.do?email="+email;
		}
		
		return false;
	});
});