package kr.co.farmstory2.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.farmstory2.service.ArticleService;
import kr.co.farmstory2.vo.ArticleVO;



@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;

	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String group = req.getParameter("group");
		String cate = req.getParameter("cate");
		
		req.setAttribute("group", group);
		req.setAttribute("cate", cate);
		
		try {
			String no = req.getParameter("no");
			String pg = req.getParameter("pg");
			String content = req.getParameter("content");
			String uid     = req.getParameter("uid");
			String regip   = req.getRemoteAddr();
			
			// 조회수 +1
			service.updateArticleHit(no);
			
			// 글 가져오기
			ArticleVO article = service.selectArticle(no);
			
			// 댓글 가져오기
			List<ArticleVO> comments = service.selectComments(no);
			
			req.setAttribute("article", article);
			req.setAttribute("comments", comments);
			
			ArticleVO comment = new ArticleVO();
			comment.setParent(no);
			comment.setContent(content);
			comment.setUid(uid);
			comment.setRegip(regip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/board/view.jsp");
		dispatcher.forward(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}