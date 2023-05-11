package kr.co.Farmstory2.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.Farmstory2.service.UserService;
import kr.co.Farmstory2.vo.userVO;

@WebServlet("/user/login.do")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService service = UserService.INSTANCE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/user/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userVO vo = service.loginUser(req.getParameter("uid"), req.getParameter("pass"));
		if(vo.getUid() != null) {
			// 회원이 맞을경우
			service.CookieCreate(req, resp, vo);
			resp.sendRedirect("/Farmstory2/index.do");
		} else {
			// 회원이 아닐경우
			resp.sendRedirect("/Farmstory2/user/login.do?success=100");
		}
	}
}