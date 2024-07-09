<%-- 
    Document   : ChangePassword
    Created on : May 28, 2024, 5:10:05 PM
    Author     : hatro
--%>

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
<!--        <form action="join?action=changepassword" method="post">
            Input New Password:
            <input type="password" name="newPassword">
            </br>
            Confirm Password:
            <input type="password" name="confirmPassword">
            <br>
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
                            <h4 style="color: red">${Error}</h4>

                            <form  action="join?action=changepassword" method="post" id="login-form">

                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="newPassword" value="${password}" id="pass" placeholder="Password"/>
                                </div>
                                <div class="form-group">
                                    <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="confirmPassword" value="${re_pass}" id="re_pass" placeholder="Repeat your password"/>
                                </div>

                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin" class="form-submit" value="Submit"/>
                                </div>

                            </form>

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
