/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.UserService;
import util.MyDB;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ModifierUserController implements Initializable {

    @FXML
    private Button exit;
    @FXML
    private TextField champemail;
    @FXML
    private TextField champname;
    @FXML
    private TextField champpassword;
    @FXML
    private Button modifieruser;
        private User user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    

    

    public void setUser(User u) {
        this.user =u;
        
        champemail.setText(u.getEmail());
        champname.setText(u.getLastname());
         champpassword.setText(u.getPassword());
        
    }

    @FXML
 public void modifierutilisateur(ActionEvent event) {
    // récupérer les nouvelles valeurs entrées par l'utilisateur
    String email = champemail.getText();
    String lastname = champname.getText();
    String password = champpassword.getText();

    // validate input fields
    if (email.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Tous les champs doivent être remplis.");
        alert.showAndWait();
    } else if (lastname.length() < 4) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Le nom doit contenir au minimum 4 caractères.");
        alert.showAndWait();
    } else if (password.length() < 6 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Le mot de passe doit être de longueur minimum 6 et doit contenir des chiffres et des lettres.");
        alert.showAndWait();
    } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("L'email n'est pas valide.");
        alert.showAndWait();
    } else {
        user.setEmail(email);
        user.setLastname(lastname);
        user.setPassword(password);

        try {
            UserService u = new UserService();
            u.ModifierUser(user);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a été modifié avec succès.");
            alert.showAndWait();
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la modification de l'utilisateur.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
    }
    
    
    
    

    


