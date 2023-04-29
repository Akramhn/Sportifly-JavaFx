/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamations;
import java.sql.Connection;
import entities.User;
import entities.roles;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.MyDB;
import static util.SessionManager.setRoles;
import org.mindrot.jbcrypt.BCrypt;
import util.SessionManager;
import static util.SessionManager.getId;

/**
 *
 * @author user
 */
public class UserService implements IService<User> {

    Connection cnx;
    Statement stm;

    public UserService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override

    public void adduser(User t) throws SQLException {
        String qry = "INSERT INTO `user`(`email`, `password`, `lastname`, `image`) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = cnx.prepareStatement(qry);
        stmt.setString(1, t.getEmail());
        stmt.setString(2, t.getPassword());
        stmt.setString(3, t.getLastname());
        stmt.setString(4, t.getImage());
        stmt.executeUpdate();
    }

    @Override
    public void SupprimerUser(User t) throws SQLException {
        try {
            String requete = "delete from user where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();

            System.out.println("Utlisateur est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> recuperer() throws SQLException {

        try {
            List<User> users = new ArrayList<>();
            String req = "SELECT lastname,experience,diplome FROM user WHERE roles=\"[\\\"Role_Coach\\\"]\"";
            // Here, we are using the JSON_EXTRACT function to extract the 'role' field from the JSON object
            // stored in the 'Role' column and check if it equals 'Role_Coach'

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                // p.setId(rs.getInt("id"));
                // p.setEmail(rs.getString("email"));
                //p.setRoles(rs.getString("roles"));
                // p.setPassword(rs.getString("password"));
                p.setLastname(rs.getString("lastname"));
                p.setDiplome(rs.getString("diplome"));
                p.setExperience(rs.getString("experience"));
                //p.setImage(rs.getString("image"));
                // p.setIs_blocked(rs.getBoolean("is_blocked"));
                //p.setIs_approved(rs.getBoolean("is_approved"));
                // p.setEtat(rs.getString("etat"));

                users.add(p);
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex; // it's better to throw the exception instead of returning null
        }
    }

    public int validate(String email, String password) {
        int id = 0;
        try {
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return id;
    }

    public User getUserById(int userId) {
        User user = null;
        try {
            String qry = "SELECT * FROM `user` WHERE id = " + userId + ";";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                // user.setRoles(roles.valueOf(rs.getString("roles")));
            }
            //"SELECT * FROM users WHERE email = ?"
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    /*    @Override
   public void register(User t) throws SQLException {
    String requeteInsert = "INSERT INTO `sportt`.`user` (`email`, `lastname` , `password`,`image`) VALUES (?, ?, ?, ?)";
    PreparedStatement pst = cnx.prepareStatement(requeteInsert);
    pst.setString(1, t.getEmail());
    pst.setString(2, t.getLastname());
    pst.setString(3, t.getPassword());
    pst.setString(4, t.getImage());
    pst.executeUpdate();
    pst.close();
}
     */
    public void register(User t) throws SQLException {
        stm = cnx.createStatement();
        String role = "[\"Role_USER\"]";
        String hashedPassword = BCrypt.hashpw(t.getPassword(), BCrypt.gensalt());
        String requeteInsert = "INSERT INTO `sportif`.`user` (`email`, `roles`, `password`, `lastname`, `diplome`, `experience`, `image`, `is_blocked`, `is_approved`) VALUES ('" + t.getEmail() + "', '" + role + "', '" + hashedPassword + "', '" + t.getLastname() + "', '" + t.getDiplome() + "', '" + t.getExperience() + "', '" + t.getImage() + "', " + t.isIs_blocked() + ", " + t.isIs_approved() + ")";
        stm.executeUpdate(requeteInsert);
        System.out.println(requeteInsert);
    }

    public void registercoach(User t) throws SQLException {
        stm = cnx.createStatement();
        String role = "[\"Role_Coach\"]";
        String hashedPassword = BCrypt.hashpw(t.getPassword(), BCrypt.gensalt());
        String requeteInsert = "INSERT INTO `sportif`.`user` (`email`, `roles`, `password`, `lastname`, `diplome`, `experience`, `image`, `is_blocked`, `is_approved`) VALUES ('" + t.getEmail() + "', '" + role + "', '" + hashedPassword + "', '" + t.getLastname() + "', '" + t.getDiplome() + "', '" + t.getExperience() + "', '" + t.getImage() + "', " + t.isIs_blocked() + ", " + t.isIs_approved() + ")";
        stm.executeUpdate(requeteInsert);
        System.out.println(requeteInsert);
    }

    public boolean isEmailRegistered(String email) throws SQLException {
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            ps = cnx.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                result = true;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return result;
    }

    public void ModifierUser(User u) throws SQLException {
        String requete = "UPDATE user SET email=?, lastname=?, password=? WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, u.getEmail());
        pst.setString(2, u.getLastname());
        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
        pst.setString(3, hashedPassword);
        pst.setInt(4, u.getId());
        pst.executeUpdate();
        System.out.println("utilisateur modifié avec succès");
    }

    @Override
    public List<Reclamations> recupererUser(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        try {
            String qry = "UPDATE `user` SET `password`='" + newPassword + "' WHERE id = '" + user.getId() + "';";
            stm = cnx.createStatement();
            stm.executeUpdate(qry);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getPasswordUser(int id) {
        String password = "";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT * FROM user WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            } else {
                System.out.println("User with ID " + id + " not found in the database.");
            }
        } catch (SQLException ex) {
            System.out.println("Error executing SQL query: " + ex.getMessage());
        }
        // Decrypt password before returning
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User getCurrentUser() {
        SessionManager session = SessionManager.getInstance().getSession();
        if (session != null) {
            int id = session.getUser().getId();
            String email = session.getUser().getEmail();
            String roles = session.getUser().getRoles();
            String password = getPasswordUser(id);
            String lastname = session.getUser().getLastname();
            String diplome = session.getUser().getDiplome();
            String experience = session.getUser().getExperience();
            String image = session.getUser().getImage();
            boolean isBlocked = session.getUser().isIs_blocked();
            boolean isApproved = session.getUser().isIs_approved();
            String etat = session.getUser().getEtat();

            User user = new User(id, email, roles, password, lastname, diplome, experience, image, isBlocked, isApproved, etat);
            return user;
        }
        return null;
    }

    public void updateUserPasswordByEmail(String email, String newPassword) {

        try {
            String qry = "UPDATE `user` SET `password`='" + newPassword + "' WHERE email = '" + email + "';";
            stm = cnx.createStatement();
            stm.executeUpdate(qry);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> Show() {
        List<User> list = new ArrayList<>();

        try {
            String req = "SELECT * from user";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("roles"),
                        rs.getString("password"),
                        rs.getString("lastname"),
                        rs.getString("diplome"),
                        rs.getString("experience"),
                        rs.getString("image"),
                        rs.getBoolean("is_blocked"),
                        rs.getBoolean("is_approved"),
                        rs.getString("etat")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public void bloquerUtilisateur(User user) {

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE user SET etat='bloqué', is_blocked=true WHERE id=?");
            ps.setInt(1, user.getId());
            int resultat = ps.executeUpdate();

            if (resultat > 0) {
                JOptionPane.showMessageDialog(null, "L'utilisateur " + user.getLastname() + " a été bloqué avec succès.", "Utilisateur bloqué", JOptionPane.WARNING_MESSAGE);

            } else {
                System.out.println("Impossible de bloquer l'utilisateur.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors du blocage de l'utilisateur : " + ex);
        }
    }

    public void debloquerUtilisateur(User user) {
        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE user SET etat='debloqué', is_blocked=false WHERE id=?");
            ps.setInt(1, user.getId());
            int resultat = ps.executeUpdate();

            if (resultat > 0) {

                JOptionPane.showMessageDialog(null, "L'utilisateur a été débloqué avec succès.");
            } else {
                System.out.println("Impossible de débloquer l'utilisateur.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors du déblocage de l'utilisateur : " + ex);
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String req = "SELECT * FROM `user` WHERE `email` = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("roles"),
                    rs.getString("password"),
                    rs.getString("lastname"),
                    rs.getString("diplome"),
                    rs.getString("experience"),
                    rs.getString("image"),
                    rs.getBoolean("is_blocked"),
                    rs.getBoolean("is_approved"),
                    rs.getString("etat")
            );

            return user; // user.setRoles(roles.valueOf(rs.getString("roles")));
        } else {
            return null; // no person found with this id
        }
    }

    public User recupererBynomUser(String lastname) throws SQLException {
        String req = "select * from user where lastname = ?";
        PreparedStatement st = cnx.prepareStatement(req);

        st.setString(1, lastname);
        ResultSet rs = st.executeQuery();
        User u = new User();
        rs.next();
        u.setId(rs.getInt("id"));
        u.setLastname(rs.getString("lastname"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));

        return u;
    }

}
