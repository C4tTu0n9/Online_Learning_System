package Dal;


import Model.AccountDTO;
import Model.Messenger;

import Model.ProfileDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessengerDAO extends DBContext {

    Connection con = null; // Kết nối với sql server
    PreparedStatement ps = null; // Ném câu lệnh query sang sql server
    ResultSet rs = null;

    public static void main(String[] args) {
        MessengerDAO test = new MessengerDAO();
//        test.insertNewMessenger(new Messenger(1, 3, "test1"));
System.out.println(test.checkFirstConversation(1, 2));
//        try {);
//            
////            System.out.println(test.getUsersWhoMessaged(3));
////            System.out.println(test.getListMessengerBetween2User(1, 3));
//        } catch (SQLException ex) {
//            Logger.getLogger(MessengerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public ProfileDTO getProfileById(int pid) {
        String sql = """
                SELECT p.ProfileId,p.Avatar,p.FullName
                                                       FROM [Project Online Learning].[dbo].[Profile] p
                                					      JOIN Account a ON a.AccountId = p.ProfileId
                                                       WHERE p.ProfileId = ?
                """;
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            rs = ps.executeQuery();

            if (rs.next()) {
                int profile_id = rs.getInt(1);
                String avt = rs.getString(2);
                String full_name = rs.getString(3);
                return new ProfileDTO(profile_id, full_name, avt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
// Gửi Tin Nhắn Mới

    public void insertNewMessenger(Messenger mess) {
        String sql = """
            INSERT INTO [Project Online Learning].[dbo].[Message] ([SenderId], [ReceiverId], [MessageText], [MessageTime])
            VALUES (?, ?, ?, CURRENT_TIMESTAMP);
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mess.getSender_id());
            statement.setInt(2, mess.getReceiver_id());
            statement.setString(3, mess.getMessage_text());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    //Lấy Danh Sách Tin Nhắn Giữa Hai Người Dùng
    public ArrayList<Messenger> getListMessengerBetween2User(int sender, int receiver) throws SQLException {
        ArrayList<Messenger> list = new ArrayList<>();
        String sql = """
            SELECT m.[MessageId], m.[SenderId], m.[ReceiverId], m.[MessageText], m.[MessageTime],p.Avatar
                        FROM [Project Online Learning].[dbo].[Message] m
            			JOIN Profile p ON p.ProfileId = m.SenderId 
                                			JOIN Account a ON a.AccountId = p.ProfileId
                        WHERE ([SenderId] = ? AND [ReceiverId] = ?)
                           OR ([SenderId] = ? AND [ReceiverId] = ?)
                        ORDER BY [MessageTime] ASC;
        """;

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sender);
            ps.setInt(2, receiver);
            ps.setInt(3, receiver);
            ps.setInt(4, sender);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int mess_id = rs.getInt(1);
                    int sender_id = rs.getInt(2);
                    int receiver_id = rs.getInt(3);
                    String mess_text = rs.getString(4);
                    Timestamp mess_time = rs.getTimestamp(5);
                    String avt = rs.getString(6);

                    list.add(new Messenger(mess_id, sender_id, receiver_id, mess_text, mess_time, avt));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }
        return list;
    }

    public ArrayList<Messenger> getUsersWhoReceived(int pid) throws SQLException {
        ArrayList<Messenger> list = new ArrayList<>();
        String sql = """
          SELECT   
                                            m.[ReceiverId],
          								  m.SenderId,
                                            p.[FullName],
                                            p.[Avatar]
                                        FROM 
                                            [Project Online Learning].[dbo].[Message] m
                                        JOIN 
                                           [Project Online Learning].[dbo].[Profile] p ON p.ProfileId = m.ReceiverId
                                            JOIN Account a ON a.AccountId = p.ProfileId
                                        WHERE 
                                            m.SenderId = ?
                                        GROUP BY                   
                                            m.[ReceiverId],
          								  m.SenderId,
                                            p.[FullName],
                                            p.[Avatar];
    """;

        try {
            Connection con = new DBContext().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int sender_id = rs.getInt("SenderId");
                int receiver_id = rs.getInt("ReceiverId");
                String avt = rs.getString("Avatar");
                String fullname = rs.getString("FullName");
                list.add(new Messenger(sender_id, receiver_id, fullname, avt));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }
        return list;
    }

    public ArrayList<Messenger> getUsersWhoSent(int pid) throws SQLException {
        ArrayList<Messenger> list = new ArrayList<>();
        String sql = """
          SELECT                     m.[ReceiverId],
                                      m.SenderId,
                                    p.[FullName],
                                      p.[Avatar]
                                       FROM 
                                          [Project Online Learning].[dbo].[Message] m
                                      JOIN 
                                       [Project Online Learning].[dbo].[Profile] p ON p.ProfileId = m.SenderId
                                      JOIN Account a ON a.AccountId = p.ProfileId
                                       WHERE 
                                          m.ReceiverId = ?
                                          GROUP BY 
                                            m.[ReceiverId],
                                           m.SenderId,
                                            p.[FullName],
                                             p.[Avatar];
                                  	
    """;

        try {
            Connection con = new DBContext().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int sender_id = rs.getInt("SenderId");
                int receiver_id = rs.getInt("ReceiverId");
                String avt = rs.getString("Avatar");
                String fullname = rs.getString("FullName");
                list.add(new Messenger(sender_id, receiver_id, fullname, avt));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
        }
        return list;
    }

    public boolean checkFirstConversation(int sender, int receiver) {
        connection = getConnection();
        String sql = """
                    SELECT CASE
                              WHEN COUNT(*) > 0 THEN 'FALSE' 
                              ELSE 'TRUE' 
                          END AS IsFirstConversation
                   FROM [Project Online Learning].[dbo].[Message]
                   WHERE (SenderId = ? AND ReceiverId = ?)
                      OR (SenderId = ? AND ReceiverId = ?);
                 """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sender);
            ps.setInt(2, receiver);
            ps.setInt(3, receiver);
            ps.setInt(4, sender);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("IsFirstConversation").equals("TRUE");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

   

}
