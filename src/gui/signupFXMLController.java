/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import services.UserService;
import util.MyDB;
import mailing.Mailing;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import mailing.Mailing;
import gui.signInFXMLController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author user
 */
public class signupFXMLController implements Initializable {
      private Stage stage;
    private Scene scene;
    private Parent root;
     private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private File file;
    private String lien = "";

    @FXML
    private TextField emailTxt;
    @FXML
    private Button createBtn;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField nameTxt1;
    @FXML
    private TextField imageTxt1;
     @FXML
    private PasswordField txtpassword;
       @FXML
    private PasswordField confirmpassword;
    @FXML
    private Button createBtn1;
      @FXML
    private ImageView eventAddImg;
       @FXML
    private TextField teltxt;
   
     Random r = new Random();
    static int nb_valider;
  UserService su = new UserService();
    @FXML
private Label emailErrorLabel;
@FXML
private Label nameErrorLabel;
@FXML
private Label passwordErrorLabel;

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
            eventAddImg.setImage(imagee);
            eventAddImg.setFitWidth(200);
            eventAddImg.setFitHeight(200);
            eventAddImg.scaleXProperty();
            eventAddImg.scaleYProperty();
            eventAddImg.setSmooth(true);
            eventAddImg.setCache(true);

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
    private void ajouter(ActionEvent event) throws IOException,Exception{
        User u=new User();
        u.setEmail(emailTxt.getText());
        u.setLastname(nameTxt1.getText());
        u.setPassword(txtpassword.getText());
        u.setImage(lien);
        UserService s =new UserService();
          String email = emailTxt.getText();
    String name = nameTxt1.getText();
    String password = txtpassword.getText();
        String image =eventAddImg.toString();
        boolean isValid = true;
        if (s.isEmailRegistered(emailTxt.getText())) {
    emailErrorLabel.setText("Cet email est déjà utilisé.");
    isValid = false;
}

if (email.isEmpty() || !email.matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")) {
    emailErrorLabel.setText("Please enter a valid email address.");
        emailErrorLabel.setStyle("-fx-text-fill: red;");
    isValid = false;
}

if (name.isEmpty() || name.length() < 4) {
    nameErrorLabel.setText("Please enter a valid name (at least 4 characters).");
      nameErrorLabel.setStyle("-fx-text-fill: red;");
    isValid = false;
}

if (password.isEmpty() || password.length() < 6 || !password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
    passwordErrorLabel.setText("Please enter a valid password (at least 6 characters and contains letters and digits).");
         passwordErrorLabel.setStyle("-fx-text-fill: red;");
    isValid = false;
}

if (isValid) {
    nb_valider = r.nextInt(10000);
    Mailing.mailingValider(emailTxt.getText(), nb_valider);

    JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
    String txt_CodeConfirmation = jop.showInputDialog(null, "Merci de saisir le code de verification !", "Verification Adresse Mail", JOptionPane.QUESTION_MESSAGE);

    if (Integer.parseInt(txt_CodeConfirmation) == nb_valider) {

        try {
            su.register(u);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bienvenue Mr(s) " + nameTxt1.getText(), ButtonType.CLOSE);

            alert.show();

            FXMLLoader LOADER = new FXMLLoader(getClass().getResource("signin.fxml"));
            try {
                Parent root = LOADER.load();
                Scene sc = new Scene(root);
                signInFXMLController cntr = LOADER.getController();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(sc);
                window.show();
            } catch (IOException ex) {

            }
            // redirection vers la page d'accueil
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Code incorrect", ButtonType.CLOSE);
        alert.show();
    }
}
}


      
       @FXML
    void returnToLgin(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
    private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
        return false;
    return pat.matcher(email).matches();
}
   
    
    
    
    
    
    
    
    }
    

