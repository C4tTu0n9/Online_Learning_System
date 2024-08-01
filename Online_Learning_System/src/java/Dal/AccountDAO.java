/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.AccountDTO;

import Model.ProfileDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class AccountDAO extends DBContext {

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
//        System.out.println((dao.getAccountByEmailPass("tuong0505ht@gmail.com", "10101010")).getEmail());
        System.out.println(dao.getAccountIdByEmail("tuong0505ht@gmail.com"));
//
////        dao.insertUser(new AccountDTO("tuongdeptrai@gmail.com", "67676767", 4), new Profile("Pham Cat Tuong", 0));
//        AccountDTO a = dao.getAccountByEmailPass("tuong0505ht@gmail.com", "10101010");

//        System.out.println(a);
        dao.activeOrInactiveAccount(12, 0);
    }

    //tra ve account theo EMAIL & PASS
    public AccountDTO getAccountByEmailPass(String email, String password) {
        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                                                  ,[Email]
                                                  ,[Password]
                                                  ,[Status]
                                                  ,[RoleId],
                       						   p.*
                                              FROM [dbo].[Account] a
                       					   left join Profile p
                       					   on a.AccountId = p.ProfileId
                       					   where Email like ? and Password like ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt(1);
                String email_in_db = resultSet.getString(2);
                String password_in_db = resultSet.getString(3);
                int role_id = resultSet.getInt(5);
                boolean status = resultSet.getBoolean(4);
                String full_name = resultSet.getString("FullName");
                boolean gender = resultSet.getBoolean("Gender");
                String avatar = resultSet.getString("Avatar");
                double money = resultSet.getDouble("Money");
                int managed_by = resultSet.getInt("ManagedBy");
                return new AccountDTO(account_id, full_name, email_in_db, password_in_db, gender, avatar, money, managed_by, status, role_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // login with google
    public AccountDTO getAccountGoogle(String email) {
        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                           ,[Email]
                           ,[Password]
                           ,[Status]
                           ,[RoleId]
                       FROM [dbo].[Account]  where Email like ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt(1);
                String email_in_db = resultSet.getString(2);
                String password_in_db = resultSet.getString(3);
                int role_id = resultSet.getInt(5);
                boolean status = resultSet.getBoolean(4);

                return new AccountDTO(account_id, email_in_db, password_in_db, status, role_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insertUser(AccountDTO account, ProfileDTO profile) {

        insertAccount(account);
        AccountDTO new_insert_account = getAccountByEmailPass(account.getEmail(), account.getPassword());
        insertProfile(profile, new_insert_account.getAccount_id());
    }

    public void insertAccount(AccountDTO account) {
        connection = getConnection();
        String sql_account = """
                             INSERT INTO [dbo].[Account]
                                        ([Email]
                                        ,[Password]
                                        ,[Status]
                                        ,[RoleId])
                                  VALUES
                                        (?,?,1,?)""";
        try {
            statement = connection.prepareStatement(sql_account);
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getRole_id());

            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertProfile(ProfileDTO profile, int account_id) {
        connection = getConnection();
        String sql_profile = """
                             INSERT INTO [dbo].[Profile]
                                        ([ProfileId]
                                        ,[FullName]
                                        ,[Gender]
                                        ,[Avatar]
                                        ,[Money]
                                        ,[ManagedBy])
                                  VALUES
                                        (?,?,null,null,0,?)""";

        try {
            statement = connection.prepareStatement(sql_profile);
            statement.setInt(1, account_id);
            statement.setString(2, profile.getFullname());
            if (profile.getManaged_by() == 0) {
                statement.setString(3, null);
            } else {
                statement.setInt(3, profile.getManaged_by());
            }
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ProfileDTO getProfile(AccountDTO account_login) {

        connection = getConnection();
        String sql = """
                     SELECT [ProfileId]
                           ,[FullName]
                           ,[Gender]
                           ,[Avatar]
                           ,[Money]
                           ,[ManagedBy]
                       FROM [Project Online Learning].[dbo].[Profile]
                       where ProfileId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_login.getAccount_id());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int profile_id = resultSet.getInt(1);
                String fullname = resultSet.getString(2);
                boolean gender = resultSet.getBoolean(3);
                String avt = resultSet.getString(4);
                double money = resultSet.getDouble(5);
                int manageBy = resultSet.getInt(6);
                return new ProfileDTO(profile_id, fullname, gender, avt, money, manageBy);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    public AccountDTO checkAccountIsExit(AccountDTO account) {
//        connection = getConnection();
//        String sql = "SELECT [uid]\n"
//                + "      ,[username]\n"
//                + "      ,[password]\n"
//                + "      ,[email]\n"
//                + "      ,[amount]\n"
//                + "      ,[code]\n"
//                + "      ,[roleid]\n"
//                + "  FROM [dbo].[account]\n"
//                + "  where email like ?";
//        try {
//            statement = connection.prepareStatement(sql);
//            statement.setString(1, account.getEmail());
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int uid = resultSet.getInt(1);
//                String fullname = resultSet.getString(2);
//                String password = resultSet.getString(3);
//                String email = resultSet.getString(4);
//                double amount = resultSet.getDouble(5);
//                String code = resultSet.getString(6);
//                int roleid = resultSet.getInt(7);
//                AccountDTO accountCheckIsExit = new AccountDTO(uid, fullname, password, email, amount, code, roleid);
//                return accountCheckIsExit;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
    public boolean checkAccountExist(String email) {
        connection = getConnection();
        String sql = """
                     select COUNT(AccountId) as NumberOfAccount from Account
                       where Email = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1) != 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void updatePassword(AccountDTO account) {
        connection = getConnection();
        String sql = """
                     UPDATE [dbo].[Account]
                        SET [Password] = ?
                          
                      WHERE Email = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getPassword());
            statement.setString(2, account.getEmail());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updatePassword_ByAccId(String password, int account_id) {
        connection = getConnection();
        String sql = "UPDATE Account\n"
                + "   SET \n"
                + "      [Password] = ?\n"
                + "      \n"
                + " WHERE AccountId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setInt(2, account_id);
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    public void updateEmail_ByAccId(String email, int account_id) {
//        connection = getConnection();
//        String sql = "UPDATE AccountDTO\n"
//                + "   SET \n"
//                + "      [Email] = ?\n"
//                + "      \n"
//                + " WHERE AccountId = ?";
//        try {
//            statement = connection.prepareStatement(sql);
//            statement.setString(1, email);
//            statement.setInt(2, account_id);
//            // thực thi câu lệnh
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    ///Profile
    public void updateFullName_ByAccId(String fullname, int account_id) {
        connection = getConnection();
        String sql = """
                     UPDATE [dbo].[Profile]
                        SET [FullName] =  ?
                      WHERE ProfileId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, fullname);
            statement.setInt(2, account_id);
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateGender_ByAccId(Boolean gender, int account_id) {
        connection = getConnection();
        String sql = """
                     UPDATE [dbo].[Profile]
                     SET [Gender] =  ?
                     WHERE ProfileId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, gender);
            statement.setInt(2, account_id);
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateAvatar_ByAccId(String avt_path_in_server, int account_id) {
        connection = getConnection();
        String sql = """
                      update Profile
                      set Avatar = ?
                      where ProfileId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, avt_path_in_server);
            statement.setInt(2, account_id);
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //===========Admin=================
    //Lấy ra tất cả tài khoản của  mentor
    
     public ArrayList<AccountDTO> getAllMentorAccounts() {
        ArrayList<AccountDTO> list = new ArrayList<>();
        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                       	  ,p.FullName
                             ,[Email]
                             ,[Password]
                             ,[Status]
                             ,[RoleId]
                         FROM [Project Online Learning].[dbo].[Account] acc
                         Join [dbo].[Profile] p on p.[ProfileId] = acc.AccountId
                         Where [RoleId] = 3""";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt(1);
                String fullName = resultSet.getString(2);
                String email_in_db = resultSet.getString(3);
                String password_in_db = resultSet.getString(4);
                boolean status = resultSet.getBoolean(5);
                int role_id = resultSet.getInt(6);

                list.add(new AccountDTO(account_id, fullName, email_in_db, password_in_db, status, role_id));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    public ArrayList<AccountDTO> getMyMentorAccounts(int manager_id) {
        ArrayList<AccountDTO> list = new ArrayList<>();
        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                       	  ,p.FullName
                             ,[Email]
                             ,[Password]
                             ,[Status]
                             ,[RoleId]
                         FROM [Project Online Learning].[dbo].[Account] acc
                         Join [dbo].[Profile] p on p.[ProfileId] = acc.AccountId
                         Where [RoleId] = 3  and p.ManagedBy = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, manager_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt(1);
                String fullName = resultSet.getString(2);
                String email_in_db = resultSet.getString(3);
                String password_in_db = resultSet.getString(4);
                boolean status = resultSet.getBoolean(5);
                int role_id = resultSet.getInt(6);

                list.add(new AccountDTO(account_id, fullName, email_in_db, password_in_db, status, role_id));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    //Lấy ra tất cả tài khoản của  manager
    public ArrayList<AccountDTO> getManagerAccount() {
        ArrayList<AccountDTO> list = new ArrayList<>();

        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                       	  ,p.FullName
                             ,[Email]
                             ,[Password]
                             ,[Status]
                             ,[RoleId]
                         FROM [Project Online Learning].[dbo].[Account] acc
                         Join [dbo].[Profile] p on p.[ProfileId] = acc.AccountId
                         Where [RoleId] = 2 """;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int account_id = resultSet.getInt(1);
                String fullName = resultSet.getString(2);
                String email_in_db = resultSet.getString(3);
                String password_in_db = resultSet.getString(4);
                boolean status = resultSet.getBoolean(5);
                int role_id = resultSet.getInt(6);

                list.add(new AccountDTO(account_id, fullName, email_in_db, password_in_db, status, role_id));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void insertUserByAdmin(AccountDTO account, ProfileDTO profile) {
        insertAccount(account);
        AccountDTO new_insert_account = getAccountByEmailPass(account.getEmail(), account.getPassword());

        insertProfileByAdmin(profile, new_insert_account.getAccount_id());
    }

    public void insertProfileByAdmin(ProfileDTO profile, int account_id) {
        connection = getConnection();
        String sql_profile = """
                             INSERT INTO [dbo].[Profile]
                                        ([ProfileId]
                                        ,[FullName]
                                        ,[Gender]
                                        ,[Avatar]
                                        ,[Money]
                                        ,[ManagedBy])
                                  VALUES
                                        (?,?,?,null,0,?)""";

        try {
            statement = connection.prepareStatement(sql_profile);
            statement.setInt(1, account_id);
            statement.setString(2, profile.getFullname());
            statement.setBoolean(3, profile.isGender());
            if (profile.getManaged_by() == 0) {
                statement.setString(4, null);
            } else {
                statement.setInt(4, profile.getManaged_by());
            }
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //lấy account theo id để admin có thể sửa thông tin tài khoản
    public AccountDTO getAccountById(int accountId) {

        connection = getConnection();
        String sql = """
                     SELECT  [AccountId]
                     		,p.[FullName]
                           ,[Email]
                           ,[Password]
                           ,[Status]
                     	  ,p.Gender
                           ,[RoleId]
                       FROM [Project Online Learning].[dbo].[Account] acc
                       Join [dbo].[Profile] p on acc.[AccountId] = p.[ProfileId]
                       Where acc.[AccountId] = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int acc_id = resultSet.getInt(1);
                String fullname = resultSet.getString(2);
                String email = resultSet.getString(3);
                String pass = resultSet.getString(4);
                boolean status = resultSet.getBoolean(5);
                boolean gender = resultSet.getBoolean(6);
                int role = resultSet.getInt(7);

                return new AccountDTO(acc_id, fullname, email, pass, status, gender, role);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateAccount(AccountDTO account, ProfileDTO profile) {

        updateProfile(profile);
        connection = getConnection();
        String sql = """
                       UPDATE [dbo].[Account]
                              SET [Password] = ?
                      		,RoleId = ?
                             WHERE Email = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getPassword());
            statement.setInt(2, account.getRole_id());
            statement.setString(3, account.getEmail());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateProfile(ProfileDTO profile) {

        connection = getConnection();
        String sql = """
                       UPDATE[dbo].[Profile]
                      
                      		SET [FullName] = ?
                            ,[Gender] =?
                            WHERE [ProfileId] = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, profile.getFullname());
            statement.setBoolean(2, profile.isGender());
            statement.setInt(3, profile.getProfile_id());

            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //kiểm tra xem tài khoản active hay inactive
    public boolean CheckActiveOrInActive(int accountid) {
        connection = getConnection();
        String sql = """
                     SELECT [Status] 
                         FROM [Project Online Learning].[dbo].[Account]
                         WHERE [AccountId] = ?
                     """;
        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, accountid);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                boolean status = resultSet.getBoolean(1);

                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In chi tiết lỗi ra console

        }

        return false;
    }

    //update account inactive nếu tài khoản đó active
    public void activeOrInactiveAccount(int accountid, int status) {
        connection = getConnection();
        String sql = """
                         UPDATE[dbo].[Account]
                                                  
                                      SET [Status] = ? 
                               WHERE [AccountId] = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, status);
            statement.setInt(2, accountid);
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
       //lấy account theo id để admin có thể sửa thông tin tài khoản
    public AccountDTO getAccountIdByEmail(String Email) {

        connection = getConnection();
        String sql = """
                     SELECT [AccountId]
                             ,[Email]
                             ,[Password]
                             ,[Status]
                             ,[RoleId]
                         FROM [Project Online Learning].[dbo].[Account]
                         Where Email = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, Email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int acc_id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String pass = resultSet.getString(3);
                boolean status = resultSet.getBoolean(4);
                int role = resultSet.getInt(5);

                return new AccountDTO(acc_id, email, pass, status, role);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
