package kr.co.jboard2.service.user;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.dao.ArticleDAO;
import kr.co.jboard2.vo.ArticleVO;


public enum ArticleService {
	
	INSTANCE;
	private ArticleDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ArticleService() {
		dao = new ArticleDAO();
	}
	
	public int insertArticle(ArticleVO vo) {
		return dao.insertArticle(vo);
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
	public void selectArticle() {
		
	}
	public void selectArticles() {
		
	}
	public void selectFile() {
		
	}
	public void selectComment() {
		
	}
	public void updateArticle() {
		
	}
	public void updateArticleHit() {
		
	}
	public void updateFileDownload() {
		
	}
	public void updateComment() {
		
	}
	public void deleteArticle() {
		
	}
	public void deleteFile() {
		
	}
	public void deleteComment() {
		
	}
	
	
	
	
	
}