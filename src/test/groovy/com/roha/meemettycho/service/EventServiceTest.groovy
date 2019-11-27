package com.roha.meemettycho.service

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetup
import com.roha.model.Event
import com.roha.model.User
import com.roha.service.EventService
import com.roha.service.UserService
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

import javax.mail.Message
import javax.persistence.EntityManager

@Narrative("""
i want to create events and users and invite users to events so that i have a better recollection who is coming with me
""")
@Title("crudding events")
@Issue("EVE-001")
@SpringBootTest
@Transactional()
@Stepwise
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application_test.properties")
class EventServiceTest extends Specification {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager entityManager
    private JavaMailSenderImplemailSender;
    @Value("\${email.host:}")
    String emailHost
    @Value("\${email.port:}") Integer emailPort
    @Value("\${email.username:}") String username
    @Value("\${email.pass:}") String password
    JavaMailSenderImpl emailSender;
    GreenMail testSmtp;
    def setup(){
        emailSender = new JavaMailSenderImpl();
        emailSender.setHost(emailHost);
        emailSender.setPort(emailPort);
        emailSender.setUsername(username);
        emailSender.setPassword(password);
        //emailSender.setDefaultEncoding("UTF_8");
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.transport.protocol","smtp");
        mailProps.setProperty("mail.smtp.auth","true");
        mailProps.setProperty("mail.smtp.starttls.enable","true");
        mailProps.setProperty("mail.debug","false");
        emailSender.setJavaMailProperties(mailProps);

        ServerSetup setup = new ServerSetup(65438, "localhost", "smtp");
        //Test properties
        testSmtp = new GreenMail(setup);
        //GreenMail server startup using test configuration
        testSmtp.start();
        //don't forget to set the same test port to EmailService, which is using by GreenMail
        emailSender.setPort(65438);
        emailSender.setHost("localhost");
    }
    def cleanup(){
        eventService.communicationService.mailSender = emailSender
        testSmtp.stop();
    }

    def "should create event"(){
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        when: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        List<Event> allEvents = eventService.listEvents()
        def dayevents = eventService.getEvents(eventDate)
        then:"the id of the event should be updated"
        event.id != null
        and:"all events should have a size of 1"
        allEvents.size() == 1
        and: "the events for this day should have a size of 1"
        dayevents.size() == 1
        when:"another event on another day is added"
        event = eventService.createEvent("!!! (Chk Chk Chk)", eventDate.plusDays(1).toDate(), false, "!!! (Chk Chk Chk)")
        event.price= 20.5f
        event.location = "Bitterzoet"
        eventService.update(event)
        allEvents = eventService.listEvents()
        dayevents = eventService.getEvents(eventDate)
        def chkchkchkid = event.id
        then: "the newly added event should have an id"
        event.id != null
        and:"there should be a total of 2 events"
        allEvents.size() == 2
        and: "but the day events for the specific date should still be one"
        dayevents.size()==1
        when: "retrieving event byId"
        Event persisted =eventService.getById(chkchkchkid);
        then: "event should have fields as defined"
        persisted.id ==chkchkchkid
        persisted.title == "!!! (Chk Chk Chk)"
        persisted.price == 20.5f
    }
    def "should invite users to events"(){
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        given: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        and:"i have some users"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        when: "i invite users to events"
        eventService.invite(event, user)
        entityManager.flush()
        entityManager.clear()
        Event persisted = eventService.getById(event.id)
        then: "the event should show who i have invited"
       persisted.invited.size() == 1
        persisted.invited[0].user.name =="ronald"
    }
    def"invitedusers should inform that they are going"(){
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        given: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        and:"i have some users"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        and: "i invite users to events"
        eventService.invite(event, user)
        when: "the users says ok"
        eventService.yesIWilljoin(event.id, user.id)
        entityManager.flush()
        event = eventService.getById(event.id)
        then:
        event.invited[0].accepted
        event.invited[0].responded

    }
    def"invitedusers should inform that they are not going"(){
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        given: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        and:"i have some users"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        and: "i invite users to events"
        eventService.invite(event, user)
        when: "the users says ok"
        eventService.NopeIwillPass(event.id, user.id)
        entityManager.flush()
        event = eventService.getById(event.id)
        then:
        !event.invited[0].accepted
        event.invited[0].responded

    }
    def "should send invites to users"(){
        eventService.communicationService.mailSender = emailSender
        def eventDate = new DateTime(2019, 12, 4, 20, 0)
        given: "an event is created"
        Event event = eventService.createEvent("toutpartout", eventDate.toDate(), true, "Whispering sons en meer")
        and:"i have some users"
        User user = userService.saveUser("ronald","email@ronaldharing.com","password")
        when: "i invite users to events"
        eventService.invite(event, user)
        Event persisted = eventService.getById(event.id)
        Long id = persisted.invited.first().id
        String expectedMessage = """Ga je op wo 4 december, 2019 mee naar toutpartout, kosten: 0.0 locatie:Paradiso
<a href='http://meemettycho/invite/ja/${id}'>ja leuk
<a href='http://meemettycho/invite/nee/${id}'>helaas ik kan niet"""
        expectedMessage = expectedMessage.replaceAll("\n","").replaceAll("\r","")
        testSmtp.waitForIncomingEmail(5000, 1);
        Message[] messages = testSmtp.getReceivedMessages();
        String bodyMail = GreenMailUtil.getBody(messages[0])
        bodyMail = bodyMail.replaceAll("\n","").replaceAll("\r","")
        then: "the mailsender should have sent an email"

        //Then after that using GreenMail need to verify mail sent or not
        messages.length == 1
        "ga je mee naar toutpartout?"== messages[0].getSubject()

       expectedMessage == bodyMail
    }
}
