/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class MainController implements Initializable {

    @FXML
    private Button EspaceCoach;
    @FXML
    private Button EspaceUser;
    @FXML
    private Button EspaceAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void EspaceCoach(ActionEvent event) {
            try {
                        Parent adminRoot = FXMLLoader.load(getClass().getResource("/gui/CoachOffres.fxml"));
                        Scene adminScene = new Scene(adminRoot);
                        Stage adminStage = new Stage();
                        adminStage.setTitle("Admin");
                        adminStage.setScene(adminScene);
                        adminStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(firstwindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
    
    }

    @FXML
    private void EspaceUser(ActionEvent event) {
            try {
                        Parent adminRoot = FXMLLoader.load(getClass().getResource("/gui/espaceUser.fxml"));
                        Scene adminScene = new Scene(adminRoot);
                        Stage adminStage = new Stage();
                        adminStage.setTitle("Admin");
                        adminStage.setScene(adminScene);
                        adminStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(firstwindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }

    @FXML
    private void EspaceAdmin(ActionEvent event) {
            try {
                        Parent adminRoot = FXMLLoader.load(getClass().getResource("/gui/AllProducts.fxml"));
                        Scene adminScene = new Scene(adminRoot);
                        Stage adminStage = new Stage();
                        adminStage.setTitle("Admin");
                        adminStage.setScene(adminScene);
                        adminStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(firstwindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    
}
