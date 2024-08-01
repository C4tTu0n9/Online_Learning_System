<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Thanh toán khóa học</title>
        <link href="assets/bootstrap.min.css" rel="stylesheet"/>
        <link href="assets/jumbotron-narrow.css" rel="stylesheet">
        <script src="assets/jquery-1.11.3.min.js"></script>
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            }
            .container {
                margin-top: 50px;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
            }
            .header {
                border-bottom: 1px solid #e5e5e5;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            .header img {
                width: 100px;
                height: auto;
            }
            h3 {
                margin-bottom: 20px;
            }
            .form-group label {
                font-weight: bold;
            }
            .form-control {
                border-radius: 0;
                box-shadow: none;
                border-color: #ced4da;
            }
            .form-control:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
            }
            .btn {
                background-color: #2bc5d4;
                color: #ffffff;
                border-radius: 5px;
            }
            .btn:hover {
                background-color: #e3f2e1;
            }
            
            .btnCancel {
                background-color: #e57067;
                color: #ffffff;
                border-radius: 5px;
            }
            
            .btnCancel:hover {
                background-color: orangered !important;
            }
            .footer {
                text-align: center;
                margin-top: 30px;

            }
            .footer p {
                color: #6c757d;
            }
        </style>
    </head>
    <body>
        
        <div class="container">
            <div class="header clearfix">
                <img src="https://tekoventures.vn/wp-content/uploads/2018/09/vnpay-logo.jpg">
            </div>
            <h3>Thanh toán khóa học</h3>
            <div class="table-responsive">
                <form action="AijaxServlet" id="frmCreateOrder" method="post">
                    <div class="form-group row">
                        <div class="col-md-6">
                            <label for="course">ID Khóa học:</label>
                            <%
                            String cid = request.getParameter("cid");
                            if (cid == null) {
                                cid = "0"; // Giá trị mặc định nếu không có giá trị được truyền
                            }
                            %>
                            <input readonly="" id="course"  class="form-control" name="courseId" value="<%= cid %>" />
                        </div>
                        <div class="col-md-6">
                            <label for="account">ID Tài khoản:</label>
                            <%
                            String acc = request.getParameter("acc");
                            if (acc == null) {
                                acc = "0"; // Giá trị mặc định nếu không có giá trị được truyền
                            }
                            %>
                            <input readonly="" id="account" name="accId" class="form-control" value="<%= acc %>" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="amount">Số tiền:</label>
                        <%
                        String price = request.getParameter("price");
                        if (price == null) {
                            price = "0"; // Giá trị mặc định nếu không có giá trị được truyền
                        }
                        %>
                        <input class="form-control" data-val="true" data-val-number="The field Amount must be a number." data-val-required="The Amount field is required." id="amount" max="10000000000" min="<%= price %>" name="amount" type="number" value="<%= price %>" />
                    </div>
                    <div class="form-group">

                        <label for="content">Nội dung chuyển khoản:</label>
                        <%
                          String ndck = request.getParameter("ndck");
                          if (ndck == null) {
                          ndck = ""; // Giá trị mặc định nếu không có giá trị được truyền
                         }
                        %>
                        <textarea id="content" class="form-control" name="ndck" required><%= ndck %></textarea>
                    </div>
                    <h4>Chọn phương thức thanh toán</h4>
                    <div class="form-group">
                        <input checked="true" type="radio" id="bankCode" name="bankCode" value="">
                        <label for="bankCode">Cổng thanh toán VNPAYQR</label><br>
                        <input type="radio" id="bankCode" name="bankCode" value="INTCARD">
                        <label for="bankCode">Thanh toán qua thẻ quốc tế</label><br>
                    </div>
                    <div class="form-group">
                        <h5>Chọn ngôn ngữ giao diện thanh toán:</h5>
                        <input type="radio" id="language" checked="true" name="language" value="vn">
                        <label for="language">Tiếng Việt</label><br>
                        <input type="radio" id="language" name="language" value="en">
                        <label for="language">Tiếng Anh</label><br>
                    </div>
                    <button style="margin-bottom: 30px" type="submit" class="btn btn-default">Pay </button>
                        <%
                        String address = request.getParameter("address");
                        %>
                    <button style="margin-bottom: 30px" type="button" onclick="Cancel('<%=address%>')" class="btn btnCancel btn-danger">Cancel</button>
                </form>
            </div>

        </div>
                    
                    
                <script>
                    function Cancel(address) {
                        if(address === 'home') {
                            window.location.href = 'home';
                        } else if(address === 'course') {
                            window.location.href = 'listCourseSeverlet?cateid=all&action=course';
                        } else {
                            window.location.href = 'CourseDetail?cid='+address;
                        }
                    }
            
                </script>            
                    
                    
        <link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css" rel="stylesheet" />
        <script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
        <script type="text/javascript">
            $("#frmCreateOrder").submit(function () {
                var postData = $("#frmCreateOrder").serialize();
                var submitUrl = $("#frmCreateOrder").attr("action");
                $.ajax({
                    type: "POST",
                    url: submitUrl,
                    data: postData,
                    dataType: 'JSON',
                    success: function (x) {
                        if (x.code === '00') {
                            if (window.vnpay) {
                                vnpay.open({width: 768, height: 600, url: x.data});
                            } else {
                                location.href = x.data;
                            }
                            return false;
                        } else {
                            alert(x.Message);
                        }
                    }
                });
                return false;
            });
        </script>
    </body>
</html>
