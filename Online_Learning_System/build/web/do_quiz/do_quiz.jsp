<%-- 
    Document   : do_quiz
    Created on : Jun 25, 2024, 2:49:24 PM
    Author     : hatro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Page</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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

        <!-- Additional CSS for the quiz counter -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/createQuiz/style.css"/>

        <style>
            .max-width-800 {
                font-family: Arial, sans-serif;
                margin: 20px auto;
                max-width: 800px;
                padding: 20px;
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .timer {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 0px;
                text-align: center;
                line-height: 1.5;
            }

            .timer span {
                background-color: #00cc66;
                color: white;
                padding: 5px;
                border-radius: 3px;
                margin: 0 2px;
            }

            .question {
                margin-bottom: 30px;
                border: 1px solid #ccc;
                padding: 15px;
                border-radius: 5px;
                background-color: #fff;
            }

            .question h3 {
                margin: 0 0 10px;
            }

            .options {
                list-style-type: none;
                padding: 0;
            }

            .options li {
                margin-bottom: 10px;
            }

            input[type="radio"],
            input[type="checkbox"] {
                display: none;
            }

            input[type="radio"]+label,
            input[type="checkbox"]+label {
                position: relative;
                padding-left: 30px;
                cursor: pointer;
                display: inline-block;
                line-height: 20px;
            }

            input[type="radio"]+label:before,
            input[type="checkbox"]+label:before {
                content: '';
                position: absolute;
                left: 0;
                top: 0;
                width: 20px;
                height: 20px;
                border: 2px solid #000;
                border-radius: 50%;
                background: #fff;
            }

            input[type="checkbox"]+label:before {
                border-radius: 3px;
            }

            input[type="radio"]:checked+label:before,
            input[type="checkbox"]:checked+label:before {
                background: #00cc66;
                border-color: #00cc66;
            }

            input[type="checkbox"]:checked+label:before {
                content: '✔';
                color: white;
                font-size: 16px;
                text-align: center;
                line-height: 20px;
            }

            input[type="radio"]:checked+label:before {
                background: #00cc66;
                border-color: #00cc66;
            }

            input[type="radio"]+label:hover:before,
            input[type="checkbox"]+label:hover:before {
                border-color: #555;
            }

            footer {
                text-align: center;
                margin-top: 20px;
                padding-top: 10px;
                border-top: 1px solid #ccc;
            }
        </style>
    </head>

    <body>
        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
            <a href="doquiz?mid=${quizDoQuiz.moduleId}&cid=${course.getCourse_id()}" class="navbar-brand d-flex align-items-center px-4 px-lg-5" id="backed" style="text-decoration: none; margin-right: 0px">
                <h2 class="m-0 text-primary">Back</h2>
            </a>

            <div class="col-2">
                <h7 style="margin-bottom: 0px">${quizDoQuiz.getQuizName()}</h7>
            </div>
            <div class="timer col-5" id="timer">
                <span id="hours">00</span>:<span id="minutes">00</span>:<span id="seconds">00</span>
            </div>

            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav ms-auto p-4 p-lg-0">
                    <span id="answeredCount" class="nav-item nav-link active">0</span>
                    <span class="nav-item nav-link active">/</span>
                    <span id="totalCount" class="nav-item nav-link active">${listQuestionsByMId.size()}</span> 
                    <span class="nav-item nav-link active">questions answered</span>
                </div>
            </div>
        </nav>
        <!-- Navbar End -->

        <div class="max-width-800">
            <form id="quizForm" action="doquiz" method="POST">
                <c:forEach items="${listQuestionsByMId}" var="question">
                    <input type="hidden" name="mid" value="${quizDoQuiz.getModuleId()}">
                    <div class="question" data-question-id="${question.getQuestionId()}">
                        <h3>${question.getQuestionNum()}. ${question.getQuestionName()}</h3>
                        <ul class="options">
                            <c:forEach items="${listAnswerByMId}" var="answer">
                                <c:if test="${question.getQuestionId() == answer.getQuestionId()}">
                                    <li>
                                        <input type="${question.isType() ? 'checkbox' : 'radio'}" 
                                               id="${answer.getQuestionId()}${answer.getChoices()}" 
                                               name="${question.getQuestionId()}" 
                                               value="${answer.getChoices()}"
                                               onchange="updateAnsweredCount()">
                                        <label for="${answer.getQuestionId()}${answer.getChoices()}">${answer.getChoices()}</label>
                                    </li>
                                </c:if>
                            </c:forEach>        
                        </ul>
                    </div>
                </c:forEach>
                <button type="submit" id="submitQuiz" class="btn btn-success" style="border: 5px">Submit</button>
            </form>
        </div>

       <script>
    function saveTimerState(timeLeft) {
        sessionStorage.setItem('quizTimeLeft', timeLeft);
    }

    function loadTimerState() {
        return parseInt(sessionStorage.getItem('quizTimeLeft')) || null;
    }

    function saveQuizState(isSubmitted) {
        sessionStorage.setItem('quizSubmitted', isSubmitted);
    }

    function loadQuizState() {
        return sessionStorage.getItem('quizSubmitted') === 'true';
    }

    function saveSelections() {
        document.querySelectorAll('input[type=radio], input[type=checkbox]').forEach((input) => {
            if (input.type === 'radio') {
                if (input.checked) {
                    sessionStorage.setItem(input.name, input.value);
                }
            } else if (input.type === 'checkbox') {
                let selectedValues = JSON.parse(sessionStorage.getItem(input.name)) || [];
                if (input.checked) {
                    if (!selectedValues.includes(input.value)) {
                        selectedValues.push(input.value);
                    }
                } else {
                    selectedValues = selectedValues.filter(value => value !== input.value);
                }
                sessionStorage.setItem(input.name, JSON.stringify(selectedValues));
            }
        });
    }

    function loadSelections() {
        document.querySelectorAll('input[type=radio], input[type=checkbox]').forEach((input) => {
            if (input.type === 'radio') {
                const savedValue = sessionStorage.getItem(input.name);
                if (savedValue !== null && savedValue === input.value) {
                    input.checked = true;
                }
            } else if (input.type === 'checkbox') {
                const savedValues = JSON.parse(sessionStorage.getItem(input.name)) || [];
                if (savedValues.includes(input.value)) {
                    input.checked = true;
                }
            }
        });
    }

    function updateTimer() {
        let hours = Math.floor(timeLeft / 3600);
        let minutes = Math.floor((timeLeft % 3600) / 60);
        let seconds = timeLeft % 60;

        hoursSpan.textContent = hours < 10 ? '0' + hours : hours;
        minutesSpan.textContent = minutes < 10 ? '0' + minutes : minutes;
        secondsSpan.textContent = seconds < 10 ? '0' + seconds : seconds;

        if (timeLeft > 0) {
            timeLeft--;
            saveTimerState(timeLeft);
            setTimeout(updateTimer, 1000);
        } else {
            submitQuiz1();
        }
    }

    function submitQuiz1() {
        saveQuizState(true);
        quizForm.submit();
        
    }

    function submitQuiz() {
        if (confirm('Are you sure you want to submit the quiz?')) {
            submitQuiz1();
            
        }
    }
    
   

    let timer = document.getElementById('timer');
    let hoursSpan = document.getElementById('hours');
    let minutesSpan = document.getElementById('minutes');
    let secondsSpan = document.getElementById('seconds');
    let submitButton = document.getElementById('submitQuiz');
    let quizForm = document.getElementById('quizForm');

    const quizTime = '${quizDoQuiz.getQuizTime()}';
    const timeParts = quizTime.split(':');
    let timeLeft = loadTimerState() || (+timeParts[0]) * 3600 + (+timeParts[1]) * 60 + (+timeParts[2]);

    let answeredCount = 0;
    const totalCount = ${listQuestionsByMId.size()};

    function updateAnsweredCount() {
        const questions = document.querySelectorAll('.question');
        answeredCount = 0;

        questions.forEach(question => {
            const radios = question.querySelectorAll('input[type="radio"]');
            const checkboxes = question.querySelectorAll('input[type="checkbox"]');
            const isRadioAnswered = Array.from(radios).some(radio => radio.checked);
            const isCheckboxAnswered = Array.from(checkboxes).some(checkbox => checkbox.checked);

            if (isRadioAnswered || isCheckboxAnswered) {
                answeredCount++;
            }
        });

        document.getElementById('answeredCount').textContent = answeredCount;
    }

    document.addEventListener('DOMContentLoaded', () => {
        if (loadQuizState()) {
            submitButton.disabled = true;
            submitButton.style.display = 'none';
        } else {
            updateTimer();
            loadSelections();
            updateAnsweredCount();

            document.querySelectorAll('input[type=radio], input[type=checkbox]').forEach((input) => {
                input.addEventListener('change', () => {
                    saveSelections();
                    updateAnsweredCount();
                });
            });

            submitButton.addEventListener('click', (e) => {
                e.preventDefault();
                submitQuiz();
            });

            const backButton = document.querySelector('#backed');
            let isAlertShown = false;

            const showAlert = function(e) {
                e.preventDefault();
                if (!isAlertShown) {
                    isAlertShown = true;
                    if (confirm('You are taking a quiz. If you exit, all your answers will be discarded. Are you sure you want to exit?')) {
                        window.location.href = backButton.getAttribute('href');
                    } else {
                        isAlertShown = false;
                    }
                }
            };

            backButton.addEventListener('click', showAlert);
        }

        updateAnsweredCount();
    });
</script>





        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/wow/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</html>
