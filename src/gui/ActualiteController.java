/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Actualite;
import entities.Commentaire_Act;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ActualiteService;
import services.Commentaire_ActService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class ActualiteController {

    @FXML
    private Text titre;
    @FXML
    private Text date;
    @FXML
    private Text categorie;
    @FXML
    private ImageView image;
    @FXML
    private Text description;
    @FXML
    private TextField tfcomment;
    @FXML
    private Button btncomment;
    @FXML
    private VBox comments;

    private int id_actualité;

    private List<Commentaire_Act> coms = new ArrayList<>();
    private Commentaire_ActService cs = new Commentaire_ActService();
    @FXML
    private Button btnRetour;
    @FXML
    private Button qrcode;

    private SessionManager session;

    public int getId_actualité() {
        return id_actualité;
    }

    public void setId_actualité(int id_actualité) {
        this.id_actualité = id_actualité;
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(int id_actualité) {
        this.id_actualité = id_actualité;

        ActualiteService as = new ActualiteService();

        try {
            Actualite act = as.recupererById2(id_actualité);
            titre.setText(act.getTitre());
            description.setText(act.getDescription());
            date.setText(String.valueOf(act.getDate()));
            categorie.setText(act.getCategorie());
            File file = new File("C:\\Users\\wadah\\OneDrive\\Desktop\\pidev\\IntegrationJavaFX\\Sportifly\\src\\uploads\\" + act.getImage());
            Image image2 = new Image(file.toURI().toString());
            System.out.println(file);
            image.setImage(image2);

            coms = cs.getCommentairesByIdActualite(id_actualité);
           

            for (Commentaire_Act comm : coms) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CommentsCard.fxml"));

                try {
                    VBox vBox = fxmlLoader.load();
                    CommentsCardController listoffreContoller = fxmlLoader.getController();

                    listoffreContoller.setComments(comm);
                    comments.getChildren().add(vBox);
                } catch (IOException ex) {
                    Logger.getLogger(ActualiteController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ActualiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Actualite actualite;

    public void setActualite(Actualite actualite) {
        this.actualite = actualite;
        titre.setText(actualite.getTitre());
        date.setText(actualite.getDate().toString());
        categorie.setText(actualite.getCategorie());
        description.setText(actualite.getDescription());
        setId_actualité(actualite.getId());

        //commentairesListView.setItems(FXCollections.observableList(actualite.getCommentaires()));
    }

    public void refreshPage() {
        // Get the current scene and stage
        Scene currentScene = titre.getScene();
        Stage stage = (Stage) currentScene.getWindow();

        // Load the same FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Actualite.fxml"));

        try {
            // Get the root node and set the controller
            Parent root = loader.load();
            ActualiteController controller = loader.getController();

            // Initialize the controller with the same id_actualité parameter
            controller.initialize(id_actualité);

            // Show the new scene on the same stage
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(ActualiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajourerComF(ActionEvent event) throws SQLException, IOException {
        String contenu = tfcomment.getText().trim(); // récupérer le contenu et supprimer les espaces au début et à la fin

        if (contenu.isEmpty()) { // Vérifier si le champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Le champ contenu est obligatoire.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si le champ est vide
        }

        Commentaire_Act comment = new Commentaire_Act();
        comment.setContenu(contenu);
        comment.setId_actualite_id(id_actualité);
        comment.setId_user(session.getId());
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Date dt = new Date(currentDate.getTime());
        Timestamp heure = new Timestamp(System.currentTimeMillis());
        comment.setDate(LocalDateTime.now());

        if (session.getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connexion obligatoire");
            alert.setHeaderText(null);
            alert.setContentText("You should Login first to submit comment.");
            alert.showAndWait();
            return;

        } else {
            cs.ajouter(comment);
            refreshPage();
        }

    }

    @FXML
    private void RetourHandleButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomePagewadha7.fxml"));
        Ajouter_ActController aec = loader.getController();
        Parent root = loader.load();
        btnRetour.getScene().setRoot(root);
    }

    @FXML
    public void qrcode() throws SQLException {

        ActualiteService as = new ActualiteService();
        Actualite act = as.recupererById2(id_actualité);

        // Convert the data to a string
        String dataString = act.toString().trim();

        // Generate the QR code image
        Image qrCodeImage = generateQRCode(dataString);

        // Display the QR code image
        ImageView imageView = new ImageView(qrCodeImage);
        VBox vbox = new VBox(imageView);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private Image generateQRCode(String data) {
        try {
            // Create a QR code writer
            QRCodeWriter writer = new QRCodeWriter();

            // Create a BitMatrix from the data and set the size
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            // Create a BufferedImage from the BitMatrix
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Create a JavaFX Image from the BufferedImage
            return SwingFXUtils.toFXImage(image, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
