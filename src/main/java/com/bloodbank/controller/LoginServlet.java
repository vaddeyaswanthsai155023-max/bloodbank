package com.bloodbank.controller;

import com.bloodbank.dao.UserDAO;
import com.bloodbank.model.User;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User u = dao.findByEmail(req.getParameter("email"));
        String pw = req.getParameter("password");
        if (u != null && pw != null && BCrypt.checkpw(pw, u.getPasswordHash())) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            session.setMaxInactiveInterval(15 * 60);
            res.sendRedirect("dashboard.jsp");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }
}