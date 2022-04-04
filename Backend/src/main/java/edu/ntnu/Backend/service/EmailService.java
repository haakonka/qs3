package edu.ntnu.Backend.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email service is used to send emails to new users added to the Database
 */
@Service
public class EmailService {
    /**
     * These variables are the senders email address info. This is the email address all the emails are sent from.
     */
    private static final String senderEmail = "ugleskippy123@gmail.com";//sender email
    private static final String senderPassword = "igjjqominyjeebrr";//sender password

    /**
     * Reformats string into a MimeMessage which can be easly sent through email, and sends it.
     * @param to Email of the receiver
     * @param title Title of the email.
     * @param html Body of the email.
     * @throws MessagingException
     */
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

    /**
     * Mehtode to add headers to the email, and parse it accordingly.
     * @param message Message sent
     * @param to Email of the receiver.
     * @param title Title of the email.
     * @param html Body of the email.
     * @throws MessagingException
     */
    public void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    /**
     * Methode to create a Session with smtp servers. and configure ports and tls details.
     *
     * @return The session which is created.
     */
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