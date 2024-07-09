/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hatro
 */
public class UserAnswer {
    private int accountId;
    private int questionId;
    private String answer;
    private boolean isCorrectUserAnswer;
    private int AttemptNumber;

    public UserAnswer() {
    }

    public UserAnswer(int accountId, int questionId, String answer, boolean isCorrectUserAnswer) {
        this.accountId = accountId;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrectUserAnswer = isCorrectUserAnswer;
    }

    public UserAnswer(int accountId, int questionId, String answer, boolean isCorrectUserAnswer, int AttemptNumber) {
        this.accountId = accountId;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrectUserAnswer = isCorrectUserAnswer;
        this.AttemptNumber = AttemptNumber;
    }
    
    

    public UserAnswer(int accountId, int questionId, boolean isCorrectUserAnswer) {
        this.accountId = accountId;
        this.questionId = questionId;
        this.isCorrectUserAnswer = isCorrectUserAnswer;
    }
    
    

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIsCorrectUserAnswer() {
        return isCorrectUserAnswer;
    }

    public void setIsCorrectUserAnswer(boolean isCorrectUserAnswer) {
        this.isCorrectUserAnswer = isCorrectUserAnswer;
    }

    public int getAttemptNumber() {
        return AttemptNumber;
    }

    public void setAttemptNumber(int AttemptNumber) {
        this.AttemptNumber = AttemptNumber;
    }

    @Override
    public String toString() {
        return "UserAnswer{" + "accountId=" + accountId + ", questionId=" + questionId + ", answer=" + answer + ", isCorrectUserAnswer=" + isCorrectUserAnswer + ", AttemptNumber=" + AttemptNumber + '}';
    }
}
