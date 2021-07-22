package org.eu5.learn_pisio.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class ParameterLoggingFilter
 */
//support for async
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class ParameterLoggingFilterAsync implements Filter {

    /**
     * Default constructor. 
     */
    public ParameterLoggingFilterAsync() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// add filter (implementation)
		request.getParameterMap().entrySet().stream().forEach(entry -> {
			System.out.println(String.format("%s:%s", entry.getKey(), entry.getValue()[0]));
		});

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
