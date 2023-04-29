 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import entities.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.mindrot.jbcrypt.BCrypt;
import services.UserService;
import util.MyDB;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author user
 */
public class UserProfileeController implements Initializable {

    @FXML
    private ImageView imagepro;
    @FXML
    private Label nameLabel;
    @FXML
    private Label rolesLabel;
    @FXML
    private Button imagenn;
    @FXML
    private PasswordField o_password;
    @FXML
    private TextField adduser_email;
    @FXML
    private TextField adduser_nom;
    @FXML
    private Button addEmployee_updateBtn1;
    @FXML
    private PasswordField n_password;
    @FXML
    private PasswordField n_password_confirm;
     private Connection cnx;
    private File file;
    private String lien = "";
     @FXML
    private Button logout;

    private SessionManager session;
User ug = signInFXMLController.user_global;
  UserService serviceUser = new UserService();


  
    /**
     * Initializes the controller class.
     */
  @Override
public void initialize(URL url, ResourceBundle rb) {
    nameLabel.setText(session.getLastname());
    rolesLabel.setText(session.getRoles());
    Image image = new Image("file:src/uploads/6498f0a2-5f02-4d60-af61-285655570f00.png");
    System.out.println(image.isError());

    // Affichage de l'image dans l'interface utilisateur
    // Ajouter l'objet Image directement à l'ImageView
    imagepro.setImage(image);
     session = SessionManager.getInstance();
}
      @FXML
    private void updateuser(ActionEvent event) throws IOException, SQLException  {
     

      try {
    cnx = MyDB.getInstance().getCnx();
    int value1 = SessionManager.getId();
    String value2 =  adduser_email.getText();
    String value3 =  adduser_nom.getText();

    String query3 = "update user set email='" + value2 + "'  ,lastname='" + value3 + "'   WHERE id = '" + value1 + "' ";
    PreparedStatement smt = cnx.prepareStatement(query3);
    smt.execute();

    // Afficher un message de confirmation
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Modification réussie");
    alert.setHeaderText(null);
    alert.setContentText("La modification a été effectuée avec succès.");
    alert.showAndWait();
} catch (Exception e) {
    e.printStackTrace();
}
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
            imagepro.setImage(imagee);
            imagepro.setFitWidth(200);
            imagepro.setFitHeight(200);
            imagepro.scaleXProperty();
            imagepro.scaleYProperty();
            imagepro.setSmooth(true);
            imagepro.setCache(true);

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
public void updatePassword(ActionEvent actionEvent) {
    UserService userService = new UserService();
    User currentUser = userService.getCurrentUser();
     System.out.println("user_global value: " + signInFXMLController.user_global);

    String passwordDB = userService.getPasswordUser(ug.getId());

    if (passwordDB == null || passwordDB.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Unable to retrieve password from database");
        alert.showAndWait();
        return;
    }

    String oldPassword = o_password.getText();
    String newPassword = n_password.getText();

    if (!BCrypt.checkpw(oldPassword, passwordDB)){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Current password incorrect");
        alert.showAndWait();
    } else {
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        userService.updateUserPassword(currentUser, hashedNewPassword);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Password updated");
        alert.showAndWait();
    }
}
  @FXML
    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    double x = event.getSceneX();
                    double y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    double x = 0;
                    stage.setX(event.getScreenX() - x);
                    double y = 0;
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
    
    
     @FXML
    public void SwitchToCoachList(ActionEvent event) throws IOException{
     Parent tableViewParent = FXMLLoader.load(getClass().getResource("ConsulterCoach.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(tableViewScene);
}
    
    
    
    
    
    
    
}
    


    
    
    
    
    
    
    
    
    
    
    /*  public void updatePassword(ActionEvent actionEvent) {
        String passwordDB = UserService.getPasswordUser(session.getUserId());
        if (!passwordDB.equals(o_password.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Mot de passe actuel incorrect");
            alert.showAndWait();
        } else {
            UserService.updateUserPassword(session.getUserId(), n_password.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Success");
            alert.setHeaderText("Mot de passe modifié");
            alert.showAndWait();
        }
    }
*/
    


