package com.shtabnoy.contact.model.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "PagesFilter")
public class PagesFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(PagesFilter.class);
    private static final String ATTRIBUTE_FOR_ID = "contact";
    private static final String ERROR_PAGE = "path.page.jsp.error";

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if ("/edit".equals(req.getPathInfo())) {
            if (!request.getParameter(ATTRIBUTE_FOR_ID).matches("[0-9]+")) {
                resp.sendRedirect("/main/error");
                return;

            }
        }
        if ("/info".equals(req.getPathInfo())) {
            if (!request.getParameter("page").matches("[0-9]+")) {
                resp.sendRedirect("/main/error");
                return;
            }
            if(request.getParameter("search") != null){
                if (!"true".equals(request.getParameter("search"))){
                resp.sendRedirect("/main/error");
                return;
            }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
