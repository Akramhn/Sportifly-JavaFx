/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Actualite;
import entities.Commentaire_Act;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
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
import services.Commentaire_ActService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class Ajouter_ComController implements Initializable {

    int id_actualite;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField TFcomment;
    @FXML
    private Button btnSubmit;
    Actualite actualite;
    SessionManager session;

    Commentaire_ActService cs = new Commentaire_ActService();
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
    private Button event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void RetourHandleButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficher_Com.fxml"));
        Ajouter_ActController aec = loader.getController();
        Parent root = loader.load();
        btnRetour.getScene().setRoot(root);
    }

    @FXML
    private void ajouterCom(ActionEvent event) throws SQLException {

        String contenu = TFcomment.getText().trim(); // récupérer le contenu et supprimer les espaces au début et à la fin

        if (contenu.isEmpty()) { // Vérifier si le champ est vide
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Le champ contenu est obligatoire.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si le champ est vide
        }

        Commentaire_Act comment = new Commentaire_Act();
        comment.setContenu(contenu);
        comment.setId_actualite_id(id_actualite);
        comment.setId_user(session.getId());
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Date dt = new Date(currentDate.getTime());
        Timestamp heure = new Timestamp(System.currentTimeMillis());
        comment.setDate(LocalDateTime.now());

        cs.ajouter(comment);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Operation completed successfully!");
        alert.showAndWait();
    }

    void setActualiteId(int id) {
        id_actualite = id;
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
        } else if (clickedButton.getId().equals("plan")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminActivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) plan.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("cat")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admi_categ.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("act")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Act.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) act.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("comm")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Com.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) comm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } //        else if (clickedButton.getId().equals("MesressA")) {
        //            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
        //            Parent root = loader.load();
        //            Scene scene = new Scene(root);
        //            Stage stage = (Stage) MesressA.getScene().getWindow();
        //            stage.setScene(scene);
        //            stage.show();
        //        }
        else if (clickedButton.getId().equals("MesressA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesressA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

}
