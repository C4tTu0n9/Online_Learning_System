/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tuong
 */
public class EnrollmentDTO {
    private int account_id;
    private int course_id;
    private String course_name;
    private String image;
    private String create_by; //full name of manager
    private String enrollment_date;
    private int progress;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(int account_id, int course_id, String course_name, String image, String create_by, String enrollment_date, int progress) {
        this.account_id = account_id;
        this.course_id = course_id;
        this.course_name = course_name;
        this.image = image;
        this.create_by = create_by;
        this.enrollment_date = enrollment_date;
        this.progress = progress;
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

    public String getCourse_name() {
        return course_name;
    }

    public String getImage() {
        return image;
    }

    public String getCreate_by() {
        return create_by;
    }

    public String getEnrollment_date() {
        return enrollment_date;
    }

    public int getProgress() {
        return progress;
    }

    
    
    
}
