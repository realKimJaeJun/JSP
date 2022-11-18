package controller.customer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDAO;
import vo.CustomerVO;


@WebServlet("/customer/modify.do")
public class ModifyController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int custId = Integer.parseInt(req.getParameter("custId"));
		
		CustomerVO vo = CustomerDAO.getInstance().selectCust(custId);
		
		req.setAttribute("vo", vo);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/Bookstore2/customer/modify.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int custId = Integer.parseInt(req.getParameter("custId"));
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		
		CustomerVO vo = new CustomerVO();
		vo.setCustId(custId);
		vo.setName(name);
		vo.setAddress(address);
		vo.setPhone(phone);
		
		
		CustomerDAO.getInstance().updateCust(vo);
		
		resp.sendRedirect("/customer/list.do");
	}
	
}