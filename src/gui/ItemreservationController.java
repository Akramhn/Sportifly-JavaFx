/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.ReservationService;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class ItemreservationController implements Initializable {

    @FXML
    private Label desc;
    @FXML
    private Label resId;
    @FXML
    private Label dateres;
    @FXML
    private Label status;
    @FXML
    private Button cancelRes;

    private ReservationService resService = new ReservationService();

    private int idRes;

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
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
        resId.setText(String.valueOf(Reservation.getId()));
        dateres.setText(String.valueOf(Reservation.getDate()));
        status.setText(Reservation.getStatus());
        setIdRes(Reservation.getId());

        if (Reservation.getStatus().equals("Refused")) {
            status.setTextFill(Color.RED);
        } else if (Reservation.getStatus().equals("Accepted")) {
            status.setTextFill(Color.GREEN);
        } else if (Reservation.getStatus().equals("En Cours")) {
            status.setTextFill(Color.GRAY);
        }

    }

    @FXML
    private void cancelRes(ActionEvent event) {
        try {
            // Create an Alert object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this reservation?");
            alert.setContentText("Click OK to confirm or Cancel to go back.");

            // Add "OK" and "Cancel" buttons to the alert
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Show the alert and wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();

            // If the user clicked "OK", delete the reservation from the database
            if (result.get() == okButton) {
                resService.supprimer(getIdRes());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListReservation.fxml"));
                Parent root;
                try {
                    root = (Parent) fxmlLoader.load();
                    Stage stage = (Stage) cancelRes.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ListOffreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ItemreservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
