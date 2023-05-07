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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.CategorieService;

/**
 * FXML Controller class
 *
 * @author hadjn
 */
public class AdmincategController implements Initializable {

    @FXML
    private TextField Recherche_User;
     @FXML
    private TableView<Categorie> Vcateg;

    @FXML
    private TableColumn<Categorie, String> vnom;
    @FXML
    private TableColumn<Categorie, String> Vdesc;
    
    @FXML
    private TableColumn<?, ?> delete;
    @FXML
    private TableColumn<?, ?> edit;
  
    @FXML
    private Button add_categ;
 
     private final ObservableList<Categorie> categList = FXCollections.observableArrayList();
    CategorieService ps = new CategorieService();
    private Button PLANN;
    @FXML
    private Button MesOffA;
    @FXML
    private Button MesressA;
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
        try {
            List<Categorie> categs = ps.recuperer();
            ObservableList<Categorie> olp = FXCollections.observableArrayList(categs);
            Vcateg.setItems(olp);

            vnom.setCellValueFactory(new PropertyValueFactory("nom"));

            Vdesc.setCellValueFactory(new PropertyValueFactory("description"));
            

            this.deletecateg();
            this.editcateg();
   
           

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    

   @FXML
    private void GoToAddCateg(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/categ.fxml"));
      
        Parent root = loader.load();
        add_categ.getScene().setRoot(root);

    }

    /*@FXML
    private void GoToEditActivite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/activite.fxml"));
        ActiviteController aec = loader.getController();
        Parent root = loader.load();
        add_categ.getScene().setRoot(root);
        add_categ.getScene().setUserData(Vcateg.getItems().get(getIndex())));

    }
     */
    
    
    private void GoToACTIVITE(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ADMIN.fxml"));
      
        Parent root = loader.load();
        PLANN.getScene().setRoot(root);

    }
    
    
 public void deletecateg() {
    delete.setCellFactory((param) -> {
        return new TableCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                setGraphic(null);
                if (!empty) {
                    Button b = new Button("delete");
                    b.setLayoutX(14.0);
                    b.setLayoutY(14.0);
                    b.setPrefWidth(108.0);
                    b.setPrefHeight(35.0);
                    b.setTextFill(Color.WHITE);
                    b.setStyle("-fx-background-color:  #2a2185; -fx-background-radius: 20;");
                    b.setOnAction((event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to delete this record?");
                        ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll(confirmButton, cancelButton);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == confirmButton) {
                            try {
                                if (ps.supprimer(Vcateg.getItems().get(getIndex()))) {
                                    Vcateg.getItems().remove(getIndex());
                                    Vcateg.refresh();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(AdmincategController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    setGraphic(b);
                }
            }
        };
    });
}


    public void editcateg() {
        edit.setCellFactory((param) -> {
            return new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    setGraphic(null);
                    if (!empty) {
                        Button b = new Button("edit");
                        b.setLayoutX(14.0);
                        b.setLayoutY(14.0);
                        b.setPrefWidth(108.0);
                        b.setPrefHeight(35.0);
                        b.setTextFill(Color.WHITE);
                        b.setStyle("-fx-background-color:  #2a2185; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            // Get the selected Activite object from the TableView
                            Categorie selectedcateg = (Categorie) getTableView().getItems().get(getIndex());

                            // Load the ActiviteController and pass the selected Activite object to it
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/edite_categ.fxml"));
                            Parent root;
                            try {
                                root = loader.load();

                                CategEditController categController = loader.getController();

                                categController.setId(selectedcateg.getId());
                               
                                add_categ.getScene().setRoot(root);
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });
                        setGraphic(b);

                    }
                }
            };

        });

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
