package kr.co.jboard2.service.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.jboard2.dao.ArticleDAO;
import kr.co.jboard2.vo.ArticleVO;
import kr.co.jboard2.vo.FileVO;


public enum ArticleService {
	
	INSTANCE;
	private ArticleDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ArticleService() {
		dao = new ArticleDAO();
	}
	
	public int insertArticle(ArticleVO article) {
		return dao.insertArticle(article);
	}
	public void insertFile(int parent, String newName, String fname) {
		dao.insertFile(parent, newName, fname);
	}
	public ArticleVO insertComment(int parent, String content, String uid, String regip) {
		return dao.insertComment(parent, content, uid, regip);
	}
	public int selectCountTotal() {
		return dao.selectCountTotal();
	}
	public ArticleVO selectArticle(String no) {
		return dao.selectArticle(no);
	}
	public List<ArticleVO> selectArticles(int limitStart) {
		return dao.selectArticles(limitStart);
	}
	public FileVO selectFile(String parent) {
		return dao.selectFile(parent);
	}
	public List<ArticleVO> selectComments(String parent) {
		return dao.selectComments(parent);
	}
	public void updateArticle(String no, String title, String content) {
		dao.updateArticle(no, title, content);
	}
	public void updateArticleHit(String no) {
		dao.updateArticleHit(no);
	}
	public void updateFileDownload(int fno) {
		dao.updateFileDownload(fno);
	}
	public int updateComment(String no, String content) {
		return dao.updateComment(no, content);
	}
	public void deleteArticle(String no) {
		dao.deleteArticle(no);
	}
	public String deleteFile(String no) {
		return dao.deleteFile(no);
	}
	public int deleteComment(String no, String parent) {
		return dao.deleteComment(no, parent);
	}
	
	// 추가적인 서비스 로직
	
	public MultipartRequest uploadFile(HttpServletRequest req, String path) throws IOException {
		int maxSize = 1024 * 1024 * 10;
		return new MultipartRequest(req, path, maxSize, "UTF-8", new DefaultFileRenamePolicy());
	}
	
	public String renameFile(String fname, String uid, String path) {
		// 파일명 수정
			int i = fname.lastIndexOf(".");
			String ext = fname.substring(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_");
			String now = sdf.format(new Date());
			String newName = now+uid+ext; // 20221026160417_chhak0503.txt
			
			File f1 = new File(path+"/"+fname);
			File f2 = new File(path+"/"+newName);
			
			f1.renameTo(f2);
			
			return newName;
	}
	
	public int getLastPageNum(int total) {
			
			int lastPageNum = 0;
			
			if(total % 10 == 0){
				lastPageNum = total / 10;
			}else{
				lastPageNum = total / 10 + 1;
			}
			
			return lastPageNum;
		}
	
	public int[] getPageGroupNum(int currentPage, int lastPageNum) {
		int pageGroupCurrent = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (pageGroupCurrent - 1) * 10 + 1;
		int pageGroupEnd = pageGroupCurrent * 10;
		
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		
		int[] result = {pageGroupStart, pageGroupEnd}; 
				
		return result;
	}
	
	public int getPageStartNum(int total, int currentPage) {
		int start = (currentPage - 1) * 10;
		return total - start;
	}
	
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null){
			currentPage = Integer.parseInt(pg);	
		}
		
		return currentPage;
	}
	
	public int getStartNum(int currentPage) {
		return (currentPage - 1) * 10;
	}
}