package controller.user5;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.User5DAO;
import vo.User5VO;


@WebServlet("/user5/list.do")
public class ListController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<User5VO> users = User5DAO.getInstance().selectUser5s();
		
		req.setAttribute("users", users);
		
		// forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("/user5/list.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
}