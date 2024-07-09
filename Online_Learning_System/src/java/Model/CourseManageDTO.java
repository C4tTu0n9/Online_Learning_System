/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author tuong
 */
public class CourseManageDTO {
    private int course_id;
    private String course_name;
    private String description;
    private ArrayList<String> mentor_name;
    private String image;
    private float price;
    private float discount;
    private String course_category_id;
    private String create_date;
    private String study_time;
    private boolean status;
    private int number_enrollment;

    public CourseManageDTO() {
    }

    public CourseManageDTO(int course_id, String course_name, String description, ArrayList<String> mentor_name, String image, float price, float discount, String course_category_id) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.description = description;
        this.mentor_name = mentor_name;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.course_category_id = course_category_id;
    }
    
    

    public CourseManageDTO(int course_id, String course_name, String description, String image, float price, float discount, String course_category_id, String create_date, String study_time, boolean status, int number_enrollment) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.course_category_id = course_category_id;
        this.create_date = create_date;
        this.study_time = study_time;
        this.status = status;
        this.number_enrollment = number_enrollment;
    }

    public CourseManageDTO(String course_name, String description, String image, float price, float discount, String course_category_id, String study_time) {

        this.course_name = course_name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.course_category_id = course_category_id;
        this.study_time = study_time;

    }
    
        public CourseManageDTO( String course_name, String description,  String image, float price, float discount, String course_category_id) {

        this.course_name = course_name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.course_category_id = course_category_id;

    }
    
    

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(ArrayList<String> mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getCourse_category_id() {
        return course_category_id;
    }

    public void setCourse_category_id(String course_category_id) {
        this.course_category_id = course_category_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getStudy_time() {
        return study_time;
    }

    public void setStudy_time(String study_time) {
        this.study_time = study_time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNumber_enrollment() {
        return number_enrollment;
    }

    public void setNumber_enrollment(int number_enrollment) {
        this.number_enrollment = number_enrollment;
    }

    
}
