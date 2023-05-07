package gui;

//import com.pi.MainApp;
//import entities.Event;
//import entities.Participant;
//import static gui.ShowAllControllerFrontEvent.sendSms;
//import services.EventService;
//import services.ParticipantService;
//import util.AlertUtils;
//import util.Constants;
//import util.RelationObject;
import entities.Actualite;
import entities.Event;
import entities.Participant;
import entities.User;
import static gui.ShowAllControllerFrontEvent.sendSms;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ActualiteService;
import services.EventService;
import services.ParticipantService;
import services.UserService;
import util.AlertUtils;
import util.Constants;
import util.RelationObject;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class HomePageController implements Initializable {

    @FXML
    private HBox EventHbox;

    List<Event> listEvent;
    List<Participant> listParticipant;
    @FXML
    private ComboBox<String> sortCB;
    @FXML
    private GridPane actCOntainer;

    private int itemsPerPage = 4;
    private int currentPageIndex = 0;

    private ActualiteService actualiteService = new ActualiteService();
    private List<Actualite> actualites = new ArrayList<>();
    @FXML
    private TextField tfrecherhce;
    @FXML
    private Button tri;
    @FXML
    private Button login;
    SessionManager session;
    @FXML
    private Button signup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listEvent = EventService.getInstance().getAll();
        sortCB.getItems().addAll("Titre", "Description", "Date", "Lieu");
        listParticipant = ParticipantService.getInstance().getAll();
        displayData("");

        if (session.getId() != 0) {
            // If the user is logged in, change the text of the login button to "Mon Espace"
            login.setText("Mon Espace");

            login.setOnAction(event -> {
                if (SessionManager.getRoles().contains("Role_USER")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/espaceUser.fxml"));

                        Parent root = loader.load();
                        login.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (SessionManager.getRoles().contains("Role_Coach")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachOffres.fxml"));

                        Parent root = loader.load();
                        login.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (SessionManager.getRoles().contains("ROLE_ADMIN")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

                        Parent root = loader.load();
                        login.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            signup.setText("Sign Out");

            signup.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to logout?");
                Optional<ButtonType> option = alert.showAndWait();
                try {
                    if (option.get().equals(ButtonType.OK)) {
                        session.setId(0);
                        signup.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event1) -> {
                            double x = event1.getSceneX();
                            double y = event1.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event1) -> {
                            double x = 0;
                            stage.setX(event1.getScreenX() - x);
                            double y = 0;
                            stage.setY(event1.getScreenY() - y);

                            stage.setOpacity(.8);
                        });

                        root.setOnMouseReleased((MouseEvent event1) -> {
                            stage.setOpacity(1);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);

                        stage.setScene(scene);
                        stage.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } else {
            // If the user is not logged in, leave the text of the login button as "Login"
            login.setText("Login");

        }

        // Retrieve actualites from the database
        try {
            actualites = actualiteService.recuperer();
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Populate the container with actualites
        int column = 0;
        int row = 1;
        final int maxColumns = 4;

        for (Actualite actualite : actualites) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("act.fxml"));
            VBox actBox = null;
            try {
                actBox = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActController actC = fxmlLoader.getController();
            actC.setActualite(actualite);

            if (column == maxColumns) {
                column = 0;
                ++row;
            }

            actCOntainer.add(actBox, column++, row);
            GridPane.setMargin(actBox, new Insets(10));
        }

        // Define a listener for the search bar text
        tfrecherhce.textProperty().addListener((observable, oldValue, newValue) -> {
            // Clear the container before adding filtered items
            actCOntainer.getChildren().clear();

            // Filter actualites based on the search bar text
            List<Actualite> filteredActualites = actualites.stream()
                    .filter(a -> a.getTitre().contains(newValue))
                    .collect(Collectors.toList());

            // Add filtered items to the container
            int filteredColumn = 0;
            int filteredRow = 1;
            for (Actualite filteredActualite : filteredActualites) {
                FXMLLoader filteredLoader = new FXMLLoader(getClass().getResource("act.fxml"));
                VBox filteredBox = null;
                try {
                    filteredBox = filteredLoader.load();
                } catch (IOException ex) {
                    Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ActController filteredController = filteredLoader.getController();
                filteredController.setActualite(filteredActualite);

                if (filteredColumn == maxColumns) {
                    filteredColumn = 0;
                    ++filteredRow;
                }

                actCOntainer.add(filteredBox, filteredColumn++, filteredRow);
                GridPane.setMargin(filteredBox, new Insets(10));
            }
        });

        // Set up an event handler for the "tri" button
        tri.setOnAction(event -> trierActualites());

    }

    public void afficherActualites(List<Actualite> actualites) {
        for (Actualite actualite : actualites) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Act.fxml"));
            Parent root;
            try {
                root = (Parent) loader.load();
                ActController actController = loader.getController();
                actController.setActualite(actualite);
                VBox actualiteBox = new VBox();
                actualiteBox.getChildren().add(root);
                // Ajouter actualiteBox à votre layout pour afficher l'actualité
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void showCommentaires(Actualite actualite) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Actualite.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            ActualiteController controller = loader.getController();
            controller.setActualite(actualite);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trierActualites() {
        actualites.sort(Comparator.comparing(Actualite::getTitre));
        actCOntainer.getChildren().clear();
        int column = 0;
        int row = 1;
        final int maxColumns = 4;
        for (Actualite actualite : actualites) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("act.fxml"));
            VBox actBox = null;
            try {
                actBox = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActController actC = fxmlLoader.getController();
            actC.setActualite(actualite);
            if (column == maxColumns) {
                column = 0;
                ++row;
            }
            actCOntainer.add(actBox, column++, row);
            GridPane.setMargin(actBox, new Insets(10));
        }
    }

    void displayData(String searchText) {
        EventHbox.getChildren().clear();

        Collections.reverse(listEvent);

        if (!listEvent.isEmpty()) {
            for (Event event : listEvent) {
                if (event.getTitre().toLowerCase().startsWith(searchText.toLowerCase())) {
                    EventHbox.getChildren().add(makeEventModel(event));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            EventHbox.getChildren().add(stackPane);
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
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            Text participerText = ((Text) innerContainer.lookup("#participerText"));
            Button participateBtn = ((Button) innerContainer.lookup("#participerBtn"));

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

            participateBtn.setOnAction((ignored)
                    -> {
                if (session.getId() == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Connexion obligatoire");
                    alert.setHeaderText(null);
                    alert.setContentText("You should Login first to participate.");
                    alert.showAndWait();
                    return;

                } else {
                    participer(event, participerText, participateBtn);
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void participer(Event event, Text text, Button participateBtn) {
        if (session.getId() != 0) {

            UserService us = new UserService();
            User user = new User();
            user = us.getUserById(session.getId());
            Participant participant = new Participant(LocalDate.now(), user, new RelationObject(event.getId(), event.getTitre()));
            if (ParticipantService.getInstance().add(participant)) {
                participateBtn.setVisible(false);
                text.setText("Vous participer a cet evennement");
                // sendSms("+21696243787", "vous avez participés");

            } else {
                AlertUtils.makeError("Error");
            }
        }
    }

    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listEvent);
        displayData("");
    }

    @FXML
    private void redirecdtToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/signin.fxml"));

        Parent root = loader.load();
        login.getScene().setRoot(root);
    }

    @FXML
    private void GoToSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/signup.fxml"));

        Parent root = loader.load();
        login.getScene().setRoot(root);
    }

}
