/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hatro
 */
public class Questions {
    private int questionId;
    private int questionNum;
    private int quizId;
    private String questionName;
    private boolean type;

    public Questions() {
    }

    public Questions(int questionId, int questionNum, int quizId, String questionName, boolean type) {
        this.questionId = questionId;
        this.questionNum = questionNum;
        this.quizId = quizId;
        this.questionName = questionName;
        this.type = type;
    }

    public Questions(int questionNum, int quizId, String questionName, boolean type) {
        this.questionNum = questionNum;
        this.quizId = quizId;
        this.questionName = questionName;
        this.type = type;
    }
    
    

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

   
    
    @Override
    public String toString() {
        return "Questions{" + "questionId=" + questionId + ", questionNum=" + questionNum + ", quizId=" + quizId + ", questionName=" + questionName + ", type=" + type + '}';
    }

   
    
    
}
