/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Category;
import Model.Course;
import java.sql.Date;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class HomeDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về
    //Lấy course theo course ID

    // Lấy tất cả course
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        String sql = """
                    SELECT 
                                              cr.[CourseId],
                                              cr.[CourseName],
                                              cr.[Description],
                                              cr.[Image],
                                              cr.[Price],
                                              cr.[CourseCategoryId],
                                              cr.[CreatedBy],
                                              cr.[DateCreated],
                                              cr.[StudyTime],
                                              cr.[Status],
                                              pro.[FullName],
                                              COUNT(e.EnrollmentId) as StudentCount,
                                          	(
                                                  SELECT TOP 1 L.LessonId
                                                  FROM Module M
                                                  INNER JOIN Lesson L ON M.ModuleId = L.ModuleId
                                                  WHERE M.CourseId = Cr.CourseId
                                                  ORDER BY L.LessonId
                                              ) AS FirstLessonId
                                          FROM 
                                              [dbo].[Course] cr
                                          JOIN 
                                              [dbo].[Profile] pro ON pro.[ProfileId] = cr.CreatedBy
                                          LEFT JOIN 
                                              Enrollment e ON cr.CourseId = e.CourseId
                     						 Where cr.Status = 1
                                          GROUP BY 
                                              cr.CourseId, cr.CourseName, cr.[Description], cr.[Image], cr.[Price],
                                              cr.[CourseCategoryId], cr.[CreatedBy], cr.[DateCreated], cr.[StudyTime],
                                              cr.[Status], pro.[FullName]
                                          ORDER BY 
                                              StudentCount DESC;""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt(1);
                String courseName = rs.getString(2);
                String description = rs.getString(3);
                String image = rs.getString(4);
                int price = rs.getInt(5);
                String courseCategoryId = rs.getString(6);
                int createdBy = rs.getInt(7);
                Date dateCreated = rs.getDate(8);
                String studyTime = rs.getString(9);
                int status = rs.getInt(10);
                String instructor = rs.getString(11);
                int amountSudentJoin = rs.getInt(12);
                int firsrLessonid = rs.getInt(13);
                Course course = new Course(courseId, courseName, description, instructor, image, price, courseCategoryId, createdBy, dateCreated, studyTime, status, amountSudentJoin, firsrLessonid);

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    //Lấy danh sách category
    public ArrayList<Category> getAllCategory() throws SQLException {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT [CourseCategoryId]\n"
                + "      ,[CategoryName]\n"
                + "  FROM [Project Online Learning].[dbo].[CourseCategory]";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                String cate_id = rs.getString(1);
                String cate_name = rs.getString(2);

                list.add(new Category(cate_id, cate_name));
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return list;
    }

    public ArrayList<Category> getCategoryAndCountCourse() throws SQLException {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "SELECT cc.CourseCategoryId,cc.[CategoryName], COUNT(c.[CourseId]) AS CourseCount\n"
                + "FROM [Project Online Learning].[dbo].[Course] c\n"
                + "INNER JOIN [Project Online Learning].[dbo].[CourseCategory] cc\n"
                + "ON c.[CourseCategoryId] = cc.[CourseCategoryId]\n"
                + "GROUP BY  cc.CourseCategoryId,cc.[CategoryName]\n"
                + "ORDER BY CourseCount DESC;";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                String cate_id = rs.getString(1);
                String cate_name = rs.getString(2);
                int count = rs.getInt(3);

                list.add(new Category(cate_id, cate_name ,count));
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return list;
    }

    public ArrayList<Course> getCourseByCategoryId(String cateId) {
        ArrayList<Course> courses = new ArrayList<>();

        String sql = """
                     	SELECT 
                                                  cr.[CourseId],
                                                  cr.[CourseName],
                                                  cr.[Description],
                                                  cr.[Image],
                                                  cr.[Price],
                                                  ct.[CourseCategoryId],
                                                  cr.[CreatedBy],
                                                  cr.[DateCreated],
                                                  cr.[StudyTime],
                                                  cr.[Status],
                                                  pro.[FullName],
                                                  COUNT(e.EnrollmentId) AS StudentCount,
                                              	(
                                                      SELECT TOP 1 L.LessonId
                                                      FROM Module M
                                                      INNER JOIN Lesson L ON M.ModuleId = L.ModuleId
                                                      WHERE M.CourseId = Cr.CourseId
                                                      ORDER BY L.LessonId
                                                  ) AS FirstLessonId
                                              FROM 
                                                  [dbo].[Course] cr
                                              JOIN 
                                                  [dbo].[CourseCategory] ct ON ct.[CourseCategoryId] = cr.[CourseCategoryId]
                         
                                              JOIN 
                                                  [dbo].[Profile] pro ON pro.[ProfileId] = cr.CreatedBy
                                              LEFT JOIN 
                                                  Enrollment e ON cr.CourseId = e.CourseId
                                              WHERE 
                                                  ct.CourseCategoryId = ? and cr.Status = 1
                                              GROUP BY 
                                                  cr.[CourseId], 
                                                  cr.[CourseName], 
                                                  cr.[Description], 
                                                  cr.[Image],
                                                  cr.[Price], 
                                                  ct.[CourseCategoryId],
                                                  cr.[CreatedBy], 
                                                  cr.[DateCreated],
                                                  cr.[StudyTime],
                                                  cr.[Status], 
                                                  pro.[FullName]
                                              ORDER BY 
                                                  StudentCount DESC;""";

        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int courseId = rs.getInt(1);
                    String courseName = rs.getString(2);
                    String description = rs.getString(3);
                    String image = rs.getString(4);
                    int price = rs.getInt(5);
                    String courseCategoryId = rs.getString(6);
                    int createdBy = rs.getInt(7);
                    Date dateCreated = rs.getDate(8);
                    String studyTime = rs.getString(9);
                    int status = rs.getInt(10);
                    String instructor = rs.getString(11); // Assuming Instructor column is in Teaching table
                    int amountSudentJoin = rs.getInt(12);
                    int firsrLessonid = rs.getInt(13);

                    courses.add(new Course(courseId, courseName, description, instructor, image, price, courseCategoryId, createdBy, dateCreated, studyTime, status, amountSudentJoin, firsrLessonid));

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public ArrayList<Course> getNewCourse() throws SQLException {
        ArrayList<Course> list = new ArrayList<>();

        String sql = """
                     	SELECT TOP 2 
                                                  cr.[CourseId],
                                                  cr.[CourseName],
                                                  cr.[Description],
                                                  cr.[Image],
                                                  cr.[Price],
                                                  cr.[CourseCategoryId],
                                                  cr.[CreatedBy],
                                                  cr.[DateCreated],
                                                  cr.[StudyTime],
                                                  cr.[Status],
                                                  pro.[FullName],
                                              	(
                                                      SELECT TOP 1 L.LessonId
                                                      FROM Module M
                                                      INNER JOIN Lesson L ON M.ModuleId = L.ModuleId
                                                      WHERE M.CourseId = Cr.CourseId
                                                      ORDER BY L.LessonId
                                                  ) AS FirstLessonId
                                              FROM 
                                                  [dbo].[Course] cr
                         
                                              JOIN 
                                                  [dbo].[Profile] pro ON pro.[ProfileId] = cr.CreatedBy
                                                WHERE cr.Status = 1
                                              ORDER BY 
                                                  cr.[CourseId] DESC;""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int course_id = rs.getInt(1);
                String course_name = rs.getString(2);
                String description = rs.getString(3);
                String image = rs.getString(4);
                int price = rs.getInt(5);
                String cate_id = rs.getString(6);
                int create_by = rs.getInt(7);
                Date date = rs.getDate(8);
                String studyTime = rs.getString(9);
                int status = rs.getInt(10);
                String instructor = rs.getString(11);
                int firsrLessonid = rs.getInt(12);

                list.add(new Course(course_id, course_name, description, instructor, image, price, cate_id, create_by, date, studyTime, status, firsrLessonid));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    public ArrayList<Course> getPopulerCourse() throws SQLException {
        ArrayList<Course> list = new ArrayList<>();

        String sql = """
                     	SELECT TOP (3) 
                                                  cr.[CourseId],
                                                  cr.[CourseName],
                                                  cr.[Description],
                                                  cr.[Image],
                                                  cr.[Price],
                                                  cr.[CourseCategoryId],
                                                  cr.[CreatedBy],
                                                  cr.[DateCreated],
                                                  cr.[StudyTime],
                                                  cr.[Status],
                                                  pro.[FullName] AS MentorName,
                                                  COUNT(e.EnrollmentId) AS StudentCount,
                                              		(
                                                      SELECT TOP 1 L.LessonId
                                                      FROM Module M
                                                      INNER JOIN Lesson L ON M.ModuleId = L.ModuleId
                                                      WHERE M.CourseId = Cr.CourseId
                                                      ORDER BY L.LessonId
                                                  ) AS FirstLessonId
                                              FROM 
                                                  [dbo].[Course] cr
                         
                                              JOIN 
                                                  [dbo].[Profile] pro ON pro.[ProfileId] = cr.CreatedBy
                                              LEFT JOIN 
                                                  Enrollment e ON cr.CourseId = e.CourseId
                                                  Where cr.Status = 1
                                              GROUP BY 
                                                  cr.[CourseId], 
                                                  cr.[CourseName], 
                                                  cr.[Description], 
                                                  cr.[Image], 
                                                  cr.[Price], 
                                                  cr.[CourseCategoryId], 
                                                  cr.[CreatedBy], 
                                                  cr.[DateCreated], 
                                                  cr.[StudyTime], 
                                                  cr.[Status], 
                                                  pro.[FullName]
                                              ORDER BY 
                                                  StudentCount DESC;""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int course_id = rs.getInt(1);
                String course_name = rs.getString(2);
                String description = rs.getString(3);
                String image = rs.getString(4);
                int price = rs.getInt(5);
                String cate_id = rs.getString(6);
                int create_by = rs.getInt(7);
                Date date = rs.getDate(8);
                String studyTime = rs.getString(9);
                int status = rs.getInt(10);
                String instructor = rs.getString(11);
                int amountSudentJoin = rs.getInt(12);
                int firsrLessonid = rs.getInt(13);

                list.add(new Course(course_id, course_name, description, instructor, image, price, cate_id, create_by, date, studyTime, status, amountSudentJoin, firsrLessonid));

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    //Tìm kiếm khóa học theo tên
    //Lấy ra những sản phẩm khi search
    public ArrayList<Course> searchByName(String txtSearch) throws SQLException {
        ArrayList<Course> list = new ArrayList<>();

        String sql = """
                     SELECT 
                         cr.[CourseId],
                         cr.[CourseName],
                         cr.[Description],
                         cr.[Image],
                         cr.[Price],
                         cr.[CourseCategoryId],
                         cr.[CreatedBy],
                         cr.[DateCreated],
                         cr.[StudyTime],
                         cr.[Status],
                         pro.[FullName] AS MentorName,
                         COUNT(e.EnrollmentId) AS StudentCount,
                     		(
                             SELECT TOP 1 L.LessonId
                             FROM Module M
                             INNER JOIN Lesson L ON M.ModuleId = L.ModuleId
                             WHERE M.CourseId = Cr.CourseId
                             ORDER BY L.LessonId
                         ) AS FirstLessonId
                     FROM 
                         [dbo].[Course] cr
                     JOIN 
                         [dbo].[Profile] pro ON pro.[ProfileId] = cr.CreatedBy
                     LEFT JOIN 
                         Enrollment e ON cr.CourseId = e.CourseId
                     WHERE 
                         cr.[CourseName] LIKE ? and cr.Status = 1
                     GROUP BY 
                         cr.[CourseId], 
                         cr.[CourseName], 
                         cr.[Description], 
                         cr.[Image], 
                         cr.[Price], 
                         cr.[CourseCategoryId], 
                         cr.[CreatedBy], 
                         cr.[DateCreated], 
                         cr.[StudyTime], 
                         cr.[Status], 
                         pro.[FullName]
                     ORDER BY 
                         StudentCount DESC;""";

        con = new DBContext().getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, "%" + txtSearch + "%");
        rs = ps.executeQuery();
        while (rs.next()) {

            int course_id = rs.getInt(1);
            String course_name = rs.getString(2);
            String description = rs.getString(3);
            String image = rs.getString(4);
            int price = rs.getInt(5);
            String cate_id = rs.getString(6);
            int create_by = rs.getInt(7);
            Date date = rs.getDate(8);
            String studyTime = rs.getString(9);
            int status = rs.getInt(10);
            String instructor = rs.getString(11);
            int amountSudentJoin = rs.getInt(12);
            int firsrLessonid = rs.getInt(13);

            list.add(new Course(course_id, course_name, description, instructor, image, price, cate_id, create_by, date, studyTime, status, amountSudentJoin, firsrLessonid));
        }
        return list;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        HomeDAO dao = new HomeDAO();
        System.out.println(dao.getPopulerCourse());

    }
}
