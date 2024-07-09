/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.CourseDetailDAO;
import Dal.StarRatingDAO;

import Model.AccountDTO;

import Model.Course;
import Model.StarRatingDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
@WebServlet(name = "StarRatingServelet", urlPatterns = {"/StarRating"})
public class StarRatingServlet extends HttpServlet {

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
            out.println("<title>Servlet StarRatingServelet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StarRatingServelet at " + request.getContextPath() + "</h1>");
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
            String cid = request.getParameter("cid");
            CourseDetailDAO dao = new CourseDetailDAO();
            
            Course course = dao.getCourseById(Integer.parseInt(cid));

            request.setAttribute("course", course);
            request.setAttribute("cid", cid);
            request.getRequestDispatcher("StarRating.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(StarRatingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
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

        HttpSession session = request.getSession();
        String cid = request.getParameter("cid");
        String star = request.getParameter("rating");
        String comment = request.getParameter("comment");

        AccountDTO account = (AccountDTO) session.getAttribute("account");

        StarRatingDAO dao = new StarRatingDAO();

        String msg = "";

   
            try {
                StarRatingDTO rating = new StarRatingDTO(Integer.parseInt(star), comment, Date.valueOf(LocalDate.now()), Integer.parseInt(cid), account.getAccount_id());
//             Chèn dữ liệu vào db
                dao.insertRating(rating);
                response.sendRedirect("CourseDetail?cid="+cid);
            } catch (Exception e) {
                e.printStackTrace();
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

    
    
}
