/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.CourseDetailDAO;
import Dal.HomeDAO;
import Dal.LessonDAO;

import Dal.LessonManageDAO;
import Dal.WishlistDAO;
import Model.AccountDTO;
import Model.Category;
import Model.Course;
import Model.Enrollment;
import Model.LessonDTO;

import Model.StarRatingDTO;
import Model.WishlistDTO;
import Util.AVGOfRaing;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.NumberFormat;
import java.util.Locale;
import javax.mail.Session;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class CourseDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet CourseDetailServelet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseDetailServelet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        CourseDetailDAO cdDao = new CourseDetailDAO();
        LessonDAO lessondao = new LessonDAO();
        try {
            String course_Id_str = request.getParameter("cid");
            int course_Id = 0;
            if (!course_Id_str.isBlank() && course_Id_str != null) {
                course_Id = Integer.parseInt(course_Id_str);
            }

            ArrayList<Course> listCourst_Relate = cdDao.getRelateCourse(course_Id);
            ArrayList<Category> listAllCategory = cdDao.getCategoryById(course_Id);
            ArrayList<StarRatingDTO> listRatings = cdDao.getRatings(course_Id);
            Course getCourseByID = cdDao.getCourseById(course_Id);

            if (acc != null) {
                //Kiểm tra đã mua khóa học này hay chưa
                ArrayList<Enrollment> listEnrollment = cdDao.getEnrollmentByAccountId(acc.getAccount_id());

                //Lấy ra list wishList để check is active icon
                getCidFromWishlistByAccId(request, response, acc.getAccount_id());
                request.setAttribute("listEnrollment", listEnrollment);
            }

            //Định dạng khóa học theo giá tiền Việt Nam và set tổng số h của khóa học

            for (Course course : listCourst_Relate) {
                course.setFormattedPrice(formartPrice(course.getPrice()));
                course.setStudy_time(sumOfDurationInCourseInHrs(course.getCourse_id()));

            }
            getCourseByID.setFormattedPrice(formartPrice(getCourseByID.getPrice()));

            getCourseByID.setStudy_time(sumOfDurationInCourseInHrs(course_Id));
            
            //Set số sao và lượt đánh giá cho từng khóa học
            for (Course course : listCourst_Relate) {
                ArrayList<StarRatingDTO> listRating = cdDao.getRatings(course.getCourse_id());
                course.setStar(AVGOfRaing.AvgRatingCourse(listRating).get(0));
                course.setSumOfRating(AVGOfRaing.AvgRatingCourse(listRating).get(1));
            }

            long lessonid = lessondao.getLessonIdByCourseId(course_Id);

            //hiện thì category in header
            displaycategory(request, response);
            //lấy ra số lượng sao trung bình và tổng số lượng đánh giá của khóa học
            displayRatingCourse(request, response, listRatings, course_Id);


            request.setAttribute("cid", course_Id);
            request.setAttribute("lessonid", lessonid);
            request.setAttribute("listRatings", listRatings);
            request.setAttribute("listCourse_relate", listCourst_Relate);
            request.setAttribute("listAllCategory", listAllCategory);
            request.setAttribute("getCourseByID", getCourseByID);

            //         out.print(getCourseByID.getImage());
        } catch (SQLException ex) {
            Logger.getLogger(CourseDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("detail.jsp").forward(request, response);
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
        String cid = request.getParameter("cid");

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

    public String formartPrice(int price) {
        NumberFormat formatTer = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatTer.format(price);
    }

    public void displayRatingCourse(HttpServletRequest request, HttpServletResponse response, ArrayList<StarRatingDTO> listRatings, int course_id)
            throws ServletException, IOException {

        //lấy ra số lượng sao trung bình và tổng số lượng đánh giá của khóa học
        ArrayList<Double> avgRatingCourse = AVGOfRaing.AvgRatingCourse(listRatings);

        request.setAttribute("avgRatingCourse", avgRatingCourse.get(0));
        request.setAttribute("amountRatingCourse", avgRatingCourse.get(1));

    }

    //Lấy courseId từ bảng wish List theo account id để xem tài khoản này đã thêm khóa học này vào wish  list hay chưa
    public void getCidFromWishlistByAccId(HttpServletRequest request, HttpServletResponse response, int acc_id)
            throws ServletException, IOException {
        WishlistDAO dao = new WishlistDAO();
        ArrayList<WishlistDTO> listWishListCoursId = dao.getCidFromWishListByAccId(acc_id);
        ArrayList<Integer> CourseIdList = new ArrayList<>();
        for (WishlistDTO wishlist : listWishListCoursId) {
            CourseIdList.add(wishlist.getCourse_id());
        }
        //response.getWriter().print(listWishListCoursId);

        request.setAttribute("CourseIdList", CourseIdList);
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
    private String sumOfDurationInCourseInHrs(int course_id)
            throws ServletException, IOException {
        LessonManageDAO dao = new LessonManageDAO();
        int sumDuration = 0;
        try {

            ArrayList<LessonDTO> listLesson = dao.getListlessonByCid(course_id);
            for (LessonDTO lesson : listLesson) {
                sumDuration += lesson.getDuration();
            }

        } catch (SQLException ex) {
            Logger.getLogger(lessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        double hours = (double) sumDuration / 3600;
        int before_hours = (int) hours;
        int after_hourse = (sumDuration % 3600) / 60;

        return before_hours + String.format(".%02d", after_hourse) + " Hrs";
    }


}
