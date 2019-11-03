package com.roha.example.spock.demo.service

import com.roha.example.spock.demo.model.Event
import com.roha.example.spock.demo.model.User
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.transaction.annotation.Transactional
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Narrative("""
If cards are bought more then 30 days in advance the buyer will get a 5 % discount and
If cards are bought more then 60 days in advance the buyer will get a 10 % discount
""")
@Title("testing boundaries for discounts events")
@Issue("EVE-001")
@SpringBootTest
@Transactional
@Subject(DiscountCalculator)
class DiscountCalculatorTest extends Specification {
    @Autowired
    EventService eventService;
    private DateTime eventDate
    private Event event
    DiscountCalculator discountCalculator = new DiscountCalculator()


    def setup() {
        eventDate = new DateTime(2019,12,4,0,0,0)
        event = eventService.createEvent("toutpartout", this.eventDate.toDate(), true, "Whispering sons en meer", 20.0f)

    }

    def "calculate discounts ticket is bought less then 30 days in advance"() {
        when: "calculate the discount"
        def price = discountCalculator.calculatePrice(event, eventDate.minusDays(25))
        then:"no discount for you"
        price == 20.0f
    }

    def "calculate discounts ticket is bought more then 30 days in advance but less then 60"() {
        when: "calculate the discount"
        def price = discountCalculator.calculatePrice(event, eventDate.minusDays(31))
        then:"a 5percent discount should be applied"
        price == 19.0f
    }

    def "calculate discounts ticket is bought more then 60 days in advance"() {
        when: "calculate the discount"
        def price = discountCalculator.calculatePrice(event, eventDate.minusDays(61))
        then:"a 10 percent discount should be applied"
        price == 18.0f
    }
}