package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.User2VO;

public class User2DAO extends DBHelper{
	
	private static User2DAO instance = new User2DAO();
	public static User2DAO getInstance() {
		return instance;
	}
	
	private User2DAO() {}
	
	public void inserUser2(User2VO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `user2` VALUES (?,?,?,?)");
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

	public void selectUser2() {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `user2` WHERE `uid`=?");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<User2VO> selectUser2s() {
		
		List<User2VO> users = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `user2`");
			
			while(rs.next()) {
				User2VO vo = new User2VO();
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
	public void updateUser2() {}
	public void deleteUser2() {}
	
}
