/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Activiter;
import entities.Categorie;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ActiviterService;
import services.CategorieService;



/**
 * FXML Controller class
 *
 * @author hadjn
 */
public class CategController implements Initializable {

   


   
 @FXML
    private Button btn_ajouter;

CategorieService cs = new CategorieService();


    @FXML
    private Button btnRetour;
    @FXML
    private TextField nom;
    @FXML
    private TextField desc;
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
       

       
    

    


 @FXML
    private void ajoutercateg(ActionEvent event) throws IOException, SQLException { 
         if (nom.getText().isEmpty() || desc.getText().isEmpty() ) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the required fields!");
        alert.showAndWait();
        return;
    }
            Categorie cat=new Categorie();
 
          cat.setNom(nom.getText());
          cat.setDescription(desc.getText());
            try {
                
                cs.ajouter(cat);
               Alert alert = new Alert(AlertType.CONFIRMATION);
               btn_ajouter.setDisable(true);
alert.setTitle("Success");
alert.setHeaderText(null);
alert.setContentText("Operation completed successfully!");
alert.showAndWait();

            } catch (SQLException ex) {
                Logger.getLogger(CategController.class.getName()).log(Level.SEVERE, null, ex);
            }

             
         
       
    }
    
     @FXML
    private void returndisplay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/admi_categ.fxml"));
        CategController aec = loader.getController();
        Parent root = loader.load();
        btnRetour.getScene().setRoot(root);
    
    
}
    
}
