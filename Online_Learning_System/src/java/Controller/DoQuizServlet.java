/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.QuizDAO;
import Model.AccountDTO;
import Model.AccountDTO;
import Model.UserAnswer;
import Model.Answer;
import Model.Questions;
import Model.Quiz;
import Model.ScoreQuiz;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hatro
 */
public class DoQuizServlet extends HttpServlet {

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
            out.println("<title>Servlet DoQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoQuizServlet at " + request.getContextPath() + "</h1>");
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
        QuizDAO quizDAO = new QuizDAO();
        HttpSession session = request.getSession();
        AccountDTO acc = (AccountDTO) session.getAttribute("account");
        int moduleId = Integer.parseInt(request.getParameter("mid"));
        Quiz quiz = quizDAO.findQuizByModuleId(moduleId);
        if (acc == null) {
            response.sendRedirect("join?action=login");
            return;
        } else {
//            ScoreQuiz scoreQuiz = quizDAO.findScoreDoQuizByAccountIdAndQuizId(acc.getAccount_id(), quiz.getQuizId());
//            if (scoreQuiz != null) {
//                response.sendRedirect("doquizsub?mid=" + moduleId);
//                return;
//            } else {
                ArrayList<Questions> listQuestionByModuleId = quizDAO.getListQuestionsByModuleId(moduleId);
                ArrayList<Answer> listAnswerByModuleId = quizDAO.getlistAnswerByModuleId(moduleId);
                request.setAttribute("quizDoQuiz", quiz);
                request.setAttribute("listQuestionsByMId", listQuestionByModuleId);
                request.setAttribute("listAnswerByMId", listAnswerByModuleId);
                request.getRequestDispatcher("do_quiz/do_quiz.jsp").forward(request, response);
//            }
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
        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        int moduleId = Integer.parseInt(request.getParameter("mid"));
        
        QuizDAO quizDAO = new QuizDAO();
        Quiz quiz = quizDAO.findQuizByModuleId(moduleId);
        ArrayList<Questions> listQuestionByModuleId = quizDAO.getListQuestionsByModuleId(moduleId);
        //LIST CAU DUNG
        ArrayList<Answer> listAnswerCorrectByModuleId = quizDAO.getlistAnswerCorrectByModuleId(moduleId);

        // Tạo một HashMap để lưu trữ questionId và các câu trả lời của người dùng
        Map<String, String[]> questionAnswersMap = new HashMap<>();
        Map<String, String[]> questionAnswersCorrect = quizDAO.getListQuestionAnswer(moduleId);

        // Lấy tất cả các tên tham số
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String questionId = parameterNames.nextElement();
            if (!questionId.equals("mid")) {
                String[] answers = request.getParameterValues(questionId);
                questionAnswersMap.put(questionId, answers);
            }
        }

        ArrayList<UserAnswer> listUserAnswer = new ArrayList<>();
        int score = 0;
        int attemptNumber = quizDAO.updateAttemptNumber(acc.getAccount_id(), quiz.getQuizId());
        // Process the user's answers and compare them with the correct answers
        for (Map.Entry<String, String[]> entry : questionAnswersMap.entrySet()) {
            int questionId = Integer.parseInt(entry.getKey());
            String[] userAnswers = entry.getValue();
            String[] correctAnswers = questionAnswersCorrect.get(entry.getKey());

            Set<String> userAnswersSet = new HashSet<>(Arrays.asList(userAnswers));
            Set<String> correctAnswersSet = new HashSet<>(Arrays.asList(correctAnswers));
            if (userAnswersSet.equals(correctAnswersSet)) {
                score++;
            }
            // Add the user's answers to the list
            for (String userAnswer : userAnswers) {
                boolean isCorrectUserAnswer = isAnswerCorrect(userAnswer, correctAnswers);
                listUserAnswer.add(new UserAnswer(acc.getAccount_id(), questionId, userAnswer, isCorrectUserAnswer, attemptNumber));
            }
        }

//      Insert danh sách đáp án của người dùng vào database
        quizDAO.insertUserAnser(listUserAnswer);
        float totalScore = (float) score / listQuestionByModuleId.size() * 10;
        
        
        ScoreQuiz scorequiz = new ScoreQuiz(acc.getAccount_id(), quiz.getQuizId(), totalScore);

        quizDAO.insertcoreQuiz(scorequiz);

        
//         Chuyển hướng sau khi xử lý
        response.sendRedirect("doquizsub?mid=" + moduleId );
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

    private boolean isAnswerCorrect(String userAnswer, String[] correctAnswers) {
        if (userAnswer == null || correctAnswers == null) {
            return false;
        }

        for (String correctAnswer : correctAnswers) {
            if (userAnswer.equals(correctAnswer)) {
                return true;
            }
        }

        return false;
    }

}
