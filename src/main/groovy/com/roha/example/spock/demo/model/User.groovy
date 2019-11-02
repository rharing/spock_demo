package com.roha.example.spock.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class User {
    @Id
    @GeneratedValue
    Long id
    private String name
    private String email
    private String password
    private String phone

    @ManyToMany(mappedBy = "invited")
    Set<Event> invitedfor = new HashSet<>()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
