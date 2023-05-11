package kr.co.Farmstory2.service;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.Farmstory2.dao.UserDAO;
import kr.co.Farmstory2.vo.termsVO;
import kr.co.Farmstory2.vo.userVO;

public enum UserService {
	INSTANCE;
	private UserDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String password = "kxuxvucyzszwfipr";
	
	private UserService() {
		dao = new UserDAO();
	}
	
	// create
	public void insertUser(userVO vo){
		dao.insertUser(vo);
	}
	
	// read
	/**
	 * 약관 불러오기
	 * @return
	 */
	public termsVO selectTerms() {
		return dao.selectTerms();
	}
	/**
	 * 로그인 처리
	 * @param uid
	 * @param pass
	 * @return
	 */
	public userVO loginUser(String uid, String pass) {
		return dao.selectUser(uid, pass);
	}
	public userVO selectUserBySessId(String sessId){
		return dao.selectUserBySessId(sessId);
	}
	/**
	 * 아이디 체크
	 * @param uid
	 */
	public int checkUid(String uid) {
		logger.info("checkUid...");
		return dao.selectCountUid(uid);
	}
	/**
	 * 닉네임 중복 체크
	 * @param nick
	 * @return
	 */
	public int checkNick(String nick) {
		return dao.selectCountNick(nick);
	}
	/**
	 * 이름 이메일 검사
	 * @param name
	 * @param email
	 * @return
	 */
	public int selectCountUserName(String name, String email) {
		return dao.selectCountUserName(name, email);
	}
	public int selectCountUserUid(String uid, String email) {
		return dao.selectCountUserUid(uid, email);
	}
	
	public userVO selectUserEmail(String email) {
		return dao.selectUserEmail(email);
	}
	
	// upload
	/**
	 * 자동 로그인 - 정보 입력
	 * @param sessId
	 * @param uid
	 */
	public void updateUserForSession(String sessId, String uid) {
		dao.updateUserForSession(sessId, uid);
	}
	
	/**
	 * 자동 로그인 - 날짜 갱신
	 * @param sessId
	 */
	public void updateUserForSessLimitDate(String sessId){
		dao.updateUserForSessLimitDate(sessId);
	}
	
	/**
	 * 로그아웃 - 자동로그인 정보 제거
	 * @param uid
	 */
	public void updateUserForSessionOut(String uid) {
		dao.updateUserForSessionOut(uid);
	}
	
	/**
	 * 비밀번호 변경
	 * @param uid
	 * @param pass
	 * @return
	 */
	public int updateUserPass(String uid, String pass) {
		return dao.updateUserPass(uid, pass);
	}
	
	// delete
	
	// service
	/**
	 * 유저 정보 입력
	 * @param req
	 * @return
	 */
	public userVO inputUserVO(HttpServletRequest req){
		logger.info("inputUserVO... User Info Insert");
		userVO uvo = new userVO();
		uvo.setUid(req.getParameter("uid"));
		uvo.setPass(req.getParameter("pass2"));
		uvo.setName(req.getParameter("name"));
		uvo.setNick(req.getParameter("nick"));
		uvo.setEmail(req.getParameter("email"));
		uvo.setHp(req.getParameter("hp"));
		uvo.setZip(req.getParameter("zip"));
		uvo.setAddr1(req.getParameter("addr1"));
		uvo.setAddr2(req.getParameter("addr2"));
		uvo.setRegip(req.getRemoteAddr());
		return uvo;
	}
	
	/**
	 * 메일 보내기
	 * @param receiver
	 * @return
	 */
	public int[] sendEmailCode(String receiver) {
		// 인증코드 생성
		int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
		
		// 기본 정보
		String sender = "me03454@gmail.com";
		
		String title = "Farmstory2 인증코드 입니다.";
		String content = "<h1>인증코드는 "+code+" 입니다.</h1>";
		
		// Gmail SMTP 서버 설정
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
			logger.debug("메일 시작...");
			message.setFrom(new InternetAddress(sender, "관리자", "UTF-8"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(title);
			message.setContent(content, "text/html;charset=utf-8");
			Transport.send(message);
			status = 1;
		} catch (Exception e) {
			logger.error("메일전송 실패..."+e.getMessage());
		}
		logger.debug("메일 전송 성공...");
		
		int result [] = {status, code};
		return result;
	}
	
	/**
	 * 자동 로그인 - 로그인 쿠키 생성
	 * @param req
	 * @param resp
	 * @param vo
	 */
	public void CookieCreate(HttpServletRequest req, HttpServletResponse resp, userVO vo) {
		HttpSession sess = req.getSession();
		sess.setAttribute("sessUser", vo);
		if(req.getParameter("auto") != null) {
			String sessId = sess.getId();
			// 쿠키 생성
			Cookie cookie = new Cookie("SESSID", sessId);
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24*3);
			resp.addCookie(cookie);
			// sessId 데이터베이스 저장
			updateUserForSession(sessId, vo.getUid());
		}
	}

	/**
	 * 자동 로그인 - 쿠키 확인 및 자동 로그인
	 * @param cookies
	 * @param sess
	 * @param arg1
	 */
	public void autoLoginCheck(Cookie[] cookies, HttpSession sess, ServletResponse arg1) {
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("SESSID")) {
					String sessId = cookie.getValue();
					userVO vo = selectUserBySessId(sessId);
					if(vo.getUid() != null) {
						sess.setAttribute("sessUser", vo);
						cookie.setPath("/");
						cookie.setMaxAge(60*60*24*3);
						((HttpServletResponse)arg1).addCookie(cookie);
						updateUserForSessLimitDate(sessId);
					}
				}
			}
		}
	}

	/**
	 * 로그아웃 기능
	 * @param req
	 * @param resp
	 */
	public void logout(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession sess = req.getSession();
		userVO vo = (userVO)sess.getAttribute("sessUser");
		
		sess.removeAttribute("sessUser");
		sess.invalidate();
		
		Cookie cookie = new Cookie("SESSID", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		
		updateUserForSessionOut(vo.getUid());
	}

	
}
