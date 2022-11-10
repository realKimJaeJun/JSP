package service.user1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServiceImpl implements CommonService{
	
	private static RegisterServiceImpl instance = new RegisterServiceImpl();
	public static RegisterServiceImpl getInstance() {
		return instance;
	}
	private RegisterServiceImpl() {}
	
	@Override
	public String requestProc(HttpServletRequest req, HttpServletResponse resp) {
		return "/user1/register.jsp";
	}

}
