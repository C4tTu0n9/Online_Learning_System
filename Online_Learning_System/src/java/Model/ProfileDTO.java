/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tuong SELECT TOP (1000) [profile_id] ,[fullname] ,[gender]
 * ,[account_id] ,[avatar] ,[money] FROM
 * [Project_Online_Learning].[dbo].[tbl_profile]
 *
 */
public class ProfileDTO {

    int profile_id;
    String fullname;
    boolean gender;
    String avt;
    double money;
    int managed_by;
    String email;
    int teaching_course;

    public ProfileDTO() {
    }

    public ProfileDTO(int profile_id, String fullname, boolean gender, String avt, double money, int managed_by, int teaching_course) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.gender = gender;
        this.avt = avt;
        this.money = money;
        this.managed_by = managed_by;
        this.teaching_course = teaching_course;
    }

    public ProfileDTO(int profile_id, String fullname, boolean gender, String avt, double money, String email) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.gender = gender;
        this.avt = avt;
        this.money = money;
        this.email = email;
    }

    public ProfileDTO(int profile_id, String fullname, boolean gender, String avt, double money, int managed_by) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.gender = gender;
        this.avt = avt;
        this.money = money;
        this.managed_by = managed_by;
    }

    //insert by admin
    public ProfileDTO(String fullname, boolean gender, double money, int managed_by) {
        this.fullname = fullname;
        this.gender = gender;
        this.money = money;
        this.managed_by = managed_by;
    }

//        update by admin
    public ProfileDTO(int profile_id, String fullname, boolean gender) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.gender = gender;
    }

    public ProfileDTO(int profile_id, String fullname, String avt) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.avt = avt;
    }

    public ProfileDTO(String fullname, int managed_by) {
        this.fullname = fullname;
        this.managed_by = managed_by;
    }

    public ProfileDTO(int profile_id, String fullname, boolean gender, String avt, double money, String email, int teaching_course) {
        this.profile_id = profile_id;
        this.fullname = fullname;
        this.gender = gender;
        this.avt = avt;
        this.money = money;
        this.email = email;
        this.teaching_course = teaching_course;
    }

    public int getTeaching_course() {
        return teaching_course;
    }

    public void setTeaching_course(int teaching_course) {
        this.teaching_course = teaching_course;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

}
