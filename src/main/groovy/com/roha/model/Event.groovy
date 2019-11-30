package com.roha.model


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

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
}
