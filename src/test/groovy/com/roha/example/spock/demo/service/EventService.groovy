package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Narrative("""
i want to create events and users and invite users to events so that i have a better recollection who is coming with me
""")
@Title("creating events")
@Issue("EVE-001")
@Subject(EventService)
class EventServiceTest extends Specification {
    EventService eventService
    private DateTime eventDate

    def setup(){
        eventService = new EventService()
        eventDate = new DateTime(2019, 12, 4, 20, 0)
    }
    def "should create event"(){
        when: "an event is created"
        Event event = eventService.createEvent("toutpartout", this.eventDate, true, "Whispering sons en meer")
        List<Event> allEvents = eventService.listEvents()
        def dayevents = eventService.getEvents(this.eventDate)
        then:"the id of the event should be updated"
        event.id != null
        and:"all events should have a size of 1"
        allEvents.size() == 1
        and: "the events for this day should have a size of 1"
        dayevents.size() == 1
        when:"another event on another day is added"
        event = eventService.createEvent("!!! (Chk Chk Chk)", this.eventDate.plusDays(1), false, "!!! (Chk Chk Chk)")
        event.price= 20.5f
        event.location = "Bitterzoet"
        eventService.update(event)
        allEvents = eventService.listEvents()
        dayevents = eventService.getEvents(this.eventDate)
        then: "the newly added event should have an id"
        event.id != null
        and:"there should be a total of 2 events"
        allEvents.size() == 2
        and: "but the day events for the specific date should still be one"
        dayevents.size()==1
        when: "retrieving event byId"
        Event persisted =eventService.getById(event.id);
        then: "event should have fields as defined"
        persisted.id ==2
        persisted.title == "!!! (Chk Chk Chk)"
        persisted.price == 20.5f
    }
    def "shortcut test for creating events"(){
        expect:
        eventService.createEvent("toutpartout", eventDate, true, "Whispering sons en meer") != null

    }
}
