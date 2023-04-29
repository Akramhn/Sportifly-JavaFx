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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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

    @FXML
    private void MesOffresA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            MesOffA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Mesress(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListResBack.fxml"));

            Parent root = loader.load();
            MesressA.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
