/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mailing.MailingOff;
import util.MyDB;

/**
 *
 * @author wadah
 */
public class ReservationService implements IServiceOffre<reservation> {

    Connection cnx;

    public ReservationService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(reservation t) throws SQLException {
        String req = "INSERT INTO reservation(id_offre_id, id_user_id, date, status) VALUES ('" + t.getId_offre() + "', '" + t.getId_user() + "', CURRENT_TIMESTAMP, '" + t.getStatus() + "')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(reservation t) throws SQLException {
        String req = "UPDATE reservation SET id_offre_id = ?, id_user_id = ?, date = ?, status = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, t.getId_offre());
        ps.setInt(2, t.getId_user());
        ps.setDate(3, new java.sql.Date(t.getDate().getTime()));
        ps.setString(4, t.getStatus());
        ps.setInt(5, t.getId());

        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reservation WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean supprimer1(reservation r) throws SQLException {
        boolean test = false;
        try {
            String req = "DELETE FROM offre WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, r.getId());
            ps.executeUpdate();
            test = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;

    }

    @Override
    public List<reservation> recuperer() throws SQLException {
        List<reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            reservation r = new reservation();
            r.setId(rs.getInt("id"));
            r.setId_user(rs.getInt("id_user_id"));
            r.setId_offre(rs.getInt("id_offre_id"));
            r.setDate(rs.getDate("date"));
            r.setStatus(rs.getString("status"));
            reservations.add(r);
        }
        return reservations;
    }

    public reservation recupereridOffByIdRes(int id) throws SQLException {
        reservation r = null;

        String req = "SELECT * FROM reservation WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            r = new reservation();
            r.setId(rs.getInt("id"));
            r.setId_user(rs.getInt("id_user_id"));
            r.setId_offre(rs.getInt("id_offre_id"));
            r.setDate(rs.getDate("date"));
            r.setStatus(rs.getString("status"));
        }
        return r;
    }

    public List<reservation> recupererById(int id) throws SQLException {
        List<reservation> reservations = new ArrayList<>();

        String req = "SELECT * FROM reservation WHERE id_user_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            reservation r = new reservation();
            r.setId(rs.getInt("id"));
            r.setId_user(rs.getInt("id_user_id"));
            r.setId_offre(rs.getInt("id_offre_id"));
            r.setDate(rs.getDate("date"));
            r.setStatus(rs.getString("status"));
            reservations.add(r);
        }
        return reservations;
    }

    public List<reservation> recupererByOffreUserId(int userId) throws SQLException {
        List<reservation> reservations = new ArrayList<>();

        String req = "SELECT r.* FROM reservation r JOIN offre o ON r.id_offre_id = o.id WHERE o.id_user_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            reservation r = new reservation();
            r.setId(rs.getInt("id"));
            r.setId_user(rs.getInt("id_user_id"));
            r.setId_offre(rs.getInt("id_offre_id"));
            r.setDate(rs.getDate("date"));
            r.setStatus(rs.getString("status"));
            reservations.add(r);
        }
        return reservations;
    }

    public String getStatus(int id) throws SQLException {
        String status = null;
        String req = "SELECT status FROM reservation WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            status = rs.getString("status");
        }
        return status;
    }

    public void updateStatus(int id, String newStatus) throws SQLException {
        String req = "UPDATE reservation SET status = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, newStatus);
        ps.setInt(2, id);
        ps.executeUpdate();
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

}
