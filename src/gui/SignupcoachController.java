/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import static gui.signupFXMLController.nb_valider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import mailing.Mailing;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class SignupcoachController implements Initializable {

    @FXML
    private TextField emailcoachtxt;
    @FXML
    private TextField passwordcoachtxt;
    @FXML
    private TextField namecoachtxt;
    @FXML
    private TextField diplomecoachtxt;
    @FXML
    private TextField experiencetxt;
    @FXML
    private TextField imagecoachtxt;
     @FXML
    private ImageView eventAddImgcoach;
       private File file;
    private String lien = "";
    
         Random r = new Random();
    static int nb_valider;
  UserService su = new UserService();
  @FXML
private Label errorEmaill;
@FXML
private Label errorNamee;
@FXML
private Label errorPasswordd;
@FXML
private Label errorDiplome;
@FXML
private Label errorExperience;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      @FXML

    private void UploadImageActionPerformed(ActionEvent event) {

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
            eventAddImgcoach.setImage(imagee);
            eventAddImgcoach.setFitWidth(200);
           eventAddImgcoach.setFitHeight(200);
           eventAddImgcoach.scaleXProperty();
          eventAddImgcoach.scaleYProperty();
           eventAddImgcoach.setSmooth(true);
           eventAddImgcoach.setCache(true);

            try {
                // save image to PNG file
                this.lien = UUID.randomUUID().toString();
                File f = new File("src\\uploads\\" + this.lien + ".png");
                System.out.println(f.toURI().toString());
                ImageIO.write(image, "PNG", f);

            } catch (IOException ex) {
                Logger.getLogger(signupFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
    }
    

    
@FXML
    private void ajoutercoach(ActionEvent event) throws IOException,Exception{
   
    User u = new User();
    u.setEmail(emailcoachtxt.getText());
    u.setLastname(namecoachtxt.getText());
    u.setPassword(passwordcoachtxt.getText());
    u.setDiplome(diplomecoachtxt.getText());
    u.setExperience(experiencetxt.getText());
    u.setImage(lien);
    UserService s = new UserService();
    String email = emailcoachtxt.getText();
    String name = namecoachtxt.getText();
    String password = passwordcoachtxt.getText();
    String diplome = diplomecoachtxt.getText();
    String experience = experiencetxt.getText();
    String image = eventAddImgcoach.toString();

    // Vérification de l'email
    if (s.isEmailRegistered(email)) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Cet email est déjà utilisé.", ButtonType.CLOSE);
        alert.show();
        return; // Sortir de la méthode car l'email n'est pas unique
    }

    if (email.isEmpty() || !email.matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")) {
        // Afficher un message d'erreur pour le format d'email invalide
        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer une adresse email valide.", ButtonType.CLOSE);
        alert.show();
        return;
    }

    // Vérification du nom
    if (name.isEmpty() || !name.matches("[a-zA-Z]{4,}")) {
        // Afficher un message d'erreur pour le format de nom invalide
        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom valide (au moins 4 lettres).", ButtonType.CLOSE);
        alert.show();
        return;
    }

    // Vérification du mot de passe
    if (password.isEmpty() || password.length() < 6 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
        // Afficher un message d'erreur pour le format de mot de passe invalide
        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer un mot de passe valide (au moins 6 caractères, avec des chiffres et des lettres).", ButtonType.CLOSE);
        alert.show();
        return;
    }

    // Vérifier que le diplôme n'est pas vide
    if (diplome.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer votre diplôme.", ButtonType.CLOSE);
        alert.show();
        return;
    }

    // Vérifier que l'expérience n'est pas vide
    if (experience.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer votre expérience.", ButtonType.CLOSE);
        alert.show();
        return;
    }

    // Générer un nombre aléatoire pour le code de validation
    int nb_valider = new Random().nextInt(10000);
    Mailing.mailingValider(email, nb_valider);

    // Demander le code de validation à l'utilisateur
    String txt_CodeConfirmation = new JOptionPane().showInputDialog(null, "Merci de saisir le code de vérification !", "Vérification Adresse Mail", JOptionPane.QUESTION_MESSAGE);

    // Vérifier si le code de validation est correct
    if (Integer.parseInt(txt_CodeConfirmation) == nb_valider) {
        try {
            s.registercoach(u);
                      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bienvenue Mr(s) "+namecoachtxt.getText() , ButtonType.CLOSE);
                      
            alert.show();
            
         FXMLLoader LOADER = new FXMLLoader(getClass().getResource("signin.fxml"));
                try {
                    Parent root = LOADER.load();
                    Scene sc = new Scene(root);
                      signInFXMLController cntr = LOADER.getController();
                    Stage window =(Stage)((Node) event.getSource()).getScene().getWindow() ;
              
                    window.setScene(sc);
                    window.show();
                } catch (IOException ex) {
                  
    }
                    // redirection vers la page d'accueil
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
//                lblResultat.setText("Inscription valide !!");
            }else {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Code incorrect", ButtonType.CLOSE);
                alert.show();
            }
    }
    
    
    
    
    
    
}
