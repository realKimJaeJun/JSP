/**
 * 날짜 : 2022/10/21
   이름 : 김철학
   내용 : 사용자 회원가입 유효성 검사 
 */
// 데이터 검증에 사용하는 정규표현식
let reUid   = /^[a-z]+[a-z0-9]{5,19}$/g;
let rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
let reName  = /^[가-힣]+$/ 
let reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

// 폼 데이터 검증 결과 상태변수
let isUidOk   = false;
let isPassOk  = false;
let isNameOk  = false;
let isNickOk  = false;
let isEmailOk = false;
let isHpOk 	  = false;
let isEmailAuthOk = false;
let isEmailAuthCodeOk = false;
let receivedCode = 0;

$(function(){
	
	
	// 아이디 유효성 검증 & 중복체크
	$('input[name=uid]').keydown(function(){
		isUidOk = false;
	});
	
	$('#btnIdCheck').click(function(){			
					
		let uid = $('input[name=uid]').val();
		
		if(isUidOk){
			return;
		}
		
		if(!uid.match(reUid)) {
			isUidOk = false;
			$('.resultUid').css('color', 'red').text('유효한 아이디가 아닙니다.');
			return;
		}
		
		let jsonData = {"uid": uid};
		
		$('.resultUid').css('color', 'black').text('...');
		
		setTimeout(()=>{
			
			$.ajax({
				url: '/Jboard2/user/checkUid.do',
				method: 'get',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					if(data.result == 0){
						$('.resultUid').css('color', 'green').text('사용 가능한 아이디 입니다.');
						isUidOk = true;
					}else{
						isUidOk = false;
						$('.resultUid').css('color', 'red').text('이미 사용중인 아이디 입니다.');
					}					
				}				
			});
			
		}, 500);
	});
	
	// 비밀번호 일치여부 확인
	$('input[name=pass2]').focusout(function(){
		
		let pass1 = $('input[name=pass1]').val();
		let pass2 = $(this).val();
		
		if(pass1 == pass2){
			
			if(pass2.match(rePass)){
				isPassOk = true;
				$('.resultPass').css('color', 'green').text('사용하실 수 있는 비밀번호 입니다.');
			}else{
				isPassOk = false;
				$('.resultPass').css('color', 'red').text('비밀번호가 일치하지 않습니다.');	
			}
			
		}else{
			isPassOk = false;
			$('.resultPass').css('color', 'red').text('숫자,영문,특수문자 포함 5자리 이상 이어야 합니다.');
		}			
		
	});
	
	// 이름 유효성 검증
	$('input[name=name]').focusout(function(){
		
		let name = $(this).val();
		
		if(!name.match(regName)){
			isNameOk = false;
			$('.resultName').css('color', 'red').text('이름은 한글 2자 이상 이어야 합니다.');
		}else{
			isNameOk = true;
			$('.resultName').text('');
		}			
	});
	
	// 별명 유효성 검사 & 중복체크
	$('input[name=nick]').keydown(function(){
		isNickOk = false;
	});
	
	$('#btnNickCheck').click(function(){			
					
		let nick = $('input[name=nick]').val();
		
		if(isNickOk){
			return;
		}
		
		if(!nick.match(reNick)) {
			isNickOk = false;
			$('.resultNick').css('color', 'red').text('유효한 별명이 아닙니다.');
			return;
		}
		
		let jsonData = {"nick": nick};
		
		$('.resultNick').css('color', 'black').text('...');
		
		setTimeout(()=>{
			
			$.ajax({
				url: '/Jboard2/user/checkNick.do',
				method: 'get',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					if(data.result == 0){
						$('.nickResult').css('color', 'green').text('사용 가능한 별명 입니다.');
						isNickOk = true;
					}else{
						$('.nickResult').css('color', 'red').text('이미 사용중인 별명 입니다.');
						isNickOk = false;
					}					
				}				
			});
			
		}, 500);
	});
	
	
	// 이메일 유효성 검사
	$('input[name=email]').focusout(function(){
		
		let email = $(this).val();
		
		if(email.match(regEmail)){
			isEmailOk = false;
			$('.emailResult').css('color', 'red').text('유효하지 않는 이메일 입니다.');
		}else{
			isEmailOk = true;
			$('.emailResult').text('');
		}			
	});
	
	// 이메일 인증코드 발송 클릭
	
	$('#btnEmail').click(function(){
		
		$(this).hide();
		let email = $('input[name=email]').val();
		console.log('here1 : '+email);
		
		if(email == ''){
			alert('이메일을 입력 하세요.');
			return;
		}
		
		if(isEmailAuthOk){
			console.log('here2');
			return;
		}
		isEmailAuthOk = true;
		
		$('.resultEmail').text('인증코드 전송 중 입니다. 잠시만 기다리세요...');
		console.log('here3');
		
		setTimeout(function(){
			console.log('here4');
			$.ajax({
				url: '/Jboard2/user/emailAuth.do',
				method: 'get',
				data: {"email":email},
				dataType: 'json',
				success: function(data){
					//console.log(data);
					
					if(data.status > 0){
						// 메일전송 성공
						console.log('here5');
						isEmailAuthOk = true;						
						$('.resultEmail').text('인증코드를 전송 했습니다. 이메일을 확인 하세요.');
						$('.auth').show();
						receivedCode = data.code;
						
					}else{
						// 메일전송 실패
						isEmailAuthOk = false;
						alert('메일전송이 실패 했습니다.')
					}
				}
			});
				
			}, 1000);
		});
	
		
		// 이메일 인증코드 확인 버튼
		$('#btnEmailConfirm').click(function(){
			
			let code = $('input[name=auth]').val();
			
			if(code == ''){
				alert('이메일 확인 후 인증코드를 입력하세요.');
				return;
			}
			
			if(code == receivedCode){
				isEmailAuthCodeOk = true;
				$('input[name=email]').attr('readonly', true);
				$('.resultEmail').text('이메일이 인증 되었습니다.');
				$('.auth').hide();
			}else{
				isEmailAuthCodeOk = false;
				alert('인증코드가 틀립니다.');
			}
			
		});
	
	// 휴대폰 유효성 검사
	$('input[name=hp]').focusout(function(){
		
		let hp = $(this).val();
		
		if(!hp.match(regHp)){
			isHpOk = false;
			$('.resultHp').css('color', 'red').text('유효하지 않는 휴대폰 입니다.');
		}else{
			isHpOk = true;
			$('.resultHp').text('');			
		}			
	});
	
	
	// 최종 폼 전송할 때
	$('.register > form').submit(function(){
		
		// 아이디 검증
		if(!isUidOk){
			alert('아이디를 확인 하십시요.');
			return false;
		}
		
		// 비밀번호 검증
		if(!isPassOk){
			alert('비밀번호가 유효하지 않습니다.');
			return false;
		}
		
		// 이름 검증
		if(!isNameOk){
			alert('이름이 유효하지 않습니다.');
			return false;
		}
		
		// 별명 검증
		if(!isNickOk){
			alert('별명이 유효하지 않습니다.');
			return false;
		}
		
		// 이메일 검증
		if(!isEmailOk){
			alert('이메일이 유효하지 않습니다.');
			return false;
		}
		
		// 이메일 인증 검증
		if(!isEmailAuthCodeOk){
			alert('이메일 인증에 실패했습니다.');
			return false;
		}
		
		// 휴대폰 검증
		if(!isHpOk){
			alert('휴대폰이 유효하지 않습니다.');
			return false;
		}
		
		return true;
	});	
}); 

 
 