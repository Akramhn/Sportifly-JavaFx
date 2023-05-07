/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Commentaire_Act;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ActualiteService;
import services.Commentaire_ActService;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class Afficher_ComController implements Initializable {

    private Button btnafficherAct;
    @FXML
    private Button btnajouterCom;
    @FXML
    private TableView<Commentaire_Act> tableCom;
    @FXML
    private TableColumn<Commentaire_Act, Integer> id;
    @FXML
    private TableColumn<Commentaire_Act, String> contenu;
    @FXML
    private TableColumn<Commentaire_Act, Integer> id_actualite_id;
    @FXML
    private TableColumn<Commentaire_Act, Timestamp> date;
    private Commentaire_ActService actualiteService;
    private ObservableList<Commentaire_Act> observableListCommentaire_Act;
    Commentaire_ActService ps = new Commentaire_ActService();
    @FXML
    private TableColumn<Commentaire_Act, Button> delete;
    @FXML
    private TableColumn<Commentaire_Act, Button> edit;
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

        /*  actualiteService = new Commentaire_ActService(); // créer une instance de votre classe de service/DAO
        observableListCommentaire_Act = FXCollections.observableArrayList(); // créer une instance de ObservableList

        // ajouter les données récupérées de la base de données à l'ObservableList
        try {
            observableListCommentaire_Act.addAll(actualiteService.afficher());
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des données depuis la base de données");
        }
         */
        try {
            List<Commentaire_Act> personnes = ps.recuperer();
            ObservableList<Commentaire_Act> olp = FXCollections.observableArrayList(personnes);
            tableCom.setItems(olp);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            id_actualite_id.setCellValueFactory(new PropertyValueFactory<>("id_actualite_id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            this.delete();
            this.edit();
        } catch (SQLException ex) {
            Logger.getLogger(Afficher_ComController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void GoToAct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter_Com.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) comm.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void delete() {
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

                        b.setStyle("-fx-background-color: #2a2185; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            try {
                                if (ps.supprimertest(tableCom.getItems().get(getIndex()))) {
                                    tableCom.getItems().remove(getIndex());
                                    tableCom.refresh();

                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Afficher_ComController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        setGraphic(b);

                    }
                }
            };

        });

    }

    public void edit() {
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

                        b.setStyle("-fx-background-color: #2a2185; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            // Get the selected Activite object from the TableView
                            System.out.println(tableCom.getItems().get(getIndex()).getContenu());
                            Commentaire_Act selectedActivite = (Commentaire_Act) tableCom.getItems().get(getIndex());

                            // Load the ActiviteController and pass the selected Activite object to it
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/modifier_Com.fxml"));
                            Parent root;
                            try {
                                root = loader.load();

                                Modifier_ComController activiteController = loader.getController();
                                System.out.println(selectedActivite.getContenu());

                                activiteController.setCom(selectedActivite);

                                btnajouterCom.getScene().setRoot(root);
                            } catch (IOException ex) {
                                Logger.getLogger(Afficher_ComController.class.getName()).log(Level.SEVERE, null, ex);
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
        } else if (clickedButton.getId().equals("plan")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminActivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) plan.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("cat")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admi_categ.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("act")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Act.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) act.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("comm")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_Com.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) comm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("MesressA")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListResBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) MesressA.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("evente")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (clickedButton.getId().equals("part")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) evente.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

}
