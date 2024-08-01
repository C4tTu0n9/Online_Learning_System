/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.AccountDTO;
import Model.Category;
import Model.Course;
import Model.Payment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class StatisticalDAO extends DBContext{

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

    public static void main(String[] args) {
        StatisticalDAO test = new StatisticalDAO();
//        System.out.println(test.getPaymentPerYear());
//        System.out.println(test.CountAccStillActive());
        try {
            System.out.println(test.getPercentCategory());
        } catch (Exception e) {
        }
    }

    public ArrayList<Payment> getTotalEarningPerMonth(int manager_id) throws SQLException {
        ArrayList<Payment> list = new ArrayList<>();
        String sql = """
                      SELECT 
                                              DATENAME(MONTH, PaymentDate) AS Month,
                                              SUM(Money) AS TotalEarnings
                                          FROM 
                                              [Project Online Learning].[dbo].[Payment] p
                     						 join Course c on p.CourseId = c.CourseId
                                          WHERE 
                                              YEAR(PaymentDate) = 2024 and c.CreatedBy = ?
                                          GROUP BY 
                                              DATENAME(MONTH, PaymentDate),
                                              DATEPART(MONTH, PaymentDate)
                                          ORDER BY 
                                              DATEPART(MONTH, PaymentDate);""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, manager_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString("Month");
                int total_earnings = rs.getInt("TotalEarnings");

                list.add(new Payment(total_earnings, month));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }

    public Payment getPaymentPerMonth() {
        String sql = """
                     SELECT
                         DATENAME(month, DATEADD(month, MONTH(PaymentDate) - 1, CAST('2000-01-01' AS datetime))) AS PaymentMonth,
                         SUM(Money) AS TotalEarnings
                     FROM [Project Online Learning].[dbo].[Payment]
                     WHERE MONTH(PaymentDate) = MONTH(GETDATE())
                     GROUP BY
                        
                         MONTH(PaymentDate)
                     ORDER BY
                         
                         MONTH(PaymentDate);""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString(1);
                int total_earnings = rs.getInt(2);
                return new Payment(total_earnings, month);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;

    }

    public Payment getPaymentPerYear() {
        String sql = """
                     SELECT
                         CONVERT(VARCHAR, YEAR(PaymentDate)) AS PaymentYearString,
                         SUM(Money) AS TotalEarnings
                     FROM [Project Online Learning].[dbo].[Payment]
                     GROUP BY
                         CONVERT(VARCHAR, YEAR(PaymentDate))
                     ORDER BY
                         CONVERT(VARCHAR, YEAR(PaymentDate));""";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String year = rs.getString(1);
                int total_earnings = rs.getInt(2);
                return new Payment(total_earnings, year);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;

    }

    public AccountDTO CountAccStillActive() {
        String sql = "SELECT COUNT(*) AS TotalAccounts\n"
                + "FROM [Project Online Learning].[dbo].[Account]\n"
                + "WHERE [Status] = 1;";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int total_acc = rs.getInt(1);
                return new AccountDTO(total_acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;

    }

    public Course CountCourseStillActive() {
        String sql = "SELECT COUNT(*) AS TotalCourse\n"
                + "FROM [Project Online Learning].[dbo].Course\n"
                + "WHERE [Status] = 1;";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int total_course = rs.getInt(1);
                return new Course(total_course);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return null;

    }

    public ArrayList<Category> getPercentCategory() throws SQLException {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "  DECLARE @TotalCount INT;\n" +
"                SELECT @TotalCount = COUNT(*)\n" +
"                FROM [Project Online Learning].[dbo].[Course];\n" +
"               \n" +
"                SELECT \n" +
"                cc.[CourseCategoryId],\n" +
"                  cc.[CategoryName],\n" +
"                 ISNULL(COUNT(c.[CourseId]), 0) AS CategoryCount,\n" +
"                  ISNULL(ROUND(CAST(COUNT(c.[CourseId]) AS FLOAT) / @TotalCount * 100, 2), 0) AS Percentage\n" +
"               FROM \n" +
"              [Project Online Learning].[dbo].[CourseCategory] cc\n" +
"                LEFT JOIN \n" +
"                  [Project Online Learning].[dbo].[Course] c\n" +
"                ON \n" +
"                  cc.[CourseCategoryId] = c.[CourseCategoryId]\n" +
"               GROUP BY \n" +
"                 cc.[CourseCategoryId], cc.[CategoryName]\n" +
"               ORDER BY \n" +
"                 cc.[CourseCategoryId];";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String category_name = rs.getString("CategoryName");
                String category_id = rs.getString("CourseCategoryId");
                int category_count = rs.getInt("CategoryCount");
                double percent = rs.getDouble("Percentage");
                list.add(new Category(category_id, category_name, category_count, percent));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;
    }
}
