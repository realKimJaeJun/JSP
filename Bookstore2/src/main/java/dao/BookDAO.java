package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import vo.BookVO;

public class BookDAO extends DBHelper{
	
	private static BookDAO instance = new BookDAO();
	public static BookDAO getInstance() {
		return instance;
	}
	private BookDAO() {}
	
	public void insertBook(BookVO vo) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `book` VALUES (?,?,?,?)");
			psmt.setInt(1, vo.getBookId());
			psmt.setString(2, vo.getBookName());
			psmt.setString(3, vo.getPublisher());
			psmt.setInt(4, vo.getPrice());
			psmt.executeUpdate();
			
			close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		public BookVO selectBook(int bookId) {
			
			BookVO vo = null;
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("SELECT * FROM `book` WHERE `bookId`=?");
				psmt.setInt(1, bookId);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					vo = new BookVO();
					vo.setBookId(rs.getInt(1));
					vo.setBookName(rs.getString(2));
					vo.setPublisher(rs.getString(3));
					vo.setPrice(rs.getInt(4));
				}
				close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vo;
		}
		public List<BookVO> selectBooks() {
			
			List<BookVO> books = new ArrayList<>();
			
			try {
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM `book`");
				
				while(rs.next()) {
					BookVO vo = new BookVO();
					vo.setBookId(rs.getInt(1));
					vo.setBookName(rs.getString(2));
					vo.setPublisher(rs.getString(3));
					vo.setPrice(rs.getInt(4));
					
					books.add(vo);
				}
				close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return books;
		}
		public void updateBook(BookVO vo) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("UPDATE `book` SET `bookName`=?, `publisher`=?, `price`=? WHERE `bookId`=?");
				psmt.setInt(1, vo.getBookId());
				psmt.setString(2, vo.getBookName());
				psmt.setString(3, vo.getPublisher());
				psmt.setInt(4, vo.getPrice());
				psmt.executeUpdate();
				close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void deleteBook(int bookId) {
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement("DELETE FROM `book` WHERE `bookId`=?");
				psmt.setInt(1, bookId);
				psmt.executeUpdate();
				close();	
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
