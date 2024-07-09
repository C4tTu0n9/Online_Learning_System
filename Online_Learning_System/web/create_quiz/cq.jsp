<%-- 
    Document   : cq
    Created on : Jun 11, 2024, 2:49:53 PM
    Author     : hatro
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create A New Quiz</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

        <!-- Prevent the demo from appearing in search engines (REMOVE THIS) -->
        <meta name="robots" content="noindex">

        <!-- Simplebar -->
        <link type="text/css" href="${pageContext.request.contextPath}/assets/vendor/simplebar.css" rel="stylesheet">

        <!-- Material Design Icons  -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <!-- Roboto Web Font -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">

        <!-- MDK -->
        <link type="text/css" href="${pageContext.request.contextPath}/assets/vendor/material-design-kit.css" rel="stylesheet">

        <!-- Sidebar Collapse -->
        <link type="text/css" href="${pageContext.request.contextPath}/assets/vendor/sidebar-collapse.min.css" rel="stylesheet">

        <!-- Touchspin -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-touchspin.css">

        <!-- Vendor CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nestable.css">
        <!-- link css quiz add-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/createQuiz/style.css"/>
        <style>
            .delete-button {
                background-color: #f44336; /* Red background */
                border: none;
                border-radius: 8px;
                padding: 16px;
                cursor: pointer;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .delete-button img {
                width: 24px; /* Adjust the size as needed */
                height: 24px; /* Adjust the size as needed */
            }

            .delete-button:hover {
                background-color: #d32f2f; /* Darker red on hover */
            }

            .delete-button:focus {
                outline: none; /* Remove the default focus outline */
            }

        </style>
    </head>
    <body>
        <jsp:include page="../common/menu.jsp"></jsp:include>
        <div class="container">
            <h1 class="page-heading h2" style="margin-top: 10px;">Create A New Quiz</h1>
            <form id="addQuizForm" action="createquiz?moduleId=${this_module.moduleid}&cid=${requestScope.cidCourse}" method="post" onsubmit="return validateForm3(event)">
            <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Module: ${this_module.modulename}</h4>
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <label for="quizTitle" class="col-sm-3 col-form-label">Quiz Title:</label>
                            <div class="col-sm-9 col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Title" aria-describedby="sizing-addon2" name="quizTitle" id="quizTitle">
                                    <div id="quizTitleError" class="error"></div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="time_toggle" class="col-sm-3 col-form-label">Timeframe:</label>
                            <div class="col-sm-9">
                                <div class="form-inline">
                                    <div class="form-group" style="margin-right: 10px; margin-top: 10px">
                                        <input type="number" min="1" max="100" class="form-control text-center" name="timeNumber" id="timeNumber" style="width: 70px;">
                                        <div id="timeNumberError" class="error"></div>
                                    </div>
                                    <div class="form-group" style="margin-right: 10px; margin-top: 10px">
                                        <select class="custom-select" name="timeUnit">
                                            <option value="minutes" selected>Minutes</option>
                                            <option value="seconds">Seconds</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="quizScore" class="col-sm-3 col-form-label">Pass Score:</label>
                            <div class="col-sm-9">
                                <div class="form-inline">
                                    <div class="form-group" style="margin-right: 10px;">
                                        <input type="number" min="1" max="10" class="form-control text-center" name="quizScore" id="quizScore" style="width: 70px;">
                                        <div id="quizScoreError" class="error"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer" style="display: flex; justify-content: space-between; align-items: center;">
                        <div class="">
                            <input type="submit" name="AddQuiz" value="Add Quiz" class="btn btn-success" style="width: 127.6px;">
                        </div>
                        <div style="">
                            <a href="ModuleManage?moduleId=${this_module.moduleid}&cid=${requestScope.cidCourse}" class="btn btn-danger">Cancel</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery.min.js"></script>

        <!-- Bootstrap -->
        <script src="${pageContext.request.contextPath}/assets/vendor/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap.min.js"></script>

        <!-- Simplebar -->
        <!-- Used for adding a custom scrollbar to the drawer -->
        <script src="${pageContext.request.contextPath}/assets/vendor/simplebar.js"></script>

        <!-- MDK -->
        <script src="${pageContext.request.contextPath}/assets/vendor/dom-factory.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/material-design-kit.js"></script>

        <!-- Sidebar Collapse -->
        <script src="${pageContext.request.contextPath}/assets/vendor/sidebar-collapse.js"></script>

        <!-- App JS -->
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

        <!-- Vendor JS -->
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery.nestable.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery.bootstrap-touchspin.js"></script>

        <!-- Initialize -->
        <script src="${pageContext.request.contextPath}/assets/js/nestable.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/touchspin.js"></script>

        <script src="${pageContext.request.contextPath}/lib/wow/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>

        <!-- JavaScript for form validation -->
        <script>
            function validateForm3(event) {
                // Lấy các giá trị từ các trường input
                let quizTitle = $('#quizTitle').val();
                let timeNumber = $('#timeNumber').val();
                let quizScore = $('#quizScore').val();

                // Clear current error messages
                $('.error').html('');

                // Check if fields are empty and show error messages
                if (quizTitle === '') {
                    $('#quizTitleError').html('Title Can Not Be Empty');
                }
                if (timeNumber === '') {
                    $('#timeNumberError').html('Time Frame Of Quiz Can Not Be Empty');
                }
                if (quizScore === '') {
                    $('#quizScoreError').html('Score Of Quiz Can Not Be Empty');
                }

                let error = '';
                $('.error').each(function () {
                    error += $(this).html();
                });
                if (error === '') {
                    $('#addQuizForm').submit();
                } else {
                    event.preventDefault();
                }
            }
        </script>
    </body>
</html>
