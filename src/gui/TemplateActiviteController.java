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
import entities.ActiviteComparator;
import entities.Activiter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.CommandLauncherFactory;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ActiviterService;
import util.SessionManager;

/**
 * FXML Controller class
 *
 * @author hadjn
 */
public class TemplateActiviteController implements Initializable {

    private List<Activiter> Activites;
    private ActiviterService activiteService = new ActiviterService();
    @FXML
    private Button add;
    @FXML
    private Button calendar;
    @FXML
    private Button qr;
    @FXML
    private TextField recherche;

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox postsContainer;

    int test = 0;

    private int itemsPerPage = 4;
    private int currentPageIndex = 0;
    private int totalPages;
    @FXML
    private ScrollPane pagin;
    @FXML
    private ImageView qrco;
    @FXML
    private Button tri;
    @FXML
    private Button Excel;
    @FXML
    private Button meet;
private SessionManager session;
    @FXML
    private Button offres;
    @FXML
    private Button reserv;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Activites = new ArrayList<>(activiteService.recupererById(session.getId()));
            filterActivites(recherche.getText()); // add this line to filter the initial list of activities
            setPage(0);
        } catch (SQLException ex) {
            Logger.getLogger(TemplateActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        recherche.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    filterActivites(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(TemplateActiviteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void setPage(int pageIndex) {
        currentPageIndex = pageIndex;
        totalPages = (int) Math.ceil((double) Activites.size() / itemsPerPage);

        postsContainer.getChildren().clear();

        int startingIndex = currentPageIndex * itemsPerPage;
        int endingIndex = Math.min(startingIndex + itemsPerPage, Activites.size());

        for (int i = startingIndex; i < endingIndex; i++) {
            Activiter act = Activites.get(i);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listAct.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                ListActController listactcont = fxmlLoader.getController();

                listactcont.setData(act);

                postsContainer.getChildren().add(hBox);
            } catch (IOException ex) {
                Logger.getLogger(TemplateActiviteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TemplateActiviteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        addPaginationControls();
    }

    private void addPaginationControls() {
        HBox paginationControls = new HBox();
        paginationControls.setAlignment(Pos.CENTER);

        Label prevLabel = new Label("Prev");
        prevLabel.setUnderline(true);
        prevLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setPage(currentPageIndex - 1);
            }
        });
        prevLabel.setDisable(currentPageIndex == 0);

        List<Label> pageLabels = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            Label pageLabel = new Label(String.valueOf(i + 1));
            pageLabel.setUnderline(true);
            final int pageIndex = i;
            pageLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setPage(pageIndex);
                }
            });
            pageLabels.add(pageLabel);
        }

        Label nextLabel = new Label("Next");
        nextLabel.setUnderline(true);
        nextLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setPage(currentPageIndex + 1);
            }
        });
        nextLabel.setDisable(currentPageIndex == totalPages - 1);

        ObservableList<Node> paginationNodes = FXCollections.observableArrayList();
        paginationNodes.addAll(prevLabel);
        paginationNodes.addAll(pageLabels.toArray(new Label[0]));
        paginationNodes.addAll(nextLabel);
        paginationControls.getChildren().addAll(paginationNodes);

        postsContainer.getChildren().add(paginationControls);
    }

    private void filterActivites(String query) throws SQLException {
        List<Activiter> filteredActivites = activiteService.recupererById(session.getId()).stream()
                .filter(act -> act.getTitre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        Activites.clear(); // clear the original list
        Activites.addAll(filteredActivites); // add the filtered results
        setPage(0);
    }

    @FXML
    public void gotoadd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addActivite_front.fxml"));
        AddActivite_FrontController aec = loader.getController();
        Parent root = loader.load();
        add.getScene().setRoot(root);

    }

    @FXML
    public void gotocalendar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CalendarActivite.fxml"));

        Parent root = loader.load();
        calendar.getScene().setRoot(root);

    }

  @FXML
    public void qrcode() throws SQLException {
ActiviterService ps = new ActiviterService();
        List<Activiter> personnes = ps.recupererById(session.getId());

        // Create a button
        // Set an event handler for the button
        // Convert the data to a string
        StringBuilder stringBuilder = new StringBuilder();
        int i=0;
        for (Activiter data : personnes) {
            i++;
             stringBuilder.append("******** Activite Num "+i);
            stringBuilder.append("Titre :"+data.getTitre());
            stringBuilder.append("\n");
            stringBuilder.append("Date debut :"+data.getDate_debut());
             stringBuilder.append("\n");
            stringBuilder.append("Date fin :"+data.getDate_fin());
      
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        }
        String dataString = stringBuilder.toString().trim();

        // Generate the QR code image
        Image qrCodeImage = generateQRCode(dataString);

        // Display the QR code image
        qrco.setImage(qrCodeImage);

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


    @FXML
    private void trierActivites(ActionEvent event) throws SQLException {
        // sort the list of events using your comparator
        ActiviterService ps = new ActiviterService();
        if (test == 0) {test=1;
            tri.setText("Annuler tri");

            List<Activiter> listActivit = new ArrayList<>(ps.recupererById(session.getId()));
            Collections.sort(listActivit, new ActiviteComparator());
            Activites = listActivit;
        } else {test=0;
            tri.setText("Tri");
            List<Activiter> listActivit = new ArrayList<>(ps.recupererById(session.getId()));
            Activites = listActivit;
        }

        setPage(0);

    }

   @FXML
private void exportToExcel(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();

    // Set extension filter for Excel files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (.xlsx)", ".xlsx");
    fileChooser.getExtensionFilters().add(extFilter);

    // Show save file dialog
    File file = fileChooser.showSaveDialog(Excel.getScene().getWindow());

    if (file != null) {
        try {
            // Create new Excel workbook and sheet
           Workbook workbook = new XSSFWorkbook();

            org.apache.poi.ss.usermodel.Sheet sheet =workbook.createSheet("Activties");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Titre");
            headerRow.createCell(2).setCellValue("Date_debut");
            headerRow.createCell(3).setCellValue("Date_fin");

            // Add data rows
            for (int i = 0; i < Activites.size(); i++) {
                Row row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(Activites.get(i).getId());
                row.createCell(1).setCellValue(Activites.get(i).getTitre());
                row.createCell(2).setCellValue(Activites.get(i).getDate_debut());
                row.createCell(3).setCellValue(Activites.get(i).getDate_fin());
            }

            // Write to file
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText(null);
            alert.setContentText("Activites exported to Excel file.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while exporting Activites to Excel file.");
            alert.showAndWait();
        }
    }
}

    @FXML
    private void goTomeet(ActionEvent event) throws IOException {
          String url = "https://meet.google.com/new";
        CommandLauncher launcher = CommandLauncherFactory.createVMLauncher();
        Runtime.getRuntime().exec(String.format("rundll32 url.dll,FileProtocolHandler %s", url));
    }

    @FXML
    private void gotoOffres(ActionEvent event) {
           
           if (SessionManager.getRoles().contains("Role_USER")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/espaceUser.fxml"));

                                    Parent root = loader.load();
                                    offres.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else if (SessionManager.getRoles().contains("Role_Coach")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachOffres.fxml"));

                                    Parent root = loader.load();
                                    offres.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
    
    
    }

    @FXML
    private void gotoreserv(ActionEvent event) {
       
    
     if (SessionManager.getRoles().contains("Role_USER")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListReservation.fxml"));

                                    Parent root = loader.load();
                                    offres.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else if (SessionManager.getRoles().contains("Role_Coach")) {

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CoachReservation.fxml"));

                                    Parent root = loader.load();
                                    offres.getScene().setRoot(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(espaceUserController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

}
}
