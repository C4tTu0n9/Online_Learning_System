/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Home;

import Dal.LessonDAO;
import Model.AccountDTO;
import Model.Enrollment;
import Model.TeachingDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
@WebServlet(name = "dataTransferLessonServlet", urlPatterns = {"/dataTransferLesson"})
public class dataTransferLessonServlet extends HttpServlet {

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
            out.println("<title>Servlet dataTransferLessonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet dataTransferLessonServlet at " + request.getContextPath() + "</h1>");
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

        try {
            HttpSession session = request.getSession();
            LessonDAO dao = new LessonDAO();
            String lessonid = request.getParameter("lessonid");
            String courseid = request.getParameter("cid");
            String createby = request.getParameter("createBy");
            AccountDTO acc = (AccountDTO) session.getAttribute("account");
            String price = request.getParameter("price");
            String ndck = request.getParameter("ndck");
            ArrayList<Enrollment> listEnrollment = dao.getEnrollmentByAccountId(acc.getAccount_id());
            String lastLessonId = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastLessonId_" + courseid)) {
                        lastLessonId = cookie.getValue();
                        break;
                    }
                }
            }

            if (lastLessonId == null) {
                // Nếu không có cookie, bạn có thể đặt giá trị mặc định, ví dụ: bài học đầu tiên
                //String lessonid = lessonidObj.toString();
                lastLessonId = lessonid;
            }

            
//            nếu tài khaonr này là mentor của khóa học 
            if (checkMentorInLesson(acc.getAccount_id(), Integer.parseInt(courseid), dao)) {
                response.sendRedirect("lesson?cid=" + courseid + "&lessonid=" + lastLessonId + "&createBy=" + createby);
                response.getWriter().print("m là mentor");

            } else if (isPaid(Integer.parseInt(courseid), listEnrollment)) {
                response.sendRedirect("lesson?cid=" + courseid + "&lessonid=" + lastLessonId + "&createBy=" + createby);
            }else {
                response.sendRedirect("vnpay_pay.jsp?price="+ price + "&cid="+Integer.parseInt(courseid) +"&acc=" + acc.getAccount_id() +"&ndck="+ndck+"chuyen khoan");
            }

            //response.sendRedirect("lesson?cid="+ courseid +"&lessonid="+ lastLessonId +"&createBy="+createby);
        } catch (SQLException ex) {
            Logger.getLogger(dataTransferLessonServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    //    kiểm tra xem có phải mentor dạy khóa học này hay không
    private boolean checkMentorInLesson(int accountid, int courseId, LessonDAO dao) throws SQLException {
        ArrayList<TeachingDTO> listmentor = dao.getListMentorByCid(courseId);
        for (TeachingDTO teachingDTO : listmentor) {
            if (teachingDTO.getMentorid() == accountid) {
                return true;
            }
        }

        return false;
    }

    private boolean isPaid(int cid, ArrayList<Enrollment> enrollmentList) {
        for (Enrollment e : enrollmentList) {
            if (cid == e.getCourseid()) {
                return true;
            }
        }

        return false;
    }

}
