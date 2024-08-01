/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.StarRatingDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class StarRatingDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

    // Chèn rating lên db
    public void insertRating(StarRatingDTO rating) {

        String sql = "  Insert into [CourseRating] \n"
                + "  values(?,?,?,?,?)";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, rating.getStar());
            ps.setString(2, rating.getComment());
            ps.setDate(3, rating.getDatecreate());
            ps.setInt(4, rating.getCourseid());
            ps.setInt(5, rating.getAccountid());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

    }

    public ArrayList<StarRatingDTO> getUserRatings(int accountId, int courseId) {
        ArrayList<StarRatingDTO> list = new ArrayList<>();
        String sql = """
                                SELECT [RatingId]
                                   ,[Star]
                                   ,[Comment]
                                   ,[DateCreated]
                                   ,[CourseId]
                                   ,cr.[AccountId]
                             	  ,p.Avatar
                             	  ,p.FullName
                               FROM [dbo].[CourseRating] cr
                               Join Profile p on p.ProfileId = cr.[AccountId]
                               Where cr.[CourseId] = ? AND AccountId = ?
                     """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setInt(2, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ratingid = rs.getInt(1);
                int star = rs.getInt(2);
                String comment = rs.getString(3);
                Date datecreate = rs.getDate(4);
                int courseid = rs.getInt(5);
                int accountid = rs.getInt(6);  // Nếu giá trị là "IT", dùng getString để tránh lỗi
                String avatar = rs.getString(7);
                String fullname = rs.getString(8);

                list.add(new StarRatingDTO(ratingid, star, comment, datecreate, courseid, accountid, avatar, fullname));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }
}
