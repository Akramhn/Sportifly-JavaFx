/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Stars;
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
 * @author wadah
 */
public class StarsService implements IServiceOffre<Stars> {

    Connection cnx;

    public StarsService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Stars t) throws SQLException {
        // Check if a rating already exists for the user and offer combination
        String checkReq = "SELECT * FROM stars WHERE u_id_id = ? AND id_offre_id = ?";
        PreparedStatement checkPs = cnx.prepareStatement(checkReq);
        checkPs.setInt(1, t.getUser_id());
        checkPs.setInt(2, t.getId_offre());
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            // A rating already exists, so update it
            String updateReq = "UPDATE stars SET rate_index = ? WHERE u_id_id = ? AND id_offre_id = ?";
            PreparedStatement updatePs = cnx.prepareStatement(updateReq);
            updatePs.setInt(1, t.getRate_index());
            updatePs.setInt(2, t.getUser_id());
            updatePs.setInt(3, t.getId_offre());
            updatePs.executeUpdate();
        } else {
            // No rating exists, so insert a new one
            String insertReq = "INSERT INTO stars(u_id_id, id_offre_id, rate_index) VALUES (?, ?, ?)";
            PreparedStatement insertPs = cnx.prepareStatement(insertReq);
            insertPs.setInt(1, t.getUser_id());
            insertPs.setInt(2, t.getId_offre());
            insertPs.setInt(3, t.getRate_index());
            insertPs.executeUpdate();
        }
    }

    @Override
    public void modifier(Stars t) throws SQLException {
        String req = "UPDATE stars SET rate_index = ? WHERE u_id_id = ? AND id_offre_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, t.getRate_index());
        ps.setInt(2, t.getUser_id());
        ps.setInt(3, t.getId_offre());

        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM stars WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Stars> recuperer() throws SQLException {
        List<Stars> starsList = new ArrayList<>();
        String req = "SELECT * FROM stars";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Stars s = new Stars();
            s.setId(rs.getInt("id"));
            s.setUser_id(rs.getInt("u_id_id"));
            s.setId_offre(rs.getInt("id_offre_id"));
            s.setRate_index(rs.getInt("rate_index"));

            starsList.add(s);
        }
        return starsList;
    }

    public int getRatingForOfferAndUser(int userId, int offreId) throws SQLException {
        String req = "SELECT rate_index FROM stars WHERE u_id_id = ? AND id_offre_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, userId);
        ps.setInt(2, offreId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("rate_index");
        } else {
            return -1;
        }
    }

    public double calculateMoyenne(int id) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("select avg (rate_index) from stars where id_offre_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0;
    }

}
