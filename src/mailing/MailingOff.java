package mailing;

import util.MyDB;
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
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class MailingOff {

    private Connection con;
    private Statement ste;

    public MailingOff() {
        con = MyDB.getInstance().getCnx();

    }

    public static void mailing(String recipient, String subject, String content) throws Exception {

        Properties prop = new Properties();
        final String moncompteEmail = "wadhah.naggui@esprit.tn";
        final String psw = "4lfa4real123";
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
            msg.setSubject(subject);
            msg.setText(content);

            Transport.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(MailingOff.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void sendSms(String toNumber, String message) {
        VonageClient client = VonageClient.builder()
                .apiKey("5750a10f")
                .apiSecret("2Y2XI453FNx6qAMN")
                .build();
        TextMessage sms = new TextMessage("Vonage APIs", toNumber, message);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(sms);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }
}
