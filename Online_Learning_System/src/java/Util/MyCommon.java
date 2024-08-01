/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Controller.CourseDetailServlet;
import Dal.AccountDAO;
import Dal.HomeDAO;
import Model.AccountDTO;
import Model.Category;
import Model.ProfileDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuong
 */
public class MyCommon {

    public MyCommon(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ProfileDTO profile = (ProfileDTO) session.getAttribute("profile");
        AccountDTO account = getMyAccount(request, response);
        session.setAttribute("profile", profile);
        session.setAttribute("account", account);
//get list category
        try {
            HomeDAO dao = new HomeDAO();
            ArrayList<Category> listCategory = dao.getAllCategory();
            session.setAttribute("listCategory", listCategory);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        session.setMaxInactiveInterval(60 * 30);
    }

    public static void getHeader(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ProfileDTO profile = (ProfileDTO) session.getAttribute("profile");
        AccountDTO account = getMyAccount(request, response);
        session.setAttribute("profile", profile);
        if (account != null) {
            session.setAttribute("account", account);
        }
//get list category
        try {
            HomeDAO dao = new HomeDAO();
            ArrayList<Category> listCategory = dao.getAllCategory();
            session.setAttribute("listCategory", listCategory);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        session.setMaxInactiveInterval(60 * 30);
    }

    public static AccountDTO getMyAccount(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AccountDTO my_account = (AccountDTO) session.getAttribute("account");
        Cookie account_id_cookies = new Cookie("account_id", String.valueOf(my_account.getAccount_id()));
        account_id_cookies.setMaxAge(60 * 60 * 24);
        if (my_account == null) {
            AccountDAO account_dao = new AccountDAO();
            Cookie cookie[] = request.getCookies();
            String account_id = "";
            for (Cookie c : cookie) {
                //delete old account_id in cookie
                if (c.getName().equals("account_id")) {
                    account_id = c.getValue();
                    break;
                }
            }
            if (account_id.isEmpty()) {
                return null;
            }
            return account_dao.getAccountById(Integer.parseInt(account_id));
        }
        else return my_account;
    }

}
