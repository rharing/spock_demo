package com.roha.mvc

import com.roha.model.User
import com.roha.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.validation.Valid

@Controller

class UsersController extends ExceptionController {
    UserService userService

    UsersController(@Autowired UserService userService) {
        this.userService = userService
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    List<UserDTO> events() {
        userService.all().collect {
            new UserDTO(it)
        }
    }

    @GetMapping("/user/{id}")
    public @ResponseBody
    UserDTO getUser(@PathVariable Long id) {
        return new UserDTO(userService.findById(id))
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user
        if (userDTO.id == null) {
            user = userService.saveUser(userDTO)
        }
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED)
    }

}
