package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.Invitee
import com.roha.example.spock.demo.model.User
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.SmartMimeMessage
import org.springframework.stereotype.Component

import javax.mail.Message
import javax.mail.internet.MimeMessage

@Component
class MessageCreator {

    void createInvite(Invitee invitee, DateTimeFormatter fmt, MimeMessage mimeMessage, String baseUrl){
        Event event = invitee.event
        User user = invitee.user

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String message ="""Ga je op ${fmt.print(new DateTime(event.eventDate))} mee naar ${event.title}, kosten: ${event.price} locatie:${event.location}
<a href='${baseUrl}/ja/${invitee.id}'>ja leuk
<a href='${baseUrl}/nee/${invitee.id}'>helaas ik kan niet
"""
        helper.setText(message, true);
        helper.setTo("user.email");
        mimeMessage.setSubject("ga je mee naar $event.title? ");
        helper.setFrom("abc@gmail.com");
        mimeMessage
    }
}
