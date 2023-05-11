package kr.co.Farmstory2.db;

public class Sql {
	// user
	public static final String INSERT_USER = "INSERT INTO `board_user` SET "
			+ "`uid`=?, "
			+ "`pass`=SHA2(?, 256), "
			+ "`name`=?, "
			+ "`nick`=?, "
			+ "`email`=?, "
			+ "`hp`=?, "
			+ "`zip`=?, "
			+ "`addr1`=?, "
			+ "`addr2`=?, "
			+ "`regip`=?, "
			+ "`rdate`=NOW()";
	
	public static final String SELECT_USER = 
			"SELECT * FROM `board_user` WHERE `uid`=? AND `pass`=SHA2(?, 256)";
	public static final String SELECT_USER_BY_SESSID = 
			"SELECT * FROM `board_user` WHERE `sessId`=? AND `sesslimitdate` > NOW()";
	public static final String SELECT_USER_EMAIL = 
			"SELECT "
			+ "`uid`, "
			+ "`pass`, "
			+ "`name`, "
			+ "`nick`, "
			+ "`email`, "
			+ "`hp`, "
			+ "`rdate` "
			+ "FROM `board_user` WHERE `email`=?";
	public static final String SELECT_COUNT_UID = 
			"SELECT COUNT(`uid`) FROM `board_user` WHERE `uid`=?";
	public static final String SELECT_COUNT_NICK = 
			"SELECT COUNT(`nick`) FROM `board_user` WHERE `nick`=?";
	public static final String SELECT_COUNT_USER_NAME_EMAIL = 
			"SELECT COUNT(`name`) FROM `board_user` WHERE `name`=? AND `email`=?";
	public static final String SELECT_COUNT_USER_UID_EMAIL =
			"SELECT COUNT(`uid`) FROM `board_user` WHERE `uid`=? AND `email`=?";
	public static final String SELECT_TERMS = "SELECT * FROM `board_terms`";
	
	public static final String UPDATE_USER_FOR_SESSION = 
			"UPDATE `board_user` SET "
			+ "`sessId`=?, "
			+ "`sessLimitDate` = DATE_ADD(NOW(), INTERVAL 3 DAY) "
			+ "WHERE `uid`=?";
	public static final String UPDATE_USER_FOR_SESS_LIMIT_DATE = 
			"UPDATE `board_user` SET "
			+ "`sessLimitDate` = DATE_ADD(NOW(), INTERVAL 3 DAY) "
			+ "WHERE `sessId`=?";
	public static final String UPDATE_USER_FOR_SESSION_OUT=
			"UPDATE `board_user` SET "
			+ "`sessId`=null, "
			+ "`sessLimitDate`=null "
			+ "WHERE `uid`=?";
	public static final String UPDATE_USER_PASS =
			"UPDATE `board_user` SET `pass` = SHA2(?, 256) WHERE `uid`=?";
	
	// board
	public static final String INSERT_ARTICLE = 
			"INSERT INTO `board_article` SET "
			+ "`title`=?, "
			+ "`content`=?, "
			+ "`cate`=?, "
			+ "`file`=?, "
			+ "`uid`=?, "
			+ "`regip`=?, "
			+ "`rdate`=NOW()";
	public static final String INSERT_FILE = 
			"INSERT INTO `board_file` SET "
			+ "`parent`=?, "
			+ "`newName`=?, "
			+ "`oriName`=?";
	public static final String INSERT_COMMENT = 
			"INSERT INTO `board_article` SET "
			+ "`parent`=?, "
			+ "`cate`='comment', "
			+ "`content`=?, "
			+ "`uid`=?, "
			+ "`regip`=?, "
			+ "`rdate`=NOW()";
	
	public static final String SELECT_ARTICLES = 
			"SELECT "
			+ "a.*, "
			+ "b.`nick` "
			+ "FROM `board_article` AS a "
			+ "JOIN `board_user` AS b ON a.`uid` = b.`uid` "
			+ "WHERE "
			+ "a.`cate` = ? "
			+ "AND (a.`title` LIKE ? "
			+ "OR b.`nick` LIKE ?) "
			+ "ORDER BY a.`no` DESC "
			+ "LIMIT ?, 10";
	public static final String SELECT_ARTICLE = 
			"SELECT * "
			+ "FROM `board_article` AS a "
			+ "LEFT JOIN `board_file` AS b ON a.no = b.parent "
			+ "WHERE `no`=?";
	public static final String SELECT_ARTICLE_LATEST = 
			"SELECT `no`, `cate`, `title`, `rdate` FROM `board_article` WHERE `cate`=? ORDER BY `no` DESC LIMIT 3";
	public static final String SELECT_ARTICLE_LATESTS = 
			"(SELECT `no`, `cate`, `title`, `rdate` FROM `board_article` WHERE `cate`=? ORDER BY `no` DESC LIMIT 5) "
			+ "UNION "
			+ "(SELECT `no`, `cate`, `title`, `rdate` FROM `board_article` WHERE `cate`=? ORDER BY `no` DESC LIMIT 5) "
			+ "UNION "
			+ "(SELECT `no`, `cate`, `title`, `rdate` FROM `board_article` WHERE `cate`=? ORDER BY `no` DESC LIMIT 5)";
	public static final String SELECT_FILE = 
			"SELECT * FROM `board_file` WHERE `parent`=?";
	public static final String SELECT_ARTICLE_COMMENTS = 
			"SELECT "
			+ "a.`no`, a.`parent`, a.`cate`, a.`content`, a.`uid`, a.`regip`, "
			+ "a.`rdate`, u.`nick` "
			+ "FROM `board_article` AS a "
			+ "JOIN `board_user` AS u ON a.uid = u.uid "
			+ "WHERE `parent`=? "
			+ "ORDER BY `no` ASC";
	public static final String SELECT_ARTICLE_LAST_COMMENT_TIME = 
			"SELECT `rdate`, `no` FROM `board_article` ORDER BY `rdate` DESC "
			+ "LIMIT 1";
	public static final String SELECT_COUNT_ARTICLES = 
			"SELECT COUNT(a.`no`) "
			+ "FROM `board_article` AS a "
			+ "JOIN `board_user` AS b ON a.`uid` = b.`uid` "
			+ "WHERE "
			+ "a.`cate` = ? "
			+ "AND (a.`title` LIKE ? "
			+ "OR b.`nick` LIKE ?)";
	public static final String SELECT_MAX_NO = 
			"SELECT MAX(`no`) FROM `board_article`";
	
	public static final String UPDATE_ARTICLE = 
			"UPDATE `board_article` SET "
			+ "`title`=?, "
			+ "`content`=?, "
			+ "`rdate`=NOW() "
			+ "WHERE `no`=?";
	public static final String UPDATE_ARTICLE_HIT_PLUS = 
			"UPDATE `board_article` SET `hit` = `hit`+1 WHERE `no`=?";
	public static final String UPDATE_ARTICLE_COMMENT_PLUS = 
			"UPDATE `board_article` SET `comment` = `comment`+1 "
			+ "WHERE `no`=?";
	public static final String UPDATE_ARTICLE_COMMENT_MINUS = 
			"UPDATE `board_article` SET `comment` = `comment`-1 "
			+ "WHERE `no`=?";
	public static final String UPDATE_COMMENT = 
			"UPDATE `board_article` SET "
			+ "`content`=?, "
			+ "`rdate`=NOW() "
			+ "WHERE `no`=?";
	public static final String UPDATE_FILE_DOWNLOAD_COUNT = 
			"UPDATE `board_file` SET `download` = `download`+1 "
			+ "WHERE `fno`=?";
	
	public static final String DELETE_ARTICLE = 
			"DELETE FROM a, f "
			+ "USING `board_article` AS a "
			+ "LEFT JOIN `board_file` AS f "
			+ "ON a.`no` = f.`parent` "
			+ "WHERE a.`no` = ? OR a.`parent`=? OR f.`parent`=?";
	public static final String DELETE_ARTICLE_COMMENT = 
			"DELETE FROM `board_article` WHERE `no`=?";
}
