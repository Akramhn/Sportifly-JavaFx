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
public class ListReservationController implements Initializable {

    @FXML
    private VBox postsContainer;
    @FXML
    private Button retour;
    @FXML
    private VBox pnitems;

    private List<reservation> reservations;
    private ReservationService resService = new ReservationService();
    @FXML
    private Button mesoffres;
    @FXML
    private Button mesReservation;
        private SessionManager session;
    @FXML
    private Button plan;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            reservations = new ArrayList<>(resService.recupererById(session.getId()));
            for (reservation reservation : reservations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Itemreservation.fxml"));
                try {
                    HBox hbox = fxmlLoader.load();
                    ItemreservationController ItemreservationController = fxmlLoader.getController();
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
    private void retour(ActionEvent event) {
    try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/espaceUser.fxml"));

            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void mesOff(ActionEvent event) {
          /*  try {
        // Get the current window's Stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("espaceUser.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    } */
                  try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/espaceUser.fxml"));
            
            Parent root = loader.load();
            mesoffres.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        } }

    @FXML
    private void gotoplanning(ActionEvent event) {
        try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/templateActivite.fxml"));

                                    Parent root = loader.load();
                                    plan.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }
    }

   

}
