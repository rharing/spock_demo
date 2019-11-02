package com.roha.example.spock.demo.dao

import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime

class EventRepository {
    List<Event> events = new ArrayList<>();

    public List<Event> getEvents(DateTime when) {
        def midnight = when.toDateMidnight()
        events.findAll {
            it.eventDate.toDateMidnight().isEqual(midnight)
        }
    }

    public Event getById(Long id){
        events.find(){
            it.id == id
        }
    }

    def save(Event event) {
        event.id = events.size()+1
        events.add(event)
        event
    }

    void update(Event event) {
        events.putAt(events.indexOf(event), event)
    }
}