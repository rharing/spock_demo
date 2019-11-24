package com.roha.example.spock.demo.mvc

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.roha.example.spock.demo.dao.EventRepository
import com.roha.example.spock.demo.dao.UserRepository
import com.roha.example.spock.demo.model.User
import com.roha.example.spock.demo.service.EventService
import com.roha.example.spock.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.persistence.EntityManager

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@DataJpaTest
class UsersControllerTest extends Specification {
    UsersController usersController
    UserService userService
    @Autowired
    UserRepository userRepository

    @Autowired
    EntityManager entityManager

    MockMvc mockMvc;

    def setup() {
        userService = new UserService()
        userService.userRepository = userRepository
        usersController = new UsersController(userService)

        mockMvc = standaloneSetup(usersController)
                .build();
    }
    def "should crud users"(){
        given: "an user"
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.setVisibility(PropertyAccessor.FIELD, ANY);
        UserDTO userDTO = new UserDTO(new User( name:"name", email:"a@a.nl", password:"password"))
        String json = objectMapper.writeValueAsString(userDTO)
        when:
        MockHttpServletResponse response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andReturn().response
        UserDTO result = objectMapper.readValue(response.contentAsString, UserDTO.class)
        then:
        response.status ==201
result.id != null;
        result.name == "name"
        result.email == "a@a.nl"

    }

}
