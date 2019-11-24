package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.dao.UserRepository
import com.roha.example.spock.demo.model.User
import com.roha.example.spock.demo.mvc.UserDTO
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
    User createUser(UserDTO userDTO) {
        User existingUser =findByEmail(userDTO.email)
        User user
        if(existingUser == null) {
             user = userRepository.save(userDTO.asUser())
        }
        else if(existingUser.id == userDTO.id){
            //update exitinguser
            user = userRepository.save(userDTO.asUser())
        }
        user
    }

    User findById(long id) {
        userRepository.findOne(id)
    }

    User findByEmail(String email) {
        userRepository.findByEmail(email).orElse(null)
    }

    def delete(long id) {
        userRepository.delete(id)
    }

    def allUsersButMe(User user) {
        userRepository.allUsersButMe(user.email)
    }

    List<User> all() {
        userRepository.findAll()
    }
}
