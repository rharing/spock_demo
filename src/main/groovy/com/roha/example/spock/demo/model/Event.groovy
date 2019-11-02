package com.roha.example.spock.demo.model

import org.joda.time.DateTime

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
class Event {
    @Id
    @GeneratedValue
    Long id
    String title
    Date eventDate
    boolean indiestad = false
    Float price =0f
    String description
    String location = "Paradiso"

    @ManyToMany(cascade = CascadeType.ALL )
    @JoinTable(
            name = "event_users",
            joinColumns = [ @JoinColumn(name = "event_id") ],
            inverseJoinColumns = [ @JoinColumn(name = "user_id") ]
    )    Set<User> invited = new HashSet<>()

    def invite(User user) {
        user.invitedfor.add(this)
        invited.add(user)
    }
}
