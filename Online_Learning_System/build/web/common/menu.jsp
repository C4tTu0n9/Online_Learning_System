<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- Navbar Start -->
<nav class="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
    <a href="home" class="navbar-brand d-flex align-items-center px-4 px-lg-5" style="text-decoration: none;">
        <h2 class="m-0 text-primary"><i class="fa fa-book me-3"></i>OLS</h2>
    </a>

    <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>


    <div class="collapse navbar-collapse" id="navbarCollapse">

        <!--        search by category-->
        <div class="navbar-nav ms-auto p-4 p-lg-0">
            <div class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" style="
                   border-style: solid;
                   border-width: thin;
                   border-radius: 10px;
                   padding: 10px;
                   margin: 15px;
                   color: #06BBCC;
                   ">Explore</a>
                <div class="dropdown-menu fade-down m-0">
                    <c:forEach items="${listCategory}" var="o">
                        <a href="listCourseSeverlet?cateid=${o.getCategory_id()}&action=course" class="dropdown-item">${o.getCategory_name()}</a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!--        search by name-->
        <form action="listCourseSeverlet?action=course" method="post" class="navbar-nav ">
            <input class="search-input" style="width: 100%;" type="text" name="search"  placeholder="Search courses">
            <input style="border-radius: 4px;" class="btn btn-primary" type="submit" value="Search">
        </form>


        <div class="navbar-nav ms-auto p-4 p-lg-0">
            <a href="home" class="nav-item nav-link  ${action == null ? "active" : ""}">Home</a>
            <a href="about.jsp?action=about" class="nav-item nav-link  ${action == "about" ? "active" : ""}">About</a>
            <a href="listCourseSeverlet?cateid=all&action=course" class="nav-item nav-link  ${action == "course" ? "active" : ""}">Courses</a>
            <!--            <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                            <div class="dropdown-menu fade-down m-0">
                                <a href="team.html" class="dropdown-item">Our Team</a>
                                <a href="testimonial.html" class="dropdown-item">Testimonial</a>
                                <a href="404.html" class="dropdown-item">404 Page</a>
                            </div>
                        </div>-->
            <c:choose>
                <c:when test="${sessionScope.account.role_id == 2}">
                    <a href="dasboard_for_admin/StatisticalSeverlet" class="nav-item nav-link">Dashboard</a>
                </c:when>
                <c:when test="${sessionScope.account.role_id == 1}">
                    <a href="dasboard_for_admin/managerAccount" class="nav-item nav-link">Dashboard</a>
                </c:when>
                <c:otherwise>
                    <a href="contact.jsp" class="nav-item nav-link">Contact</a>
                </c:otherwise>
            </c:choose>

        </div>

        <c:if test="${sessionScope.account != null}">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <div class="nav-item dropdown">
                    <a href="#" class="btn btn-primary py-4 px-lg-5 d-lg-block" data-toggle="dropdown">
                        ${sessionScope.profile.fullname}<i class="fa fa-arrow-down ms-3"></i>
                    </a>
                    <div class="dropdown-menu fade-down m-0">
                        <a class="dropdown-item" href="profile">My Profile</a>
                        <c:if test="${sessionScope.account.role_id == 2}">
                            <a class="dropdown-item" href="course-manage">Manager</a>
                        </c:if>
                        <c:if test="${sessionScope.account.role_id == 3}">
                            <a class="dropdown-item" href="course-manage">My Teaching</a>
                        </c:if>
                        <c:if test="${sessionScope.account.role_id == 3}">
                            <a class="dropdown-item" href="messenger?sender_id=${sessionScope.account.getAccount_id()}&receiver_id=0">Messenger</a>
                        </c:if>
                        <a class="dropdown-item" href="certificate">My Certificate</a>
                        <a class="dropdown-item" href="my-courses">My Learning</a>
                        <a class="dropdown-item" href="join?action=logout">Log Out</a>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.account == null}">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <div class="nav-item dropdown">
                    <a href="#" class="btn btn-primary py-4 px-lg-5 d-lg-block" data-toggle="dropdown">
                        Login/Register<i class="fa fa-arrow-down ms-3"></i>
                    </a>
                    <div class="dropdown-menu fade-down m-0">
                        <a href="join?action=login" class="dropdown-item">Login</a>
                        <a href="join?action=signup" class="dropdown-item">Register</a>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</nav>
<!-- Navbar End -->

