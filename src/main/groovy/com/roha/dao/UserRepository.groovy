package com.roha.dao

import com.roha.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component


@Component
interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
