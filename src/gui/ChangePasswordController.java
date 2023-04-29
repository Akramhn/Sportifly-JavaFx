/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mailing.Mailing;
import org.mindrot.jbcrypt.BCrypt;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private TextField n_password;
    @FXML
    private Button submitButton;
    @FXML
    private TextField n_confirm_password;
    UserService serviceUser = new UserService();
        static String random_password;
            public TextField email;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     public void sendEmail(ActionEvent actionEvent) throws Exception {
        random_password = "1234";
        Mailing.sendMail(email.getText().trim(), "reset password", random_password);
        Parent productParent = FXMLLoader.load(getClass().getResource("ResetCode.fxml"));
        Scene productScene = new Scene(productParent, 1074, 576);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(productScene);
        window.show();

    }

   public void changePassword(ActionEvent actionEvent) throws IOException {
    if (!n_password.getText().equals(n_confirm_password.getText())) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning"); 
        alert.setHeaderText("Verifier votre mot de passe");
        alert.showAndWait();
    } else {
        String hashedPassword = BCrypt.hashpw(n_password.getText(), BCrypt.gensalt());
        serviceUser.updateUserPasswordByEmail(ForgotPasswordController.email_, hashedPassword);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Success"); 
        alert.setHeaderText("Mot de passe modifié");
        alert.showAndWait();
    }
}
}
