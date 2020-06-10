package com.ouver.o2o.interception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ouver.o2o.domain.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 对没登录的用户进行拦截
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			PersonInfo user = (PersonInfo) userObj;
			if (user != null && user.getUserId() != null
					&& user.getUserId() > 0 && user.getEnableStatus() == 1
					&& user.getShopOwnerFlag() == 1)
				return true;
		}
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath()
				+ "/user/loginByShop','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}
}
