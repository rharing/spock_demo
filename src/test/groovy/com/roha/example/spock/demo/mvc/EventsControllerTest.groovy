package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.dao.EventRepository
import com.roha.example.spock.demo.service.CommunicationService
import com.roha.example.spock.demo.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.persistence.EntityManager

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.http.HttpStatus.*

@DataJpaTest
class EventsControllerTest extends Specification {

    EventsController eventsController
    EventService eventService
    @Autowired
    EventRepository eventRepository

    @Autowired
    EntityManager entityManager

    MockMvc mockMvc;

    def setup() {
        eventService = new EventService()
        eventService.eventRepository = eventRepository
        eventsController = new EventsController(eventService)

        mockMvc = standaloneSetup(eventsController)
                .build();
    }

    def "should crud events"() {
        when: "nothing here yet"
        MockHttpServletResponse response = mockMvc.perform(get("/events")).andReturn().response
        then:
        response.contentAsString =="[]"
        response.status == 200
    }
}
