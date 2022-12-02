package kr.co.farmstory2.service.board;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.farmstory2.dao.ArticleDAO;
import kr.co.farmstory2.vo.ArticleVO;
import kr.co.farmstory2.vo.FileVO;

public enum ArticleService {
	INSTANCE;
	private ArticleDAO dao;
	private ArticleService() {
		dao = new ArticleDAO();
	}
	Logger logger = LoggerFactory.getLogger(this.getClass());
	//====== list ======//
	/*** 검색 조건에 해당하는 게시판 전체 개수 구하는 서비스 ***/
	public int countArticles(Map<String, Object> map) {
		return dao.countArticles(map);
	}
	
	/*** 검색 조건에 맞는 게시물 목록을 반환하는 서비스 ***/
	public Map<String, Object> selectListPage(Map<String, Object> map){
		return dao.selectListPage(map);
	}
	
	//====== write ======//
	
	/*** 글을 등록하는 서비스 ***/
	public int insertArticleAndFile(ArticleVO aVo, FileVO fVo) {
		return dao.insertArticleAndFile(aVo, fVo);
	}
	
	//====== view ======//
	/*** 조건에 해당하는 게시물을 가져오는 서비스 ***/
	public Map<String, Object> selectArticle(int no) {
		return dao.selectArticle(no);
	}
	
	
	/*** 조건에 해당하는 게시물 조회수를 올리는 서비스 ***/
	public int plusHit(int no) {
		return dao.plusHit(no);
	}
	
	//====== view-download ======//
	/*** 조건에 해당하는 파일을 가져오는 서비스 ***/
	public FileVO selectFile(int no) {
		return dao.selectFile(no);
	}
	
	/*** 파일 다운로드 수 +1 ***/
	public void plusDownload(int no) {
		dao.plusDownload(no);
	}

	//====== delete ======//
	/*** 조건에 해당하는 게시판 및 관련 파일, 댓글을 삭제하는 서비스 ***/
	public Map<String, Object> deleteArticle(int no) {
		return dao.deleteArticle(no);
	}
	
	//====== update ======//
	// 조건에 해당하는 게시물 수정
	public int updateArticle(ArticleVO aVo, FileVO fVo, boolean newSave) {
		return dao.updateArticleAndFile(aVo, fVo, newSave);
	}
	
	//====== index ======//
	// 조건에 해당하는 게시물 조회
	public Map<String, Object> selectArticles() {
		return dao.selectArticles();
	}
	
	//===== 추가 서비스 =====//
	/*** 페이징 처리 ***/
	public Map<String, Object> paging(Map<String, Object> map){
		logger.info("paging...");
		
		HttpServletRequest req = (HttpServletRequest)map.get("request");
		String searchField = (String)map.get("searchField"); // 검색 필드
		String searchWord = (String)map.get("searchWord");   // 검색 단어
		
		
		map.put("searchField", searchField);
		map.put("searchWord", searchWord);
		
		int totalCount  = dao.countArticles(map); // 전체 게시물 개수 
		int pageSize    = 10; // 페이지당 출력할 페이지 개수
		int lastPageNum = (int)Math.ceil(totalCount / 10.0); // 마지막 페이지 번호 계산
		int currentPage = 1;  // 기본값
		int limitStart  = 0;  // 현재 페이지에서 시작하는 게시물 시작값
		
		int pageGroupCurrent = 1; // 그룹 번호
		int pageGroupStart   = 1; // 그룹에서 첫 페이지
		int pageGroupEnd     = 0; // 그룹에서 마지막 페이지
		int pageStartNum     = 0; // 게시물의 번호 정렬
		
		if(req.getParameter("pageNum") != null && !req.getParameter("pageNum").equals("")) {
			currentPage = Integer.parseInt(req.getParameter("pageNum"));
		}
		
		// 현재 페이지 기준 DB상의 첫번째 게시물의 번호 - limit 시작값 계산
		limitStart = (currentPage - 1) * pageSize;
		map.put("limitStart", limitStart);
		
		// 페이지 그룹 계산
		pageGroupCurrent = (int)Math.ceil(currentPage / 10.0);
		pageGroupStart = (pageGroupCurrent - 1) * pageSize + 1;
		pageGroupEnd = pageGroupCurrent * pageSize;
		
		// 마지막 페이지보다 그룹 마지막 페이지가 클 경우
		if(pageGroupEnd > lastPageNum) pageGroupEnd = lastPageNum;
		
		// 게시판에 표시할 시작 번호 계산
		pageStartNum = totalCount - limitStart;
		
		map = dao.selectListPage(map);
		
		map.put("pageGroupStart", pageGroupStart);
		map.put("pageGroupEnd", pageGroupEnd);
		map.put("pageGroupCurrent", pageGroupCurrent);
		map.put("currentPage", currentPage);
		map.put("lastPageNum", lastPageNum);
		map.put("pageStartNum", pageStartNum);
		
		logger.debug("map : " + map);
		return map;
	}
	
	/*** 페이지 태그 문자열을 반환하는 서비스  ***/
	public String getPageTags(Map<String, Object> map) {
		logger.info("getPageTags...");
		
		String searchField   = (String)map.get("searchField");
		String searchWord   = (String)map.get("searchWord");
		int pageGroupStart   = (int)map.get("pageGroupStart");
		int pageGroupEnd     = (int)map.get("pageGroupEnd");
		int pageGroupCurrent = (int)map.get("pageGroupCurrent");
		int currentPage      = (int)map.get("currentPage");
		int lastPageNum      = (int)map.get("lastPageNum");

		StringBuffer pageTags = new StringBuffer(); // 페이지 태그 모음
		int prevPage = pageGroupStart - 1;    // 이전 페이지
		int nextPage = pageGroupEnd + 1;      // 다음 페이지
		String contextPath = ((HttpServletRequest)map.get("request")).getContextPath();
		
		String group = (String)map.get("group");
		String cate = (String)map.get("cate");
		
		// 이전 페이지 tag 
		if(pageGroupCurrent > 1) {
			
			String uri = "<a href=\"" + contextPath + "/board/list.do?pageNum=" + prevPage
					   + "&group=" + group + "&cate=" + cate;
			
			if(searchWord != null) {
				uri += "&searchField=" + searchField + "&searchWord=" + searchWord; 
			}
			
			uri += "\" class=\"prev\">이전</a>";

			pageTags.append(uri);
		}
		
		for(int i=pageGroupStart; i<=pageGroupEnd; i++) {
			if(currentPage == i) { // 현재 페이지와 값이 같다면 링크X
				pageTags.append("<a href=\"#\" class=\"num current\">" + String.valueOf(i) + "</a>");
			} else {
				String uri = "<a href=\"" + contextPath + "/board/list.do?pageNum=" + i
						   + "&group=" + group + "&cate=" + cate;
				
				if(searchWord != null) {
					uri += "&searchField=" + searchField + "&searchWord=" + searchWord; 
				}
						
				uri += "\" class=\"next\">" + String.valueOf(i) + "</a>";
				
				pageTags.append(uri);
			}
		}
		
		if(pageGroupEnd < lastPageNum) { // 반복문의 마지막이며 마지막 페이지 번호보다 작을 경우
			String uri = "<a href=\"" + contextPath + "/board/list.do?pageNum=" + nextPage
					   + "&group=" + group + "&cate=" + cate;
			
			if(searchWord != null) {
				uri += "&searchField=" + searchField + "&searchWord=" + searchWord;
			}
			
			uri += "\">다음</a>";
			
			pageTags.append(uri);
		}
		
		logger.debug("pageTags : " + pageTags.toString());
		return pageTags.toString();
	}
	
	
	/*** 파일 업로드 서비스 ***/
	public MultipartRequest uploadFile(HttpServletRequest req, String savePath, int maxPostSize) {
		
		MultipartRequest mr = null;
		
		try {
			logger.info("MultipartRequest...");
			// 파일 업로드
			mr = new MultipartRequest(req, savePath, maxPostSize, "utf-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.debug("mr : " + mr);
		return mr;
	}
	
	/*** 명시한 파일을 찾아 다운로드 서비스 ***/
	public void downloadFile(HttpServletRequest req, HttpServletResponse resp, String directory, String newName, String oriName) {
		logger.info("fileDownload...");
		
		String sDirectory = req.getServletContext().getRealPath(directory);
		
		try {
			// 파일 다운로드용 응답 헤더 설정
			resp.reset();
			resp.setContentType("application/octet-stream");
			resp.setHeader("content-Disposition", "attacment; filename=\"" + URLEncoder.encode(oriName, "utf-8") + "\"");
			
			// 파일을 찾아 입력 스트림 생성
			File file = new File(sDirectory, newName);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			
			// response 내장 객체로부터 새로운 출력 스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
			
			while(true) {
				int data = bis.read();
				
				if(data == -1) break;
				
				bos.write(data);
			}
			
			bos.flush();
			bos.close();
			bis.close();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	/*** 지정한 위치의 파일을 삭제 서비스 ***/
	public void deleteFile(HttpServletRequest req, String directory, String newName) {
		logger.info("deleteFile...");
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory, newName);
		if(file.exists()) file.delete();
	}
	
	
	
}