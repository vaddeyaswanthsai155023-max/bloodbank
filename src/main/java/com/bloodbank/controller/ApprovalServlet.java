package com.bloodbank.controller;

import com.bloodbank.dao.RequestDAO;
import com.bloodbank.exception.InsufficientStockException;
import com.bloodbank.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/approvals")
public class ApprovalServlet extends HttpServlet {
    private final RequestDAO dao = new RequestDAO();

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(false);
        User u = (s == null) ? null : (User) s.getAttribute("user");
        if (u == null) { res.sendRedirect("login"); return false; }
        if (!"ADMIN".equals(u.getRole())) { res.sendRedirect("dashboard.jsp"); return false; }
        return true;
    }

    private void show(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("requests", dao.all());
        req.getRequestDispatcher("approvals.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!isAdmin(req, res)) return;
        show(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!isAdmin(req, res)) return;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if ("approve".equals(req.getParameter("action"))) {
                dao.approve(id);
            } else {
                dao.reject(id);
            }
            res.sendRedirect("approvals");
        } catch (InsufficientStockException e) {
            req.setAttribute("error", e.getMessage());
            show(req, res);
        } catch (NumberFormatException e) {
            res.sendRedirect("approvals");
        }
    }
}