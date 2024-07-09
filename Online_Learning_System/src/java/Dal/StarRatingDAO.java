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
}
