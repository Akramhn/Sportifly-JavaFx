/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import entities.roles;
import entities.User;
import java.awt.Color;
import services.UserService;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import static pi.GUI.signupFXMLController.doHashing;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.util.Duration;
//import static net.sf.jasperreports.engine.util.DeepPrintElementCounter.count;
//import static net.sf.jasperreports.engine.util.DeepPrintElementCounter.count;
import org.mindrot.jbcrypt.BCrypt;
import util.MyDB;
import util.SessionManager;



/**
 * FXML Controller class
 *
 * @author user
 */
public class signInFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView instagramIcon;
    @FXML
    private ImageView facebookIcon;
    @FXML
    private Button loginBtn;
    @FXML
    private Button forgotBtn;
    @FXML
    private Button signupBtn;
    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private Label errorMsg;
    @FXML
    private Label errorLabel;

    static User user_global = new User();

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private ImageView sportifly;
    @FXML
    private Button signupBtn1;
    private int numAttempts = 0;
    private boolean disableLogin = false;
    private Timer timer;
    
    private SessionManager session ;

    @FXML
    public void login() throws IOException {
        String email = txtemail.getText().trim();
        String password = txtpassword.getText().trim();

        if (email.equals("amirakhalfy12@gmail.com") && password.equals("adminadmin")) {
            // Admin login
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sportifly :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue Admin");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } else {
            String query = "SELECT * FROM user WHERE email=?";
            cnx = MyDB.getInstance().getCnx();
            try {
                PreparedStatement smt = cnx.prepareStatement(query);
                smt.setString(1, email);
                ResultSet rs = smt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String hashedPassword = rs.getString("password");
                    boolean passwordMatch = BCrypt.checkpw(password, hashedPassword.replace("$2y$", "$2a$"));

                    String etat = rs.getString("etat");

                    if (passwordMatch) {
                        if (etat.equals("bloqué")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Sportifly :: Bloqué");
                            alert.setHeaderText(null);
                            alert.setContentText("Désolé, votre compte a été bloqué. Veuillez contacter l'administrateur.");
                            animateAlert(alert, 1); // Pass the additional parameter `1`
                        } else {
                            // User login successful
                            User user = new User(rs.getString("email"), rs.getString("lastname"), hashedPassword, rs.getString("diplome"), rs.getString("experience"), rs.getString("image"));
                            SessionManager.getInstace(userId, rs.getString("email"), rs.getString("lastname"), rs.getString("roles"));

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Sportifly :: Success Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous êtes connecté");
                            alert.showAndWait();
                            if (SessionManager.getRoles().contains("Role_USER")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomePagewadha7.fxml"));

                                    Parent root = loader.load();
                                    loginBtn.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else if (SessionManager.getRoles().contains("Role_Coach")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomePagewadha7.fxml"));

                                    Parent root = loader.load();
                                    loginBtn.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else if (SessionManager.getRoles().contains("ROLE_ADMIN")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

                                    Parent root = loader.load();
                                    loginBtn.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        }
                    } else {
                        // Incorrect password
                        numAttempts++;
                        if (numAttempts >= 3) {
                            disableLogin();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Sportifly :: Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Mot de passe incorrect !!");
                            alert.showAndWait();
                        }
                    }
                } else {
                    // Incorrect email
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sportifly :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Adresse email incorrecte !!");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void disableLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sportifly :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez entré un mot de passe incorrect 3 fois. Réessayez dans 5 secondes.");
        alert.showAndWait();

        loginBtn.setDisable(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            loginBtn.setDisable(false);
        }));
        timeline.play();
    }

    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("captcha.fxml"));
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

    private boolean validateInput() {
        if (errorLabel == null || txtemail.getText().isEmpty() || txtpassword.getText().isEmpty()) {
            // if errorLabel is null, log an error or throw an exception to alert the developer
            errorLabel.setText("email and password are required.");
            return false;
        }
        return true;
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));

        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            // Handle exception
        }

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openInstagram(MouseEvent event) {
        String instagramUrl = "https://www.instagram.com/artec9197/";
        try {
            Desktop.getDesktop().browse(new URI(instagramUrl));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openFacebook(MouseEvent event) {
        String facebookUrl = "https://www.facebook.com/profile.php?id=100091951306863&is_tour_dismissed=true";
        try {
            Desktop.getDesktop().browse(new URI(facebookUrl));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(txtemail.getText());

        if (match.find() && match.group().equals(txtemail.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
                        System.out.println(session.getId()+"ba3d login");


    }

    private void animateAlert(Alert alert, int count) {
        if (count > 0) {
            String text = "Désolé, votre compte a été bloqué. Veuillez contacter l'administrateur.";
            ImageView imageView = new ImageView(new Image("gui/lock_1.png"));
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
            Label label = new Label(text);
            label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            label.setTextFill(Paint.valueOf("RED"));

            HBox hBox = new HBox(10, imageView, label);

            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double textWidth = hBox.getLayoutBounds().getWidth();
            double duration = (textWidth + screenWidth) / 150;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), hBox);
            translateTransition.setByX(-screenWidth - textWidth);
            translateTransition.setInterpolator(Interpolator.LINEAR);
            translateTransition.setOnFinished(event -> {
                animateAlert(alert, count - 1);
            });

            VBox vbox = new VBox(hBox);
            vbox.setPadding(new Insets(20));

            vbox.setStyle("-fx-background-color: #F7F7F7; -fx-background-radius: 10; -fx-border-color: #C62828; -fx-border-radius: 10; -fx-border-width: 2;");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setContent(vbox);
            dialogPane.getStyleClass().add("my-dialog-pane");
            alert.setOnShown(e -> translateTransition.play());
            alert.show();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                animateAlert(alert, count - 1);
            }));
            timeline.setCycleCount(count);
            timeline.play();

            alert.setOnHidden(e -> {
                timeline.stop();
                timeline.getKeyFrames().clear();
            });
        }
    }

    @FXML
    void google(MouseEvent event) throws IOException, URISyntaxException {
        // Desktop.getDesktop().browse(new URI("http://www.google.com"));
        // Parent root = FXMLLoader.load(getClass().getResource("GoogleAuth.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // stage.setScene(scene);
        // stage.show();

        String myVariable = "undefined"; // replace with your variable value

        try {
            URL url = new URL("http://localhost:5000/my-variable"); // replace with your URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // create JSON payload
            String payload = "{\"myVariable\": \"" + myVariable + "\"}";

            // send payload
            OutputStream os = connection.getOutputStream();
            os.write(payload.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // read response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Desktop.getDesktop().browse(new URI("http://localhost/zeroWateSignIn/"));
        Parent root = FXMLLoader.load(getClass().getResource("GoogleAuth.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
