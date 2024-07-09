/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.ModuleDAO;
import Dal.QuizDAO;
import Model.ModuleDTO;
import Model.Modules;
import Model.Quiz;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author hatro
 */
public class CreateQuizServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateQuizServlet at " + request.getContextPath() + "</h1>");
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
        int midModule = Integer.parseInt(request.getParameter("moduleid"));
        int cidCourse = Integer.parseInt(request.getParameter("cid"));
        ModuleDAO module_dao = new ModuleDAO();
        ModuleDTO this_module = module_dao.FindModuleByModuleId(midModule);
        request.setAttribute("this_module", this_module);
        request.setAttribute("cidCourse", cidCourse);
        request.getRequestDispatcher("create_quiz/cq.jsp").forward(request, response);
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
        int mid = Integer.parseInt(request.getParameter("moduleId"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        
        String quizTitle = request.getParameter("quizTitle");
        String timeNumber = request.getParameter("timeNumber");
        String timeUnit = request.getParameter("timeUnit");
        int quizScore = Integer.parseInt(request.getParameter("quizScore"));
        Time quizTime = null;

        if (timeNumber != null && !timeNumber.isEmpty()) {
            try {
                int timeValue = Integer.parseInt(timeNumber);
                // Tạo 1 đối tượng calendar để thao tác với thời gian
                Calendar calendar = Calendar.getInstance();
                // Đặt lại tất cả các trường để tránh các giá trị không mong muốn
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // Nếu timeUnit có giá trị là hour
                if (timeUnit.equalsIgnoreCase("minutes")) {
                    calendar.set(Calendar.MINUTE, timeValue);
                    // Nếu timeUnit có giá trị là minutes
                } else if (timeUnit.equalsIgnoreCase("seconds")) {
                    calendar.set(Calendar.SECOND, timeValue);
                }

                // Chuyển đổi từ java.util.Date sang java.sql.Time
                quizTime = new Time(calendar.getTimeInMillis());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Quiz quiz = null;
        if (quizTime != null) {
            QuizDAO quizDAO = new QuizDAO();
            quiz = quizDAO.insertQuiz( new Quiz( mid, quizTitle, quizTime, quizScore));
        }
        HttpSession session = request.getSession();
        session.setAttribute("quizId", quiz.getQuizId());
        session.setAttribute("quiz", quiz);
        request.setAttribute("midCreate", mid);
        request.setAttribute("cidCreate", cid);
        request.getRequestDispatcher("create_quiz/cquestions.jsp").forward(request, response);
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
