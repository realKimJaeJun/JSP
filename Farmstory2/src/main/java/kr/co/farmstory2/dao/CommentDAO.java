package kr.co.farmstory2.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.farmstory2.db.DBHelper;
import kr.co.farmstory2.db.Sql;
import kr.co.farmstory2.vo.ArticleVO;

public class CommentDAO extends DBHelper {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public Map<String, Object> insertComment(ArticleVO vo) {
		int result = 0;
		Map<String, Object> map = null;
		try {
			logger.info("insertComment...");
			map = new HashMap<>();
			
			con = getConnection();
			con.setAutoCommit(false);
			psmt = con.prepareStatement(Sql.INSERT_COMMENT);
			psmt.setInt(1, vo.getNo());
			psmt.setString(2, vo.getContent());
			psmt.setString(3, vo.getUid());
			psmt.setString(4, vo.getRegip());
			result = psmt.executeUpdate();
			map.put("result", result);
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(Sql.SELECT_LATEST_COMMENT);
			con.commit();
			
			if(rs.next()) {
				map.put("no", rs.getInt("no"));
				map.put("rdate", rs.getString("rdate").substring(2, 10));
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.debug("map : " + map);
		return map;
	}
	
	public int deleteComment(String no) {
		int result = 0;
		
		try {
			logger.info("deleteComment...");
			con = getConnection();
			psmt = con.prepareStatement(Sql.DELETE_COMMENT);
			psmt.setString(1, no);
			
			result = psmt.executeUpdate();
			
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.debug("result : " + result);
		return result;
	}
	
	public int updateComment(String no, String comment) {
		int result = 0;
		
		try {
			logger.info("updateComment...");
			con = getConnection();
			psmt = con.prepareStatement(Sql.UPDATE_COMMENT);
			psmt.setString(1, comment);
			psmt.setString(2, no);
			
			result = psmt.executeUpdate();
			
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.debug("result : " + result);
		return result;
	}
	
	public void plusComment(String no) {
		try {
			logger.info("plusComment...");
			con = getConnection();
			psmt = con.prepareStatement(Sql.PLUS_COMMENT);
			psmt.setString(1, no);
			
			psmt.executeUpdate();
			
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void minusComment(String no) {
		try {
			logger.info("minusComment...");
			con = getConnection();
			psmt = con.prepareStatement(Sql.MINUS_COMMENT);
			psmt.setString(1, no);
			
			psmt.executeUpdate();
			
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}