package com.roha.example.spock.demo.model

import org.joda.time.DateTime

class Event {
    Long id
    String title
    DateTime eventDate
    boolean indiestad = false
    Float price =0f
    String description
    String location = "Paradiso"
}
