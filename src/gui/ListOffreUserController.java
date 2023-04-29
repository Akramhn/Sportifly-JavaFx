/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import entities.Stars;
import entities.reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ReservationService;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.StarsService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class ListOffreUserController implements Initializable {

    @FXML
    private VBox Vbox;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label username;
    @FXML
    private Label time;
    @FXML
    private ImageView audiance;
    @FXML
    private Label Desc;
    @FXML
    private Label Prix;
    @FXML
    private ImageView affOffre;
    @FXML
    private Button resOffre;

    private int idOffre;
    @FXML
    private Rating rating;
        private SessionManager session;


    public int getId() {
        return idOffre;
    }

    public void setId(int id) {
        this.idOffre = id;
    }

    public void setData(Offre offre) throws SQLException {

        Image image = new Image(getClass().getResourceAsStream(offre.getAffiche()));
        affOffre.setImage(image);
        Prix.setText("prix :" + String.valueOf(offre.getPrix()));
        Desc.setText("Descritpion :" + offre.getDescription());
        setId(offre.getId());

        username.setText("Messi");
        // Get the rating for the current user and the current offer
        int id_user = session.getId(); // replace this with the actual user ID
        StarsService ss = new StarsService();
        int ratingValue = ss.getRatingForOfferAndUser(id_user, idOffre);

        // Display the rating value
        rating.setRating(ratingValue);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set the default rating to 0
        rating.setOnMouseClicked(event -> { // add an event listener for when the user clicks the rating
            try {
                int id_user = session.getId(); // replace this with the actual user ID
                int ratingValue = (int) rating.getRating(); // get the rating value
                Stars stars = new Stars(id_user, idOffre, ratingValue); // create a new Stars object to save the rating
                StarsService ss = new StarsService();
                ss.ajouter(stars); // call the method to save the rating to the database

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rating Submitted");
                alert.setHeaderText(null);
                alert.setContentText("Thank you for rating this offer with " + ratingValue + " stars!");
                alert.showAndWait();

            } catch (SQLException ex) {
                Logger.getLogger(ListOffreUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void resOff(ActionEvent event) throws IOException {
        try {
            int id_user = session.getId();
            LocalDateTime now = LocalDateTime.now();
            java.sql.Date date = java.sql.Date.valueOf(now.toLocalDate());

            // Create an Alert object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Reservation");
            alert.setHeaderText("Are you sure you want to make this reservation?");
            alert.setContentText("Click OK to confirm or Cancel to go back.");

            // Add "OK" and "Cancel" buttons to the alert
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Show the alert and wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();

            // If the user clicked "OK", add the reservation to the database
            if (result.get() == okButton) {
                reservation r = new reservation(id_user, idOffre, date, "En Cours");
                ReservationService rs = new ReservationService();
                rs.ajouter(r);
                
           Scene scene = ((Node)event.getSource()).getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("espaceUser.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListOffreUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
