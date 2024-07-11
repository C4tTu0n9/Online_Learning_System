/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Course;
import Model.Enrollment;
import Model.LessonDTO;
import Model.Payment;

import Model.ModuleDTO;
import Model.TeachingDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class LessonDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

    // Chèn bill lên db
    public void insertBillPayment(Payment payment) {

        String sql = "insert into Payment\n"
                + "values (?,?,?,?,?)";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, payment.getAccountid());
            ps.setInt(2, payment.getCourseid());
            ps.setDate(3, payment.getPaymentDate());
            ps.setString(4, payment.getPaymentmethodid());
            ps.setInt(5, payment.getAmount());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

    }

    //khi thanh toán xong thì phải insert thông tin vào enrollment
    public void insertEnrollment(Enrollment enrollment) {

        String sql = "insert into Enrollment\n"
                + "values (?,?,?,?)";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, enrollment.getAccountid());
            ps.setInt(2, enrollment.getCourseid());
            ps.setDate(3, enrollment.getEnrollmentdate());
            ps.setInt(4, enrollment.getProgress());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

    }

    //Lấy lisst lesson in module theo course ID

    public ArrayList<LessonDTO> getListModulByCidd(int courseId) throws SQLException {
        ArrayList<LessonDTO> list = new ArrayList<>();
        String sql = """
                       SELECT
                         l.LessonId,
                         m.ModuleName,
                         l.LessonName,
                         l.LessonContent,
                         l.LessonVideo,
                         c.CourseName,
                         p.FullName,
                         p.Avatar,
                         c.CourseId,
                         p.ProfileId,
                         c.CreatedBy,
                         Duration
                     FROM dbo.Lesson l
                     JOIN dbo.Module m ON l.ModuleId = m.ModuleId
                     JOIN dbo.Course c ON c.CourseId = m.CourseId
                     JOIN dbo.Profile p ON p.ProfileId = c.CreatedBy
                     WHERE c.CourseId = ?""";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                String modulname = rs.getString(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                String course_name = rs.getString(6);
                String mentor_name = rs.getString(7);
                String Avatar = rs.getString(8);
                int course_id = rs.getInt(9);
                long duration = rs.getInt("Duration");
                int profile_id = rs.getInt("ProfileId");
                int create_by = rs.getInt("CreatedBy");


                list.add(new LessonDTO(lesson_id, modulname, lesson_name, lesson_content, lesson_video, course_name, mentor_name, Avatar, course_id, duration, profile_id, create_by));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }


    public LessonDTO getlessonByCid(int courseId, int lessonid) throws SQLException {

        String sql = "  SELECT\n"
                + "                   l.LessonId,\n"
                + "                m.ModuleName,\n"
                + "                  l.LessonName,\n"
                + "                   l.LessonContent,\n"
                + "                    l.LessonVideo,\n"
                + "                   c.CourseName,\n"
                + "                    p.FullName,\n"
                + "                    p.Avatar,\n"
                + "                	c.CourseId,\n"
                + "			p.ProfileId,\n"
                + "                     c.[CreatedBy]\n"
                + "               	,[Duration]\n"
                + "                FROM [dbo].[Lesson] l\n"
                + "                JOIN [dbo].[Module] m ON l.ModuleId = m.ModuleId\n"
                + "                JOIN [dbo].[Course] c ON c.CourseId = m.CourseId\n"
                + "                JOIN [dbo].[Teaching] teach ON teach.CourseId = c.CourseId\n"
                + "                JOIN [dbo].[Profile] p ON p.ProfileId = teach.MentorId\n"
                + "                WHERE c.CourseId = ? and l.LessonId = ?";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setInt(2, lessonid);

            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                String modulname = rs.getString(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                String course_name = rs.getString(6);
                String mentor_name = rs.getString(7);
                String Avatar = rs.getString(8);
                int course_id = rs.getInt(9);
                int profile_id = rs.getInt("ProfileId");
                long duration = rs.getInt("Duration");
                int create_by = rs.getInt("CreatedBy");


                return new LessonDTO(lesson_id, modulname, lesson_name, lesson_content, lesson_video, course_name, mentor_name, Avatar, course_id, duration, profile_id ,create_by);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;
    }

    //Lấy lisst module theo course ID

    public ArrayList<ModuleDTO> getListModulByCid(int courseId) throws SQLException {
        ArrayList<ModuleDTO> list = new ArrayList<>();
        String sql = """
                       SELECT  [ModuleId]
                                ,[ModuleName]
                                ,[CourseId]
                     		   ,[ModuleNumber]
                        FROM [dbo].[Module] 
                        WHERE CourseId = ?
                        Order by ModuleNumber ASC """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int moduleid = rs.getInt(1);
                String modulename = rs.getString(2);
                int course_id = rs.getInt(3);

                list.add(new ModuleDTO(moduleid, modulename));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    //Lấy ra list enrollment để kiểm tra người dùng đã mua khóa học này hay chưa
    public ArrayList<Enrollment> getEnrollmentByAccountId(int courseId) throws SQLException {
        ArrayList<Enrollment> list = new ArrayList<>();
        String sql = " SELECT [EnrollmentId]\n"
                + "      ,[AccountId]\n"
                + "      ,[CourseId]\n"
                + "      ,[EnrollmentDate]\n"
                + "      ,[Progress]\n"
                + "  FROM [dbo].[Enrollment]\n"
                + "  WHERE [AccountId] = ?";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {

                int enrollId = rs.getInt(1);
                int accId = rs.getInt(2);
                int courseid = rs.getInt(3);
                Date enrollmentDate = rs.getDate(4);
                int progress = rs.getInt(5);

                list.add(new Enrollment(enrollId, accId, courseid, enrollmentDate, progress));
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return list;
    }

    //Lấy ra lesson Id để chuyuển hướn gnười tới khóa học với lesson đó khí thanh toán thành công
    public long getLessonIdByCourseId(int courseId) throws SQLException {

        String sql = """
                       SELECT TOP (1) [LessonId]
                     
                       FROM [dbo].[Lesson] l
                     JOIN [dbo].[Module] m ON l.ModuleId = m.ModuleId
                     JOIN [dbo].[Course] c ON c.CourseId = m.CourseId
                       WHERE c.CourseId = ?
                       ORDER BY [LessonId] ASC
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {

                long lessonId = rs.getInt(1);

                return lessonId;
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return 0;
    }


    
        //Lấy ra createby  để chuyuển hướn gnười tới khóa học với lesson đó khí thanh toán thành công
    public long getCreateByByCourseId(int courseId) throws SQLException {

        String sql = """
                     SELECT [CreatedBy]
                         FROM [Project Online Learning].[dbo].[Course]
                       Where [CourseId] = ?
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {

                long createBy = rs.getInt(1);

                return createBy;
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return 0;
    }

    
    
        //Lấy lisst module theo course ID

    public ArrayList<TeachingDTO> getListMentorByCid(int courseId) throws SQLException {
        ArrayList<TeachingDTO> list = new ArrayList<>();
        String sql = """
                        SELECT [MentorId]
                          ,t.[CourseId]
                       	  ,p.Avatar
                       	  ,p.ProfileId
                       	  ,p.FullName
                         FROM [Teaching] t
                          JOIN [dbo].[Course] c ON c.CourseId = t.CourseId
                          JOIN [dbo].[Profile] p ON p.ProfileId = t.MentorId
                          where t.CourseId = ? """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int mentorid = rs.getInt(1);
                int CourseId = rs.getInt(2);
                String avatar = rs.getString(3);
                int ProfileId = rs.getInt(4);
                String mentorName = rs.getString(5);

                list.add(new TeachingDTO(mentorid, CourseId, avatar, ProfileId, mentorName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        LessonDAO dao = new LessonDAO();
        System.out.println(dao.getListMentorByCid(2));
    }
}
