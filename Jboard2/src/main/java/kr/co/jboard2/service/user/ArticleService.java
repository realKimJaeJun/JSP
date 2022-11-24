package kr.co.jboard2.service.user;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}