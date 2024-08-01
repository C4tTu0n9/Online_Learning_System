<%-- 
    Document   : index
    Created on : May 18, 2024, 12:18:29 PM
    Author     : tuong
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
        <style>
            @import url('https://fonts.googleapis.com/css?family=Dosis:400,600,700,800');
            .ms-auto-1{
                margin-left: 10px !important;
            }

            .product-slider__fav {
                color: #888e94;
                background: none;
                border: none;
                position: relative;
                outline: none;
                cursor: pointer;
                transition: all .3s;
                font-family: 'Dosis', sans-serif;
                font-weight: 600;
                text-transform: uppercase;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 40px;
                text-decoration: none;

            }


            .heart {
                margin-top :15px;
                margin-left: -20px;
                display: block;
                position: absolute;
                top: 10%;
                left: 10%;
                transform: translate(-50%, -50%) scale(0.7);
                pointer-events: none;
                width: 100px;
                height: 100px;
                background: url("https://res.cloudinary.com/muhammederdem/image/upload/v1536405215/starwars/heart.png") no-repeat;
                background-position: 0 0;
                transition: background-position 1s steps(28);
                transition-duration: 0s;
            }
            .heart.is-active {
                transition-duration: 1s;
                background-position: -2800px 0;
            }
        </style>    
    </head>
    <body>


        <jsp:include page="common/menu.jsp"></jsp:include>
            <!-- Carousel Start -->
            <div class="container-fluid p-0 mb-5">

                <div class="owl-carousel header-carousel position-relative">
                <c:forEach items="${listNewCourse}" var="o">
                    <div class="owl-carousel-item position-relative">
                        <img class="img-fluid" style="height: 670px" src="${o.getImage()}" alt="">
                        <div class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center" style="background: rgba(24, 29, 56, .7);">
                            <div class="container">
                                <div class="row justify-content-start">
                                    <div class="col-sm-10 col-lg-8">
                                        <h5 class="text-primary text-uppercase mb-3 animated slideInDown">New Online Courses</h5>
                                        <h1 class="display-3 text-white animated slideInDown">${o.getCourse_name()}</h1>
                                        <p class="fs-5 text-white mb-4 pb-2 description" >${o.getDescription()}</p>
                                        <a href="CourseDetail?cid=${o.getCourse_id()}" class="btn btn-primary py-md-3 px-md-5 me-3 animated slideInLeft">Read More</a>

                                        <c:choose>

                                            <c:when test="${sessionScope.account == null}">
                                                <!--nguoi dung chua dang nhap-->
                                                <a href="join?action=login" class="btn btn-light py-md-3 px-md-5 animated slideInRight">Join Now</a>
                                            </c:when>

                                            <c:otherwise><!--Nguoi dung da dang nhap-->

                                                        <a href="dataTransferLesson?cid=${o.getCourse_id()}&lessonid=${o.getFirstlessonid()}&createBy=${o.getCreate_by()}&price=${o.getPrice()}&ndck=${sessionScope.profile.fullname}&address=home" class="btn btn-light py-md-3 px-md-5 animated slideInRight">Join Now</a>
                                                 
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
        <!-- Carousel End -->


        <!-- Service Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="row g-4">
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                        <div class="service-item text-center pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-graduation-cap text-primary mb-4"></i>
                                <h5 class="mb-3">Skilled Instructors</h5>
                                <p>Advanced skill

                                    1. Extensive Professional Knowledge

                                    2.Practical Applicability</p>
                                <!--                                <p>   1.Kiến Thức Sâu Rộng về Chuyên Môn:</p>
                                                                <p>   2.Khả Năng Áp Dụng Thực Tiễn:</p>-->

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                        <div class="service-item text-center pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-globe text-primary mb-4"></i>
                                <h5 class="mb-3">Online Classes</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                        <div class="service-item text-center pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-home text-primary mb-4"></i>
                                <h5 class="mb-3">Home Projects</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                        <div class="service-item text-center pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-book-open text-primary mb-4"></i>
                                <h5 class="mb-3">Book Library</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Service End -->




        <!-- About Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="row g-5">
                    <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.1s" style="min-height: 400px;">
                        <div class="position-relative h-100">
                            <img class="img-fluid position-absolute w-100 h-100" src="img/about.jpg" alt="" style="object-fit: cover;">
                        </div>
                    </div>
                    <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.3s">
                        <h6 class="section-title bg-white text-start text-primary pe-3">About Us</h6>
                        <h1 class="mb-4">Welcome to OLS</h1>
                        <p class="mb-4">With the desire to bring quality, free knowledge to everyone, We - OLS created this website to provide flexible, affordable, and job-appropriate online learning programs for students. individuals and organizations worldwide. 
                        </p>
                        <p class="mb-4">We offer a variety of learning opportunities—from hands-on projects and courses to job-ready certificates and degree programs.</p>
                        <div class="row gy-2 gx-4 mb-4">
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Skilled Instructors</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Online Classes</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>International Certificate</p>
                            </div>
                            <!--                            <div class="col-sm-6">
                                                            <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Skilled Instructors</p>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Online Classes</p>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>International Certificate</p>
                                                        </div>-->
                        </div>
                        <a class="btn btn-primary py-3 px-5 mt-2" href="about.jsp">Read More</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- About End -->


        <!-- Categories Start -->
       <div class="container-xxl py-5 category">
            <div class="container">
                <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Categories</h6>
                    <h1 class="mb-5">Courses Categories</h1>
                </div>
                
           
                <div class="row g-3">
                    <div class="col-lg-7 col-md-6">
                        
                        <div class="row g-3">
                            
                            <div class="col-lg-12 col-md-12 wow zoomIn" data-wow-delay="0.1s">
                                <a class="position-relative d-block overflow-hidden" href="listCourseSeverlet?cateid=IT&action=course">
                                    <img class="img-fluid" src="img/IT.png" alt=""style="width: 100%;max-height: 300px;  ">
                                    <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                        <h5 class="m-0">Information Technology</h5>
                                    </div>
                                </a>
                            </div>
                                        
                                    <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.3s">
                                <a class="position-relative d-block overflow-hidden" href="listCourseSeverlet?cateid=MC&action=course">
                                    <img class="img-fluid" src="img/MC.png" alt=""style="width: 100%;max-height: 200px; ">
                                    <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                        <h5 class="m-0">Multimedia Communications</h5>
                                        
                                    </div>
                                </a>
                            </div>          
                                                                                                      
                            <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.5s">
                                <a class="position-relative d-block overflow-hidden" href="listCourseSeverlet?cateid=BSN&action=course">
                                    <img class="img-fluid" src="img/BSN.png" alt=""style="width: 100%;max-height: 200px;  ">
                                    <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                        <h5 class="m-0">Business</h5>
                                        
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 col-md-6 wow zoomIn" data-wow-delay="0.7s" style="min-height: 350px;">
                        <a class="position-relative d-block h-100 overflow-hidden" href="listCourseSeverlet?cateid=CS&action=course">
                            <img class="img-fluid position-absolute w-100 h-100" src="img/CS.png" alt="" style="object-fit: cover; ">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin:  1px;">
                                <h5 class="m-0">Computer Science</h5>
<!--                                <small class="text-primary">49 Courses</small>-->
                            </div>
                        </a>
                    </div>
                </div>
               
                
            </div>
        </div>
        <!-- Categories Start -->


        <!-- Courses Start -->

        <!--Populer Course--> 
        <div class="container-xxl py-5">
            <div class="container">
                <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Courses</h6>
                    <h1 class="mb-5">Popular Courses</h1>
                </div>
                <div class="row g-4 justify-content-center">
                    <c:forEach items="${listPopulerCourse}" var="o">
                        <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
                            <div class="course-item bg-light">
                                <div class="position-relative overflow-hidden">
                                    <img  style="width: 100%; /* Đảm bảo rằng ảnh sẽ rộng hết phần tử cha */
                                          height: 250px; /* Chiều cao mong muốn */; /* Đảm bảo tỷ lệ khung hình bảo toàn */
                                          border: 2px solid #ccc; /* Định dạng viền với độ dày và màu sắc tùy chọn */
                                          border-radius: 10px; /* Định dạng viền cong (nếu bạn muốn) */
                                          box-sizing: border-box; /* Đảm bảo rằng kích thước của viền được tính vào kích thước của phần tử */" class="img-fluid" src="${o.getImage()}" alt="">
                                    <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                                        <a href="CourseDetail?cid=${o.getCourse_id()}" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Read More</a>


                                        <c:choose>

                                            <c:when test="${sessionScope.account == null}">
                                                <!--nguoi dung chua dang nhap-->
                                                <a href="join?action=login&cid=${o.getCourse_id()}" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Join Now</a>

                                            </c:when>


                                            <c:otherwise><!--Nguoi dung da dang nhap-->


                                            <a href="dataTransferLesson?cid=${o.getCourse_id()}&lessonid=${o.getFirstlessonid()}&createBy=${o.getCreate_by()}&price=${o.getPrice()}&ndck=${sessionScope.profile.fullname}&address=home" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Join Now</a>



                                            </c:otherwise>

                                        </c:choose>

                                    </div>
                                </div>


                                <!--ADD TO WISHLIST-->
                                <c:if test="${sessionScope.account != null}">

                                    <!--nguoi dung chua dang nhap-->
                                    <div class ="wishlist">
                                        <div class="product-slider">
                                            <a class="product-slider__fav js-fav" href="my-courses?accid=${sessionScope.account.getAccount_id()}&cid=${o.getCourse_id()}">
                                                <c:choose>
                                                    <c:when test="${CourseIdList.contains(o.getCourse_id())}">
                                                        <div class="heart is-active">
                                                            <div class ="wishlist-text">ADD TO WISHLIST </div>
                                                        </div>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <div class="heart ">
                                                            <div class ="wishlist-text">ADD TO WISHLIST </div>
                                                        </div> 
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                        </div>
                                    </div>

                                </c:if>
                                <div class="text-center pb-0" style ="padding-left: 12px;padding-right: 12px;padding-bottom: 12px;padding-top: 7px">
                                    <h3 class="mb-0">${o.getFormattedPrice()}₫</h3>
                                    <div class="mb-3">
                                        <c:forEach var="i" begin="1" end="${o.getStar()}">
                                            <small class="fa fa-star text-primary"></small>
                                        </c:forEach>
                                        <c:forEach var="i" begin="${o.getStar() +1}" end="5">
                                            <small class="fa fa-star empty-star"></small>
                                        </c:forEach>

                                        <small>(${o.getSumOfRating()})</small>
                                    </div>
                                    <h5 class="mb-4">${o.getCourse_name()}</h5>
                                </div>
                                <div class="d-flex border-top">
                                    <small class="flex-fill text-center border-end py-2"><i class="fa fa-user-tie text-primary me-2"></i>${o.getInstructor()}</small>
                                    <small class="flex-fill text-center border-end py-2"><i class="fa fa-clock text-primary me-2"></i>${o.getStudy_time()}</small>
                                    <small class="flex-fill text-center py-2"><i class="fa fa-user text-primary me-2"></i>${o.getAmountSudentJoin()} Students</small>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>
        <!-- Courses End -->




        <jsp:include page="common/footer.jsp"></jsp:include>


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <script>      document.addEventListener('DOMContentLoaded', function () {
                var favButtons = document.querySelectorAll(".js-fav");
                favButtons.forEach(function (favButton) {
                    favButton.addEventListener("click", function (event) {
                        event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a> (chuyển hướng)

                        // Thực hiện ngầm hành động thêm vào wishlist
                        var href = this.getAttribute("href");
                        console.log("Adding to wishlist: " + href);

                        // Thực hiện thêm vào wishlist (Ví dụ: gửi yêu cầu AJAX)
                        var xhr = new XMLHttpRequest();
                        xhr.open("GET", href, true);
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState === 4 && xhr.status === 200) {
                                console.log("Added to wishlist");
                            }
                        };
                        xhr.send();

                        // Thay đổi trạng thái của icon trái tim
                        var heartIcon = this.querySelector('.heart');
                        if (heartIcon) {
                            heartIcon.classList.toggle("is-active");
                        }
                    });
                });
            });</script>
    </body>
</html>
