/**
 * 날짜 : 2022/10/21
 * 이름 : 김재준
 * 내용 : 사용자 회원가입 유효성 검사
 */
 //데이터 검증에 사용하는 정규표현식
	
const reUid = /^[a-z]+[a-z0-9]{5,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,255}$/;
const reName = /^[ㄱ-힣]+$/;
const reNick = /^[a-zA-Zㄱ-힣][a-zA-Zㄱ-힣0-9 ]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

let isUidOk 	= false;
let isPassOk 	= false;
let isNameOk 	= false;
let isNickOk 	= false;
let isEmailOk 	= false;
let isEmailAuthOk = false;
let isHpOk 		= false;

let isEmailCheckSend = false; // 이메일 인증코드를 보냈는가?
let emailChecking = false; // 이메일 검사중 체크
let emailCode = 0; // 이메일 인증 코드

$(function(){
	
	// 아이디 검사하기
	$('input[name=uid]').keydown(function(){
		isUidOk = false;
		$('.uidResult').text('');
	});
	
	$('#btnIdCheck').click(function(){
		
		if(isUidOk) {
			return;
		}
		
		let uid = $('input[name=uid]').val();
		
		if (!uid.match(reUid)) {
			isUidOk = false;
			$('.uidResult').css('color','red').text('유효한 아이디가 아닙니다.');
			return;
		}
		
		let jsonData = {
				"uid" : uid
		};
		
		$('.uidResult').css('color','black').text('...');
			
		$.ajax({
			url: '/Farmstory2/user/checkUid.do',
			method:'get',
			data: jsonData,
			dataType:'json',
			success:(data)=>{
					if(data.result == 0) {
						isUidOk = true;
						$('.uidResult').css('color','green').text('사용 가능한 아이디 입니다.');
					} else {
						isUidOk = false;
						$('.uidResult').css('color','red').text('이미 사용중인 아이디 입니다.');
					}
			}
		});
	});
	
	// 비밀번호 검사하기
	$('input[name=pass2]').focusout(function(){
		let pass1 = $('input[name=pass1]').val();
		let pass2 = $('input[name=pass2]').val();
		
		if(pass2.match(rePass)) {
			if(pass1 == pass2) {
				isPassOk = true;
				$('.passResult').css('color', 'green').text('사용하실 수 있는 비밀번호 입니다.');
			} else {
				isPassOk =false;
				$('.passResult').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
			}
		} else {
			isPassOk = false;
			$('.passResult').css('color', 'red').text('비밀번호는 숫자,영문,특수문자 포함 8자리 이상 이어야 합니다.');
		}
	});
	
	// 이름 검사하기
	$('input[name=name]').focusout(function(){
		let name = $(this).val();
		
		if(name.match(reName)) {
			isNameOk = true;
			$('.nameResult').text('');
		} else {
			isNameOk = false;
			$('.nameResult').css('color','red').text('유효한 이름이 아닙니다.');
		}
	});
	
	// 별명 검사하기
	$('input[name=nick]').keydown(function(){
		isNickOk = false;
		$('.nickResult').text('');
	});
	
	$('#btnNickCheck').click(function(){
		
		if(isNickOk) {
			return;
		}
		
		let nick = $('input[name=nick]').val();
		
		if (!nick.match(reNick)) {
			isNickOk = false;
			$('.nickResult').css('color','red').text('유효한 별명이 아닙니다.');
			return;
		}
		
		let jsonData = {
				"nick" : nick
		};
		
		$.ajax({
			url: '/Farmstory2/user/checkNick.do',
			method:'get',
			data: jsonData,
			dataType:'json',
			success:(data)=>{
					if(data.result == 0) {
						isNickOk = true;
						$('.nickResult').css('color','green').text('사용 가능한 별명 입니다.');
					} else {
						isNickOk = false;
						$('.nickResult').css('color','red').text('이미 사용중인 별명 입니다.');
					}
			}
		});
	});
	
	// 이메일 검사하기
	// 이메일 수정시 유효성 검사
	$('input[name=email]').change(function(){
		const email = $(this).val();
		
		isEmailAuthOk = false; // 이메일 수정시 이메일 인증 해제
		isEmailCheckSend = false; // 이메일 수정시 전송된 코드의 유효성 해제
		$('.auth').hide(); // 이메일 수정시 인증코드 입력 창 숨기기 이메일이 변경 되었는데 인증 받기 방지
		
		if (email.match(reEmail)) {
			isEmailOk = true;
			$('.emailResult').text('');
		} else {
			isEmailOk = false;
			$('.emailResult').css('color','red').text('유효한 메일이 아닙니다.');
		}
	});
	
	// 이메일 인증 검사
	$('#btnEmailAuth').click(function(){
			
			if (isEmailAuthOk == true) {
				alert('이미 확인이 완료 되었습니다.');
				return;
			}
			
			if (emailChecking == true) { // 이메일 체크 중이면 중지
				alert('이메일 확인중 입니다.');
				return;
			} else { // 이메일 인증 시작
				emailChecking = true;
				// 이메일 검사중 수정 불가능하게 변경
				$('input[name=email]').attr('readonly', true);
			}
			
			const email = $('input[name=email]').val();
			
			$('.emailResult').css('color', 'black').text('이메일 전송 중 입니다.');
			
			$.ajax({
				url: '/Farmstory2/user/emailAuth.do',
				method: 'get',
				data: {"email":email},
				dataType: 'json',
				success: function(data) {
					if(data.status == 1) {
						// 메일 발송 성공
						emailCode = data.code;
						isEmailCheckSend = true; // 이메일 코드 전송됨
						emailChecking = false; // 검사 진행 끝
						$('input[name=email]').attr('readonly', false); // 이메일 수정 가능
						$('.emailResult').css('color','green').text('인증코드를 전송 했습니다. 이메일을 확인 하세요.');
						$('.auth').show();
					} else {
						// 메일 발송 실패
						emailChecking = false; // 검사 진행 끝
						$('input[name=email]').attr('readonly', false); // 이메일 수정 가능
						$('.emailResult').css('color','red').text('이메일 전송을 실패했습니다. 이메일을 확인 후 다시 시도 하시기 바랍니다.');
					}
				}
			});
		});
		
		// 이메일 인증코드 확인
		$('#btnEmailConfirm').click(function(){
		 	const code = $('input[name=auth]').val();
		 	
		 	// 입력한 코드가 이메일 코드와 맞고 이메일 코드가 전송된 상태일때
		 	if (code == emailCode && isEmailCheckSend == true) {
		 		isEmailAuthOk = true; // 이메일 인증 완료
		 		$('.emailResult').text('이메일이 인증 되었습니다.');
		 	}
		});
	
	// 휴대폰 검사하기
	$('input[name=hp]').focusout(function(){
		const hp = $(this).val();
		
		if (hp.match(reHp)) {
			isHpOk = true;
			$('.hpResult').text('');
		} else {
			isHpOk = false;
			$('.hpResult').css('color','red').text('유효한 번호가 아닙니다.');
		}
	});
	
	// 최종 폼 전송할 때
	$('.register > form').submit(function(){
		// ID 검증
		if(!isUidOk) {
			alert('아이디가 유효하지 않습니다.');
			return false;
		}
		// 비밀번호 검증
		if(!isPassOk) {
			alert('비밀번호가 유효하지 않습니다.');
			return false;
		}
		// 이름 검증
		if(!isNameOk) {
			alert('이름이 유효하지 않습니다.');
			return false;
		}
		// 별명 검증
		if(!isNickOk) {
			alert('별명이 유효하지 않습니다.');
			return false;
		}
		// 이메일 검증
		if(!isEmailOk) {
			alert('이메일이 유효하지 않습니다.');
			return false;
		}
		// 이메일 인증 검증
		if(!isEmailAuthOk) {
			alert('이메일을 인증 하셔야 합니다.');
			return false;
		}
		// 휴대폰 검증
		if(!isHpOk) {
			alert('휴대폰이 유효하지 않습니다.');
			return false;
		}
		
		return true;
	});
});