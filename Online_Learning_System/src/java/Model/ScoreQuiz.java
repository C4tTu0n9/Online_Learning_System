/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hatro
 */
public class ScoreQuiz {
    private int accountId;
    private int quizId;
    private float Score;

    public ScoreQuiz() {
    }

    public ScoreQuiz(int accountId, int quizId, float Score) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.Score = Score;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public float getScore() {
        return (float)((int)(Score*100))/100;
    }

    public void setScore(float Score) {
        this.Score = Score;
    }

    @Override
    public String toString() {
        return "ScoreQuiz{" + "accountId=" + accountId + ", quizId=" + quizId + ", Score=" + Score + '}';
    }
    

    
}
