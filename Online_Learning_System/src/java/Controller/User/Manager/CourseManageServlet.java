/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.AccountDAO;
import Dal.CourseDetailDAO;
import Dal.CourseManageDAO;
import Dal.HomeDAO;
import Dal.LessonManageDAO;
import Dal.ModuleDAO;
import Dal.ProfileManageDAO;
import Model.AccountDTO;
import Model.Category;
import Model.CourseManageDTO;
import Model.LessonDTO;
import Model.ModuleDTO;
import Model.ProfileDTO;
import Util.MyCommon;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuong
 */
@MultipartConfig
public class CourseManageServlet extends HttpServlet {

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
            out.println("<title>Servlet CourseManageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseManageServlet at " + request.getContextPath() + "</h1>");
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
    AccountDAO account_dao = new AccountDAO();
    CourseManageDAO course_manage_DAO = new CourseManageDAO();
    ProfileManageDAO profile_manage_dao = new ProfileManageDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter o = response.getWriter();
        MyCommon.getHeader(request, response);
        //get Parameter
        String cid = (String) request.getParameter("cid") == null ? "" : (String) request.getParameter("cid");
        String action = (String) request.getParameter("action") == null ? "" : (String) request.getParameter("action");

        HttpSession session = request.getSession();
//        AccountDTO my_account = (AccountDTO) session.getAttribute("account");
        AccountDTO my_account = MyCommon.getMyAccount(request,response);

        if (my_account.getRole_id() == 2) {
            ArrayList<CourseManageDTO> list_managed_course = course_manage_DAO.getMyManagedCourses(my_account.getAccount_id());
            request.setAttribute("list_managed_couse", list_managed_course);
        } else if (my_account.getRole_id() == 3) {
            ArrayList<CourseManageDTO> list_managed_course = course_manage_DAO.getMyTeachingCourses(my_account.getAccount_id());
            request.setAttribute("list_managed_couse", list_managed_course);
        }
        if (my_account.getRole_id() == 2) {
            ArrayList<ProfileDTO> list_my_mentors = course_manage_DAO.getMyMentors(my_account.getAccount_id());
            request.setAttribute("list_my_mentors", list_my_mentors);
            request.setAttribute("my_role", my_account.getRole_id());
        } else if (my_account.getRole_id() == 3) {
            ArrayList<ProfileDTO> list_my_mentors = course_manage_DAO.getMyMentors(my_account.getManaged_by());
            request.setAttribute("list_my_mentors", list_my_mentors);
            request.setAttribute("my_role", my_account.getRole_id());
        }
        switch (action) {
            case "update":
                updateCourseDoGet(request, response, cid);
                return;

            case "add_module":
                addModule(request, response, cid);
                return;
            case "course_teaching":
                courseTeaching(request, response);
                return;

            case "add_new_course":
                try {
                addCourse(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CourseManageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
            default:
                request.getRequestDispatcher("CourseManage.jsp").forward(request, response);
        }
    }
//    public static void main(String[] args) {
//        CourseManageDAO course_manage_DAO = new CourseManageDAO();
//        ArrayList<CourseManageDTO> list_managed_course = course_manage_DAO.getMyManagedCourse(2);
//        System.out.println(list_managed_course.get(0).getCourse_name());
//    }

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
        String action = request.getParameter("action");
        String cid = (String) request.getParameter("cid") == null ? "" : (String) request.getParameter("cid");
        PrintWriter o = response.getWriter();
        switch (action) {
            case "update":
                updateCourseDoPost(request, response, cid);
                break;
            case "add_new_module":
                addNewModuleDoPost(request, response, cid);
                break;
            case "add_new_course":
                addNewCouseDoPost(request, response);
                break;
            case "delete":
                deleteCourse(cid, request, response);
                break;
            case "activate":
                activateCourse(cid, request, response);
                return;
            default:
                throw new AssertionError();
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

    private void deleteCourse(String cid, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseManageDAO courseManageDAO = new CourseManageDAO();
        String msg = "";
        boolean success = false;
        // kiểm tra nếu true thì có thể thay đổi trạng thái khóa học
        if (courseManageDAO.canChangeStatusCourse(cid)) {
            success = courseManageDAO.deleteCourse(cid);
        } else {
            msg = "Can't change status of this course because there are still students enrolled.";
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"success\":" + success + ", \"message\":\"" + msg + "\"}");

        out.flush();
    }

    private void activateCourse(String cid, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean success = course_manage_DAO.activateCourse(cid);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + "}");
        out.flush();
    }
//    public static void main(String[] args) {
//        CourseManageServlet c = new CourseManageServlet();
//        c.deleteCourse("1");
//    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.getRequestDispatcher("AddNewCourse.jsp").forward(request, response);
    }

    private void addNewCouseDoPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part file_image_course = request.getPart("image");
        String course_name = request.getParameter("courseName") == null ? "" : request.getParameter("courseName");
        String description = request.getParameter("description") == null ? "" : request.getParameter("description");
        String price = "".equals(request.getParameter("price")) ? "0" : request.getParameter("price");
        String discount = "".equals(request.getParameter("discount")) ? "0" : request.getParameter("discount");
        String category = request.getParameter("category") == null ? "" : request.getParameter("category");

        String[] fullFields = {course_name, description, category};

        request.setAttribute("price", price);
        request.setAttribute("discount", discount);

        String image_file_name = "";
        if (file_image_course != null && file_image_course.getSize() > 0) {
            image_file_name = Validation.inputFile(request, file_image_course, "image_course");
            request.setAttribute("image", image_file_name);
        }
        course_name = Validation.validName(course_name);
        if (!Validation.checkString(course_name)) {
            request.setAttribute("course_name", course_name);
            request.setAttribute("error_name", "You must input a valid course name!!");
        }
        if (!Validation.checkDesciptionCourse(description)) {
            request.setAttribute("description", description);
            request.setAttribute("error_desciption", "You must input a valid desciption (<2000 characters)!!");
        }
        if (!Validation.checkString(category)) {
            request.setAttribute("category", category);
            request.setAttribute("error_category", "You must choose category!");
        }
        if (Validation.checkStringArray(fullFields)) {
            HttpSession session = request.getSession();
            AccountDTO my_account = (AccountDTO) session.getAttribute("account");
            CourseManageDTO new_course = new CourseManageDTO(course_name, description, image_file_name, Float.parseFloat(price), Float.parseFloat(discount), category);
            course_manage_DAO.insertCourse(my_account.getAccount_id(), new_course);
            int cid = course_manage_DAO.getMyLastInsertedCourse(my_account.getAccount_id()).getCourse_id();
            response.sendRedirect("course-manage?cid=" + cid + "&action=update");
        } else {
            request.getRequestDispatcher("AddNewCourse.jsp").forward(request, response);
        }
    }

    private void addModule(HttpServletRequest request, HttpServletResponse response, String cid) throws ServletException, IOException {
        request.setAttribute("cid", cid);
        ModuleDAO module_dao = new ModuleDAO();
        ArrayList<ModuleDTO> list_module = module_dao.getListModulByCid(cid);
        ArrayList<Integer> list_number_module = new ArrayList<>();
        if (list_module.isEmpty()) {
            //nếu chưa có module nào thì request module number = 1
            list_number_module.add(1);
        } else {
            //kiểm tra nếu bị thiếu (đã xóa) module number nào thì có thể chèn vào
            list_module.sort(Comparator.comparingInt(ModuleDTO::getModule_number));
            int expectedNumber = 1;
            for (ModuleDTO module : list_module) {
                int currentNumber = module.getModule_number();
                // Thêm tất cả các số thiếu vào list_number_module
                while (expectedNumber < currentNumber) {
                    list_number_module.add(expectedNumber);
                    expectedNumber++;
                }
                expectedNumber = currentNumber + 1;
            }
            //truyền vào module number cuối cùng tiếp theo
            list_number_module.add((list_module.get(list_module.size() - 1).getModule_number()) + 1);
        }
        request.setAttribute("list_module_number_valid", list_number_module);
        request.getRequestDispatcher("AddNewModule.jsp").forward(request, response);
    }

    private void addNewModuleDoPost(HttpServletRequest request, HttpServletResponse response, String cid) throws IOException, ServletException {
        request.setAttribute("cid", cid);
        String module_name = request.getParameter("module_name") == null ? "" : request.getParameter("module_name");
        String module_number = request.getParameter("module_number") == null ? "" : request.getParameter("module_number");
        ModuleDAO module_dao = new ModuleDAO();
        ArrayList<ModuleDTO> list_module = module_dao.getListModulByCid(cid);
        ArrayList<Integer> list_number_module = new ArrayList<>();
        if (list_module.isEmpty()) {
            //nếu chưa có module nào thì request module number = 1
            list_number_module.add(1);
        } else {
            //kiểm tra nếu bị thiếu (đã xóa) module number nào thì có thể chèn vào
            list_module.sort(Comparator.comparingInt(ModuleDTO::getModule_number));
            int expectedNumber = 1;
            for (ModuleDTO module : list_module) {
                int currentNumber = module.getModule_number();
                // Thêm tất cả các số thiếu vào list_number_module
                while (expectedNumber < currentNumber) {
                    list_number_module.add(expectedNumber);
                    expectedNumber++;
                }
                expectedNumber = currentNumber + 1;
            }
            //truyền vào module number cuối cùng tiếp theo
            list_number_module.add((list_module.get(list_module.size() - 1).getModule_number()) + 1);
        }
        request.setAttribute("list_module_number_valid", list_number_module);

        String[] fullFields = {module_name, module_number};
        PrintWriter o = response.getWriter();
        module_name = Validation.validName(module_name);
        if (!Validation.checkString(module_name)) {
            request.setAttribute("module_name", module_name);
            request.setAttribute("error_module_name", "You must input module name!");
            request.setAttribute("module_number", module_number);
        }
//da nhap du fields
        if (Validation.checkStringArray(fullFields)) {
            ModuleDAO module_DAO = new ModuleDAO();
            ModuleDTO new_module = new ModuleDTO(module_name, Integer.parseInt(module_number));
            module_DAO.insertModule(cid, new_module);
            new_module = module_DAO.getModuleInserted(cid, module_number);
            response.sendRedirect("ModuleManage?moduleId=" + new_module.getModuleid() + "&cid=" + cid);
        } else {
            request.getRequestDispatcher("AddNewModule.jsp").forward(request, response);
        }
    }

    private void updateCourseDoGet(HttpServletRequest request, HttpServletResponse response, String cid) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDTO my_account = (AccountDTO) session.getAttribute("account");
        request.setAttribute("cid", cid);
        ModuleDAO module_dao = new ModuleDAO();
        LessonManageDAO lesson_manage_dao = new LessonManageDAO();
        try {
            ArrayList<ModuleDTO> list_module = module_dao.getListModulByCid(cid);
            ArrayList<LessonDTO> list_lesson = lesson_manage_dao.getListlessonByCid(Integer.parseInt(cid));
            CourseManageDTO my_managed_course = course_manage_DAO.getMyManagedCourseById(my_account.getAccount_id(), cid);
//            ArrayList<ProfileDTO> list_mentor = profile_manage_dao.getMyListManagedMentorByCouresId(my_account.getAccount_id(), cid);
            ArrayList<ProfileDTO> list_mentor_by_courseId = profile_manage_dao.getMyListManagedMentorByCouresId(my_account.getAccount_id(), cid);
            request.setAttribute("list_module", list_module);
            request.setAttribute("list_lesson", list_lesson);
            request.setAttribute("my_managed_course", my_managed_course);
//            request.setAttribute("list_mentor", list_mentor);
            request.setAttribute("my_role", my_account.getRole_id());
            request.setAttribute("list_mentor_by_courseId", list_mentor_by_courseId);
        } catch (SQLException ex) {
            Logger.getLogger(CourseManageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("UpdateCourse.jsp").forward(request, response);
    }

    private void updateCourseDoPost(HttpServletRequest request, HttpServletResponse response, String cid) throws ServletException, IOException {
//get data form
        Part file_image_course = request.getPart("image");
        String currentImage = request.getParameter("current_image") == null ? "" : request.getParameter("current_image");
        String course_name = request.getParameter("courseName") == null ? "" : request.getParameter("courseName");
        String description = request.getParameter("description") == null ? "" : request.getParameter("description");
        String price = "".equals(request.getParameter("price")) ? "0" : request.getParameter("price");
        String discount = "".equals(request.getParameter("discount")) ? "0" : request.getParameter("discount");
        String category = request.getParameter("category") == null ? "" : request.getParameter("category");
        String[] list_assigned_mentor = request.getParameterValues("mentors");
        PrintWriter o = response.getWriter();
//create string[] to check input all fields
        String[] fullFields = {course_name, description, category};
//set attribute price, discount 
        request.setAttribute("price", price);
        request.setAttribute("discount", discount);
//my account
        HttpSession session = request.getSession();
        AccountDTO my_account = (AccountDTO) session.getAttribute("account");
//update course
//check invalid
        String image_file_name = "";
        if (file_image_course != null && file_image_course.getSize() > 0) {
                image_file_name = Validation.inputFile(request, file_image_course, "image_course");
                request.setAttribute("image", image_file_name);
        } else {
            image_file_name = currentImage;
        }
        course_name = Validation.validName(course_name);
        if (!Validation.checkString(course_name)) {
            request.setAttribute("course_name", course_name);
            request.setAttribute("error_name", "You must input a valid course name!");
        }
        if (!Validation.checkDesciptionCourse(description)) {
            request.setAttribute("description", description);
            request.setAttribute("error_desciption", "You must input a valid desciption (<2000 characters)!");
        }
        if (!Validation.checkString(category)) {
            request.setAttribute("category", category);
            request.setAttribute("error_category", "You must choose category!");
        }
        course_manage_DAO.deleteMentorTeaching(cid);
        if (list_assigned_mentor == null) {
            list_assigned_mentor = new String[]{""};
        } else {
            if (Validation.checkStringArray(list_assigned_mentor)) {
                for (String mentorId : list_assigned_mentor) {
                    course_manage_DAO.assignMentorToCourse(mentorId, cid);
                }
            } else {
                System.out.println("Invalid mentor list");
            }
        }
//valid
        if (Validation.checkStringArray(fullFields)) {
            CourseManageDTO new_course = new CourseManageDTO(Integer.parseInt(cid), course_name, description, null, image_file_name, Float.parseFloat(price), Float.parseFloat(discount), category);
            course_manage_DAO.updateCourse(my_account.getAccount_id(), new_course);
            session.setAttribute("successMessage", "Changes saved successfully!");
            response.sendRedirect("course-manage?cid=" + cid + "&action=update");
        } else {
            ModuleDAO module_dao = new ModuleDAO();
            LessonManageDAO lesson_manage_dao = new LessonManageDAO();
            try {
                ArrayList<ModuleDTO> list_module = module_dao.getListModulByCid(cid);
                ArrayList<LessonDTO> list_lesson = lesson_manage_dao.getListlessonByCid(Integer.parseInt(cid));
                CourseManageDTO my_managed_course = course_manage_DAO.getMyManagedCourseById(my_account.getAccount_id(), cid);
                ArrayList<ProfileDTO> list_mentor = profile_manage_dao.getMyListManagedMentorByCouresId(my_account.getAccount_id(), cid);
                ArrayList<ProfileDTO> list_mentor_by_courseId = profile_manage_dao.getMyListManagedMentorByCouresId(my_account.getAccount_id(), cid);

                request.setAttribute("list_module", list_module);
                request.setAttribute("list_lesson", list_lesson);
                request.setAttribute("my_managed_course", my_managed_course);
                request.setAttribute("list_mentor", list_mentor);
                request.setAttribute("list_mentor_by_courseId", list_mentor_by_courseId);
            } catch (SQLException ex) {
                Logger.getLogger(CourseManageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("UpdateCourse.jsp").forward(request, response);
        }
    }

    private void courseTeaching(HttpServletRequest request, HttpServletResponse response) {

    }

}
