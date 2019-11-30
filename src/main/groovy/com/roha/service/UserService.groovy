package com.roha.service

import com.roha.dao.UserRepository
import com.roha.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    UserRepository userRepository

    User createUser(String name, String email, String password) {
        User user = new User(name: name, email: email, password: password)
        userRepository.save(user)
    }

    User findById(long id) {
        userRepository.findById(id).orElse(null)
    }

    User findByEmail(String email) {
        userRepository.findByEmail(email).orElse(null)
    }

    def delete(long id) {
        userRepository.deleteById(id)
    }
}
