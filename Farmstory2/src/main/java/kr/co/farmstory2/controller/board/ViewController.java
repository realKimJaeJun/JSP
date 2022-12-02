package kr.co.farmstory2.controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import kr.co.farmstory2.service.board.ArticleService;


@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init() throws ServletException {}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("ViewController...");
		
		int no = Integer.parseInt(req.getParameter("no"));   // 게시글 번호
		Map<String, Object> map = service.selectArticle(no); // 게시글 정보를 가져오는 서비스
		
		// no=xxx 쿼리스트링을 제거하는 작업
		String queryString = req.getQueryString();
		String[] arr = queryString.split("&");
		StringJoiner joiner = new StringJoiner("&");
		for(int i=1; i<arr.length; i++) {
			joiner.add(arr[i]);
		}
		
		req.setAttribute("no", no);											// 게시글 번호
		req.setAttribute("joiner", joiner.toString());						// no=xxx를 제거한 쿼리스트링
		req.setAttribute("queryString", req.getQueryString());  			// no를 포함한 전체 쿼리스트링
		req.setAttribute("group", req.getParameter("group"));   			// 그룹
		req.setAttribute("cate", req.getParameter("cate"));    				// 카테고리
		req.setAttribute("searchField", req.getParameter("searchField"));	// 검색 필드
		req.setAttribute("searchWord", req.getParameter("searchWord"));		// 검색 단어
		req.setAttribute("pageNum", req.getParameter("pageNum"));			// 페이지 번호
		req.setAttribute("map", map);										// 게시글 정보를 담고 있는 map 
		req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int no = Integer.parseInt(req.getParameter("no"));
		int result = service.plusHit(no); // 게시글 조회수 +1					
		
		resp.setContentType("json/application;charset=UTF-8");
		JsonObject json = new JsonObject();
		json.addProperty("result", result);
		PrintWriter writer = resp.getWriter();
		writer.print(json.toString());
	}
}