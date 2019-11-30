package com.roha.mvc

import com.roha.model.Invitee

/**
 * Created on 27-11-19.
 */
class InvitedDTO {
    private long id;
    private String who
    private boolean accepted
    private boolean paid
    private boolean responded


    public InvitedDTO(Invitee invitee) {
        this.id = invitee.id
        this.who = invitee.user.name
        this.accepted = invitee.accepted
        this.paid = invitee.paid
        this.responded = invitee.responded
    }

}
