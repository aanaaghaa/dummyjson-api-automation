package com.anagha.api.dummyjson_api_test.reporting;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Utility class for sending test reports via email after the test suite execution.
 * This class sends an HTML email with a clickable report link and attaches
 * the actual Extent report file as an attachment.
 */
public class EmailReportSender {

	//Sends the HTML Extent report via email with subject, body, and attachment.
    public static void sendReport(String reportPath) {
        final String username = "anagha2924@gmail.com";         
        final String password = "kaiefhxdacbifaki";       

        //// SMTP server configuration for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //// Start a new mail session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
        	 // Create email message object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("anagha2924@gmail.com")  
            );
            
         // Construct full local report URL (for clickable HTML body)
            String reportURL = "file:///C:/Users/Anagha/git/repository/dummyjson-api-test/target/test-output/ExtentReport.html" + reportPath.replace("\\", "/");
            
         // Email body in HTML format
            String htmlBody = "<h3>Hi,</h3>"
                    + "<p>The latest API automation test run has completed. Please find the report below:</p>"
                    + "<a href='" + reportURL + "' target='_blank'>Click to View Extent Report</a>"
                    + "<br><br><p>Regards,<br>Test Automation Framework</p>";

         // Format timestamp for subject
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            message.setSubject("Dummy JSON API Test Report - " + timeStamp);

         // Create attachment part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody, "text/html");


            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(reportPath));

            // Combine both parts into multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

         // Send the message
            Transport.send(message);
            System.out.println("Report emailed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




