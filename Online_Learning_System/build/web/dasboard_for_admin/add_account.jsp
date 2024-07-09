<%-- 
    Document   : add_account
    Created on : Jun 27, 2024, 10:23:51 PM
    Author     : Tuan Anh(Gia Truong)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
﻿<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Edumin - Bootstrap Admin Dashboard </title>


        <!-- Custom fonts for this template -->
        <link href="${pageContext.request.contextPath}/static_dasboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
              rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/static_dasboard/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/static_dasboard/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">



        <link rel="stylesheet" href="${pageContext.request.contextPath}/static_dasboard/vendor/bootstrap-select/dist/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static_dasboard/css/style.css">

        <!-- Pick date -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static_dasboard/vendor/pickadate/themes/default.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static_dasboard/vendor/pickadate/themes/default.date.css">
        
        <style>
            body{
                margin-top: -25px;
            }
        </style>
    </head>

    <body>

        <div id="wrapper">

            <jsp:include page="menuDasboard.jsp"></jsp:include>
            
               <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
            
                            <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Search -->
                    <form
                        class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group">
                            <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                                aria-label="Search" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>
                    </form>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">

                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <li class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-search fa-fw"></i>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                                aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small"
                                            placeholder="Search for..." aria-label="Search"
                                            aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button">
                                                <i class="fas fa-search fa-sm"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>

                        <!-- Nav Item - Alerts -->
                       

                        

                    </ul>

                </nav>

                <div class="content-body">
                    <!-- row -->
                    <div class="container-fluid">

                        <div class="row page-titles mx-0">
                            <div class="col-sm-6 p-md-0">
                                <div class="welcome-text">
                                    <h4>Add account</h4>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-xl-12 col-xxl-12 col-sm-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Basic Info</h5>
                                    </div>
                                    <div class="card-body">
                                        <form action="manageAccount?action=addAccount" method="post">
                                            <div class="row">
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Full Name</label>
                                                        <input required="" type="text" value="${fullname}" name="fullname" class="form-control">
                                                    </div>
                                                </div>
                                              
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Email Here</label>
                                                        <input required="" type="text" value="${email}" name="email" class="form-control">
                                                    </div>
                                                </div>
                                               
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Password</label>
                                                        <input required="" type="password" value="${pass}" name="pass" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Confirm Password</label>
                                                        <input required="" type="password" value="${cfpass}" name="cfpass" class="form-control">
                                                    </div>
                                                </div>
                                               
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Gender</label>
                                                        <select class="form-control" name="gender">
                                                            <option value="Male" ${gender == "Male" ? "selected" : ""}>Male</option>
                                                            <option value="Female" ${gender == "Female" ? "selected" : ""}>Female</option>
                                                        </select>
                                                    </div>
                                                </div>
                                               
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <div class="form-group">
                                                        <label class="form-label">Role</label>
                                                        <select class="form-control" name="role">
                                                            <option value="1"  ${role == "1" ? "selected" : ""}>Admin</option>
                                                            <option value="2"  ${role == "2" ? "selected" : ""}>Manager</option>
                                                            <option value="3"  ${role == "3" ? "selected" : ""}>Menbtor</option>
                                                            <option value="4"  ${role == "4" ? "selected" : ""}>Mentee</option>
                                                        </select>
                                                    </div>
                                                </div>
                                               
<!--                                                <div class="col-lg-12 col-md-12 col-sm-12">
                                                    <div class="form-group fallback w-100">
                                                        <input type="file" name="file" class="dropify" data-default-file="">
                                                    </div>
                                                </div>-->
<p class="text-danger">${msg}</p>
                                                <div class="col-lg-12 col-md-12 col-sm-12">
                                                    <button type="submit" class="btn btn-primary">Submit</button>

                                                    <button onclick="cancelAdd(${cid})" type="button" class="btn btn-light">Cencel</button>

                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--**********************************
                    Content body end
                ***********************************-->


                 <!-- Footer -->
                    <footer class="sticky-footer bg-white">
                        <div class="container my-auto">
                            <div class="copyright text-center my-auto">
                                <span>Copyright &copy; Your Website 2020</span>
                            </div>
                        </div>
                    </footer>
                <!--**********************************
                    Footer end
                ***********************************-->
             

</div>

</div>

            </div>



        <script>
            function cancelAdd(cid) {
                window.location.href = '/Project_E-Learning/dasboard_for_admin/manageAccount';
            }
        </script>

      


        <!-- Bootstrap core JavaScript-->
        <script src="${pageContext.request.contextPath}/static_dasboard/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/static_dasboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/static_dasboard/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="${pageContext.request.contextPath}/static_dasboard/js/sb-admin-2.min.js"></script>

        <!-- Page level plugins -->
        <script src="${pageContext.request.contextPath}/static_dasboard/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/static_dasboard/vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="${pageContext.request.contextPath}/static_dasboard/js/demo/datatables-demo.js"></script>

    </body>
</html>
