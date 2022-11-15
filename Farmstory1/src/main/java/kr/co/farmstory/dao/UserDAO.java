package kr.co.farmstory.dao;

import kr.co.farmstory.bean.TermsBean;
import kr.co.farmstory.db.DBHelper;
import kr.co.farmstory.db.Sql;

public class UserDAO extends DBHelper {

	private static UserDAO instance = new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	private UserDAO() {}
	
	
	
	public TermsBean selectTerms() {
		
		TermsBean tb = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(Sql.SELECT_TERMS);
			if(rs.next()) {
				tb = new TermsBean();
				tb.setTerms(rs.getString(1));
				tb.setPrivacy(rs.getString(2));
			}
			close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tb;		
	}
	
	
	
}
