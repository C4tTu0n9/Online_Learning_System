<%-- 
    Document   : SignUp.jsp
    Created on : May 12, 2024, 3:06:54 PM
    Author     : hatro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Register Form</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="fonts_login/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="css_login/style.css">
    </head>
    <body>
        <!--        <form method="post" action="signup">
                    <h1>
                        FullName:
                        <input type="text" name="fullname" value="${fullname}">
                        <br>
                        Password:
                        <input type="password" name="password" value="${password}">
                        <br>
                        Email:
                        <input type="text" name="email" value="${email}">
                        <br>
                       
                        <br>
                        <input type="submit" value="submit">
                    </h1>
        
                </form>-->


        <div class="main">
            <!-- Sign up form -->
            <section class="signup">
                <div class="container">
                    <div class="signup-content">
                        <div class="signup-form">
                            <h2 class="form-title">Sign up</h2>
                            <h4 style="color: red" class="alert-primary">
                                ${error}
                            </h4>
                            <form method="post" action="join?action=signup" class="register-form" id="register-form">
                                <div class="form-group">
                                    <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                    <input type="text" name="fullname" value="${fullname}" id="name" placeholder="Your Name"/>
                                </div>
                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                    <input type="email"name="email" value="${email}" id="email" placeholder="Your Email"/>
                                </div>
                                <h4 style="color: red" class="alert-primary">
                                    ${error_pass}
                                </h4>
                                <h4 style="color: red" class="alert-primary">
                                    ${error_pass_length}
                                </h4>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="password" value="${password}" id="pass" placeholder="Password"/>
                                </div>
                                <div class="form-group">
                                    <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="re_pass" value="${re_pass}" id="re_pass" placeholder="Repeat your password"/>
                                </div>
                                <h4 style="color: red" class="alert-primary">
                                    ${error_terms}
                                </h4>
                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" />
                                    <label for="agree-term" class="label-agree-term"><span><span></span></span>I agree all statements in  
                                        <a href="#" class="term-service">Terms of service</a></label>
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register"/>
                                </div>
                            </form>
                            <div class="social-login">
                                <span class="social-label">Or login with</span>
                                <ul class="socials">
                                    <li><a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/Project_E-Learning/google&response_type=code&client_id=83884111115-sdp6j9md5ok9ajc3hrvf90mbiub9v60k.apps.googleusercontent.com&approval_prompt=force"><i class="display-flex-center zmdi zmdi-google"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="signup-image">
                            <figure><img src="images_login/signup-image.jpg" alt="sing up image"></figure>
                            <a href="join?action=login" class="signup-image-link">I am already member</a>

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
