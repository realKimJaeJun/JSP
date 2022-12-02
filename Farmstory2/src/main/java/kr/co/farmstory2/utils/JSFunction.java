package kr.co.farmstory2.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter; // 필요한 클래스 임포트

public class JSFunction {
	
	// 메시지 알림창을 띄운 후 명시한 URL로 이동한다.
	public static void alertLocation(HttpServletResponse resp, String msg, String url) {
		
		try {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer = resp.getWriter();
			
 			String script = "" // 삽입할 자바스크립트 코드
					+ "<script>"
					+ "	alert('" + msg + "');"
					+ " 	location.href='" + url + "';"
					+ "</script>";
			writer.print(script);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 메시지 알림창을 띄운 후 이전 페이지로 돌아갑니다.
	public static void alertBack(HttpServletResponse resp, String msg) {
		try {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer = resp.getWriter(); 
			String script = "" 
					+ "<script>"
					+ "	alert('" + msg + "');"
					+ " 	history.back();"
					+ "</script>";
			writer.print(script);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
}