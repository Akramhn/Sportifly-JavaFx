/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import entities.reservation;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mailing.MailingOff;
import static mailing.MailingOff.mailing;
import services.OffreService;
import services.ReservationService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class ItemCoachReservationController implements Initializable {

    @FXML
    private Label desc;
    @FXML
    private Label resId;
    @FXML
    private Label dateres;
    @FXML
    private Button cancelRes;
    @FXML
    private Button acceptRes;

    private int idReservation;

    private int id_offre;

    private SessionManager session;

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setData(reservation Reservation) {
        desc.setText(String.valueOf(Reservation.getId_offre()));
        dateres.setText(String.valueOf(Reservation.getDate()));
        setIdReservation(Reservation.getId());

        ReservationService reservationService = new ReservationService();
        try {
            // example reservation ID
            String status = reservationService.getStatus(idReservation);
            if (!status.equals("En Cours")) {
                acceptRes.setDisable(true);
                cancelRes.setDisable(true);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCoachReservationController.class.getName()).log(Level.SEVERE, null, ex);
            // Skip the error and disable the acceptRes button
            acceptRes.setDisable(true);
            cancelRes.setDisable(true);

        }

    }

    @FXML
    private void accRes(ActionEvent event) throws Exception {
        ReservationService reservationService = new ReservationService();
        OffreService os = new OffreService();

        MailingOff sendEmail = new MailingOff();
        try {
            // update the status of the reservation to "Accepted"
            reservationService.updateStatus(idReservation, "Accepted");
            reservation res = reservationService.recupereridOffByIdRes(idReservation);

            int idOffre = res.getId_offre();
            Offre offre = os.recupererById(idOffre);
            String descOff = offre.getDescription();

            System.out.println(descOff);

            // disable both buttons after updating the status
            acceptRes.setDisable(true);
            cancelRes.setDisable(true);

            String recipient = session.getEmail();
            String recipientNumber = "+21694240978";
            String subject = "Confirmation de réservation";
            String content = "Votre réservation pour l'offre suivante a été confirmée :\n\n" + "offre:" + descOff;
            System.out.println();

            sendEmail.mailing(recipient, subject, content);
            sendEmail.sendSms(recipientNumber, content);
            // display message to the user
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Reservation Status");
            alert.setHeaderText(null);
            alert.setContentText("Reservation has been accepted.");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(ItemCoachReservationController.class.getName()).log(Level.SEVERE, null, ex);
            // display error message to the user
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating reservation status. Please try again later.");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancRes(ActionEvent event) throws Exception {
        ReservationService reservationService = new ReservationService();
        OffreService os = new OffreService();
        MailingOff sendEmail = new MailingOff();

        try {
            // update the status of the reservation to "Refused"
            reservationService.updateStatus(idReservation, "Refused");
            reservation res = reservationService.recupereridOffByIdRes(idReservation);

            int idOffre = res.getId_offre();
            Offre offre = os.recupererById(idOffre);
            String descOff = offre.getDescription();

            // disable both buttons after updating the status
            acceptRes.setDisable(true);
            cancelRes.setDisable(true);

            String recipient = session.getEmail();
            String recipientNumber = "+21694240978";
            String subject = "Reservation Status";
            String content = "Votre réservation pour l'offre suivante a été refuseé :\n\n" + "offre:" + descOff;
            System.out.println();

            sendEmail.mailing(recipient, subject, content);
            sendEmail.sendSms(recipientNumber, content);

            // display message to the user
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Reservation Status");
            alert.setHeaderText(null);
            alert.setContentText("Reservation has been refused.");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(ItemCoachReservationController.class.getName()).log(Level.SEVERE, null, ex);
            // display error message to the user
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating reservation status. Please try again later.");
            alert.showAndWait();
        }
    }

}
