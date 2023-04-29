/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.pdf.PdfContentByte;

import com.itextpdf.text.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Activiter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import services.ActiviterService;
import services.CategorieService;

/**
 * FXML Controller class
 *
 * @author hadjn
 */
public class ListActController implements Initializable {

    int id;
    Activiter act;
    @FXML
    private Label categ;
    @FXML
    private Button delet;

    public Activiter getAct() {
        return act;
    }

    public void setAct(Activiter act) {
        this.act = act;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @FXML
    private Label titre;
    @FXML
    private Label deb;
    @FXML
    private Label fin;
    @FXML
    private Button edit;
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Activiter act) throws SQLException {
        this.act = act;

        this.id = act.getId();
        titre.setText(act.getTitre());

        deb.setText(act.getDate_debut().toString());
        fin.setText(act.getDate_fin().toString());
        CategorieService cs = new CategorieService();
        categ.setText(cs.recupererByid(act.getRef_categ()).getNom());

    }

    @FXML
    public void edit(ActionEvent event) throws SQLException {
        // Load the ActiviteController and pass the selected Activite object to it
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/editeActivitefront.fxml"));
        Parent root;
        try {
            root = loader.load();
            ActiviteEditFrontController EditactController = loader.getController();

            EditactController.initialize(getAct());

            edit.getScene().setRoot(root);

        } catch (IOException ex) {
            Logger.getLogger(ListActController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void delet(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this record?");
        ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == confirmButton) {
            ActiviterService as = new ActiviterService();
            if (as.supprimer(id)) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Operation completed successfully!");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Operation failed!");
                errorAlert.showAndWait();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TemplateActivite.fxml"));
            Parent root = loader.load();
            delet.getScene().setRoot(root);
        }
    }

    @FXML
    public void pdf(ActionEvent event) {
        // Create a new Document
        Document document = new Document();

        try {
            // Create a PdfWriter to write the Document to a file or output stream
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("activite.pdf"));

            // Open the Document
            document.open();

            // Load the background image
            Image bg = Image.getInstance("pdf.png");
            bg.setAbsolutePosition(0, 0); // Set the position of the image to the bottom-left corner of the page
            bg.scaleAbsolute(document.getPageSize()); // Scale the image to fit the page

            // Add the background image to the page
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(bg);

            // Add the content to the page
            Paragraph para = new Paragraph();
            para.setAlignment(Element.ALIGN_CENTER);
            para.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
            para.add("Activité");
            document.add(para);

            float[] columnWidths = {1, 2}; // the widths of the columns in the table
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(new PdfPCell(new Phrase("Titre:", new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase(getAct().getTitre(), new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase("Date Debut:", new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase(getAct().getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase("Date Fin:", new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase(getAct().getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));

// Set the total width of the table
            table.setTotalWidth(document.right() - document.left());

// Calculate the x and y coordinates for the table
            float x = (70+document.right() - document.left() - table.getTotalWidth()) / 2 ;
            float y = document.top() - table.getTotalHeight() - 20;

// Write the table to the PDF document
            table.writeSelectedRows(0, -1, x, y, writer.getDirectContent());

            // Add the table to the Document
         
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
