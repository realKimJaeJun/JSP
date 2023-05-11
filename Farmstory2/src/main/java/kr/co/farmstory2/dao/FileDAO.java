package kr.co.Farmstory2.dao;

import kr.co.Farmstory2.db.DBCP;
import kr.co.Farmstory2.db.Sql;
import kr.co.Farmstory2.vo.FileVO;

public class FileDAO extends DBCP {
	// create
	/**
	 * 글작성 - 파일 저장
	 * @param parent
	 * @param newName
	 * @param fileName
	 */
	public void insertFile(int parent, String newName, String fileName) {
		try {
			logger.info("insertFile...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.INSERT_FILE);
			psmt.setInt(1, parent);
			psmt.setString(2, newName);
			psmt.setString(3, fileName);
			psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	// read
	/**
	 * download - 파일 정보 불러오기
	 * @param no
	 * @return
	 */
	public FileVO selectFile(String no) {
		FileVO vo = new FileVO();
		try {
			logger.info("selectFile...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_FILE);
			psmt.setString(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
				vo.setFno(rs.getInt("fno"));
				vo.setParent(rs.getInt("parent"));
				vo.setNewName(rs.getString("newName"));
				vo.setOriName(rs.getString("oriName"));
				vo.setDownload(rs.getInt("download"));
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vo;
	}
	
	// upload
	/**
	 * download - 파일 다운로드 횟수 증가
	 * @param fno
	 */
	public void updateFileDownloadCount(int fno) {
		try {
			logger.info("updateFileDownloadCount...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_FILE_DOWNLOAD_COUNT);
			psmt.setInt(1, fno);
			psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	// delete
}
