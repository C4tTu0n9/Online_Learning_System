/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.User.Manager;

import Dal.AccountDAO;
import Dal.QuizDAO;
import Model.AccountDTO;
import Model.Answer;
import Model.ProfileDTO;
import Model.Questions;
import Model.Quiz;
import Util.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hatro
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class CreateQuestionsServlet extends HttpServlet {

    QuizDAO quizDAO = new QuizDAO();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get action
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "download":
                downloadTemplateQuizExcelDoGet(request, response);
                //response.sendRedirect("controller");
                break;
            default:
        }
        response.sendRedirect("controllerquestion");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //get action
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "create":
                createQuestion(request);
                //response.sendRedirect("controller");
                break;
            case "delete":
                deleteQuestion(request, response);
                break;
            case "edit":
                editQuestion(request, response);
                break;
            case "import":
                importQuestionFileExe(request, response);
                break;
            default:
        }
        response.sendRedirect("controllerquestion");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void createQuestion(HttpServletRequest request) {

        HttpSession session = request.getSession();
        int mid = Integer.parseInt(request.getParameter("midCreate"));
        int cid = Integer.parseInt(request.getParameter("cidCreate"));
        int quizId = (int) session.getAttribute("quizId");
        String questionNumber_str = request.getParameter("questionNumber");
        String titleQuestion = request.getParameter("titleQuestion");
        String typeQuestion_str = request.getParameter("typeQuestion");
        String[] choices = request.getParameterValues("answer");

        int questionNumber = Integer.parseInt(questionNumber_str);
        boolean typeQuestion = false;
        typeQuestion = typeQuestion_str.equalsIgnoreCase("radioBox");

        Questions questions = quizDAO.insertQuestions(new Questions(questionNumber, quizId, titleQuestion, typeQuestion));

        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= choices.length; i++) {
            boolean correctAnswer = request.getParameter("correctAnswer" + i) == null ? false : true;
            answers.add(new Answer(questions.getQuestionId(), choices[i - 1], correctAnswer));
        }
        quizDAO.insertAnswers(answers);
        quizDAO.updateTypeQuestion(questions);
        session.setAttribute("questions", questions);
        session.setAttribute("mid", mid);
        session.setAttribute("cid", cid);
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int quizId = (int) session.getAttribute("quizId");
        int questionId = Integer.parseInt(request.getParameter("id"));
        quizDAO.deleteQuestionById(questionId, quizId);
    }

    private void editQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int quizId = (int) session.getAttribute("quizId");
        int idQuestionEdit = Integer.parseInt(request.getParameter("id"));
        String questionNumber_str = request.getParameter("number");
        String titleQuestion = request.getParameter("title");
        String typeQuestion_str = request.getParameter("typeQuestion");
        String[] choices = request.getParameterValues("answer");

        int questionNumber = Integer.parseInt(questionNumber_str);
        boolean typeQuestion = false;
        typeQuestion = typeQuestion_str.equalsIgnoreCase("radioBox");

        Questions questions = quizDAO.editQuestionsById(new Questions(questionNumber, quizId, titleQuestion, typeQuestion), idQuestionEdit);

        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= choices.length; i++) {
            boolean correctAnswer = request.getParameter("correctAnswer" + i) == null ? false : true;
            answers.add(new Answer(idQuestionEdit, choices[i - 1], correctAnswer));
        }
        quizDAO.editAnswers(answers);
        quizDAO.updateTypeQuestion(questions);
    }

    private void importQuestionFileExe(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int mid = Integer.parseInt(request.getParameter("midCreate"));
        int cid = Integer.parseInt(request.getParameter("cidCreate"));
        int quizId = (int) session.getAttribute("quizId");
        Part filePart = request.getPart("file");

        try (InputStream fileContent = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            Questions question = new Questions();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Check if questionNum and questionTitle are not null
                Cell questionNumCell = row.getCell(0);
                Cell questionTitleCell = row.getCell(1);

                if (questionNumCell == null || questionTitleCell == null) {
                    continue; // Skip this row if either cell is null
                }

                // Check if questionNum is numeric and questionTitle is a string
                if (questionNumCell.getCellType() != CellType.NUMERIC || questionTitleCell.getCellType() != CellType.STRING) {
                    continue; // Skip this row if cell types do not match expected types
                }

                int questionNum = (int) questionNumCell.getNumericCellValue();
                String questionTitle = questionTitleCell.getStringCellValue().trim();

                if (questionTitle.isEmpty()) {
                    continue; // Skip this row if questionTitle is empty
                }

                boolean typeQuestion = false;
                question = quizDAO.insertQuestions(new Questions(questionNum, quizId, questionTitle, typeQuestion));

                ArrayList<Answer> answersList = new ArrayList<>();
                for (int i = 2; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        String answerText = null;
                        boolean correctAnswer = false;

                        // Check the cell type and handle accordingly
                        switch (cell.getCellType()) {
                            case STRING:
                                answerText = cell.getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                answerText = String.valueOf((int) cell.getNumericCellValue()).trim();
                                break;
                            default:
                                continue; // Skip other types
                        }

                        // Check if the answer ends with '*' and remove it if present
                        if (answerText.endsWith("*")) {
                            correctAnswer = true;
                            answerText = answerText.substring(0, answerText.length() - 1).trim();
                        }

                        Answer answer = new Answer(question.getQuestionId(), answerText, correctAnswer);
                        answersList.add(answer);
                    }
                }

                quizDAO.insertAnswers(answersList);
                quizDAO.updateTypeQuestion(question);
            }
            session.setAttribute("questions", question);
            session.setAttribute("mid", mid);
            session.setAttribute("cid", cid);

        } catch (Exception e) {
            response.getWriter().println("An error occurred while processing the file: " + e.getMessage());
            e.printStackTrace(response.getWriter());
        }
    }

    private void downloadTemplateQuizExcelDoGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        String filePath = "/Excel_mentor_template/Create_Question_Template.xlsx";
        File downloadFile = new File(getServletContext().getRealPath(filePath));
        FileInputStream inStream = new FileInputStream(downloadFile);

        // Cấu hình response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=Create_Question_Template.xlsx");

        // Ghi file vào OutputStream
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

}
