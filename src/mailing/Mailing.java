package mailing;

import  util.MyDB;
//import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import entities.User;

public class Mailing {

    private Connection con;
    private Statement ste;

    public Mailing() {
        con = MyDB.getInstance().getCnx();

    }

    public static void mailing(String recipient) throws Exception {

        Properties prop = new Properties();
        final String moncompteEmail = "amira.khalfi@esprit.tn";
        final String psw = "unstoppablegirlawlaysalways";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session ses = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
        });

        try {

            Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(moncompteEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("Email verification");
            msg.setContent("Bonjour", "text/html");

            Transport.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void mailingValider(String recipient, int nombre) throws Exception {

        Properties prop = new Properties();
        final String moncompteEmail = "amira.khalfi@esprit.tn";
        final String psw = "unstoppableamouraalwaysalways";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session ses = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
        });

        try {

            Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(moncompteEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("Code de confirmation");
            msg.setText("Merci pour votre Interet a sportifly , voici votre code de confirmation:   "+String.valueOf(nombre));

            Transport.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
     public static void sendMail(String recepient, String object, String content) throws Exception{
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "amira.khalfi@esprit.tn";
        //Your gmail password
        String password = "unstoppableamouraalwaysalways";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        //Prepare email message
        Message message = prepareMessage((Session) session, myAccountEmail, recepient, object, content);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }
     private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String object, String code) {
    try {
        String content = "Bonjour,<br><br>Merci d'entrer le code suivant pour réinitialiser votre mot de passe : <br><br><strong>" + code + "</strong><br><br>Cordialement,<br>L'équipe de support.";
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(object);
        message.setContent(content, "text/html");
        return message;
    } catch (Exception ex) {
        Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
     }
}




