package kr.co.jboard2.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.jboard2.service.user.UserService;
import kr.co.jboard2.vo.UserVO;

@WebServlet("/user/info.do")
public class InfoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService service = UserService.INSTANCE;

	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/user/info.jsp");
		dispatcher.forward(req, resp);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uid = req.getParameter("uid");
		String pass = req.getParameter("pass");
		
		UserVO vo = service.selectPass(pass);
		
		if(vo != null) {
			// 회원이 맞을 경우
			HttpSession sess = req.getSession();
			sess.setAttribute("sessUser", vo);
			
			resp.sendRedirect("/Jboard2/user/myInfo.do");
			
		}else {
			// 회원이 아닐 경우
			resp.sendRedirect("/Jboard2/user/info.do?success=100");
		}
		
		
	}
}