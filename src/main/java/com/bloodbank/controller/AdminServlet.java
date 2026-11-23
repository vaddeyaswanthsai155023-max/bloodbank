package com.bloodbank.controller;

import com.bloodbank.dao.StockDAO;
import com.bloodbank.exception.DonorNotEligibleException;
import com.bloodbank.model.Donor;
import com.bloodbank.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final StockDAO dao = new StockDAO();

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(false);
        User u = (s == null) ? null : (User) s.getAttribute("user");
        if (u == null) { res.sendRedirect("login"); return false; }
        if (!"ADMIN".equals(u.getRole())) { res.sendRedirect("dashboard.jsp"); return false; }
        return true;
    }

    private void show(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("donors", dao.allDonors());
        req.setAttribute("summary", dao.stockSummary());
        req.getRequestDispatcher("admin.jsp").forward(req, res);
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
            int donorId;
            int units;
            try {
                donorId = Integer.parseInt(req.getParameter("donorId"));
                units = Integer.parseInt(req.getParameter("units"));
            } catch (NumberFormatException e) {
                throw new DonorNotEligibleException("Choose a donor and enter valid units.");
            }
            if (units < 1 || units > 2) {
                throw new DonorNotEligibleException("Units must be 1 or 2.");
            }
            Donor d = dao.findDonor(donorId);
            if (d == null) {
                throw new DonorNotEligibleException("Donor not found.");
            }
            LocalDate today = LocalDate.now();
            if (d.getLastDonationDate() != null) {
                long gap = ChronoUnit.DAYS.between(d.getLastDonationDate(), today);
                if (gap < 90) {
                    throw new DonorNotEligibleException("Donor must wait 90 days between donations. Next eligible date: "
                            + d.getLastDonationDate().plusDays(90));
                }
            }
            dao.recordDonation(d, units, today);
            res.sendRedirect("admin?saved=1");
        } catch (DonorNotEligibleException e) {
            req.setAttribute("error", e.getMessage());
            show(req, res);
        }
    }
}