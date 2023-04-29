package entities;

import com.itextpdf.text.Document;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import services.UserService;

/**
 *
 * @author AZAYEZ BINSA
 */
public class Pdf {

      public void GeneratePdf(String filename, User p, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document() {
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();

        UserService us = new UserService();
        document.add(new Paragraph("            le nom :"+p.getLastname()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------"));

        document.add(new Paragraph("email :" + p.getEmail()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("roles :" + p.getRoles()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("diplome:" + p.getDiplome()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("experience :" + p.getExperience()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("image :" + p.getImage()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("image :" + p.getImage()));
        document.add(new Paragraph("                      "));
       

        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph("                             Sportifly                   "));
        document.close();
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }

}
