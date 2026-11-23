package com.bloodbank.controller;

import com.bloodbank.dao.DonorDAO;
import com.bloodbank.exception.DonorNotEligibleException;
import com.bloodbank.model.Donor;
import com.bloodbank.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/donor")
public class DonorServlet extends HttpServlet {
    private final DonorDAO dao = new DonorDAO();
    private static final List<String> GROUPS =
            Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

    private User donorUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(false);
        User u = (s == null) ? null : (User) s.getAttribute("user");
        if (u == null) { res.sendRedirect("login"); return null; }
        if (!"DONOR".equals(u.getRole())) { res.sendRedirect("dashboard.jsp"); return null; }
        return u;
    }

    private void show(HttpServletRequest req, HttpServletResponse res, Donor d)
            throws ServletException, IOException {
        req.setAttribute("groups", GROUPS);
        req.setAttribute("donor", d);
        if (d != null && d.getLastDonationDate() != null) {
            req.setAttribute("nextEligible", d.getLastDonationDate().plusDays(90));
        }
        req.getRequestDispatcher("donor.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User u = donorUser(req, res);
        if (u == null) return;
        show(req, res, dao.findByUserId(u.getId()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User u = donorUser(req, res);
        if (u == null) return;

        Donor d = dao.findByUserId(u.getId());
        if (d == null) {
            d = new Donor();
            d.setUserId(u.getId());
        }
        String group = req.getParameter("bloodGroup");
        String phone = req.getParameter("phone");
        String city = req.getParameter("city");
        try {
            if (group == null || !GROUPS.contains(group)) {
                throw new DonorNotEligibleException("Choose a valid blood group.");
            }
            if (phone == null || !phone.matches("\\d{10}")) {
                throw new DonorNotEligibleException("Phone number must be exactly 10 digits.");
            }
            LocalDate dob;
            try {
                dob = LocalDate.parse(req.getParameter("dob"));
            } catch (DateTimeParseException | NullPointerException e) {
                throw new DonorNotEligibleException("Enter a valid date of birth.");
            }
            if (dob.isAfter(LocalDate.now())) {
                throw new DonorNotEligibleException("Date of birth cannot be in the future.");
            }
            if (Period.between(dob, LocalDate.now()).getYears() < 18) {
                throw new DonorNotEligibleException("Donors must be at least 18 years old.");
            }
            d.setBloodGroup(group);
            d.setPhone(phone);
            d.setCity(city == null ? null : city.trim());
            d.setDob(dob);
            dao.saveOrUpdate(d);
            res.sendRedirect("donor?saved=1");
        } catch (DonorNotEligibleException e) {
            req.setAttribute("error", e.getMessage());
            show(req, res, d);
        }
    }
}