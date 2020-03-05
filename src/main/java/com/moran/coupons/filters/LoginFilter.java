package com.moran.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.logic.CacheController;

@Component
public class LoginFilter implements Filter{

	@Autowired
	private CacheController cacheController;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		// Exclude : "Register", "Login" (something with req...)

		String pageRequested = req.getRequestURL().toString();
		if (pageRequested.endsWith("/login")) {
			chain.doFilter(request, response);
			return;
		} 

		if (pageRequested.endsWith("/customers") && req.getMethod().toString().equals("POST")) {
			chain.doFilter(request, response);
			return;
		}


		//for angular
		String token = req.getHeader("Authorization");
		//for talend
//		String token = req.getParameter("token");
		UserAfterLoginData data = (UserAfterLoginData) cacheController.get(token); 
		if (data!=null) {

			req.setAttribute("userLoginData", data);
			// U're logged in - all is good
			// Move forward to the next filter in chain
			chain.doFilter(req, response);
			return;

		}

		HttpServletResponse res = (HttpServletResponse) response;
		// U're not logged in
		res.setStatus(401);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
