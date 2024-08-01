<%-- 
    Document   : UpdateCourse
    Created on : Jun 22, 2024, 2:54:29 PM
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
            .mentor-item {
                padding: 5px;
                cursor: pointer;
            }
            .mentor-item:hover {
                background-color: #f0f0f0;
            }
            .hidden {
                display: none;
            }
        </style>
    </head>

    <body>
        <jsp:include page="common/menu.jsp"></jsp:include>


            <div class="container-fluid">
                <div class="container py-5">
                    <div class="col-2 h6">
                        <a href="course-manage" class="fas fa-angle-left">
                            Course Manage
                        </a>
                    </div>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">
                        ${successMessage}
                    </div>
                    <% session.removeAttribute("successMessage"); %>
                </c:if>
                <div class="row">
                    <div class="col-10">
                        <h6 href="" class="display-6 text-dark animated slideInDown">${my_managed_course.course_name}</h6>
                    </div>

                </div>
            </div>
            <div class="container light-style flex-grow-1 container-p-y">

                <div class=" overflow-hidden">
                    <div class="row no-gutters row-bordered row-border-light">
                        <div class="col-md-3 pt-0">
                            <div class="list-group list-group-flush account-settings-links" id="moduleList">
                                <a class="list-group-item list-group-item-action active" href="#"">Edit Course Information</a>
                                <h5>Modules</h5>
                                <c:forEach items="${list_module}" var="module">
                                    <a class="list-group-item list-group-item-action" href="ModuleManage?moduleId=${module.moduleid}&cid=${my_managed_course.course_id}" data-module-id="${module.moduleid}">${module.modulename}</a>
                                </c:forEach>
                                <a class="btn btn-outline-primary" href="course-manage?action=add_module&cid=${my_managed_course.course_id}">Add New Module</a>
                            </div>
                        </div>


                        <div class="col-md-9 card_mine">
                            <div class="tab-content">
                                <div class="tab-pane fade active show" id="Courses">
                                    <h3 style="color: red">${requestScope.error}</h3>

                                    <form id="updateCourseForm" action="course-manage?cid=${my_managed_course.course_id}&action=update" method="post" enctype="multipart/form-data">
                                        <div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_images}</h3>
                                                <img style="width: 200px; height: auto; border-radius: 17px" src="${requestScope.my_managed_course.image}" id="image" alt="course image" class=" iamge d-block">
                                                <label class="form-label">Change Image</label>
                                                <div class="media-body ml-4">
                                                    <input type="file" id="imageFile" accept=".jpg, .jpeg, .png" class="" name="image">
                                                    <div class="text-black-50 small mt-1">Allowed JPG, JPEG or PNG</div>
                                                </div>
                                                <input type="hidden" name="current_image" value="${requestScope.my_managed_course.image}">
                                            </div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_name}</h3>
                                                <label class="form-label">Course Name</label>
                                                <input name="courseName" type="text" class="form-control" value="${requestScope.my_managed_course.course_name}">
                                            </div>
                                            <div class="form-group">
                                                <h3 style="color: red">${requestScope.error_desciption}</h3>
                                                <label class="form-label">Description</label>
                                                <textarea id="description" name="description" class="form-control auto-resize">${requestScope.my_managed_course.description}</textarea>
                                            </div>
                                            <div class="form-group" style="width: 15%">
                                                <label class="form-label">Price</label>
                                                <input name="price" type="number" min="0" max="9999999" class="form-control" value="${requestScope.my_managed_course.price}">
                                            </div>
                                            <div hidden="" class="form-group" style="width: 10%">
                                                <label class="form-label">Discount</label>
                                                <!--<input name="discount" type="number" min="0" max="100" class="form-control" value="${requestScope.my_managed_course.discount}"%">-->
                                                <input name="discount" type="number" min="0" max="100" class="form-control" value="0"%">
                                            </div>
                                            <div class="form-group">
                                                ${requestScope.my_managed_course.course_category_id}
                                                <h3 style="color: red">${requestScope.error_category}</h3>
                                                <label class="form-label">Category</label>

                                                <select name="category" class="custom-select">
                                                    <option value="" >Choose one</option>
                                                    <c:forEach items="${listCategory}" var="o">
                                                        <option value="${o.category_id}"<c:if test="${not empty requestScope.my_managed_course and requestScope.my_managed_course.course_category_id == o.category_id}">selected</c:if> >${o.getCategory_name()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <c:if test="${my_role == 2}">
                                                <div class="form-group">
                                                    <label class="form-label">Assign Mentor</label><br>
                                                    <input id="mentorSearch" class="search-input" type="text" name="search" placeholder="Search Mentor"><br>
                                                    <div id="mentorListContainer">
                                                        <table class="table table-bordered" id="mentorList">
                                                            <thead>
                                                                <tr>
                                                                    <th>Select</th>
                                                                    <th>Name</th>
                                                                    <th>Email</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${list_mentor_by_courseId}" var="mentor" varStatus="status">
                                                                    <tr class="mentor-item" data-name="${mentor.fullname}" data-email="${mentor.email}">
                                                                        <td><input <c:if test="${mentor.teaching_course == my_managed_course.course_id}">checked=""</c:if> type="checkbox" name="mentors" value="${mentor.profile_id}"></td>
                                                                        <td>${mentor.fullname}</td>
                                                                        <td>${mentor.email}</td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <br>

                                            <c:if test="${my_role == 2}">
                                                <!-- Form fields here -->
                                                <div class="form-group" style="text-align: right;">
                                                    <button type="submit" class="btn btn-outline-primary">Save Change</button>
                                                </div>
                                            </c:if>

                                        </div>
                                    </form>
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
            <script>
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
                document.addEventListener('DOMContentLoaded', function () {
                    var form = document.getElementById('updateCourseForm');
                    form.addEventListener('submit', function (event) {
                        event.preventDefault(); // Prevent the form from submitting immediately

                        if (confirm('Are you sure you want to save these changes?')) {
                            this.submit(); // If user confirms, submit the form
                        }
                    });
                });

                document.addEventListener('DOMContentLoaded', function () {
                    const searchInput = document.getElementById('mentorSearch');
                    const mentorList = document.getElementById('mentorList');
                    const mentorItems = mentorList.getElementsByClassName('mentor-item');
                    searchInput.addEventListener('input', function () {
                        const searchTerm = this.value.toLowerCase();

                        Array.from(mentorItems).forEach(function (item) {
                            const mentorName = item.getAttribute('data-name').toLowerCase();
                            const mentorEmail = item.getAttribute('data-email').toLowerCase();

                            if (mentorName.includes(searchTerm) || mentorEmail.includes(searchTerm)) {
                                item.classList.remove('hidden');
                            } else {
                                item.classList.add('hidden');
                            }
                        });
                    });
                });

                //validate image input
                document.getElementById('imageFile').addEventListener('change', function () {
                    var file = this.files[0];
                    var fileType = file.type;
                    var match = ['image/jpeg', 'image/png', 'image/jpeg'];
                    if (!match.includes(fileType)) {
                        alert('Chỉ chấp nhận file JPG, JPEG hoặc PNG.');
                        this.value = '';
                        return false;
                    }
                });
                function chooseFile(fileInput) {
                    if (fileInput.files && fileInput.files[0]) {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            $('#image').attr('src', e.target.result);
                        };
                        reader.readAsDataURL(fileInput.files[0]);
                    }
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

