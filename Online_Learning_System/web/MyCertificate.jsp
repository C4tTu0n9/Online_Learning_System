<%-- 
    Document   : Courses
    Created on : May 31, 2024, 1:24:26 PM
    Author     : tuong
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Course</title>
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
    </head>
    <body>
        <jsp:include page="common/menu.jsp"></jsp:include>

            <div class="container-fluid">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-lg-10">
                            <h1 class="display-3 text-dark animated slideInDown">My Courses</h1>
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
                                    <a class="list-group-item list-group-item-action active" data-toggle="list" href="#MyCouses-certificate">My Certificate</a>
                                    <a class="list-group-item list-group-item-action" data-toggle="list" href="#MyCouses-wishlist"></a>
                                </div>
                            </div>


                            <div class="col-md-9 card_mine">
                                <div class="tab-content">
                                    <div class="tab-pane fade active show" id="MyCouses-courses">
                                        <h3 style="color: red">${requestScope.error}</h3>


                                    <div class="tab-pane fade show" id="MyCouses-courses">
                                        <div class="card-body pb-2">
                                            <c:forEach items="${getListCertificateByAccountId}" var="o">
                                                <div class="row card-body media align-items-center" style="border: 1px solid #ced4da;">
                                                    <div class="col-lg-2">
                                                        <img src="${o.getImg()}"
                                                             width="100px" height="100px" alt="alt"/>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <label class="form-label" style="color: black; font-size: 15px">Course | ${o.getFullname_mentor()}</label><br>                                                        
                                                        <label class="form-label" style="color: #06BBCC; font-size: 27px ">${o.getCourse_name()}</label><br>
                                                        <div>
                                                            <progress  style="
                                                                      width: 80%;
                                                                      height: 15px;
                                                                      " max="100" value="100"></progress>&nbsp;&nbsp;
                                                            <label style="color: black; font-size: 15px;">100%</label>
                                                        </div>
                                                        <label class="form-label" style="color: #666666; font-size: 13px">${o.getIssuer_date()}</label><br>   
                                                    </div>
                                                    <div class="col-lg-2">
                                                        <a href="CertificateDetail?cid=${o.getCourse_id()}" class="btn btn-outline-primary">View Certificate</a>
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
            <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
            <script type="text/javascript">

            </script>

        </div>
        <!-- Header End -->

        <!-- Footer Start -->
              <jsp:include page="common/footer.jsp"></jsp:include>
        <!-- Footer End -->


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
