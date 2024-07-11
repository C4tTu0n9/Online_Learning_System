/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.AccountDTO;
import Model.CourseManageDTO;
import Model.ProfileDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
public class ProfileManageDAO extends DBContext {

    public static void main(String[] args) {
        ProfileManageDAO dao = new ProfileManageDAO();
        ArrayList<ProfileDTO> list = dao.getMyListManagedMentorByCouresId(2, "2");

        for (ProfileDTO profileDTO : list) {
            System.out.println(profileDTO.getProfile_id());
        }

    }

    public ArrayList<ProfileDTO> getMyListManagedMentorByCouresId(int manager_account_id, String course_id) {
        connection = getConnection();
        ArrayList<ProfileDTO> list = new ArrayList<>();
        String sql = """
                     select p1.ProfileId,p1.FullName,p1.Gender,p1.Avatar, p1.Money, a.Email, tbl1.CourseId
                     from Profile p1 left join (select * from Profile p
                     join Account a
                     on p.ProfileId = a.AccountId
                     left join Teaching t
                     on a.AccountId = t.MentorId
                     where p.ManagedBy = ? and t.CourseId = ?) tbl1
                     on p1.ProfileId = tbl1.AccountId
                     join Account a on p1.ProfileId = a.AccountId where p1.ManagedBy = ? and a.Status = 1""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, manager_account_id);
            statement.setString(2, course_id);
            statement.setInt(3, manager_account_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int profile_id = resultSet.getInt(1);
                String full_name = resultSet.getString(2);
                String avatar = resultSet.getString(4);
                boolean gender = resultSet.getBoolean(3);
                float money = resultSet.getFloat(5);
                String email = resultSet.getString(6);
                int teaching_course = resultSet.getInt(7);
                list.add(new ProfileDTO(profile_id, full_name, gender, avatar, money, email, teaching_course));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<ProfileDTO> getMyListManagedMentor(int manager_account_id) {
        connection = getConnection();
        ArrayList<ProfileDTO> list = new ArrayList<>();
        String sql = """
                     select p1.ProfileId,p1.FullName,p1.Gender,p1.Avatar, p1.Money, a.Email, tbl1.CourseId
                     from Profile p1 left join (select * from Profile p
                     join Account a
                     on p.ProfileId = a.AccountId
                     left join Teaching t
                     on a.AccountId = t.MentorId
                     where p.ManagedBy = ? and t.CourseId = null) tbl1
                     on p1.ProfileId = tbl1.AccountId
                     join Account a on p1.ProfileId = a.AccountId where p1.ManagedBy = ? and a.Status = 1""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, manager_account_id);
            statement.setInt(2, manager_account_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int profile_id = resultSet.getInt(1);
                String full_name = resultSet.getString(2);
                String avatar = resultSet.getString(4);
                boolean gender = resultSet.getBoolean(3);
                float money = resultSet.getFloat(5);
                String email = resultSet.getString(6);
                int teaching_course = resultSet.getInt(7);
                list.add(new ProfileDTO(profile_id, full_name, gender, avatar, money, email, teaching_course));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
