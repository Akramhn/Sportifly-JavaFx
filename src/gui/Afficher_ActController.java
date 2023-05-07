/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Actualite;
import java.awt.Desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import services.ActualiteService;
import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Nour moutii
 */
public class Afficher_ActController implements Initializable {

    @FXML
    private TableView<Actualite> tableAct;
    @FXML
    private TableColumn<Actualite, Integer> col_id;
    @FXML
    private TableColumn<Actualite, String> col_titre;
    @FXML
    private TableColumn<Actualite, String> col_image;
    @FXML
    private TableColumn<Actualite, String> col_description;
    @FXML
    private TableColumn<Actualite, String> col_categorie;
    @FXML
    private TableColumn<Actualite, LocalDateTime> col_date;

    private ActualiteService actualiteService;
    private ObservableList<Actualite> observableListActualites;
    @FXML
    private Button btnajouter;
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
        actualiteService = new ActualiteService(); // créer une instance de votre classe de service/DAO
        observableListActualites = FXCollections.observableArrayList(); // créer une instance de ObservableList

        // ajouter les données récupérées de la base de données à l'ObservableList
        try {
            observableListActualites.addAll(actualiteService.afficher());
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des données depuis la base de données");
        }

        // définir l'ObservableList comme la source de données de la TableView
        tableAct.setItems(observableListActualites);

        // configurer les colonnes de la TableView pour afficher les données
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Actualite, Void> col_actions = new TableColumn<>("Actions");
        col_actions.setCellFactory(param -> new ActionTableCell());
        tableAct.getColumns().add(col_actions);

    }

    @FXML
    private void GoToAddAct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ajouter_Act.fxml"));
        Ajouter_ActController aec = loader.getController();
        Parent root = loader.load();
        btnajouter.getScene().setRoot(root);

    }

    private void GoToCom(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficher_Com.fxml"));
        Afficher_ComController aec = loader.getController();

        Parent root = loader.load();
        btnajouter.getScene().setRoot(root);

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

    public class ActionTableCell extends TableCell<Actualite, Void> {

        private final Button modifierButton = new Button("Modifier");
        private final Button supprimerButton = new Button("Supprimer");
        private final Button AjoutComButton = new Button("ajouter commentaire");
        private final Button pdf = new Button("fournir pdf");

        public ActionTableCell() {

            // Code pour modifier l'actualité 
            modifierButton.setOnAction((event) -> {                             // Get the selected Activite object from the TableView                      
                Actualite actualite = (Actualite) tableAct.getItems().get(getIndex());                              // Load the ActiviteController and pass the selected Activite object to it                     

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/modifier_Act.fxml"));

                Parent root;

                try {

                    root = loader.load();
                    Modifier_ActController mac = loader.getController();
                    mac.setActualite(actualite);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    // Fermer la fenêtre d'affichage d'actualité
                    Stage currentStage = (Stage) modifierButton.getScene().getWindow();
                    currentStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            supprimerButton.setOnAction(event -> {
                Actualite actualite = tableAct.getItems().get(getIndex());
                // Code pour supprimer l'actualité
                try {
                    actualiteService.supprimer(actualite);

                    // mettre à jour la liste ObservableList d'actualités
                    observableListActualites.clear();
                    observableListActualites.addAll(actualiteService.afficher());

                    // définir la nouvelle liste comme source de données de la TableView
                    tableAct.setItems(observableListActualites);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            AjoutComButton.setOnAction((event) -> {                             // Get the selected Activite object from the TableView                      
                Actualite actualite = (Actualite) tableAct.getItems().get(getIndex());                              // Load the ActiviteController and pass the selected Activite object to it                     

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ajouter_Com.fxml"));

                Parent root;

                try {

                    root = loader.load();
                    Ajouter_ComController mac = loader.getController();
                    mac.setActualiteId(actualite.getId());
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    // Fermer la fenêtre d'affichage d'actualité
                    Stage currentStage = (Stage) AjoutComButton.getScene().getWindow();
                    currentStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            /* pdf.setOnAction((event) -> {                             // Get the selected Activite object from the TableView                      
                Actualite actualite = (Actualite) tableAct.getItems().get(getIndex());                              // Load the ActiviteController and pass the selected Activite object to it                     

               Document document = new Document() ;

                            try {
                                // Create a PdfWriter to write the Document to a file or output stream
                                PdfWriter.getInstance(document, new FileOutputStream("actualite.pdf"));
                            } catch (FileNotFoundException ex) {
                                System.out.println(ex.getMessage());
                            } catch (DocumentException ex) {
                                System.out.println(ex.getMessage());
                            }

// Open the Document
                            document.open();

                            try {
                                float[] columnWidths = {1, 2}; // the widths of the columns in the table
                                PdfPTable table = new PdfPTable(columnWidths);
                                table.setWidthPercentage(100); // set the width of the table to 100% of the page
                                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); // center the text in the table cells

// Add the table headers
                               

// Add the Activite details to the table

                                table.addCell(new PdfPCell(new Phrase("Titre:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(actualite.getTitre(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(actualite.getDate().toString(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("description", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(actualite.getDescription(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("categorie", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(actualite.getCategorie(), new Font(Font.FontFamily.HELVETICA, 10))));

// Add the table to the Document
                                document.add(table);

                            
                               
                            } catch (DocumentException ex) {
                                  System.out.println(ex.getMessage());
                            }

// Close the Document
                            document.close();

                            if (Desktop.isDesktopSupported()) {
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(new File("actualite.pdf"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
    

            });*/
            pdf.setOnAction((event) -> {
                // Get the selected Actualite object from the TableView                      
                Actualite actualite = (Actualite) tableAct.getItems().get(getIndex());

                // Create a new Document object
                Document document = new Document();

                try {
                    // Create a PdfWriter to write the Document to a file or output stream
                    PdfWriter.getInstance(document, new FileOutputStream("actualite.pdf"));
                    document.open();

                    // Create a new Paragraph object with the title text and style
                    Paragraph title = new Paragraph(actualite.getTitre(), new Font(FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);

                    // Create a new Paragraph object with the date and category text and style
                    Paragraph dateCategory = new Paragraph(actualite.getDate().toString() + " | " + actualite.getCategorie(), new Font(FontFamily.TIMES_ROMAN, 14, Font.ITALIC));
                    dateCategory.setAlignment(Element.ALIGN_CENTER);
                    document.add(dateCategory);

                    File file = new File("D:\\pidev\\Sportifly - Copie\\src\\uploads\\" + actualite.getImage());
                    Image image = Image.getInstance(file.getPath());
                    image.setAlignment(Element.ALIGN_CENTER);
                    image.scaleToFit(500, 500); // Resize the image to fit the page width
                    document.add(image);

                    Image logo = Image.getInstance("D:\\pidev\\Sportifly - Copie\\src\\uploads\\sportifly.png");
                    logo.scaleToFit(100, 100); // Redimensionner l'image à la taille souhaitée
                    logo.setAbsolutePosition(36, PageSize.A4.getHeight() - 36 - logo.getScaledHeight()); // Définir la position absolue de l'image
                    document.add(logo);

                    // Create a new Paragraph object with the description text and style
                    Paragraph description = new Paragraph(actualite.getDescription(), new Font(FontFamily.TIMES_ROMAN, 14));
                    description.setSpacingBefore(20);
                    document.add(description);

                } catch (DocumentException ex) {
                    System.out.println(ex.getMessage());
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    document.close();
                }

                // Open the generated PDF file using the default system PDF viewer
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.open(new File("actualite.pdf"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        }

        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                HBox hbox = new HBox(modifierButton, supprimerButton, AjoutComButton, pdf);
                hbox.setSpacing(10);
                setGraphic(hbox);
            } else {
                setGraphic(null);
            }
        }

    }
    /* @FXML 
        public void qrcode() throws SQLException {

        List<Actualite> personnes = (List<Actualite>) actualiteService.recuperer();

        // Create a button
        // Set an event handler for the button
        // Convert the data to a string
        StringBuilder stringBuilder = new StringBuilder();
        for (Actualite data : personnes) {
            stringBuilder.append(data);
            stringBuilder.append("\n");
        }
        String dataString = stringBuilder.toString().trim();

        // Generate the QR code image
        Image qrCodeImage = generateQRCode(dataString);

        // Display the QR code image
        ImageView imageView = new ImageView(qrCodeImage);
        VBox vbox = new VBox(imageView);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    private Image generateQRCode(String data) {
        try {
            // Create a QR code writer
            QRCodeWriter writer = new QRCodeWriter();

            // Create a BitMatrix from the data and set the size
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            // Create a BufferedImage from the BitMatrix
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Create a JavaFX Image from the BufferedImage
            return SwingFXUtils.toFXImage(image, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
}

// Create a new Document
/* public void pdf() {
        pdf.setCellFactory((param) -> {
            return new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    setGraphic(null);
                    if (!empty) {
                        Button b = new Button("pdf");
                        b.setLayoutX(14.0);
                        b.setLayoutY(14.0);
                        b.setPrefWidth(108.0);
                        b.setPrefHeight(35.0);
                        b.setTextFill(Color.WHITE);
                        b.setStyle("-fx-background-color: #b0f2b6; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            System.out.println("gggg");
                            // Get the selected Activite object from the TableView
                            Actualite selectedActivite = (Actualite) getTableView().getItems().get(getIndex());
                            // Create a new Document
                            Document document = new Document();

                            try {
                                // Create a PdfWriter to write the Document to a file or output stream
                                PdfWriter.getInstance(document, new FileOutputStream("activite.pdf"));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DocumentException ex) {
                                Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                            }

// Open the Document
                            document.open();

                            try {
                                float[] columnWidths = {1, 2}; // the widths of the columns in the table
                                PdfPTable table = new PdfPTable(columnWidths);
                                table.setWidthPercentage(100); // set the width of the table to 100% of the page
                                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); // center the text in the table cells

// Add the table headers
                               

// Add the Activite details to the table
                                table.addCell(new PdfPCell(new Phrase("Titre:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(selectedActivite.getTitre(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date Debut:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(selectedActivite.getDate().toString(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date Fin:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(selectedActivite.getDescription(), new Font(Font.FontFamily.HELVETICA, 10))));

// Add the table to the Document
                                document.add(table);

                            
                               
                            } catch (DocumentException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            }

// Close the Document
                            document.close();

                            if (Desktop.isDesktopSupported()) {
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(new File("activite.pdf"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        setGraphic(b);
                    }
                }
            };

        });

    }*/
 /*   
 public class Col_commentaires extends TableColumn<Actualite, Void> {

    private final Button ajouterCommentaireButton = new Button("Ajouter commentaire");

    public Col_commentaires() {
        setCellFactory(param -> new TableCell<Actualite, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Récupérer l'actualité correspondante à cette ligne
                    Actualite actualite = getTableView().getItems().get(getIndex());

                    // Ajouter un bouton "ajouter commentaire" avec un événement qui va ouvrir la page Ajouter_Com et transmettre l'id de l'actualité correspondante
                    ajouterCommentaireButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ajouter_Com.fxml"));
                            Ajouter_ComController ajouterComController = loader.getController();
                            ajouterComController.setActualiteId(actualite.getId());
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    // Ajouter le bouton "ajouter commentaire" à la cellule
                    HBox hbox = new HBox();
                    hbox.getChildren().add(ajouterCommentaireButton);
                    setGraphic(hbox);
                }
            }
        });
    }
}*/
 /*  public void ajouter_commentaire() {
        ajouter_commentaire.setCellFactory((TableColumn<Actualite, Button> param) -> {
            return new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    setGraphic(null);
                    if (!empty) {
                        Button b = new Button("ajouter commentaire");
                        b.setLayoutX(14.0);
                        b.setLayoutY(14.0);
                        b.setPrefWidth(108.0);
                        b.setPrefHeight(35.0);

                        b.setStyle("-fx-background-color: #b0f2b6; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            // Get the selected Activite object from the TableView
                            Actualite selectedActivite = (Actualite) tableAct.getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ajouter_Com.fxml"));
                            Parent root;
                            try {
                                root = loader.load();

                                Ajouter_ComController activiteController = loader.getController();

                                activiteController.setActualite(selectedActivite);

                            } catch (IOException ex) {
                                Logger.getLogger(Afficher_ActController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                    }
                }
             };

        });

    }
 */
