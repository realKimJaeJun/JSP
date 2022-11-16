package kr.co.farmstory1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import kr.co.farmstory1.bean.TermsBean;
import kr.co.farmstory1.bean.UserBean;
import kr.co.farmstory1.db.DBCP;
import kr.co.farmstory1.db.Sql;

public enum UserDAO {
	
	INSTANCE;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void insertUser(UserBean ub) {
		try{
			logger.info("insertUser...");
			Connection conn = DBCP.getConnection();
			PreparedStatement psmt = conn.prepareStatement(Sql.INSERT_USER);
			psmt.setString(1, ub.getUid());
			psmt.setString(2, ub.getPass());
			psmt.setString(3, ub.getName());
			psmt.setString(4, ub.getNick());
			psmt.setString(5, ub.getEmail());
			psmt.setString(6, ub.getHp());
			psmt.setString(7, ub.getZip());
			psmt.setString(8, ub.getAddr1());
			psmt.setString(9, ub.getAddr2());
			psmt.setString(10, ub.getRegip());
			psmt.executeUpdate();
			
			conn.close();
			psmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	public TermsBean selectTerms() {
		
		TermsBean tb = null;
		try {
			Connection conn = DBCP.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Sql.SELECT_TERMS);
			
			if(rs.next()) {
				tb = new TermsBean();
				tb.setTerms(rs.getString(1));
				tb.setPrivacy(rs.getString(2));
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return tb;
	}
	public UserBean selectUser(String uid, String pass) {
		
		UserBean ub = null;
		try {
			logger.info("selectUser...");
			
			Connection conn = DBCP.getConnection();
			PreparedStatement psmt = conn.prepareStatement(Sql.SELECT_USER);
			psmt.setString(1, uid);
			psmt.setString(2, pass);
			ResultSet rs = psmt.executeQuery();
			
			if(rs.next()) {
				ub = new UserBean();
				ub.setUid(rs.getString(1));
				ub.setPass(rs.getString(2));
				ub.setName(rs.getString(3));
				ub.setNick(rs.getString(4));
				ub.setEmail(rs.getString(5));
				ub.setHp(rs.getString(6));
				ub.setGrade(rs.getInt(7));
				ub.setZip(rs.getString(8));
				ub.setAddr1(rs.getString(9));
				ub.setAddr2(rs.getString(10));
				ub.setRegip(rs.getString(11));
				ub.setRdate(rs.getString(12));
			}
			
			rs.close();
			psmt.close();
			conn.close();
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.debug("ub : "+ub);
		
		return ub;
	}
	public void selectUsers() {}
	public void updateUser() {}
	public void deleteUser() {}
}