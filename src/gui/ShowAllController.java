package gui;

import entities.Event;
import gui.MainWindowController;
import services.EventService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Event currentEvent;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    List<Event> listEvent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listEvent = EventService.getInstance().getAll();
       
        displayData();
             

        
   }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listEvent);

        if (!listEvent.isEmpty()) {
            for (Event event : listEvent) {
                
                mainVBox.getChildren().add(makeEventModel(event));
                

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeEventModel(
            Event event
    ) {
        Parent parent = null;
        try {
                
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_EVENT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#titreText")).setText("Titre : " + event.getTitre());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + event.getDescription());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierEvent(event));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerEvent(event));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       
        return parent;
    }

    @FXML
    private void ajouterEvent(ActionEvent event) {
        currentEvent = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_EVENT);
    }

    private void modifierEvent(Event event) {
        currentEvent = event;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_EVENT);
    }

    private void supprimerEvent(Event event) {
        currentEvent = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer event ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (EventService.getInstance().delete(event.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_EVENT);
            } else {
                AlertUtils.makeError("Could not delete event");
            }
        }
    }
}
