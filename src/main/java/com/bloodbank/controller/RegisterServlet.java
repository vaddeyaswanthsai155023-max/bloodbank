package com.bloodbank.controller;

import com.bloodbank.dao.UserDAO;
import com.bloodbank.model.User;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        if (name == null || name.trim().isEmpty() || email == null
                || !email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")
                || password == null || password.length() < 8) {
            req.setAttribute("error", "Enter a valid name, email and a password of 8+ characters.");
            req.getRequestDispatcher("register.jsp").forward(req, res);
            return;
        }
        if (dao.findByEmail(email) != null) {
            req.setAttribute("error", "Email already registered.");
            req.getRequestDispatcher("register.jsp").forward(req, res);
            return;
        }
        User u = new User();
        u.setName(name.trim());
        u.setEmail(email);
        u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        u.setRole("HOSPITAL".equals(role) ? "HOSPITAL" : "DONOR");
        dao.save(u);
        res.sendRedirect("login");
    }
}