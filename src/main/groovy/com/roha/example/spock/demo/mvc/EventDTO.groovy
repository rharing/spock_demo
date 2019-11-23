package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.model.Event

class EventDTO {
    Long id
    String title
    Date eventDate
    boolean indiestad = false
    Float price = 0f
    String description
    String location

    List<InvitedDTO> invited = []


    EventDTO(Event event) {
        event
        this.id = event.id
        this.title  = event.title
        this.eventDate = event.eventDate
        this.indiestad = event.indiestad
        this.price = eveent.price
        this.description = event.description
        this.location = event.location
        event.invited.forEach {
            this.invited << new InvitedDTO(it)
        }
    }
}
