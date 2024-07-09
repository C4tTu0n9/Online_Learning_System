<%-- 
    Document   : mentor_add_lesson
    Created on : Jun 24, 2024, 7:41:26 PM
    Author     : Tuan Anh(Gia Truong)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <!-- Mirrored from learnplus.frontendmatter.com/fixed-instructor-lesson-add.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 09 Jun 2018 08:43:05 GMT -->
    <head>
        <meta charset="utf-8">
        <title>Online Learning</title>
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

        <link href="css/add_lesson.css" rel="stylesheet">
        <!-- Material Design Icons  -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">


        <style>
            .custom-select{
                display:inline-block;
                max-width:100%;
                height:calc(2.25rem + 2px);
                padding:.375rem 1.75rem .375rem .75rem;
                line-height:1.5;
                color:#495057;
                vertical-align:middle;
                background:#fff url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 4 5'%3E%3Cpath fill='%23333' d='M2 0L0 2h4zm0 5L0 3h4z'/%3E%3C/svg%3E") no-repeat right .75rem center;
                background-size:8px 10px;
                border:1px solid #eceeef;
                border-radius:.25rem;
                -webkit-appearance:none;
                -moz-appearance:none;
                appearance:none
            }
            .custom-select:focus{
                border-color:#9acffa;
                outline:none
            }
            .custom-select:focus::-ms-value{
                color:#495057;
                background-color:#fff
            }
            .custom-select[multiple]{
                height:auto;
                background-image:none
            }
            .custom-select:disabled{
                color:#868e96;
                background-color:#e9ecef
            }
            .custom-select::-ms-expand{
                opacity:0
            }
            .custom-select-sm{
                height:calc(1.8125rem + 2px);
                padding-top:.375rem;
                padding-bottom:.375rem;
                font-size:75%
            }


            .material-icons.md-18 {
                font-size: 18px;
            }
            .material-icons {
                vertical-align: sub;
            }


            .form-group {
                margin-bottom: 25px;
            }

            .textContent {
                height: 200px;
                border-radius: 4px;
                border: 1px solid #333;
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

            .page-heading{
                margin-top: 20px
            }
        </style>

    </head>

    <body>

        <jsp:include page="common/menu.jsp"></jsp:include>

            <div class="container">

            <c:choose>
                <c:when test="${action == 'updatelesson'}">
                    <h1 class="page-heading h2">Update Lesson</h1>
                </c:when>
                
                <c:otherwise>
                    <h1 class="page-heading h2">Add Lesson</h1>
                </c:otherwise>
            </c:choose>
                <div class="card">
                    <div class="card-body">
                        <form action="LessonManage" method="post">
                            <input type="hidden" value="${cid}" name="cid">
                        <div class="form-group row">
                            <label for="" class="form-control-label col-md-3">Lesson Name:</label>
                            <div class="col-md-6">
                                <input required="" value="${lessonName}" type="text" name="lessonName" class="form-control" placeholder="Write an awesome title">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="select" class="col-md-3  form-control-label">Module:</label>
                            <div class="col-md-4">
                                <select required="" class="custom-control custom-select form-control" name="module">
                                    <c:forEach items="${listModule}" var="o">
                                        <option value="${o.getModuleid()}"
                                                ${o.getModuleid() == moduleid ? "selected" : ""}>${o.getModulename()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="" class="form-control-label col-md-3">Lesson Content:</label>
                            <div class="col-md-6">
                                <textarea required=""e type="text" name="lessonContent" class="form-control textContent" placeholder="Write any thing in here">${lessonContent}</textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="video"  class="form-control-label col-md-3">Upload Video</label>
                            <div class="col-md-9">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <input required="" type="text" name="videoLink" id="videoLink" class="form-control" value="${videoLink}" />
                                            <small class="help-block text-muted-light"><i class="material-icons md-18">ondemand_video</i> <span class="icon-text">Paste Video</span></small>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <div class="embed-responsive embed-responsive-16by9">
                                                <iframe  id="videoFrame" class="embed-responsive-item" src="${videoLink}" allowfullscreen=""></iframe>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                                            <p class="text-danger">${msg}</p>
                        <div class="text-right mt-3">
                            <c:if test="${action == 'addlesson'}">
                                <input type="hidden" value="addlesson" name="action">
                                <button type="submit" name="add" class="btn btn-primary">Add Course</button>&nbsp;
                            </c:if>


                            <button type="button" onclick="cancelAdd(${cid}, ${moduleid})" name="cancel" class="btn btn-outline-danger md-btn-flat">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>



        <!-- Footer Start -->
        <jsp:include page="common/footer.jsp"></jsp:include>








        <!-- Required by Calendar (fullcalendar) -->
        <script src="assets/vendor/dropzone.min.js"></script>

        <script src="lib/wow/wow.min.js"></script>



        <!-- Init -->
        <script src="assets/js/fancytree.js"></script>

        <script>
                                document.getElementById('videoLink').addEventListener('input', function () {
                                    var videoUrl = this.value;
                                    document.getElementById('videoFrame').src = videoUrl;
                                });
        </script>

        <script>
            function cancelAdd(cid, moduleid) {
                window.location.href = '/Project_E-Learning/ModuleManage?moduleId=' + moduleid + '&cid=' + cid;
            }
        </script>

    </body>


    <!-- Mirrored from learnplus.frontendmatter.com/fixed-instructor-lesson-add.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 09 Jun 2018 08:43:05 GMT -->
</html>