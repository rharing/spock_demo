package com.roha.example.spock.demo.mvc

import com.roha.example.spock.demo.model.Invitee

class InvitedDTO {
    private long id;
    private String who
    private boolean accepted
    private boolean paid
    private boolean responded


    InvitedDTO(Invitee invitee) {
        this.id = invitee.id
        this.who = invitee.user.name
        this.accepted = invitee.accepted
        this.paid = invitee.paid
        this.responded = invitee.responded
    }
}
