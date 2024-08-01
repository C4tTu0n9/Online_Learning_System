/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.AccountDAO;
import Dal.ProfileManageDAO;

import Model.AccountDTO;
import Model.ProfileDTO;
import Util.MyCommon;
import static Util.SendEmail.sendEmailWithAttachment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
@WebServlet(name = "MentorManageServlet", urlPatterns = {"/mentor-manage"})
@MultipartConfig
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
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "downloadTemplate":
                downloadTemplateExcel(request, response);
                break;
            default:
                showData(request, response);
        }
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
    AccountDAO account_dao = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String mentor_id = request.getParameter("mentor_id") == null ? "" : request.getParameter("mentor_id");
        switch (action) {
            case "delete":
                account_dao.activeOrInactiveAccount(Integer.parseInt(mentor_id), 0);
                break;
            case "add_mentor":
                addMentor(response, request);
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
        if (list_managed_mentor
                == null || list_managed_mentor.isEmpty()) {
            request.setAttribute("list_managed_mentor", new ArrayList<>());
        } else {
            request.setAttribute("list_managed_mentor", list_managed_mentor);
        }

        request.setAttribute(
                "my_role", my_account.getRole_id());
        request.getRequestDispatcher(
                "MentorManage.jsp").forward(request, response);
    }

    private void addMentor(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        AccountDTO my_account = MyCommon.getMyAccount(request, response);
        Part filePart = request.getPart("excelFile");
        String fileName = filePart.getSubmittedFileName();
        String app_pass = request.getParameter("app_pass") == null ? "" : request.getParameter("app_pass");

        // Lưu file tạm thời
        File tempFile = File.createTempFile("temp", fileName);
        try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }

        // Gửi email với file đính kèm
        sendEmailWithAttachment(tempFile, fileName, my_account.getEmail(), app_pass);

        // Xóa file tạm
        tempFile.delete();

        request.setAttribute("message", "File submitted successfully!");
    }

    private void downloadTemplateExcel(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        String filePath = "/Excel_mentor_template/Mentor_Register_Template.xlsx";
        File downloadFile = new File(getServletContext().getRealPath(filePath));
        FileInputStream inStream = new FileInputStream(downloadFile);

        // Cấu hình response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=register_mentor_template.xlsx");

        // Ghi file vào OutputStream
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }
}
