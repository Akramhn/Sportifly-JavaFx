/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author Nour moutii
 */
public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      
              // Créer les boutons
        Button buttonToFront = new Button("Aller vers front");
        Button buttonToBack = new Button("Aller vers back");

        // Créer un conteneur pour les boutons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttonToFront, buttonToBack);

        // Créer une nouvelle scène avec le conteneur de boutons
        Scene buttonScene = new Scene(buttonBox, 300, 100);

        // Définir l'action du bouton "Aller vers l'avant"
        buttonToFront.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("front.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("front actualite");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        // Définir l'action du bouton "Aller vers l'arrière"
        buttonToBack.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Afficher_Act.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("back actualite");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        // Afficher la scène des boutons
        primaryStage.setTitle("Application");
        primaryStage.setScene(buttonScene);
        primaryStage.show();
    }
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
