/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import Dal.AccountDAO;
import Model.AccountDTO;
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
 * @author Tuan Anh(Gia Truong)
 */
@WebServlet(name = "ManagerAccountServlet", urlPatterns = {"/dasboard_for_admin/managerAccount"})
public class ManagerAccountServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAccountServlet at " + request.getContextPath() + "</h1>");
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
        //response.getWriter().println("Mentor accounts imported and activated successfully.");
        HttpSession session = request.getSession();
        String accountId = request.getParameter("accountid");
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));
        AccountDAO accDao = new AccountDAO();
        PrintWriter o = response.getWriter();
        AccountDTO acc = (AccountDTO) session.getAttribute("account");
        if (acc == null || (acc.getRole_id() != 1 && acc.getRole_id() != 2)) {
            response.sendRedirect("../home");
            return;
        }

        try {

            switch (action) {
                case "addAccount":
                    request.getRequestDispatcher("add_account.jsp").forward(request, response);
                    break;
                case "updateAccount":
                    AccountDTO account = accDao.getAccountById(Integer.parseInt(accountId));

                    request.setAttribute("accid", accountId);
                    request.setAttribute("account", account);
                    request.getRequestDispatcher("update_account.jsp").forward(request, response);
                    break;
                default:
//                    read
                    if (acc.getRole_id() == 1) {
                        ArrayList<AccountDTO> listAllAccount = accDao.getManagerAccount();
                        //response.getWriter().print(listAllAccount);
                        request.setAttribute("listAllAccount", listAllAccount);
                        request.getRequestDispatcher("manageAccount.jsp").forward(request, response);
                        o.print(acc.getAccount_id());
                    } else {
                        ArrayList<AccountDTO> listAllAccount = accDao.getMyMentorAccounts(acc.getAccount_id());
                        request.setAttribute("listAllAccount", listAllAccount);
                        request.getRequestDispatcher("MentorAccount.jsp").forward(request, response);
                        o.print(acc.getAccount_id());
                    }
            }

        } catch (Exception e) {
        }
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
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));

        switch (action) {
            case "addAccount":
                addNewAccountByAdmin(request, response);
                break;
            case "updateAccount":
                updateAccountByAdmin(request, response);
                break;
            case "statusAccount":
                statusAccount(request, response);
                break;
            default:
                throw new AssertionError();
//                response.getWriter().print("Lỗi rồi,không nhận được action phù hợp");
        }
    }

    //thêm tài khoản mới
    private void addNewAccountByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDao = new AccountDAO();
        HttpSession session = request.getSession();
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String cfpass = request.getParameter("cfpass");
        String gender_str = request.getParameter("gender");
        String role = request.getParameter("role");
        //String avatar = request.getParameter("");

        AccountDTO accSession = (AccountDTO) session.getAttribute("account");

        String msg = "";
        boolean gender = false;
        if (gender_str.equals("Male")) {
            gender = true;
        }

        if (!pass.equals(cfpass)) {
            msg = "Password must equal repassword";
        } else if (pass.length() < 8) {
            msg = "The length password must be longer 8 character";
        } else if (accDao.checkAccountExist(email)) {
            msg = "Account was exist";
        } else {
            try {

                AccountDTO account = new AccountDTO(email, pass, Integer.parseInt(role));

                ProfileDTO profile = new ProfileDTO(fullname, gender, 0, accSession.getAccount_id());
                accDao.insertUserByAdmin(account, profile);
                response.sendRedirect("managerAccount");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("pass", pass);
        request.setAttribute("cfpass", cfpass);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);

        request.getRequestDispatcher("add_account.jsp").forward(request, response);
    }

    //Update tài khoản bỏi admin
    private void updateAccountByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDao = new AccountDAO();

        HttpSession session = request.getSession();
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String gender_str = request.getParameter("gender");
        String role = request.getParameter("role");
        String profiId = request.getParameter("accid");

        String msg = "";
        boolean gender = false;
        if (gender_str.equals("Male")) {
            gender = true;
        }

        if (pass.length() < 8) {
            msg = "The length password must be longer 8 character";
        } else {
            try {
                AccountDTO account = new AccountDTO(email, pass, Integer.parseInt(role));

                ProfileDTO profile = new ProfileDTO(Integer.parseInt(profiId), fullname, gender);
                accDao.updateAccount(account, profile);
                response.sendRedirect("managerAccount");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("pass", pass);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);
        request.setAttribute("accid", profiId);

        request.getRequestDispatcher("update_account.jsp").forward(request, response);

    }

    private void statusAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountid = request.getParameter("accid");
        AccountDAO accDao = new AccountDAO();

        try {
            if (accDao.CheckActiveOrInActive(Integer.parseInt(accountid))) {
                accDao.activeOrInactiveAccount(Integer.parseInt(accountid), 0);
            } else {
                accDao.activeOrInactiveAccount(Integer.parseInt(accountid), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("managerAccount");

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

}
