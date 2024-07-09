/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.AccountDTO;
import Model.Course;
import Model.EnrollmentDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
public class EnrollmentDAO extends DBContext {

    public static void main(String[] args) {
        EnrollmentDAO dao = new EnrollmentDAO();
        ArrayList<EnrollmentDTO> list = dao.getCourseByAccId(1);
        System.out.println(list);
    }

    public ArrayList<EnrollmentDTO> getCourseByAccId(int account_id) {
        connection = getConnection();
        ArrayList<EnrollmentDTO> list = new ArrayList<>();
        String sql = """
                     select c.CourseId,c.CourseName,c.Image, p.FullName, e.EnrollmentDate,e.Progress 
                     from Enrollment e
                     join Course c on e.CourseId = c.CourseId 
                     join Profile p on c.CreatedBy = p.ProfileId
                     where e.AccountId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int course_id = resultSet.getInt(1);
                String course_name = resultSet.getString(2);
                String image = resultSet.getString(3);
                String create_by = resultSet.getString(4);
                String enrollment_date = resultSet.getString(5);
                int progress = resultSet.getInt(6);

                list.add(new EnrollmentDTO(account_id,course_id, course_name, image, create_by, enrollment_date, progress));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
