package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.BookVO;
import vo.CustomerVO;

public class CustomerDAO extends DBHelper{
	
	private static CustomerDAO instance = new CustomerDAO();
	public static CustomerDAO getInstance() {
		return instance;
	}
	private CustomerDAO() {}
	
	public void insertCust(CustomerVO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `customer` VALUES (?,?,?,?)");
			psmt.setInt(1, vo.getCustId());
			psmt.setString(2, vo.getName());
			psmt.setString(3, vo.getAddress());
			psmt.setString(4, vo.getPhone());
			psmt.executeUpdate();
			
			close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		public CustomerVO selectCust(int custId) {
			
			CustomerVO vo = null;
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("SELECT * FROM `customer` WHERE `custId`=?");
				psmt.setInt(1, custId);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					vo = new CustomerVO();
					vo.setCustId(rs.getInt(1));
					vo.setName(rs.getString(2));
					vo.setAddress(rs.getString(3));
					vo.setPhone(rs.getString(4));
				}
				close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vo;
		}
		public List<CustomerVO> selectCusts() {
			
			List<CustomerVO> custs = new ArrayList<>();
			
			try {
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM `customer`");
				
				while(rs.next()) {
					CustomerVO vo = new CustomerVO();
					vo.setCustId(rs.getInt(1));
					vo.setName(rs.getString(2));
					vo.setAddress(rs.getString(3));
					vo.setPhone(rs.getString(4));
				}
				close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return custs;
		}
		public void updateCust(CustomerVO vo) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("UPDATE `customer` SET `name`=?, `address`=?, `phone`=? WHERE `custId`=?");
				psmt.setInt(1, vo.getCustId());
				psmt.setString(2, vo.getName());
				psmt.setString(3, vo.getAddress());
				psmt.setString(4, vo.getPhone());
				psmt.executeUpdate();
				close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void deleteCust(int custId) {
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("DELETE FROM `customer` WHERE `custId`=?");
				psmt.setInt(1, custId);
				psmt.executeUpdate();
				close();	
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
