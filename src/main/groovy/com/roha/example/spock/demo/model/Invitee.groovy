package com.roha.example.spock.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Invitee {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    Long id

    @ManyToOne
    private Event event
    @ManyToOne
    private User user
    private boolean accepted
    private boolean paid

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    Event getEvent() {
        return event
    }

    void setEvent(Event event) {
        this.event = event
    }

    User getUser() {
        return user
    }

    void setUser(User user) {
        this.user = user
    }

    boolean getAccepted() {
        return accepted
    }

    void setAccepted(boolean accepted) {
        this.accepted = accepted
    }

    boolean getPaid() {
        return paid
    }

    void setPaid(boolean paid) {
        this.paid = paid
    }
}
