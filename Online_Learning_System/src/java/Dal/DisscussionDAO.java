/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.DiscussionLesson;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class DisscussionDAO {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null; // Nhận kết quả trả về

    public void InsertComment(DiscussionLesson lesson) {
        String sql = "INSERT INTO DiscussionLesson (ParentCommentID,[CreatedAt],Comment, AccountId, LessonId) \n" +
"	VALUES (?, ?, ?, ?, ?)";
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
             // Đặt các tham số vào câu lệnh PreparedStatement
            if (lesson.getParentId()!= null) {
                ps.setInt(1, lesson.getParentId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setTimestamp(2, lesson.getCreateAt());
            ps.setString(3, lesson.getComment());
            ps.setInt(4, lesson.getAcccountId());
            ps.setInt(5, lesson.getLessonId());
            ps.executeUpdate();
            System.out.println("Chèn dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteReplyComment(int discussionId) {
        String sql = "DELETE FROM DiscussionLesson WHERE DisscussionId = ?";
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, discussionId);
            ps.executeUpdate();
            System.out.println("Xóa dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
//    Xóa tất cả comment (comment cha)
    public void deleteComent(int discussionId) {
    deleteAllComentReplies(discussionId); // Xóa tất cả các comment reply
    
    String sql = "DELETE FROM DiscussionLesson WHERE DisscussionId = ?";
    try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, discussionId);
        ps.executeUpdate();
        System.out.println("Xóa dữ liệu của comment cha thành công");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
       public void deleteAllComentReplies(int commentId) {
        //xóa tất cả các reply liên quan
        String sql = "	DELETE FROM DiscussionLesson WHERE ParentCommentID = ?";
        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, commentId);
            ps.executeUpdate();
            System.out.println("Xóa dữ liệu thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public ArrayList<DiscussionLesson> getCommentsByLesson(int lessonID) {
        ArrayList<DiscussionLesson> listComment = new ArrayList<>();
        String sql = " SELECT \n"
                + "    d.DisscussionId,\n"
                + "	d.ParentCommentID,\n"
                + "    d.LessonId,\n"
                + "    d.AccountId,\n"
                + "    d.Comment,\n"
                + "    p.FullName,\n"
                + "    p.Avatar,\n"
                + "	d.CreatedAt\n"
                + "	\n"
                + "\n"
                + "FROM \n"
                + "    DiscussionLesson d\n"
                + "INNER JOIN \n"
                + "    Account a ON d.AccountId = a.AccountId\n"
                + "LEFT JOIN \n"
                + "    Profile p ON d.AccountId = p.ProfileId\n"
                + "WHERE d.LessonId = ?\n"
                + "ORDER BY \n"
                + "    CreatedAt DESC;";

        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lessonID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int commentId = rs.getInt(1);
                    Integer parentId = rs.getInt(2);
                    int lessonId = rs.getInt(3);
                    int accid = rs.getInt(4);
                    String comment = rs.getString(5);
                    String fullname = rs.getString(6);
                    String avatar = rs.getString(7);
                    Timestamp createAt = rs.getTimestamp(8);

                    DiscussionLesson comments = new DiscussionLesson(commentId, parentId, accid, lessonId, comment, fullname, avatar, createAt);
                    listComment.add(comments);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listComment;
    }


 
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DisscussionDAO dao = new DisscussionDAO();
         DiscussionLesson comment = new DiscussionLesson();
        comment.setParentId(null); // Đây là comment chính, không phải reply
        comment.setAcccountId(1); // Giả sử AccountId là 1
        comment.setLessonId(1); // Giả sử LessonId là 101
        comment.setComment("This is a test comment");
        comment.setCreateAt(new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại

        // Tạo đối tượng DAO và chèn vào cơ sở dữ liệu
//        dao.InsertComment(comment);
       
            dao.deleteComent(25);
        //    dao.deleteComment(1);
    }
}
