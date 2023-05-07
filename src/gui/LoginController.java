package gui;

import com.pi.MainApp;
import entities.User;
import services.ParticipantService;
import util.AlertUtils;
import util.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import util.SessionManager;

public class LoginController implements Initializable {

    @FXML
    public ComboBox<User> userCB;
    SessionManager session;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (User user : ParticipantService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }
    }

    @FXML
    public void frontend(ActionEvent actionEvent) {
        if (userCB.getValue() != null) {
            session.setUser(userCB.getValue());
            MainApp.getInstance().loadFront();
        } else {
            AlertUtils.makeError("Choisir un utilisateur");
        }
    }

    @FXML
    public void backend(ActionEvent actionEvent) {
        MainApp.getInstance().loadBack();
    }
}
