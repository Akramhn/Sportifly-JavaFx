/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Nour moutii
 */
public interface IServiceAct <T> {
       void ajouter(T t) throws SQLException ;

    void modifier(T t) throws SQLException ;

    void supprimer(T t) throws SQLException ;

    List<T> recuperer() throws SQLException ; 
}
