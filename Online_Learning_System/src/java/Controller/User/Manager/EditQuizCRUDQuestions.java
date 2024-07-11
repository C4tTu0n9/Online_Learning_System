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
import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class EditQuizCRUDQuestions extends HttpServlet {

    QuizDAO quizDAO = new QuizDAO();

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
            out.println("<title>Servlet EditQuizCRUDQuestions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditQuizCRUDQuestions at " + request.getContextPath() + "</h1>");
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
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Quiz quizEdit = quizDAO.GetQuizByQuizId(quizId);
        Modules moduleOfQuizEdit = quizDAO.GetModulebyQuizId(quizId);
        ArrayList<Questions> listQuestions = quizDAO.getListQuestionsByQuizId(quizId);
        ArrayList<Answer> listAnswers = quizDAO.getListAnswers();
        request.setAttribute("quizEdit", quizEdit);
        request.setAttribute("listQuestions", listQuestions);
        request.setAttribute("listAnswers", listAnswers);
        request.setAttribute("moduleOfQuizEdit", moduleOfQuizEdit);
        request.getRequestDispatcher("edit_quiz/editquestion.jsp").forward(request, response);
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
        // lấy ra quiz id để trả về đúng trang edit quiz cần đến
//        courseId 
//        moduleId
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Modules module = quizDAO.getCourseIdAndModuleIdByQuizId(quizId);
        //get action
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "create":
                createQuestion(request);
                //response.sendRedirect("controller");
                break;
            case "delete":
                deleteQuestion(request, response);
                break;
            case "edit":
                editQuestion(request, response);
            default:
        }
//        response.sendRedirect("editquiz?action=edit&cid=" + module.getCourseid() + 
//                "&moduleId=" + module.getModuleid() + "&quizId=" + quizId);

          response.sendRedirect("editquizcrudquestion?quizId="+ quizId);
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

    private void createQuestion(HttpServletRequest request) {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        String questionNumber_str = request.getParameter("questionNumber");
        String titleQuestion = request.getParameter("titleQuestion");
        String typeQuestion_str = request.getParameter("typeQuestion");
        String[] choices = request.getParameterValues("answer");

        int questionNumber = Integer.parseInt(questionNumber_str);
        boolean typeQuestion = false;
        typeQuestion = typeQuestion_str.equalsIgnoreCase("radioBox");

        Questions questions = quizDAO.insertQuestions(new Questions(questionNumber, quizId, titleQuestion, typeQuestion));

        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= choices.length; i++) {
            boolean correctAnswer = request.getParameter("correctAnswer" + i) == null ? false : true;
            answers.add(new Answer(questions.getQuestionId(), choices[i - 1], correctAnswer));
        }
        quizDAO.insertAnswers(answers);
        quizDAO.updateTypeQuestion(questions);

    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int questionId = Integer.parseInt(request.getParameter("id"));
        quizDAO.deleteQuestionDoQuizById(questionId);
    }

    private void editQuestion(HttpServletRequest request, HttpServletResponse response) {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int idQuestionEdit = Integer.parseInt(request.getParameter("id"));
        String questionNumber_str = request.getParameter("number");
        String titleQuestion = request.getParameter("title");
        String typeQuestion_str = request.getParameter("typeQuestion");
        String[] choices = request.getParameterValues("answer");

        int questionNumber = Integer.parseInt(questionNumber_str);
        boolean typeQuestion = false;
        typeQuestion = typeQuestion_str.equalsIgnoreCase("radioBox");

        Questions questions = quizDAO.editQuestionsById(new Questions(questionNumber, quizId, titleQuestion, typeQuestion), idQuestionEdit);

        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= choices.length; i++) {
            boolean correctAnswer = request.getParameter("correctAnswer" + i) == null ? false : true;
            answers.add(new Answer(idQuestionEdit, choices[i - 1], correctAnswer));
        }
        quizDAO.editAnswers(answers);
        quizDAO.updateTypeQuestion(questions);
    }

}
