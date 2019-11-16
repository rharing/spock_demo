package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.User
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class MessageCreator {
    SimpleMailMessage createInvite(Event event, User user ){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage()
        simpleMailMessage.setTo user.email
    }
}
