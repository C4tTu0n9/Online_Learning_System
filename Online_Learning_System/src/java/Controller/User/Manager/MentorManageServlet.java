/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.AccountDAO;
import Dal.CourseManageDAO;
import Dal.ProfileManageDAO;

import Model.AccountDTO;
import Model.CourseManageDTO;
import Model.ProfileDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
@WebServlet(name = "MentorManageServlet", urlPatterns = {"/mentor-manage"})
public class MentorManageServlet extends HttpServlet {

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
            out.println("<title>Servlet MentorManageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MentorManageServlet at " + request.getContextPath() + "</h1>");
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
        showData(request, response);
    }

//    public static void main(String[] args) {
//        ProfileManageDAO mentor_manage_DAO = new ProfileManageDAO();
//        ArrayList<ProfileDTO> list_managed_mentor = mentor_manage_DAO.getMyListManagedMentorByCouresId(2);
//        System.out.println(list_managed_mentor.get(0).getFullname());
//    }
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
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String mentor_id = request.getParameter("mentor_id") == null ? "" : request.getParameter("mentor_id");
        switch (action) {
            case "delete":
                AccountDAO account_dao = new AccountDAO();
                account_dao.activeOrInactiveAccount(Integer.parseInt(mentor_id), 0);
                break;
            default:

        }
        showData(request, response);
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

    private void showData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrintWriter o = response.getWriter();

        AccountDTO my_account = (AccountDTO) session.getAttribute("account");

        ProfileManageDAO mentor_manage_DAO = new ProfileManageDAO();
        ArrayList<ProfileDTO> list_managed_mentor = mentor_manage_DAO.getMyListManagedMentor(my_account.getAccount_id());
        if (list_managed_mentor == null || list_managed_mentor.isEmpty()) {
            request.setAttribute("list_managed_mentor", new ArrayList<>());
        } else {
            request.setAttribute("list_managed_mentor", list_managed_mentor);
        }
        request.setAttribute("my_role", my_account.getRole_id());
        request.getRequestDispatcher("MentorManage.jsp").forward(request, response);
    }

}
