/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import Dal.AccountDAO;

import Model.AccountDTO;

import Model.ProfileDTO;
import Util.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
@WebServlet(name = "imPortMentorServlet", urlPatterns = {"/dasboard_for_admin/manageAccount"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class ManageAccountMentorByAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet imPortMentorServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet imPortMentorServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        //response.getWriter().println("Mentor accounts imported and activated successfully.");
        HttpSession session = request.getSession();
        String accountId = request.getParameter("accountid");
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));
        AccountDAO accDao = new AccountDAO();

        AccountDTO acc = (AccountDTO) session.getAttribute("account");
        if (acc == null || (acc.getRole_id() != 1 && acc.getRole_id() != 2)) {
            response.sendRedirect("../home");
            return;
        }

        try {

            switch (action) {
                case "addAccount":
                    request.getRequestDispatcher("add_account.jsp").forward(request, response);
                    break;
                case "updateAccount":
                    AccountDTO account = accDao.getAccountById(Integer.parseInt(accountId));

                    request.setAttribute("accid", accountId);
                    request.setAttribute("account", account);
                    request.getRequestDispatcher("update_account.jsp").forward(request, response);
                    break;
                default:
                    if (acc.getRole_id() == 1) {
                        ArrayList<AccountDTO> listAllAccount = accDao.getAllMentorAccounts();
                        //response.getWriter().print(listAllAccount);
                        request.setAttribute("listAllAccount", listAllAccount);
                        request.getRequestDispatcher("MentorAccount.jsp").forward(request, response);
                    } else {
                        ArrayList<AccountDTO> listAllAccount = accDao.getMyMentorAccounts(acc.getAccount_id());
                        request.setAttribute("listAllAccount", listAllAccount);
                        request.getRequestDispatcher("MentorAccount.jsp").forward(request, response);
                        PrintWriter o = response.getWriter();
                        o.print(acc.getAccount_id());
                    }
            }

        } catch (Exception e) {
        }

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
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));

        switch (action) {
            case "import":
                importMentorFromFileExcel(request, response);
                break;
            case "addAccount":
                addNewAccountByAdmin(request, response);
                break;
            case "updateAccount":
                updateAccountByAdmin(request, response);
                break;
            case "statusAccount":
                statusAccount(request, response);
                break;
            default:
                throw new AssertionError();
//                response.getWriter().print("Lỗi rồi,không nhận được action phù hợp");
        }
    }

    //Lấy dữ liệu từ excel lên database
    private void importMentorFromFileExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Part filePart = request.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("No file uploaded or file is empty.");
            return;
        }

        // Xử lý file và đọc dữ liệu từ file Excel
        try (InputStream fileContent = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(fileContent)) {

            Sheet sheet = workbook.getSheetAt(0);
            AccountDAO accDao = new AccountDAO();
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua hàng tiêu đề
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            // Đọc dữ liệu từ các hàng
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String email = getCellValue(row.getCell(1));
                String fullName = getCellValue(row.getCell(0));
                String emailManageBy = getCellValue(row.getCell(2));
                

//                Nếu trong excel không có name thì lấy name theo email
                if (fullName == null || fullName.isEmpty()) {
                    fullName = getNameFromEmail(email);
                }

                // Kiểm tra nếu email đã tồn tại
                if (accDao.checkAccountExist(email)) {
                    response.getWriter().println("Email already exists: " + email);
                    continue; // bỏ qua tài khoản này và tiếp tục với tài khoản khác
                }
                
                //Lấy tài khoản của manager theo email
                AccountDTO manager_Account = accDao.getAccountIdByEmail(emailManageBy);
                
                // Tạo mật khẩu ngẫu nhiên
                String password = generateRandomPassword(8);

                // Tạo đối tượng tài khoản và hồ sơ
                AccountDTO account = new AccountDTO(email, password, 3);
                ProfileDTO profile = new ProfileDTO(fullName, manager_Account.getRole_id());

                // Thêm vào database
                accDao.insertUser(account, profile);

                // Gửi email mật khẩu tới mentor 
                SendEmail sendMk = new SendEmail();
                sendMk.send("hatronghung7777@gmail.com", "chnzvsbysoeesgwe", email, "Account Activation",
                        "Dear mentor,\nYour account has been activated. Your password is: " + password, response);

            }

            response.getWriter().println("Mentor accounts imported and activated successfully.");

        } catch (Exception e) {
            //response.getWriter().println("An error occurred while processing the file: " + e.getMessage());
            session.setAttribute("err", "An error occurred while processing the file: " + e.getMessage());
            e.printStackTrace(response.getWriter());
        }
        session.setAttribute("msg", "Mentor accounts imported and activated successfully.");
        response.sendRedirect("manageAccount");
    }

    //thêm tài khoản mới
    private void addNewAccountByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDao = new AccountDAO();
        HttpSession session = request.getSession();
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String cfpass = request.getParameter("cfpass");
        String gender_str = request.getParameter("gender");
        String role = request.getParameter("role");
        //String avatar = request.getParameter("");

        AccountDTO accSession = (AccountDTO) session.getAttribute("account");

        String msg = "";
        boolean gender = false;
        if (gender_str.equals("Male")) {
            gender = true;
        }

        if (!pass.equals(cfpass)) {
            msg = "Password must equal repassword";
        } else if (pass.length() < 8) {
            msg = "The length password must be longer 8 character";
        } else if (accDao.checkAccountExist(email)) {
            msg = "Account was exist";
        } else {
            try {

                AccountDTO account = new AccountDTO(email, pass, Integer.parseInt(role));

                ProfileDTO profile = new ProfileDTO(fullname, gender, 0, accSession.getAccount_id());
                accDao.insertUserByAdmin(account, profile);
                response.sendRedirect("manageAccount");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("pass", pass);
        request.setAttribute("cfpass", cfpass);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);

        request.getRequestDispatcher("add_account.jsp").forward(request, response);
    }

    //Update tài khoản bỏi admin
    private void updateAccountByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDao = new AccountDAO();

        HttpSession session = request.getSession();
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String gender_str = request.getParameter("gender");
        String role = request.getParameter("role");
        String profiId = request.getParameter("accid");

        String msg = "";
        boolean gender = false;
        if (gender_str.equals("Male")) {
            gender = true;
        }

        if (pass.length() < 8) {
            msg = "The length password must be longer 8 character";
        } else {
            try {
                AccountDTO account = new AccountDTO(email, pass, Integer.parseInt(role));

                ProfileDTO profile = new ProfileDTO(Integer.parseInt(profiId), fullname, gender);
                accDao.updateAccount(account, profile);
                response.sendRedirect("manageAccount");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("pass", pass);
        request.setAttribute("gender", gender);
        request.setAttribute("role", role);
        request.setAttribute("accid", profiId);

        request.getRequestDispatcher("update_account.jsp").forward(request, response);

    }

    private void statusAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountid = request.getParameter("accid");
        AccountDAO accDao = new AccountDAO();
        HttpSession session = request.getSession();
        try {
            if (accDao.CheckActiveOrInActive(Integer.parseInt(accountid))) {
                accDao.activeOrInactiveAccount(Integer.parseInt(accountid), 0);
            } else {
                accDao.activeOrInactiveAccount(Integer.parseInt(accountid), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("msg", "Change account status successfully.");
        response.sendRedirect("manageAccount");

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

    // Hàm lấy giá trị ô kiểu chuỗi
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
    }

    // Hàm lấy giá trị ô kiểu int
    private int getCellValueInt(Cell cell) {
        if (cell == null) {
            return 0;
        }

        return cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue() : 0;

    }

    // Tạo mật khẩu ngẫu nhiên
    private static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }

    private String getNameFromEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "";
        }

        String[] parts = email.split("@");
        if (parts.length < 2 || parts[0].isEmpty()) {
            return "";
        }

        String name = parts[0];

        String fullname = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        return fullname;
    }

}
