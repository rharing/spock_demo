package com.roha.service

import com.roha.model.Event
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title


@Narrative("""
i want to create events and users and invite users to events so that i have a better recollection who is coming with me
""")
@Title("crudding events")
@Issue("EVE-001")
@SpringBootTest
@Transactional
class EventServiceTest extends Specification {
    @Autowired
    EventService eventService;

    def setup() {
    }

    def "should create event"() {
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        when: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        List<Event> allEvents = eventService.listEvents()
        def dayevents = eventService.getEvents(eventDate)
        then: "the id of the event should be updated"
        event.id != null
        and: "all events should have a size of 1"
        allEvents.size() == 1
        and: "the events for this day should have a size of 1"
        dayevents.size() == 1
        when: "another event on another day is added"
        event = eventService.createEvent("!!! (Chk Chk Chk)", eventDate.plusDays(1).toDate(), false, "!!! (Chk Chk Chk)")
        event.price = 20.5f
        event.location = "Bitterzoet"
        eventService.update(event)
        allEvents = eventService.listEvents()
        dayevents = eventService.getEvents(eventDate)
        then: "the newly added event should have an id"
        event.id != null
        and: "there should be a total of 2 events"
        allEvents.size() == 2
        and: "but the day events for the specific date should still be one"
        dayevents.size() == 1
        when: "retrieving event byId"
        Event persisted = eventService.getById(event.id);
        then: "event should have fields as defined"
        persisted.id != null
        persisted.title == "!!! (Chk Chk Chk)"
        persisted.price == 20.5f
    }
}
