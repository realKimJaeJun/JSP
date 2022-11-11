package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.User1VO;

public class User1DAO extends DBHelper {

	private static User1DAO instance = new User1DAO();
	public static User1DAO getInstance() {
		return instance;
	}
	private User1DAO() {}
	
	public void insertUser1(User1VO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `user1` VALUES (?,?,?,?)");
			psmt.setString(1, vo.getUid());
			psmt.setString(2, vo.getName());
			psmt.setString(3, vo.getHp());
			psmt.setInt(4, vo.getAge());
			psmt.executeUpdate();
			
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public User1VO selectUser1(String uid) {
		
		User1VO vo = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `user1` WHERE `uid`=?");
			psmt.setString(1, uid);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				vo = new User1VO();
				vo.setUid(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setHp(rs.getString(3));
				vo.setAge(rs.getInt(4));
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
		
	}
	
	public List<User1VO> selectUser1s() {
		List<User1VO> users = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `user1`");
			
			while(rs.next()) {
				User1VO vo = new User1VO();
				vo.setUid(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setHp(rs.getString(3));
				vo.setAge(rs.getInt(4));
				users.add(vo);
				
			}
			
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public void updateUser1(User1VO vo) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("UPDATE `user1` SET `name`=?, `hp`=?, `age`=? WHERE `uid`=?");
			psmt.setString(1, vo.getName());
			psmt.setString(2, vo.getHp());
			psmt.setInt(3, vo.getAge());
			psmt.setString(4, vo.getUid());
			psmt.executeUpdate();
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void deleteUser1(String uid) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("DELETE FROM `user1` WHERE `uid`=?");
			psmt.setString(1, uid);
			psmt.executeUpdate();
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
