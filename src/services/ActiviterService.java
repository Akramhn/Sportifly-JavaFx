/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Activiter;
import entities.Reclamations;
import entities.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyDB;

/**
 *
 * @author Skand
 */
public class ActiviterService implements IService<Activiter> {

    Connection cnx;

    public ActiviterService() {
        cnx = MyDB.getInstance().getCnx();
    }

   
    public void ajouter(Activiter t) throws SQLException {
        /*    String req = "insert into activiter(ref_categ_id,id_user_id,titre,date_debut,date_fin) values(" + t.getRef_categ() + "," + t.getId_user() + ",'" + t.getTitre()+ "'," + t.getDate_debut() + "," + t.getDate_fin() + ")";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);*/
        
        
         String req = "insert into activiter set ref_categ_id = ?, id_user_id = ?, titre = ?, date_debut = ?, date_fin = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

       ps.setInt(1, t.getRef_categ());
        ps.setInt(2, t.getId_user());
        ps.setString(3, t.getTitre());
        ps.setTimestamp(4, t.getDate_debut());
         ps.setTimestamp(5, t.getDate_fin());
    

        ps.executeUpdate();
        
        

    }

    
    public void modifier(Activiter t) throws SQLException {
        String req = "update activiter set ref_categ_id = ?,  titre = ?, date_debut = ?, date_fin = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);

       ps.setInt(1, t.getRef_categ());
    
        ps.setString(2, t.getTitre());
        ps.setTimestamp(3, t.getDate_debut());
         ps.setTimestamp(4, t.getDate_fin());
        ps.setInt(5, t.getId());

        ps.executeUpdate();

    }


    public boolean supprimer(Activiter a)  {
           boolean  test=false;
        try {
       
                    String req = "delete from activiter  where id = ?";
                    PreparedStatement ps = cnx.prepareStatement(req);
                    
                    ps.setInt(1, a.getId());
                    ps.executeUpdate();
                    test=true;
        } catch (SQLException ex) {
            Logger.getLogger(ActiviterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    
    
    public boolean supprimer(int id)  {
           boolean  test=false;
        try {
       
                    String req = "delete from activiter  where id = ?";
                    PreparedStatement ps = cnx.prepareStatement(req);
                    
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    test=true;
        } catch (SQLException ex) {
            Logger.getLogger(ActiviterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    

    @Override
    public List<Activiter> recuperer() throws SQLException {
        List<Activiter> activitees = new ArrayList<>();
        String req = "select * from activiter";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Activiter p = new Activiter();
            p.setId(rs.getInt("id"));
            p.setRef_categ(rs.getInt("ref_categ_id"));
            p.setId_user(rs.getInt("id_user_id"));
            p.setTitre(rs.getString("titre"));
            p.setDate_debut(rs.getTimestamp("date_debut"));
            p.setDate_fin(rs.getTimestamp("date_fin"));

            activitees.add(p);
        }
        return activitees;
    }

    public List<Activiter> recupererById(int id) throws SQLException {
        String req = "select * from activiter where id_user_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
         List<Activiter> activitees = new ArrayList<>();
          while (rs.next()) {
            Activiter p = new Activiter();
            p.setId(rs.getInt("id"));
            p.setRef_categ(rs.getInt("ref_categ_id"));
            p.setId_user(rs.getInt("id_user_id"));
            p.setTitre(rs.getString("titre"));
            p.setDate_debut(rs.getTimestamp("date_debut"));
            p.setDate_fin(rs.getTimestamp("date_fin"));

            activitees.add(p);
        }
        return activitees;
    }

    
    
    
    
     public List<Activiter> getCalendarActivitiesMonth(ZonedDateTime mth) throws SQLException {
        String req = "select * from activiter where Month(date_debut) = ? and year(date_debut)=?";
         System.out.println(mth.getMonthValue());
        PreparedStatement st = cnx.prepareStatement(req);
        st.setObject(1, mth.getMonthValue());
        st.setObject(2, mth.getYear());
        ResultSet rs = st.executeQuery();
         List<Activiter> activitees = new ArrayList<>();
          while (rs.next()) {
            Activiter p = new Activiter();
            p.setId(rs.getInt("id"));
            p.setRef_categ(rs.getInt("ref_categ_id"));
            p.setId_user(rs.getInt("id_user_id"));
            p.setTitre(rs.getString("titre"));
            p.setDate_debut(rs.getTimestamp("date_debut"));
            p.setDate_fin(rs.getTimestamp("date_fin"));

            activitees.add(p);
        }
        return activitees;
    }

    @Override
    public void adduser(Activiter t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(Activiter t) throws SQLException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registercoach(Activiter t) throws SQLException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ModifierUser(Activiter t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SupprimerUser(Activiter t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reclamations> recupererUser(Activiter t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> Show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
