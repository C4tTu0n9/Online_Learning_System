<%-- 
    Document   : MentorManage.jsp
    Created on : Jun 18, 2024, 8:10:00 PM
    Author     : tuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Mentors</title>
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
        </style>
    </head>
    <body>
        <jsp:include page="common/menu.jsp"></jsp:include>


            <div class="container-fluid">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-lg-10">
                            <h1 class="display-3 text-dark animated slideInDown">Manage</h1>
                        </div>
                    </div>
                </div>
                <div class="container light-style flex-grow-1 container-p-y">

                    <div class=" overflow-hidden">
                        <div class="row no-gutters row-bordered row-border-light">
                            <div class="col-md-3 pt-0">
                                <div class="list-group list-group-flush account-settings-links">
                                    <!-- <a class="list-group-item list-group-item-action" data-toggle="list" href="#account-info">Information</a>-->
                                    <!--                                <a class="list-group-item list-group-item-action" data-toggle="list" href="#account-social-links">Social links</a>
                                                                    <a class="list-group-item list-group-item-action" data-toggle="list" href="#account-connections">Connections</a>-->
                                    <!--                                <a class="list-group-item list-group-item-action" data-toggle="list" href="#account-notifications">Notifications</a>-->
                                    <a class="list-group-item list-group-item-action" href="course-manage">Courses </a>
                                <c:if test="${my_role == 2}">
                                    <a class="list-group-item list-group-item-action active" href="mentor-manage">Mentors</a>
                                </c:if>
                            </div>
                        </div>


                        <div class="col-md-9 card_mine">
                            <div class="tab-content">
                                <div class="tab-pane fade active show" id="mentor-manage">
                                    <h3 style="color: red">${requestScope.error}</h3>
                                    <c:if test="${my_role == 2}">
                                        <div>
                                            <form action="mentor-manage?action=add_mentor" method="post">
                                                <input type="file">
                                                <div>
                                                    Upload file to add account for your mentors!
                                                </div>
                                                <button type="submit" class="btn btn-outline-primary">Submit</button>
                                            </form>
                                        </div>
                                    </c:if>
                                    <div class="tab-pane fade show" id="mentor-manage">
                                        <div class="card-body pb-2">
                                            <c:forEach items="${list_managed_mentor}" var="mentor">
                                                <form action="mentor-manage?action=delete&mentor_id=${mentor.profile_id}" method="post" onsubmit="return confirmDelete()">
                                                    <div class="row card-body media align-items-center" style="border: 1px solid #ced4da;">
                                                        <div class="col-lg-2">
                                                            <img src="${mentor.avt}" width="100px" height="100px" alt="alt"/>
                                                        </div>
                                                        <div class="col-lg-7">
                                                            <label class="form-label" style="color: black; font-size: 20px">Mentor Name: ${mentor.fullname}</label>&nbsp;&nbsp;<br>
                                                            <label class="form-label" style="color: black; font-size: 17px">Mentor Email: ${mentor.email}</label><br>
                                                            <label class="form-label" style="color: black; font-size: 15px">Mentor Gender: ${mentor.gender ? 'Male' : 'Female'}</label>&nbsp;&nbsp;
                                                        </div>
                                                        <c:if test="${my_role == 2}">
                                                            <div class="col-lg-3">
                                                                <button type="submit" class="btn btn-outline-danger">Delete Mentor</button>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </form>
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
            <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
            <script type="text/javascript">
                                                    function confirmDelete() {
                                                        return confirm("Are you sure you want to delete this mentor?");
                                                    }
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
