/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User;

import Controller.CourseDetailServlet;
import Dal.AccountDAO;
import Dal.HomeDAO;
import Dal.ProfileManageDAO;

import Model.AccountDTO;
import Model.Category;
import Model.ProfileDTO;
import Util.MyCommon;
import Util.Validation;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuong
 */
@MultipartConfig
public class profileServlet extends HttpServlet {
    ProfileManageDAO profile_dao = new ProfileManageDAO();
    AccountDAO accountDAO = new AccountDAO();
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
            out.println("<title>Servlet profileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet profileServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        MyCommon.getHeader(request, response);
        switch (action) {
            case "general" -> {
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
                return;
            }
            case "change_password" -> {
                request.getRequestDispatcher("ProfileChangePassword.jsp").forward(request, response);
                return;
            }
            default ->
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
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
        PrintWriter o = response.getWriter();
//get action
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");


        switch (action) {
            case "general":
                changeInformationProfile(request, response);
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
                return;
            case "change_password":
                changePassword(request, response);
                request.getRequestDispatcher("ProfileChangePassword.jsp").forward(request, response);
                return;
            default:
                throw new AssertionError();

        }
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
// </editor-fold>

    private void changeInformationProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
//start get account data from cookie account_id
        AccountDTO my_account = MyCommon.getMyAccount(request, response);
        ProfileDTO my_profile = accountDAO.getProfile(my_account);
//end get account data from
//data access object

//start get data from Profile.jsp 
        Part file_avt = request.getPart("avt");
        String fullname = request.getParameter("fullname") == null ? "" : request.getParameter("fullname").trim();
        //String email = request.getParameter("email") == null ? "" : request.getParameter("email");
        String gender = request.getParameter("gender") == null ? "" : request.getParameter("gender");
        boolean bool_gender = gender.equals("male");
//end get data from Profile.jsp


//update avatar
        if (file_avt != null && file_avt.getSize() > 0) {
            String avt_file_name = Validation.inputFile(request, file_avt, "avatar_images");
            my_profile.setAvt(avt_file_name);
            accountDAO.updateAvatar_ByAccId(avt_file_name, my_account.getAccount_id());
            request.setAttribute("success_avatar", "Avatar is changed successfully!");
        }
//update name        
        if (Validation.checkName(fullname)) {
            fullname = (Validation.validName(fullname));
            //update db profile name if changed
            if (!fullname.equals(my_profile.getFullname())) {
                accountDAO.updateFullName_ByAccId(fullname, my_account.getAccount_id());
                my_profile.setFullname(fullname);
                request.setAttribute("success_name", "Name is changed successfully!");
            } else {
                request.setAttribute("info_name", "Name remains unchanged.");
            }
        } else {
            request.setAttribute("error_name", "Name is not valid!");
        }
//update gender  
        if (!gender.equals("")) {
            //update gender vao db profile if changed
            if (!gender.equalsIgnoreCase(my_profile.isGender() ? "male" : "female")) {
                AccountDTO account = (AccountDTO) session.getAttribute("account");
                accountDAO.updateGender_ByAccId(bool_gender, account.getAccount_id());
                my_profile.setGender(bool_gender);
                request.setAttribute("success_gender", "Gender is changed successfully!");
            }
        }
        session.setAttribute("profile", my_profile);
        session.setAttribute("account", my_account);
        session.setMaxInactiveInterval(60 * 30);
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
//start get account data from session
        AccountDTO my_account = MyCommon.getMyAccount(request, response);
        ProfileDTO my_profile = accountDAO.getProfile(my_account);
//end get account data from
//data access object

        //start get data from Profile.jsp 
        String my_password = my_account.getPassword();
        String old_password = request.getParameter("old_password") == null ? "" : request.getParameter("old_password");
        String new_password = request.getParameter("new_password") == null ? "" : request.getParameter("new_password");
        String re_new_password = request.getParameter("re_new_password") == null ? "" : request.getParameter("re_new_password");
//end get data from Profile.jsp
        String[] fullFields = {old_password, new_password, re_new_password};

        if (Validation.checkStringArray(fullFields)) {
            if (!my_password.equals(old_password)) {
                request.setAttribute("error_current_pass", "You inputed wrong old password!");
            } else {
                if (!new_password.equals(re_new_password)) {
                    request.setAttribute("error_match_new_pass", "New passwords are not match!");
                } else {
                    if (new_password.length() < 8) {
                        //request.setAttribute("password", password);
                        request.setAttribute("error_length_new_pass", "Password must be at least 8 characters!");
                    } else {
                        //update password
                        accountDAO.updatePassword_ByAccId(new_password, my_account.getAccount_id());
                        my_account.setPassword(new_password);
                        //add cookies
                        Cookie password_remember = new Cookie("password", new_password);
                        password_remember.setMaxAge(60 * 60 * 24);
                        response.addCookie(password_remember);
                        request.setAttribute("success_password", "Password is changed successfully!");
                    }
                }
            }
        }
        else{
            request.setAttribute("error_full_fields", "If you want to change your password, please fill in all fields!");
        }
        session.setAttribute("profile", my_profile);
        session.setAttribute("account", my_account);
        session.setMaxInactiveInterval(60 * 30);

    }
}
