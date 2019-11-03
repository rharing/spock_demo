package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.dao.EventRepository
import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.User
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class EventService {
//    EventRepository eventRepository = new EventRepository();
    @Autowired
    EventRepository eventRepository
    @Autowired
    CommunicationService communicationService;
    DateTimeFormatter fmt = DateTimeFormat.forPattern("E d MMMM, yyyy");
    @Autowired
    DiscountCalculator discountCalculator
    public Event createEvent(String title, Date when, boolean indiestad, String description) {
        Event event = new Event(title: title, description: description, eventDate: when, indiestad: indiestad)
        eventRepository.save(event)

    }
    public Event createEvent(String title, Date when, boolean indiestad, String description, float price) {
        Event event = new Event(title: title, description: description, eventDate: when, indiestad: indiestad,price: price)
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

    def invite(Event event, User user) {
        event.invite(user)

        communicationService.sendInvite(user.email, "ga je mee naar $event.title","${event.description} speelt op ${fmt.print(new DateTime(event.eventDate))}")
    }

    def buyTicket(Event event, User user, DateTime buyingdate = new DateTime()) {
        def price = discountCalculator.calculatePrice(event, buyingdate)
        communicationService.sendInvite(user.email, "ticket binnen voor $event.title","je krijgt nog een tikkie na volgende deploy voor ${price}")
    }
}
