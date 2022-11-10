package service.user1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListServiceImpl implements CommonService{
	
	public static ListServiceImpl instance = new ListServiceImpl();
	public static ListServiceImpl getInstance() {
		return instance;
	}
	private ListServiceImpl() {}

	@Override
	public String requestProc(HttpServletRequest req, HttpServletResponse resp) {
		return "/user1/list.jsp";
	}

}
