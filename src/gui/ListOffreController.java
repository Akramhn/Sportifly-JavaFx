/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.OffreService;
import services.StarsService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ListOffreController implements Initializable {

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
    private ImageView affOffre;
    @FXML
    private Button Editoffre;
    @FXML
    private Button deleteoffre;

    private OffreService offreService = new OffreService();

    private int id;
    private SessionManager session ;

    @FXML
    private VBox Vbox;
    @FXML
    private Label Prix;
    @FXML
    private Rating rating;

    public int getId() {
        return id;
    }

    /**
     * Initializes the controller class.
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setData(Offre offre) throws SQLException {

        File file = new File("C:\\Users\\wadah\\OneDrive\\Desktop\\integre\\usersgestion\\public\\uploads\\offre\\" + offre.getAffiche());
        Image image = new Image(file.toURI().toString());
        System.out.println(offre.getAffiche());
        affOffre.setImage(image);
        Prix.setText("prix :" + String.valueOf(offre.getPrix()));
        Desc.setText("Descritpion :" + offre.getDescription());
        setId(offre.getId());

        username.setText(session.getLastname());
        StarsService ss = new StarsService();
        double ratingValue = ss.calculateMoyenne(id);
        rating.setRating(ratingValue);

    }

    @FXML
    private void editOff(ActionEvent event) {
        System.out.println(Desc.getText());

        // Load the ActiviteController and pass the selected Activite object to it
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/EditOffre.fxml"));
        Parent root;
        try {
            root = loader.load();
            EditOffreController EditOffreController = loader.getController();
            EditOffreController.initialize(getId());

            Editoffre.getScene().setRoot(root);
            System.out.println(getId());
        } catch (IOException ex) {
            Logger.getLogger(ListOffreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteOff(ActionEvent event) throws SQLException {

        offreService.supprimer(getId());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CoachOffres.fxml"));
        Parent root;
        try {
            root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) deleteoffre.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ListOffreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
