<%-- 
    Document   : footer
    Created on : Jun 12, 2024, 6:04:11 PM
    Author     : Tuan Anh(Gia Truong)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Footer Start -->
<div class="container-fluid bg-dark text-light footer pt-5 mt-5 wow fadeIn" data-wow-delay="0.1s">
    <div class="container py-5">
        
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Quick Link</h4>
                <a class="btn btn-link" href="about.jsp">About Us</a>
                <a class="btn btn-link" href="contact.jsp">Contact Us</a>
                <!--                        <a class="btn btn-link" href="">Privacy Policy</a>
                                        <a class="btn btn-link" href="">Terms & Condition</a>
                                        <a class="btn btn-link" href="">FAQs & Help</a>-->
            </div>
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Contact</h4>
                <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>Thach Hoa, Thach That, Ha Noi</p>
                <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>+84 869620295</p>
                <p class="mb-2"><i class="fa fa-envelope me-3"></i>systemonlinelearning@gmail.com</p>
                <div class="d-flex pt-2">
                    <a class="btn btn-outline-light btn-social" href="https://www.facebook.com/profile.php?id=100064783599647"><i class="fab fa-facebook-f"></i></a>
                    <a class="btn btn-outline-light btn-social" href="https://www.youtube.com/channel/UC9xB0jUppfV72k_jpPn5bMw"><i class="fab fa-youtube"></i></a>

                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Category</h4>
                <div class="row g-2 pt-2">
                  
                    <c:forEach items="${listCategory}" var="o">
                        <a class="btn btn-link" href="listCourseSeverlet?cateid=${o.getCategory_id()}">${o.getCategory_name()}</a>
                    </c:forEach>
                    
                   
                </div>
            </div>

    </div>

        <div class="copyright">
            <div >
                <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                    &copy; <a class="border-bottom" href="#">OnLine Learning System (OLS)</a>
                </div>

            </div>
        </div>
    
</div>
<!-- Footer End -->