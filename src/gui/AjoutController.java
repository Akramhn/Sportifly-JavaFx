package gui;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pi.MainApp;
import entities.Event;
import gui.MainWindowController;
import gui.ShowAllController;
import services.EventService;
import util.AlertUtils;
import util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.SessionManager;

public class AjoutController implements Initializable {

    @FXML
    public TextField titreTF;
    @FXML
    public TextField descriptionTF;
    @FXML
    public DatePicker dateDP;
    @FXML
    public ImageView imageIV;
    @FXML
    public TextField lieuTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Event currentEvent;
    Path selectedImagePath;
    boolean imageEdited;
    @FXML
    private Button pdf;
    @FXML
    private Button calendar;
    @FXML
    private Button excel;
    SessionManager session;
    private static final String SAVE_PATH = "C:/Users/wadah/OneDrive/Desktop/integre/usersgestion/public/uploads/event/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentEvent = ShowAllController.currentEvent;

        if (currentEvent != null) {
            topText.setText("Modifier event");
            btnAjout.setText("Modifier");

            try {
                titreTF.setText(currentEvent.getTitre());
                descriptionTF.setText(currentEvent.getDescription());
                dateDP.setValue(currentEvent.getDate());
                selectedImagePath = FileSystems.getDefault().getPath(SAVE_PATH + selectedImagePath.getFileName());
                
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }
                lieuTF.setText(currentEvent.getLieu());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter event");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentEvent.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            Event event = new Event(
                    titreTF.getText(),
                    descriptionTF.getText(),
                    dateDP.getValue(),
                    imagePath,
                    lieuTF.getText()
            );
            System.out.println(imagePath);

            if (currentEvent == null) {

                if (EventService.getInstance().add(event, session.getId())) {
                    AlertUtils.makeSuccessNotification("Event ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_EVENT);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                event.setId(currentEvent.getId());
                if (EventService.getInstance().edit(event)) {
                    AlertUtils.makeSuccessNotification("Event modifié avec succés");
                    ShowAllController.currentEvent = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_EVENT);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }

    @FXML
    public void chooseImage(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
           selectedImagePath= FileSystems.getDefault().getPath(SAVE_PATH + selectedImagePath.getFileName());
            
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

  public void createImageFile() {
        try {
         
            Path newPath = FileSystems.getDefault().getPath(SAVE_PATH + selectedImagePath.getFileName());
          selectedImagePath= FileSystems.getDefault().getPath(SAVE_PATH + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            
            selectedImagePath = newPath.getFileName(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private boolean controleDeSaisie() {

        if (titreTF.getText().isEmpty()) {
            AlertUtils.makeInformation("titre ne doit pas etre vide");
            return false;
        }

        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }

        if (dateDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour date");
            return false;
        }

        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }

        if (lieuTF.getText().isEmpty()) {
            AlertUtils.makeInformation("lieu ne doit pas etre vide");
            return false;
        }

        return true;
    }

    @FXML
    private void buttpdf(ActionEvent event) throws FileNotFoundException, DocumentException, SQLException, BadElementException, IOException {

        try {
            EventService EventService = new EventService();
            List<Event> events = EventService.getAll();

// Create a new PDF document
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream("sponsors.pdf"));

// Open the document
            document.open();

// Create a table with four columns
            PdfPTable table = new PdfPTable(4);

// Add table headers
            table.addCell("Intitule");
            table.addCell("Durée");
            table.addCell("Date début");
//table.addCell("Date fin");

// Add the information of all sponsors to the table
            for (Event s : events) {
                table.addCell(s.getTitre());
                table.addCell(s.getDescription());
                table.addCell(s.getLieu());
                // table.addCell(s.getDatefinc().toString());
            }

// Add the table to the document
            document.add(table);

// Close the document
            document.close();

// Show a success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Impression réussie");
            alert.setContentText("La liste des sponsors a été imprimée avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            // Show an error message if an exception occurs
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'impression");
            alert.setContentText("Une erreur s'est produite lors de l'impression de la liste des sponsors.");
            alert.showAndWait();

        }

    }

    @FXML
    void handleButtonClick(ActionEvent event) throws IOException {
        Parent myNewScene = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        Scene scene = new Scene(myNewScene);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    void handleButtonClick2(ActionEvent event) throws IOException {
        Parent myNewScene = FXMLLoader.load(getClass().getResource("Excel.fxml"));
        Scene scene = new Scene(myNewScene);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter for Excel files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (.xlsx)", ".xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(excel.getScene().getWindow());

        if (file != null) {
            try {
                // Create new Excel workbook and sheet
                Workbook workbook = new XSSFWorkbook();

                Sheet sheet = workbook.createSheet("Events");

                // Create header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("Titre");
                headerRow.createCell(2).setCellValue("Description");
                headerRow.createCell(3).setCellValue("Date");
                headerRow.createCell(3).setCellValue("Lieu");

                // Add data rows
                EventService EventService = new EventService();
                List<Event> events = EventService.getAll();
                for (int i = 0; i < events.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(events.get(i).getId());
                    row.createCell(1).setCellValue(events.get(i).getTitre());
                    row.createCell(2).setCellValue(events.get(i).getDescription());
                    row.createCell(3).setCellValue(events.get(i).getDate());
                    row.createCell(4).setCellValue(events.get(i).getLieu());
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
                alert.setContentText("Events exported to Excel file.");
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Failed");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while exporting events to Excel file.");
                alert.showAndWait();
            }
        }
    }

}
