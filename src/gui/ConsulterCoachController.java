/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ConsulterCoachController implements Initializable {

    @FXML
    private ImageView imagepro;
    @FXML
    private Label nameLabel;
    @FXML
    private Label rolesLabel;
    @FXML
    private VBox vboxcoach;
    @FXML
    private TextField tfRecherche;

    /**
     * Initializes the controller class.
     */
      private List<User> listUser;
    
    private UserService userService = new UserService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                try {
        loadData();
    } catch (SQLException ex) {
        Logger.getLogger(ConsulterCoachController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(ConsulterCoachController.class.getName()).log(Level.SEVERE, null, ex);
    }
  recherche();             
}

private void loadData() throws SQLException, FileNotFoundException {
    listUser = userService.recuperer();
    vboxcoach.getChildren().clear();
    for (User user : listUser) {
        Node node = createEventNode(user);
        if (node != null) {
            vboxcoach.getChildren().add(node);
        }
    }
}
             
       
        

 private Node createEventNode(User event) throws FileNotFoundException {
    // Créer un VBox pour contenir le nom et le prix de l'article
     if(event == null) {
        return null;
    }
     else{
    VBox articleBox = new VBox();
          articleBox.setPrefSize(150, 150);
                articleBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
              
               // File file = new File("C:\\Users\\user\\OneDrive\\Documents\\NetBeansProjects\\PIDEV-MBV2\\src\\uploads\\"+event.getImage_ev());
               // System.out.println(file);
               // Image image = new Image(file.toURI().toString());
                //System.out.println(image);

    // Créer des labels pour le nom et le prix de l'article
            Label namelabel=new Label(event.getLastname());    
            namelabel.setFont(Font.font("Verdana",FontWeight.BOLD, 16));
            namelabel.setAlignment(Pos.CENTER);
            articleBox.getChildren().add(namelabel);
            
            Label diplomelabel=new Label(event.getDiplome());    
           diplomelabel.setFont(Font.font("Verdana",FontWeight.BOLD, 12));
           diplomelabel.setAlignment(Pos.CENTER);
            articleBox.getChildren().add( diplomelabel);
           
            
            Label experiencelabel=new Label(event.getExperience());    
           experiencelabel.setFont(Font.font("Verdana",FontWeight.BOLD, 10));
            experiencelabel.setAlignment(Pos.CENTER);
            articleBox.getChildren().add(experiencelabel);
            
ImageView imageView = new ImageView();               
Image image = new Image("file:src/uploads/" + event.getImage() + ".png");
imageView.setImage(image);
imageView.setFitWidth(150);
imageView.setFitHeight(100);
imageView.setPreserveRatio(true);
articleBox.getChildren().add(imageView);
            Button connectButton = new Button("contacter");
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // TODO: Ajouter le code pour gérer la connexion de l'utilisateur
            }
        });
        
        
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatBot.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the button
            Stage stage = (Stage) connectButton.getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
});
        connectButton.setMaxWidth(Double.MAX_VALUE);
        articleBox.getChildren().add(connectButton);
          
           
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(articleBox);

    // Ajouter un style CSS au VBox pour qu'il soit bien présenté dans le FlowPane
    articleBox.setStyle("-fx-padding: 10px; -fx-background-color: #f2f2f2; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #cccccc; -fx-border-width: 1px;");

    // Définir les contraintes de taille pour le VBox et l'ImageView
    articleBox.setPrefWidth(150);
    articleBox.setMaxWidth(150);
    vboxcoach.getChildren().add(articleBox);
  vboxcoach.setMargin(articleBox, new Insets(5, 5, 5, 5));

    // Retourner le StackPane contenant l'ImageView et le VBox
    return stackPane;
     }
}
 
 
 @FXML
private void recherche() {
    UserService sp = new UserService();  
    tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
        List<User> eventrecherche = null;
        try {
            eventrecherche = sp.recuperer().stream()
                    .filter(evenement ->
                            evenement.getLastname().toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
        } catch (SQLException ex) {
            Logger.getLogger(ConsulterCoachController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Vider le FlowPane actuel pour afficher les evenements filtrés
        vboxcoach.getChildren().clear();
        for (User evenement :  eventrecherche ) {
            Node articleNode = null;
            try {
                articleNode = createEventNode(evenement);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConsulterCoachController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (articleNode != null) {
               vboxcoach.getChildren().add(articleNode); // ajouter le nouveau noeud dans le FlowPane
            }
        }
       vboxcoach.layout();
    });
}
 
 
 
     @FXML
void chatbot(MouseEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatBot.fxml"));
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
  @FXML
    public void SwitchToProfile(ActionEvent event) throws IOException{
     Parent tableViewParent = FXMLLoader.load(getClass().getResource("UserProfilee.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(tableViewScene);
}
    
 
}
      
        
