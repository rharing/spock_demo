package com.roha.example.spock.demo.model


import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Event {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    Long id
    String title
    Date eventDate
    boolean indiestad = false
    Float price = 0f
    String description
    String location = "Paradiso"

    @OneToMany( mappedBy = "event", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    Set<Invitee> invited = new HashSet<>()

    public Invitee invite(User user) {
        def invitee = new Invitee(user: user, event:this)
        user.invitedFor.add(invitee)
        invited.add(invitee)
        invitee
    }
}
