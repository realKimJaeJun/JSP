package kr.co.Farmstory2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.Farmstory2.service.UserService;
import kr.co.Farmstory2.vo.userVO;

public class AutoLoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private UserService service = UserService.INSTANCE;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		logger.info("AutoLoginFilter... start");
		
		HttpServletRequest req = (HttpServletRequest)arg0;
		HttpSession sess = req.getSession();
		
		// 현재 로그인 상태 확인
		if(((userVO)sess.getAttribute("sessUser")) == null) {
			// 로그인 상태가 아닐 경우
			// 자동 로그인 여부에 따라 로그인 처리
			Cookie[] cookies = req.getCookies();
			service.autoLoginCheck(cookies, sess, arg1);
		}
		
		// 다음 필터 실행
		arg2.doFilter(arg0, arg1);
	}
	
	@Override
	public void destroy() {
	}
}
