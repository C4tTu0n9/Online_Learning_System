<%-- 
    Document   : Home
    Created on : May 14, 2024, 1:35:42 AM
    Author     : hatro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
    <style>
        .btn {
            padding: 10px 20px;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn-login {
            background-color: #4CAF50;
            color: white;
        }
        .btn-signup {
            background-color: #008CBA;
            color: white;
        }
    </style>
</head>
<body>
    <h1>Welcome to our website!</h1>
    <p>This is the home page.</p>
    <h1>Hello : ${sessionScope.account.fullname}</h1>
    <form>
    <a href="Login.jsp" class="btn btn-login">Login</a>
    </form>
    <form action="signup" method="post">
    <a href="SignUp.jsp" class="btn btn-signup">Sign Up</a>
    </form>
    
    <c:if test="${account != null}">
        
        <a href="login?action=logout">Login Out</a>
    </c:if>
        <br>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/Project_E-LearningV2/google&response_type=code&client_id=83884111115-sdp6j9md5ok9ajc3hrvf90mbiub9v60k.apps.googleusercontent.com&approval_prompt=force"> Login With Google</a>
</body>
</html>
