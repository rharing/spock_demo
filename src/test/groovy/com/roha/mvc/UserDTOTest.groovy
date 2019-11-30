package com.roha.meemettycho.mvc

import com.roha.model.User
import com.roha.mvc.UserDTO
import spock.lang.Specification

class UserDTOTest extends Specification {

    def "should set properties from user"(){
        given: "an user"
        User user = new User(id:3, name:"name", email:"email@ronaldharing.com", password: "password" )
        when: 'dto is created from an user'
        def userdto = new UserDTO(user)
        then: "the fields should be copies from user"
        userdto.id == user.id
        userdto.name == user.name
        userdto.email == user.email
        userdto.password == user.password
        when: "userdto is requested as user"
        User user2 = userdto.asUser()
        then:"the user2 has same values as orignal user"
        user2.id == user.id
        user2.name == user.name
        user2.email == user.email
        user2.password == user.password
    }
}
