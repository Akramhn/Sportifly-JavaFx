/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 *
 * @author lenovo
 */
public class CaptureEcran {

    public static void main(String[] args) throws Exception {
    }
    
public static void capturer(Node node) {
        // Récupération de la scène contenant le nœud à capturer
        Scene scene = node.getScene();

        // Création d'un SnapshotParameters pour capturer seulement le contenu de l'AnchorPane
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Capture d'écran du contenu de l'AnchorPane uniquement
        WritableImage image = node.snapshot(parameters, null);

        // Enregistrement de la capture
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la capture d'écran");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers PNG", "*.png"),
                new FileChooser.ExtensionFilter("Tous les fichiers", ".")
        );
        File fichier = fileChooser.showSaveDialog(new Stage());
        if (fichier != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fichier);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


   /*    public static void capturer(Scene scene) throws Exception {
     

       // Initialisation de JavaFX
        new JFXPanel();
        Platform.runLater(() -> {
            // Création de la scène
            Pane root = new Pane();
            //Scene scene = new Scene(root, 800, 600);
            // ...
            // Ajout des éléments à la scène
            // ...
            // Capture d'écran
            try {
                Robot robot = new Robot();
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                BufferedImage image = robot.createScreenCapture(
                        new java.awt.Rectangle(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight()));
                WritableImage writableImage = new WritableImage(image.getWidth(), image.getHeight());
                SwingFXUtils.toFXImage(image, writableImage);

                // Enregistrement de la capture
                String nomFichier = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss'.png'").format(new Date());
                String DOSSIER_CAPTURES = "C:\\Users\\lenovo\\Desktop\\PIDEV_Desc\\src\\CapturesEcran";
                File dossier = new File(DOSSIER_CAPTURES);
        
                if (!dossier.exists()) {
                    dossier.mkdirs();
                }
                File fichier = new File(dossier, nomFichier);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", fichier);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
 
    }*/
}