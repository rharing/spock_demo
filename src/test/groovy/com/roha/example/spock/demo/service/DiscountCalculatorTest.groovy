package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.*

@Narrative("""
If cards are bought more then 30 days in advance the buyer will get a 5 % discount and
If cards are bought more then 60 days in advance the buyer will get a 10 % discount
""")
@Title("testing boundaries for discounts events")
@Issue("EVE-001")
@SpringBootTest
@Transactional
@Subject(DiscountCalculator.class)
class DiscountCalculatorTest extends Specification {
    @Autowired
    EventService eventService;
    private DateTime eventDate
    private Event event
    DiscountCalculator discountCalculator = new DiscountCalculator()


    def setup() {
        eventDate = new DateTime(2019, 12, 4, 0, 0, 0)
        event = eventService.createEvent("toutpartout", this.eventDate.toDate(), true, "Whispering sons en meer", 20.0f)

    }

    @Unroll
    def "calculate discounts ticket"() {
        expect: "calculate the discount"
        price == discountCalculator.calculatePrice(event, eventDate.minusDays(days))
        where:
        price | days
        20.0f | 29
        19.0f | 30
        19.0f | 31
        19.0f | 59
        18.0f | 60
        18.0f | 61
    }
}
