package com.roha.example.spock.demo.dao

import com.roha.example.spock.demo.model.User
import org.springframework.stereotype.Component

@Component
class UserRepository {
    List<User> users = new ArrayList<>()

    User save(User user) {
        user.id = users.size()
        this.users.add(user);
        user
    }

    User findById(long id) {
        users.find(){
            it.id == id
        }
    }
}
