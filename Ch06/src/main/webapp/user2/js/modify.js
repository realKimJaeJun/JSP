/**
 * 
 */
 
 /**
 * 유저 수정
 */
 
function modify(u){
	
	// u에는 maneger.jsp로 전달받은 user 멤버의 정보가 담겨있는 td태그가 있다.
	$(function(){
		
		// 데이터 수신
		let uid = u.eq(0).text();
		let name = u.eq(1).text();
		let hp = u.eq(2).text();
		let age = u.eq(3).text();
		
		
		// 모든 태그 값을 삭제 후 수정과 관련된 태그로 바꾸는 작업
		$('nav').empty().html("<h4>user2수정</h4><h4><a href='#' id='userList'>user2목록</a></h4>");
		$('section').empty();
		
		let table = "<table border='1'>";
			table += "<tr>";						
			table += "<td>아이디</td>";
			table += "<td>";
			table += "<input type='text' name='uid' value='" + uid + "'/>";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";						
			table += "<td>이름</td>";
			table += "<td>";
			table += "<input type='text' name='name' value='" + name + "'/>";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";						
			table += "<td>휴대폰</td>";
			table += "<td>";
			table += "<input type='text' name='hp' value='" + hp + "'/>";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";						
			table += "<td>나이</td>";
			table += "<td>";
			table += "<input type='text' name='age' value='" + age + "'/>";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";						
			table += "<td colspan='2' id='btnSubmit'>";
			table += "<input type='submit' id='btnModify' value='수정'/>";
			table += "</td>";
			table += "</tr>";
			table += "</table>";
		
		$('section').append(table);
	});
}