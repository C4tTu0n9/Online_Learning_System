/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class CertificateDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

        public static void main(String[] args) {
            CertificateDAO test = new CertificateDAO();
            try {
                System.out.println(test.getListCertificateByAccountId(1));
            } catch (Exception e) {
            }
        }

        public void insertCertificate() {

            String sql = """
                                    INSERT INTO [Project Online Learning].[dbo].[Certificate] (
                                                  [CourseId]
                                                  ,[AccountId]
                                                  ,[Issuer]
                                                  ,[IssueDate]
                                            )
                                            SELECT 
                                                  e.[CourseId],
                                                  e.[AccountId],
                                                  c.[CreatedBy] AS [Issuer], 
                                                  GETDATE() AS [IssueDate]
                                            FROM [Project Online Learning].[dbo].[Enrollment] e
                                            JOIN [Project Online Learning].[dbo].[Course] c ON e.[CourseId] = c.[CourseId]
                                            JOIN [Project Online Learning].[dbo].[Account] a ON a.AccountId = e.AccountId 
                                            WHERE e.[Progress] = 100
                                              AND NOT EXISTS (
                                                    SELECT 1 
                                                    FROM [Project Online Learning].[dbo].[Certificate] ct
                                                    WHERE ct.[CourseId] = e.[CourseId]
                                                      AND ct.[AccountId] = e.[AccountId]
                                                  );""";
            try (Connection connection = new DBContext().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {        
                statement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }

        public ArrayList<Certificate> getListCertificateByAccountId(int accid) throws SQLException {
            ArrayList<Certificate> list = new ArrayList<>();
            String sql = "  SELECT \n"
                    + "    ct.[CertificateId],\n"
                    + "    ct.[CourseId],\n"
                    + "    ct.[AccountId],p.[FullName] AS [MenteeFullName],\n"
                    + "    ct.[Issuer],\n"
                    + "	issuerProfile.[FullName] AS [IssuerName],\n"
                    + "    ct.[IssueDate],\n"
                    + "    c.[CourseName],\n"
                    + "    c.[Image]\n"
                    + "FROM \n"
                    + "    [Project Online Learning].[dbo].[Certificate] ct\n"
                    + "JOIN \n"
                    + "    [Project Online Learning].[dbo].[Account] a ON a.[AccountId] = ct.[AccountId]\n"
                    + "JOIN \n"
                    + "    [Project Online Learning].[dbo].[Course] c ON c.[CourseId] = ct.[CourseId] AND c.[CreatedBy] = ct.[Issuer]\n"
                    + "JOIN \n"
                    + "    [Project Online Learning].[dbo].[Enrollment] e ON e.[AccountId] = a.[AccountId] AND e.[CourseId] = c.[CourseId]\n"
                    + "JOIN \n"
                    + "    [Project Online Learning].[dbo].[Profile] p ON p.[ProfileId] = a.[AccountId]\n"
                    + "JOIN \n"
                    + "    [Project Online Learning].[dbo].[Profile] issuerProfile ON issuerProfile.[ProfileId] = ct.[Issuer] \n"
                    + "WHERE \n"
                    + "    ct.[AccountId] = ?\n"
                    + "    AND e.[Progress] = 100;";

            try {
                con = new DBContext().getConnection();
                ps = con.prepareStatement(sql);
                ps.setInt(1, accid);
                rs = ps.executeQuery();

                while (rs.next()) {
                    int certificate_id = rs.getInt("CertificateId");
                    int course_id = rs.getInt("CourseId");
                    int account_id = rs.getInt(1);
                    int issuer = rs.getInt("Issuer");
                    Date issuer_date = rs.getDate("IssueDate");
                    String course_name = rs.getString("CourseName");
                    String img = rs.getString("Image");
                    String fullname_mentor = rs.getString("IssuerName");
                    String fullname_mentee = rs.getString("MenteeFullName");
                    list.add(new Certificate(certificate_id, course_id, account_id, issuer, issuer_date, course_name, img, fullname_mentor, fullname_mentee));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi");
            }

            return list;

        }

    public ArrayList<Certificate> getDetailCertificateByAccIdAndCourseId(int accid, int cid) throws SQLException {
        ArrayList<Certificate> list = new ArrayList<>();
        String sql = "   SELECT \n"
                + "    ct.[CertificateId],\n"
                + "    ct.[CourseId],\n"
                + "    ct.[AccountId],p.[FullName] AS [MenteeFullName],\n"
                + "    ct.[Issuer],\n"
                + "	issuerProfile.[FullName] AS [IssuerName],\n"
                + "    ct.[IssueDate],\n"
                + "    c.[CourseName],\n"
                + "    c.[Image]\n"
                + "FROM \n"
                + "    [Project Online Learning].[dbo].[Certificate] ct\n"
                + "JOIN \n"
                + "    [Project Online Learning].[dbo].[Account] a ON a.[AccountId] = ct.[AccountId]\n"
                + "JOIN \n"
                + "    [Project Online Learning].[dbo].[Course] c ON c.[CourseId] = ct.[CourseId] AND c.[CreatedBy] = ct.[Issuer]\n"
                + "JOIN \n"
                + "    [Project Online Learning].[dbo].[Enrollment] e ON e.[AccountId] = a.[AccountId] AND e.[CourseId] = c.[CourseId]\n"
                + "JOIN \n"
                + "    [Project Online Learning].[dbo].[Profile] p ON p.[ProfileId] = a.[AccountId]\n"
                + "JOIN \n"
                + "    [Project Online Learning].[dbo].[Profile] issuerProfile ON issuerProfile.[ProfileId] = ct.[Issuer] \n"
                + "WHERE \n"
                + "    ct.[AccountId] = ? AND ct.CourseId = ?\n"
                + "    AND e.[Progress] = 100;";

        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, accid);
            ps.setInt(2, cid);
            rs = ps.executeQuery();

            while (rs.next()) {
                int certificate_id = rs.getInt("CertificateId");
                int course_id = rs.getInt("CourseId");
                int account_id = rs.getInt(1);
                int issuer = rs.getInt("Issuer");
                Date issuer_date = rs.getDate("IssueDate");
                String course_name = rs.getString("CourseName");
                String img = rs.getString("Image");
                String fullname_mentor = rs.getString("IssuerName");
                String fullname_mentee = rs.getString("MenteeFullName");
                list.add(new Certificate(certificate_id, course_id, account_id, issuer, issuer_date, course_name, img, fullname_mentor, fullname_mentee));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }

        return list;

    }
}
