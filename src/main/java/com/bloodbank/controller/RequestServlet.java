package com.bloodbank.controller;

import com.bloodbank.dao.RequestDAO;
import com.bloodbank.model.BloodRequest;
import com.bloodbank.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
    private final RequestDAO dao = new RequestDAO();
    private static final List<String> GROUPS =
            Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

    private User hospital(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(false);
        User u = (s == null) ? null : (User) s.getAttribute("user");
        if (u == null) { res.sendRedirect("login"); return null; }
        if (!"HOSPITAL".equals(u.getRole())) { res.sendRedirect("dashboard.jsp"); return null; }
        return u;
    }

    private void show(HttpServletRequest req, HttpServletResponse res, User u)
            throws ServletException, IOException {
        req.setAttribute("groups", GROUPS);
        req.setAttribute("requests", dao.byHospital(u.getId()));
        req.getRequestDispatcher("request.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User u = hospital(req, res);
        if (u == null) return;
        show(req, res, u);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User u = hospital(req, res);
        if (u == null) return;
        String group = req.getParameter("bloodGroup");
        String urgency = "URGENT".equals(req.getParameter("urgency")) ? "URGENT" : "NORMAL";
        int units;
        try {
            units = Integer.parseInt(req.getParameter("units"));
        } catch (NumberFormatException e) {
            units = 0;
        }
        if (group == null || !GROUPS.contains(group) || units < 1 || units > 20) {
            req.setAttribute("error", "Choose a blood group and units between 1 and 20.");
            show(req, res, u);
            return;
        }
        BloodRequest r = new BloodRequest();
        r.setHospitalId(u.getId());
        r.setBloodGroup(group);
        r.setUnits(units);
        r.setUrgency(urgency);
        r.setStatus("PENDING");
        dao.save(r);
        res.sendRedirect("request?saved=1");
    }
}