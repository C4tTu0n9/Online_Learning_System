/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class Enrollment {
    private int enrollmentid;
    private int accountid;
    private int courseid;
    private Date enrollmentdate;
    private int progress;

    public Enrollment() {
    }

    public Enrollment(int enrollmentid, int accountid, int courseid, Date enrollmentdate, int progress) {
        this.enrollmentid = enrollmentid;
        this.accountid = accountid;
        this.courseid = courseid;
        this.enrollmentdate = enrollmentdate;
        this.progress = progress;
    }
    
        public Enrollment( int accountid, int courseid, Date enrollmentdate, int progress) {
        this.accountid = accountid;
        this.courseid = courseid;
        this.enrollmentdate = enrollmentdate;
        this.progress = progress;
    }

    public int getEnrollmentid() {
        return enrollmentid;
    }

    public void setEnrollmentid(int enrollmentid) {
        this.enrollmentid = enrollmentid;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public Date getEnrollmentdate() {
        return enrollmentdate;
    }

    public void setEnrollmentdate(Date enrollmentdate) {
        this.enrollmentdate = enrollmentdate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Enrollment{" + "enrollmentid=" + enrollmentid + ", accountid=" + accountid + ", courseid=" + courseid + ", enrollmentdate=" + enrollmentdate + ", progress=" + progress + '}';
    }
        
        
    
    
}
