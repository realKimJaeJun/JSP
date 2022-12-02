package kr.co.farmstory2.controller.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;

import kr.co.farmstory2.service.board.ArticleService;
import kr.co.farmstory2.utils.JSFunction;
import kr.co.farmstory2.vo.ArticleVO;
import kr.co.farmstory2.vo.FileVO;
import kr.co.farmstory2.vo.UserVO;

@WebServlet("/board/modify.do")
public class ModifyController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("ModifyController doGet...");
		
		req.setAttribute("no", req.getParameter("no"));				// 게시글 번호
		req.setAttribute("title", req.getParameter("title"));		// 게시글 제목
		req.setAttribute("content", req.getParameter("content"));   // 게시글 내
		req.setAttribute("fname", req.getParameter("fname"));       // 파일 이름
		req.setAttribute("group", req.getParameter("group"));  	    // 그룹
		req.setAttribute("cate", req.getParameter("cate"));         // 카테고리
		
		
		req.getRequestDispatcher("/board/modify.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("ModifyController doPost...");
		
		// 1. 파일 업로드 처리
		// 업로드 디렉터리의 물리적 경로 확인
		String savePath = getServletContext().getRealPath("/file");
		File saveDirectory = new File(savePath);
		
		if(!saveDirectory.exists()) { // savePath 디렉터리가 존재하지않으면 생성
			saveDirectory.mkdirs();
		}
		
		int maxPostSize = 1024 * 1024 * 10; // 10MB
		
		// 파일 업로드 
		MultipartRequest mr = service.uploadFile(req, savePath, maxPostSize);
		if(mr == null) {
			// 파일 업로드 실패
			JSFunction.alertBack(resp, "첨부 파일이 제한 용량을 초과합니다.");
			return;
		}
		
		int no = Integer.parseInt(mr.getParameter("no"));				// 게시글 번호
		String title = mr.getParameter("title");						// 게시글 제목
		String content = mr.getParameter("content");					// 게시글 내용
		String oriName = mr.getFilesystemName("oriName");				// 파일명
		String regip = req.getRemoteAddr();								// 회원 IP주소
		String uid = ((UserVO)req.getAttribute("reqUser")).getUid();    // 회원 ID
		
		// 2. 파일 업로드 외 처리
		// 폼값을 VO에 저장
		ArticleVO aVo = new ArticleVO();
		aVo.setNo(no);
		aVo.setUid(uid);
		aVo.setTitle(title);
		aVo.setContent(content);
		aVo.setFile(oriName == null? 0 : 1);
		aVo.setRegip(regip);
		
		
		/*** 원본 파일명과 저장된 파일 이름 설정 ***/
		String newName = "";
		FileVO fVo = null;
		String changeFile = mr.getParameter("changeFile");   // 수정 파일 true or false
		String currentFile = mr.getParameter("currentFile"); // 수정 전 저장되어 있는 파일명 (없으면 null)
		
		boolean newSave = false;
		if(oriName != null && "true".equals(changeFile)) { // 첨부 파일이 있고 changeFile == true일 경우
			
			// 기존에 저장되어 있던 파일이 있다면 삭제
			if(currentFile != null && !"".equals(currentFile)) {
				FileVO vo = service.selectFile(no);
				service.deleteFile(req, "/file", vo.getNewName());
			} else {
				newSave = true;
			}
			
			// 파일명 수정
			int i = oriName.lastIndexOf(".");
			String ext = oriName.substring(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_");
			String now = sdf.format(new Date());
			newName = now + uid + ext; // 새로운 파일 이름 설정
			
			File oriFile = new File(saveDirectory, oriName); // saveDirectory에 실제로 존재하는 객체
			File newFile = new File(saveDirectory, newName); // 가상의 파일 객체
			oriFile.renameTo(newFile);
			
			fVo = new FileVO();
			fVo.setNewName(newName);
			fVo.setOriName(oriName);
		}
		
		
		
		int result = 0; 								   // 결과 변수
		result = service.updateArticle(aVo, fVo, newSave); // 작성 글 등록
		
		String group = req.getParameter("group"); // 그룹
		String cate = req.getParameter("cate");   // 카테고리
		
		if(result > 0) { // 글 및 파일 등록 성공시
			resp.sendRedirect(req.getContextPath() + "/board/view.do?no=" + no + "&group=" + group + "&cate=" + cate);
			return;
		} else {
			JSFunction.alertBack(resp, "글 수정에 실패 했습니다.");
			return;
		}
	}

}