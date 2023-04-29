/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Categorie;
import entities.Offre;
import entities.reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import services.OffreService;
import services.ReservationService;

/**
 * FXML Controller class
 *
 * @author wadah
 */
public class ListResBackController implements Initializable {

    @FXML
    private Button mesoff;
    @FXML
    private Button ajouterOff;
    @FXML
    private TableColumn<?, ?> tid;
    @FXML
    private TableColumn<?, ?> userId;

    @FXML
    private TableColumn<?, ?> date;
    private TableColumn<?, ?> prix;
    private TableColumn<?, ?> desc;
    private TableColumn<?, ?> nbPlace;

    @FXML
    private TableView<reservation> Voff;
    @FXML
    private TableColumn<?, ?> edit;
    @FXML
    private TableColumn<?, ?> delete;
    OffreService oc = new OffreService();
    @FXML
    private TableColumn<?, ?> OffreID;
    @FXML
    private TableColumn<?, ?> Status;

    private final ObservableList<reservation> resList = FXCollections.observableArrayList();
    ReservationService rs = new ReservationService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            List<reservation> reserList = rs.recuperer();
            ObservableList<reservation> olp = FXCollections.observableArrayList(reserList);
            Voff.setItems(olp);
            tid.setCellValueFactory(new PropertyValueFactory("id"));
            userId.setCellValueFactory(new PropertyValueFactory("id_user"));
            date.setCellValueFactory(new PropertyValueFactory("date"));

            OffreID.setCellValueFactory(new PropertyValueFactory("id_offre"));
            Status.setCellValueFactory(new PropertyValueFactory("status"));

            this.deleteOff();
            //  this.editcateg();

        } catch (SQLException ex) {
            Logger.getLogger(ListResBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteOff() {
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
                                    if (rs.supprimer1(Voff.getItems().get(getIndex()))) {
                                        Voff.getItems().remove(getIndex());
                                        Voff.refresh();
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(ListResBackController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        setGraphic(b);
                    }
                }
            };
        });
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AddReservation.fxml"));

            Parent root = loader.load();
            ajouterOff.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mesoff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListOffreBack.fxml"));

            Parent root = loader.load();
            mesoff.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
