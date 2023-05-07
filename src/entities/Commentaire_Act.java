/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.time.LocalDateTime;



public class Commentaire_Act {

    private int id,id_actualite_id,id_user;
   
    private String contenu;
    
    private LocalDateTime date;
    
     

    public Commentaire_Act() {
    }

    public Commentaire_Act(int id, int id_actualite_id, int id_user, String contenu, LocalDateTime date) {
        this.id = id;
        this.id_actualite_id = id_actualite_id;
        this.id_user = id_user;
        this.contenu = contenu;
        this.date = date;
    }

    public Commentaire_Act(int id_actualite_id, int id_user, String contenu, LocalDateTime date) {
        this.id_actualite_id = id_actualite_id;
        this.id_user = id_user;
        this.contenu = contenu;
        this.date = date;
    }

    
    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

       public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

  

    public int getId_actualite_id() {
        return id_actualite_id;
    }

    public void setId_actualite_id(int id_actualite_id) {
        this.id_actualite_id = id_actualite_id;
    }

    
    
}


    
    

