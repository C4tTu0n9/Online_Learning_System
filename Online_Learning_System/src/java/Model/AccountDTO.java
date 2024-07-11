/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * SELECT [account_id] ,[email] ,[password] ,[role_id] ,[is_active]
 * ,[managed_by] FROM [dbo].[tbl_account]
 *
 */
public class AccountDTO {

    private int account_id;
    private String fullName;
    private String email;
    private String password;
    private boolean gender;
    String avt;
    double money;
    int managed_by;
    int teaching_course;
    private boolean status;
    //1 - active 
    //2 - inactive
    private int role_id;

    /*
     role_id = 1 =>ADMIN
                2 => Manager
                3 => Mentor
                4 => Mentee
     */
    public AccountDTO() {
    }

    public AccountDTO(String email) {
        this.email = email;
    }

    public AccountDTO(int account_id, String fullName, String email, String password, boolean status, int role_id) {
        this.account_id = account_id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role_id = role_id;
    }

    public AccountDTO(int account_id, String fullName, String email, String password, boolean status, boolean gender, int role_id) {
        this.account_id = account_id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.gender = gender;
        this.role_id = role_id;
    }

    public AccountDTO(int account_id) {
        this.account_id = account_id;
    }

    public AccountDTO(int account_id, String email, String password, boolean status, int role_id) {
        this.account_id = account_id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role_id = role_id;
    }

    public AccountDTO(String email, String password, int role_id) {
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public AccountDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountDTO(int account_id, String fullName, String email, String password, boolean gender, String avt, double money, int managed_by, boolean status, int role_id) {
        this.account_id = account_id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avt = avt;
        this.money = money;
        this.managed_by = managed_by;
        this.teaching_course = teaching_course;
        this.status = status;
        this.role_id = role_id;
    }
    
    

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRoleName() {
        String role = "";
        switch (getRole_id()) {
            case 1:
                role = "Admin";
                break;
            case 2:
                role = "Manager";
                break;
            case 3:
                role = "Mentor";
                break;
            default:
                role = "Mentee";
                break;
        }
        return role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getManaged_by() {
        return managed_by;
    }

    public void setManaged_by(int managed_by) {
        this.managed_by = managed_by;
    }

    public int getTeaching_course() {
        return teaching_course;
    }

    public void setTeaching_course(int teaching_course) {
        this.teaching_course = teaching_course;
    }

    @Override
    public String toString() {
        return "AccountDTO{" + "account_id=" + account_id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + ", status=" + status + ", gender=" + gender + ", role_id=" + role_id + '}';
    }

}
