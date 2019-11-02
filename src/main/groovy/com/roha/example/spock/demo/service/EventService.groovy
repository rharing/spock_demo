package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.dao.EventRepository
import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime

class EventService {
    EventRepository eventRepository = new EventRepository();

    public Event createEvent(String title, DateTime when, boolean indiestad, String description) {
        Event event = new Event(title: title,description: description, eventDate:when, indiestad:indiestad)
        eventRepository.save(event)

    }
    public void update(Event event){
        eventRepository.update(event)
    }

    public List<Event> listEvents() {
        eventRepository.events
    }

    public List<Event> getEvents(DateTime when) {
        eventRepository.getEvents(when)
    }
    public Event getById(Long id) {
        return eventRepository.getById(id)
    }


}
