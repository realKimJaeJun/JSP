package kr.co.Farmstory2.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.Farmstory2.service.FileService;
import kr.co.Farmstory2.vo.FileVO;

@WebServlet("/board/download.do")
public class DownloadController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FileService service = FileService.INSTANCE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 정보 불러오기
		FileVO vo = service.selectFile(req.getParameter("no"));
		
		// 파일 다운로드 횟수 증가
		service.updateFileDownloadCount(vo.getFno());
		
		// 파일 다운로드 세팅
		service.fileDownloadSetting(vo.getOriName(), resp);
		
		// 파일 전송
		service.fileDownloadStart(req.getServletContext().getRealPath("/file"), resp.getOutputStream(), vo.getNewName());
	}
}
