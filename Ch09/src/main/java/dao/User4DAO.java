package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.User4VO;

public class User4DAO extends DBHelper{
	
	private static User4DAO instance = new User4DAO();
	public static User4DAO getInstance() {
		return instance;
	}
	
	private User4DAO() {}
	
	public void inserUser4(User4VO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `user4` VALUES (?,?,?,?,?)");
			psmt.setInt(1, vo.getSeq());
			psmt.setString(2, vo.getName());
			psmt.setInt(3, vo.getGender());
			psmt.setInt(4, vo.getAge());
			psmt.setString(5, vo.getAddr());
			psmt.executeUpdate();
			
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User4VO selectUser4(int seq) {
		
		User4VO vo = null;
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `user4` WHERE `seq`=?");
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				vo = new User4VO();
				vo.setSeq(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setGender(rs.getInt(3));
				vo.setAge(rs.getInt(4));
				vo.setAddr(rs.getString(5));
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	public List<User4VO> selectUser4s() {
		
		List<User4VO> users = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `user4`");
			
			while(rs.next()) {
				User4VO vo = new User4VO();
				vo.setSeq(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setGender(rs.getInt(3));
				vo.setAge(rs.getInt(4));
				vo.setAddr(rs.getString(5));
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public void updateUser4(User4VO vo) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("UPDATE `user4` SET `name`=?, `gender`=?, `age`=?, `addr`=? WHERE `seq`=?");
			psmt.setInt(1, vo.getSeq());
			psmt.setString(2, vo.getName());
			psmt.setInt(3, vo.getGender());
			psmt.setInt(4, vo.getAge());
			psmt.setString(5, vo.getAddr());
			psmt.executeUpdate();
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteUser4(int seq) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("DELETE FROM `user4` WHERE `seq`=?");
			psmt.setInt(1, seq);
			psmt.executeUpdate();
			close();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
