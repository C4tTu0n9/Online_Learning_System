<%-- 
    Document   : lesson
    Created on : Jun 10, 2024, 12:53:18 AM
    Author     : Tuan Anh(Gia Truong)
--%>
<%@page import="Model.Enrollment"%>
<%@page import="java.util.ArrayList"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="YoutubeAPI.YoutubeDuration" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Student - Take course</title>

    <!-- Prevent the demo from appearing in search engines (REMOVE THIS) -->
    <meta name="robots" content="noindex">

    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">

    <!-- Simplebar -->
    <link type="text/css" href="assets/vendor/simplebar.css" rel="stylesheet">

    <!-- Material Design Icons  -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Roboto Web Font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">

    <!-- MDK -->
    <link type="text/css" href="assets/vendor/material-design-kit.css" rel="stylesheet">

    <!-- Sidebar Collapse -->
    <link type="text/css" href="assets/vendor/sidebar-collapse.min.css" rel="stylesheet">

    <!-- App CSS -->
    <!--    <link  href="assets/css/style.css" rel="stylesheet">-->


    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->

    <link href="lib/owlcarousels/assets/owl.carousel.min.css" rel="stylesheet">
    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <!-- Vendor CSS -->


    <style>
        .rounded-circle{
            height: 50px;
            width: 50px;
        }

        .breadcrumb-item + .breadcrumb-item::before {
            color: black;
        }
        .breadcrumb{
            margin-top: 10px;
        }


        .active-quiz {
            background-color: #f9c2ff; /* Màu nền cho quiz đã chọn */
        }
        
.lesson-completed {
    background-color: #d4edda; /* Màu nền cho bài học đã hoàn thành */
    text-decoration: line-through; /* Gạch ngang bài học đã hoàn thành */
}

.quiz-completed {
    background-color: #c3e6cb; /* Màu nền cho quiz đã hoàn thành */
    text-decoration: line-through; /* Gạch ngang quiz đã hoàn thành */
}
.scrollable-mentor-list {
    max-height: 80px; /* Đặt chiều cao tối đa cho phần tử */
    overflow-y: auto; /* Cho phép cuộn theo chiều dọc */
}

    </style>

</head>

<body>



    <jsp:include page="common/menu.jsp"></jsp:include>


        <div class="mdk-drawer-layout js-mdk-drawer-layout flex" data-fullbleed data-push data-has-scrolling-region>
            <div class="mdk-drawer-layout__content mdk-drawer-layout__content--scrollable">
                <div class="container-fluid">

                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="home">Home</a></li>
                        <li class="breadcrumb-item active">${lesson.getCoursename()}</li>
                </ol>
                <h1 class="page-heading h2">${lesson.getCoursename()}</h1>


                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="embed-responsive embed-responsive-16by9">
                                <iframe id="lessonVideo" class="embed-responsive-item" src="${lesson.getLessonvideo()}"title="0" frameborder="0" allow="0"  allowfullscreen></iframe>
                            </div>
                            <div class="card-body">
                                ${lesson.getLessoncontent()}
                            </div>
                        </div>

                        <!--comment-->

                        <div class="comments">
                            <h2>Comments</h2>
                            <!-- Thêm comment mới -->
                            <form action="lesson?status=insert" method="post">
                                <div class="comment-input">
                                    <img src="${sessionScope.profile.getAvt()}" alt="User Avatar" class="avatar">
                                    <textarea required="" name="content" rows="1" placeholder="Add a comment..."></textarea>
                                    <input type="hidden" name="cid" value="${lesson.getCourseid()}">
                                    <input type="hidden" name="lessonid" value="${lesson.getLessonid()}">
                                    <input type="hidden" name="parentCommentID" value="">
                                    <input type="hidden" name="createBy" value="${lesson.getCreateby()}">

                                    <button type="submit">Submit</button>
                                </div>
                            </form>





                            <!-- Hiển thị comment và reply -->
                            <div class="comment-list">
                                <c:forEach var="o" items="${mainComments}"> 
                                    <c:if test="${o.getLessonId() == lesson.getLessonid()}">
                                        <div class="comment">

                                            <img src="${o.getAvatar()}" alt="User1 Avatar" class="avatar">    

                                            <div class="comment-content">
                                                <p><strong>${o.getName()}</strong>  <span class="timestamp">${o.getTimeAgo()}</span></p>
                                                <p>${o.getComment()}</p> 




                                            </div> 
                                            <!-- Nút xóa comment -->
                                            <c:if test="${sessionScope.account.getAccount_id() == o.getAcccountId()}">
                                                <form action="lesson?status=delete" method="post" class="delete-form" onsubmit="return confirmDelete(this);">
                                                    <input type="hidden" name="cid" value="${lesson.getCourseid()}">
                                                    <input type="hidden" name="lessonid" value="${lesson.getLessonid()}">
                                                    <input type="hidden" name="disscussID" value="${o.getDisscussionID()}">
                                                    <input type="hidden" name="createBy" value="${lesson.getCreateby()}">

                                                    <input type="hidden" name="parent" value="null">
                                                    <button type="submit" class="del">Delete</button>
                                                </form>
                                            </c:if>

                                        </div>

                                        <div class="comment-list replies">
                                            <!-- Hiển thị các reply -->
                                            <c:forEach var="reply" items="${repliesMap[o.getDisscussionID()]}">
                                                <div class="comment replies">                    
                                                    <img src="${reply.getAvatar()}" alt="User Avatar" class="avatar">

                                                    <div class="comment-content">
                                                        <p><strong>${reply.getName()}</strong><span class="timestamp">${reply.getTimeAgo()}</span></p>
                                                        <p>${reply.getComment()}</p>


                                                    </div>          
                                                    <!--    Nút xóa reply 
                                                    -->  
                                                    <c:if test="${sessionScope.account.getAccount_id() == reply.getAcccountId()}">
                                                        <form action="lesson?status=delete" method="post" class="delete-form" onsubmit="return confirmDelete(this);">
                                                            <input type="hidden" name="cid" value="${lesson.getCourseid()}">
                                                            <input type="hidden" name="lessonid" value="${lesson.getLessonid()}">
                                                            <input type="hidden" name="disscussID" value="${reply.getDisscussionID()}">
                                                            <input type="hidden" name="parent" value="${reply.getParentId()}">
                                                            <input type="hidden" name="createBy" value="${lesson.getCreateby()}">
                                                            <button type="submit" class="del">Delete</button>
                                                        </form>
                                                    </c:if>
                                                </div>
                                            </c:forEach>
                                        </div> 


                                        <!-- Form để thêm reply -->
                                        <form action="lesson?status=insert" method="post" class="reply-form">
                                            <input type="hidden" name="lessonid" value="${lesson.getLessonid()}">
                                            <input type="hidden" name="parent" value="${o.getDisscussionID()}">
                                            <!--Chuyển lai trang có cid hiện tại-->
                                            <input type="hidden" name="cid" value="${lesson.getCourseid()}">
                                            <input type="hidden" name="createBy" value="${lesson.getCreateby()}">

                                            <!-- Thêm class `reply-textarea` vào textarea để dễ dàng chọn từ JavaScript -->
                                            <textarea required="" name="content" rows="1" placeholder="Reply to this comment..." class="reply-textarea" style="display: none;"></textarea>
                                            <button type="button" class="reply-btn">Reply</button>
                                            <button type="submit"  class="submit-reply-btn">Submit Reply</button>
                                            <button type="button" class="cancel-reply-btn">Cancel</button>
                                        </form>



                                    </c:if>
                                </c:forEach>
                            </div>

                        </div>

                        <script>
                            function confirmDelete(form) {
                                return confirm("Are you sure you want to delete this comment?");
                            }
                        </script>

                        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                var replyButtons = document.querySelectorAll('.reply-btn');
                                var cancelReplyButtons = document.querySelectorAll('.cancel-reply-btn');

                                replyButtons.forEach(function (button) {
                                    button.addEventListener('click', function () {
                                        // Hiển thị ô textarea, nút Submit và nút Cancel
                                        var form = this.parentElement;
                                        form.querySelector('.reply-textarea').style.display = 'block';
                                        form.querySelector('.submit-reply-btn').style.display = 'inline-block';
                                        form.querySelector('.cancel-reply-btn').style.display = 'inline-block';

                                        // Ẩn nút Reply
                                        this.style.display = 'none';
                                    });
                                });

                                cancelReplyButtons.forEach(function (button) {
                                    button.addEventListener('click', function () {
                                        // Ẩn ô textarea, nút Submit và nút Cancel
                                        var form = this.parentElement;
                                        form.querySelector('.reply-textarea').style.display = 'none';
                                        form.querySelector('.submit-reply-btn').style.display = 'none';
                                        this.style.display = 'none';

                                        // Hiển thị lại nút Reply
                                        form.querySelector('.reply-btn').style.display = 'inline-block';
                                    });
                                });
                            });
                        </script>



                        <!-- Lessons -->
                        <ul class="">

                        </ul>
                    </div>


                    <!--list lesson-->
                    <div  class="col-md-4">
                        <div class="card">

                            <div class="card-body " >

                                <div style="background-color: #ffff" class="block-header block-header-default bg-gd-primary">
                                    <i style="margin-right: 4px; " class="fa fa-fw fa-list text-inherit"></i>
                                    <h3 class="block-title font-w800 text-inherit ">   DANH SÁCH BÀI HỌC</h3>

                                    <!--                                    <div class="block-options">
                                                                            <button type="button" class="btn-block-option " data-toggle="block-option" data-action="content_toggle">
                                                                                <i class="fa fa-fw fa-chevron-up"></i>
                                                                            </button>
                                                                        </div>-->
                                </div>

                                <div class="child-hai">  
                                    <c:set var="index" value="1" /> <!-- Tăng biến đếm -->
                                    <c:forEach items="${moduleList}" var="o" varStatus="status">
                                     
                                        <div class="dev" >
                                            <div style="background-color: #edeff1" class="block-header block-header-default">
                                                <h3 class="block-title font-w800 text-black ">${status.index + 1}. ${o.getModulename()}</h3>

                                                <div class="block-options">
                                                    <button type="button" class="btn-block-option " data-toggle="block-option" data-action="content_toggle">
                                                        <i class="fa fa-fw fa-chevron-up"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <c:forEach items="${lessonList}" var="i">
                                                <div class="module-content">     
                                                    <c:if test="${o.getModulename() == i.getModulname()}">
                                                        <a style="color: black" href="lesson?cid=${i.getCourseid()}&lessonid=${i.getLessonid()}&createBy=${i.getCreateby()}" class="btn btn-block btn--col module-lesson" data-lessonid="${i.getLessonid()}">
                                                         ${index}. ${i.getLessonname()} 
                                                            <div>
                                                                <small class="text-muted module-lesson" style="color: black">${ YoutubeDuration.convertToMinutesAndSeconds(i.getDuration())}</small>
                                                            </div>
                                                        </a> 
                                                         <c:set var="index" value="${index + 1}" />
                                                    </c:if>

                                               
                                                </div>
                                            </c:forEach>
                                                
                                            <!--List quiz here-->
                                            <c:forEach items="${quizLits}" var="j" varStatus="status">
                                                <div class="module-content">
                                                    <c:if test="${o.getModuleid() == j.getModuleId()}">
                                                        <a style="color: black"  class="btn btn-block btn--col module-lesson"  data-quizid="${j.getQuizId()}"  href="doquiz?mid=${j.getModuleId()}&cid=${j.course_id}"> 
                                                            ${index}. ${j.getQuizName()}
                                                            <div>
                                                                <small class="text-muted module-lesson" style="color: black">Do quiz</small>
                                                            </div>
                                                        </a>
                                                        <c:set var="index" value="${index + 1}" />
                                                    </c:if>
                                                </div>
                                            </c:forEach>   
                                        </div>

                                    </c:forEach>
                                </div>

                            </div>
                        </div>
                        
                         <div class="card">
                           <div class="card-header bg-white scrollable-mentor-list"> 
                               <c:forEach items="${mentorList}" var="o">
                            <div class="card-header bg-white">
                               
                                <div class="media">
                                    <div class="media-left media-middle">
                                        <img src="${o.getAvatar()}"  class="rounded-circle">
                                    </div>
                                    <div class="media-body media-middle">
                                        <h4 class="card-title"><a href="#">${o.getFullname()}</a></h4>
                                        <p class="card-subtitle">Instructor</p>
                                    </div>
                                    <a class="buttons" href="messenger?sender_id=${sessionScope.account.getAccount_id()}&receiver_id=${o.getProfileid()}">Chat Now</a>
                                </div>
                              
                            </div>
                              </c:forEach>
                            </div>
                        </div>

                        <div class="card">
                            <ul class="list-group list-group-fit">
                                <li class="list-group-item">
                                    <div class="media">
                                        <div class="media-left">
                                            <i class="material-icons text-muted-light">schedule</i>
                                        </div>
                                        <div class="media-body media-middle">
                                            ${totalTime}
                                            <!--                                            2 <small class="text-muted">hrs</small> &nbsp; 26 <small class="text-muted">min</small>-->
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="media">
                                        <div class="media-left">
                                            <i class="material-icons text-muted-light">assessment</i>
                                        </div>
                                        <div class="media-body media-middle">Beginner</div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Ratings</h4>
                            </div>
                            <div class="card-body">
                                <div class="rating">
                                    <c:forEach var="i" begin="1" end="${avgRatingCourse}">
                                        <i class="material-icons" >star</i>
                                    </c:forEach>
                                    <c:forEach var="i" begin="${avgRatingCourse +1}" end="5">
                                        <i class="material-icons" >star_border</i>
                                    </c:forEach>
                                </div>
                                <small class="text-muted">
                                    <fmt:formatNumber value="${amountRatingCourse}" type="number" maxFractionDigits="0" /> ratings
                                </small>
                            </div>
                        </div>

                        <a href="#" class="btn btn-default btn-block">
                            <i class="material-icons btn__icon--left">help</i> Get Help
                        </a>
                    </div>
                </div>

            </div>
        </div>






    </div>


    <!-- jQuery -->
    <script src="assets/vendor/jquery.min.js"></script>

    <!-- Bootstrap -->
    <script src="assets/vendor/popper.min.js"></script>
    <script src="assets/vendor/bootstrap.min.js"></script>

    <!-- Simplebar -->
    <!-- Used for adding a custom scrollbar to the drawer -->
    <script src="assets/vendor/simplebar.js"></script>

    <!-- MDK -->
    <script src="assets/vendor/dom-factory.js"></script>
    <script src="assets/vendor/material-design-kit.js"></script>

    <!-- Sidebar Collapse -->
    <script src="assets/vendor/sidebar-collapse.js"></script>

    <!-- App JS -->
    <script src="assets/js/main.js"></script>

    <script src="js/module-toggle.js"></script>




    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="lib/easings/easing.min.js"></script>
    <script src="lib/waypointss/waypoints.min.js"></script>
    <script src="lib/counterup/counterup.min.js"></script>

    <script src="lib/wow/wow.min.js"></script>


    <!-- Template Javascript -->
    <script src="js/index.js"></script>
    <!-- Template Javascript -->
    <script src="js/main.js"></script>



</body>


<!-- Mirrored from learnplus.frontendmatter.com/student-take-course.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 09 Jun 2018 08:42:29 GMT -->
</html>