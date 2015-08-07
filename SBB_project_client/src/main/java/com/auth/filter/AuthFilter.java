package com.auth.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.cdi.bean.LoginBean;


public class AuthFilter implements Filter {
	@Inject
	private LoginBean loginBean;
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		if (loginBean.getEmail() == null || !loginBean.isAdmin() ) {
		      ((HttpServletResponse) resp).sendRedirect("/SBB_project_client/Login.xhtml");
		    } else {
		      chain.doFilter(req, resp);
		    }
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
