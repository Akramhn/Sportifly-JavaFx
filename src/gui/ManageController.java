package gui;


import entities.Participant;
import entities.User;
import gui.MainWindowController;
import services.ParticipantService;
import util.AlertUtils;
import util.Constants;
import util.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public DatePicker dateParticipationDP;
    @FXML
    public ComboBox<User> userCB;
    @FXML
    public ComboBox<RelationObject> eventCB;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Participant currentParticipant;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (User user : ParticipantService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }
        for (RelationObject event : ParticipantService.getInstance().getAllEvents()) {
            eventCB.getItems().add(event);
        }

        currentParticipant = ShowAllControllerBackPart.currentParticipant;

        if (currentParticipant != null) {
            topText.setText("Modifier participant");
            btnAjout.setText("Modifier");

            try {
                dateParticipationDP.setValue(currentParticipant.getDateParticipation());
                userCB.setValue(currentParticipant.getUser());
                eventCB.setValue(currentParticipant.getEvent());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter participant");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Participant participant = new Participant(
                    dateParticipationDP.getValue(),
                    userCB.getValue(),
                    eventCB.getValue()
            );

            if (currentParticipant == null) {
                if (ParticipantService.getInstance().add(participant)) {
                    AlertUtils.makeSuccessNotification("Participant ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PARTICIPANT);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                participant.setId(currentParticipant.getId());
                if (ParticipantService.getInstance().edit(participant)) {
                    AlertUtils.makeSuccessNotification("Participant modifié avec succés");
                    ShowAllControllerBackPart.currentParticipant = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PARTICIPANT);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {

        if (dateParticipationDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour dateParticipation");
            return false;
        }

        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir utilisateur");
            return false;
        }


        if (eventCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir event");
            return false;
        }


        return true;
    }
}