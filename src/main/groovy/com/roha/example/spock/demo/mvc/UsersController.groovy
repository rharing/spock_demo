package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.model.User
import com.roha.example.spock.demo.service.EventService
import com.roha.example.spock.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
@Controller

class UsersController {
    UserService userService

    UsersController(@Autowired UserService userService) {
        this.userService = userService
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDTO> events(){
        userService.all().collect{
            new UserDTO(it)
        }
    }
    @PostMapping(value = "/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user
        if(userDTO.id == null){
            user = userService.createUser(userDTO)
        }
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED)
    }

}
