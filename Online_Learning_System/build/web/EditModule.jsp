<%-- 
    Document   : EditModule
    Created on : Jun 29, 2024, 11:45:46 PM
    Author     : tuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Course</title>
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
            .account-settings-fileinput {
                position: absolute;
                visibility: hidden;
                width: 1px;
                height: 1px;
                opacity: 0;
            }
            .ui-w-80 {
                width: 80px !important;
                height: auto;
                border-radius: 17px;
            }
            .media{
                display: flex
            }
            .ml-4{
                margin-left: 10px !important;
            }
            .list-group-flush>.list-group-item{
                margin-bottom: 10px;
                border-width:0 0 0px
            }
            .card_mine{
                position:relative;
                display:flex;
                flex-direction:column;
                min-width:0;
                word-wrap:break-word;
                background-color:#fff;
                background-clip:border-box;
                border-left:1px solid rgba(0,0,0,0.125)
            }
            .custom-select{
                display: inline-block;
                width: 100%;
                height: calc(1.5em + .75rem + 2px);
                padding: .375rem 1.75rem .375rem .75rem;
                font-size: 1rem;
                font-weight: 400;
                line-height: 1.5;
                color: #495057;
                vertical-align: middle;
                background: #fff url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='4' height='5' viewBox='0 0 4 5'%3e%3cpath fill='%23343a40' d='M2 0L0 2h4zm0 5L0 3h4z'/%3e%3c/svg%3e") no-repeat right .75rem center/8px 10px;
                border: 1px solid #ced4da;
                border-radius: .25rem;
                -webkit-appearance: none;
                -moz-appearance: none;
                appearance: none;
            }
            .text-right{
                text-align: right!important;
            }
            input.form-control{
                padding: 12px
            }
            .btn-outline-danger:hover {
                color: #fff;
                background-color: #dc3545;
                border-color: #dc3545
            }
            .btn-outline-primary:hover {
                color: #000;
                background-color: #06BBCC;
                border-color: #06BBCC
            }
            .btn-outline-success:hover {
                color: #fff;
                background-color: #198754;
                border-color: #198754
            }

            .form-label{
                margin-bottom: 0px;
            }

        </style>
    </head>
    <body>
        <jsp:include page="common/menu.jsp"></jsp:include>


            <div class="container-fluid">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-lg-10">
                            <h5 class="display-6 text-dark">${my_managed_course.course_name}</h5>
                    </div>
                </div>
            </div>

            <div class="container light-style flex-grow-1 container-p-y">

                <div class=" overflow-hidden">
                    <div class="row no-gutters row-bordered row-border-light">

                        <div class="col-md-3 pt-0">
                            <div class="list-group list-group-flush account-settings-links" id="moduleList">
                                <a class="list-group-item list-group-item-action" href="course-manage?cid=${cid}&action=update"">Edit Course Information</a>
                                <h5>Modules</h5>
                                <c:forEach items="${list_module}" var="module">
                                    <a class="list-group-item list-group-item-action" href="ModuleManage?moduleId=${module.moduleid}&cid=${my_managed_course.course_id}" data-module-id="${module.moduleid}">${module.modulename}</a>
                                </c:forEach>
                                <a class="btn btn-outline-primary" href="course-manage?action=add_module&cid=${my_managed_course.course_id}">Add New Module</a>
                            </div>
                        </div>
                        <div class="col-md-9 card_mine">
                            <div class="tab-content">
                                <div class="tab-pane fade active show">
                                    <h3 style="color: red">${requestScope.error}</h3>

                                    <div style="display: flex; justify-content: space-between; align-items: center;">
                                        <div>
                                            <a href="LessonManage?action=addlesson&cid=${cid}&moduleid=${module_id}" class="btn btn-outline-success">Add New Lesson</a>
                                            <a href="createquiz?action=addQuiz&cid=${cid}&moduleid=${module_id}" class="btn btn-outline-primary">Add New Quiz</a>
                                        </div>
                                        <div>
                                            <form action="ModuleManage?action=delete&cid=${cid}&moduleId=${module_id}" method="post">
                                                <input type="submit" class="btn btn-outline-danger" value="Delete Module">
                                            </form>
                                        </div>
                                    </div>

                                    <div class="tab-pane fade show" id="Courses">
                                        <div class="card-body pb-2">
                                            <c:forEach items="${list_module}" var="module">
                                                <c:if test="${module.moduleid == module_id}">
                                                    <div>
                                                        <form action="ModuleManage?action=update&moduleId=${module_id}&cid=${cid}" method="post">
                                                            <input style="width: 50%" type="text" name="module_name" value="${module.modulename}">
                                                            <input style="height: 50.38px;
                                                                   width: auto;
                                                                   border-radius: 4px;" type="submit" class="btn btn-outline-primary" value="Update Module Name">
                                                        </form>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <div class="card-body pb-2">
                                            <h5>Lesson</h5>
                                            <c:forEach items="${list_lesson_in_module}" var="lesson">
                                                <div id="course-${lesson.lessonname}" class="row card-body media align-items-center" style="border: 1px solid #ced4da;">
                                                    <div class="col-lg-2">
                                                        <div class="embed-responsive embed-responsive-16by9">
                                                            <iframe  id="videoFrame" class="embed-responsive-item" src="${lesson.lessonvideo}"  width="100%" height="auto" allowfullscreen=""></iframe>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <label class="form-label" style="color: black; font-size: 20px">Lesson Name: ${lesson.lessonname}</label><br>                                                        
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Content: ${lesson.lessoncontent}</label><br>
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Create By: ${lesson.createbyName}</label><br>
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Duration: ${lesson.duration}</label><br>
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Video: ${lesson.lessonvideo}</label><br>                                                
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <a href="LessonManage?action=updatelesson&cid=${cid}&lessonid=${lesson.getLessonid()}" class="btn btn-outline-success" style="padding-right: 6px">Update lesson</a>

                                                        <a  onclick="deleteQuestionModal(${lesson.getLessonid()}, ${cid}, ${lesson.getModuleid()})"
                                                            class="btn btn-outline-danger" style="margin-top: 2px;" data-toggle="modal" data-target="#delete-lessson-modal" >Delete lesson</a>

                                                    </div>
                                                </div>                                        
                                            </c:forEach>
                                            <br>
                                            <hr class="border-light m-0">
                                        </div>
                                        <div class="card-body pb-2">
                                            <h5>Quiz</h5>
                                            <c:forEach items="${requestScope.list_quiz_by_moduleId}" var="quiz">
                                                <div class="row card-body media align-items-center" style="border: 1px solid #ced4da;">
                                                    <div class="col-lg-2">
                                                        <div class="embed-responsive embed-responsive-16by9">
                                                            <iframe  id="videoFrame" class="embed-responsive-item" src="${lesson.lessonvideo}"  width="100%" height="auto" allowfullscreen=""></iframe>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <label class="form-label" style="color: black; font-size: 20px">Quiz Name: ${quiz.quizName}</label><br>                                                        
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Quiz Time: ${quiz.quizTime}</label><br>
                                                        <label class="form-label" style="color: #000; font-size: 15px ">Pass Score: ${quiz.passScore}</label><br>                                               
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <a href="#" class="btn btn-outline-success" style="padding-right: 6px">Update Quiz</a>

                                                        <a class="btn btn-outline-danger" style="margin-top: 2px;" data-toggle="modal" data-target="#delete-lessson-modal" >Delete Quiz</a>

                                                    </div>
                                                </div>                                        
                                            </c:forEach>
                                            <br>
                                            <hr class="border-light m-0">
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>

        </div>


        <!--Form delete-->
        <div class="modal fade" id="delete-lessson-modal" >
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title" id="delete-modal-label">Delete</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this lesson ?</p>
                    </div>
                    <div class="modal-footer">
                        <form action="LessonManage?action=deletelesson" method="POST">
                            <div class="form-group" style="display: none">
                                <input type="text" class="form-control" id="idlessonInput" name="lessonid">
                                <input type="text" class="form-control" id="idCourseInput" name="cid">
                                <input type="text"  class="form-control" id="idModuleInput" name="module">
                            </div>
                            <button type="button" class="as btn-secondary" data-dismiss="modal">No</button>
                            <button type="submit" class="as btn-danger">Yes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript">
        </script>



        <script>
            function deleteQuestionModal(lessonid, courseId, moduleId) {
                let inputlessonId = document.querySelector("#idlessonInput");
                let inputICourseId = document.querySelector("#idCourseInput");
                let inputModuleId = document.querySelector("#idModuleInput");

                inputlessonId.value = lessonid;
                inputICourseId.value = courseId;
                inputModuleId.value = moduleId;
            }

        </script>




        <script>
            function deleteCourse(courseId) {
                if (confirm('Are you sure you want to delete this course?')) {
                    fetch('course-manage?action=delete&cid=' + courseId, {
                        method: 'POST'
                    })
                            .then(response => response.json())
                            .then(data => {
                                if (data.success) {
                                    // Cập nhật UI
                                    var courseElement = document.getElementById('course-' + courseId);
                                    var statusLabel = courseElement.querySelector('label[style="color: #00cc66; font-size: 15px "]');
                                    if (statusLabel) {
                                        statusLabel.style.color = '#cc0033';
                                        statusLabel.textContent = 'Inactive';

                                        alert('Course deleted successfully');
                                        window.location.reload();
                                    }

                                } else {
                                    alert('An error occurred while deleting the course');
                                }
                            })
                            .catch(error => {
                                console.error('Error:', error);
                                alert('An error occurred while deleting the course');
                            });
                }
            }

            function activeCourse(courseId) {
                if (confirm('Are you sure you want to active this course?')) {
                    fetch('course-manage?action=activate&cid=' + courseId, {
                        method: 'POST'
                    })
                            .then(response => response.json())
                            .then(data => {
                                if (data.success) {
                                    // Cập nhật UI
                                    const courseElement = document.getElementById('course-' + courseId);
                                    var statusLabel = courseElement.querySelector('label[style="color: #cc0033; font-size: 15px "]');
                                    const activeButton = document.getElementById('activeBtn-' + courseId);
                                    const deleteButton = document.getElementById('deleteBtn-' + courseId);
                                    if (statusLabel) {
                                        statusLabel.style.color = '#00cc66';
                                        statusLabel.textContent = 'Active';
                                        alert('Course activated successfully');
                                        window.location.reload();
                                    }
                                } else {
                                    alert('An error occurred while activating the course');
                                }
                            })
                            .catch(error => {
                                console.error('Error:', error);
                                alert('An error occurred while activating the course');
                            });
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                var moduleList = document.getElementById('moduleList');
                var links = moduleList.getElementsByClassName('list-group-item');

                // Lấy moduleId từ URL (nếu có)
                var urlParams = new URLSearchParams(window.location.search);
                var currentModuleId = urlParams.get('moduleId');

                for (var i = 0; i < links.length; i++) {
                    links[i].addEventListener('click', function (event) {
                        // Loại bỏ class 'active' từ tất cả các thẻ
                        for (var j = 0; j < links.length; j++) {
                            links[j].classList.remove('active');
                        }

                        // Thêm class 'active' vào thẻ được click
                        this.classList.add('active');
                    });

                    // Đặt active cho module hiện tại (nếu có)
                    if (currentModuleId && links[i].getAttribute('data-module-id') === currentModuleId) {
                        links[i].classList.add('active');
                    }
                }
            });
        </script>

    </div>
    <jsp:include page="common/footer.jsp"></jsp:include>

    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="lib/wow/wow.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/waypoints/waypoints.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>
</html>
