<%-- 
    Document   : Captra
    Created on : May 28, 2024, 2:58:51 PM
    Author     : hatro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Captcha</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="fonts_login/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="css_login/style.css">
    </head>
    <body>
        
<!--        <form action="join?action=capcha" method="post">
            Kiem tra captra trong emial va dien dung vao day
            <input type="text" name="capcha">
            <input type="submit" value="Submint">
            ${Error};
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
                            <h2 class="form-title">Check captcha in your email!</h2>
                            <h4 style="color: red">${Error}</h4>

                            <form  action="join?action=capcha" method="post" id="login-form">

                                <div class="form-group">
                                    <label for="captcha"><i class="zmdi zmdi-code"></i></label>
                                    <input value="" type="text" name="capcha" id="your_email" placeholder="Captcha"/>
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
