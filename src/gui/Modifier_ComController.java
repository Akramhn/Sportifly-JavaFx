/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Commentaire_Act;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.Commentaire_ActService;
import util.SessionManager;



/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class Modifier_ComController implements Initializable {
Commentaire_Act com;
    Commentaire_ActService ps = new Commentaire_ActService();
    @FXML
    private Button btnRetour;
    @FXML
    private TextField TFcomment;
    @FXML
    private Button btnSubmit;
    SessionManager session ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    public void setCom(Commentaire_Act com) {
        
        this.com = com;
         TFcomment.setText(com.getContenu());
    }

    @FXML
    private void RetourHandleButton(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficher_Com.fxml"));
        Afficher_ComController aec = loader.getController();
     
        Parent root = loader.load();
        btnRetour.getScene().setRoot(root);
    }

    @FXML
   private void modifierAct(ActionEvent event) {
    String contenu = TFcomment.getText();
    if (contenu.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a comment!");
        alert.showAndWait();
    } else {
        com.setContenu(contenu);
        try {
            ps.modifier(com);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Operation completed successfully!");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

    
}
