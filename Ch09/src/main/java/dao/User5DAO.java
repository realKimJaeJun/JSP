package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.User5VO;

public class User5DAO extends DBHelper{
	
	private static User5DAO instance = new User5DAO();
	public static User5DAO getInstance() {
		return instance;
	}
	
	private User5DAO() {}
	
	public void inserUser5(User5VO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `user5` VALUES (?,?,?,?,?,?,?)");
			psmt.setString(1, vo.getUid());
			psmt.setString(2, vo.getName());
			psmt.setString(3, vo.getBirth());
			psmt.setInt(4, vo.getGender());
			psmt.setInt(5, vo.getAge());
			psmt.setString(6, vo.getAddr());
			psmt.setString(7, vo.getHp());
			psmt.executeUpdate();
			
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User5VO selectUser5(String uid) {
		
		User5VO vo = null;
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `user5` WHERE `uid`=?");
			psmt.setString(1, uid);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				vo = new User5VO();
				vo.setUid(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setBirth(rs.getString(3));
				vo.setGender(rs.getInt(4));
				vo.setAge(rs.getInt(5));
				vo.setAddr(rs.getString(6));
				vo.setHp(rs.getString(7));
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	public List<User5VO> selectUser5s() {
		
		List<User5VO> users = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `user5`");
			
			while(rs.next()) {
				User5VO vo = new User5VO();
				vo.setUid(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setBirth(rs.getString(3));
				vo.setGender(rs.getInt(4));
				vo.setAge(rs.getInt(5));
				vo.setAddr(rs.getString(6));
				vo.setHp(rs.getString(7));
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public void updateUser5(User5VO vo) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("UPDATE `user5` SET `name`=?, `birth`=?, `gender`=?, `age`=?, `addr`=?, `hp`=? WHERE `uid`=?");
			psmt.setString(1, vo.getUid());
			psmt.setString(2, vo.getName());
			psmt.setString(3, vo.getBirth());
			psmt.setInt(4, vo.getGender());
			psmt.setInt(5, vo.getAge());
			psmt.setString(6, vo.getAddr());
			psmt.setString(7, vo.getHp());
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteUser5(String uid) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("DELETE FROM `user5` WHERE `uid`=?");
			psmt.setString(1, uid);
			psmt.executeUpdate();
			close();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
