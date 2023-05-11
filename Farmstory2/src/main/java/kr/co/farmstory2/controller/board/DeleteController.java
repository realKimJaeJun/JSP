package kr.co.Farmstory2.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.Farmstory2.service.BoardService;

@WebServlet("/board/delete.do")
public class DeleteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.INSTANCE;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service.deleteArticle(req.getParameter("no"), req.getParameter("file"), req.getServletContext().getRealPath("/file"));
		resp.sendRedirect("/board/deleteCheck.do?cate="+req.getParameter("cate")+"&tit="+req.getParameter("tit")+"&pg="+req.getParameter("pg")+"&search="+req.getParameter("search")+"&success=300");
	}
}
