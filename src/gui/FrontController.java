/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import java.util.stream.Collectors;
import entities.Actualite;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ActualiteService;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class FrontController implements Initializable {

    @FXML
    private GridPane actCOntainer;
    private List<Actualite> actualites = new ArrayList<>();
    @FXML
    private TextField tfrecherhce;
    private ActualiteService actualiteService = new ActualiteService();
    @FXML
    private Button tri;
    private int itemsPerPage = 4;
private int currentPageIndex = 0;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    try {
        actualites = actualiteService.recuperer();
    } catch (SQLException ex) {
        Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
    }

    int column = 0;
    int row = 1;
    final int maxColumns = 4;

    for (Actualite actualite : actualites) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("act.fxml"));
        VBox actBox = null;
        try {
            actBox = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ActController actC = fxmlLoader.getController();
        actC.setActualite(actualite);

        if (column == maxColumns) {
            column = 0;
            ++row;
        }

        actCOntainer.add(actBox, column++, row);
        GridPane.setMargin(actBox, new Insets(10));

        // Handle search bar filter
        tfrecherhce.textProperty().addListener((observable, oldValue, newValue) -> {
            // Clear the container before adding filtered items
            actCOntainer.getChildren().clear();

            // Filter actualites based on the search bar text
            List<Actualite> filteredActualites = actualites.stream()
                    .filter(a -> a.getTitre().contains(newValue))
                    .collect(Collectors.toList());

            // Add filtered items to the container
            int filteredColumn = 0;
            int filteredRow = 1;
            for (Actualite filteredActualite : filteredActualites) {
                FXMLLoader filteredLoader = new FXMLLoader(getClass().getResource("act.fxml"));
                VBox filteredBox = null;
                try {
                    filteredBox = filteredLoader.load();
                } catch (IOException ex) {
                    Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ActController filteredController = filteredLoader.getController();
                filteredController.setActualite(filteredActualite);

                if (filteredColumn == maxColumns) {
                    filteredColumn = 0;
                    ++filteredRow;
                }

                actCOntainer.add(filteredBox, filteredColumn++, filteredRow);
                GridPane.setMargin(filteredBox, new Insets(10));
            }
        });
        
        tri.setOnAction(event -> trierActualites());
    }
}


    public void afficherActualites(List<Actualite> actualites) {
        for (Actualite actualite : actualites) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Act.fxml"));
            Parent root;
            try {
                root = (Parent) loader.load();
                ActController actController = loader.getController();
                actController.setActualite(actualite);
                VBox actualiteBox = new VBox();
                actualiteBox.getChildren().add(root);
                // Ajouter actualiteBox à votre layout pour afficher l'actualité
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void showCommentaires(Actualite actualite) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Actualite.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            ActualiteController controller = loader.getController();
            controller.setActualite(actualite);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void trierActualites() {
    actualites.sort(Comparator.comparing(Actualite::getTitre));
    actCOntainer.getChildren().clear();
    int column = 0;
    int row = 1;
    final int maxColumns = 4;
    for (Actualite actualite : actualites) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("act.fxml"));
        VBox actBox = null;
        try {
            actBox = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ActController actC = fxmlLoader.getController();
        actC.setActualite(actualite);
        if (column == maxColumns) {
            column = 0;
            ++row;
        }
        actCOntainer.add(actBox, column++, row);
        GridPane.setMargin(actBox, new Insets(10));
    }
}

    
}
