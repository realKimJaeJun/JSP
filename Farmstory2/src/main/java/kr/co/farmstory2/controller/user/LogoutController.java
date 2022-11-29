package kr.co.farmstory2.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.farmstory2.service.UserService;
import kr.co.farmstory2.vo.UserVO;

@WebServlet("/user/logout.do")
public class LogoutController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private UserService service = UserService.INSTANCE;
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sess = req.getSession();
		UserVO sessUser = (UserVO) sess.getAttribute("sessUser");
		String uid = sessUser.getUid();
		
		// 세션 해제 (로그아웃 시 자동로그인 세션 해제)
		sess.removeAttribute("sessUser");
		sess.invalidate();
		
		// 쿠키 삭제 (로그아웃 시 자동로그인 쿠키 삭제)
		Cookie cookie = new Cookie("SESSID", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		
		// 데이터베이스 사용자 sessId update
		service.updateUserForSessionOut(uid);
		resp.sendRedirect("/Farmstory2/index.do?success=200");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
