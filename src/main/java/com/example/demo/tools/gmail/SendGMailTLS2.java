package com.example.demo.tools.gmail;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.example.demo.config.GmailConfiguration;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SendGMailTLS2
{   
    @PostConstruct
    public void init() {
        log.info("###################         " + GmailConfiguration.gmailSendAccount + ","
                + GmailConfiguration.gmailSendPassword);
        log.info("#####################################################################################");
    }
      
    public static void main(String[] args)
    {
        // Recipient's email ID needs to be mentioned.
        String to = GmailConfiguration.gmailSendAccountTO;// change accordingly

        // Sender's email ID needs to be mentioned
        String from = GmailConfiguration.gmailSendAccount;// change accordingly

        final String username = GmailConfiguration.gmailSendAccount;// change accordingly
        final String password = GmailConfiguration.gmailSendPassword;// change accordingly

        // Set Subject: header field
        String subject = "Gmail - Server Warn";

        // Now set the actual message
        String text = "Hello";

        sendddd(to, from, username, password, subject, text);

    }

    public static void sendddd(String to, String from, String username, String password, String subject, String text)
    {
        // // Recipient's email ID needs to be mentioned.
        // String to = "sendToAddress@example.com";//change accordingly
        //
        // // Sender's email ID needs to be mentioned
        // String from = "sendFromAddress@gmail.com";//change accordingly
        //
        // final String username = "sendFromAddress@gmail.com";//change
        // accordingly
        // final String password = "********";//change accordingly

        //
        // // Recipient's email ID needs to be mentioned.
        // String to = "835215587@qq.com";// change accordingly
        //
        // // Sender's email ID needs to be mentioned
        // String from = "dorosdebby@gmail.com";// change accordingly
        //
        // final String username = "dorosdebby@gmail.com";// change accordingly
        // final String password = "asdasd123!";// change accordingly

        // GMail's SMTP server
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);

        // Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        });

        try
        {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // // Set Subject: header field
            // message.setSubject("Gmail - Email Test");
            //
            // // Now set the actual message
            // message.setText("Hello, this is sample email to check/send " +
            // "email using JavaMailAPI from GMAIL");
            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully.... from GMAIL");

        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }
    }
}