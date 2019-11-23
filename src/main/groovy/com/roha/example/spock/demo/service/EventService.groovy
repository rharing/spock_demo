package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.dao.EventRepository
import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.Invitee
import com.roha.example.spock.demo.model.User
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.persistence.EntityManager


@Service
class EventService {
//    EventRepository eventRepository = new EventRepository();
    @Autowired
    EventRepository eventRepository
    @Autowired
    CommunicationService communicationService;
    @Autowired
    EntityManager entityManager

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
        return eventRepository.findOne(id)
    }

    def invite(Event event, User user) {
        Invitee invitee = event.invite(user)
        entityManager.flush()

        communicationService.sendInvite(invitee)
    }

    def yesIWilljoin(long eventId, long userId) {
        def event = getById(eventId)
        event.invited.every({
            if(it.user.id == userId){
                it.accepted =true
                it.responded =true
                eventRepository.save(it.event)
            }
        })
    }
    def NopeIwillPass(long eventId, long userId) {
        def event = getById(eventId)
        event.invited.every({
            if(it.user.id == userId){
                it.accepted =false
                it.responded =true
                eventRepository.save(it.event)
            }
        })
    }
}
