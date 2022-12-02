package kr.co.farmstory2.service.user;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.farmstory2.dao.UserDAO;
import kr.co.farmstory2.vo.TermsVO;
import kr.co.farmstory2.vo.UserVO;

public enum UserService {
	
	INSTANCE;
	private UserDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private UserService() {
		dao = new UserDAO();
	}
	
	/*** register ***/
	// 약관 동의
	public TermsVO selectTerms() {
		return dao.selectTerms();
	}
	// 아이디 체크
	public int checkUser(String uid) {
		return dao.checkUser(uid);
	}
	// 별명 체크
	public int checkNick(String nick) {
		return dao.checkNick(nick);
	}
	// 이메일 체크
	public int checkEmail(String email) {
		return dao.checkEmail(email);
	}
	
	// 회원 등록
	public void insertUser(UserVO vo) {
		dao.insertUser(vo);
	}
	
	/*** login ***/
	
	// 아이디 / 패스워드 확인
	public UserVO selectUser(String uid, String pass) {
		return dao.selectUser(uid, pass);
	}
	
	// 자동로그인시 로그인 회원 정보 가져오는 메서드
	public UserVO selectUserBySessId(String sessId) {
		return dao.selectUserBySessId(sessId);
	}
	
	// 자동로그인시 로그인시 현재 세션 ID와 쿠키 세션ID가 동일하지 않을때 만료일을 연장시키는 메서드
	public int updateUserForSessLimitDate(String sessId) {
		return dao.updateUserForSessLimitDate(sessId);
	}
	
	
	
	// 자동 로그인 체크 후 로그인시 sessId DB에 저장
	public int updateUserForSession(String uid, String sessId) {
		return dao.updateUserForSession(uid, sessId);
	}
	
	/*** 아이디 찾기 ***/
	public UserVO selectUserForFindId(String name, String email) {
		return dao.selectUserForFindId(name, email);
	}
	
	/*** 비밀번호 찾기 ***/
	public int selectUserForFindPw(String uid, String email) {
		return dao.selectUserForFindPw(uid, email);
	}
	
	/*** 비밀번호 변경 ***/
	public int updateUserPw(String uid, String pass) {
		return dao.updateUserPw(uid, pass);
	}
	
	/*** logout ***/
	public void updateUserForSessionOut(String uid) {
		dao.updateUserForSessionOut(uid);
	}
	
	
	
	
	public void selectUsers() {}
	public void updateUser() {}
	public void deleteUser() {}
	
	public int[] sendEmailCode(String receiver) {
		
		// 인증코드 생성(6자리수 랜덤 생성)
		int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
		
		
		// 기본정보
		String sender = "ooo3345sjh@gmail.com";
		String password = "lquirlsdppjprekj";
		
		String title = "Jboard2 인증코드 입니다.";
		
		String content = "<h1>인증코드는 " + code + "입니다.</h1>";
		
		// Gmail SMTP 서버설정
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});
		
		
		// 메일발송
		Message message = new MimeMessage(session);
		int status = 0;
		try {
			logger.info("메일 전송 시작...");
			message.setFrom(new InternetAddress(sender, "관리자", "utf-8"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(MimeUtility.encodeText(title, "utf-8", "B"));
			message.setContent(content, "text/html;charset=utf-8");
			Transport.send(message);
			
			status = 1;
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
			logger.error("메일 전송 실패...");
		}
		logger.debug("메일 전송 성공...");
		
		int result[] = {status, code};
		
		return result;
	}
}