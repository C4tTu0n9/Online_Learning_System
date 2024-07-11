/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Answer;
import Model.ModuleDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuong
 */
public class ModuleDAO extends DBContext {

    //Lấy lisst module theo course ID
    public ArrayList<Model.ModuleDTO> getListModulByCid(String courseId) {
        connection = getConnection();
        ArrayList<Model.ModuleDTO> list = new ArrayList<>();
        String sql = """
                      select * from Module where CourseId = ?
                       order by ModuleNumber asc""";
        try {
            connection = new DBContext().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, courseId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int moduleid = resultSet.getInt("ModuleId");
                String modulename = resultSet.getString("ModuleName");
                int module_number = resultSet.getInt("ModuleNumber");
                list.add(new Model.ModuleDTO(moduleid, modulename, module_number));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertModule(String courseId, ModuleDTO new_module) {
        connection = getConnection();
        String sql = """
                      insert into Module
                      values
                      (?,?,?)""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, new_module.getModulename());
            statement.setString(2, courseId);
            statement.setInt(3, new_module.getModule_number());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateModuleName(ModuleDTO module) {
        connection = getConnection();
        String sql = """
                      update Module
                     set ModuleName = ?
                     where ModuleId = ?
                     """;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, module.getModulename());
            statement.setInt(2, module.getModuleid());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteModule(int module_id) {
        connection = getConnection();
        String sql = """
                     delete AnswerQuestion
                       where QuestionId in
                       (select qt.QuestionId from Question qt 
                       join Quiz qz on qt.QuizId = qz.QuizId
                       join Module m on qz.ModuleId = m.ModuleId
                       where m.ModuleId = ?
                       )
                     
                       delete QuestionChoices
                       where QuestionId in 
                       (select qt.QuestionId from Question qt 
                       join Quiz qz on qt.QuizId = qz.QuizId
                       join Module m on qz.ModuleId = m.ModuleId
                       where m.ModuleId = ?
                       )
                     
                       delete Question
                       where QuizId in (select q.QuizId from Quiz q join Module m  on q.ModuleId = m.ModuleId where m.ModuleId = ?)
                       
                       delete from ScoreQuiz
                       where QuizId in (select q.QuizId from Quiz q join Module m  on q.ModuleId = m.ModuleId where m.ModuleId = ?)
                     
                       delete Quiz
                       where ModuleId = ?
                     
                       delete DiscussionLesson
                       where LessonId in (
                       select l.LessonId from Lesson l
                       join Module m on
                       l.ModuleId = m.ModuleId
                       where m.ModuleId = ?
                       )
                     
                       delete Lesson
                       where ModuleId = ?
                     
                       delete from Module
                       where ModuleId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, module_id);
            statement.setInt(2, module_id);
            statement.setInt(3, module_id);
            statement.setInt(4, module_id);
            statement.setInt(5, module_id);
            statement.setInt(6, module_id);
            statement.setInt(7, module_id);
            statement.setInt(8, module_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ModuleDAO dao = new ModuleDAO();
        ModuleDTO u = dao.FindModuleByModuleId(1);
        System.out.println(u.getModulename());
    }

    // find module by moduleId
    public ModuleDTO FindModuleByModuleId(int midModule) {
        connection = getConnection();
        String sql = "SELECT [ModuleId]\n"
                + "      ,[ModuleName]\n"
                + "      ,[CourseId]\n"
                + "      ,[ModuleNumber]\n"
                + "  FROM [dbo].[Module]\n"
                + "  where ModuleId = ?";
        try {
            connection = new DBContext().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, midModule);
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int moduleid = resultSet.getInt("ModuleId");
                String modulename = resultSet.getString("ModuleName");
                int module_number = resultSet.getInt("ModuleNumber");
                ModuleDTO this_module = new ModuleDTO(moduleid, modulename, module_number);
                return this_module;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ModuleDTO getModuleInserted(String cid, String module_number) {
        connection = getConnection();
        String sql = """
                    SELECT [ModuleId]
                                                  ,[ModuleName]
                                                  ,[CourseId]
                                                  ,[ModuleNumber]
                                              FROM [dbo].[Module]
                                              where CourseId = ? and ModuleNumber = ?""";
        try {
            connection = new DBContext().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, cid);
            statement.setString(2, module_number);
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int moduleid = resultSet.getInt("ModuleId");
                String modulename = resultSet.getString("ModuleName");
                ModuleDTO this_module = new ModuleDTO(moduleid, modulename, Integer.parseInt(module_number));
                return this_module;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
