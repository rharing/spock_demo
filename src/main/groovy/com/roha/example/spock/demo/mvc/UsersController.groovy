package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.service.EventService
import com.roha.example.spock.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
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
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public createUser(@RequestBody UserDTO userDTO) {
        if(userDTO.id == null){
            userService.createUser(userDTO)
        }
    }

}
