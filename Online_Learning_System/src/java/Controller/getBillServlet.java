/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;
import Dal.LessonDAO;
import Model.Enrollment;
import Model.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
@WebServlet(name = "getBillServlet", urlPatterns = {"/bill"})
public class getBillServlet extends HttpServlet {

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
            out.println("<title>Servlet getBillServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet getBillServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String accId = request.getParameter("accId");
        String coureId = request.getParameter("courseId");
        String date = request.getParameter("date");
        String money = request.getParameter("money");
        String ndtt = request.getParameter("ndck");
        
        
        String err = "";
        if(accId.isBlank() || coureId.isBlank() || date.isBlank() || money.isBlank() || ndtt.isBlank()) {
            err = "Loi roi! Chua Nhap gi dung khong";
        } else {
            
            try {
                String dateFormat = formaDate(date);
                Payment payment = new Payment(Integer.parseInt(accId), Integer.parseInt(coureId), Date.valueOf(dateFormat), "VNPAY",Integer.parseInt(money) / 100);
                Enrollment enrollment = new Enrollment(Integer.parseInt(accId), Integer.parseInt(coureId), Date.valueOf(dateFormat), 0);
                LessonDAO dao = new LessonDAO();
                dao.insertBillPayment(payment);
                dao.insertEnrollment(enrollment);
                long lessonid = dao.getLessonIdByCourseId(Integer.parseInt(coureId));

                long createBY = dao.getCreateByByCourseId(Integer.parseInt(coureId));
                response.sendRedirect("lesson?cid="+coureId+"&lessonid="+lessonid+"&createBy="+createBY);

            } catch (SQLException ex) {
                Logger.getLogger(getBillServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
            }
        }

        
        

    }
    
    private String formaDate(String Date) {
        String year = "";
        String month = "";
        String day = "";
        String hour = "";
        String munite = "";
        String second = "";


        for(int i = 0; i < Date.length(); i++) {
            if(i < 4) {
                year += Date.charAt(i);
            } else if(i < 6) {
                month +=Date.charAt(i);
            } else if(i < 8) {
                day += Date.charAt(i);
            } else if(i < 10) {
                hour += Date.charAt(i);
            } else if(i < 12) {
                munite += Date.charAt(i);
            } else {
                second += Date.charAt(i);
            }
        }
        return year +"-"+month+"-"+day;
//        +" "+hour+":"+munite+":"+second
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
