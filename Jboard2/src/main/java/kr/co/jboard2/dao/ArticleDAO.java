package kr.co.jboard2.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.db.DBHelper;
import kr.co.jboard2.db.Sql;
import kr.co.jboard2.vo.ArticleVO;
import kr.co.jboard2.vo.FileVO;
import kr.co.jboard2.vo.UserVO;

// DAO(Data Access Object) : 데이터베이스 처리 클래스
public class ArticleDAO extends DBHelper{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 기본 CRUD
	public int insertArticle(ArticleVO article) {
		
		int parent = 0;
		
		try{
			logger.info("insertArticle...");
			
			conn = getConnection();
			// 트랜젝션 시작
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(Sql.INSERT_ARTICLE);
			stmt = conn.createStatement();
			
			psmt.setString(1, article.getTitle());
			psmt.setString(2, article.getContent());
			psmt.setInt(3, article.getFname() == null ? 0 : 1);
			psmt.setString(4, article.getUid());
			psmt.setString(5, article.getRegip());
			psmt.executeUpdate();
			rs = stmt.executeQuery(Sql.SELECT_MAX_NO);
			
			
			// 작업확정
			conn.commit();
			
			if(rs.next()){
				parent = rs.getInt(1);
			}
			
			close();			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return parent;
	}
	
	public void insertFile(int parent, String newName, String fname) {
		try{
			logger.info("insertFile...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.INSERT_USER);
			psmt.setInt(1, parent);
			psmt.setString(2, newName);
			psmt.setString(3, fname);
			
			psmt.executeUpdate();

			close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	public ArticleVO insertComment(int parent, String content, String uid, String regip) {
		
		ArticleVO vo = null;
		int result = 0;
		
		try{
			logger.info("insertComment...");
			conn = getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement psmt1 = conn.prepareStatement(Sql.INSERT_USER);
			PreparedStatement psmt2 = conn.prepareStatement(Sql.UPDATE_ARTICLE_COMMENT_PLUS);
			stmt = conn.createStatement();
			psmt1.setInt(1, parent);
			psmt1.setString(2, content);
			psmt1.setString(3, uid);
			psmt1.setString(4, regip);
			
			psmt2.setInt(1, parent);
			
			result = psmt1.executeUpdate();
			psmt2.executeUpdate();
			rs = stmt.executeQuery(Sql.SELECT_COMMENT_LATEST);
			
			conn.commit();
			
			if(rs.next()) {
				vo = new ArticleVO();
				vo.setParent(rs.getInt(1));
				vo.setParent(rs.getInt(2));
				vo.setContent(rs.getString(6));
				vo.setUid(rs.getString(11).substring(2, 10));
				vo.setRegip(rs.getString(12));
			}
			
			close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return vo;
	}
	
	public int selectCountTotal() {
		
		int total = 0;
		
		try {
			logger.info("selectCountTotal");
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(Sql.SELECT_COUNT_TOTAL);
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return total;
	}
	
	public ArticleVO selectArticle(String no) {
		
		ArticleVO vo = null;
		
		try{
			logger.info("selectArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE);
			psmt.setString(1, no);
			
			rs = psmt.executeQuery();
			
			if(rs.next()){
				vo = new ArticleVO();
				vo.setNo(rs.getInt(1));
				vo.setParent(rs.getInt(2));
				vo.setComment(rs.getInt(3));
				vo.setCate(rs.getString(4));
				vo.setTitle(rs.getString(5));
				vo.setContent(rs.getString(6));
				vo.setFile(rs.getInt(7));
				vo.setHit(rs.getInt(8));
				vo.setUid(rs.getString(9));
				vo.setRegip(rs.getString(10));
				vo.setRdate(rs.getString(11));
				vo.setFno(rs.getInt(12));
				vo.setPno(rs.getInt(13));
				vo.setNewName(rs.getString(14));
				vo.setOriName(rs.getString(15));
				vo.setDownload(rs.getInt(16));
			}
			
			close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return vo;
	}
	
	public List<ArticleVO> selectArticles(int limitStart) {
		
		List<ArticleVO> articles = new ArrayList<>();
		
		try{
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLES);
			psmt.setInt(1, limitStart);
			
			rs = psmt.executeQuery();
			
			while(rs.next()){
				ArticleVO article = new ArticleVO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setUid(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11));
				article.setNick(rs.getString(12));
				
				articles.add(article);			
			}
			
			close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return articles;
	}
	
	public FileVO selectFile(String parent) {
		FileVO fv = null;
		
		try{
			logger.info("selectFile...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_FILE);
			psmt.setString(1, parent);
		 	rs = psmt.executeQuery();
		 	
		 	if(rs.next()){
		 		fv = new FileVO();
		 		fv.setFno(rs.getInt(1));
		 		fv.setParent(rs.getInt(2));
		 		fv.setNewName(rs.getString(3));
		 		fv.setOriName(rs.getString(4));
		 		fv.setDownload(rs.getInt(5));
		 	}
		 	close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return fv;
	}
	
	public List<ArticleVO> selectComments(String parent) {
		
		List<ArticleVO> comments = new ArrayList<>();
		
		try {
			logger.info("selectComments...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_COMMENTS);
			psmt.setString(1, parent);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ArticleVO comment = new ArticleVO();
				comment.setNo(rs.getInt(1));
				comment.setParent(rs.getInt(2));
				comment.setComment(rs.getInt(3));
				comment.setCate(rs.getString(4));
				comment.setTitle(rs.getString(5));
				comment.setContent(rs.getString(6));
				comment.setFile(rs.getInt(7));
				comment.setHit(rs.getInt(8));
				comment.setUid(rs.getString(9));
				comment.setRegip(rs.getString(10));
				comment.setRdate(rs.getString(11));
				comment.setNick(rs.getString(12));
				
				comments.add(comment);
			}
			
			close();
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return comments;
	}
	
	public void updateArticle(String no, String title, String content) {
		
		try {
			logger.info("updateArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setString(3, no);
			
			psmt.executeUpdate();
			close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void updateArticleHit(String no) {
		try {
			logger.info("updateArticleHit...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE_HIT);
			psmt.setString(1, no);
			psmt.executeUpdate();

			close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void updateFileDownload(int fno) {
		try {
			logger.info("updateFileDownload...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_FILE_DOWNLOAD);
			psmt.setInt(1, fno);
			psmt.executeUpdate();
			psmt.close();
			conn.close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public int updateComment(String no, String content) {
		int result = 0;
		try {
			logger.info("updateComment...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_COMMENT);
			psmt.setString(1, content);
			psmt.setString(2, no);
			
			result = psmt.executeUpdate();
			
			close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	public void deleteArticle(String no) {
		try {
			logger.info("deleteArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.DELETE_ARTICLE);
			psmt.setString(1, no);
			psmt.setString(2, no);
			psmt.executeUpdate();

			close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public String deleteFile(String no) {
		
		String newName = null;
		
		try {
			logger.info("deleteFile...");
			conn = getConnection();
			
			conn.setAutoCommit(false);
			PreparedStatement psmt1 = conn.prepareStatement(Sql.SELECT_FILE);
			PreparedStatement psmt2 = conn.prepareStatement(Sql.DELETE_FILE);
			
			psmt1.setString(1, no);
			psmt2.setString(1, no);
			
			rs = psmt1.executeQuery();
			psmt2.executeUpdate();
			
			conn.commit();
			
			if(rs.next()) {
				newName = rs.getString(3);
			}
			
			close();
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return newName;
	}
	
	public int deleteComment(String no, String parent) {
		int result = 0;
		try {
			logger.info("deleteComment...");
			conn = getConnection();
			
			conn.setAutoCommit(false);
			PreparedStatement psmt1 = conn.prepareStatement(Sql.DELETE_COMMENT);
			PreparedStatement psmt2 = conn.prepareStatement(Sql.UPDATE_ARTICLE_COMMENT_MINUS);
			psmt1.setString(1, no);
			psmt2.setString(1, parent);
			result = psmt1.executeUpdate();
			psmt2.executeUpdate();
			
			conn.commit();
			
			close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
}
