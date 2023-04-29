/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author user
 */
public class Reclamations {
    private int id;
    private String type;
    private String description;
    private String date;
      private String etat;

    public Reclamations() {
    }

    public Reclamations(int id, String type, String description, String date, String etat) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.date = date;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamations{" + "id=" + id + ", type=" + type + ", description=" + description + ", date=" + date + ", etat=" + etat + '}';
    }
    
    
    
    
}
