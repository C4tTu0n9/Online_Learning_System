/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Certificate {
    private int certificate_id;
    private int course_id;
    private int account_id;
    private int issuer;
    private Date issuer_date;
    private String course_name;
    private String img;
    private String fullname_mentor;
    private String fullname_mentee;

    public Certificate() {
    }

    public Certificate(int certificate_id, int course_id, int account_id, int issuer, Date issuer_date, String course_name, String img, String fullname_mentor, String fullname_mentee) {
        this.certificate_id = certificate_id;
        this.course_id = course_id;
        this.account_id = account_id;
        this.issuer = issuer;
        this.issuer_date = issuer_date;
        this.course_name = course_name;
        this.img = img;
        this.fullname_mentor = fullname_mentor;
        this.fullname_mentee = fullname_mentee;
    }

    public int getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(int certificate_id) {
        this.certificate_id = certificate_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getIssuer() {
        return issuer;
    }

    public void setIssuer(int issuer) {
        this.issuer = issuer;
    }

    public Date getIssuer_date() {
        return issuer_date;
    }

    public void setIssuer_date(Date issuer_date) {
        this.issuer_date = issuer_date;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFullname_mentor() {
        return fullname_mentor;
    }

    public void setFullname_mentor(String fullname_mentor) {
        this.fullname_mentor = fullname_mentor;
    }

    public String getFullname_mentee() {
        return fullname_mentee;
    }

    public void setFullname_mentee(String fullname_mentee) {
        this.fullname_mentee = fullname_mentee;
    }

    @Override
    public String toString() {
        return "Certificate{" + "certificate_id=" + certificate_id + ", course_id=" + course_id + ", account_id=" + account_id + ", issuer=" + issuer + ", issuer_date=" + issuer_date + ", course_name=" + course_name + ", img=" + img + ", fullname_mentor=" + fullname_mentor + ", fullname_mentee=" + fullname_mentee + '}';
    }
    
    
}
