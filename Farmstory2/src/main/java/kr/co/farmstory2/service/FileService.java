package kr.co.Farmstory2.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.Farmstory2.dao.FileDAO;
import kr.co.Farmstory2.vo.FileVO;

public enum FileService {
	
	INSTANCE;
	private FileDAO dao;
	private FileService() {
		dao = new FileDAO();
	}
	
	// create
	public void insertFile(int parent, String newName, String fileName) {
		dao.insertFile(parent, newName, fileName);
	}
	
	// read
	/**
	 * 파일 정보 불러오기
	 * @param no
	 * @return
	 */
	public FileVO selectFile(String no) {
		return dao.selectFile(no);
	}
	
	// upload
	/**
	 * 파일 다운로드 횟수 증가
	 * @param fno
	 */
	public void updateFileDownloadCount(int fno) {
		dao.updateFileDownloadCount(fno);
	}
	
	// delete
	
	// service
	/**
	 * 파일 전송 세팅
	 * @param oriName
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	public void fileDownloadSetting(String oriName, HttpServletResponse resp) throws UnsupportedEncodingException {
		oriName = URLEncoder.encode(oriName, "UTF-8");
		oriName = oriName.replaceAll("\\+", "%20");
		
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename="+oriName+";");
		resp.setHeader("Content-Transfer-Encoding", "binary");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "private");
	}

	/**
	 * 파일 전송 기능
	 * @param savePath
	 * @param out
	 * @param newName
	 * @throws IOException
	 */
	public void fileDownloadStart(String savePath, ServletOutputStream out, String newName) throws IOException {
		File file = new File(savePath + "/" + newName);
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		while(true) {
			int data = bis.read();
			
			if(data == -1) break;
			
			bos.write(data);
		}
		
		bos.close();
		bis.close();
	}
}
