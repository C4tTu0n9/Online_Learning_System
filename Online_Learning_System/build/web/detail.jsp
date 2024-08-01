<%-- 
    Document   : detail
    Created on : Jun 7, 2024, 2:07:40 AM
    Author     : Tuan Anh(Gia Truong)
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

        <!-- AOS Library Stylesheet -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Slick CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css" >
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">




        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <style>

            img.avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                margin-right: 10px;
            }


            /*Rating star*/


            .class {
                width: 100%;
                max-width: 1270px;
                margin: 20px auto;
                padding: 20px;

                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }


            .name{
                display: inline-block;
            }
            .date{
                margin-left: 53px;
            }
            .review-rating{
                margin-left: 53px;
                margin-bottom: 20px;
            }
            .review-body{
                width: 70%;
                margin-top: 16px;
            }
            .review-heading{
                width: 30%;
            }

            .material-icons{
                font-size: 19px;
                color:#ffa726;
            }
            .review-item{
                border-top: 1px solid #ccc;
                margin-top: 30px;
                padding-top: 30px;
            }

            .rating-avg {
                display: flex;
                align-items: center;

            }

            /* CSS để tùy chỉnh màu sắc của sao rỗng */
            .empty-star {
                color: #ccc; /* Đổi màu sao rỗng thành màu mong muốn */
            }


        </style>

    </head>

    <body>

        <%
            String courseId =request.getParameter("cid");
                String lastLessonId = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastLessonId_" + courseId)) {
                        lastLessonId = cookie.getValue();
                        break;
                    }
                }
                }
                
            Object lessonidObj = request.getAttribute("lessonid");
            if (lastLessonId == null) {
            // Nếu không có cookie, bạn có thể đặt giá trị mặc định, ví dụ: bài học đầu tiên
            String lessonid = lessonidObj.toString();
            lastLessonId = lessonid; 
            }
        %>

        <jsp:include page="common/menu.jsp"></jsp:include>


            <!-- Header Start -->
            <div class="container-fluid bg-primary py-5 mb-5 page-header">
                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-10 text-center">
                            <h1 class="display-3 text-white animated slideInDown">Course Detail</h1>
                            <div class="breadcrumb justify-content-center">
                                <p class="breadcrumb-item"><a class="text-white" href="home">Home</a></p>
                                <i class="fa fa-angle-double-right pt-1 px-3 text-white"></i>
                                <p class="breadcrumb-item text-white active">Course Detail</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Header End -->



            <!-- Detail Start -->
            <div data-aos="fade-up" class="container-fluid py-5">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-lg-8">
                            <div  class="mb-5">
                                <div class="position-relative mb-5">
                                    <h6 style="color: #F14D5D !important" class="d-inline-block position-relative text-secondary text-uppercase pb-2">Course Detail</h6>
                                    <h1 class="display-4">${getCourseByID.getCourse_name()}</h1>
                            </div>
                            <img style="width: 100%; /* Đảm bảo rằng ảnh sẽ rộng hết phần tử cha */
                                 height: 480px; /* Chiều cao mong muốn */; /* Đảm bảo tỷ lệ khung hình bảo toàn */
                                 border: 2px solid #ccc; /* Định dạng viền với độ dày và màu sắc tùy chọn */
                                 border-radius: 10px; /* Định dạng viền cong (nếu bạn muốn) */
                                 box-sizing: border-box; /* Đảm bảo rằng kích thước của viền được tính vào kích thước của phần tử */
                                 "data-aos="fade-up" class="img-fluid rounded w-100 mb-4 " src="${getCourseByID.getImage()}" alt="Image">

                            <p data-aos="fade-up" >${getCourseByID.getDescription()}</p>
                        </div>



                        <!--Relate course-->
                        <h2 class="mb-3">Related Courses</h2>

                        <div class=" related-carousel position-relative" >
                            <c:forEach items="${listCourse_relate}" var="o"> 

                                <div style="margin-left: 10px" data-aos="fade-up" class="col-lg-11 col-md-11">
                                    <div class="course-item bg-light">
                                        <div class="position-relative overflow-hidden">
                                            <img style="width: 100%; /* Đảm bảo rằng ảnh sẽ rộng hết phần tử cha */
                                                 height: 200px; /* Chiều cao mong muốn */; /* Đảm bảo tỷ lệ khung hình bảo toàn */
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

                                                        <a href="dataTransferLesson?cid=${o.getCourse_id()}&lessonid=${o.getFirstlessonid()}&createBy=${o.getCreate_by()}&price=${o.getPrice()}&ndck=${sessionScope.profile.fullname}&address=${getCourseByID.getCourse_id()}" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Join Now</a>

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
                                        <div class="text-center p-4 pb-0">     

                                            <h3 class="mb-0">${o.getFormattedPrice()}₫</h3>
                                            <!--Star-->
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
                            <!--end course relate-->
                        </div>

                    </div>




                    <div class="col-lg-4 mt-5 mt-lg-0">
                        <div class="bg-primary mb-5 py-3">
                            <h3 class="text-white py-3 px-4 m-0">Course Features</h3>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Instructor</h6>
                                <h6 class="text-white my-3">${getCourseByID.getInstructor()}</h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Rating</h6>
                                <h6 class="text-white my-3">${avgRatingCourse} <small>(${amountRatingCourse})</small></h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Lectures</h6>
                                <h6 class="text-white my-3">${getCourseByID.getLeture()}</h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Duration</h6>
                                <h6 class="text-white my-3">${getCourseByID.getStudy_time()}</h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Skill level</h6>
                                <h6 class="text-white my-3">All Level</h6>
                            </div>
                            <div class="d-flex justify-content-between px-4">
                                <h6 class="text-white my-3">Language</h6>
                                <h6 class="text-white my-3">English</h6>
                            </div>
                            <h5 class="text-white py-3 px-4 m-0">Course Price: ${getCourseByID.getFormattedPrice()}₫</h5>
                            <c:if test="${sessionScope.account == null}">
                                <div class="py-3 px-4">
                                    <a style="background-color: #ee2a3d" class="btn btn-block btn-secondary py-3 px-5" href="join?action=login&cid=${getCourseByID.getCourse_id()}">Enroll Now</a>
                                </div>
                            </c:if>

                            <c:if test="${sessionScope.account != null}">
                                <!--Nguoi này là ngươi tao ra khoa hoc duoc join truc tiep-->
                                <c:choose>
                                    <c:when test="${sessionScope.account.getAccount_id() == getCourseByID.getCreate_by()}">
                                        <div class="py-3 px-4">
                                            <a style="background-color: #ee2a3d" class="btn btn-block btn-secondary py-3 px-5"href="lesson?cid=${getCourseByID.getCourse_id()}&lessonid=<%=lastLessonId%>&createBy=${getCourseByID.getCreate_by()}">Join Now</a>
                                        </div>
                                    </c:when>
                                        
                                    <c:otherwise>

                                        <c:set var="currentCourseId" value="${getCourseByID.getCourse_id()}"/>
                                        <c:choose>

                                            <c:when test="${fn:length(listEnrollment) > 0}">
                                                <c:forEach items="${listEnrollment}" var="o">
                                                    <c:if test="${currentCourseId == o.getCourseid()}">
                                                        <div class="py-3 px-4">
                                                            <a style="background-color: #ee2a3d" class="btn btn-block btn-secondary py-3 px-5"href="dataTransferLesson?cid=${getCourseByID.getCourse_id()}&lessonid=<%=lastLessonId%>&createBy=${getCourseByID.getCreate_by()}&price=${getCourseByID.getPrice()}&ndck=${sessionScope.profile.fullname}&address=${getCourseByID.getCourse_id()}">Resume</a>
                                                        </div>
                                                        <c:set var="isPaid" value="true"/>
                                                    </c:if>
                                                </c:forEach>

                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="isPaid" value="false"/>
                                            </c:otherwise>




                                        </c:choose> 

                                        <c:if test="${isPaid != true}">
                                            <div class="py-3 px-4">
                                                <a style="background-color: #ee2a3d" class="btn btn-block btn-secondary py-3 px-5"href="dataTransferLesson?cid=${getCourseByID.getCourse_id()}&lessonid=<%=lastLessonId%>&createBy=${getCourseByID.getCreate_by()}&price=${getCourseByID.getPrice()}&ndck=${sessionScope.profile.fullname}&address=${getCourseByID.getCourse_id()}" >Enroll Now</a>
                                            </div>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>


                        </div>

                        <div class="mb-5 wow animate__animated animate__fadeInUp" data-wow-delay="0.1s">
                            <h2 class="mb-3">Categories</h2>

                            <ul class="list-group list-group-flush"> 
                                <c:forEach items="${listAllCategory}" var="o">
                                    <li class="list-group-item d-flex justify-content-between align-items-center px-0 wow animate__animated animate__fadeInUp" data-wow-delay="0.2s">
                                        <a href="listCourseSeverlet?cateid=${o.getCategory_id()}" class="text-decoration-none h6 m-0">${o.getCategory_name()}</a>
                                        <span style="background-color: #06bbcc;
                                              border-radius: 4px;" class="badge badge-primary badge-pill">${o.getNumberofCate()}</span>
                                    </li>

                                </c:forEach>
                            </ul>

                        </div>


                    </div>
                </div>
            </div>
        </div>


        <!--Ratting star about course-->
        <!--Ratting star about course-->
        <div class="class">
            <div style="margin-bottom: 26px;font-size: 35px">
                <h3>PRODUCT REVIEWS</h3>
            </div>
            <div class="rating-avg" >
                <span style="font-size: 30px; margin-right:20px">${avgRatingCourse}/5</span>
                <div class="rating-stars">
                    <c:forEach var="i" begin="1" end="${avgRatingCourse}">
                        <i class="material-icons" style="font-size: 30px">star</i>
                    </c:forEach>
                    <c:forEach var="i" begin="${avgRatingCourse +1}" end="5">
                        <i class="material-icons" style="font-size: 30px">star_border</i>
                    </c:forEach>
                </div>

            </div>
            <small class="text-muted">${amountRatingCourse} ratings</small>
            <!-- Reviews -->

            <div id="reviews">
                <ul class="reviews">
                    <c:forEach items="${listRatings}" var="o">
                        <li class="review-item" style="display: flex;">
                            <div class="review-heading">
                                <img src="${o.getAvatar()}" alt="" class="avatar">
                                <h5 class="name">${o.getFullname()}</h5>
                                <p class="date">${o.getDatecreate()}</p>
                                <div class="review-rating" style="margin-left: 51px;">
                                    <c:forEach var="i" begin="1" end="5">
                                        <i class="material-icons">
                                            <c:choose>
                                                <c:when test="${i <= o.getStar()}">star</c:when>
                                                <c:otherwise>star_border</c:otherwise>
                                            </c:choose>
                                        </i>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="review-body">
                                <p>${o.getComment()}</p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <jsp:include page="common/footer.jsp"></jsp:include>

        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary rounded-0 btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>


        <script src="lib/wow/wow.min.js"></script>

        <script src="js/main.js"></script>
        <!-- AOS Library Javascript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
        <script>
            AOS.init();
        </script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/wow/1.1.2/wow.min.js"></script>
        <script>
            new WOW().init();
        </script>

        <!-- Template Javascript -->
        <script src="js/index.js"></script>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Slick JS -->
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>


        <!-- JavaScript Libraries -->


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



        <script>
            $(document).ready(function () {
                $('.related-carousel').slick({
                    infinite: true,
                    slidesToShow: 3, // Số slide hiển thị cùng lúc
                    slidesToScroll: 1, // Số slide sẽ di chuyển mỗi khi bạn nhấn nút điều hướng
                    autoplay: true,
                    autoplaySpeed: 2000, // Thời gian giữa các lần tự động chuyển slide (ms)
                    dots: true, // Hiển thị các chấm điều hướng
                    responsive: [
                        {
                            breakpoint: 1024, // Dưới 1024px, số slide hiển thị là 2
                            settings: {
                                slidesToShow: 2,
                                slidesToScroll: 1
                            }
                        },
                        {
                            breakpoint: 600, // Dưới 600px, số slide hiển thị là 1
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 1
                            }
                        }
                    ]
                });

                // Sự kiện click cho các thẻ a bên trong carousel
                $('.related-carousel .course-item a').on('click', function (e) {
                    e.stopPropagation(); // Ngăn chặn sự kiện click lan sang các phần tử khác trong carousel
                    // Xử lý các hành động khi click vào phần tử trong carousel
                });
            });

        </script>
    </body>

</html>
