package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.stereotype.Component

@Component
class DiscountCalculator {
    float calculatePrice(Event event, DateTime buyingDate) {
        int daysinAdvance = Math.abs(Days.daysBetween(new DateTime(event.eventDate),buyingDate).getDays())
        float price = event.price
        if (daysinAdvance>=60){
            price = price - (0.1*price)
        }
        else if (daysinAdvance>=30){
            price = price - (0.05*price)
        }
        price
    }
}
