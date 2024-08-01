<%-- 
    Document   : tables
    Created on : Jun 27, 2024, 2:21:16 PM
    Author     : Tuan Anh(Gia Truong)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>SB Admin 2 - Tables</title>

        <!-- Custom fonts for this template -->
        <link href="${pageContext.request.contextPath}/static_dasboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">


        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
              rel="stylesheet">


        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/static_dasboard/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/static_dasboard/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <style>
            .card-header {
                border-color: #eaeaea;
                position: relative;
                background: transparent;
                padding: 1.25rem 1.25rem 5px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border: 0px;
            }


            /* CSS Classes */
            .status-active {

                color: #00cc66; /* Màu xanh lá đậm cho chữ */
                text-align: center;
            }

            .status-inactive {
                color: #cc0033; /* Màu đỏ đậm cho chữ */
                text-align: center;
            }




        </style>



    </head>

    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <jsp:include page="menuDasboard.jsp"></jsp:include>
                <!-- End of Sidebar -->

                <!-- Content Wrapper -->
                <div id="content-wrapper" class="d-flex flex-column">

                    <!-- Main Content -->
                    <div id="content">

                        <!-- Topbar -->
                        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                            <!-- Sidebar Toggle (Topbar) -->
                            <form class="form-inline">
                                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                                    <i class="fa fa-bars"></i>
                                </button>
                            </form>

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

                                

                            </ul>

                        </nav>
                        <!-- End of Topbar -->

                        <!-- Begin Page Content -->
                        <div class="container-fluid">

                            <!-- Page Heading -->
                            <h1 class="h3 mb-2 text-gray-800">Tables</h1>
<!--                            <div class="container">
                                <h2>Import Mentor Accounts</h2>
                                <form action="manageAccount?action=import" method="POST" enctype="multipart/form-data" class="upload-form">
                                    <label for="file-upload" class="custom-file-upload">
                                        <input type="file" id="file-upload" name="file" accept=".xlsx">
                                    </label>
                                    <input type="submit" value="Import" class="submit-btn">
                                </form>
                            </div>-->

                            <!-- DataTales Example -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h5 class="card-title">Managers List  </h5>
                                    <div>

                                        <a href="manageAccount?action=addAccount" class="btn btn-primary">+ Add new</a>
<!--                                        <a href="manageAccount?action=import" style="background-color: orangered" class="btn btn-primary">- Delete</a>-->

                                    </div>
                                </div>




                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th style="width:0px;">Id</th>
                                                    <th style="width: 200px;">Full Name</th>
                                                    <th style="width: 250px;">Email</th>
                                                    <th style="width: 200px;">Password</th>
                                                    <th style="width: 100px;">Role</th>
                                                    <th style="width: 0px;">Status</th>
                                                    <th style="width:0px;">Active</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Id</th>
                                                    <th>Full Name</th>
                                                    <th>Email</th>
                                                    <th>Password</th>
                                                    <th>Role</th>
                                                    <th>Status</th>
                                                    <th>Active</th>
                                                </tr>
                                            </tfoot>
                                            <tbody>
                                            <c:forEach items="${listAllAccount}" var="o">
                                                <tr>
                                                    <td>${o.getAccount_id()}</td>
                                                    <td>${o.getFullName()}</td>
                                                    <td>${o.getEmail()}</td>

                                                    <td>
                                                        ${"*".repeat(o.getPassword().length())}
                                                    </td>

                                                    <td>${o.getRoleName()}</td>
                                                    <td>
                                                        <span class="${o.isStatus() ? 'status-active' : 'status-inactive'}">
                                                            ${o.isStatus() ? "Active" : "Inactive"}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <a href="manageAccount?action=updateAccount&accountid=${o.getAccount_id()}" class="btn btn-sm btn-primary"><i class="fas fa-pencil-alt"></i></a>
                                                        <!--<a href="manageAccount?action=statusAccount" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt"></i></a>-->
                                                        <c:choose>
                                                            <c:when test="${o.isStatus()}">
                                                                <a onclick="changeStatus(${o.getAccount_id()})" style="padding: .25rem .45rem"  class="btn btn-sm btn-danger" data-toggle="modal" data-target="#logoutModal">
                                                                    <i class="fas fa-lock-open"></i>
                                                                </a>
                                                            </c:when>

                                                            <c:otherwise>
                                                                <a onclick="changeStatus(${o.getAccount_id()})"  class="btn btn-sm btn-danger" data-toggle="modal" data-target="#logoutModal">
                                                                    <i class="fas fa-lock"></i>
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span> &copy; OnLine Learning System (OLS)</span>
                        </div>
                    </div>
                </footer>
                <!-- End of Footer -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ready to change account status?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Select "Change" below if you are ready to change account status.</div>
                    <div class="modal-footer">
                        <form action="manageAccount?action=statusAccount" method="POST">
                            <input type="hidden" name="accid" id="inputaccountid">
                            <button type="submit" class="btn btn-primary" >Change Status</button>
                        </form>

                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                        <!--                        <a class="btn btn-primary" href="login.html">Logout</a>-->
                    </div>
                </div>
            </div>
        </div>


        <script>
            function changeStatus(accountId) {
                let inputaccountid = document.querySelector("#inputaccountid");

                inputaccountid.value = accountId;

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