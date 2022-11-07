package config;

public class SQL {
	// lecture
	
	public static final String SELECT_LECTURE = "SELECT * FROM `lecture`";
	
	public static final String INSERT_LECTURE = "INSERT INTO `lecture` (`lecno`, `lecname`, `leccredit`, `lectime`, `lecclass`) "
											  + "values (?,?,?,?,?)";
	
	
	// register
	
	public static final String SELECT_REGISTER = "SELECT a.*, b.lecname, c.stdname FROM `register` AS a "
												+ "JOIN `lecture` AS b ON a.reglecno = b.lecno "
												+ "JOIN `student` AS c ON a.regstdno = c.stdno";
	
	public static final String INSERT_REGISTER = "INSERT INTO `register` (`regstdno`, `stdname`, `lecname`) "
			  									+ "values (?,?,?)";
	
	
	
	// student
	
	public static final String SELECT_STUDENT = "SELECT * FROM `student`";
	
	public static final String INSERT_STUDENT = "INSERT INTO `student` (`stdno`, `stdname`, `stdhp`, `stdyear`, `stdaddress`) "
			  									+ "values (?,?,?,?,?)";
}
