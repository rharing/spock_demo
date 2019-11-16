package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.Invitee
import com.roha.example.spock.demo.model.User
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

import javax.mail.Message
import javax.mail.internet.MimeMessage

@Component
class MessageCreator {

    SimpleMailMessage createInvite(Invitee invitee,DateTimeFormatter fmt){
        Event event = invitee.event
        User user = Invitee.user
        MimeMessage mimeMessage = new MimeMessage()
        mimeMessage.setRecipients(Message.RecipientType.TO, user.email)
        mimeMessage.setSubject("ga je mee naar $event.title? ")
        tring message =""" 
Ga je op $fmt.print(event.eventDate) mee naar ${event.title}, kosten: ${event.price} locatie:${event.location}
<a href='${baseUrl}/ja/${invitee.id}'>ja leuk
<a href='${baseUrl}/nee/${invitee.id}'>helaas ik kan niet
"""
        mimeMessage.setContent(message," text/html")
        mimeMessage
    }
}
