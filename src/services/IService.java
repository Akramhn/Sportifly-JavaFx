/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamations;
import entities.User;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author user
 * @param <u>
 */
public interface IService<u> {

    void adduser(u t) throws SQLException ;
        void register(u t)throws SQLException, NoSuchAlgorithmException;
         void registercoach(u t)throws SQLException, NoSuchAlgorithmException;

void ModifierUser(u t) throws SQLException;
    void SupprimerUser(u t) throws SQLException ;

    List<u> recuperer() throws SQLException ;
      public List<Reclamations> recupererUser(u t);
          public List<User> Show();

}


