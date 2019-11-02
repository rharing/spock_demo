package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.User
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Narrative("""
i want store users
""")
@Title("crudding users with spring added")
@Issue("EVE-002")
@SpringBootTest
class UserServiceTest extends Specification {
    @Autowired
    UserService userService;

    def setup(){

    }
    def "should save users"(){
        when: "an user is created"
        User user = userService.createUser("ronald","email@ronaldharing.com","password")
        then:"the id of the user should be updated"
        user.id != null
    }
    def "complete crud"(){
        given: "a user is created"
        User user = userService.createUser("ronald","email@ronaldharing.com","password")
        when:"an user is retrieved by its id"
        User persisted =userService.findById(user.id)
        then:"the user should be as defined"
        user == persisted
    }
}
