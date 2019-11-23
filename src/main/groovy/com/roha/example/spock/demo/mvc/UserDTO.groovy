package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.model.User
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty

class UserDTO {
    Long id
    @NotEmpty
    private String name
    @NotEmpty
    @Email
    private String email
    @NotEmpty
    private String password
    @NotEmpty
    private String phone

    UserDTO(User user) {
        this.id = user.id
        this.name = user.name
        this.email = user.email
        this.password = user.password
        this.phone = user.phone
    }

    User asUser() {
        User user = new User()
        user.id = this.id
        user.name = this.name
        user.email = this.email
        user.password = this.password
        user.phone = this.phone
        user
    }
}
