/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import entities.User;
import entities.reservation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import services.CategorieService;
import services.OffreService;
import services.ReservationService;
import services.UserService;
import util.MyDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AddReservationController implements Initializable {

    private SessionManager session;

    private TextField tfDesc;
    private TextField tfPrix;
    private TextField tfNbp;
    private Button imageUpload;
    private ComboBox<String> tfCat;
    private Button btn_ajoutoff;

    private String lien;
    private File file;
    @FXML
    private Button MesOffA;
    private Button MesreservationA;
    @FXML
    private VBox postsContainer;
    @FXML
    private Button btnRetourA;
    @FXML
    private Button MesressA;
    @FXML
    private ComboBox<String> offreList;
    @FXML
    private ComboBox<String> UserList;
    @FXML
    private Button btn_ajoutres;
    @FXML
    private Button utilisateur;
    @FXML
    private Button plan;
    @FXML
    private Button cat;
    @FXML
    private Button act;
    @FXML
    private Button comm;
    @FXML
    private Button evente;
    @FXML
    private Button part;

    public TextField getTfDesc() {
        return tfDesc;
    }

    public void setTfDesc(String tfDesc) {
        this.tfDesc.setText(tfDesc);
    }

    public TextField getTfPrix() {
        return tfPrix;
    }

    public void setTfPrix(Float tfPrix) {
        this.tfPrix.setText(String.valueOf(tfPrix));
    }

    public TextField getTfNbp() {
        return tfNbp;
    }

    public void setTfNbp(TextField tfNbp) {
        this.tfNbp = tfNbp;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection cnx = MyDB.getInstance().getCnx();

            // query to fetch descriptions from offre table
            String queryOffre = "SELECT description FROM offre";
            PreparedStatement pstOffre = cnx.prepareStatement(queryOffre);
            ResultSet rsOffre = pstOffre.executeQuery();

            // populate the combobox with descriptions
            while (rsOffre.next()) {
                String description = rsOffre.getString("description");
                offreList.getItems().add(description);
            }

            // close the statement and result set
            pstOffre.close();
            rsOffre.close();

            // query to fetch last names from User table
            String queryUser = "SELECT lastName FROM User";
            PreparedStatement pstUser = cnx.prepareStatement(queryUser);
            ResultSet rsUser = pstUser.executeQuery();

            // populate the combobox with last names
            while (rsUser.next()) {
                String lastName = rsUser.getString("lastName");
                UserList.getItems().add(lastName);
            }

            // close the statement and result set
            pstUser.close();
            rsUser.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void PhotoImportation(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage image = ImageIO.read(file);
            WritableImage imagee = SwingFXUtils.toFXImage(image, null);

            try {
                // save image to PNG file
                this.lien = UUID.randomUUID().toString();
                File f = new File("src\\img\\" + this.lien + ".png");
                System.out.println(f.toURI().toString());
                ImageIO.write(image, "PNG", f);
                imageUpload.setText(file.getName());

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
//      
    }

    private void MesOffresA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            MesOffA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MesreseA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            MesreservationA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RetourHandleButtonA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListResBack.fxml"));

            Parent root = loader.load();
            btnRetourA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void Ajoutres(ActionEvent event) {
        try {
            UserService us = new UserService();
            OffreService oc = new OffreService();

            // Check if both ComboBoxes have a selection
            if (offreList.getSelectionModel().isEmpty() || UserList.getSelectionModel().isEmpty()) {
                // Create an Alert object to display the error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select an offer and a user.");
                alert.showAndWait();
                return;
            }

            int idOffre = oc.recupererBydesc(offreList.getValue()).getId();
            int id_user = us.recupererBynomUser(UserList.getValue()).getId();;
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
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListOffreUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
     private void handleClicks(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getId().equals("MesOffA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListOffreBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesOffA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("utilisateur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) utilisateur.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("plan")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminActivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) plan.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("cat")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admi_categ.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("act")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Act.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) act.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("comm")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Com.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) comm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("MesressA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesressA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("evente")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
             else if (clickedButton.getId().equals("part")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

}
