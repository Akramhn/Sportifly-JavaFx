/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class Offre {
    
    private int id,id_user,id_categroy,nb_place ;
    private Date date ;
    private String affiche , description ;
    private float prix ;

    public Offre() {
    }

    public Offre(int id, int id_user, int id_categroy, int nb_place, Date date, String affiche, String description, float prix) {
        this.id = id;
        this.id_user = id_user;
        this.id_categroy = id_categroy;
        this.nb_place = nb_place;
        this.date = date;
        this.affiche = affiche;
        this.description = description;
        this.prix = prix;
    }

    public Offre(int id_user, int id_categroy, int nb_place, Date date, String affiche, String description, float prix) {
        this.id_user = id_user;
        this.id_categroy = id_categroy;
        this.nb_place = nb_place;
        this.date = date;
        this.affiche = affiche;
        this.description = description;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_categroy() {
        return id_categroy;
    }

    public int getNb_place() {
        return nb_place;
    }

    public Date getDate() {
        return date;
    }

    public String getAffiche() {
      
        return affiche;
    }

    public String getDescription() {
        return description;
    }

    public float getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_categroy(int id_categroy) {
        this.id_categroy = id_categroy;
    }

    public void setNb_place(int nb_place) {
        this.nb_place = nb_place;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", id_user=" + id_user + ", id_categroy=" + id_categroy + ", nb_place=" + nb_place + ", date=" + date + ", affiche=" + affiche + ", description=" + description + ", prix=" + prix + '}';
    }
    
    
    
    
    
    
    
    
   
}
