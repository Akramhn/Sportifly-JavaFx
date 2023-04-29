/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.Random;
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
import services.UserService;


/**
 * FXML Controller class
 *
 * @author user
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private Button submitButton;
      static String random_password;
         static String email_;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void sendEmail(ActionEvent actionEvent) throws Exception {
       
    String emailInput = email.getText().trim();
    if (emailInput.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("required field");
        alert.setContentText("Please fill the required field.");
        alert.showAndWait();
        return;
    }
    if (!emailInput.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid email");
        alert.setContentText("Please enter a valid email address.");
        alert.showAndWait();
        return;
    }
       UserService userService = new UserService();
    if (!userService.isEmailRegistered(emailInput)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Email not found");
        alert.setContentText("The email you entered is not registered.");
        alert.showAndWait();
        return;
    }
        Random rand = new Random();

        random_password = String.format("%04d", rand.nextInt(10000));

        email_ = email.getText().trim();

        Mailing.sendMail(email.getText().trim(), "reset password", random_password);
        Parent productParent = FXMLLoader.load(getClass().getResource("ResetCode.fxml"));
        Scene productScene = new Scene(productParent,600, 500);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(productScene);
        window.show();

    }
    
}
