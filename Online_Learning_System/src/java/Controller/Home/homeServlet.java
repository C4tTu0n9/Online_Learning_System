/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Home;


import Dal.AccountDAO;
import Dal.CourseDetailDAO;
import Dal.HomeDAO;
import Dal.LessonManageDAO;
import Dal.WishlistDAO;
import Model.AccountDTO;
import Model.Category;
import Model.Course;
import Model.Enrollment;
import Model.LessonDTO;

import Model.ProfileDTO;
import Model.StarRatingDTO;
import Model.WishlistDTO;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuong
 */
public class homeServlet extends HttpServlet {

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
            out.println("<title>Servlet homeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet homeServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        AccountDTO acc = (AccountDTO) session.getAttribute("account");

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        try {
            HomeDAO dao = new HomeDAO();
            CourseDetailDAO cdDao = new CourseDetailDAO();
            ArrayList<Category> listCategory = dao.getAllCategory();
            ArrayList<Course> listNewCourse = dao.getNewCourse();
            ArrayList<Course> listPopulerCourse = dao.getPopulerCourse();
            ArrayList<Category> listCategoryAndCountCourse = dao.getCategoryAndCountCourse();
            if (acc != null) {
                ArrayList<Enrollment> listEnrollment = cdDao.getEnrollmentByAccountId(acc.getAccount_id());
                request.setAttribute("listEnrollment", listEnrollment);


                //Lấy ra list wishList để check is active icon
                getCidFromWishlistByAccId(request, response, acc.getAccount_id());

            }

            //Định dạng khóa học theo giá tiền Việt Nam và set tổng số h của khóa học
            for (Course course : listPopulerCourse) {
                course.setFormattedPrice(formartPrice(course.getPrice()));
                course.setStudy_time(sumOfDurationInCourseInHrs(course.getCourse_id()));
            }
            //Set số sao và lượt đánh giá cho từng khóa học
            for (Course course : listPopulerCourse) {
                ArrayList<StarRatingDTO> listRating = cdDao.getRatings(course.getCourse_id());
                course.setStar(AVGOfRaing.AvgRatingCourse(listRating).get(0));
                course.setSumOfRating(AVGOfRaing.AvgRatingCourse(listRating).get(1));
            }




            request.setAttribute("action", action);
            request.setAttribute("listCategoryAndCountCourse", listCategoryAndCountCourse);
            request.setAttribute("listPopulerCourse", listPopulerCourse);
            request.setAttribute("listNewCourse", listNewCourse);
            request.setAttribute("listCategory", listCategory);

        } catch (SQLException ex) {
            Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProfile(request, response);

        request.getRequestDispatcher("index.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        getProfile(request, response);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void getProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie[] = request.getCookies();
        if (cookie == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        //check xem trong cookie co luu email va password ko
        boolean check_remember_email = false;
        boolean check_remember_password = false;

        String email = "";
        String password = "";

        for (Cookie c : cookie) {
            if (c.getName().equals("email")) {
                request.setAttribute("email", c.getValue());
                email = c.getValue();
                check_remember_email = true;
            }
            if (c.getName().equals("password")) {
                check_remember_password = true;
                password = c.getValue();
                request.setAttribute("password", c.getValue());
            }
        }

        // Neu trong cookie da luu tai khoan roi thi tu dong dang nhap luon
        if (check_remember_email && check_remember_password) {
            AccountDAO accountDAO = new AccountDAO();
            HttpSession session = request.getSession();
            AccountDTO account_login = accountDAO.getAccountByEmailPass(email, password);
            if (account_login != null) {
                ProfileDTO profile = accountDAO.getProfile(account_login);
                session.setAttribute("profile", profile);
            }

            session.setAttribute("account", account_login);
            session.setMaxInactiveInterval(60 * 30);
        }
    }

    public String formartPrice(int price) {
        NumberFormat formatTer = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatTer.format(price);
    }


    //kiểm tra xem heart active or inactive
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

        } catch(SQLException e) {
            
        }
        
        double hours = (double)sumDuration / 3600;
        int before_hours = (int) hours;
        int after_hourse = (sumDuration % 3600) / 60;
        
        
        return before_hours + String.format(".%02d", after_hourse) + " Hrs";
    }

    
    

}
