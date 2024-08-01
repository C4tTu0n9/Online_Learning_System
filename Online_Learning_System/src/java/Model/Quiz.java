
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Time;
/**
 *
 * @author hatro
 */
public class Quiz {
    private int quizId;
    private int moduleId;
    private int quizNumber;
    private String quizName;
    private Time quizTime;
    private int passScore;
    private int course_id;

    public Quiz() {
    }

    public Quiz(int quizId) {
        this.quizId = quizId;
    }
    

    public Quiz(int quizId, int moduleId, int quizNumber, String quizName, Time quizTime, int passScore) {
        this.quizId = quizId;
        this.moduleId = moduleId;
        this.quizNumber = quizNumber;
        this.quizName = quizName;
        this.quizTime = quizTime;
        this.passScore = passScore;
    }
    
      public Quiz(int quizId, int moduleId, String quizName, Time quizTime, int passScore) {
        this.quizId = quizId;
        this.moduleId = moduleId;
        this.quizName = quizName;
        this.quizTime = quizTime;
        this.passScore = passScore;
    }

    public Quiz(int quizId, int moduleId ,String quizName, Time quizTime, int passScore, int course_id) {
        this.quizId = quizId;
        this.moduleId = moduleId;
        this.quizName = quizName;
        this.quizTime = quizTime;
        this.passScore = passScore;
        this.course_id = course_id;
    }


    

    public Quiz(String quizName, Time quizTime, int passScore) {
        this.quizName = quizName;
        this.quizTime = quizTime;
        this.passScore = passScore;
    }

    public Quiz(int moduleId, String quizName, Time quizTime, int passScore) {
        this.moduleId = moduleId;
        this.quizName = quizName;
        this.quizTime = quizTime;
        this.passScore = passScore;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
    
    

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(int quizNumber) {
        this.quizNumber = quizNumber;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Time getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(Time quizTime) {
        this.quizTime = quizTime;
    }

    public int getPassScore() {
        return passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    @Override
    public String toString() {
        return "Quiz{" + "quizId=" + quizId + ", moduleId=" + moduleId + ", quizNumber=" + quizNumber + ", quizName=" + quizName + ", quizTime=" + quizTime + ", passScore=" + passScore + '}';
    }
}
