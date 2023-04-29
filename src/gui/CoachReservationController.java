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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ReservationService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class CoachReservationController implements Initializable {

    private SessionManager session;

    @FXML
    private Button addOffre;
    @FXML
    private VBox postsContainer;
    @FXML
    private Button retour;
    @FXML
    private VBox pnitems;
    private List<reservation> reservations;
    private ReservationService resService = new ReservationService();
    @FXML
    private Button mesoff;
    @FXML
    private Button plann;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            reservations = new ArrayList<>(resService.recupererByOffreUserId(session.getId()));
            for (reservation reservation : reservations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemCoachreservation.fxml"));
                try {
                    HBox hbox = fxmlLoader.load();
                    ItemCoachReservationController ItemreservationController = fxmlLoader.getController();
                    ItemreservationController.setData(reservation);
                    pnitems.getChildren().add(hbox);
                } catch (IOException ex) {
                    Logger.getLogger(CoachOffresController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Addoffre(ActionEvent event) {
    }

    @FXML
    private void MesOffr(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachOffres.fxml"));

            Parent root = loader.load();
            mesoff.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void retourOff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachOffres.fxml"));

            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gotoPlanning(ActionEvent event) throws IOException {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/templateActivite.fxml"));

                                    Parent root = loader.load();
                                    plann.getScene().setRoot(root);
                                
    }

}
