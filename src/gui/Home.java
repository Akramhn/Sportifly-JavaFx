/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class Home extends Application{

   @Override
    public void start(Stage primaryStage) {
        try {
      //  Parent root =FXMLLoader.load(getClass().getResource("MenuDynamicDevelopers.fxml"));
       // Parent root =FXMLLoader.load(getClass().getResource("Back.fxml"));
               Parent root =FXMLLoader.load(getClass().getResource("signin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("sportifly");
            primaryStage.setTitle("sportifly application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
