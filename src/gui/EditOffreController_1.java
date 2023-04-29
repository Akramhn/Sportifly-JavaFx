/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import services.CategorieService;
import services.OffreService;
import util.MyDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class EditOffreController_1 {

    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfNbp;
    @FXML
    private Button imageUpload;
    @FXML
    private ComboBox<String> tfCat;

    @FXML
    private Button btnRetour;
    @FXML
    private Button btn_Edit;

    private int offerId;
    @FXML
    private VBox postsContainer;
    private String lien;
    private File file;
    @FXML
    private Button mesoff;
    private SessionManager session;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public TextField getTfDesc() {
        return tfDesc;
    }

    public void setTfDesc(String tfDesc) {
        this.tfDesc.setText(tfDesc);
    }

    public TextField getTfPrix() {
        return tfPrix;
    }

    public void setTfPrix(String tfPrix) {
        this.tfPrix.setText(tfPrix);
    }

    public TextField getTfNbp() {
        return tfNbp;
    }

    public void setTfNbp(String tfNbp) {
        this.tfNbp.setText(tfNbp);
    }

    public Button getImageUpload() {
        return imageUpload;
    }

    public void setImageUpload(Button imageUpload) {
        this.imageUpload = imageUpload;
    }

    public ComboBox<String> getTfCat() {
        return tfCat;
    }

    public void setTfCat(ComboBox<String> tfCat) {
        this.tfCat = tfCat;
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(int offerId) {

        this.offerId = offerId;
        // TODO
        OffreService oc = new OffreService();
        try {
            Connection cnx = MyDB.getInstance().getCnx();
            String query = "SELECT nom FROM categorie_activite";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("nom");
                tfCat.getItems().add(categoryName);
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            Offre offre = oc.recupererById(offerId);
            tfDesc.setText(offre.getDescription());
            tfPrix.setText(String.valueOf(offre.getPrix()));
            tfNbp.setText(String.valueOf(offre.getNb_place()));
            //Retrieve the category name
            String categoryName = oc.recupererNomCategorie(offre.getId_categroy());
            tfCat.getSelectionModel().select(categoryName);

        } catch (SQLException ex) {
            Logger.getLogger(EditOffreController_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
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

    @FXML
    private void RetourHandleButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            btn_Edit.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void EditOff(ActionEvent event) {
        try {
            // Validate form input
            if (tfDesc.getText().isEmpty() || tfPrix.getText().isEmpty() || tfNbp.getText().isEmpty() || tfCat.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return;
            }

            if (tfDesc.getText().length() < 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("La description doit comporter au moins 10 caractères !");
                alert.showAndWait();
                return;
            }

            try {
                Float.parseFloat(tfPrix.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le prix doit être un nombre !");
                alert.showAndWait();
                return;
            }

            try {
                Integer.parseInt(tfNbp.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nombre de places doit être un nombre entier !");
                alert.showAndWait();
                return;
            }

            CategorieService cs = new CategorieService();

            int id_user = session.getId();
            int id_category = cs.recupererBynom(tfCat.getValue()).getId();

            LocalDateTime now = LocalDateTime.now();
            java.sql.Date date = java.sql.Date.valueOf(now.toLocalDate());
            String description = tfDesc.getText();
            float prix = Float.parseFloat(tfPrix.getText());
            int nb_place = Integer.parseInt(tfNbp.getText());
            Offre o = new Offre(offerId, id_user, id_category, nb_place, date, lien + ".png", description, prix);
            OffreService oc = new OffreService();

            oc.modifier(o);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Offre Modifié");
            alert.setHeaderText(null);
            alert.setContentText("L'offre a été Modifié avec succès !");
            alert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

                Parent root = loader.load();
                btnRetour.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void MesOffr(ActionEvent event) {
    }

}
