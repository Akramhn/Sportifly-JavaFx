/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Offre;
import entities.OffreComparator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CategorieService;
import services.OffreService;
import util.SessionManager;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class espaceUserController implements Initializable {

    @FXML
    private VBox postsContainer;
    private List<Offre> Offres;
    private OffreService offreService = new OffreService();
    private CategorieService categoryService = new CategorieService();
    private int id;
    @FXML
    private Button mesoffres;
    @FXML
    private Button mesreservation;
    @FXML
    private VBox vboxCheck;
    private List<Integer> selectedCategories = new ArrayList<>();
    @FXML
    private TextField search;
    @FXML
    private Button triOffre;

    private String tri = "tri";
    
    private SessionManager session ;
    @FXML
    private Button planning;
    @FXML
    private Button profil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Offres = new ArrayList<>(offreService.recuperer(session.getId()));

            List<String> categoryNames = categoryService.recuperernom();

            for (String categoryName : categoryNames) {
                CheckBox checkBox = new CheckBox(categoryName);
                checkBox.setOnAction(e -> {
                    if (checkBox.isSelected()) {
                        try {
                            selectedCategories.add(categoryService.recupererIdByNom(categoryName));
                            for (int h : selectedCategories) {
                                System.out.println("add :" + h);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            System.out.println(categoryService.recupererIdByNom(categoryName));
                            Integer p = categoryService.recupererIdByNom(categoryName);
                            selectedCategories.remove(p);
                            for (int h : selectedCategories) {
                                System.out.println("remove :" + h);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        filterOffers();
                    } catch (SQLException ex) {
                        Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                vboxCheck.getChildren().add(checkBox);
            }

            for (Offre offre : Offres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ListOffreUser.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ListOffreUserController listoffreUserContoller = fxmlLoader.getController();
                    listoffreUserContoller.setData(offre);
                    postsContainer.getChildren().add(vBox);
                } catch (IOException ex) {
                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mesOff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("espaceUser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void meRes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListReservation.fxml"));

            Parent root = loader.load();
            mesreservation.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterOffers() throws SQLException {
        List<Offre> filteredOffres;
        if (selectedCategories.isEmpty()) {
            filteredOffres = Offres;
        } else {
            filteredOffres = Offres.stream()
                    .filter(offre -> selectedCategories.contains(offre.getId_categroy()))
                    .collect(Collectors.toList());
        }

        postsContainer.getChildren().clear();
        for (Offre offre : filteredOffres) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ListOffreUser.fxml"));
            try {
                VBox vBox = fxmlLoader.load();
                ListOffreUserController listoffreUserContoller = fxmlLoader.getController();
                listoffreUserContoller.setData(offre);
                postsContainer.getChildren().add(vBox);
            } catch (IOException ex) {
                Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML

    private void SearchOffre(ActionEvent event) throws SQLException {
        String searchText = search.getText().toLowerCase();
        List<Offre> filteredOffres;
        if (searchText.isEmpty() && selectedCategories.isEmpty()) {
            filteredOffres = Offres;
        } else {
            filteredOffres = Offres.stream()
                    .filter(offre -> offre.getDescription().toLowerCase().contains(searchText))
                    .filter(offre -> selectedCategories.isEmpty() || selectedCategories.contains(offre.getId_categroy()))
                    .collect(Collectors.toList());
        }

        postsContainer.getChildren().clear();
        for (Offre offre : filteredOffres) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ListOffreUser.fxml"));
            try {
                VBox vBox = fxmlLoader.load();
                ListOffreUserController listoffreUserContoller = fxmlLoader.getController();
                listoffreUserContoller.setData(offre);
                postsContainer.getChildren().add(vBox);
            } catch (IOException ex) {
                Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void triOffre(ActionEvent event) throws SQLException {
        // sort the list of events using your comparator

        if (tri == "tri") {
            tri = "annuler tri";
            triOffre.setText("Annuler Tri");

            Collections.sort(Offres, new OffreComparator());
            // clear the main VBox
            postsContainer.getChildren().clear();
            for (Offre offre : Offres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ListOffreUser.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ListOffreUserController listoffreUserContoller = fxmlLoader.getController();
                    listoffreUserContoller.setData(offre);
                    postsContainer.getChildren().add(vBox);
                } catch (IOException ex) {
                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.Offres = Offres;
        } else {
            tri = "tri";
            triOffre.setText("Best Offres");
            Offres = new ArrayList<>(offreService.recuperer(session.getId()));
              postsContainer.getChildren().clear();
            for (Offre offre : Offres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ListOffreUser.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ListOffreUserController listoffreUserContoller = fxmlLoader.getController();
                    listoffreUserContoller.setData(offre);
                    postsContainer.getChildren().add(vBox);
                } catch (IOException ex) {
                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           

        }

    }

    @FXML
    private void gotoPlanning(ActionEvent event) {
        
       
              try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/templateActivite.fxml"));
            
            Parent root = loader.load();
            planning.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        } }

    @FXML
    private void gotoProfil(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/UserProfilee.fxml"));

            Parent root = loader.load();
            profil.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                   
                
    
          
    

}
