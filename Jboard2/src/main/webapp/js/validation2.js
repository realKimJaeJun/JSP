/**
 * 날짜 : 2022/10/21
   이름 : 김재준
   내용 : 사용자 회원가입 유효성 검사 
 */
 
$('button[name=Delete]').click(()=>{
	const chk = confirm('삭제 확인');
	const uid = $('input[name=uid]').val();
	
	if(check == true){
		$.ajax({
			url: '/Jboard2/user/delete.do',
				method: 'get',
				data: {"uid":uid},
				dataType: 'json',
				success:(data)=>{
					if(data.result > 0){
						alert('탈퇴 되었습니다.');
						location.href = "/Jboard2/user/login.do";
					}else{
						alert('오류');
						return false;
					}
				}
		});
	}
	return false;
});

const uid = $('input[name=uid]').val();
const pass = $('input[name=pass]').val();
const name = $('input[name=name]').val();
const nick = $('input[name=nick]').val();
const email = $('input[name=email]').val();
const hp = $('input[name=hp]').val();
const zip = $('input[name=zip]').val();
const addr1 = $('input[name=addr1]').val();
const addr2 = $('input[name=addr2]').val();

	$.ajax({
		url: '/Jboard2/user/myInfo.do',
				method: 'post',
				data: {"uid":uid,
						"pass":pass,
						"name":name,
						"nick":nick,
						"email":email,
						"hp":hp,
						"zip":zip,
						"addr1":addr1,
						"addr2":addr2
						},
				dataType: 'json',
				success:(data)=>{
					if(data.result > 0){
						alert('수정 되었습니다.');
						location.href = "/Jboard2/user/list.do";
					}else{
						alert('오류');
						return false;
					}
				}
	});
