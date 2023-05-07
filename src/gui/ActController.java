/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Actualite;


import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class ActController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Label titreLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label descriptionLabel;

    private Button voirCommentairesButton;

    private Actualite actualite;

    private FrontController frontController;
    @FXML
    private Button vcb;
    
    private int id_act ;

    public int getId_act() {
        return id_act;
    }

    public void setId_act(int id_act) {
        this.id_act = id_act;
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setActualite(Actualite actualite) {
        // this.actualite = actualite;
        File file = new File("C:\\Users\\wadah\\OneDrive\\Desktop\\pidev\\IntegrationJavaFX\\Sportifly\\src\\uploads\\" + actualite.getImage());
        Image image = new Image(file.toURI().toString());
        System.out.println(file);
       // System.out.println(file);
        imageView.setImage(image);
        titreLabel.setText(actualite.getTitre());
        dateLabel.setText(actualite.getDate().toString());
        descriptionLabel.setText(actualite.getDescription());
        setId_act(actualite.getId());



        /* // Tentative de récupération de l'image à partir du chemin stocké dans la base de données
    try {
        BufferedImage image = ImageIO.read(new File(actualite.getImage()));
        WritableImage imagee = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(imagee);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.scaleXProperty();
        imageView.scaleYProperty();
        imageView.setSmooth(true);
        imageView.setCache(true);
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
      
        File defaultImageFile = new File("path/to/default/image.jpg");
        Image image = new Image(defaultImageFile.toURI().toString());
        imageView.setImage(image);
    }*/
    }

    public void setFrontController(FrontController frontController) {
        this.frontController = frontController;
    }

    @FXML
    private void vcb(ActionEvent event) {
         System.out.println(descriptionLabel.getText());

        // Load the ActiviteController and pass the selected Activite object to it
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/actualite.fxml"));
        Parent root;
        try {
            root = loader.load();
            ActualiteController ActCon = loader.getController();
            ActCon.initialize(getId_act());

            vcb.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ActController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
