/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.QuizDAO;
import Model.Answer;
import Model.Modules;
import Model.Questions;
import Model.Quiz;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author hatro
 */
public class EditQuizServlet extends HttpServlet {

            QuizDAO quizDAO = new QuizDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Quiz quizEdit = quizDAO.GetQuizByQuizId(quizId);
        Modules moduleOfQuizEdit = quizDAO.GetModulebyQuizId(quizId);
        // lấy ra thời gian cho vào edit quiz
        String time_quiz = quizEdit.getQuizTime().toString();
        String[] time_quiz_to_array = time_quiz.split(":");
        request.setAttribute("minutes", Integer.valueOf(time_quiz_to_array[1]));
        request.setAttribute("seconds", Integer.valueOf(time_quiz_to_array[2]));
        
        // cho câu hỏi và câu trả lời vào editquiz
        
        ArrayList<Questions> listQuestions = quizDAO.getListQuestionsByQuizId(quizId);
        ArrayList<Answer> listAnswers = quizDAO.getListAnswers();
        request.setAttribute("listQuestions", listQuestions);
        request.setAttribute("listAnswers", listAnswers);
        request.setAttribute("quizEdit", quizEdit);
        request.setAttribute("moduleOfQuizEdit", moduleOfQuizEdit);
        request.getRequestDispatcher("edit_quiz/editquiz.jsp").forward(request, response);
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
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int cid = Integer.parseInt(request.getParameter("cid1"));
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
        
        if (quizTime != null) {
            QuizDAO quizDAO = new QuizDAO();
            quizDAO.editQuizByQuizId( new Quiz( quizId,mid, quizTitle, quizTime, quizScore));
        }
        
        //=============================================THỰC HIỆN CHUYỂN HƯỚNG Ở ĐÂY======================================================
        response.sendRedirect("ModuleManage?moduleId=" + mid + "&cid=" + cid );

    }


}
