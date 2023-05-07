package gui;

import com.pi.MainApp;
import util.Animations;
import util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class BarreController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#000000");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnEvents;
    @FXML
    private Button btnParticipants;
    @FXML
    private Button mainComponent;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnEvents,
                btnParticipants,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnEvents.setTextFill(Color.WHITE);
        btnParticipants.setTextFill(Color.WHITE);

    }

    private void afficherEvents(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_EVENT);

        btnEvents.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnEvents, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void afficherParticipants(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_PARTICIPANT);

        btnParticipants.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnParticipants, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        MainApp.getInstance().logout();
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }
}
