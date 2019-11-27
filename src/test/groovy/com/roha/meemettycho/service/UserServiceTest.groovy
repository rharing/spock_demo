package com.roha.meemettycho.service

import com.roha.model.User
import com.roha.mvc.UserDTO
import com.roha.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Narrative("""
i want store users
""")
@Title("crudding users with spring added and storing in db")
@Issue("EVE-002")
@SpringBootTest
@Transactional
class UserServiceTest extends Specification {
    @Autowired
    UserService userService

    def setup(){

    }
    def "should save users"(){
        when: "an user is created"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        then:"the id of the user should be updated"
        user.id != null
    }
    def "complete crud"(){
        given: "a user is created"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        when:"an user is retrieved by its id"
        User persisted = userService.findById(user.id)
        then:"the user should be as defined"
        user == persisted
        when: "an user can be found using his email"
        persisted = userService.findByEmail("email@ronaldharing.com")
        then:"the user should be as defined"
        user == persisted
        when:"an existing user is updated as defined by id"
        UserDTO userdto = new UserDTO(persisted)
        userdto.name="updated"
        user = userService.saveUser(userdto)
        then: "the user should be updated"
        userdto.name=="updated"
        userdto.email=="email@ronaldharing.com"
        userdto.id==persisted.id

//        when:"a persisted user is compared to a new user"
//        then:"the error should show us that they are not the same"
//        persisted == new User(id:persisted.id, name:"Ronald", email:"email@ronaldharing.com", password: "password")
        when:"this user is deleted"
        userService.delete(persisted.id)
        persisted = userService.findByEmail("email@ronaldharing.com")
        then:"the user is not found when retrieving him/her"
        persisted == null
    }

    def "should not update existing user to another user with same email"(){
        given:"two existing users"
        User usera = userService.saveUser("ronalda","a@a.nl","password")
        User userb = userService.saveUser("ronaldb","b@b.nl","password")
        when: "usera changes email to userb"
        usera.email = "b@b.nl"
        User persisted = userService.saveUser(new UserDTO(usera))
then:
persisted == null
    }
    def "loading all users"(){
        given:"a number of users"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        user = userService.saveUser("tycho","tycho@easywriter.nl","password")
        when: "all users are retrieved"
        def users = userService.allUsersButMe user
        then:"we should have all users but the loggedin user"
        users.size() == 1
        users.get(0).email == "email@ronaldharing.com"

    }
}
