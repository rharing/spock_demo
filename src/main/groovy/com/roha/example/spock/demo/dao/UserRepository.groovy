package com.roha.example.spock.demo.dao

import com.roha.example.spock.demo.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component


@Component
interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
