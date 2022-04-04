package edu.ntnu.Backend.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    private static final String senderEmail = "ugleskippy123@gmail.com";//sender email
    private static final String senderPassword = "igjjqominyjeebrr";//sender password

    public void sendAsHtml(String to, String title, String html) throws MessagingException {
        System.out.println("Sending email to " + to);
        Session session = createSession();
        //create message using session
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, to, title, html);
        //sending message
        Transport.send(message);
        System.out.println("Done");
    }

    public void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    public Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", "smtp.gmail.com"); //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", "587");//Outgoing port

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }
}