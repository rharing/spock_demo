package com.roha.service

import com.roha.dao.EventRepository
import com.roha.model.Event
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventService {
//    EventRepository eventRepository = new EventRepository();
    @Autowired
    EventRepository eventRepository

    public Event createEvent(String title, Date when, boolean indiestad, String description) {
        Event event = new Event(title: title, description: description, eventDate: when, indiestad: indiestad)
        eventRepository.save(event)

    }

    public void update(Event event) {
        eventRepository.save(event)
    }

    public List<Event> listEvents() {
        eventRepository.findAll() as List<Event>
    }

    public List<Event> getEvents(DateTime when) {
        def midnight = when.toDateMidnight()
        List<Event> events = eventRepository.findAll() as List<Event>
        return events.findAll {
            new org.joda.time.DateMidnight(it.eventDate).isEqual(midnight)
        } as List<Event>
    }


    public Event getById(Long id) {
        return eventRepository.findById(id).orElse(null)
    }


}
