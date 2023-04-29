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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserService;

public class ChatBotController implements Initializable {
    
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtInput;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblOutput;

    private final UserService userService = new UserService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set button action
        btnSend.setOnAction(event -> {
            String input = txtInput.getText().toLowerCase();
            String response = getResponse(input);
            lblOutput.setText(response);
            txtInput.clear();
        });
    }

 private String getResponse(String input) {
    if (input.contains("hello") || input.contains("bonjour")) {
        return "Comment puis-je vous aider ?";
    } else if (input.contains("je veux faire des cours avec ce coach")) {
        return "Le coach va consulter son planning et il vous répondra dès que possible. Bienvenue chez Sportifly !";
    } else if (input.contains("j'ai pas pu consulter le planning du coach")) {
        return "Je suis désolé pour cela. Je vais demander au coach de vous contacter directement.";
    } else if (input.contains("je veux changer la date du cours avec ce coach")) {
        return "D'accord, nous allons vous contacter pour fixer une nouvelle date.";
    } else if (input.contains("merci")) {
        return "Merci pour votre fidélité avec Sportifly !";
    } else {
        return "Je suis désolé, je ne comprends pas. Pouvez-vous reformuler votre question s'il vous plaît ?";
    }
}
 
 
 
  @FXML
    void retour(ActionEvent event) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("ConsulterCoach.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
 
 
}
