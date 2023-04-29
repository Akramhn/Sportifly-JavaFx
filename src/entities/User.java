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
public class User {
       private int id;
    private String email;
    public String roles;
        private String password;
    private String lastname;
    private String diplome;
    private String experience;
    private String image;
     private String reset_token;
     private boolean is_blocked;
      private boolean is_approved;
       private String etat;
           private String tel;
      //public static User Current_User;

    public User() {
    }
  

    public User(int id, String email,String roles, String password, String lastname, String diplome, String experience, String image, String reset_token, boolean is_blocked, boolean is_approved, String etat) {
        this.id = id;
        this.email = email;
     this.roles=roles;
        this.password = password;
        this.lastname = lastname;
        this.diplome = diplome;
        this.experience = experience;
        this.image = image;
        this.reset_token = reset_token;
        this.is_blocked = is_blocked;
        this.is_approved = is_approved;
        this.etat = etat;
    }
    public User(int id, String email,String roles, String lastname, String diplome, String experience, String image, boolean is_blocked, boolean is_approved, String etat) {
        this.id = id;
        this.email = email;
     this.roles=roles;
        this.password = password;
        this.lastname = lastname;
        this.diplome = diplome;
        this.experience = experience;
        this.image = image;
        this.reset_token = reset_token;
        this.is_blocked = is_blocked;
        this.is_approved = is_approved;
        this.etat = etat;
    }
    public User(String email, String password, String lastname, String diplome, String experience, String image) {
        this.email = email;
        this.password = password;
        this.lastname = lastname;
        this.diplome = diplome;
        this.experience = experience;
        this.image = image;
        this.id=id;
    }

    public User(String email, String password, String lastname) {
        this.email = email;
        this.password = password;
        this.lastname = lastname;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String lastname, String password, String image) {
         this.email = email;
        this.password = password;
        this.lastname = lastname;
        this.image=image;
    }

   

 public User(int id, String email, String roles, String password, String lastname, String diplome, String experience, String image, boolean is_blocked, boolean is_approved, String etat) {
    this.id = id;
    this.email = email;
    this.roles = roles;
    this.password = password;
    this.lastname = lastname;
    this.diplome = diplome;
    this.experience = experience;
    this.image = image;
    this.is_blocked= is_blocked;
    this.is_approved= is_approved;
    this.etat = etat;
}

  
  
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

  
    

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", roles=" + roles + ", password=" + password + ", lastname=" + lastname + ", diplome=" + diplome + ", experience=" + experience + ", image=" + image + ", reset_token=" + reset_token + ", is_blocked=" + is_blocked + ", is_approved=" + is_approved + ", etat=" + etat + '}';
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

   

  
    
       
       
       
       
}
