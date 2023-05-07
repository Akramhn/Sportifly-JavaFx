/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Commentaire_Act;
import entities.User;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import services.ActiviterService;
import services.Commentaire_ActService;
import services.UserService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class CommentsCardController implements Initializable {

    @FXML
    private ImageView userPic;
    @FXML
    private Label username;
    @FXML
    private TextField comments;
    @FXML
    private Button modif;
    @FXML
    private Button supp;

    SessionManager session;
    private Commentaire_Act comm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setComments(Commentaire_Act comm) {
        UserService us = new UserService();

        User u = new User();
        u = us.getUserById(comm.getId_user());
        this.comm = comm;
        comments.setText(comm.getContenu());
        username.setText(u.getLastname());

        System.out.println(comm.getId_user());
        if (comm.getId_user() == session.getId()) {
            modif.setVisible(true);
            supp.setVisible(true);
        } else {
            modif.setVisible(false);
            supp.setVisible(false);
        }
    }

    @FXML
    private void modifierComment(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/modifier_Com_1.fxml"));
        Parent root;
        try {
            root = loader.load();

            Modifier_ComController_1 CommentsComController = loader.getController();

            CommentsComController.setCom(comm);

            modif.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(Afficher_ComController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimerComment(ActionEvent event) throws SQLException, IOException {

        Commentaire_ActService as = new Commentaire_ActService();
        as.supprimertest(comm);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/actualite.fxml"));
        Parent root;
        try {
            root = loader.load();
            ActualiteController ActCon = loader.getController();
            ActCon.initialize(comm.getId_actualite_id());

            supp.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ActController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
