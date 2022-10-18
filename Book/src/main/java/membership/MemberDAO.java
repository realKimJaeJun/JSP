package membership;

public class MemberDAO extends JDBConnect{
	// 명시한 데이터베이스로의 연결이 완료된 MemberDAO 객체를 생성합니다.
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
	// 명시한 아이디/패스워드와 일치하는 회원 정보를 반환합니다.
	public MemberDTO getMemberDTO(String uid, String upass) {
		MemberDTO dto = new MemberDTO(); // 회원 정보 DTO 객체 생성
		String query = "SELECT * FROM member WHERE id=? AND pass=?";
										// 쿼리문 템플릿
		
		try {
			// 쿼리 실행
			psmt = con.prepareState
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
