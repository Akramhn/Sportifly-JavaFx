package entities;

import util.Constants;

import java.time.LocalDate;

public class Event implements Comparable<Event> {

    private int id;
    private String titre;
    private String description;
    private LocalDate date;
    private String image;
    private String lieu;

    public Event(int id, String titre, String description, LocalDate date, String image, String lieu) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.image = image;
        this.lieu = lieu;
    }

    public Event(String titre, String description, LocalDate date, String image, String lieu) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.image = image;
        this.lieu = lieu;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }


    @Override
    public int compareTo(Event event) {
        switch (Constants.compareVar) {
            case "Titre":
                return event.getTitre().compareTo(this.getTitre());
            case "Description":
                return event.getDescription().compareTo(this.getDescription());
            case "Date":
                return event.getDate().compareTo(this.getDate());
            case "Lieu":
                return event.getLieu().compareTo(this.getLieu());
            default:
                return 0;
        }
    }
}