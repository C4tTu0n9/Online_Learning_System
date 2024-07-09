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

    public void deleteModule(ModuleDTO module) {
        connection = getConnection();
        String sql = """
                      delete Module
                       where ModuleId = ?
                     """;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, module.getModuleid());
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
}
