<%-- 
    Document   : ForgotPassword
    Created on : May 28, 2024, 2:37:24 PM
    Author     : hatro
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Forgot Pass Form</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="fonts_login/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="css_login/style.css">
    </head>
    <body>
<!--        <form action="join?action=forgot" method="post">
            Email:
            <br/>
            <input type="text" name="email">
            <br/>
            <input type="submit" value="submit">
            ${Error}
        </form>-->



        <div class="main-1">
            <!-- Sing in  Form -->
            <section class="sign-in">
                <div class="container">
                    <div class="signin-content">

                        <div class="signin-image">
                            <figure><img src="images_login/signin-image.jpg" alt="sing up image"></figure>
                        </div>

                        <div class="signin-form">
                            <h2 class="form-title">Reset Password</h2>
                           

                            <form  action="join?action=forgot" method="post" id="login-form">
                                
                                <div class="form-group">
                                    <label for="your_email"><i class="zmdi zmdi-email"></i></label>
                                    <input value="${email}" type="text" name="email" id="your_email" placeholder="Email"/>
                                </div>
                                
                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin" class="form-submit" value="Send email"/>
                                </div>
                                
                            </form>
                             <h4 style="color: red">${Error}</h4>
                        </div>
                    </div>
                </div>
            </section>

        </div>

        <!-- JS -->
        <script src="vendor_login/jquery/jquery.min.js"></script>
        <script src="js_login/main.js"></script>
    </body>
</html>
