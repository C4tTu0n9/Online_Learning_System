<%-- 
    Document   : courses
    Created on : May 14, 2024, 7:40:05 PM
    Author     : Tuan Anh(Gia Truong)
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>eLEARNING - eLearning HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <style>
            button.feather-send {
                padding: 10px 20px;
                border-radius: 5px;
                border: none;
                background-color: #007bff;
                color: white;
                font-size: 16px;
                cursor: pointer;
                transition: background-color .3s;
            }

            button.feather-send:hover {
                background-color: #0056b3;
            }
        </style>

    </head>


    <body class="body-message" style="height: 100%;">

        <div class="app">
            <jsp:include page="common/menu.jsp"></jsp:include>

                <div class="col-lg-10">
                    <h2 class="display-3 text-dark animated slideInDown">Messenger</h2>
                </div>
                <div class="wrapper"> 

                    <div class="conversation-area">
                   
                    
                    <c:forEach items="${listUserWhoReceiver}" var="o">
                        <c:choose>
                            <c:when test="${listProfile.getProfile_id() != null && listProfile.getProfile_id() == o.getReceiver_id()}">
                                <a class="msg active">
                                    <div class="msg-profile group">
                                        <svg viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1">
                                        <path d="M12 2l10 6.5v7L12 22 2 15.5v-7L12 2zM12 22v-6.5"/>
                                        <path d="M22 8.5l-10 7-10-7"/>
                                        <path d="M2 15.5l10-7 10 7M12 2v6.5"/>
                                        </svg>
                                        <img class="msg-profile" src="${o.getAvt()}" alt="" />
                                    </div>
                                    <div class="msg-detail">
                                        <div class="msg-username">${o.getFullname()}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="msg online" href="messenger?sender_id=${sessionScope.account.getAccount_id()}&receiver_id=${o.getReceiver_id()}" >
                                    <img class="msg-profile" src="${o.getAvt()}" alt="" />
                                    <div class="msg-detail">
                                        <div class="msg-username">${o.getFullname()}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                  
                </div>

                <div class="chat-area">
                    <div class="chat-area-header">
                        <div class="chat-area-title">${listProfile.getFullname()}</div>
                    </div>

                    <c:forEach items="${listMessages}" var="m">
                        <div class="chat-area-main">
                            <c:choose>
                                <c:when test="${sessionScope.account.getAccount_id() != null && sessionScope.account.getAccount_id() == m.getSender_id()}">
                                    <div class="chat-msg owner">
                                        <div class="chat-msg-profile">
                                            <img class="chat-msg-img" src="${m.getAvt()}" alt="" />
                                            <div class="chat-msg-date">${m.getMessage_time()}</div>
                                        </div>
                                        <div class="chat-msg-content">
                                            <div class="chat-msg-text">${m.getMessage_text()}</div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="chat-msg">
                                        <div class="chat-msg-profile">
                                            <img class="chat-msg-img" src="${m.getAvt()}" alt="" />
                                            <div class="chat-msg-date">${m.getMessage_time()}</div>
                                        </div>
                                        <div class="chat-msg-content">
                                            <div class="chat-msg-text">${m.getMessage_text()}</div>

                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>

                    <form action="messenger" method="post">
                        <div class="chat-area-footer">
                            <input type="text" name="message_text" placeholder="Type something here..." />
                            <input type="hidden" name="sender_id" value="${sessionScope.account.getAccount_id()}">
                            <input type="hidden" name="receiver_id" value="${listProfile.getProfile_id()}"> 
                            <button type="submit" class="feather feather-send">SEND</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>







        <!--        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>-->


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <script src=js/module-toggle.js></script>
        <script>
            const toggleButton = document.querySelector('.dark-light');
            const colors = document.querySelectorAll('.color');

            colors.forEach(color => {
                color.addEventListener('click', (e) => {
                    colors.forEach(c => c.classList.remove('selected'));
                    const theme = color.getAttribute('data-color');
                    document.body.setAttribute('data-theme', theme);
                    color.classList.add('selected');
                });
            });

            toggleButton.addEventListener('click', () => {
                document.body.classList.toggle('dark-mode');
            });
        </script>

    </body>


</html>
