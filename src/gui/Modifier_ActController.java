/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Actualite;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ActualiteService;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class Modifier_ActController implements Initializable {

    @FXML
    private TextField TAdescription;
    @FXML
    private TextField TFcategorie;
    @FXML
    private TextField TFtitre;
    @FXML
    private TextField TFimage;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnSubmit;
    
    private Actualite actualite;
      ActualiteService ps = new ActualiteService();
     private int id;
    private TextField TFid;
    @FXML
    private Button MesOffA;
    @FXML
    private Button MesressA;
    @FXML
    private Button utilisateur;
    @FXML
    private Button plan;
    @FXML
    private Button cat;
    @FXML
    private Button act;
    @FXML
    private Button comm;
    @FXML
    private Button evente;
    @FXML
    private Button part;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
   
   }    
 @FXML
     private void RetourHandleButton(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficher_Act.fxml"));
        Modifier_ActController aec = loader.getController();
        Parent root = loader.load();
        btnRetour.getScene().setRoot(root);
    }
     
     @FXML
   private void modifierAct(ActionEvent event)  throws SQLException  {
    String titre = TFtitre.getText();
    String description = TAdescription.getText();
    String image = TFimage.getText();
    String categorie = TFcategorie.getText();
   
    // Vérifier si tous les champs sont remplis
    if (titre.trim().isEmpty() || description.trim().isEmpty() || image.trim().isEmpty() || categorie.trim().isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
    }

    actualite.setDescription(description);
    actualite.setImage(image);
    actualite.setCategorie(categorie);
    actualite.setTitre(titre);

    try {
        ps.modifier(actualite);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Opération effectuée avec succès !");
        alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(Modifier_ActController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
     public void setActualite(Actualite actualite) {
       this.actualite = actualite;
        TFtitre.setText(actualite.getTitre());
        TAdescription.setText(actualite.getDescription());
        TFimage.setText(actualite.getImage());
        TFcategorie.setText(actualite.getCategorie());
    }

    @FXML
      private void handleClicks(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getId().equals("MesOffA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListOffreBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesOffA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("utilisateur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) utilisateur.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("plan")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminActivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) plan.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("cat")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admi_categ.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("act")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Act.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) act.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("comm")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Com.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) comm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("MesressA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesressA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("evente")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
             else if (clickedButton.getId().equals("part")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


    
    }

        
    
    

