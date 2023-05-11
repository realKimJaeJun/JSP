package kr.co.Farmstory2.controller.board;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import kr.co.Farmstory2.service.BoardService;
import kr.co.Farmstory2.vo.FileVO;
import kr.co.Farmstory2.vo.articleVO;
import kr.co.Farmstory2.vo.userVO;

@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.INSTANCE;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no = req.getParameter("no");
		
		Map<String, Object> vos = service.selectArticle(no); // 게시물 불러오기
		articleVO avo = (articleVO)vos.get("avo");
		
		// 로그인한 아이디와 게시글의 글쓴이의 아이디가 같지 않을때 게시물 조회수 증가
		if (!((userVO)req.getSession().getAttribute("sessUser")).getUid().equals(avo.getUid())) {
			service.updateHitCount(no);
		}
		
		req.setAttribute("avo", avo); // 게시물 정보
		req.setAttribute("avo2", service.selectArticleComment(no)); // 댓글 불러오기
		req.setAttribute("fvo", (FileVO)vos.get("fvo")); // 파일 정보
		
		req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		// 작성한 댓글 정보 저장
		articleVO vo = service.saveArticleVO(req);
		
		// type에 따라 작업 처리
		JsonObject json = service.commentWorkForType(req.getParameter("type"), vo, req.getParameter("parent"));
		
		// 출력
		resp.getWriter().print(json.toString());
	}
}
