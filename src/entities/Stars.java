/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author wadah
 */
public class Stars {
    int id ,user_id,id_offre,rate_index;

    public Stars() {
    }

    public Stars(int id, int user_id, int id_offre, int rate_index) {
        this.id = id;
        this.user_id = user_id;
        this.id_offre = id_offre;
        this.rate_index = rate_index;
    }

    public Stars(int user_id, int id_offre, int rate_index) {
        this.user_id = user_id;
        this.id_offre = id_offre;
        this.rate_index = rate_index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public int getRate_index() {
        return rate_index;
    }

    public void setRate_index(int rate_index) {
        this.rate_index = rate_index;
    }
    
    
    
}
