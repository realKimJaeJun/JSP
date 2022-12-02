package kr.co.farmstory2.service.comment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.farmstory2.dao.CommentDAO;
import kr.co.farmstory2.vo.ArticleVO;


public enum CommentService {
	INSTANCE;
	private CommentDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private CommentService() {
		dao = new CommentDAO();
	}
	
	public Map<String, Object> insertComment(ArticleVO vo) {
		return dao.insertComment(vo);
	}
	
	public int deleteComment(String no) {
		return dao.deleteComment(no);
	}
	
	public int updateComment(String no, String comment) {
		return dao.updateComment(no, comment);
	}
	
	public void plusComment(String no) {
		dao.plusComment(no);
	}
	
	public void minusComment(String no) {
		dao.minusComment(no);
	}
	
	
	
	
}