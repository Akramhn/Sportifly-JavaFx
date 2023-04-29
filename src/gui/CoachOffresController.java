/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.CommandLauncherFactory;
import services.OffreService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class CoachOffresController implements Initializable {
    private SessionManager session;

    @FXML
    private VBox postsContainer;
    private List<Offre> Offres;
    private OffreService offreService = new OffreService();
    @FXML
    private Button addOffre;
    @FXML
    private Button Mesreservation;
    @FXML
    private Button meet;
    @FXML
    private Button planning;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Offres = new ArrayList<>(offreService.recupererOffById(session.getId()));
            for (Offre offre : Offres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ListOffre.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ListOffreController listoffreContoller = fxmlLoader.getController();
                    
                    listoffreContoller.setData(offre);
                    postsContainer.getChildren().add(vBox);
                } catch (IOException ex) {
                    Logger.getLogger(CoachOffresController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(CoachOffresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Addoffre(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AddOffre.fxml"));

            Parent root = loader.load();
            addOffre.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Mesrese(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachReservation.fxml"));

            Parent root = loader.load();
            Mesreservation.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void GotoMeet(ActionEvent event) throws IOException {
        String url = "https://meet.google.com/new";
        CommandLauncher launcher = CommandLauncherFactory.createVMLauncher();
        Runtime.getRuntime().exec(String.format("rundll32 url.dll,FileProtocolHandler %s", url));
    }
    
    
     @FXML
    private void gotoPlanning(ActionEvent event) {
        
       
              try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/templateActivite.fxml"));
            
            Parent root = loader.load();
            planning.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        } }

}
