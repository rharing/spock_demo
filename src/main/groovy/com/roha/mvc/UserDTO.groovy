package com.roha.mvc

import com.roha.model.User

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


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

    UserDTO() {
    }

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

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }
}
