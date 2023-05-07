/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Categorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.CategorieService;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class StaticOffreByCatController implements Initializable {

    @FXML
    private Button MesOffA;
    @FXML
    private Button MesressA;
    @FXML
    private BarChart<String, Integer> stats;
    private CategorieService categoryService = new CategorieService();
    @FXML
    private Button utilisateur;
    @FXML
    private Button plan;
    @FXML
    private Button cat;
    @FXML
    private Button act;
    @FXML
    private Button comm;
    @FXML
    private Button evente;
    @FXML
    private Button part;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Categorie> categories = new ArrayList<>();

        try {
            categories = (ArrayList<Categorie>) categoryService.recuperer();
        } catch (SQLException ex) {
            Logger.getLogger(StaticOffreByCatController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(categories);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        System.out.println("Entering for loop");
        for (Categorie category : categories) {
            try {
                int reservationCount = categoryService.getReservationCountByCategory(category.getId());
                System.out.println("Reservation count for category " + category.getNom() + ": " + reservationCount);
                series.getData().add(new XYChart.Data<>(category.getNom(), reservationCount));
            } catch (SQLException ex) {
                // Handle the exception
            }
        }

        stats.setTitle("Reservations by Offer Category");
        stats.getXAxis().setLabel("Category");
        stats.getYAxis().setLabel("Number of Reservations");

        System.out.println(series.getData());

        // Add the series to the bar chart
        stats.getData().add(series);
    }

    private void MesOffresA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            MesOffA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Mesress(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListResBack.fxml"));

            Parent root = loader.load();
            MesressA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
     private void handleClicks(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getId().equals("MesOffA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListOffreBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesOffA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("utilisateur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) utilisateur.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("plan")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminActivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) plan.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("cat")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admi_categ.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("act")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Act.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) act.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("comm")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Com.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) comm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("MesressA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesressA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (clickedButton.getId().equals("evente")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
             else if (clickedButton.getId().equals("part")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


}
