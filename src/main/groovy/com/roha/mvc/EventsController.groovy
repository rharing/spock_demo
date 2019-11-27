package com.roha.mvc

import com.roha.service.EventService
import com.roha.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class EventsController {
    EventService eventService

    EventsController(@Autowired EventService eventService) {
        this.eventService = eventService
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public @ResponseBody
    List<EventDTO> events(){
        return eventService.listEvents().collect{
            new EventDTO(it)
        }
    }

}
