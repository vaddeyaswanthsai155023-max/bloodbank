package com.bloodbank.filter;

import com.bloodbank.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String ctx = req.getContextPath();
        String path = req.getRequestURI().substring(ctx.length());

        // Pages anyone may open
                boolean isPublic = path.equals("/") || path.equals("/index.html")
                || path.equals("/login") || path.equals("/login.jsp")
                || path.equals("/register") || path.equals("/register.jsp")
                || path.equals("/logout") || path.equals("/error.jsp")
                || path.startsWith("/css/");
        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        // Everything else needs a logged-in user
        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");
        if (user == null) {
            res.sendRedirect(ctx + "/login");
            return;
        }

        // Role-based access
        String role = user.getRole();
        boolean allowed = true;
        if (path.startsWith("/admin") || path.startsWith("/approvals")) {
            allowed = "ADMIN".equals(role);
        } else if (path.startsWith("/donor")) {
            allowed = "DONOR".equals(role);
        } else if (path.startsWith("/request")) {
            allowed = "HOSPITAL".equals(role);
        }
        if (!allowed) {
            res.sendRedirect(ctx + "/dashboard.jsp");
            return;
        }

        // Stop the browser's Back button from showing pages after logout
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        chain.doFilter(request, response);
    }
}