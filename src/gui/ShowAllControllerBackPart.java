package gui;

import entities.Participant;
import gui.MainWindowController;
import services.ParticipantService;
import util.AlertUtils;
import util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllControllerBackPart implements Initializable {

    public static Participant currentParticipant;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    List<Participant> listParticipant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listParticipant = ParticipantService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listParticipant);

        if (!listParticipant.isEmpty()) {
            for (Participant participant : listParticipant) {
                mainVBox.getChildren().add(makeParticipantModel(participant));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeParticipantModel(Participant participant) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_PARTICIPANT)));
                
            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#dateParticipationText")).setText("DateParticipation : " + participant.getDateParticipation());
            ((Text) innerContainer.lookup("#userText")).setText("User : " + participant.getUser().getLastname());
            ((Text) innerContainer.lookup("#eventText")).setText("Event : " + participant.getEvent().getName());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierParticipant(participant));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerParticipant(participant));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterParticipant(ActionEvent event) {
        currentParticipant = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_PARTICIPANT);
    }

    private void modifierParticipant(Participant participant) {
        currentParticipant = participant;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_PARTICIPANT);
    }

    private void supprimerParticipant(Participant participant) {
        currentParticipant = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer participant ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ParticipantService.getInstance().delete(participant.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PARTICIPANT);
                } else {
                    AlertUtils.makeError("Could not delete participant");
                }
            }
        }
    }
}
