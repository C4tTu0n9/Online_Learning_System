/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import Dal.StatisticalDAO;

import Model.AccountDTO;

import Model.Category;
import Model.Course;
import Model.Payment;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "StatisticalSeverlet", urlPatterns = {"/dasboard_for_admin/StatisticalSeverlet"})
public class StatisticalSeverlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StatisticalSeverlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticalSeverlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        StatisticalDAO admin_manage_DAO = new StatisticalDAO();
        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        if (acc == null || acc.getRole_id() != 1) {
            response.sendRedirect("../home");
            return;
        }

        try {
            Payment TotalPerMonth = admin_manage_DAO.getPaymentPerMonth();
            Payment TotalPerYear = admin_manage_DAO.getPaymentPerYear();
            TotalPerMonth.setFormattedPrice(formartPrice(TotalPerMonth.getAmount()));
            TotalPerYear.setFormattedPrice(formartPrice(TotalPerYear.getAmount()));
            AccountDTO CountAccStilActive = admin_manage_DAO.CountAccStillActive();
            Course CountCourseStilActive = admin_manage_DAO.CountCourseStillActive();
            ArrayList<Payment> TotalEarningPerMonthChart = admin_manage_DAO.getTotalEarningPerMonth();
            ArrayList<Category> PercentCategory = admin_manage_DAO.getPercentCategory();

            request.setAttribute("TotalPerMonth", TotalPerMonth);
            request.setAttribute("TotalPerYear", TotalPerYear);
            request.setAttribute("CountAccStilActive", CountAccStilActive);
            request.setAttribute("CountCourseStilActive", CountCourseStilActive);
            request.setAttribute("TotalEarningPerMonth", TotalEarningPerMonthChart);
            request.setAttribute("PercentCategory", PercentCategory);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalSeverlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("dasboard_home.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String formartPrice(int price) {
        NumberFormat formatTer = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatTer.format(price);
    }
}
