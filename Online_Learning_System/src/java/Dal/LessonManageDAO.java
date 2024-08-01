/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.LessonDTO;
import Model.ModuleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
//===========CRUD LESSON===========
public class LessonManageDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

    public void InsertLesson(LessonDTO lesson) {
        String sql = """
                       insert into [Lesson] 
                       values(?,?,?,?,?)
                     """;
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Đặt các tham số vào câu lệnh PreparedStatement
            ps.setInt(1, lesson.getModuleid());
            ps.setString(2, lesson.getLessonname());
            ps.setString(3, lesson.getLessoncontent());
            ps.setString(4, lesson.getLessonvideo());
            ps.setLong(5, lesson.getDuration());
            ps.executeUpdate();
            System.out.println("Chèn dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLessonByLessonId(int lessonId) {
        String sql = """
                       DELETE FROM [Lesson]
                       WHERE LessonId = ?
                     """;
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, lessonId);
            ps.executeUpdate();
            System.out.println("Xóa dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //UPDATE LESSON
    public void updateLesson(LessonDTO lesson) {
        String sql = """
                        UPDATE [Lesson]
                         SET [ModuleId] = ?
                             ,[LessonName] = ?
                             ,[LessonContent] = ?
                             ,[LessonVideo] = ?
                             ,[Duration] = ?
                         WHERE LessonId = ?
                     """;
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Đặt các tham số vào câu lệnh PreparedStatement
            ps.setInt(1, lesson.getModuleid());
            ps.setString(2, lesson.getLessonname());
            ps.setString(3, lesson.getLessoncontent());
            ps.setString(4, lesson.getLessonvideo());
            ps.setLong(5, lesson.getDuration());
            ps.setInt(6, lesson.getLessonid());
            ps.executeUpdate();
            System.out.println("Chèn dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Lấy lisst module theo course ID để mentor chọn để tạo thêm lesson
    public ArrayList<ModuleDTO> getListModuleByCid(int courseId) throws SQLException {
        ArrayList<ModuleDTO> list = new ArrayList<>();
        String sql = """
                       SELECT  [ModuleId]
                           ,[ModuleName]
                       FROM [dbo].[Module]
                       where [CourseId] = ?
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int module_id = rs.getInt(1);
                String modulname = rs.getString(2);

                list.add(new ModuleDTO(module_id, modulname));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    //Lấy ra tất cả lesson của course by cid
    public ArrayList<LessonDTO> getListlessonByCid(int courseid) throws SQLException {
        ArrayList<LessonDTO> list = new ArrayList<>();
        String sql = """
				SELECT  
                               [LessonId]
                               ,l.[ModuleId]
                                     ,[LessonName]
                                   ,[LessonContent]
                                ,[LessonVideo]
                                ,[Duration]
                                 ,m.ModuleName
                                ,p.FullName as CreateBy
                                 FROM [Project Online Learning].[dbo].[Lesson] l 
                                join [dbo].[Module] m on m.ModuleId = l.ModuleId
                                Join [dbo].[Course] c on c.CourseId = m.CourseId
                                join [dbo].[Account] a on a.AccountId = c.CreatedBy
                               join [dbo].[Profile] p on p.ProfileId = a.AccountId
                                where  c.CourseId = ?

                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseid);

            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                int moduleid = rs.getInt(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                long duration = rs.getInt(6);
                String moduleName = rs.getString(7);
                String createbyname = rs.getString(8);
                list.add(new LessonDTO(lesson_id, moduleid, lesson_name, lesson_content, lesson_video, duration, moduleName, createbyname));

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    
    //Lấy list lesson theo module id, quan lí lesson
    public ArrayList<LessonDTO> getListlessonByModuleId(String module_id) {
        ArrayList<LessonDTO> list = new ArrayList<>();
        String sql = """
                        SELECT  
                        [LessonId]
                        ,l.[ModuleId]
                        ,[LessonName]
                        ,[LessonContent]
                        ,[LessonVideo]
                        ,[Duration]
                        ,m.ModuleName
                        ,p.FullName as CreateBy
                         FROM [Project Online Learning].[dbo].[Lesson] l 
                         join [dbo].[Module] m on m.ModuleId = l.ModuleId
                         Join [dbo].[Course] c on c.CourseId = m.CourseId
                         join [dbo].[Account] a on a.AccountId = c.CreatedBy
                       join [dbo].[Profile] p on p.ProfileId = a.AccountId
                        where  m.ModuleId = ?

                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, module_id);

            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                int moduleid = rs.getInt(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                long duration = rs.getInt(6);
                String moduleName = rs.getString(7);
                String createbyname = rs.getString(8);

                list.add(new LessonDTO(lesson_id, moduleid, lesson_name, lesson_content, lesson_video, duration, moduleName,createbyname));

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    //Lấy lesoson theo lesson id
    public LessonDTO getlessonByLessonid(int lessonId) throws SQLException {

        String sql = """
                      SELECT  [LessonId]
                             ,[ModuleId]
                             ,[LessonName]
                             ,[LessonContent]
                             ,[LessonVideo]
                             ,[Duration]
                         FROM [dbo].[Lesson]
                     where LessonId = ?
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, lessonId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                int moduleid = rs.getInt(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                long duration = rs.getInt(6);

                return new LessonDTO(lesson_id, moduleid, lesson_name, lesson_content, lesson_video, duration);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;
    }

    public LessonDTO checkLessonExist(String lessonName) throws SQLException {

        String sql = """
                       SELECT [LessonId]
                       ,l.[ModuleId]
                       ,[LessonName]
                       ,[LessonContent]
                       ,[LessonVideo]
                       ,[Duration]
                     , c.CourseId
                       FROM [Project Online Learning].[dbo].[Lesson] l
                     	join [dbo].[Module] m on m.ModuleId = l.ModuleId
                     	join [dbo].[Course] c on m.CourseId = c.CourseId
                        WHERE LOWER(LessonName) = LOWER(?)
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, lessonName);
            rs = ps.executeQuery();
            while (rs.next()) {
                int lesson_id = rs.getInt(1);
                int modulid = rs.getInt(2);
                String lesson_name = rs.getString(3);
                String lesson_content = rs.getString(4);
                String lesson_video = rs.getString(5);
                long duration = rs.getInt(6);
                int courseId = rs.getInt(7);

                return new LessonDTO(lesson_id, modulid, lesson_name, lesson_content, lesson_video, duration, courseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;
    }

    public static void main(String[] args) throws SQLException {
        LessonManageDAO dao = new LessonManageDAO();
        System.out.println(dao.getListlessonByModuleId("2"));

    }
}
