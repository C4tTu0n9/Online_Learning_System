<%-- 
    Document   : Certificate
    Created on : Jun 28, 2024, 2:58:54 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Certificate Template</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link rel="stylesheet" href="css/certificate.css">
    </head>
    <body>
        <c:forEach var="c" items="${getDetailCertificateByAccIdAndCourseId}">
            <div id="invoice">
                <header>
                    <h1>Certificate</h1>
                    <address>
                        <p style="font-size: x-large;">${c.getCourse_name()}</p>
                        <p>${c.getFullname_mentor()}</p>
                        <p > </p>
                    </address>
                    <span><img alt="Certificate" src="${c.getImg()}" width="150"></span>
                </header>
                <article>
                    <address class="norm">
                        <h4>${c.getFullname_mentee()}</h4>
                        <p>has successfully completed the online Certificate</p> 
                        <p>Dear ${c.getFullname_mentee()},<br>
                        <p>Congratulations on completing the course!  <br>
                        <p>Your dedication and hard work have <br>
                        <p>truly paid off, and it has been a pleasure <br>
                        <p>to see your growth throughout the term.<br>
                        <p>Keep up the fantastic work, and continue striving<br>
                        <p>for excellence in all your future endeavors. Well done!<br>

                        <p>Best regards,<br>  
                        <p>${c.getFullname_mentor()}<br> 
                    </address>

                    <table class="meta">
                        <tr>
                            <th><span>Certificate No.</span></th>
                            <td><span>${c.getCertificate_id()}</span></td>
                        </tr>
                        <tr>
                            <th><span>Available from date:</span></th>
                            <td><span>${c.getIssuer_date()}</span></td>
                        </tr>

                    </table>
                </article>
                <aside>
                    <h1><span>Prommotion for old customers</span></h1>
                    <div>
                        <p>Thank you for your interest in our course. We are very grateful for that. So to thank you, our system will give you a discount when you purchase your next course. </p>
                        <p>Sincerely thank!</p>
                    </div>
                </aside>

            </div>

            <a href="javascript:void(0)" class="btn-download" data-certificate-id="${c.getCertificate_id()}">Download to PDF</a>

        </c:forEach>
    </body>
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/jspdf.debug.js"></script>
    <script src="js/html2canvas.min.js"></script>
    <script src="js/html2pdf.min.js"></script>

    <script>
    const options = {
        margin: 0.5,
        image: {
            type: 'jpeg',
            quality: 500
        },
        html2canvas: {
            scale: 1
        },
        jsPDF: {
            unit: 'in',
            format: 'letter',
            orientation: 'portrait'
        }
    };

    $('.btn-download').click(function (e) {
        e.preventDefault();
        const element = document.getElementById('invoice');
        const certificateId = $(this).data('certificate-id'); // Lấy certificate_id từ thuộc tính data-certificate-id
        options.filename = `chungchi_${certificateId}.pdf`; // Tạo tên tệp PDF duy nhất
        html2pdf().from(element).set(options).save(); // Tạo và lưu tệp PDF
    });

    function printDiv(divName) {
        var printContents = document.getElementById(divName).innerHTML;
        var originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;

        window.print();

        document.body.innerHTML = originalContents;
    }
</script>

</html>
