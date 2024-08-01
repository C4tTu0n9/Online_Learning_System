/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.CourseDetailDAO;
import Dal.DisscussionDAO;
import Dal.HomeDAO;
import Dal.LessonDAO;
import Dal.LessonManageDAO;
import Dal.QuizDAO;

import Model.AccountDTO;

import Model.Category;
import Model.Course;
import YoutubeAPI.YoutubeDuration;
import Model.DiscussionLesson;
import Model.Enrollment;
import Model.LessonDTO;
import Model.Quiz;
import Model.StarRatingDTO;
import Model.TeachingDTO;
import Util.AVGOfRaing;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class lessonServlet extends HttpServlet {

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
            out.println("<title>Servlet lessonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet lessonServlet at " + request.getContextPath() + "</h1>");
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
        LessonDAO dao = new LessonDAO();
        DisscussionDAO discussDao = new DisscussionDAO();
        QuizDAO quizDao = new QuizDAO();
        CourseDetailDAO cdtDao = new CourseDetailDAO();
        HttpSession session = request.getSession();

        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        String createBy = request.getParameter("createBy");
        String courseid_str = request.getParameter("cid");
        String lessonid_str = request.getParameter("lessonid");

        int course_id = 0;
        int lesson_id = 0;
        int createBy_id = 0;

        // Kiểm tra nếu session không có thuộc tính 'user' thì chuyển hướng về trang đăng nhập
        if (acc == null) {
            response.sendRedirect("join?action=login");
            return;
        } else {
            try {

                if (courseid_str != null && createBy != null) {
                    course_id = Integer.parseInt(courseid_str);
                    createBy_id = Integer.parseInt(createBy);
                }

//                đề phòng nếu lessonid_str rỗng
                // Kiểm tra cookie nếu không có lessonid trong request
                if (lessonid_str == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            if (c.getName().equals("lastLessonId_" + course_id)) {
                                lessonid_str = c.getValue();
                                break;
                            }
                        }
                    }
                    // Nếu vẫn không có lessonid, đặt bài học đầu tiên là mặc định
                    if (lessonid_str == null) {
                        ArrayList<LessonDTO> lessonList = dao.getListModulByCidd(course_id);

                        if (!lessonList.isEmpty()) {
                            lesson_id = lessonList.get(0).getLessonid();
                        }
                    }
                }

                if (lessonid_str != null) {
                    lesson_id = Integer.parseInt(lessonid_str);
                }

                ArrayList<Enrollment> listEnrollment = dao.getEnrollmentByAccountId(acc.getAccount_id());

                Course c = cdtDao.getCourseById(course_id);
                //Kiểm tra có phải người tạo ra khóa học hay không(Phân Quyền)
                if(!checkMentorInLesson(acc.getAccount_id(), course_id, dao) && createBy_id != acc.getAccount_id()){
                    //Kiểm tra người dùng nếu chưa mua khóa học mà truy cập đường link thì chuyển về home       
                    if (!isPaid(course_id, listEnrollment) && c.getPrice() > 0) {
                        response.sendRedirect("home");
                        return;
                    }
            }
                
                ArrayList<LessonDTO> lessonList = dao.getListModulByCidd(course_id);
                ArrayList<Model.ModuleDTO> moduleList = dao.getListModulByCid(course_id);
                LessonDTO lesson = dao.getlessonByCid(course_id, lesson_id);
                ArrayList<TeachingDTO> mentor_list = dao.getListMentorByCid(course_id);

//                List all comment 
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
                displayRatingCourse(request, response, course_id);

                //Hiện thỉ tổng thời gian khóa học
                long totalDuration = sumOfDurationInCourse(course_id);
                displayTotalTimeLearnCourse(request, response, totalDuration);

                //Lấy quiz theo module id
                ArrayList<Quiz> quizLits = quizDao.findListQuizByCourseId(course_id);

                // Đặt các thuộc tính cho JSP
                request.setAttribute("mainComments", mainComments);
                request.setAttribute("repliesMap", repliesMap);

//                out.print(parentCommentid);
                request.setAttribute("lesson", lesson);
                request.setAttribute("mentorList", mentor_list);
                request.setAttribute("moduleList", moduleList);
                request.setAttribute("quizLits", quizLits);
                request.setAttribute("lessonList", lessonList);
                request.setAttribute("listEnrollment", listEnrollment);
                //response.getWriter().print(lessonList);

                //Lưu trữ id bài học hiện tại lên Cookie
                Cookie lastLessonCookie = new Cookie("lastLessonId_" + course_id, String.valueOf(lesson_id));
                lastLessonCookie.setMaxAge(60 * 60 * 24 * 30); //lưu trong vong một tháng
                // Đặt đường dẫn của cookie để nó có hiệu lực trên toàn bộ trang web
                lastLessonCookie.setPath("/");
                response.addCookie(lastLessonCookie);

            } catch (SQLException ex) {
                Logger.getLogger(lessonServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("mentee_my_lesson.jsp").forward(request, response);

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

        String status = (request.getParameter("status") == null) ? "" : request.getParameter("status");

        switch (status) {
            case "insert":
                insertComent(request, response);
                break;
            case "delete":
                deleteComent(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void deleteComent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String parentCommentId = request.getParameter("parent");
        String disscusionId = request.getParameter("disscussID");
        String cid = request.getParameter("cid");
        String lessonid = request.getParameter("lessonid");
        String createBy = request.getParameter("createBy");

//        out.print(parentCommentId);
        DisscussionDAO dao = new DisscussionDAO();
        if (parentCommentId.equals("null")) {
            //delete comment cha và delete tất comment reply

            dao.deleteComent(Integer.parseInt(disscusionId));

            response.sendRedirect("lesson?cid=" + cid + "&lessonid=" + lessonid + "&createBy=" + createBy);
        } else {
            //delete 1 comment repy
            dao.deleteReplyComment(Integer.parseInt(disscusionId));
            response.sendRedirect("lesson?cid=" + cid + "&lessonid=" + lessonid + "&createBy=" + createBy);
        }

    }

    public void insertComent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String parentComentId_str = request.getParameter("parent");
        
        Integer parentCommentId = null;
        if (parentComentId_str != null && !parentComentId_str.isEmpty() && !"null".equals(parentComentId_str)) {
            parentCommentId = Integer.parseInt(parentComentId_str);
        }
        String createBy = request.getParameter("createBy");
        String comment = request.getParameter("content");
        String cid = request.getParameter("cid");

        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        String lession_id = request.getParameter("lessonid");
        try {
        if(Integer.parseInt(lession_id) == 0) {
            response.sendRedirect("lesson?cid=" + cid + "&lessonid=" + lession_id + "&createBy=" + createBy);
            return;
        }
        DisscussionDAO dao = new DisscussionDAO();
        DiscussionLesson discuss = new DiscussionLesson(parentCommentId, acc.getAccount_id(), Integer.parseInt(lession_id), comment, new Timestamp(System.currentTimeMillis()));
        dao.InsertComment(discuss);
        } catch(Exception ex) {
              response.sendRedirect("lesson?cid=" + cid + "&lessonid=" + 0 + "&createBy=" + createBy);
            return;
        }
        response.sendRedirect("lesson?cid=" + cid + "&lessonid=" + lession_id + "&createBy=" + createBy);
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

    //Kiểm tra xem người dùng đã mua khóa học này hay chưa nếu chưa thì chuyển về home
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
    
   private boolean checkMentorInLesson(int accountid, int courseId, LessonDAO dao) throws SQLException {
       ArrayList<TeachingDTO> listmentor = dao.getListMentorByCid(courseId);
       for (TeachingDTO teachingDTO : listmentor) {
           if(teachingDTO.getMentorid() == accountid) {
               return true;
           }
       }
       
       return false;
   }

}
