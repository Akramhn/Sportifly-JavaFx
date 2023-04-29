/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Activiter;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.ActiviterService;

/**
 * FXML Controller class
 *
 * @author hadjn
 */
public class AdminActiviteController implements Initializable {

    @FXML
    private TextField Recherche_User;
     @FXML
    private TableView<Activiter> Vactivite;

    @FXML
    private TableColumn<Activiter, String> Vtitre;
    @FXML
    private TableColumn<Activiter, Integer> Vref_categ;
    @FXML
    private TableColumn<Activiter, Timestamp> Vdate_deb;
    @FXML
    private TableColumn<Activiter, Timestamp> Vdate_fin;
    @FXML
    private TableColumn<Activiter, Integer> Vid_user;
    @FXML
    private TableColumn<?, ?> delete;
    @FXML
    private TableColumn<?, ?> edit;
    @FXML
    private TableColumn<?, ?> pdf;
    @FXML
    private Button add_activite;
    @FXML
    private Button qrcode;
     private final ObservableList<Activiter> eventList = FXCollections.observableArrayList();
    ActiviterService ps = new ActiviterService();
    @FXML
    private Button dashcateg;
    @FXML
    private Button stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Activiter> personnes = ps.recuperer();
            ObservableList<Activiter> olp = FXCollections.observableArrayList(personnes);
            Vactivite.setItems(olp);

            Vtitre.setCellValueFactory(new PropertyValueFactory("titre"));

            Vref_categ.setCellValueFactory(new PropertyValueFactory("ref_categ"));
            Vid_user.setCellValueFactory(new PropertyValueFactory("id_user"));
            Vdate_deb.setCellValueFactory(new PropertyValueFactory("date_debut"));
            Vdate_fin.setCellValueFactory(new PropertyValueFactory("date_fin"));

            this.deleteactivite();
            this.editactivite();
            this.pdf();
           

        } catch (SQLException ex) {
            Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

   @FXML
    private void GoToAddActivite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/activite.fxml"));
        ActiviteController aec = loader.getController();
        Parent root = loader.load();
        add_activite.getScene().setRoot(root);

    }
    
    
    @FXML
    private void GoTodashcateg(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Admi_categ.fxml"));
        ActiviteController aec = loader.getController();
        Parent root = loader.load();
        dashcateg.getScene().setRoot(root);

    }

    /*@FXML
    private void GoToEditActivite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/activite.fxml"));
        ActiviteController aec = loader.getController();
        Parent root = loader.load();
        add_activite.getScene().setRoot(root);
        add_activite.getScene().setUserData(Vactivite.getItems().get(getIndex())));

    }
     */
   public void deleteactivite() {
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
                            if (ps.supprimer(Vactivite.getItems().get(getIndex()))) {
                                Vactivite.getItems().remove(getIndex());
                                Vactivite.refresh();
                            }
                        }
                    });
                    setGraphic(b);
                }
            }
        };
    });
}


    public void editactivite() {
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
                            Activiter selectedActivite = (Activiter) getTableView().getItems().get(getIndex());

                            // Load the ActiviteController and pass the selected Activite object to it
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/editeActivite.fxml"));
                            Parent root;
                            try {
                                root = loader.load();

                                ActiviteEditController activiteController = loader.getController();

                                activiteController.setId(selectedActivite.getId());
                                activiteController.setTitre(selectedActivite.getTitre());
                                activiteController.setDate_deb(selectedActivite.getDate_debut().toLocalDateTime().toLocalDate());
                                activiteController.setDate_fin(selectedActivite.getDate_fin().toLocalDateTime().toLocalDate());

                                add_activite.getScene().setRoot(root);
                            } catch (IOException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        setGraphic(b);

                    }
                }
            };

        });

    }

    @FXML
    public void qrcode() throws SQLException {

        List<Activiter> personnes = ps.recuperer();

        // Create a button
        // Set an event handler for the button
        // Convert the data to a string
        StringBuilder stringBuilder = new StringBuilder();
        for (Activiter data : personnes) {
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
        }
    }

    public void pdf() {
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
                        b.setStyle("-fx-background-color:  #2a2185; -fx-background-radius: 20;");
                        b.setOnAction((event) -> {
                            
                            // Get the selected Activite object from the TableView
                            Activiter selectedActivite = (Activiter) getTableView().getItems().get(getIndex());
                            // Create a new Document
                            Document document = new Document();

                            try {
                                // Create a PdfWriter to write the Document to a file or output stream
                                PdfWriter.getInstance(document, new FileOutputStream("activite.pdf"));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DocumentException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
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
                                table.addCell(new PdfPCell(new Phrase(selectedActivite.getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date Fin:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(selectedActivite.getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));

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

    }

    @FXML
    private void gotostat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Chart.fxml"));
        ActiviteController aec = loader.getController();
        Parent root = loader.load();
        stat.getScene().setRoot(root);
    }
    
}
