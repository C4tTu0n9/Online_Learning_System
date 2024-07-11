<%-- 
    Document   : AddNewCourse
    Created on : Jun 22, 2024, 3:34:29 PM
    Author     : tuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Course</title>
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
                width: 100% !important;
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
    <body>
        <jsp:include page="common/menu.jsp"></jsp:include>

            <div class="container-fluid">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-lg-10">
                            <h1 class="display-3 text-dark animated slideInDown">Add New Course</h1>
                        </div>
                    </div>
                </div>
                <form action="course-manage?action=add_new_course" method="post" enctype="multipart/form-data">
                    <div class="container light-style flex-grow-1 container-p-y">
                        <div class=" overflow-hidden">
                            <div class="row no-gutters row-bordered row-border-light">
                                <div class="card_mine">
                                    <div class="tab-content">
                                        <div class="tab-pane fade active show" id="account-general">
                                            <div class="card-body align-items-center">
                                                <img src="${requestScope.image}" alt="image" class="d-block ui-w-80">
                                        </div>
                                        <hr class="border-light m-0">
                                        <div class="card-body">
                                            <div class="form-group">
                                                <label class="form-label">Change Image</label>
                                                <div class="media-body ml-4">
                                                    <input type="file" class="" name="image">
                                                    <div class="text-black-50 small mt-1">Allowed JPG or PNG. Max size of 800K</div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_name}</h3>
                                                <label class="form-label">Course Name</label>
                                                <input name="courseName" type="text" class="form-control" value="${requestScope.course_name}">
                                            </div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_desciption}</h3>
                                                <label class="form-label">Description</label>
                                                <textarea id="description" name="description" rows="7" class="form-control auto-resize" value="${requestScope.desciption}"></textarea>
                                            </div>
                                            <div class="form-group" style="width: 15%">
                                                <label class="form-label">Price</label>
                                                <input name="price" type="number" min="0" max="9999999" class="form-control" value="${requestScope.price}">
                                            </div>
                                            <div hidden="" class="form-group" style="width: 5%">
                                                <label class="form-label">Discount</label>
                                                <!--<input name="discount" type="number" min="0" max="100" class="form-control" value="${requestScope.discount}"%">-->
                                                <input name="discount" type="number" min="0" max="100" class="form-control" value="0"%">
                                            </div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_category}</h3>
                                                <label class="form-label">Category</label>
                                                <select name="category" class="custom-select">
                                                    <option value="" selected >Choose one</option>
                                                    <c:forEach items="${listCategory}" var="o">
                                                        <option value="${o.category_id}" <c:if test="${requestScope.category} == null}">selected</c:if>>${o.getCategory_name()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div><br>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-right mt-3">
                        <a onclick="cancel(event)" href="#" name="cancel" class="btn btn-outline-danger md-btn-flat">Cancel</a>
                        <button type="submit" name="add" class="btn btn-primary">Add Course</button>&nbsp;
                    </div>
                </div>
            </form>
            <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
            <script type="text/javascript">
                            function cancel(event) {
                                event.preventDefault();
                                if (confirm("Are you sure you want to exit?")) {
                                    window.location.href = "course-manage";
                                }
                            }
                            document.addEventListener('DOMContentLoaded', function () {
                                function autoResize(textarea) {
                                    textarea.style.height = 'auto';
                                    textarea.style.height = textarea.scrollHeight + 'px';
                                }

                                var textareas = document.querySelectorAll('.auto-resize');
                                textareas.forEach(function (textarea) {
                                    textarea.setAttribute('style', 'height:' + (textarea.scrollHeight) + 'px;overflow-y:hidden;');
                                    textarea.addEventListener("input", function () {
                                        autoResize(this);
                                    }, false);

                                    // Resize on page load
                                    autoResize(textarea);
                                });
                            });
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
</body>
</html>


