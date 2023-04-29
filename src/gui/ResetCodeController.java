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

/**
 * FXML Controller class
 *
 * @author user
 */
public class ResetCodeController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private Button submitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void verifyCode(ActionEvent actionEvent) throws IOException {
    String codeText = code.getText().trim(); 
    if (codeText.isEmpty()) { 
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Code is required");
        alert.setContentText("Please fill the required field.");

        alert.showAndWait();
    } else if (!ForgotPasswordController.random_password.equals(codeText)) { 
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid code");
        alert.setContentText("Please enter a valid Code.");
        alert.showAndWait();
    } else { 
        Parent productParent = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        Scene productScene = new Scene(productParent, 600, 500);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(productScene);
        window.show();
    }
    }
}

