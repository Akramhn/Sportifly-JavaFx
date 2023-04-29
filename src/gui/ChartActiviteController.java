package gui;

import entities.Categorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.CategorieService;

public class ChartActiviteController implements Initializable {

    @FXML
    private Pane chart;
    @FXML
    private Button bye;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategorieService categorieService = new CategorieService();
        ObservableList<Categorie> categories = null;
        try {
            categories = categorieService.recuperer2();
          
        List<PieChart.Data >a=new ArrayList<>();
            for (Categorie data : categories) {
                 PieChart.Data x=new PieChart.Data(data.getNom(), data.getId());
                
                a.add(x);
                  
            }
                  ObservableList<PieChart.Data> genderData = FXCollections.observableArrayList (a);
                     PieChart genderChart = new PieChart(genderData);
        chart.getChildren().add(genderChart);

        } catch (SQLException ex) {
            Logger.getLogger(ChartActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
                              }

   
                 
        @FXML
    private void ExitChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Admin.fxml"));
        Scene rcScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rcScene);
        window.show();
    }

     
    }

    


