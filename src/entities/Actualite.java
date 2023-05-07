package entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class Actualite {

    private int id;

    private String titre;

    private String image;

    private String description;

    private String categorie;

    private LocalDateTime date;

    private List<Commentaire_Act> commentaires; // Liste des commentaires associés à l'actualité

    public Actualite() {
    }

    public Actualite(String titre, String image, String description, String categorie) {
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
    }

    public Actualite(int id, String titre, String image, String description, String categorie, LocalDateTime date) {
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
    }

    public Actualite(int id, String titre, String image, String description, String categorie, LocalDateTime date, List<Commentaire_Act> commentaires) {
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.commentaires = new ArrayList<>(); // Initialisation de la liste des commentaires
    }

    public Actualite(String titre, String image, String description, String categorie, LocalDateTime date) {
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
    }

    public Actualite(String titre, String image, String description, String categorie, java.util.Date date) {
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.categorie = categorie;
        this.date = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Commentaire_Act> getCommentaires(Actualite actualite) {
        List<Commentaire_Act> commentairesDeLActualite = new ArrayList<>();
        for (Commentaire_Act commentaire : commentaires) {
            if (commentaire.getId_actualite_id() == actualite.getId()) {
                commentairesDeLActualite.add(commentaire);
            }
        }
        return commentairesDeLActualite;
    }

    public void ajouterCommentaire(Commentaire_Act commentaire) {
        this.commentaires.add(commentaire);
    }

    @Override
    public String toString() {
        return "Actualite{" + "id=" + id + ", titre=" + titre + ", image=" + image + ", description=" + description + ", categorie=" + categorie + ", date=" + date + '}';
    }

    /*  public Object getCommentaires() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
