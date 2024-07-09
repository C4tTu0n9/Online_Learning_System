/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.EnrollmentDTO;
import Model.WishlistDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
public class WishlistDAO extends DBContext {

    public static void main(String[] args) {
        WishlistDAO dao = new WishlistDAO();
        ArrayList<WishlistDTO> list = dao.getWishListByAccId(2);
        System.out.println(dao.getCidFromWishListByAccId(1));
    }

    public void insetWishList(int account_id, int course_id) {
        connection = getConnection();
        String sql = """
                     INSERT INTO [dbo].[WishList] 
                                  ([AccountId],
                            [CourseId])
                            VALUES
                                  (?
                                  ,?)""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            statement.setInt(2, course_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    
    
     public ArrayList<WishlistDTO> getCidFromWishListByAccId(int account_id) {
        connection = getConnection();
        ArrayList<WishlistDTO> list = new ArrayList<>();
        String sql = """
                     SELECT  [CourseId]
                         FROM [dbo].[WishList]
                         Where AccountId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) { 
                int course_id = resultSet.getInt(1);
 
                list.add(new WishlistDTO(course_id));
            }
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    
    public ArrayList<WishlistDTO> getWishListByAccId(int account_id) {
        connection = getConnection();
        ArrayList<WishlistDTO> list = new ArrayList<>();
        String sql = """
                     select tbl1.*, tbl2.[Avg Star] from
                       (select c.CourseId,c.CourseName,c.Image,c.Price,c.Discount,p.FullName
                       from WishList wl
                       join Course c on wl.CourseId = c.CourseId
                       join Profile p on c.CreatedBy = p.ProfileId
                       where wl.AccountId = ?) tbl1
                       left join (select cr.CourseId,SUM(CAST(Star AS FLOAT))/COUNT(RatingId) as [Avg Star] from CourseRating cr
                       group by cr.CourseId) tbl2
                       on tbl1.CourseId = tbl2.CourseId""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int course_id = resultSet.getInt(1);
                String course_name = resultSet.getString(2);
                String image = resultSet.getString(3);
                float price = resultSet.getFloat(4);
                float discount = resultSet.getFloat(5);
                String create_by = resultSet.getString(6);
                float star = resultSet.getFloat(7);

                list.add(new WishlistDTO(course_id, course_name, image, price, discount, create_by, star));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
