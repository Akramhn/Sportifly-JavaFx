package gui;

import com.pi.MainApp;
import entities.Event;
import entities.Participant;
import services.EventService;
import services.ParticipantService;
import util.AlertUtils;
import util.Constants;
import util.RelationObject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import util.SessionManager;

public class ShowAllControllerFrontEvent implements Initializable {

    public static Event currentEvent;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;
    @FXML
    public ComboBox<String> sortCB;
    SessionManager session;
    List<Event> listEvent;
    List<Participant> listParticipant;
    public static final String ACCOUNT_SID = "AC529fc2fae76a63745bb1c234c52e9811";
    public static final String AUTH_TOKEN = "d710ddea6b80bbdc86c4b9116bc7da1a";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listEvent = EventService.getInstance().getAll();
        sortCB.getItems().addAll("Titre", "Description", "Date", "Lieu");
        listParticipant = ParticipantService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listEvent);

        if (!listEvent.isEmpty()) {
            for (Event event : listEvent) {
                if (event.getTitre().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeEventModel(event));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeEventModel(Event event) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_EVENT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#titreText")).setText("Titre : " + event.getTitre());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + event.getDescription());

            Path selectedImagePath = FileSystems.getDefault().getPath("C:\\Users\\wadah\\OneDrive\\Desktop\\integre\\usersgestion\\public\\uploads\\event\\" + event.getImage());
            System.out.println(selectedImagePath);
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
                System.out.println(selectedImagePath);
            }

            Text participerText = ((Text) innerContainer.lookup("#participerText"));
            Button participateBtn = ((Button) innerContainer.lookup("#participerBtn"));
            if (session.getId() == 0) {
                participateBtn.setVisible(true);
            }
            for (Participant participant : listParticipant) {
                if (participant.getEvent() != null) {
                    if (participant.getEvent().getId() == event.getId()) {
                        if (participant.getUser() != null) {
                            if (participant.getUser().getId() == session.getId()) {
                                participateBtn.setVisible(false);
                                participerText.setText("participer");
                            }
                        }
                    }
                }
            }

            participateBtn.setOnAction((ignored) -> participer(event, participerText, participateBtn));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    public static void sendSms(String recipient, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(recipient), // To number
                new PhoneNumber("+12706793378"), // From number
                messageBody) // SMS body
                .create();

        System.out.println("Message sent: " + message.getSid());
    }

    private void participer(Event event, Text text, Button participateBtn) {

        Participant participant = new Participant(LocalDate.now(), session.getUser(), new RelationObject(event.getId(), event.getTitre()));
        if (ParticipantService.getInstance().add(participant)) {
            participateBtn.setVisible(false);
            text.setText("Vous participer a cet evennement");
            sendSms("+21696243787", "vous avez participés");

        } else {
            AlertUtils.makeError("Error");
        }
    }

    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }

    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listEvent);
        displayData("");
    }
}
