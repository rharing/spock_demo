package com.roha.example.spock.demo.mvc

import com.fasterxml.jackson.databind.BeanProperty
import com.roha.example.spock.demo.model.User
import org.apache.commons.beanutils.BeanUtils

class UserDTO {
    Long id
    private String name
    private String email
    private String password
    private String phone
    User user

    UserDTO(User user) {
        BeanUtils.copyProperties(this, user)

    }
}
