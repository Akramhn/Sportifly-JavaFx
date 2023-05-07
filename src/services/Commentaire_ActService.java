/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Commentaire_Act;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.MyDB;

/**
 *
 * @author Nour moutii
 */
public class Commentaire_ActService implements IServiceAct<Commentaire_Act> {
    
    Connection cnx;
    
    public Commentaire_ActService() {
        cnx = MyDB.getInstance().getCnx();
    }
    
    @Override
    public void ajouter(Commentaire_Act t) throws SQLException {
        String req = "insert into commentaire_Act (id_actualite_id,id_user_id,contenu,date) values(?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ps.setInt(1, t.getId_actualite_id());
        ps.setString(3, filtrerGrosMots(t.getContenu()));
        ps.setInt(2, t.getId_user());
        ps.setTimestamp(4, Timestamp.valueOf(t.getDate()));
        
        ps.executeUpdate();
    }
    
    @Override
    public void modifier(Commentaire_Act t) throws SQLException {
        String req = "update commentaire_Act set contenu = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ps.setString(1, t.getContenu());
        ps.setInt(2, t.getId());
        
        ps.executeUpdate();
    }
    
    @Override
    public void supprimer(Commentaire_Act t) throws SQLException {
        
    }
    
    public boolean supprimertest(Commentaire_Act t) throws SQLException {
        
        boolean test = false;
        try {
            
            String req = "delete from commentaire_Act where id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            
            ps.setInt(1, t.getId());
            
            ps.executeUpdate();
            test = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
        
    }
    
    public ObservableList<Commentaire_Act> afficher() throws SQLException {
        ObservableList<Commentaire_Act> comments = FXCollections.observableArrayList();
        String req = "select * from commentaire_Act";
        try (Statement statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(req)) {
            while (resultSet.next()) {
                Commentaire_Act p = new Commentaire_Act();
                p.setId(resultSet.getInt("id"));
                p.setId_actualite_id(resultSet.getInt("id_actualite_id"));
                p.setId_user(resultSet.getInt("id_user_id"));
                p.setContenu(resultSet.getString("contenu"));
                p.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                comments.add(p);
            }
        }
        return comments;
    }
    
    public String filtrerGrosMots(String texte) {
        String[] grosMots = {"merde", "putain", "enculé", "connard", "salope", "bordel", "bite", "cul"};
        for (String mot : grosMots) {
            texte = texte.replaceAll("(?i)" + mot, "***");
        }
        return texte;
    }
    
    public List<Commentaire_Act> recupererById(int id) throws SQLException {
        String req = "select * from commentaire_Act where id_user_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        List<Commentaire_Act> comments = new ArrayList<>();
        while (rs.next()) {
            Commentaire_Act p = new Commentaire_Act();
            p.setId(rs.getInt("id"));
            p.setId_actualite_id(rs.getInt("id_actualite_id"));
            p.setId_user(rs.getInt("id_user_id"));
            p.setContenu(rs.getString("contenu"));
            p.setDate(rs.getTimestamp("date").toLocalDateTime());
            
            comments.add(p);
        }
        return comments;
    }
    
    @Override
    public List<Commentaire_Act> recuperer() throws SQLException {
        List<Commentaire_Act> ommentaire_Act = new ArrayList<>();
        String req = "select * from commentaire_Act";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Commentaire_Act p = new Commentaire_Act();
            p.setId(rs.getInt("id"));
            p.setId_actualite_id(rs.getInt("id_actualite_id"));
            p.setId_user(rs.getInt("id_user_id"));
            p.setContenu(rs.getString("contenu"));
            p.setDate(rs.getTimestamp("date").toLocalDateTime());
            
            ommentaire_Act.add(p);
        }
        return ommentaire_Act;
    }
    
    public List<Commentaire_Act> getCommentairesByIdActualite(int id_actualite) {
        List<Commentaire_Act> commentaires = new ArrayList<>();
        // Assuming that you have a database connection object called "connection"
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM commentaire_act WHERE id_actualite_id = ?");
            statement.setInt(1, id_actualite);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Commentaire_Act commentaire = new Commentaire_Act();
                commentaire.setId(result.getInt("id"));
                commentaire.setId_actualite_id(result.getInt("id_actualite_id"));
                commentaire.setContenu(result.getString("contenu"));
                commentaire.setId_user(result.getInt("id_user_id"));
                
                Timestamp timestamp = result.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                commentaire.setDate(localDateTime);
                // You can add more fields as needed
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }
    
}
