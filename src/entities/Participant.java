package entities;


import util.RelationObject;

import java.time.LocalDate;

public class Participant {

    private int id;
    private LocalDate dateParticipation;
    private User user;
    private RelationObject event;

    public Participant(int id, LocalDate dateParticipation, User user, RelationObject event) {
        this.id = id;
        this.dateParticipation = dateParticipation;
        this.user = user;
        this.event = event;
    }

    public Participant(LocalDate dateParticipation, User user, RelationObject event) {
        this.dateParticipation = dateParticipation;
        this.user = user;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateParticipation() {
        return dateParticipation;
    }

    public void setDateParticipation(LocalDate dateParticipation) {
        this.dateParticipation = dateParticipation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RelationObject getEvent() {
        return event;
    }

    public void setEvent(RelationObject event) {
        this.event = event;
    }


}