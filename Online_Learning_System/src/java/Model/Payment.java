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
public class Payment {

    private int paymentid;
    private int accountid;
    private int courseid;
    private Date paymentDate;
    private String paymentmethodid;
    private int amount;
    private String paymentDateString;
    private String formattedPrice;

    public Payment() {
    }

    public Payment(int paymentid, int accountid, int courseid, Date paymentDate, String paymentmethodid, int amount) {
        this.paymentid = paymentid;
        this.accountid = accountid;
        this.courseid = courseid;
        this.paymentDate = paymentDate;
        this.paymentmethodid = paymentmethodid;
        this.amount = amount;
    }

    public Payment(int accountid, int courseid, Date paymentDate, String paymentmethodid, int amount) {
        this.accountid = accountid;
        this.courseid = courseid;
        this.paymentDate = paymentDate;
        this.paymentmethodid = paymentmethodid;
        this.amount = amount;
    }

    public Payment(int amount, String paymentDateString) {
        this.amount = amount;
        this.paymentDateString = paymentDateString;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentmethodid() {
        return paymentmethodid;
    }

    public void setPaymentmethodid(String paymentmethodid) {
        this.paymentmethodid = paymentmethodid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentDateString() {
        return paymentDateString;
    }

    public void setPaymentDateString(String paymentDateString) {
        this.paymentDateString = paymentDateString;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    @Override
    public String toString() {
        return "Payment{" + "amount=" + amount + ", paymentDateString=" + paymentDateString + '}';
    }

}
