/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author hadjn
 */
public class Activiter {
    int id,ref_categ,id_user;
    String titre;
    Timestamp date_debut,date_fin;

    public Activiter() {
    }

    public Activiter(int id, int ref_categ, int id_user, String titre, Timestamp date_debut, Timestamp date_fin) {
        this.id = id;
        this.ref_categ = ref_categ;
        this.id_user = id_user;
        this.titre = titre;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    

    public Activiter(String titre, Timestamp date_debut, Timestamp date_fin) {
        this.titre = titre;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Activiter(int ref_categ, int id_user, String titre, Timestamp date_debut, Timestamp date_fin) {
        this.ref_categ = ref_categ;
        this.id_user = id_user;
        this.titre = titre;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRef_categ() {
        return ref_categ;
    }

    public void setRef_categ(int ref_categ) {
        this.ref_categ = ref_categ;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Activiter{" + "ref_categ=" + ref_categ + ", id_user=" + id_user + ", titre=" + titre + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }
    
    
    
    
}
