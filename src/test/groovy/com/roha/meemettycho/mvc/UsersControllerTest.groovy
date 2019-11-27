package com.roha.meemettycho.mvc


import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.roha.dao.UserRepository
import com.roha.model.User
import com.roha.mvc.UserDTO
import com.roha.mvc.UsersController
import com.roha.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.persistence.EntityManager

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@DataJpaTest
class UsersControllerTest extends Specification {
    UsersController usersController
    UserService userService
    @Autowired
    UserRepository userRepository

    @Autowired
    EntityManager entityManager

    MockMvc mockMvc
    private ObjectMapper objectMapper

    def setup() {
        userService = new UserService()
        userService.userRepository = userRepository
        usersController = new UsersController(userService)

        mockMvc = standaloneSetup(usersController).build()
        objectMapper = new ObjectMapper()
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, ANY);

    }

    def "should crud users"() {
        given: "an user"
        UserDTO userDTO = new UserDTO(new User(name: "name", email: "a@a.nl", password: "password"))
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
        response.status == 201
        result.id != null;
        result.name == "name"
        result.email == "a@a.nl"
        when: "requesting a specificUser"
        response = mockMvc.perform(get("/user/" + result.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andReturn().response
        UserDTO persistedUser = objectMapper.readValue(response.contentAsString, UserDTO.class)
        then:
        persistedUser != null
        persistedUser.email == "a@a.nl"
//        when:"updating an existinguser"

        when: "requesting all users"
        response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andReturn().response
        then:
        response.contentAsString == "[{\"id\":${result.id},\"name\":\"name\",\"email\":\"a@a.nl\",\"password\":\"password\",\"phone\":null}]"
        response.status == 200
    }

    def "checking validations"() {
        UserDTO userDTO = new UserDTO(new User(email: "a@a.nl", password: "password"))
        String json = objectMapper.writeValueAsString(userDTO)
        when:
        MockHttpServletResponse response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andReturn().response
        then:
        response.status == 400
        response.contentAsString == """{"fieldErrors":[{"codes":["NotEmpty.userDTO.name","NotEmpty.name","NotEmpty.java.lang.String","NotEmpty"],"arguments":[{"codes":["userDTO.name","name"],"arguments":null,"defaultMessage":"name","code":"name"}],"defaultMessage":"may not be empty","objectName":"userDTO","field":"name","rejectedValue":null,"bindingFailure":false,"code":"NotEmpty"},{"codes":["NotEmpty.userDTO.phone","NotEmpty.phone","NotEmpty.java.lang.String","NotEmpty"],"arguments":[{"codes":["userDTO.phone","phone"],"arguments":null,"defaultMessage":"phone","code":"phone"}],"defaultMessage":"may not be empty","objectName":"userDTO","field":"phone","rejectedValue":null,"bindingFailure":false,"code":"NotEmpty"}]}"""
    }

}
