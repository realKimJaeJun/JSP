package kr.co.Farmstory2.dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.Farmstory2.db.DBCP;
import kr.co.Farmstory2.db.Sql;
import kr.co.Farmstory2.service.FileService;
import kr.co.Farmstory2.vo.FileVO;
import kr.co.Farmstory2.vo.articleVO;

public class ArticleDAO extends DBCP {

	private FileService service = FileService.INSTANCE;
	
	// create
	/**
	 * 글작성 - 글 정보 저장
	 * @param vo
	 * @param fileName
	 * @param saveDirectory
	 */
	public void insertArticle(articleVO vo, String fileName, String saveDirectory) {
		try {
			logger.info("insertArticle...");
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(Sql.INSERT_ARTICLE);
			stmt = conn.createStatement();
			
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getContent());
			psmt.setString(3, vo.getCate());
			psmt.setInt(4, fileName == null ? 0 : 1);
			psmt.setString(5, vo.getUid());
			psmt.setString(6, vo.getRegip());
			
			psmt.executeUpdate();
			rs = stmt.executeQuery(Sql.SELECT_MAX_NO);
			
			conn.commit();
			
			int parent = 0;
			if (rs.next()) parent = rs.getInt(1);
			
			if(fileName != null) {
				// 첨부 파일이 있을 경우 파일명 변경
				// 새로운 파일명 생성
				String now = new SimpleDateFormat("yyyyMMddHHmmss_").format(new Date());
				String ext = fileName.substring(fileName.lastIndexOf("."));
				String newFileName = now + vo.getUid() + ext;
				
				// 파일명 변경
				File oldFile = new File(saveDirectory + File.separator + fileName);
				File newFile = new File(saveDirectory + File.separator + newFileName);
				oldFile.renameTo(newFile);
				
				// 파일 테이블 Insert
				service.insertFile(parent, newFileName, fileName);
			}
			
			close();
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 댓글작성 - 댓글 정보 저장
	 * @param vo
	 * @return
	 */
	public int insertComment(articleVO vo) {
		int result = 0;
		try {
			logger.info("insertComment...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.INSERT_COMMENT);
			psmt.setInt(1, vo.getParent());
			psmt.setString(2, vo.getContent());
			psmt.setString(3, vo.getUid());
			psmt.setString(4, vo.getRegip());
			result = psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	// read
	public List<articleVO> selectArticles(String cateName, int limitStart, String search) {
		List<articleVO> avos = new ArrayList<>();
		String word = "";
		if (search != null) word = "%"+search+"%";
		else word = "%%";
		try {
			logger.info("selectArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLES);
			psmt.setString(1, cateName);
			psmt.setString(2, word);
			psmt.setString(3, word);
			psmt.setInt(4, limitStart);
			rs = psmt.executeQuery();
			while(rs.next()) {
				articleVO vo = new articleVO();
				vo.setNo(rs.getInt(1));
				vo.setParent(rs.getInt(2));
				vo.setComment(rs.getInt(3));
				vo.setCate(rs.getString(4));
				vo.setTitle(rs.getString(5));
				vo.setContent(rs.getString(6));
				vo.setFile(rs.getInt(7));
				vo.setHit(rs.getInt(8));
				vo.setUid(rs.getString(9));
				vo.setRegip(rs.getString(10));
				vo.setRdate(rs.getString(11).substring(2, 10));
				vo.setNick(rs.getString(12));
				avos.add(vo);
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return avos;
	}
	
	/**
	 * view - 게시물 정보 불러오기 (글 정보, 파일 정보)
	 * @param no
	 * @return
	 */
	public Map<String, Object> selectArticle(String no) {
		Map<String, Object> vos = new HashMap<>();
		articleVO avo = new articleVO();
		FileVO fvo = new FileVO();
		try {
			logger.info("selectArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE);
			psmt.setString(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
				avo.setNo(rs.getInt(1));
				avo.setParent(rs.getInt(2));
				avo.setComment(rs.getInt(3));
				avo.setCate(rs.getString(4));
				avo.setTitle(rs.getString(5));
				avo.setContent(rs.getString(6));
				avo.setFile(rs.getInt(7));
				avo.setHit(rs.getInt(8));
				avo.setUid(rs.getString(9));
				avo.setRegip(rs.getString(10));
				avo.setRdate(rs.getString(11));
				fvo.setFno(rs.getInt(12));
				fvo.setParent(rs.getInt(13));
				fvo.setNewName(rs.getString(14));
				fvo.setOriName(rs.getString(15));
				fvo.setDownload(rs.getInt(16));
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		vos.put("avo", avo);
		vos.put("fvo", fvo);
		return vos;
	}
	
	/**
	 * index - latests 게시물 정보 불러오기
	 * @param cate1
	 * @param cate2
	 * @param cate3
	 * @return
	 */
	public List<articleVO> selectArticleLatests(String cate1, String cate2, String cate3) {
		List<articleVO> vos = new ArrayList<>();
		try {
			logger.info("selectArticleLatests...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE_LATESTS);
			psmt.setString(1, cate1);
			psmt.setString(2, cate2);
			psmt.setString(3, cate3);
			rs = psmt.executeQuery();
			while(rs.next()) {
				articleVO vo = new articleVO();
				vo.setNo(rs.getInt("no"));
				vo.setCate(rs.getString("cate"));
				vo.setTitle(rs.getString("title"));
				vo.setRdate(rs.getString("rdate").substring(2, 10));
				vos.add(vo);
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vos;
	}
	
	/**
	 * index - latest 게시물 정보 불러오기
	 * @param cate
	 * @return
	 */
	public List<articleVO> selectarticlelatest(String cate) {
		List<articleVO> vos = new ArrayList<>();
		try {
			logger.info("selectarticlelatest...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE_LATEST);
			psmt.setString(1, cate);
			rs = psmt.executeQuery();
			while(rs.next()) {
				articleVO vo = new articleVO();
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				vo.setRdate(rs.getString("rdate").substring(2, 10));
				vos.add(vo);
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vos;
	}
	
	/**
	 * view - 댓글들 정보 불러오기
	 * @param parent
	 * @return
	 */
	public List<articleVO> selectArticleComment(String parent) {
		List<articleVO> vos = new ArrayList<>();
		try {
			logger.info("selectArticleComment...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE_COMMENTS);
			psmt.setString(1, parent);
			rs = psmt.executeQuery();
			while(rs.next()) {
				articleVO vo = new articleVO();
				vo.setNo(rs.getInt("no"));
				vo.setParent(rs.getInt("parent"));
				vo.setCate(rs.getString("cate"));
				vo.setContent(rs.getString("content"));
				vo.setUid(rs.getString("uid"));
				vo.setRegip(rs.getString("regip"));
				vo.setRdate(rs.getString("rdate").substring(2, 10));
				vo.setNick(rs.getString("nick"));
				vos.add(vo);
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vos;
	}
	
	/**
	 * 카테고리별 전체 게시물 갯수 + 검색기능
	 * @param search
	 * @param cateName
	 * @return
	 */
	public int selectCountArticles(String search, String titName) {
		int total = 0;
		String word = "";
		if (search != null) word = "%"+search+"%";
		else word = "%%";
		try {
			logger.info("selectCountArticles...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_COUNT_ARTICLES);
			psmt.setString(1, titName);
			psmt.setString(2, word);
			psmt.setString(3, word);
			rs = psmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}
	
	// upload
	/**
	 * modify - 게시글 수정
	 * @param title
	 * @param content
	 * @param no
	 */
	public void updateArticle(String title, String content, String no) {
		try {
			logger.info("updateArticle...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setString(3, no);
			psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * view - 게시물 조회수 증가
	 * @param no
	 */
	public void updateHitCount(String no) {
		try {
			logger.info("updateHitCount...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE_HIT_PLUS);
			psmt.setString(1, no);
			psmt.executeUpdate();
			close();
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 댓글작성 - 댓글 갯수 증가, 마지막 댓글의 작성시간 and 등록 번호 return
	 * @param no
	 * @return
	 */
	public articleVO updateCommnetPlus(int no) {
		articleVO vo = new articleVO();
		try {
			logger.info("updateCommnetPlus...");
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE_COMMENT_PLUS);
			psmt.setInt(1, no);
			psmt.executeUpdate();
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(Sql.SELECT_ARTICLE_LAST_COMMENT_TIME);
			if(rs.next()) {
				vo.setRdate(rs.getString("rdate"));
				vo.setNo(rs.getInt("no"));
			}
			
			conn.commit();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vo;
	}
	
	/**
	 * 댓글수정 - 댓글수정 기능
	 * @param content
	 * @param no
	 * @return
	 */
	public int updateComment(String content, int no) {
		int result = 0;
		try {
			logger.info("updateComment...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_COMMENT);
			psmt.setString(1, content);
			psmt.setInt(2, no);
			result = psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 댓글삭제 - 댓글삭제시 댓글 갯수 감소 기능
	 * @param parent
	 */
	public void uploadArticleCommentMinus(String parent) {
		try {
			logger.info("uploadArticleCommentMinus...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE_COMMENT_MINUS);
			psmt.setString(1, parent);
			psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	// delete
	/**
	 * 댓글삭제 - 댓글삭제 기능
	 * @param no
	 * @return
	 */
	public int deleteComment(int no) {
		int result = 0;
		try {
			logger.info("deleteComment...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.DELETE_ARTICLE_COMMENT);
			psmt.setInt(1, no);
			result = psmt.executeUpdate();
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 게시물 삭제 - 게시물, 댓글, 파일 삭제 기능
	 * @param no
	 * @param fileCheck
	 * @param path
	 */
	public void deleteArticle(String no, int fileCheck, String path) {
		try {
			logger.info("deleteArticle...");
			conn = getConnection();
			conn.setAutoCommit(false);
			String fileName = null;
			
			if(fileCheck > 0) {
				psmt = conn.prepareStatement(Sql.SELECT_FILE);
				psmt.setString(1, no);
				rs = psmt.executeQuery();
				if(rs.next()) {
					fileName = rs.getString("newName");
				}
				if(fileName != null) {
					File file = new File(path, fileName);
					if(file.exists()) {
						file.delete();
					}
				}
			}
			
			psmt = conn.prepareStatement(Sql.DELETE_ARTICLE);
			psmt.setString(1, no);
			psmt.setString(2, no);
			psmt.setString(3, no);
			psmt.executeUpdate();
			conn.commit();
			
			close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
