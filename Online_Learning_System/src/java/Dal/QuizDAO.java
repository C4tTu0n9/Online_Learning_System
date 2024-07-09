/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.AccountDTO;
import Model.Answer;
import Model.Category;
import Model.Course;
import Model.Modules;
import Model.Questions;
import Model.Quiz;
import Model.ScoreQuiz;
import Model.UserAnswer;
import java.beans.Statement;
import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatro
 */
public class QuizDAO extends DBContext {

    public Quiz insertQuiz(Quiz quiz) {
        connection = getConnection();
        if (connection == null) {
            System.out.println("Failed to make connection!");
            return null;
        }
        String sql = "INSERT INTO [dbo].[Quiz] ([QuizName], [QuizTime], [PassScore], [ModuleId]) VALUES (?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, quiz.getQuizName());
            statement.setTime(2, quiz.getQuizTime());
            statement.setInt(3, quiz.getPassScore());
            statement.setInt(4, quiz.getModuleId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    quiz.setQuizId(rs.getInt(1));
                }
                rs.close();
            }

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quiz;
    }

    // edit quiz by quizId
    public void editQuizByQuizId(Quiz quiz) {
        connection = getConnection();
        String sql = "UPDATE [dbo].[Quiz]\n"
                + "   SET [ModuleId] = ?\n"
                + "      ,[QuizName] = ?\n"
                + "      ,[QuizTime] = ?\n"
                + "      ,[PassScore] = ?\n"
                + " WHERE QuizId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quiz.getModuleId());
            statement.setString(2, quiz.getQuizName());
            statement.setTime(3, quiz.getQuizTime());
            statement.setInt(4, quiz.getPassScore());
            statement.setInt(5, quiz.getQuizId());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

//===================================================================CRUD QUESTION==============================================================================
    public Questions insertQuestions(Questions questions) {
        connection = getConnection();
        if (connection == null) {
            System.out.println("Failed to make connection!");
            return null;
        }
        String sql = "INSERT INTO [dbo].[Question] ([QuestionNum], [QuizId], [QuestionName], [Type]) VALUES (?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, questions.getQuestionNum());
            statement.setInt(2, questions.getQuizId());
            statement.setString(3, questions.getQuestionName());
            statement.setBoolean(4, questions.isType());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    questions.setQuestionId(rs.getInt(1));
                }
                rs.close();
            }

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    public void insertAnswers(List<Answer> answers) {
        connection = getConnection();
        String sql = "INSERT INTO [dbo].[QuestionChoices] ([QuestionId], [Choices], [IsCorrect]) VALUES (?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);

            for (Answer answer : answers) {
                statement.setInt(1, answer.getQuestionId());
                statement.setString(2, answer.getChoices());
                statement.setBoolean(3, answer.isIsCorrect());
                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // select questionId where question have one or more than 2 correct question
    public void updateTypeQuestion(Questions questions1) {
        connection = getConnection();
        String sql = """
                     select * from (SELECT QuestionId, COUNT(*) AS CorrectCount
                     FROM QuestionChoices
                     WHERE isCorrect = 1
                     GROUP BY QuestionId) tbl1 where tbl1.QuestionId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions1.getQuestionId());
            resultSet = statement.executeQuery();
            int is_correct = 0;
            while (resultSet.next()) {
                is_correct = resultSet.getInt(2);
            }
            if (is_correct > 1) {
                updatetypeQuestByQuestionId(questions1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update type question by question id select
    public void updatetypeQuestByQuestionId(Questions questions1) {
        connection = getConnection();
        String sql = "update Question\n"
                + "set Type = 1 where QuestionId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions1.getQuestionId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // DELETEQUESTION BY QUESTION ID
    public void deleteQuestionById(int questionId, int quizId) {
        connection = getConnection();
        String sql = "BEGIN TRANSACTION;\n"
                + "\n"
                + "\n"
                + "DELETE FROM QuestionChoices\n"
                + "WHERE QuestionId IN (\n"
                + "    SELECT QuestionId FROM Question\n"
                + "    WHERE QuestionId = ?\n"
                + ");\n"
                + "\n"
                + "\n"
                + "DELETE FROM Question\n"
                + "WHERE QuizId = ? and QuestionId =?;\n"
                + "\n"
                + "\n"
                + "COMMIT TRANSACTION;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questionId);
            statement.setInt(2, quizId);
            statement.setInt(3, questionId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // EDIT QUESTION 
    public Questions editQuestionsById(Questions questions, int idQuestionEdit) {
        connection = getConnection();
        if (connection == null) {
            System.out.println("Failed to make connection!");
            return null;
        }
        String sql = "UPDATE [dbo].[Question]\n"
                + "   SET [QuestionNum] = ?\n"
                + "      ,[QuizId] = ?\n"
                + "      ,[QuestionName] = ?\n"
                + "      ,[Type] = ?\n"
                + " WHERE [QuestionId] = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions.getQuestionNum());
            statement.setInt(2, questions.getQuizId());
            statement.setString(3, questions.getQuestionName());
            statement.setBoolean(4, questions.isType());
            statement.setInt(5, idQuestionEdit);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                questions.setQuestionId(idQuestionEdit); // Đặt lại questionId sau khi cập nhật
            }

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    public void editAnswers(ArrayList<Answer> answers) {
        connection = getConnection();
        deleteAnswers(answers);
        String sql = "insert into QuestionChoices\n"
                + "  values(?,?,?)";
        try {
            statement = connection.prepareStatement(sql);

            for (Answer answer : answers) {
                statement.setInt(1, answer.getQuestionId());
                statement.setString(2, answer.getChoices());
                statement.setBoolean(3, answer.isIsCorrect());
                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAnswers(ArrayList<Answer> answers) {
        connection = getConnection();
        String sql_Delete = "delete from [dbo].[QuestionChoices]"
                + "where [QuestionId] = ?";
        try {
            statement = connection.prepareStatement(sql_Delete);
            statement.setInt(1, answers.get(0).getQuestionId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //==========================================================================================GET LIST =============================================================================
    public ArrayList<Questions> getListQuestions(Questions questions) {
        ArrayList<Questions> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Question] q\n"
                + "  join Quiz qz on q.QuizId = qz.QuizId\n"
                + "  where q.QuizId = ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions.getQuizId());
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                int questionNum = resultSet.getInt("questionNum");
                int quizId = resultSet.getInt("quizId");
                String questionName = resultSet.getString("questionName");
                Boolean type = resultSet.getBoolean("type");
                Questions question = new Questions(questionId, questionNum, quizId, questionName, type);
                listFound.add(question);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    public ArrayList<Answer> getListAnswers(Questions questions) {
        ArrayList<Answer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "SELECT [QuestionId]\n"
                + "      ,[Choices]\n"
                + "      ,[IsCorrect]\n"
                + "  FROM [dbo].[QuestionChoices]";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String choices = resultSet.getString("choices");
                Boolean isCorrect = resultSet.getBoolean("isCorrect");

                Answer answer = new Answer(questionId, choices, isCorrect);
                listFound.add(answer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;

    }

    public ArrayList<Answer> getListAnswersCorrectByQuestionId(Questions questions) {
        ArrayList<Answer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = """
                     SELECT [QuestionId]
                           ,[Choices]
                           ,[IsCorrect]
                       FROM [dbo].[QuestionChoices] where QuestionId = ? and IsCorrect =  1""";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions.getQuestionId());
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String choices = resultSet.getString("choices");
                Boolean isCorrect = resultSet.getBoolean("isCorrect");

                Answer answer = new Answer(questionId, choices, isCorrect);
                listFound.add(answer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;

    }

    // get list question by module id
    public ArrayList<Questions> getListQuestionsByModuleId(int moduleId) {
        ArrayList<Questions> listFound = new ArrayList<>();
        connection = getConnection();
        String sql = "select * \n"
                + "from Question que\n"
                + "join Quiz qui on que.QuizId = qui.QuizId \n"
                + "where qui.ModuleId = ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, moduleId);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                int questionNum = resultSet.getInt("questionNum");
                int quizId = resultSet.getInt("quizId");
                String questionName = resultSet.getString("questionName");
                Boolean type = resultSet.getBoolean("type");
                Questions question = new Questions(questionId, questionNum, quizId, questionName, type);
                listFound.add(question);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    // get list Answer by module id
    public ArrayList<Answer> getlistAnswerByModuleId(int moduleId) {
        ArrayList<Answer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select qc.QuestionId, qc.Choices, qc.IsCorrect, que.QuestionNum, que.QuestionName, qi.QuizId, que.Type, qi.ModuleId\n"
                + "from QuestionChoices qc\n"
                + "join Question que on qc.QuestionId = que.QuestionId\n"
                + "join Quiz qi on que.QuizId = qi.QuizId \n"
                + "where qi.ModuleId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, moduleId);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String choices = resultSet.getString("choices");
                Boolean isCorrect = resultSet.getBoolean("isCorrect");

                Answer answer = new Answer(questionId, choices, isCorrect);
                listFound.add(answer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    // find quiz by module id dùng để hiển thị cho người dùng là quiz
    public Quiz findQuizByModuleId(int moduleIdSelect) {
        connection = getConnection();

        String sql = "select *\n"
                + "from Quiz q \n"
                + "join Module m on  q.ModuleId = m.ModuleId\n"
                + "where m.ModuleId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, moduleIdSelect);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizId = resultSet.getInt(1);
                int moduleId = resultSet.getInt(2);
                String quizName = resultSet.getString(3);
                Time quizTime = resultSet.getTime(4);
                int passScore = resultSet.getInt(5);

                Quiz quiz = new Quiz(quizId, moduleId, quizName, quizTime, passScore);
                return quiz;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<Quiz> getListQuizByModuleId(int moduleId) {
        ArrayList<Quiz> quiz_list = new ArrayList<>();
        connection = getConnection();

        String sql = """
                     select *
                     from Quiz q 
                     join Module m on  q.ModuleId = m.ModuleId
                     where m.ModuleId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, moduleId);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizId = resultSet.getInt(1);
                String quizName = resultSet.getString(3);
                Time quizTime = resultSet.getTime(4);
                int passScore = resultSet.getInt(5);

                Quiz quiz = new Quiz(quizId, moduleId, quizName, quizTime, passScore);
                quiz_list.add(quiz);
            }
            return quiz_list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // lấy ra danh sách câu trả lời đúng của câu hỏi dựa trên moduleId
    public ArrayList<Answer> getlistAnswerCorrectByModuleId(int moduleId) {
        ArrayList<Answer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select qc.QuestionId, qc.Choices, qc.IsCorrect, que.QuestionNum, que.QuestionName, qi.QuizId, que.Type, qi.ModuleId\n"
                + "from QuestionChoices qc\n"
                + "join Question que on qc.QuestionId = que.QuestionId\n"
                + "join Quiz qi on que.QuizId = qi.QuizId \n"
                + "where qi.ModuleId = ? and qc.IsCorrect = 1;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, moduleId);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String choices = resultSet.getString("choices");
                Boolean isCorrect = resultSet.getBoolean("isCorrect");

                Answer answer = new Answer(questionId, choices, isCorrect);
                listFound.add(answer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    // Map chứa id câu hỏi và câu trả lời đúng dùng để insert
    public Map<String, String[]> getListQuestionAnswer(int moduleId) {
        Map<String, String[]> new_map = new HashMap<String, String[]>();
        ArrayList<Questions> list_question = getListQuestionsByModuleId(moduleId);
        for (int i = 0; i < list_question.size(); i++) {
            ArrayList<Answer> ans = getListAnswersCorrectByQuestionId(list_question.get(i));
            String[] anw_string = new String[ans.size()];
            for (int j = 0; j < ans.size(); j++) {
                anw_string[j] = ans.get(j).getChoices().toString();
            }
            new_map.put(String.valueOf(list_question.get(i).getQuestionId()), anw_string);
        }
        return new_map;
    }

    // Map chứa id câu hỏi và câu trả lời đúng
    public Map<Integer, String[]> getListQuestionAnswerQuizSubmit(int moduleId) {
        // tạo map để lưu trữ câu hỏi và câu trả lời 
        Map<Integer, String[]> new_map = new HashMap<>();
        // lấy ra list câu hỏi dựa trên moduleId
        ArrayList<Questions> list_question = getListQuestionsByModuleId(moduleId);
        // duyệt qua từng phần tử trong danh sách question
        for (Questions question : list_question) {
            // lấy ra danh sách câu trả lời đúng
            ArrayList<Answer> ans = getListAnswersCorrectByQuestionId(question);
            // tạo mảng để lưu trữ câu trả lời dưới dạng chuỗi
            String[] anw_string = new String[ans.size()];
            // duyệt qua từng phần tử trong list ans và chuyển nó thành chuỗi
            for (int j = 0; j < ans.size(); j++) {
                anw_string[j] = ans.get(j).getChoices().toString();
            }
            // đưa câu hỏi và mảng câu trả lời vào map
            new_map.put(question.getQuestionId(), anw_string);
        }
        return new_map;
    }

    // Lấy list quiz theo cid để hiện thị lên người dùng
    public ArrayList<Quiz> findListQuizByCourseId(int courseid) {
        ArrayList<Quiz> list = new ArrayList<>();
        connection = getConnection();

        String sql = """
                   SELECT [QuizId]
                           ,m.[ModuleId]
                           ,[QuizName]
                           ,[QuizTime]
                           ,[PassScore]
                       FROM [dbo].[Quiz] q
                       Join [dbo].[Module] m on m.ModuleId = q.ModuleId
                       join Course c on c.CourseId = m.CourseId
                       Where c.CourseId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, courseid);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizId = resultSet.getInt(1);
                int moduleId = resultSet.getInt(2);
                String quizName = resultSet.getString(3);
                Time quizTime = resultSet.getTime(4);
                int passScore = resultSet.getInt(5);

                list.add(new Quiz(quizId, moduleId, quizName, quizTime, passScore));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<Questions> getListQuestionsByModlueId(Questions questions, int midModule) {
        ArrayList<Questions> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = """
                     select *
                     from Question qu
                     join Quiz qz on qu.QuizId = qz.QuizId
                     join Module m on qz.ModuleId = m.ModuleId
                     where qu.QuizId = ? and  m.ModuleId = ?""";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, questions.getQuizId());
            statement.setInt(2, midModule);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                int questionNum = resultSet.getInt("questionNum");
                int quizId = resultSet.getInt("quizId");
                String questionName = resultSet.getString("questionName");
                Boolean type = resultSet.getBoolean("type");
                Questions question = new Questions(questionId, questionNum, quizId, questionName, type);
                listFound.add(question);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    // =================================== Edit Quiz++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // hàm này dùng để lấy ra quiz cần edit
    public Quiz GetQuizByQuizId(int quizIdSelect) {
        connection = getConnection();
        String sql = """
                     select *
                     from Quiz
                     where QuizId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quizIdSelect);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizId = resultSet.getInt(1);
                int moduleId = resultSet.getInt(2);
                String quizName = resultSet.getString(3);
                Time quizTime = resultSet.getTime(4);
                int passScore = resultSet.getInt(5);

                Quiz quiz = new Quiz(quizId, moduleId, quizName, quizTime, passScore);
                return quiz;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // hàm này dùng để lấy ra module của quiz cần edit
    public Modules GetModulebyQuizId(int quizIdSelect) {
        connection = getConnection();
        String sql = "select *\n"
                + "from Module m\n"
                + "join Quiz q on q.ModuleId = m.ModuleId\n"
                + "where q.QuizId = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quizIdSelect);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int moduleid = resultSet.getInt(1);
                String modulename = resultSet.getString(2);
                int courseid = resultSet.getInt(3);
                Modules module = new Modules(moduleid, modulename, courseid);
                return module;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // láy ra danh sách question để edit quiz
    public ArrayList<Questions> getListQuestionsByQuizId(int quizIdSelect) {
        ArrayList<Questions> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Question] q\n"
                + "  join Quiz qz on q.QuizId = qz.QuizId\n"
                + "  where q.QuizId = ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quizIdSelect);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                int questionNum = resultSet.getInt("questionNum");
                int quizId = resultSet.getInt("quizId");
                String questionName = resultSet.getString("questionName");
                Boolean type = resultSet.getBoolean("type");
                Questions question = new Questions(questionId, questionNum, quizId, questionName, type);
                listFound.add(question);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    // lấy ra danh sách answer để edit quiz
    public ArrayList<Answer> getListAnswers() {
        ArrayList<Answer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "SELECT [QuestionId]\n"
                + "      ,[Choices]\n"
                + "      ,[IsCorrect]\n"
                + "  FROM [dbo].[QuestionChoices]";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String choices = resultSet.getString("choices");
                Boolean isCorrect = resultSet.getBoolean("isCorrect");

                Answer answer = new Answer(questionId, choices, isCorrect);
                listFound.add(answer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;

    }

    //===================================================== DO QUIZ======================================================================
    // lấy ra điểm mới nhất của người dùng
    public ScoreQuiz findScoreDoQuizByAccountIdAndQuizId(int account_id, int quizIdSelect) {
        connection = getConnection();
        String sql = """
                     SELECT TOP 1 * 
                     FROM ScoreQuiz 
                     WHERE AccountId = ? AND QuizId = ? 
                     ORDER BY ScoreQuizId DESC""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            statement.setInt(2, quizIdSelect);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("accountId");
                int quizId = resultSet.getInt("quizId");
                float Score = resultSet.getFloat("Score");
                ScoreQuiz score = new ScoreQuiz(accountId, quizId, Score);
                return score;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // update số lần thực hiện bài quiz
    public int updateAttemptNumber(int account_id, int quizId) {
        int AttemptNumber = 1;
        connection = getConnection();
        String sql = """
                     select max(aq.AttemptNumber)
                     from AnswerQuestion aq
                     join Question qu on aq.QuestionId = qu.QuestionId
                     join Quiz qz on qz.QuizId = qu.QuizId
                     where aq.AccountId = ? and qz.QuizId = ?""";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            statement.setInt(2, quizId);
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getInt(1) != 0) {
                AttemptNumber = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return AttemptNumber;
    }

    // lưu trữ danh sách câu trả lời của sinh viên 
    public void insertUserAnser(ArrayList<UserAnswer> userAnswer) {
        connection = getConnection();
        String sql = "INSERT INTO [dbo].[AnswerQuestion]\n"
                + "           ([AccountId]\n"
                + "           ,[QuestionId]\n"
                + "           ,[Answer]\n"
                + "           ,[IsCorrect]\n"
                + "           ,[AttemptNumber])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?, ?)";
        try {
            statement = connection.prepareStatement(sql);

            for (UserAnswer useranswer : userAnswer) {
                statement.setInt(1, useranswer.getAccountId());
                statement.setInt(2, useranswer.getQuestionId());
                statement.setString(3, useranswer.getAnswer());
                statement.setBoolean(4, useranswer.isIsCorrectUserAnswer());
                statement.setInt(5, useranswer.getAttemptNumber());
                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // inser  Score  này để lư trữ điểm số của sinh viên khi làm 
    public void insertcoreQuiz(ScoreQuiz scorequiz) {
        connection = getConnection();
        String sql = "INSERT INTO [dbo].[ScoreQuiz]\n"
                + "           ([AccountId]\n"
                + "           ,[QuizId]\n"
                + "           ,[Score])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, scorequiz.getAccountId());
            statement.setInt(2, scorequiz.getQuizId());
            statement.setFloat(3, scorequiz.getScore());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lấy ra danh sách câu trả lời mới nhất của người dùng làm  bài quiz
    public ArrayList<UserAnswer> getListUserAnswerByQuizIdAndAccId(int account_id, int quizId) {
        ArrayList<UserAnswer> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = """
                     select aq.*
                     from AnswerQuestion aq
                     join Question qu on aq.QuestionId = qu.QuestionId
                     join Quiz qz on qz.QuizId = qu.QuizId
                     where aq.AccountId = ? and qz.QuizId = ? AND aq.AttemptNumber = (
                     select max(aq.AttemptNumber)
                     from AnswerQuestion aq
                     join Question qu on aq.QuestionId = qu.QuestionId
                     join Quiz qz on qz.QuizId = qu.QuizId
                     where aq.AccountId = ? and qz.QuizId = ?)""";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            statement.setInt(2, quizId);
            statement.setInt(3, account_id);
            statement.setInt(4, quizId);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int accountId = resultSet.getInt("accountId");
                int questionId = resultSet.getInt("questionId");
                String answer = resultSet.getString("answer");
                boolean isCorrect = resultSet.getBoolean("isCorrect");
                int AttemptNumber = resultSet.getInt("AttemptNumber");
                UserAnswer us = new UserAnswer(accountId, questionId, answer, isCorrect, AttemptNumber);
                listFound.add(us);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    public static void main(String[] args) {
        QuizDAO dao = new QuizDAO();
        //dao.updateTypeQuestion(new Questions(295, 1, 289, "Hello Elearning", true));
        System.out.println(dao.findScoreDoQuizByAccountIdAndQuizId(2, 89));

    }
}
