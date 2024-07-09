package Util;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.http.HttpResponse;

public class SendEmail {

    public static void send(final String fromEmail, final String password, String toEmail, String subject, String body, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        // Cấu hình thuộc tính cho phiên làm việc
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Sử dụng SMTP host của Google
        props.put("mail.smtp.port", "587"); // SMTP port của Google
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên làm việc
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Thêm thông tin metadata
            message.setHeader("X-Mailer", "JavaMail API");

            // Gửi message
            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

//    public static void main(String[] args) {
//        // Thông tin tài khoản email người gửi
//        final String fromEmail = "hatronghung7777@gmail.com";
//        final String password = "chnzvsbysoeesgwe";
//
//        // Thông tin người nhận
//        String toEmail = "hunghthe173381@fpt.edu.vn";
//
//        // Thông tin email
//        String subject = "Chủ đề email";
//        String body = "chúng tôi là chó";
//        SendEmail.send(fromEmail, password, toEmail, subject, body);
//
//    }
}
