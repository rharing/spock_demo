package com.roha.dao

import com.roha.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Component
interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email <> :email")
    List<User> allUsersButMe(@Param("email")String email)
}
