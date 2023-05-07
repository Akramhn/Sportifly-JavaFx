/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Offre;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.MyDB;

/**
 *
 * @author admin
 */
public class OffreService implements IServiceOffre<Offre> {

    Connection cnx;

    public OffreService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Offre t) throws SQLException {
        String req = "INSERT INTO offre(id_user_id, id_category_id, date, affiche, prix, description, nbplace) VALUES ('" + t.getId_user() + "','" + t.getId_categroy() + "', CURRENT_TIMESTAMP, '" + t.getAffiche() + "', " + t.getPrix() + ", '" + t.getDescription() + "', " + t.getNb_place() + ")";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Offre t) throws SQLException {
        String req = "UPDATE offre SET id_user_id = ?, id_category_id = ?, date = ?, affiche = ?, prix = ?, description = ?, nbplace = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, t.getId_user());
        ps.setInt(2, t.getId_categroy());
        ps.setDate(3, t.getDate());
        ps.setString(4, t.getAffiche());
        ps.setFloat(5, t.getPrix());
        ps.setString(6, t.getDescription());
        ps.setInt(7, t.getNb_place());
        ps.setInt(8, t.getId());

        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM offre WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean supprimer1(Offre o) throws SQLException {
        boolean test = false;
        try {
            String req = "DELETE FROM offre WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, o.getId());
            ps.executeUpdate();
            test = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;

    }

    public List<Offre> recuperer(int id_user) throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM offre o WHERE NOT EXISTS (SELECT 1 FROM reservation r WHERE r.id_user_id = " + id_user + " AND r.id_offre_id = o.id) order by date DESC";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Offre o = new Offre();
            o.setId(rs.getInt("id"));
            o.setId_user(rs.getInt("id_user_id"));
            o.setId_categroy(rs.getInt("id_category_id"));
            o.setDate(rs.getDate("date"));
            o.setAffiche(rs.getString("affiche"));
            o.setPrix(rs.getFloat("prix"));
            o.setDescription(rs.getString("description"));
            o.setNb_place(rs.getInt("nbplace"));

            offres.add(o);
        }
        return offres;
    }

    @Override
    public List<Offre> recuperer() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM offre";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Offre o = new Offre();
            o.setId(rs.getInt("id"));
            o.setId_user(rs.getInt("id_user_id"));
            o.setId_categroy(rs.getInt("id_category_id"));
            o.setDate(rs.getDate("date"));
            o.setAffiche( rs.getString("affiche"));
            o.setPrix(rs.getFloat("prix"));
            o.setDescription(rs.getString("description"));
            o.setNb_place(rs.getInt("nbplace"));

            offres.add(o);
        }
        return offres;
    }

    public Offre recupererById(int id) throws SQLException {
        String req = "SELECT * FROM offre WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Offre o = new Offre();
        if (rs.next()) {
            o.setId(rs.getInt("id"));
            o.setId_user(rs.getInt("id_user_id"));
            o.setId_categroy(rs.getInt("id_category_id"));
            o.setDate(rs.getDate("date"));
            o.setAffiche(rs.getString("affiche"));
            o.setPrix(rs.getFloat("prix"));
            o.setDescription(rs.getString("description"));
            o.setNb_place(rs.getInt("nbplace"));
        }
        return o;
    }

    public String recupererNomCategorie(int id) throws SQLException {
        String nomCategorie = "";
        Connection cnx = MyDB.getInstance().getCnx();
        String query = "SELECT nom FROM categorie_activite WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            nomCategorie = rs.getString("nom");
        }
        pst.close();
        rs.close();
        return nomCategorie;
    }

    public List<Offre> recupererOffById(int id) throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM offre WHERE id_user_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Offre o = new Offre();
            o.setId(rs.getInt("id"));
            o.setId_user(rs.getInt("id_user_id"));
            o.setId_categroy(rs.getInt("id_category_id"));
            o.setDate(rs.getDate("date"));
            o.setAffiche(rs.getString("affiche"));
            o.setPrix(rs.getFloat("prix"));
            o.setDescription(rs.getString("description"));
            o.setNb_place(rs.getInt("nbplace"));

            offres.add(o);
        }
        return offres;
    }

    public Offre recupererBydesc(String desc) throws SQLException {
        String req = "select * from offre where description = ?";
        PreparedStatement st = cnx.prepareStatement(req);

        st.setString(1, desc);
        ResultSet rs = st.executeQuery();
        Offre o = new Offre();
        rs.next();
        o.setId(rs.getInt("id"));
        o.setId_user(rs.getInt("id_user_id"));
        o.setId_categroy(rs.getInt("id_category_id"));
        o.setDate(rs.getDate("date"));
        o.setAffiche( rs.getString("affiche"));
        o.setPrix(rs.getFloat("prix"));
        o.setDescription(rs.getString("description"));
        o.setNb_place(rs.getInt("nbplace"));

        return o;
    }

}
