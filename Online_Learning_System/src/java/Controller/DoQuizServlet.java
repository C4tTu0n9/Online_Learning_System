/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.CourseDetailDAO;
import Dal.DisscussionDAO;
import Dal.EnrollmentDAO;
import Dal.HomeDAO;
import Dal.LessonDAO;
import Dal.LessonManageDAO;
import Dal.QuizDAO;
import Dal.StarRatingDAO;
import Model.AccountDTO;
import Model.AccountDTO;
import Model.UserAnswer;
import Model.Answer;
import Model.Category;
import Model.Course;
import Model.DiscussionLesson;
import Model.Enrollment;
import Model.LessonDTO;
import Model.Questions;
import Model.Quiz;
import Model.ScoreQuiz;
import Model.StarRatingDTO;
import Util.AVGOfRaing;
import YoutubeAPI.YoutubeDuration;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;

/**
 *
 * @author hatro
 */
public class DoQuizServlet extends HttpServlet {

    LessonDAO dao = new LessonDAO();
    DisscussionDAO discussDao = new DisscussionDAO();
    EnrollmentDAO enrollment_dao = new EnrollmentDAO();

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
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        QuizDAO quizDAO = new QuizDAO();
        HttpSession session = request.getSession();
        AccountDTO acc = (AccountDTO) session.getAttribute("account");
        session.removeAttribute("quizTimeLeft");
        int moduleId = Integer.parseInt(request.getParameter("mid"));
        PrintWriter o = response.getWriter();
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
            Course course = quizDAO.findCourseIdAndCreateByByModuleId(moduleId);
            ArrayList<Questions> listQuestionByModuleId = quizDAO.getListQuestionsByModuleId(moduleId);
            ArrayList<Answer> listAnswerByModuleId = quizDAO.getlistAnswerByModuleId(moduleId);
            ScoreQuiz score = quizDAO.findScoreDoQuizByAccountIdAndQuizId(acc.getAccount_id(), quiz.getQuizId());
            ScoreQuiz my_max_score = quizDAO.getMaxMyScoreInQuiz(acc.getAccount_id(), quiz.getQuizId());
            request.setAttribute("score", score);
            request.setAttribute("my_max_score", my_max_score);
            request.setAttribute("course", course);
            request.setAttribute("quizDoQuiz", quiz);
            o.print(quiz.getModuleId());
            request.setAttribute("listQuestionsByMId", listQuestionByModuleId);
            request.setAttribute("listAnswerByMId", listAnswerByModuleId);
            switch (action) {
                case "do_quiz":
                    request.getRequestDispatcher("do_quiz/do_quiz.jsp").forward(request, response);
                    break;
                default:
                    showInformation(request, response);
            }
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

        quizDAO.deleteAnswerByAccountIdAndQuizId(acc.getAccount_id(), quiz.getQuizId());
//      Insert danh sách đáp án của người dùng vào database
        quizDAO.insertUserAnser(listUserAnswer);
        float totalScore = (float) score / listQuestionByModuleId.size() * 10;
        boolean pass_quiz = false;
        if (totalScore > quiz.getPassScore()) {
            pass_quiz = true;
        }
        ScoreQuiz scorequiz = new ScoreQuiz(acc.getAccount_id(), quiz.getQuizId(), totalScore, pass_quiz);

        quizDAO.insertcoreQuiz(scorequiz);
        ArrayList<Quiz> total_quiz_in_course = quizDAO.getListQuizByCourseId(quiz.getCourse_id());
        ArrayList<Quiz> my_quiz_pass = quizDAO.getListQuizPassed(acc.getAccount_id(), quiz.getCourse_id());
        int progress = (int) ((float) my_quiz_pass.size() / (float) total_quiz_in_course.size() * 100);
        enrollment_dao.updateProgressCourse(acc.getAccount_id(), quiz.getCourse_id(), progress);
        //progress = 100 thi chuyen huong sang trang rate course
        boolean noRatedBefore = checkIfUserHasRated(acc.getAccount_id(), quiz.getCourse_id());
        if (enrollment_dao.getMyProgress(acc.getAccount_id(), quiz.getCourse_id()) == 100) {
            if (noRatedBefore) {
                response.sendRedirect("StarRating?cid=" + quiz.getCourse_id());
                return;
            }
        }
//         Chuyển hướng sau khi xử lý
        response.sendRedirect("doquizsub?mid=" + moduleId);
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

    private boolean isPaid(int cid, ArrayList<Enrollment> enrollmentList) {
        for (Enrollment e : enrollmentList) {
            if (cid == e.getCourseid()) {
                return true;
            }
        }

        return false;
    }

    public void displayRatingCourse(HttpServletRequest request, HttpServletResponse response, int course_id)
            throws ServletException, IOException {
        try {
            CourseDetailDAO cdDao = new CourseDetailDAO();
            ArrayList<StarRatingDTO> listRatings = cdDao.getRatings(course_id);
            //lấy ra số lượng sao trung bình và tổng số lượng đánh giá của khóa học
            ArrayList<Double> avgRatingCourse = AVGOfRaing.AvgRatingCourse(listRatings);

            request.setAttribute("avgRatingCourse", avgRatingCourse.get(0));
            request.setAttribute("amountRatingCourse", avgRatingCourse.get(1));
        } catch (SQLException ex) {
            Logger.getLogger(lessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displaycategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HomeDAO dao = new HomeDAO();
            ArrayList<Category> listCategory = dao.getAllCategory();
            request.setAttribute("listCategory", listCategory);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //tính tổng thời gian học khóa học
    private long sumOfDurationInCourse(int course_id)
            throws ServletException, IOException {
        LessonManageDAO dao = new LessonManageDAO();
        long sumDuration = 0;
        try {

            ArrayList<LessonDTO> listLesson = dao.getListlessonByCid(course_id);
            for (LessonDTO lesson : listLesson) {
                sumDuration += lesson.getDuration();
            }

        } catch (SQLException ex) {
            Logger.getLogger(lessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sumDuration;
    }

    private void displayTotalTimeLearnCourse(HttpServletRequest request, HttpServletResponse response, long sumDuration)
            throws ServletException, IOException {
        String time = null;
        if (sumDuration >= 3600) {
            time = YoutubeDuration.SumConvertToHoursAndMinutesLesson(sumDuration);
        } else if (sumDuration > 0) {
            time = YoutubeDuration.SumConvertToMinutesAndSecondsLesson(sumDuration);
        } else {
            time = "00 hrs  00 min  00 sec";
        }

        if (time != null) {
            request.setAttribute("totalTime", time);
        } else {
            request.setAttribute("totalTime", "00 hrs  00 min  00 sec");

        }
    }

    private void showInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuizDAO quizDAO = new QuizDAO();
        HttpSession session = request.getSession();
        AccountDTO acc = (AccountDTO) session.getAttribute("account");
        int moduleId = Integer.parseInt(request.getParameter("mid"));
        PrintWriter o = response.getWriter();
        Quiz quiz = quizDAO.findQuizByModuleId(moduleId);
        if (acc == null) {
            response.sendRedirect("join?action=login");
            return;
        }
        String cid = request.getParameter("cid");

        ArrayList<LessonDTO> lessonList = null;
        try {
            lessonList = dao.getListModulByCidd(Integer.parseInt(cid));

            int lesson_id = 0;
            if (!lessonList.isEmpty()) {
                lesson_id = lessonList.get(0).getLessonid();
            }
            ArrayList<Enrollment> listEnrollment = dao.getEnrollmentByAccountId(acc.getAccount_id());
            ArrayList<Model.ModuleDTO> moduleList = dao.getListModulByCid(Integer.parseInt(cid));
            LessonDTO lesson = dao.getlessonByCid(Integer.parseInt(cid), lesson_id);

            ArrayList<DiscussionLesson> allComments = discussDao.getCommentsByLesson(lesson_id);
//              // Phân tách comments chính và các replies
            ArrayList<DiscussionLesson> mainComments = new ArrayList<>();
            Map<Integer, List<DiscussionLesson>> repliesMap = new HashMap<>();

            for (DiscussionLesson comment : allComments) {
                if (comment.getParentId() == null || comment.getParentId() == 0) {
                    mainComments.add(comment);
                } else {
                    int parentId = comment.getParentId();
                    if (!repliesMap.containsKey(parentId)) {
                        repliesMap.put(parentId, new ArrayList<>());
                    }
                    repliesMap.get(parentId).add(comment);
                }
            }
            //hiện thị ra các category trên header
            displaycategory(request, response);

            //hiện thị số lượng sao của khóa học đó
            displayRatingCourse(request, response, Integer.parseInt(cid));

            //Hiện thỉ tổng thời gian khóa học
            long totalDuration = sumOfDurationInCourse(Integer.parseInt(cid));
            displayTotalTimeLearnCourse(request, response, totalDuration);

            //Lấy quiz theo module id
            ArrayList<Quiz> quizLits = quizDAO.findListQuizByCourseId(Integer.parseInt(cid));
            request.setAttribute("mainComments", mainComments);
            request.setAttribute("repliesMap", repliesMap);

//                out.print(parentCommentid);
            request.setAttribute("lesson", lesson);
            request.setAttribute("moduleList", moduleList);
            request.setAttribute("quizLits", quizLits);
            request.setAttribute("lessonList", lessonList);
            request.setAttribute("listEnrollment", listEnrollment);
            //response.getWriter().print(lessonList);
        } catch (SQLException ex) {
            Logger.getLogger(DoQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Course course = quizDAO.findCourseIdAndCreateByByModuleId(moduleId);
        ArrayList<Questions> listQuestionByModuleId = quizDAO.getListQuestionsByModuleId(moduleId);
        ArrayList<Answer> listAnswerByModuleId = quizDAO.getlistAnswerByModuleId(moduleId);
        request.setAttribute("course", course);
        request.setAttribute("quizDoQuiz", quiz);
        o.print(quiz.getModuleId());
        request.setAttribute("listQuestionsByMId", listQuestionByModuleId);
        request.setAttribute("listAnswerByMId", listAnswerByModuleId);
        request.getRequestDispatcher("mentee_my_quiz.jsp").forward(request, response);
    }

    private boolean checkIfUserHasRated(int accountId, int courseId) {
        StarRatingDAO dao = new StarRatingDAO();
        return dao.getUserRatings(accountId, courseId) == null;
    }

}
