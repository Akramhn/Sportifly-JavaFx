/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.UserService;
import util.MyDB;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import static util.SessionManager.getId;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.TextField;
import static util.SessionManager.getLastname;
import gui.CaptureEcran;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import gui.CaptureEcran;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import javafx.scene.layout.AnchorPane;
import org.codehaus.plexus.util.xml.XmlStreamWriter;
import javax.xml.stream.*;



/**
 * FXML Controller class
 *
 * @author user
 */
public class AdminController implements Initializable {

    @FXML
    private TextField Recherche_User;
    @FXML
    private TableView<User> tvUsers;
    @FXML
    private TableColumn<?, ?> emailUser;
    @FXML
    private TableColumn<?, ?> lastnameuser;
    @FXML
    private TableColumn<?, ?> roleuser;
    @FXML
    private TableColumn<?, ?> etatuser;
    @FXML
    private TableColumn<?, ?> experienceuser;

    @FXML
    private TableColumn<?, ?> diplomeuser;
    @FXML
    private Button refresh;
    private Button modifier;
    private Button logout;
  private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
   int index = -1;

    private TextField names;

    private TextField roless;

    private ImageView userShowImg;
    private TextField emails;
    private TextField etats;
    private TextField experiences;
  
  @FXML
private TableColumn<User, String> imageuser;
          @FXML
    private TableColumn<?, ?> is_blocked;

private User selectedUser;
  
   @FXML
    private Button statbtn;
    
   @FXML
    private Button excelbtn;
   
  static User user;
  @FXML
    private Button capbtn;
  @FXML
    private AnchorPane monAnchorPane;
 private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button utilisateur;
    @FXML
    private Button MesressA;
    @FXML
    private Button MesOffA;
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

  
 @Override
public void initialize(URL url, ResourceBundle rb) {
    ObservableList<User> list = getUserList();
    emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
    roleuser.setCellValueFactory(new PropertyValueFactory<>("roles"));
    lastnameuser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    diplomeuser.setCellValueFactory(new PropertyValueFactory<>("diplome"));
    experienceuser.setCellValueFactory(new PropertyValueFactory<>("experience"));
    imageuser.setCellValueFactory(new PropertyValueFactory<>("image"));
    etatuser.setCellValueFactory(new PropertyValueFactory<>("etat"));
      is_blocked.setCellValueFactory(new PropertyValueFactory<>("is_blocked"));

    // Set up custom cell factory for image column
    imageuser.setCellFactory(column -> {
        TableCell<User, String> cell = new TableCell<User, String>() {
            private final ImageView imageView = new ImageView();
            
            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image("file:src/uploads/" + imagePath + ".png");
                     imageView.setImage(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                setGraphic(imageView);
                }
            }
        };
        return cell;
    });  
    
    
    tvUsers.setItems(list);
    search_user();
 capbtn.setOnAction(event -> {
        CaptureEcran cap = new CaptureEcran();
    try {
        cap.capturer(monAnchorPane);
        System.out.println("La capture d'écran a été générée avec succès !");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Capture d'écran");
        alert.setHeaderText("Capture d'écran générée avec succès !");
        alert.showAndWait();
    } catch (Exception ex) {
        System.out.println("Erreur lors de la capture d'écran : " + ex.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur lors de la capture d'écran !");
        alert.setContentText("Une erreur est survenue lors de la capture d'écran : " + ex.getMessage());
        alert.showAndWait();
    }
});
  
    
}

 @FXML
    public void getSelected(MouseEvent event) throws SQLException {
        index = tvUsers.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

            return;
        }
        emails.setText(emailUser.getCellData(index).toString());
         roless.setText(roleuser.getCellData(index).toString());
       names.setText(lastnameuser.getCellData(index).toString());
                etats.setText(etatuser.getCellData(index).toString());
                  experiences.setText(experienceuser.getCellData(index).toString());
                
        userShowImg.setImage(new Image("file:src/uploads/" + imageuser.getCellData(index).toString() + ".png"));
        System.out.println(imageuser.getCellData(index).toString());    
    }
   
  public  ObservableList<User> getUserList() {
         cnx = MyDB.getInstance().getCnx();
        
        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
                String query2="SELECT  *from user ";
                PreparedStatement smt = cnx.prepareStatement(query2);
                User user;
                ResultSet rs= smt.executeQuery();
            while(rs.next()){
user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("roles"), rs.getString("lastname"), rs.getString("diplome"), rs.getString("experience"), rs.getString("image"), rs.getBoolean("is_blocked"), rs.getBoolean("is_approved"), rs.getString("etat"));
                UserList.add(user);
            }
                System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;
   
    }


     private void refresh(){
    ObservableList<User> list = getUserList();
    emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
    roleuser.setCellValueFactory(new PropertyValueFactory<>("roles"));
    lastnameuser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    diplomeuser.setCellValueFactory(new PropertyValueFactory<>("diplome"));
    experienceuser.setCellValueFactory(new PropertyValueFactory<>("experience"));
    imageuser.setCellValueFactory(new PropertyValueFactory<>("image"));
    etatuser.setCellValueFactory(new PropertyValueFactory<>("etat"));
          is_blocked.setCellValueFactory(new PropertyValueFactory<>("is_blocked"));


    // Set up custom cell factory for image column
    imageuser.setCellFactory(column -> {
        TableCell<User, String> cell = new TableCell<User, String>() {
            private final ImageView imageView = new ImageView();
            
            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image("file:src/uploads/" + imagePath + ".png");
                     imageView.setImage(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                setGraphic(imageView);
                }
            }
        };
        return cell;
    });  
    
    tvUsers.setItems(list);
    search_user();
 
       
    }
     
      @FXML
public void showRec() {
    ObservableList<User> list = getUserList();
         emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
         roleuser.setCellValueFactory(new PropertyValueFactory<>("roles"));
         lastnameuser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
         diplomeuser.setCellValueFactory(new PropertyValueFactory<>("diplome"));
         experienceuser.setCellValueFactory(new PropertyValueFactory<>("experience"));
         imageuser.setCellValueFactory(new PropertyValueFactory<>("image"));
        etatuser.setCellValueFactory(new PropertyValueFactory<>("etat"));
              is_blocked.setCellValueFactory(new PropertyValueFactory<>("is_blocked"));


TableColumn<User, Void> colBtn = new TableColumn("Actions");

Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell;
                cell = new TableCell<User, Void>() {
                    
                    private final Button btnDelete = new Button("Supprimer");
                    private final Button btnEdit = new Button("Modifier");
                    private final Button btnBlock = new Button("Bloquer");
                    private final Button btndebBlock = new Button("Debloquer");
                   // private final Button btnsms = new Button("envoyer sms");
                    
                    
                    {
                        btnDelete.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            Supprimer(data);
                        });
                        
                        btnEdit.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            Modifierutilisateur(data);
                            refresh();
                        });
                        btnBlock.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            UserService userService = new UserService();
                            userService.bloquerUtilisateur(user);
                            refresh();
                        });
                        btndebBlock.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            UserService userService = new UserService(); // Créer une instance de UserService
                            userService.debloquerUtilisateur(data);
                            refresh();
                            SMS();
                            refresh();
                        });
                         
                        
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox();
                            hbox.getChildren().addAll(btnDelete, btnEdit,btnBlock, btndebBlock);
                            hbox.setSpacing(5);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

colBtn.setCellFactory(cellFactory);

tvUsers.setItems(list);
tvUsers.getColumns().addAll(colBtn);
   search_user();

  statbtn.setOnAction((ActionEvent event) -> {
           try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statuser.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    });  
   



}
     
  private void Supprimer(User user) {
    UserService u=new UserService();
        try {
            u.SupprimerUser(user);
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refresh();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Sportifly :: Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Utilisateur supprimé");
    alert.showAndWait();
       search_user();
}
  
   private void Modifierutilisateur(User u) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUser.fxml"));
    Parent root;
    try {
        root = loader.load();
    } catch (IOException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        return;
        
    }
    
    ModifierUserController controller = loader.getController();
    
    controller.setUser(u);
    
    Stage stage = new Stage();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
   
   
   
    void showSelectedEvent(MouseEvent event) throws SQLException {
        User e = tvUsers.getSelectionModel().getSelectedItem();
        if (e != null) {

            System.out.println("file:src/uploads/" + e.getImage() + ".png");
            userShowImg.setImage(new Image("file:src/uploads/" + e.getImage() + ".png"));
            //modifier.setVisible(true);
            modifier.setDisable(false);
            // if(e.getUserId() == uid)
            {
               // btnEventDelete.setVisible(true);
                //btnEventDelete.setDisable(false);
                // btnEventEdit.setVisible(true);
                // btnEventEdit.setDisable(false);
                //   }else{
               /* btnEventDelete.setVisible(false);
                 btnEventDelete.setDisable(true);
                 btnEventEdit.setVisible(false);
                 btnEventEdit.setDisable(true);*/
           // }

            //EventPieChart.setVisible(false);
            }
        }
    }
    
    
     void search_user() {

         emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
             roleuser.setCellValueFactory(new PropertyValueFactory<>("roles"));
         lastnameuser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
           diplomeuser.setCellValueFactory(new PropertyValueFactory<>("diplome"));
               experienceuser.setCellValueFactory(new PropertyValueFactory<>("experience"));
                  imageuser.setCellValueFactory(new PropertyValueFactory<>("image"));
        etatuser.setCellValueFactory(new PropertyValueFactory<>("etat"));
        cnx = MyDB.getInstance().getCnx();

        ObservableList<User> dataList = getUserList();

        tvUsers.setItems(dataList);

        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);
        Recherche_User.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getLastname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (person.getRoles().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password*/
                } else if (String.valueOf(person.getEmail()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvUsers.comparatorProperty());
        tvUsers.setItems(sortedData);

    }
      private double x = 0;
    private double y = 0;
    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } 
    
    
   /*  @FXML
    private void pdf_user(ActionEvent event) {
     System.out.println("hello");
        try{
            
JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\user\\Documents\\NetBeansProjects\\Sportifly\\src\\gui\\login\\report.jrxml");
       
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
           
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, null, cnx);
           
            JasperViewer viewer = new JasperViewer(jPrint, false);
           
            viewer.setTitle("Liste des Utilistaeurs");
            viewer.show();
            System.out.println("hello");
            
           
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
*/
     void genererPDF(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            UserService as = new  UserService();
            List<User> userList = as. Show();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(System.getProperty("user.dir") + "gui/imgLogo.png");

                // Positionner l'image en haut à gauche
                image.setAbsolutePosition(5, document.getPageSize().getHeight() - 120);

                // Modifier la taille de l'image
                image.scaleAbsolute(150, 150);

                // Ajouter l'image au document
                document.add(image);

                // Créer une police personnalisée pour la date
                Font fontDate = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                BaseColor color = new BaseColor(50, 187, 111); // Rouge: 50, Vert: 187, Bleu: 111
                fontDate.setColor(color); // Définir la couleur de la police

             
      

                // Créer une police personnalisée
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);
                BaseColor titleColor = new BaseColor(67, 136, 43); //
                font.setColor(titleColor);

                // Ajouter le contenu au document
                Paragraph title = new Paragraph("Liste des utilisateurs", font);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingBefore(50); // Ajouter une marge avant le titre pour l'éloigner de l'image
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(5); // 5 colonnes pour les 5 attributs des produits
                table.setWidthPercentage(100);
                table.setSpacingBefore(30f);
                table.setSpacingAfter(30f);

                // Ajouter les en-têtes de colonnes
                Font hrFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
                BaseColor hrColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hrColor);

                PdfPCell cell1 = new PdfPCell(new Paragraph("ID", hrFont));
                BaseColor bgColor = new BaseColor(222, 254, 230);
                cell1.setBackgroundColor(bgColor);
                cell1.setBorderColor(titleColor);
                cell1.setPaddingTop(20);
                cell1.setPaddingBottom(20);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell2 = new PdfPCell(new Paragraph("Email", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("Lastname", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                
                
                  PdfPCell cell4 = new PdfPCell(new Paragraph("diplome", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                  PdfPCell cell5 = new PdfPCell(new Paragraph("experience", hrFont));
                cell5.setBackgroundColor(bgColor);
                cell5.setBorderColor(titleColor);
                cell5.setPaddingTop(20);
                cell5.setPaddingBottom(20);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                  table.addCell(cell4);
                    table.addCell(cell5);
             

                Font hdFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                BaseColor hdColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hdColor);
                // Ajouter les données des produits
                for (User us : userList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(String.valueOf(us.getId()), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(us.getEmail(), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(String.valueOf(us.getLastname()), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);
                    
                    PdfPCell cellR4 = new PdfPCell(new Paragraph(String.valueOf(us.getDiplome()), hdFont));
                    cellR4.setBorderColor(titleColor);
                    cellR4.setPaddingTop(10);
                    cellR4.setPaddingBottom(10);
                    cellR4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR4);
                      PdfPCell cellR5 = new PdfPCell(new Paragraph(String.valueOf(us.getExperience()), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);

                }
                table.setSpacingBefore(20);
                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    
    }
  void statuser(MouseEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("statuser.fxml"));
    Parent root = null;
    try {
        root = (Parent) loader.load();
    } catch (IOException ex) {
        // Handle exception
    }
    Stage stage = new Stage();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}


  
void SMS(){
    String ACCOUNT_SID = "ACed59c2efc292602af3f856aad90c6bb6";
    String AUTH_TOKEN = "92cd551a97f0c569558593c3b5a99d28";
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
UserService user =new UserService();
    String recipientNumber = "+21624712880";
    String message = "Bonjour vous pouvez accéder maintenant à votre compte. Merci de votre fidélité et à bientôt chez Sportifly. Cordialement, Sportifly.";
    Message twilioMessage = Message.creator(
        new PhoneNumber(recipientNumber),
        new PhoneNumber("+12762849300"),
        message)
        .create();
        
    System.out.println("SMS envoyé : " + twilioMessage.getSid());
}





public  void capturer(Node node) {
        // Récupération de la scène contenant le nœud à capturer
        Scene scene = node.getScene();

        // Création d'un SnapshotParameters pour capturer seulement le contenu de l'AnchorPane
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Capture d'écran du contenu de l'AnchorPane uniquement
        WritableImage image = node.snapshot(parameters, null);

        // Enregistrement de la capture
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la capture d'écran");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers PNG", "*.png"),
                new FileChooser.ExtensionFilter("Tous les fichiers", ".")
        );
        File fichier = fileChooser.showSaveDialog(new Stage());
        if (fichier != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fichier);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



 
    @FXML
    void stataff(ActionEvent event) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("statuser.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
  @FXML
  private void Excel(ActionEvent event) throws IOException, SQLException {
        Writer writer = null;
                ObservableList<User> list = getUserList();
         try {
            //badel path fichier excel
            File file = new File("C:\\Users\\user\\Documents\\NetBeansProjects\\Sportifly\\src\\gui\\User.csv");
            writer = new BufferedWriter(new FileWriter(file));
            
            for (User user : list) {

String text = user.getEmail() + "," + user.getRoles() + "," + user.getLastname() + "\n";
                System.out.println(text);
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
             writer.close();
             Alert alert= new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("excel");
        alert.setHeaderText(null);
        alert.setContentText("!!!excel exported!!!");
        alert.showAndWait();
        }
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
    
    
  
  
    
    









