/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class Answer {
    private int questionId;
    private String choices;
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(int questionId, String choices, boolean isCorrect) {
        this.questionId = questionId;
        this.choices = choices;
        this.isCorrect = isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Answer{" + "questionId=" + questionId + ", choices=" + choices + ", isCorrect=" + isCorrect + '}';
    }

    
   
}
