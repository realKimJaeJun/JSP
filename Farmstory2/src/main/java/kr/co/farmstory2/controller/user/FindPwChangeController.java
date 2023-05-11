package kr.co.Farmstory2.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import kr.co.Farmstory2.service.UserService;

@WebServlet("/user/findPwChange.do")
public class FindPwChangeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService service = UserService.INSTANCE;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("vo", service.selectUserEmail(req.getParameter("email")));
		req.getRequestDispatcher("/user/findPwChange.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonObject json = new JsonObject();
		json.addProperty("result", service.updateUserPass(req.getParameter("uid"), req.getParameter("pass")));
		resp.getWriter().print(json.toString());
	}
}
