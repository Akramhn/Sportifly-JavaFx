/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author wadah
 */
public class reservation {

    private int id, id_user;
    private int id_offre;
    private Date date;
    private String status;

    public reservation() {
    }

    public reservation(int id_user, int id_offre, Date date, String status) {
        this.id_user = id_user;
        this.id_offre = id_offre;
        this.date = date;
        this.status = status;
    }

    public reservation(int id, int id_user, int id_offre, Date date, String status) {
        this.id = id;
        this.id_user = id_user;
        this.id_offre = id_offre;
        this.date = date;
        this.status = status;
    }
    
    
    

    public reservation(int id_offre, Date date, String status) {
        this.id_offre = id_offre;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "reservation{" + "id=" + id + ", id_offre=" + id_offre + ", date=" + date + ", status=" + status + '}';
    }

}
