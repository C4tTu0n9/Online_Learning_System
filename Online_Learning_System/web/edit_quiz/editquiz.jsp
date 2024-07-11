<%-- 
    Document   : cquestions
    Created on : Jun 17, 2024, 2:38:26 PM
    Author     : hatro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
            .question-number {
                font-weight: bold;
                margin-bottom: 10px;
                font-size: 1.2em;
                color: #333;
            }
            .answer {
                padding: 5px;
                margin-bottom: 5px;
                border-radius: 4px;
            }
            .answer.correct {
                color: #ffffff;
                background-color: #28a745;
            }
            .answer.incorrect {
                color: #333333;
                background-color: #f8f9fa;
            }
            .buttons-container {
                display: flex;
                justify-content: space-between;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../common/menu.jsp"></jsp:include>

            <div class="container">
                <h1 class="page-heading h2" style="margin-top: 10px;">Edit Quiz</h1>
                <form id="addQuizForm" action="editquiz" method="post">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Module: ${moduleOfQuizEdit.getModulename()}</h4>
                        <input type="hidden" name="moduleId" value="${moduleOfQuizEdit.getModuleid()}">
                        <input type="hidden" name="quizId" value="${quizEdit.getQuizId()}">
                        <input type="hidden" name="cid1" value="${moduleOfQuizEdit.getCourseid()}">
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <label for="quizTitle" class="col-sm-3 col-form-label">Quiz Title:</label>
                            <div class="col-sm-9 col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Title" aria-describedby="sizing-addon2" name="quizTitle" id="quizTitle" value="${quizEdit.getQuizName()}">
                                    <div id="quizTitleError" class="error"></div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="time_toggle" class="col-sm-3 col-form-label">Timeframe:</label>
                            <div class="col-sm-9">
                                <div class="form-inline">
                                    <div class="form-group" style="margin-right: 10px; margin-top: 10px">
                                        <c:if test="${requestScope.minutes != 0}">
                                            <input type="number" min="1" max="100" class="form-control text-center" name="timeNumber" id="timeNumber" style="width: 70px;" value="${requestScope.minutes}">
                                        </c:if>
                                        <c:if test="${requestScope.seconds != 0 && requestScope.minutes == 0}">
                                            <input type="number" min="1" max="59" class="form-control text-center" name="timeNumber" id="timeNumber" style="width: 70px;" value="${requestScope.seconds}">
                                        </c:if>

                                        <div id="timeNumberError" class="error"></div>
                                    </div>
                                    <div class="form-group" style="margin-right: 10px; margin-top: 10px">
                                        <select class="custom-select" name="timeUnit">
                                            <c:if test="${requestScope.minutes != 0}">
                                                <option value="minutes" selected>Minutes</option>
                                                <option value="seconds">Seconds</option>
                                            </c:if>
                                            <c:if test="${requestScope.seconds != 0}">
                                                <option value="seconds" selected>Seconds</option>
                                                <option value="minutes">Minutes</option>
                                            </c:if>
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
                                        <input type="number" min="1" max="10" class="form-control text-center" name="quizScore" id="quizScore" style="width: 70px;" value="${quizEdit.getPassScore()}">
                                        <div id="quizScoreError" class="error"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer" style="display: flex; justify-content: space-between; align-items: center;">
                        <div class="">
                            <button type="button" class="btn btn-success" style="width: 127.6px;" onclick="validateAndShowModal()">Save Change</button>
                        </div>
                        <div style="">
                            <a href="ModuleManage?moduleId=${moduleOfQuizEdit.getModuleid()}&cid=${moduleOfQuizEdit.getCourseid()}" class="btn btn-danger">Cancel</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="modal fade" id="save-changes-modal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title" id="save-modal-label">Edit Question</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Do you want to edit questions?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="editQuestions()">Yes</button>
                        <button type="button" class="btn btn-primary" onclick="submitForm()">No</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery.min.js"></script>

        <!-- Bootstrap -->
        <script src="${pageContext.request.contextPath}/assets/vendor/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap.min.js"></script>

        <!-- Simplebar -->
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

        <!-- Custom Script -->
        <script>
            function showSaveModal() {
                $('#save-changes-modal').modal('show');
            }

            function submitForm() {
                document.getElementById('addQuizForm').submit();
            }

            function editQuestions() {
                document.getElementById('addQuizForm').submit();
                // Replace 'edit-questions.jsp' with the actual URL of your new JSP page
                window.location.href = 'editquizcrudquestion?quizId=${quizEdit.getQuizId()}';
            }

            function validateAndShowModal() {
                // Lấy các giá trị từ các trường input
                let quizTitle = $('#quizTitle').val();
                let timeNumber = $('#timeNumber').val();
                let quizScore = $('#quizScore').val();

                // Clear current error messages
                $('.error').html('');

                // Check if fields are empty and show error messages
                let isValid = true;
                if (quizTitle === '') {
                    $('#quizTitleError').html('Title Can Not Be Empty');
                    isValid = false;
                }
                if (timeNumber === '') {
                    $('#timeNumberError').html('Time Frame Of Quiz Can Not Be Empty');
                    isValid = false;
                }
                if (quizScore === '') {
                    $('#quizScoreError').html('Score Of Quiz Can Not Be Empty');
                    isValid = false;
                }

                if (isValid) {
                    showSaveModal();
                }
            }
        </script>

    </body>
</html>
