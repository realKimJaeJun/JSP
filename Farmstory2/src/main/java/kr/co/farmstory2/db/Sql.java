package kr.co.farmstory2.db;

public class Sql {

	/*** user ***/
	// terms
	public static final String SELECT_TERMS = "SELECT * FROM `board_terms`";
	
	
	// register
	public static final String CHECK_USER = "SELECT * FROM `board_user` WHERE `uid`= ?";
	public static final String CHECK_NICK = "SELECT * FROM `board_user` WHERE `nick`= ?";
	public static final String CHECK_EMAIL = "SELECT * FROM `board_user` WHERE `email` = ?";
	public static final String INSERT_USER = "INSERT INTO `board_user` SET "
			                               + " `uid`=?, "
			                               + " `pass`=SHA2(?, 256), "
			                               + " `name`=?, "
			                               + " `nick`=?, "
			                               + " `email`=?, "
			                               + " `hp`=?, "
			                               + " `zip`=?, "
			                               + " `addr1`=?, "
			                               + " `addr2`=?, "
			                               + " `regip`=?, "
			                               + " `rdate`=NOW() ";
	
	// login
	public static final String SELECT_USER = "SELECT * FROM `board_user` WHERE `uid`= ? and `pass`= SHA2(?, 256)";
	public static final String SELECT_USER_BY_SESSID = "SELECT * FROM `board_user` WHERE `sessId`=? AND `sessLimitDate` > NOW()";
	public static final String UPDATE_USER_FOR_SESSION = "UPDATE `board_user` SET `sessId`=?, `sessLimitDate` = DATE_ADD(NOW(),INTERVAL 3 DAY) WHERE `uid`= ?";
	public static final String UPDATE_USER_FOR_SESS_LIMIT_DATE = "UPDATE `board_user` SET `sessLimitDate` = DATE_ADD(NOW(),INTERVAL 3 DAY) WHERE `sessId`= ?";
	
	// logout
	public static final String UPDATE_USER_FOR_SESSION_OUT = "UPDATE `board_user` SET `sessId`= null, `sessLimitDate` = null WHERE `uid`=?";
	
	// 아이디 찾기
	public static final String SELECT_USER_FOR_FIND_ID = "SELECT * FROM `board_user` WHERE `name`= ? and `email` = ?";
	
	// 비밀번호 찾기
	public static final String SELECT_USER_FOR_FIND_PW = "SELECT * FROM `board_user` WHERE `uid`= ? and `email` = ?";
	
	// 비밀번호 변경
	public static final String UPDATE_USER_PW = "UPDATE `board_user` SET `pass`=SHA2(?, 256) WHERE `uid`=?";
											  
	
	/*** board ***/
	
	/* write */
	// 게시글 등록
	public static final String INSERT_ARTICLE = "INSERT INTO "
											  + " `board_article` SET "
											  + "	`cate`=?, "
		                                      + "	`title`=?, "
		                                      + "	`content`=?, "
		                                      + "	`file`=?, "
		                                      + "	`uid`=?, "
		                                      + "	`regip`=?, "
		                                      + "	`rdate`=NOW() ";
			                                       
	// 파일 등록
	public static final String INSERT_FILE = "INSERT INTO `board_file` SET "
										   + "	`parent`=(SELECT MAX(`no`) FROM `board_article`), "
										   + "	`newName`=?, "
										   + "	`oriName`=? ";

	/* view */
	// 조건에 해당하는 게시물 조회
	public static final String SELECT_ARTICLE = "SELECT a.*, f.`oriName`, f.`download`, u.`nick` FROM `board_article` a JOIN `board_user` u "
											  + "ON a.`uid` = u.`uid` "
			                                  + "LEFT JOIN `board_file` f  ON a.`no` = f.`parent` "
			                                  + "WHERE a.`no` = ? OR a.`parent`=?";
	
	public static final String PLUS_HIT = "UPDATE `board_article` SET `hit`= `hit`+1 WHERE `no`=?";
	/* view - download */
	// 파일 다운
	public static final String SELECT_FILE = "SELECT * FROM `board_file` WHERE parent=?";
	
	// 파일 다운로드 수 +1
	public static final String PLUS_DOWNLOAD = "UPDATE `board_file` SET `download` = `download`+1 WHERE `parent`=? ";

	/* delete */
	// 조건에 해당하는 게시물 및 관련 파일 댓글 삭제
	public static final String DELETE_ARTICLE = "DELETE a.*, f.* FROM `board_article` a LEFT JOIN `board_file` f "
			                                  + "ON a.`no` = f.`parent` WHERE a.`no` = ? OR a.`parent`=? ";
	/* update */
	// 조건에 해당하는 게시물 수정
	public static final String UPDATE_ARTICLE = "UPDATE `board_article` SET `title`=?, `content`=?, `rdate`=NOW() WHERE `no`=? ";
	public static final String UPDATE_FILE = "UPDATE `board_file` SET `newName`=?, `oriName`=? "
										   + "WHERE `parent`= ?";
	public static final String UPDATE_ADD_FILE = "INSERT INTO `board_file` SET `parent`=?, `newName`=?, `oriName`=?";
	
	// 조건에 해당하는 파일 삭제
	public static final String DELETE_FILE = "DELETE FROM `board_file` WHERE `parent`=?";
	
	/* index */
	// 조건에 해당하는 게시물 조회
	public static final String SELECT_ARTICLES = "SELECT * FROM(SELECT *, "
											   + "ROW_NUMBER() OVER(PARTITION BY `cate` ORDER BY `no` DESC) a FROM `board_article`) rankrow "
											   + "WHERE	rankrow.a <=5 AND `cate` IN ('croptalk1', 'croptalk2', 'croptalk3', "
											   + "'community1', 'community4', 'community5')";
	
	/*** comment ***/
	// 댓글 등록
	public static final String INSERT_COMMENT = "INSERT INTO `board_article` SET "
											  + "`parent`=?, "
											  + "`title`='댓글', "
											  + "`content`=?, "
											  + "`uid`=?, "
											  + "`regip`=?, "
											  + "`rdate`=NOW()";
	// 가장 최근 등록된 댓글 조회
	public static final String SELECT_LATEST_COMMENT = "SELECT * FROM `board_article` a JOIN `board_user` u "
													 + "ON a.`uid` = u.`uid` WHERE `parent` <> 0 "
													 + "ORDER BY `no` DESC LIMIT 1";
	// 댓글 삭제
	public static final String DELETE_COMMENT = "DELETE FROM `board_article` WHERE `no`=?";
	
	// 댓글 수정
	public static final String UPDATE_COMMENT = "UPDATE `board_article` SET "
											  + "`content`=?, rdate=NOW()  WHERE `no`=?";
	
	// 댓글 +1
	public static final String PLUS_COMMENT = "UPDATE `board_article` SET `comment`= `comment`+1 WHERE `no`=? ";
	
	// 댓글 -1
	public static final String MINUS_COMMENT = "UPDATE `board_article` SET `comment`= `comment`-1 WHERE `no` IN "
											 + "(SELECT t.parent FROM "
											 + "(SELECT `parent` FROM `board_article` WHERE `no`=? ) AS t)";
	
}