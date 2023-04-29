/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Categorie;
import entities.Reclamations;
import entities.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import util.MyDB;

/**
 *
 * @author hadjn
 */
public class CategorieService implements IService<Categorie> {

    Connection cnx;

    public CategorieService() {
        cnx = MyDB.getInstance().getCnx();
    }

 
    public void ajouter(Categorie t) throws SQLException {
         String req = "insert into categorie_activite set nom = ?, description = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

       ps.setString(1, t.getNom());
        ps.setString(2, t.getDescription());
        
    

        ps.executeUpdate();
        
    }

   
    public void modifier(Categorie t) throws SQLException {
       String req = "update categorie_activite set nom = ?, description = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, t.getNom());
        ps.setString(2, t.getDescription());
        ps.setInt(3, t.getId());

        ps.executeUpdate();
    }

 
    public boolean supprimer(Categorie c) throws SQLException {
        boolean test = false;
        try {

            String req = "delete from categorie_activite  where id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, c.getId());
            ps.executeUpdate();
            test = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

    @Override
    public List<Categorie> recuperer() throws SQLException {
        List<Categorie> activitees = new ArrayList<>();
        String req = "select * from categorie_activite";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Categorie p = new Categorie();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));

            activitees.add(p);
        }
        return activitees;
    }
    
    public ObservableList<Categorie> recuperer2() throws SQLException {
    List<Categorie> activitees = new ArrayList<>();
    String req = "SELECT DISTINCT c.*, a.*\n" +
"FROM categorie_activite c\n" +
"INNER JOIN activiter a ON c.id = a.ref_categ_id\n" +
"GROUP BY c.id;";
    Statement st = cnx.createStatement();
    ResultSet rs = st.executeQuery(req);
    while (rs.next()) {
        Categorie p = new Categorie();
        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom"));
        p.setDescription(rs.getString("description"));

        activitees.add(p);
    }
    return FXCollections.observableArrayList(activitees);
}


    public Categorie recupererBynom(String nom) throws SQLException {
        String req = "select * from categorie_activite where nom = ?";
        PreparedStatement st = cnx.prepareStatement(req);

        st.setString(1, nom);
        ResultSet rs = st.executeQuery();
        Categorie p = new Categorie();
        rs.next();
        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom"));
        p.setDescription(rs.getString("description"));

        return p;
    }

    public Categorie recupererByid(int id) throws SQLException {
        String req = "select * from categorie_activite where id = ?";
        PreparedStatement st = cnx.prepareStatement(req);

        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Categorie p = new Categorie();
        rs.next();
        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom"));
        p.setDescription(rs.getString("description"));

        return p;
    }

public List<String> recuperernom() throws SQLException {
        List<String> activitees = new ArrayList<>();
        String req = "select nom from categorie_activite";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            String n;
            n = rs.getString("nom");

            activitees.add(n);
        }
        return activitees;
    }

    public int recupererIdByNom(String nom) throws SQLException {
        String req = "SELECT id FROM categorie_activite WHERE nom = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, nom);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("No categorie with name " + nom + " found.");
        }
    }

    public int getReservationCountByCategory(int categoryId) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM reservation "
                + "WHERE id_offre_id IN (SELECT id FROM offre WHERE id_category_id = ?)";
        PreparedStatement st = cnx.prepareStatement(query);
        st.setInt(1, categoryId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        } else {
            throw new SQLException("Erro");
        }

        return count;
    }

    @Override
    public void adduser(Categorie t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(Categorie t) throws SQLException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registercoach(Categorie t) throws SQLException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ModifierUser(Categorie t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SupprimerUser(Categorie t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reclamations> recupererUser(Categorie t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> Show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
