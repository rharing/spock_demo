package com.roha.mvc

import com.roha.dao.EventRepository
import com.roha.mvc.EventsController
import com.roha.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.persistence.EntityManager

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

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
        response.contentAsString == "[]"
        response.status == 200
    }
}
