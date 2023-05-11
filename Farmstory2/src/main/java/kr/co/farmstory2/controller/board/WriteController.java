package kr.co.Farmstory2.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.co.Farmstory2.service.BoardService;
import kr.co.Farmstory2.vo.articleVO;

@WebServlet("/board/write.do")
public class WriteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.INSTANCE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/board/write.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 업로드 디렉터리의 물리적 경로 확인
		String saveDirectory = req.getServletContext().getRealPath("/file");
		
		// 첨부 파일 최대 용량 설정 - 1MB
		int maxPostSize = 1024*1000;
		
		// 파일 업로드
		MultipartRequest mr = service.uploadFile(req, saveDirectory, maxPostSize);
		
		// 폼값을 vo에 저장
		articleVO vo = service.saveVOFromForm(req, mr);
		
		// 원본 파일명과 저장된 파일 이름 설정
		String fileName = mr.getFilesystemName("file");
		
		// 데이터 베이스 처리
		service.insertArticle(vo, fileName, saveDirectory);
		
		// 목록으로 이동
		resp.sendRedirect("/Farmstory2/board/list.do?cate="+mr.getParameter("cate")+"&tit="+mr.getParameter("tit"));
	}
}
